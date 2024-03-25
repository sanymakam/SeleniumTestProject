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

    private WebDriver webDriver;
    private final String browser = getBrowserValue();

    public void setWebDriverManager() {

        if (browser.equalsIgnoreCase(Constant.BROWSER_CHROME)) {
            WebDriverManager.chromedriver().setup();

        } else if (browser.equalsIgnoreCase(Constant.BROWSER_FIREFOX)) {
            WebDriverManager.firefoxdriver().setup();
        }
    }

    public WebDriver createDriverInstance() {
        setWebDriverManager();
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

    public void instantiateDriver() throws MalformedURLException {
        if (browser.equalsIgnoreCase(Constant.BROWSER_CHROME)) {
            webDriver = new ChromeDriver(getChromeOptions());
        } else if (browser.equalsIgnoreCase(Constant.BROWSER_FIREFOX)) {
            webDriver = new FirefoxDriver(getFirefoxOptions());
        }
    }

    public WebDriver getDriver() {
        return webDriver;
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
//		options.addArguments("--start-fullscreen");
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
}
