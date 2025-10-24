package amazon;

import gradle.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

public class AmazonSearchTest extends BaseTest {
    @Test(groups = {"Amazon_in_search_for_shoe"})
    public void testSearchLaptop() {
        driver.get().navigate().to("https://www.amazon.in");
        driver.get().findElement(By.id("twotabsearchtextbox")).sendKeys("laptop");
        driver.get().findElement(By.xpath("//input[@value='Go']")).click();

        // Verify that the search results are displayed
        Assert.assertFalse(driver.get().findElements(By.cssSelector(".s-result-item")).isEmpty());
    }

    @AfterMethod
    public void teardown() {
        driver.get().quit();
    }
}