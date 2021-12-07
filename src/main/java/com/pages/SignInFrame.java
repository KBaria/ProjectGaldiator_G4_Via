package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class SignInFrame extends TestBase{
	// reference for the SignUpFrame object
	SignUpFrame signUpFrame;

	// clicks on the sign up tab and makes the object for the SignUpFrame class
	public SignUpFrame clickSignUp() {
		WebElement signUpTab = driver.findElement(By.className("signUpBtn"));
		signUpTab.click();

		signUpFrame = new SignUpFrame();
		return signUpFrame;
	}

	// performs login for valid credentials
	public Boolean validLogin(String email, String pass) {
		// sends data for the email field
		WebElement emailId = driver.findElement(By.id("loginIdText"));
		emailId.clear();
		emailId.sendKeys(email);

		//sends data for the password field
		WebElement password = driver.findElement(By.id("passwordText"));
		password.clear();
		password.sendKeys(pass);

		//clicks on the sign in button
		WebElement signInBtn = driver.findElement(By.id("loginValidate"));
		signInBtn.click();

		// closes alert
		closeAlerts();

		// after closing alert we check whether hi <user's name> is visible on the sign in tab
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement user = wait.until(ExpectedConditions.elementToBeClickable(By.id("userNameSecondaryNav")));

		// if the user's name is mentioned return true
		Boolean result = user.isDisplayed();

		return result;
	}

	// performs login for invalid credentials
	public Boolean invalidLogin(String email, String pass) {
		// send data for the email field
		WebElement emailId = driver.findElement(By.id("loginIdText"));
		emailId.clear();
		emailId.sendKeys(email);

		// send data for the password field
		WebElement password = driver.findElement(By.id("passwordText"));
		password.clear();
		password.sendKeys(pass);

		// click on the sign in button
		WebElement signInBtn = driver.findElement(By.id("loginValidate"));
		signInBtn.click();

		// we try to catch any errors
		Boolean result = errorCatch();

		// return true is error or alert is visible
		return result;
	}

	//	// returns true if the text box is highlighted red or an alert pops up
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
				errorFlag =  driver.findElement(By.xpath("//*[contains(@class, 'validatorError') or contains(@class, 'qtip')]")).isDisplayed();
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
