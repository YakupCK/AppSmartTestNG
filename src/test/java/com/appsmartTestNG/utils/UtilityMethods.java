package com.appsmartTestNG.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class UtilityMethods {

	//driver object initialized by Hooks
	private static WebDriver driver = Driver.getDriver();

	//wait for an element to be clickable (with web element)
	public static void waitClickability(WebElement element, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//wait for an element to be clickable (with By locator)
	public static void waitClickability(By locator, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//wait till URL contains a specific text
	public static void waitForURLContains(String urlPart, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.urlContains(urlPart));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//wait for visibility of a web element
	public static void waitForVisibility(WebElement element, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//wait for visibility of a web element
	public static void waitForVisibility(By by, int timeOut) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//static wait - Thread.sleep()
	public static void wait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//wait till a new window gets opened
	public static void waitForNewWindow() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 4);
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//switch to another window by passing index number
	public static void switchToWindow(int index) {
		try {
			waitForNewWindow();
			Set<String> windowHandles = driver.getWindowHandles();
			ArrayList<String> allTabs = new ArrayList<>(windowHandles);
			driver.switchTo().window(allTabs.get(index));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//wait for a web element till has a specific text
	public static void waitForText(WebElement element, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 6);
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//switch to iframe
	public static void switchToFrame(WebElement frame) {
		WebDriverWait wait = new WebDriverWait(driver, 4);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
//		driver.switchTo().frame(frame);
	}

	//click on a web element using JSexecutor
	public static void clickWithJSExe(WebElement element) {
		waitClickability(element, 3);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	//attempt to click on a web element a couple of times
	public static void clickManyTimes(WebElement element) {
		waitClickability(element, 3);
		for (int i = 0; i < 3; i++) {
			try {
				element.click();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		}
	}

	//open a new tab using JSexecutor
	public static void openNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open();");
	}

	//scroll into an element
	public static void scrollToElement(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//scroll into an element
	public static void scrollToElement(By by) {
		try {
			WebElement element = driver.findElement(by);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//take screenshot for reporting
	public static String getScreenshot(String name) throws IOException {
		String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);
		String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
		File finalDestination = new File(target);
		FileUtils.copyFile(source, finalDestination);
		return target;
	}

	//---------------------------------------------------
	public static String[][] getDataFromExcel(Sheet workSheet) {
		Cell cell;
		int rowCount = workSheet.getLastRowNum()+1;
		int columnCount = workSheet.getRow(0).getLastCellNum();

		String[][] data = new String[rowCount-1][columnCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				String value = getCellData(workSheet, i, j);
				data[i - 1][j] = value;
			}
		}
		return data;

	}

	public static String getCellData(Sheet workSheet, int rowNum, int colNum) {
		Cell cell;

		try {
			cell = workSheet.getRow(rowNum).getCell(colNum);
				return cell.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
