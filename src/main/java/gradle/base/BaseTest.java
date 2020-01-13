package gradle.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class BaseTest extends DriverFactory {
	public WebDriver driver;

	@BeforeSuite(alwaysRun = true)
	public void setUp() {
		if (driver == null) {
			this.driver = new DriverFactory().setWebDriverManager();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterSuite(alwaysRun = true)
	public void shutDownBrowser() {
		if (DriverFactory.getDriver() != null) {
			DriverFactory.quitDriverInstance();
			driver.quit();
		}
	}
}
