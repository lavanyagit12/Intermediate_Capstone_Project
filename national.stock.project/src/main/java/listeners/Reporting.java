package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporting extends TestListenerAdapter {

    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext testContext) {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String repName = "Test-Report-" + timeStamp + ".html";

            htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/" + repName);
            htmlReporter.config().setDocumentTitle("Automation Testing");
            htmlReporter.config().setReportName("Functional Testing");
            htmlReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Host Name", "Localhost");
            extent.setSystemInfo("QA Name", "Lavanya");
            extent.setSystemInfo("OS", "Windows 11");
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
        if (extent != null) {
            extent.flush();
        }
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        ExtentTest xTest = extent.createTest(tr.getName());
        test.set(xTest);

        test.get().log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
        test.get().log(Status.PASS, "Test Passed");

        attachScreenshot(tr.getName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        ExtentTest xTest = extent.createTest(tr.getName());
        test.set(xTest);

        test.get().log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
        test.get().log(Status.FAIL, "Test Failed");

        attachScreenshot(tr.getName());     
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        ExtentTest xTest = extent.createTest(tr.getName());
        test.set(xTest);

        test.get().log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
        test.get().log(Status.SKIP, "Test Skipped");
    }

    private void attachScreenshot(String testName) {
    	String dateFormat = new SimpleDateFormat("YYYY_MM_DD_hh_mm_ss").format(new Date());

        String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + testName + "_" + dateFormat  + ".png";
        File file = new File(screenshotPath);

        if (file.exists()) {
            try {
                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                test.get().log(Status.INFO, "Unable to attach screenshot: " + e.getMessage());
            }
        }
    }
}
