package stockAutomationUtils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseClass {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) {
        logger.info("Setting up the browser: {}", browser);

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                logger.error("Unsupported browser: {}", browser);
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        logger.info("Browser setup complete.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing the browser.");
            driver.quit();
        }
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                logger.info("Capturing screenshot for failed test: {}", result.getName());
                TakesScreenshot ts = (TakesScreenshot) driver;
                File src = ts.getScreenshotAs(OutputType.FILE);
                String dateFormat = new SimpleDateFormat("YYYY_MM_DD_hh_mm_ss").format(new Date());

                String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + result.getName() + "_" + dateFormat  + ".png";
                File screenshotDir = new File(System.getProperty("user.dir") + "\\Screenshots\\");
                if (!screenshotDir.exists()) {
                    screenshotDir.mkdir();
                }

                FileUtils.copyFile(src, new File(screenshotPath));
                logger.info("Screenshot saved at: {}", screenshotPath);
            } catch (IOException e) {
                logger.error("Failed to capture screenshot: {}", e.getMessage());
            }
        }
    }
}
