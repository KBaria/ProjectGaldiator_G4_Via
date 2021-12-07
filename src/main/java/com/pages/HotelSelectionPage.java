package com.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class HotelSelectionPage extends TestBase{
	// reference for the HotelBookingPage
	HotelBookingPage hotelBookingPage;
	
	// performs selection of a hotel if the search is based on location and not the hotel name
	public HotelBookingPage validateHotelSelection() throws Exception{
		// wait for the progress bar to complete 100% width
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.attributeToBe(By.className("progressBar"), "style", "width: 100%; opacity: 0;"));
		
		// click on the select room button for the first hotel that appears on the page
		WebElement selectBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("selectBtn")));
		selectBtn.click();
		
		// make a list of book buttons after clicking on the select room button
		List<WebElement> bookBtn = driver.findElements(By.xpath("//div[@class='bookDiv']//div[contains(@class,'bookBtn')]"));
		
		// scroll to the DIV containing the first book button
		WebElement hotel = driver.findElement(By.xpath("//div[@class='result' and @id=0]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hotel);
		
		// click on the very first book button that appears
		bookBtn.get(0).click();
		
		// make an object for HotelBookingPage and return it
		hotelBookingPage = new HotelBookingPage();
		return hotelBookingPage;
	}
	
	// performs hotel room selection if the search is based on the hotel name
	public HotelBookingPage validateHotelSelectionByName() {
		// wait for the loading page to go away
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loadingFirst")));
		
		// click on the ROOMS & RATE tab
		WebElement selRoom = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), 'ROOMS & RATE')]")));
		selRoom.click();
		
		// click on the very first book room button (only works if the room is not sold out or else we have to manually click)
		WebElement hotel = driver.findElement(By.className("js-hotelName"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hotel);
		
		List<WebElement> bookBtn = driver.findElements(By.className("bookBtn"));
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", bookBtn.get(1));
		
		// return object for HotelBookingPage
		return new HotelBookingPage();
	}
}
