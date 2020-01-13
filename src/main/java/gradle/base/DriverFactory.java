package gradle.base;

import gradle.utils.ConfigReader;
import gradle.utils.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.net.MalformedURLException;

public class DriverFactory {

	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private String browser = getBrowserValue();

	public WebDriver setWebDriverManager() {

		if (browser.equalsIgnoreCase(Constant.BROWSER_CHROME)) {
			WebDriverManager.chromedriver().setup();

		} else if (browser.equalsIgnoreCase(Constant.BROWSER_FIREFOX)) {
			WebDriverManager.firefoxdriver().setup();
		}
		return createDriverInstance();
	}

	private WebDriver createDriverInstance() {
		try {
			instantiateDriver();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getDriver();
	}

	private String getBrowserValue() {
		if (ConfigReader.getConfigValue("browser") != null && !ConfigReader.getConfigValue("browser").isEmpty()) {
			return ConfigReader.getConfigValue("browser");
		} else {
			return "chrome";
		}
	}

	public synchronized void instantiateDriver() throws MalformedURLException {
		String browser = getBrowserValue();
		if (browser.equalsIgnoreCase(Constant.BROWSER_CHROME)) {
			tlDriver.set(new ChromeDriver(getChromeOptions()));
		} else if (browser.equalsIgnoreCase(Constant.BROWSER_FIREFOX)) {
			tlDriver.set(new FirefoxDriver(getFirefoxOptions()));
		}
	}

	public static synchronized WebDriver getDriver() {
		System.out.println("Thread id = " + Thread.currentThread().getId());
		// System.out.println("Hashcode of webDriver instance = " +
		// tlDriver.get().hashCode());
		return tlDriver.get();
	}

	public ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--start-fullscreen");
		options.addArguments("--no-proxy-server");
		return options;
	}
	
	// Get Firefox Options
		public FirefoxOptions getFirefoxOptions() {
			FirefoxOptions options = new FirefoxOptions();
			FirefoxProfile profile = new FirefoxProfile();
			options.setProfile(profile);
			// Accept Untrusted Certificates
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			// Use No Proxy Settings
			profile.setPreference("network.proxy.type", 0);
			// Set Firefox profile to capabilities
			options.setCapability(FirefoxDriver.PROFILE, profile);
			return options;
		}

	public static synchronized void quitDriverInstance() {
		if (!(tlDriver.get() == null)) {
			tlDriver.get().quit();
		} else {
			try {
				throw new Exception("WebDriver Exception Occurred");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
