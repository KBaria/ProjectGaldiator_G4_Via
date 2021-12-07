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
import com.pages.HotelSearchPage;
import com.pages.HotelSelectionPage;
import com.util.ExcelReader;

public class HotelSearchTest extends TestBase{
	// creating reference for HomePage, HotelSearchPage and HotelSelectionPage objects
	HomePage homePage;
	HotelSearchPage hotelSearchPage;
	HotelSelectionPage hotelSelectionPage;
	
	public HotelSearchTest() {
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
	}
	
	// performing valid hotel search
	@Test(dataProvider = "getValidData")
	public void validateValidHotelSearch(String location) {
		// perform search operation return hotel selection page object if relevant hotel is selected from the drop down
		hotelSelectionPage = hotelSearchPage.validateHotelSearch(location);
		// evaluate result
		Assert.assertTrue(hotelSelectionPage != null);
		
		// log the test as passed if search is successful
		ExtentTest test = extent.createTest("validateValidHotelSearch");
		reportLogger(test, "validateValidHotelSearch", hotelSelectionPage != null);
		reportInfo(test, location);
	}
	
	// performing invalid hotel booking
	@Test(dataProvider = "getInvalidData")
	public void validateInvalidHotelSearch(String location) {
		// perform search operation return true if drop down doesn't show results relevant to search value
		Boolean actualResult = hotelSearchPage.invalidHotelSearch(location);
		// evaluate results
		Assert.assertTrue(actualResult);
		
		// log the test as passed if search is unsuccessful
		ExtentTest test = extent.createTest("validateInvalidHotelSearch");
		reportLogger(test, "validateInvalidHotelSearch", actualResult);
		reportInfo(test, location);
	}
	
	// get data for valid hotel searching
	@DataProvider(name = "getValidData")
	public Object[][] getValidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "HSvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// get data for invalid hotel searching
	@DataProvider(name = "getInvalidData")
	public Object[][] getInvalidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "HSinvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	// close the driver
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
	public void reportInfo(ExtentTest test, String location) {
		test.info(String.format("Location: %s", location));
	}
}
