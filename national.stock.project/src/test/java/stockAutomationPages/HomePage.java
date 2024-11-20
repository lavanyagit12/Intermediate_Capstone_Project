package stockAutomationPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage {
    private WebDriver driver;
   
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private By searchBox = By.id("header-search-input");
    private By stockSymbol = By.xpath("//ul[@id='header-search-input_listbox']/li[1]");
   

    public HomePage(WebDriver driver) 
    {
        this.driver = driver;
    }
	
	/*
	 * public void searchStock(String stockName) {
	 * logger.info("Searching for stock :stockName");
	 * 
	 * driver.findElement(searchBox).sendKeys(stockName);
	 * 
	 * // Wait for the auto-suggestions to appear WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 *  WebElement autoSuggestion = wait.until(ExpectedConditions.visibilityOfElementLocated(stockSymbol));
	 * 
	 * // Click on the suggestion 
	 * autoSuggestion.click();
	 * 
	 * }
	 */
	 
}