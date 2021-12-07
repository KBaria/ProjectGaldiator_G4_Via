package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class FlightSearchPage extends TestBase{
	// reference for the FlightSelectionPage object
	FlightSelectionPage flightSelectionPage;
	
	// performs valid flight search and returns FlightSelectionPage object
	public FlightSelectionPage validateValidFlightSearch(String source, String destination) {
		// sends data for the FROM field
		WebElement sourceAirport = driver.findElement(By.id("source"));
		sourceAirport.clear();
		sourceAirport.sendKeys(source);
		
		// clicks on the valid airport that appears in the drop down
		selectAirport(source, "1");
		
		// sends the data for the TO field
		WebElement destinationAirport = driver.findElement(By.id("destination"));
		destinationAirport.clear();
		destinationAirport.sendKeys(destination);
		
		// clicks on the valid airport that appears in the drop down
		selectAirport(destination, "2");
		
		// setting the departure date
		setDepartureDate();
		
		// click on the search button
		WebElement searchBtn = driver.findElement(By.id("search-flight-btn"));
		searchBtn.click();
		
		// make a new object for the FLightSelectionPage class
		flightSelectionPage = new FlightSelectionPage();
		
		// return the object
		return flightSelectionPage;
	}
	
	// performs invalid flight search
	public Boolean validateInvalidFlightSearch(String source, String destination) {
		// sends data for the FROM field
		WebElement sourceAirport = driver.findElement(By.id("source"));
		sourceAirport.clear();
		sourceAirport.sendKeys(source);
		
		// clicks on the valid airport that appears in the drop down
		selectAirport(source, "1");

		// sends the data for the TO field
		WebElement destinationAirport = driver.findElement(By.id("destination"));
		destinationAirport.clear();
		destinationAirport.sendKeys(destination);
		
		// clicks on the valid airport that appears in the drop down
		selectAirport(destination, "2");
		
		// setting the departure date
		setDepartureDate();
		
		// click on the search button
		WebElement searchBtn = driver.findElement(By.id("search-flight-btn"));
		searchBtn.click();
		
		// an error should pop up after searching for invalid airport location this is how we find that error
		Boolean result = driver.findElement(By.xpath("//div[contains(@class, 'qtip')]")).isDisplayed();
		
		// return true if error is visible
		return result;
	}
	
	// selecting the airport from the drop down list
	public void selectAirport(String location, String count) {
		try {
			// setting the explicit wait time should look for the valid airport up to 5 seconds after which it will throw exception
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
			// after entering airport data we look for a suggestion in the drop down that matches the airport data 
			WebElement airport = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(., '" + location + "')]")));
			
			// if airport is found click on it
			airport.click();
		}catch(Exception e) {
			// if we did not find valid airport for the To destination click on the calendar icon
			if(count.equals("2")) {
				driver.findElement(By.className("calendar-icon")).click();
			}
		}
	}
	
	// after selecting valid airport in the TO destination a calendar will automatically pop up
	public void setDepartureDate() {
		// this the Xpath for the calendar for the departure date
		String monthBoxXpath = "//div[@id='depart-cal']";
		
		// we get two month box inside the calendar @data-month='11' stands for the month December which is visible in the calendar
		// @data-data ='25' stands for 25th of December
		String datesXpath = monthBoxXpath + "//div[@data-month='11' and @data-year='2021']//div[@class='vc-cell ' and @data-date='25']";
		
		// get the date box for the above date 
		WebElement dateBox = driver.findElement(By.xpath(datesXpath));
		
		// click on the date box
		dateBox.click();
	}
}
