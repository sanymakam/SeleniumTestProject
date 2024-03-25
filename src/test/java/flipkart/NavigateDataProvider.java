package flipkart;

import org.testng.annotations.DataProvider;

public class NavigateDataProvider {
    /**
     * Provides navigate data for the test
     */

    @DataProvider(name = "navigateSections",parallel = true)
    public static Object[][] navigateSections() {
        return new Object[][]{{"Mobiles","mobile-phones-store"}, {"Appliances","appliances"},{"Travel","travel"}};
    }
}
