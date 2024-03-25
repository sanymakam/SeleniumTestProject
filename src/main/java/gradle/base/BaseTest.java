package gradle.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest extends DriverFactory {
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    @BeforeMethod(alwaysRun = true)
    public void createBrowserInstances() {
        WebDriver webDriver = new DriverFactory().createDriverInstance();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.set(webDriver);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanupBrowserInstances() {
        driver.get().quit();
    }
}
