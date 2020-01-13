package flipkart;

import gradle.base.BaseTest;
import gradle.pages.HomePage;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Test class for checkout cases
 *
 * @author santhoshlmakam
 */
public class SearchTest extends BaseTest {
    /**
     * Test case to checkout pillow with valid credit card
     *
     * @param checkOutObject
     */

    @Test(groups = "search", priority = 0, dataProvider = "searchItems", dataProviderClass = SearchDataProvider.class)
    public void searchItems(String item) {
		HomePage homePage = new HomePage(driver);
		homePage.goToHomePage();
		homePage.searchItem(item);
		homePage.verifyTheSearch(item);
    }
}
