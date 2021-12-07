package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class SignUpFrame extends TestBase{
	// performs valid registration
	public Boolean validateSignUp(String uname, String email, String pass, String phone) throws Exception{
		// sends data for email field
		WebElement emailId = driver.findElement(By.id("emailIdSignUp"));
		emailId.clear();
		emailId.sendKeys(email);

		// sends data for password field
		WebElement password = driver.findElement(By.id("passwordSignUp"));
		password.clear();
		password.sendKeys(pass);

		// sends data for name field
		WebElement name = driver.findElement(By.id("nameSignUp"));
		name.clear();
		name.sendKeys(uname);

		// sends data for phone number field
		WebElement phoneNo = driver.findElement(By.id("mobileNoSignUp"));
		phoneNo.clear();
		phoneNo.sendKeys(phone);

		// clicks on the sign up button
		WebElement signUpBtn = driver.findElement(By.id("signUpValidate"));
		signUpBtn.click();

		// closes alerts
		closeAlerts();

		// after closing alerts return true if users name is mentioned on the sign in tab if not we log the test as failed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement user = wait.until(ExpectedConditions.elementToBeClickable(By.id("userNameSecondaryNav")));
		Boolean result = user.isDisplayed();

		return result;
	}

	// performs invalid registration
	public Boolean validateInvalidSignUp(String uname, String email, String pass, String phone) {
		// sends data for email id
		WebElement emailId = driver.findElement(By.id("emailIdSignUp"));
		emailId.clear();
		emailId.sendKeys(email);

		// sends data for password field
		WebElement password = driver.findElement(By.id("passwordSignUp"));
		password.clear();
		password.sendKeys(pass);

		// sends data for name field
		WebElement name = driver.findElement(By.id("nameSignUp"));
		name.clear();
		name.sendKeys(uname);

		// sends data for phone number field
		WebElement phoneNo = driver.findElement(By.id("mobileNoSignUp"));
		phoneNo.clear();
		phoneNo.sendKeys(phone);

		// clicks on the sign up button
		WebElement signUpBtn = driver.findElement(By.id("signUpValidate"));
		signUpBtn.click();

		// we try to catch any errors
		Boolean result = errorCatch();

		// return true is error or alert is visible
		return result;
	}

	// returns true if the text box is highlighted red or an alert pops up
	public Boolean errorCatch() {
		// Boolean variables to check if the alert is visible (alertFlag) or the text box is highlighted in red (errorFlag)
		Boolean errorFlag = false;
		Boolean alertFlag = false;

		try {
			// if the alert pops up return true then and there
			alertFlag =  driver.findElement(By.id("viaAlert")).isDisplayed();
			if(alertFlag) {
				return alertFlag;
			}
		}catch(Exception e) {} // no need to handle any exception since any one of these two will appear no matter what

		try {
			// if the text box is highlighted red return true then and there
			errorFlag =  driver.findElement(By.xpath("//input[contains(@class, 'validatorError')]")).isDisplayed();
			if(errorFlag) {
				return errorFlag;
			}
		}catch(Exception e) {} // no need to handle any exception since any one of these two will appear no matter what

		return false; // this statement should not execute but we write it so the compiler wont get angry
	}

	// close alert if it pops up
	public void closeAlerts() {
		try {
			driver.findElement(By.className("viaAlertClose")).click();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
