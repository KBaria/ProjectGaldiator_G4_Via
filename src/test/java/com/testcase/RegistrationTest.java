package com.testcase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.TestBase;
import com.pages.HomePage;
import com.pages.SignInFrame;
import com.pages.SignUpFrame;
import com.util.ExcelReader;


public class RegistrationTest extends TestBase{
	// creating reference for HomePage, SignInFrame and SignUpFrame objects
	HomePage homePage;
	SignInFrame signInFrame;
	SignUpFrame signUpFrame;
	
	public RegistrationTest() {
		// calling the constructor of TestBase class
		super();
	}
	
	// Method executes before the test case
	@BeforeMethod
	public void setUp() {
		// calling the initialize method of TestBase class
		super.initialize();
		// creating a HomePage object
		homePage = new HomePage();
		// closing the alerts
		homePage.closeAlerts();
		// click on the sign in tab
		signInFrame = homePage.clickSignIn();
		// click on the sign up tab
		signUpFrame = signInFrame.clickSignUp();
	}
	
	// performing valid registration
	@Test(dataProvider = "getValidData")
	public void validateValidRegistration(String uname, String email, String pass, String phone) {
		// Boolean variable to store results
		Boolean actualResult = false;
		try{
			// generate random phone number and email
			phone = randomPhNoGenerator();
			email = randomEmailGenerator();
			// if the user name is displayed on the top right return true
			actualResult = signUpFrame.validateSignUp(uname, email, pass, phone);
		// log test as failed if registration is unsuccessful
		}catch(Exception e) {
			ExtentTest test = extent.createTest("validateValidRegistrationTest");
			test.log(Status.FAIL, "Test Failed");
			reportInfo(test, uname, email, pass, phone);
		}
		// evaluate result
		Assert.assertTrue(actualResult);
		// log test as passed if registration is successful
		ExtentTest test = extent.createTest("validateValidSignUpTest");
		test.log(Status.PASS, "Test Passed");
		reportInfo(test, uname, email, pass, phone);
	}
	
	// performing invalid registration
	@Test(dataProvider = "getInvalidData")
	public void validateInvalidRegistration(String uname, String email, String pass, String phone) {
		// return true if any errors appear after clicking on sign up button
		Boolean actualResult = signUpFrame.validateInvalidSignUp(uname, email, pass, phone);
		// evaluate results
		Assert.assertTrue(actualResult);
		
		// log test as passed is registration is unsuccessful
		ExtentTest test = extent.createTest("validateInvalidRegistrationTest");
		test.log(Status.PASS, "Test Passed");
		reportInfo(test, uname, email, pass, phone);
	}
	
	// get data for valid registration
	@DataProvider(name = "getValidData")
	public Object[][] getValidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "Rvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// get data for invalid registration
	@DataProvider(name = "getInvalidData")
	public Object[][] getInvalidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "Rinvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// close driver
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	
	// displays test data in extent reports
	public void reportInfo(ExtentTest test, String uname, String email, String pass, String phone) {
		test.info(String.format("Email Id: %s", email));
		test.info(String.format("Password: %s", pass));
		test.info(String.format("Name: %s", uname));
		test.info(String.format("Phone no.: %s", phone));
	}
	
	// provides with a randomly generated phone number
	public String randomPhNoGenerator() {
		int min = 45000;
		int max = 89000;
		int PhNoLast5Digits = (int)(Math.random() * (max - min + 1) + min);
		String phNo = "99675" + String.valueOf(PhNoLast5Digits);
		return phNo;
	}
	
	// provides with a randomly generated email id
	public String randomEmailGenerator() {
		int min = 2000;
		int max = 4000;
		int emailDigits = (int)(Math.random() * (max - min + 1) + min);
		String email = "MScott" + String.valueOf(emailDigits) + "@gmail.com";
		return email;
	}
}
