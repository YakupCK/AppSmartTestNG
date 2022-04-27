package com.appsmartTestNG.tests;

import com.appsmartTestNG.utils.Driver;
import com.appsmartTestNG.utils.PropertyReader;
import com.appsmartTestNG.utils.UtilityMethods;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestBase {

	public WebDriver driver;


	@BeforeMethod
	public void setUp() {

		driver = Driver.getDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(PropertyReader.getProperty("url"));

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		Driver.quitDriver();
	}


}
