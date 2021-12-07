package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class FlightBookingPage extends TestBase{
	// performs valid flight booking
	public Boolean validFLightBooking(String titleVal, String fname, String lname, String phone, String email) throws Exception{
		// waits for the title option to be click-able
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement title = wait.until(ExpectedConditions.elementToBeClickable(By.id("adult1Title")));

		// clicks on the title and sets it to the titleVal
		title.click();
		WebElement option = driver.findElement(By.xpath("//option[@value='" + titleVal + "']"));
		option.click();

		// sends data for the first name field
		WebElement firstName = driver.findElement(By.id("adult1FirstName"));
		firstName.clear();
		firstName.sendKeys(fname);

		// sends data for the last name field
		WebElement lastName = driver.findElement(By.id("adult1Surname"));
		lastName.clear();
		lastName.sendKeys(lname);

		// sends data for the phone number field
		WebElement phoneNo = driver.findElement(By.id("contactMobile"));
		phoneNo.clear();
		phoneNo.sendKeys(phone);

		// sends data for the email id field
		WebElement emailId = driver.findElement(By.id("contactEmail"));
		emailId.clear();
		emailId.sendKeys(email);

		// goes to the end of the form and clicks on the proceed to pay button
		fillPassengerForm();

		// clicks the button on the review itinerary page
		confirmPayment();

		// looks for the field to enter the credit card number
		WebElement cardDetails = wait.until(ExpectedConditions.elementToBeClickable(By.id("ccNum-allcardsuihandler")));
		Boolean result = cardDetails.isDisplayed();

		// if the field is found return true
		return result;
	}

	// performs invalid flight booking
	public Boolean invalidFLightBooking(String titleVal, String fname, String lname, String phone, String email) throws Exception{
		// waits for the title option to be click-able
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement title = wait.until(ExpectedConditions.elementToBeClickable(By.id("adult1Title")));

		// clicks on the title and sets it to the titleVal
		title.click();
		WebElement option = driver.findElement(By.xpath("//option[@value='" + titleVal + "']"));
		option.click();

		// sends data for the first name field
		WebElement firstName = driver.findElement(By.id("adult1FirstName"));
		firstName.clear();
		firstName.sendKeys(fname);

		// sends data for the last name field
		WebElement lastName = driver.findElement(By.id("adult1Surname"));
		lastName.clear();
		lastName.sendKeys(lname);

		// sends data for the phone number field
		WebElement phoneNo = driver.findElement(By.id("contactMobile"));
		phoneNo.clear();
		phoneNo.sendKeys(phone);

		// sends data for the email id field
		WebElement emailId = driver.findElement(By.id("contactEmail"));
		emailId.clear();
		emailId.sendKeys(email);

		// goes to the end of the form and clicks on the proceed to pay button
		fillPassengerForm();

		// we try to catch any errors
		String errorXpath = "//*[contains(@class, 'validatorError') or contains(@class, 'qtip')]";

		Boolean result = driver.findElement(By.xpath(errorXpath)).isDisplayed();

		// return true is error or alert is visible
		return result;
	}

	public void fillPassengerForm() throws Exception{
		// getting the DIV where the payment button is
		WebElement tncDiv = driver.findElement(By.className("tncDiv"));

		// waits for the total amount color to be green
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.attributeToBe(By.className("js-total-amt-pay"), "style", "color: rgb(18, 181, 138);"));

		// scrolls to the payment button
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tncDiv);

		// waits for the button to be click-able
		WebElement paymentBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("makePayCTA")));

		// clicks the payment button
		paymentBtn.click();
	}

	// clicks the payment button in the review itinerary page
	public void confirmPayment() {
		WebElement confirmPaymentBtn = driver.findElement(By.id("confirmProceedPayBtn"));
		confirmPaymentBtn.click();
	}
}
