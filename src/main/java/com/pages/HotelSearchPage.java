package com.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class HotelSearchPage extends TestBase{
	// page factory for the hotel search button and the search bar
	@FindBy(xpath="//div[@class='element-hotel']//input[@name='destination']") WebElement searchBar;
	@FindBy(xpath="//div[contains(text(), 'Search Hotels')]") WebElement searchBtn;
	
	// reference for HotelSelectionPage object
	HotelSelectionPage hotelSelectionPage;
	
	// constructor to initialize page factory
	public HotelSearchPage() {
		PageFactory.initElements(driver, this);
	}
	
	// searches hotels based on the location
	public HotelSelectionPage validateHotelSearch(String location) {
		searchBar.clear();
		searchBar.sendKeys(location);
		List<WebElement> destinations = driver.findElements(By.xpath("//li[contains(., '" + location + "')]"));
		destinations.get(0).click();
		searchBtn.click();
		
		hotelSelectionPage = new HotelSelectionPage();
		
		return hotelSelectionPage;
	}
	
	// performs invalid search
	public Boolean invalidHotelSearch(String location) {
		searchBar.clear();
		searchBar.sendKeys(location);
		// try to search the hotel
		selectLocation(location);
		
		searchBtn.click();
		
		// if the search bar is highlighted red return true
		Boolean result = driver.findElement(By.className("qtip")).isDisplayed();
		
		return result;
	}
	
	// clicks the first list element that matches the location string or else displays invalid location
	public void selectLocation(String location) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement hotel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(., '" + location + "')]")));
			hotel.click();
		}catch(Exception e) {
			System.out.println("invalid hotel location " + location);
		}
	}
}
