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
import com.pages.HotelBookingPage;
import com.pages.HotelSearchPage;
import com.pages.HotelSelectionPage;
import com.util.ExcelReader;

public class HotelBookingTest extends TestBase{
	// creating reference for HomePage, HotelSearchPage, HotelSelectionPage and HotelBookingPage objects
	HomePage homePage;
	HotelSearchPage hotelSearchPage;
	HotelSelectionPage hotelSelectionPage;
	HotelBookingPage hotelBookingPage;
	
	public HotelBookingTest() {
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
		// clicking on the hotels tab on home page
		hotelSearchPage = homePage.navigatToHotelSearch();
		// getting the property for hotel search value from config.properties
		String hotel = prop.getProperty("hotel");
		// searching hotels
		hotelSelectionPage = hotelSearchPage.validateHotelSearch(hotel);
	}
	
	// performing valid hotel booking
	@Test(dataProvider = "getValidData")
	public void validateValidHotelBooking(String titleVal, String fname, String lname, String pan, String phone, String email) {
		// Boolean variable to store results
		Boolean actualResult = false;
		try{
			// selecting hotel and entering data inside the booking page fields 
			hotelBookingPage = hotelSelectionPage.validateHotelSelection();
			// if credit card field is visible return true and store the result
			actualResult = hotelBookingPage.validHotelBooking(titleVal, fname, lname, pan, phone, email);
		// if test fails log the test as failed
		}catch(Exception e) {
			ExtentTest test = extent.createTest("validateValidHotelBookingTest");
			test.log(Status.FAIL, "Test Failed");
			reportInfo(test, titleVal, fname, lname, pan, phone, email);
		}
		// evaluate result
		Assert.assertTrue(actualResult);
		
		// test is logged as passed if hotel booking is successful
		ExtentTest test = extent.createTest("validateValidHotelBookingTest");
		test.log(Status.PASS, "Test Passed");
		reportInfo(test, titleVal, fname, lname, pan, phone, email);
	}
	
	// performing invalid hotel booking
	@Test(dataProvider = "getInvalidData")
	public void validateInvalidHotelBooking(String titleVal, String fname, String lname, String pan, String phone, String email) {
		// Boolean variable to store results
		Boolean actualResult = false;
		try {
			// selecting hotel from results
			hotelBookingPage = hotelSelectionPage.validateHotelSelection();
		// log the test as failed if selection is unsuccessful 
		}catch(Exception e) {
			ExtentTest test = extent.createTest("validateInvalidHotelBookingTest");
			test.log(Status.FAIL, "Test Failed");
			reportInfo(test, titleVal, fname, lname, pan, phone, email);
		}
		// enter data in the hotel booking page fields and return true if any error appears
		actualResult = hotelBookingPage.invalidHotelBooking(titleVal, fname, lname, pan, phone, email);
		// evaluate result
		Assert.assertTrue(actualResult);
		
		// test is logged as passed if hotel booking is unsuccessful
		ExtentTest test = extent.createTest("validateInvalidHotelBookingTest");
		test.log(Status.PASS, "Test Passed");
		reportInfo(test, titleVal, fname, lname, pan, phone, email);
	}
	
	// get data for valid hotel booking
	@DataProvider(name = "getValidData")
	public Object[][] getValidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "HBvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// get data for invalid hotel booking
	@DataProvider(name = "getInvalidData")
	public Object[][] getInvalidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "HBinvalid";
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
	
	// displays the test data in extent reports
	public void reportInfo(ExtentTest test, String titleVal, String fname, String lname, String pan, String phone, String email) {
		test.info(String.format("Title: %s", titleVal));
		test.info(String.format("First name: %s", fname));
		test.info(String.format("Last name: %s", lname));
		test.info(String.format("PAN: %s", pan));
		test.info(String.format("Phone no.: %s", phone));
		test.info(String.format("Email ID: %s", email));
	}
}
