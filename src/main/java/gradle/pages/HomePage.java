package gradle.pages;

import gradle.utils.ConfigReader;
import gradle.utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Home page related locators and actions to perform
 * 
 * @author santhoshlmakam
 *
 */
public class HomePage {
	public WebDriverUtils utility;
	public WebDriver driver;
//	private static final Logger logger = Logger.getLogger(HomePage.class.getName());

	@FindBy(xpath = "//img[@title='Flipkart']")
	WebElement flipkartLogo;

	@FindBy(xpath = "//input[@title='Search for products, brands and more']")
	WebElement searchBar;

	String productDescription = "//div/a[@title='%s']";

	@FindBy(xpath = "//span[text()='Cart']")
	WebElement cartButton;

	@FindBy(xpath = "//button[text()='✕']")
	WebElement closePopUp;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		utility = new WebDriverUtils(driver);
	}

	public void searchItem(String item) {
		utility.waitTillElementFound(searchBar,5);
		searchBar.sendKeys(item);
		searchBar.sendKeys(Keys.ENTER);
	}

	public void verifyTheSearch(String item) {
		String elements = String.format(productDescription,item);
		WebElement element = driver.findElement(By.xpath(String.format(productDescription,item)));
		utility.waitTillElementFound(element,25);
		Assert.assertTrue(utility.isElementPresent(element));
	}

	public void goToHomePage() {
		Reporter.log("Navigating to home page", true);
		driver.navigate().to(ConfigReader.getConfigValue("url"));
		utility.waitTillElementFound(flipkartLogo, 10);
		Assert.assertEquals(driver.getTitle(), "Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!");
		Reporter.log("Navigated to home page", true);
		if (utility.isElementPresent(closePopUp)) {
			closePopUp.click();
		}
	}
}
