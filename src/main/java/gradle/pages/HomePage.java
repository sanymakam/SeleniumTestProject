package gradle.pages;

import gradle.utils.ConfigReader;
import gradle.utils.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Home page related locators and actions to perform
 *
 * @author santhoshlmakam
 */
public class HomePage {
    public WebDriverUtils utility;
    public WebDriver driver;
    @FindBy(xpath = "//img[@title='Flipkart']")
    WebElement flipkartLogo;
    String sectionSelection = "//img[@alt='%s']";
    @FindBy(xpath = "//span[text()='Cart']")
    WebElement cartButton;
    @FindBy(xpath = "//span[@role='button']")
    WebElement closePopUp;
    @FindAll(@FindBy(xpath = "//span[text()='VIEW ALL']"))
    WebElement[] viewAllButtons;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        utility = new WebDriverUtils(driver);
    }

    public void goToHomePage() {
        Reporter.log("Navigating to home page", true);
        driver.navigate().to(ConfigReader.getConfigValue("url"));
        utility.waitTillElementFound(flipkartLogo, 10);
        Assert.assertEquals(driver.getTitle(), "Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!");
        Reporter.log("Navigated to home page", true);
    }

    public void closeLoginPopUp() {
        utility.waitTillElementFound(closePopUp, 5);
        if (utility.isElementPresent(closePopUp)) {
            closePopUp.click();
        }
    }

    public void clickOnSection(String sectionName) {
        WebElement sectionLocator = driver.findElement(By.xpath(String.format(sectionSelection, sectionName)));
        utility.waitTillElementFound(sectionLocator, 10);
        sectionLocator.click();
    }

    public void verifyCurrentPageByURL(String verificationString) {
        Assert.assertTrue(driver.getCurrentUrl().contains(verificationString), "Verification String: "
                + verificationString + "\nIs not present in current url: " + driver.getCurrentUrl());
        Reporter.log("Navigated to: " + verificationString);
    }
}
