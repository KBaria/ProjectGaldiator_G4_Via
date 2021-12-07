package com.testcase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.TestBase;
import com.pages.FlightBookingPage;
import com.pages.FlightSearchPage;
import com.pages.FlightSelectionPage;
import com.pages.HomePage;
import com.util.ExcelReader;

public class FlightBookingPageTest extends TestBase{
	// creating reference for HomePage, FlightSearchPage, FlightSelectionPage and FlightBookingPage objects
	HomePage homePage;
	FlightSearchPage flightSearchPage;
	FlightSelectionPage flightSelectionPage;
	FlightBookingPage flightBookingPage;
	
	public FlightBookingPageTest() {
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
		
		// click on the flights tab
		flightSearchPage = homePage.navigateToFlightSearch();
		// get the from and to destination from properties file
		String from = prop.getProperty("from");
		String to = prop.getProperty("to");
		// search flight
		flightSelectionPage = flightSearchPage.validateValidFlightSearch(from, to);
	}
	
	// perform valid flight booking
	@Test(dataProvider = "getValidData")
	public void validateValidFligtBooking(String titleVal, String fname, String lname, String phone, String email) {
		// Boolean variable to store result
		Boolean actualResult = false;
		try {
			// select flight, book it and enter data in flight booking page fields
			flightBookingPage = flightSelectionPage.validateFlightSelection();
			actualResult = flightBookingPage.validFLightBooking(titleVal, fname, lname, phone, email);
		// log test as failed if flight selection or booking is unsuccessful 
		}catch(Exception e) {
			ExtentTest test = extent.createTest("validateValidFlightBookingTest");
			test.log(Status.FAIL, "Test Failed");
			reportInfo(test, titleVal, fname, lname, phone, email);
		}
		
		// evaluate results
		Assert.assertTrue(actualResult);
		
		// log test asa passed if flight booking is successful
		ExtentTest test = extent.createTest("validateValidFlightBookingTest");
		test.log(Status.PASS, "Test Passed");
		reportInfo(test, titleVal, fname, lname, phone, email);
	}
	
	
	// performs invalid flight booking
	@Test(dataProvider = "getInvalidData")
	public void validateInvalidFlightBooking(String titleVal, String fname, String lname, String phone, String email) {
		Boolean actualResult = false;
		try {
			// select flight, book it and enter data in flight booking page fields
			flightBookingPage = flightSelectionPage.validateFlightSelection();
			actualResult = flightBookingPage.invalidFLightBooking(titleVal, fname, lname, phone, email);
		// log test as failed if flight selection is unsuccessful
		}catch(Exception e) {
			ExtentTest test = extent.createTest("validateInvalidFlightBookingTest");
			test.log(Status.FAIL, "Test Failed");
			reportInfo(test, titleVal, fname, lname, phone, email);
		}
		// evaluate results
		Assert.assertTrue(actualResult);
		
		// log test as passed if flight booking is unsuccessful
		ExtentTest test = extent.createTest("validateInvalidFlightBookingTest");
		test.log(Status.PASS, "Test Passed");
		reportInfo(test, titleVal, fname, lname, phone, email);
	}
	
	// get data for valid hotel searching
	@DataProvider(name = "getValidData")
	public Object[][] getValidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "FBvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// get data for invalid flight booking
	@DataProvider(name = "getInvalidData")
	public Object[][] getInvalidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "FBinvalid";
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
	public void reportInfo(ExtentTest test, String titleVal, String fname, String lname, String phone, String email) {
		test.info(String.format("Title: %s", titleVal));
		test.info(String.format("First name: %s", fname));
		test.info(String.format("Last name: %s", lname));
		test.info(String.format("Phone no.: %s", phone));
		test.info(String.format("Email ID: %s", email));
	}
}
