#!/usr/bin/env python3
"""
MCP – Model Context Protocol helper for Selenium + Java automation.

Usage examples:
  python3 mcp.py generate "Search for laptop" --url "https://www.amazon.in"
  python3 mcp.py run search
"""

import os
import subprocess
import typer
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

app = typer.Typer(help="Local MCP CLI for Selenium + Java automation")

# ──────────────────────────────────────────────────────────────
# Helpers
# ──────────────────────────────────────────────────────────────
def get_page_context(url: str, screenshot_path: str):
    """Launch a headless browser, capture HTML + screenshot, return cleaned HTML."""
    typer.echo(f"🌐  Fetching page source from {url}")

    chrome_opts = Options()
    # chrome_opts.add_argument("--headless=new")
    chrome_opts.add_argument("--no-sandbox")
    chrome_opts.add_argument("--disable-gpu")
    chrome_opts.add_argument("--window-size=1920,1080")

    driver = webdriver.Chrome(options=chrome_opts)
    driver.get(url)

    # Screenshot
    driver.save_screenshot(screenshot_path)
    typer.echo(f"📸  Saved screenshot → {screenshot_path}")

    html = driver.page_source
    driver.quit()

    # Clean & summarize HTML
    soup = BeautifulSoup(html, "html.parser")
    for tag in soup(["script", "style", "noscript"]):
        tag.decompose()

    # Keep top 10 interactive elements
    elements = []
    for el in soup.find_all(["input", "button", "a", "select", "textarea"]):
        text = (el.get_text() or "").strip()
        attrs = " ".join(f"{k}='{v}'" for k, v in el.attrs.items())
        elements.append(f"<{el.name} {attrs}>{text}</{el.name}>")
        if len(elements) >= 10:
            break

    summary = "\n".join(elements)
    return summary


def run_ollama(prompt: str) -> str:
    """Call a local Ollama model and return generated text."""
    model = os.getenv("OLLAMA_MODEL", "deepseek-coder:6.7b")
    typer.echo(f"🧠  Generating code using model: {model}")
    try:
        result = subprocess.run(
            ["ollama", "run", model],
            input=prompt.encode("utf-8"),
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            check=True,
        )
        return result.stdout.decode("utf-8")
    except subprocess.CalledProcessError as e:
        typer.echo(f"❌  Ollama error: {e.stderr.decode('utf-8')}")
        raise typer.Exit(code=1)


def save_generated_code(task: str, code: str) -> str:
    """Save model output under ./generated/java/<module>/"""
    safe_name = "".join(c if c.isalnum() else "_" for c in task).strip("_")
    out_dir = os.path.abspath(f"./generated/java/{safe_name}")
    os.makedirs(out_dir, exist_ok=True)
    out_path = os.path.join(out_dir, "GeneratedTest.java")
    with open(out_path, "w", encoding="utf-8") as f:
        f.write(code)
    typer.echo(f"✅  Saved generated code → {out_path}")
    return out_path


# ──────────────────────────────────────────────────────────────
# CLI commands
# ──────────────────────────────────────────────────────────────
@app.command()
def generate(
        task: str = typer.Argument(..., help="Describe the automation task."),
        url: str = typer.Option(None, "--url", "-u", help="Optional target URL to extract context from."),
):
    """
    Generate Java Selenium POM code for the given task.
    If a URL is provided, collects HTML + screenshot context first.
    """
    context_summary = ""
    screenshot_path = ""
    if url:
        screenshot_path = os.path.abspath("./context_screenshot.png")
        context_summary = get_page_context(url, screenshot_path)

    prompt = open("prompts/java_pagefactory_prompt.txt").read()
    final_prompt = f"{prompt}\n\nTASK: Generate automation for {task}\n\nCONTEXT:\n{context_summary}"
    code = run_ollama(final_prompt)
    save_generated_code(task, code)


@app.command()
def run(
        module: str = typer.Argument(None, help="Name of test module (folder) to run"),
        all: bool = typer.Option(False, "--all", "-a", help="Run all tests"),
):
    """Run generated Java tests using Maven."""
    if all:
        typer.echo("🚀 Running all tests with Maven...")
        subprocess.run("mvn test", shell=True)
        return

    if not module:
        typer.echo("❌ Please specify a module name or use --all.")
        raise typer.Exit(1)

    typer.echo(f"🚀 Running tests for module: {module}")
    subprocess.run(f"mvn test -Dtest=*{module}*", shell=True)


if __name__ == "__main__":
    app()
