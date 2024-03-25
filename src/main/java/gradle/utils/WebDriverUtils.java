package gradle.utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class WebDriverUtils {
    public WebDriver driver;

    public WebDriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void waitTillElementFound(WebElement element, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            Reporter.log("Timed out while waiting for: " + element + " for: " + timeOut, true);
        }
    }

    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    public String priceFormat(String price) {
        String[] priceString = new String[2];
        if (price.contains(".")) {
            price = price.split("\\.")[0];
        }

        if (price.contains(",")) {
            priceString = price.split(",");
            price = priceString[0] + priceString[1];
        }
        return price;
    }
}
