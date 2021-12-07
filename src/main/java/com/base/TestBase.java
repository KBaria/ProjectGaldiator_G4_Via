package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	// reference for the web driver, properties file, extent reports and spark reporter 
	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports extent;
	public static ExtentSparkReporter reporter; 
	
	public TestBase() {
		// loading the config.properties file
		FileInputStream fis;
		prop = new Properties();
		try {
			fis = new FileInputStream("./src/main/java/com/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		// getting the browser value and url value from properties file
		String browser = prop.getProperty("browser");
		String url = prop.getProperty("url");
		
		// changing the driver type based on the browser value from properties file 
		if(browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if(browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}else {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		
		// getting the implicit and page load times from the properties file
		int pageLoad = Integer.parseInt(prop.getProperty("pageLoad"));
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		
		// setting the page load time and implicit wait time
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoad));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		
		// launching the browser
		driver.get(url);
		
		// maximizing the window
		driver.manage().window().maximize();
	}
	
	// method that runs before the test suite
	@BeforeSuite
	public void ExtentReportSetup() {
		String reportPath = System.getProperty("user.dir") + "/extentreport/";
		String fileName = "Report.html";
		reporter = new ExtentSparkReporter(reportPath + fileName);
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}
	
	// closing the extent report report
	@AfterSuite
	public void CloseReportSetup() {
		extent.flush();
	}
}
