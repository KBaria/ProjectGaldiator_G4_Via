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
import com.util.ExcelReader;

public class LoginTest extends TestBase{
	HomePage homePage;
	SignInFrame signInFrame;
	
	public LoginTest() {
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
		// clicks on sign in tab
		signInFrame = homePage.clickSignIn();
	}
	
	@Test(dataProvider = "getValidData")
	public void validateValidLogin(String email, String pass){
		// return true if user name is displayed on top right
		Boolean actualResult = signInFrame.validLogin(email, pass);
		// evaluate results
		Assert.assertTrue(actualResult);
		
		// log test as passed if login is successful
		ExtentTest test = extent.createTest("validateInvalidSignInTest");
		reportLogger(test, "validateValidLogin", actualResult);
		reportInfo(test, email, pass);
	}
	
	@Test(dataProvider = "getInvalidData")
	public void validateInvalidLogin(String email, String pass) {
		// return true if any errors appear after clicking on sign in button
		Boolean actualResult = signInFrame.invalidLogin(email, pass);
		// evaluate result
		Assert.assertTrue(actualResult);
		
		// log test asa passed if login is unsuccessful
		ExtentTest test = extent.createTest("validateInvalidSignInTest");
		reportLogger(test, "validateInvalidLogin", actualResult);
		reportInfo(test, email, pass);
	}
	
	// get data for valid login
	@DataProvider(name = "getValidData")
	public Object[][] getValidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "Lvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// get data for invalid login
	@DataProvider(name = "getInvalidData")
	public Object[][] getInvalidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "Linvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// close driver
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	
	public void reportLogger(ExtentTest test, String methodName, Boolean result) {
		if(result) {
			test.log(Status.PASS, methodName + " Test Passed successfully");
		}else {
			test.log(Status.FAIL, methodName + "Test Failed");
		}
	}
	
	// displays test data in extent reports
	public void reportInfo(ExtentTest test, String email, String pass) {
		test.info(String.format("Email Id: %s", email));
		test.info(String.format("Password: %s", pass));
	}
}
