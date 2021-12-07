package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class HomePage extends TestBase{
	// references for the sign in frame, hotel search Page & flight search page
	SignInFrame signInFrame;
	HotelSearchPage hotelSearchPage;
	FlightSearchPage flightSearchPage;
	
	// clicks on the sign in tab and makes object for the SignInFrame class
	public SignInFrame clickSignIn() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement signInTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("SignIn")));
		signInTab.click();
		signInFrame = new SignInFrame();
		return signInFrame;
	}
	
	// clicks on the hotels tab and makes the object for the HotelSearchPage class
	public HotelSearchPage navigatToHotelSearch() {
		WebElement hotelsTab = driver.findElement(By.id("Hotels"));
		hotelsTab.click();
		hotelSearchPage = new HotelSearchPage();
		return hotelSearchPage;
	}
	
	// makes the object for the FlightSearchPage class
	public FlightSearchPage navigateToFlightSearch() {
		flightSearchPage = new FlightSearchPage();
		return flightSearchPage;
	}
	
	// closes alerts
	public void closeAlerts() {
		// we try to close the alerts if they don't & we handle the exception as to not break the program
		try {
			driver.findElement(By.id("wzrk-cancel")).click();
			driver.findElement(By.className("viaAlertClose")).click();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
