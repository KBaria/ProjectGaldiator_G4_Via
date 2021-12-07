package com.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class HotelBookingPage extends TestBase{
	// method that probably is unused 
	public void validateRoomBooking() {
		List<WebElement> bookBtn = driver.findElements(By.xpath("//div[@class='bookDiv']//div[contains(@class,'bookBtn')]"));
		
		WebElement hotel = driver.findElement(By.xpath("//div[@class='result' and @id=0]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hotel);
		bookBtn.get(0).click();
	}
	
	// fill guest details based on the count i.e. first adult(0) or second adult(1)
	public void fillGuestDetails(String count, String titleVal, String fname, String lname) {
		// wait for title to be click-able
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement title = wait.until(ExpectedConditions.elementToBeClickable(By.name("Room0AdultTitle" + count)));
		
		// set the title to titleVal
		title.click();
		String titleOpt = "Room0AdultTitle" + count;
		WebElement option = driver.findElement(By.xpath("//select[@name = '" + titleOpt + "']//option[@value='" + titleVal + "']"));
		option.click();
		
		// send data for first name
		WebElement firstName = driver.findElement(By.name("Room0AdultFirstName" + count));
		firstName.clear();
		firstName.sendKeys(fname);
		
		// send data for last name
		WebElement lastName = driver.findElement(By.name("Room0AdultLastName" + count));
		lastName.clear();
		lastName.sendKeys(lname);
	}
	
	// fills the contact details
	public void fillContactDetails(String pan, String phone, String email) {
		WebElement panNo = driver.findElement(By.name("panNumber"));
		panNo.clear();
		panNo.sendKeys(pan);
		
		WebElement phoneNo = driver.findElement(By.id("contactMobile"));
		phoneNo.clear();
		phoneNo.sendKeys(phone);
		
		WebElement emailId = driver.findElement(By.id("contactEmail"));
		emailId.clear();
		emailId.sendKeys(email);
	}
	
	// performs valid hotel booking
	public Boolean validHotelBooking(String titleVal, String fname, String lname, String pan, String phone, String email) throws Exception{
		// fill the details for the first adult
		fillGuestDetails("0", titleVal, fname, lname);
		
		// if details are required for the second adult
		int size = driver.findElements(By.xpath("//div[contains(@id, 'room1Adult')]")).size();
		if(size>1) {
			// send dummy data
			fillGuestDetails("1", "Mr", "Fname", "Lname");
		}
		
		// fill the contact details of the first adult
		fillContactDetails(pan, phone, email);
		
		// payment button
		WebElement makePaymentBtn = driver.findElement(By.id("makePayCTA"));
		// scroll to payment button
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", makePaymentBtn);
		
		// TnC 
		WebElement termsCheckBox = driver.findElement(By.className("customCBox"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		termsCheckBox = wait.until(ExpectedConditions.elementToBeClickable(termsCheckBox));
		
		// click on TnC and Payment Button
		termsCheckBox.click();
		makePaymentBtn.click();
		
		// click on the payment button at the review itinerary page and return true if credit card number text box is visible
		Boolean result = confirmPayment();
		
		return result;
	}
	
	// performs invalid hotel booking
	public Boolean invalidHotelBooking(String titleVal, String fname, String lname, String pan, String phone, String email) {
		// similar to valid hotel booking details but at the end we check if any error appear and return true if so
		fillGuestDetails("0", titleVal, fname, lname);
		
		int size = driver.findElements(By.xpath("//div[contains(@id, 'room1Adult')]")).size();
		if(size>1) {
			fillGuestDetails("1", "Mr", "Fname", "Lname");
		}
		
		fillContactDetails(pan, phone, email);
		
		WebElement makePaymentBtn = driver.findElement(By.id("makePayCTA"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", makePaymentBtn);
		
		WebElement termsCheckBox = driver.findElement(By.className("customCBox"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		termsCheckBox = wait.until(ExpectedConditions.elementToBeClickable(termsCheckBox));
		
		termsCheckBox.click();
		makePaymentBtn.click();
		
		// here
		WebElement error = driver.findElement(By.xpath("//*[contains(@class, 'validatorError') or contains(@class, 'qtip')]"));
		Boolean result = error.isDisplayed();
		
		return result;
	}
	
	// click on the payment button at the review itinerary page and return true if credit card number text box is visible
	public Boolean confirmPayment() throws Exception{ 
			WebElement confirmPaymentBtn = driver.findElement(By.id("confirmProceedPayBtn"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			confirmPaymentBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmPaymentBtn));
			
			confirmPaymentBtn.click();
			
			WebElement cardDetails = wait.until(ExpectedConditions.elementToBeClickable(By.id("ccNum-allcardsuihandler")));
			
			Boolean result = cardDetails.isDisplayed();
			
			return result;
	}
}
