package flipkart;

import gradle.base.BaseTest;
import gradle.pages.HomePage;
import org.testng.annotations.Test;

/**
 * Test class for sections navigation
 *
 * @author santhoshlmakam
 */
public class NavigateTest extends BaseTest {
    /**
     * This test will click on different section on Homepage
     * Verifies if navigated to correct page from the URL
     *
     * @param selectionName
     * @param verificationString
     */

    @Test(groups = "navigateTests", priority = 0, dataProvider = "navigateSections", dataProviderClass = NavigateDataProvider.class)
    public void navigateTests(String selectionName, String verificationString) {
        HomePage homePage = new HomePage(driver.get());
        homePage.goToHomePage();
        homePage.closeLoginPopUp();
        homePage.clickOnSection(selectionName);
        homePage.verifyCurrentPageByURL(verificationString);
    }
}
