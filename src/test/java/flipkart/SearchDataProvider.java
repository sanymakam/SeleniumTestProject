package flipkart;

import org.testng.annotations.DataProvider;

public class SearchDataProvider {
    /**
     * Provides navigate data for the test
     */

    @DataProvider(name = "searchItems",parallel = true)
    public static Object[][] searchItems() {
        return new Object[][]{{"Mobiles","mobile-phones-store"}, {"Appliances","appliances"},{"Travel","travel"}};
    }
}
