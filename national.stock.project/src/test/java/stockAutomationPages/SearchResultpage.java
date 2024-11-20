package stockAutomationPages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class SearchResultpage {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(SearchResultpage.class);

    private By currentValue = By.cssSelector("#quoteLtp");
    private By highValueText = By.xpath("//div[@class='col-md-3 card-spacing priceinfodiv']//tbody/tr[1]/td[1]");
    private By lowValueText = By.xpath("//div[@class='col-md-3 card-spacing priceinfodiv']//tbody/tr[2]/td[1]");

    public SearchResultpage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCurrentValue() {
        logger.info("Waiting for the current value element to become visible");

        // Wait for the current value element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement currentValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(currentValue));
        
        // Retrieve the text
        String currentValueText = currentValueElement.getText();
        
        logger.info("current stock value retrieved");
        
        System.out.println("Current Value:" + currentValueText);
		return currentValueText;
        
    }

    public String getHighValue() {
        //logger.info("Waiting for the 52-week high value element to become visible");

        // Wait for the high value row
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement highValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(highValueText));

        // Retrieve sibling value using relative XPath
        String highValueTextContent = highValueElement.getText();
        String fiftyTwoWeekHighValue = highValueElement.findElement(By.xpath("//span[@id='week52highVal']")).getText();
        
        logger.info("52 Week high value retrieved");
        
        System.out.println("High Value: " + highValueTextContent + " : " + fiftyTwoWeekHighValue);
		return fiftyTwoWeekHighValue;
        
    }

    public String getLowValue() {
        logger.info("Waiting for the 52-week low value element to become visible");

        // Wait for the low value row
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement lowValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(lowValueText));

        // Retrieve sibling value using relative XPath
        String lowValueTextContent = lowValueElement.getText();
        String fiftyTwoWeekLowValue = lowValueElement.findElement(By.xpath("//span[@id='week52lowVal']")).getText();
        
        logger.info("52 Week Low value retrieved");

        System.out.println("Low Value: " + lowValueTextContent + " : " + fiftyTwoWeekLowValue);
		return fiftyTwoWeekLowValue;
        
    }
    
    // Calculate Profit/Loss
    public void calculateProfitLoss() {
        try {
            // Get values as strings
            String currentValueStr = getCurrentValue();
            String highValueStr = getHighValue();
            String lowValueStr = getLowValue();
            
            // Convert values to double for calculation
            double current = Double.parseDouble(currentValueStr);
            double high = Double.parseDouble(highValueStr);
            double low = Double.parseDouble(lowValueStr);
            
            // Calculate profit or loss
            double profitFromLow = ((current - low) / low) * 100;
            double lossFromHigh = ((current - high) / high) * 100;

            // Print the results
            System.out.println("Profit from 52-week low: " + String.format("%.2f", profitFromLow) + "%");
            System.out.println("Loss from 52-week high: " + String.format("%.2f", lossFromHigh) + "%");

        } catch (NumberFormatException e) {
            logger.error("Error parsing numeric values for profit/loss calculation", e);
        }
    }
    
    
}
