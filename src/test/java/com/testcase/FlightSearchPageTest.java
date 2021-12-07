package com.testcase;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.base.TestBase;
import com.pages.FlightSearchPage;
import com.pages.FlightSelectionPage;
import com.pages.HomePage;
import com.util.ExcelReader;

public class FlightSearchPageTest extends TestBase{
	// creating reference for HomePage, FlightSearchPage, and FlightSelectionPage objects
	HomePage homePage;
	FlightSearchPage flightSearchPage;
	FlightSelectionPage flightSelectionPage;
	
	public FlightSearchPageTest() {
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
		flightSearchPage = homePage.navigateToFlightSearch();
	}
	
	@Test(dataProvider = "getValidData")
	public void validateValidFlightSearch(String from, String to) {
		// return flight selection page object if flight search is successful
		flightSelectionPage = flightSearchPage.validateValidFlightSearch(from, to);
		// evaluate result
		Assert.assertTrue(flightSelectionPage != null);
		
		// log test as passed if search is successful
		ExtentTest test = extent.createTest("validateValidFlightSearchTest");
		reportLogger(test, "validateValidFlightSearch", flightSelectionPage != null);
		reportInfo(test, from, to);
	}
	
	@Test(dataProvider = "getInvalidData")
	public void validateInvalidFlightSearch(String from, String to) {
		Boolean actualResult = flightSearchPage.validateInvalidFlightSearch(from, to);
		Assert.assertTrue(actualResult);
		
		ExtentTest test = extent.createTest("validateInvalidFlightSearchTest");
		reportLogger(test, "validateInvalidFlightSearch", actualResult);
		reportInfo(test, from, to);
	}
	
	@DataProvider(name = "getValidData")
	public Object[][] getValidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "FSvalid";
		ExcelReader reader = new ExcelReader(path, sheet);
		Object[][] data = reader.getTestData(sheet);
		
		return data;
	}
	
	@DataProvider(name = "getInvalidData")
	public Object[][] getInvalidData(){
		String path = "./dataFiles/TestData.xlsx";
		String sheet = "FSinvalid";
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
	public void reportInfo(ExtentTest test, String from, String to) {
		test.info(String.format("From: %s", from));
		test.info(String.format("To: %s", to));
	}
}
