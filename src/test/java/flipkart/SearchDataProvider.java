package flipkart;

import org.testng.annotations.DataProvider;

import java.util.HashMap;

public class SearchDataProvider {
    /**
     * Provides search items for the test
     */

    @DataProvider(name = "searchItems")
    public static Object[][] searchItems() {
        return new Object[][]{{"Mobiles"}, {"Televisions"},{"Refrigerators"}};
    }
}
