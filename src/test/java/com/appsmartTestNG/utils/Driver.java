package com.appsmartTestNG.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
	private Driver() {};

	private static WebDriver driver;

	//returns regular driver object
	public static WebDriver getDriver() {
		if (driver == null) {
			String browser = PropertyReader.getProperty("browser");
			switch (browser) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
					break;
				case "ie":
					WebDriverManager.iedriver().setup();
					driver = new InternetExplorerDriver();
					break;
				case "edge":
					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver();
					break;
				case "safari":
					WebDriverManager.getInstance(SafariDriver.class).setup();
					driver = new SafariDriver();
					break;
			}
		}
		return driver;
	}


	//quit and null the driver
	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}


}