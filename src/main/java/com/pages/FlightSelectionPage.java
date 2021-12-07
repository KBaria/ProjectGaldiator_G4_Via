package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class FlightSelectionPage extends TestBase{
	// page factory for the book button 
	@FindBy(xpath="//button[contains(text(), 'Book' )]") WebElement bookBtn;
	
	// constructor to initialize the page factory
	public FlightSelectionPage() {
		PageFactory.initElements(driver, this);
	}
	
	// reference for the FlightBookingPage object
	FlightBookingPage flightBookingPage;
	
	// selects a flight by clicking on the book button and returns a FlightBookinggPage object
	public FlightBookingPage validateFlightSelection() throws Exception{
		// explicit wait to wait for the progress bar to finish loading up to 100%
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		// we wait for the width of the progress bar to become 100%
		wait.until(ExpectedConditions.attributeToBe(By.className("progressBar"), "style", "width: 100%; opacity: 0;"));
		
		// click on the first book button that appears on the page
		bookBtn = driver.findElement(By.xpath("//button[contains(@class, 'bookCTA' )]"));
		bookBtn.click();
		
		// make a new object for the FlightBookinPage class
		flightBookingPage = new FlightBookingPage();
		
		// return the object
		return flightBookingPage;
	}
}
