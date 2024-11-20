package stockAutomationTests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import stockAutomationPages.HomePage;
import stockAutomationPages.SearchResultpage;
import stockAutomationUtils.*;

public class StockInfoValidationTest extends BaseClass {

    private static final Logger logger = LoggerFactory.getLogger(StockInfoValidationTest.class);

    @Test
    public void verifyStockInformation() throws InterruptedException {
   
        logger.info("Starting the stock information verification test");

        // Get Base URL and Stock Name from config.properties
        String baseUrl = ConfigReader.getProperty("baseUrl");
        String stockName = ConfigReader.getProperty("stockName");
        
        String url = baseUrl +  "?symbol=" +stockName;

   
        logger.info("Base URL: {}", url);
        logger.info("Stock Name: {}", stockName);

        // Navigate to NSE website
        driver.get(url);
       logger.info("Navigated to NSE India website");

        // Create page object for home page
        HomePage homePage = new HomePage(driver);

        // Search for stock
        //homePage.searchStock(stockName);
        
        
        //Create page object for SearchResults
        SearchResultpage searchResultpage = new SearchResultpage(driver);
        
        // Expected values
        String expectedCurrentValue = ConfigReader.getProperty("expectedCurrentValue");
        String expectedHighValue = ConfigReader.getProperty("expectedHighValue") ;
        String expectedLowValue = ConfigReader.getProperty("expectedLowValue");

        // Actual values
        String actualCurrentValue = searchResultpage.getCurrentValue();
        String actualHighValue = searchResultpage.getHighValue();
        String actualLowValue = searchResultpage.getLowValue();

        // Soft assertions
        //SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(actualCurrentValue, expectedCurrentValue, "Current value does not match");
        Assert.assertEquals(actualHighValue, expectedHighValue, "High value does not match");
        Assert.assertEquals(actualLowValue, expectedLowValue, "Low value does not match");

        //Calculate profit/Loss
        searchResultpage.calculateProfitLoss();
        
		logger.info("Stock information verification test completed successfully");
		
		
		    
    }
}
