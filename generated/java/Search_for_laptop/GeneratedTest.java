Here is a simple example of how you could write this test using Selenium WebDriver with the Page Object Model in Java:

First, define your page object:
```java
public class AmazonHomePage {
    private final By searchBox = By.id("twotabsearchtextbox");
    
    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchForLaptop() {
        WebElement search = driver.findElement(searchBox);
        search.sendKeys("laptop");
        search.submit();
    }
}
```
In the above code, we have defined a page object `AmazonHomePage` with an action method `searchForLaptop()` that sends 'laptop' to the search box and submits it. The locator for the search box is stored in a private By variable. 

Next, define your test:
```java
public class AmazonTest {
    WebDriver driver;
    
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver(); // or any other browser's driver you are using
        driver.get("https://www.amazon.in");
    }
  
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void searchLaptopTest() {
        AmazonHomePage homePage = new AmazonHomePage(driver);
        homePage.searchForLaptop();
        
        // Add assertion or any other test steps here
    }
}
```
In the above code, in `setUp` method we initialize our WebDriver and navigate to the desired webpage. In `tearDown` method we close the browser. The `searchLaptopTest` is a simple JUnit test where we create an instance of `AmazonHomePage` and call its action method.

Please ensure you have added required Selenium library dependencies in your Maven or Gradle project, replace 'ChromeDriver()' with WebDriver based on the browser driver you are using. Also, consider handling exceptions for better error handling during tests execution. 

Remember that each locator could be different depending on website structure and elements which might change over time, so this is a basic example to get you started. You should update your test accordingly as per the actual website changes or requirements of your project. 

Please ensure to replace `AmazonHomePage` and `AmazonTest` with actual names based on your project’s naming conventions. Also, in case Amazon's URL is different for non-indian customers you might want to use a dynamic one (like https://www.amazon.com) or configure it as per the requirements of your test.


