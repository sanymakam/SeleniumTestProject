//package gradle.listeners;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
//import com.aventstack.extentreports.reporter.configuration.ChartLocation;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//import com.maven.base.DriverFactory;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.Augmenter;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//
//public class ExtentReportListener implements ITestListener {
//    private static final String OUTPUT_FOLDER = "/test-output/";
//    private static final String FILE_NAME = "testReport.html";
//    private static ExtentReports extent = init();
//    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
//    private static ExtentReports init() {
//
//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + OUTPUT_FOLDER + FILE_NAME);
//        htmlReporter.config().setDocumentTitle("Amazon Search");
//        htmlReporter.config().setReportName("Amazon Search Automation Test Results");
//        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
//        htmlReporter.config().setTheme(Theme.STANDARD);
//
//        extent = new ExtentReports();
//        extent.attachReporter(htmlReporter);
//        extent.setReportUsesManualConfiguration(true);
//
//        return extent;
//    }
//
//    @Override
//    public synchronized void onStart(ITestContext context) {
//        System.out.println("Test Suite started!");
//    }
//
//    @Override
//    public synchronized void onFinish(ITestContext context) {
//        System.out.println(("Test Suite is ending!"));
//        extent.flush();
//        test.remove();
//    }
//
//    @Override
//    public synchronized void onTestStart(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " started!"));
//        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
//
//        extentTest.assignCategory(result.getTestContext().getSuite().getName());
//        test.set(extentTest);
//        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
//    }
//
//    @Override
//    public synchronized void onTestSuccess(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " passed!"));
//        test.get().pass("Test passed");
//        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
//    }
//
//    @Override
//    public synchronized void onTestFailure(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " failed!"));
//        try {
//        	String screenShot = getScreenshot(result);
//        	if (screenShot!=null) {
//        		test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(screenShot.replace("build/", "")).build());
//			}
//        } catch (IOException e) {
//            System.err.println("Exception thrown while updating test fail status " + Arrays.toString(e.getStackTrace()));
//        }
//        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
//    }
//
//    @Override
//    public synchronized void onTestSkipped(ITestResult result) {
//        System.out.println((result.getMethod().getMethodName() + " skipped!"));
//        try {
//            test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(result).replace("build/", "")).build());
//        } catch (IOException e) {
//            System.err.println("Exception thrown while updating test skip status " + Arrays.toString(e.getStackTrace()));
//        }
//        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
//    }
//
//    @Override
//    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
//    }
//
//    /*private String getScreenshotPath(final String testName) {
//        System.out.println("====> fails here" + testName);
//        File dir = new File("./build/screenshots");
//        File[] files = dir.listFiles();
//
//        List<Object> list = Arrays.stream(files)
//                .filter(p -> p.getName().contains(testName + ".png"))
//                .sorted(Comparator.comparing(File::lastModified))
//                .collect(Collectors.toList());
//        return list.size() > 0 ? list.get(0).toString().replace("/build", "") : "";
//    }*/
//
//    private Date getTime(long millis) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(millis);
//        return calendar.getTime();
//    }
//
//    private boolean createFile(File screenshot) {
//        boolean fileCreated = false;
//
//        if (screenshot.exists()) {
//            fileCreated = true;
//        } else {
//            File parentDirectory = new File(screenshot.getParent());
//            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
//                try {
//                    fileCreated = screenshot.createNewFile();
//                } catch (IOException errorCreatingScreenshot) {
//                    errorCreatingScreenshot.printStackTrace();
//                }
//            }
//        }
//
//        return fileCreated;
//    }
//
//    private void writeScreenshotToFile(WebDriver driver, File screenshot) {
//        try {
//            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
//            screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
//            screenshotStream.close();
//        } catch (IOException unableToWriteScreenshot) {
//            System.err.println("Unable to write " + screenshot.getAbsolutePath());
//            System.err.println(Arrays.toString(unableToWriteScreenshot.getStackTrace()));
//        }
//    }
//
//    private boolean isBrowserExist(WebDriver driver) {
//
//    	if (driver!=null) {
//			try {
//				return (driver.getWindowHandles() != null);
//			}catch(Exception e) {
//				return false;
//			}
//		}
//
//    	return false;
//    }
//
//    public String getScreenshot(ITestResult failingTest) {
//        try {
//            System.out.println("Taking Screen Shot ");
//            WebDriver driver = DriverFactory.getDriver();
//            System.out.println("--- " + driver);
//            if (isBrowserExist(driver)) {
//            	String screenshotDirectory = System.getProperty("screenshotDirectory", "build/screenshots");
//            	String screenshotAbsolutePath = screenshotDirectory + File.separator + System.currentTimeMillis() + "_" + failingTest.getName() + "_" + Thread.currentThread().getId() + ".png";
//            	File screenshot = new File(screenshotAbsolutePath);
//            	if (createFile(screenshot)) {
//            		try {
//            			writeScreenshotToFile(driver, screenshot);
//            		} catch (ClassCastException weNeedToAugmentOurDriverObject) {
//            			writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
//            		}
//            		System.out.println("Written screenshot to " + screenshotAbsolutePath);
//            	} else {
//            		System.err.println("Unable to create " + screenshotAbsolutePath);
//            	}
//            	return screenshotAbsolutePath;
//			}
//        } catch (Exception ex) {
//            System.err.println("Unable to capture screenshot...");
//            System.err.println(Arrays.toString(ex.getStackTrace()));
//        }
//        return null;
//    }
//}
