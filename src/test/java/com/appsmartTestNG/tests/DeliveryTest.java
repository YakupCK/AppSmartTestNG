package com.appsmartTestNG.tests;

import com.appsmartTestNG.pages.HomePage;
import com.appsmartTestNG.pages.InitialPage;
import com.appsmartTestNG.utils.UtilityMethods;
import com.aventstack.extentreports.ExtentTest;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;

public class DeliveryTest extends TestBase {


	@DataProvider
	public Object[][] deliveryDataProvider() {

		Sheet workSheet;
		try {
			FileInputStream ExcelFile = new FileInputStream("src/test/resources/appSmartTestData.xlsx");
			Workbook workBook = WorkbookFactory.create(ExcelFile);
			workSheet = workBook.getSheet("DeliveryTestData");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String[][] dataArray = UtilityMethods.getDataFromExcel(workSheet);
		return dataArray;
	}


	@Test(dataProvider = "deliveryDataProvider")
	public void verifyValidAddress(String validAddress, String invalidAddress) {

		InitialPage initialPage = new InitialPage();
		initialPage.searchAddress(validAddress);
		initialPage.ifAddressErrorNotDisplayed();

	}


	@Test(dataProvider = "deliveryDataProvider")
	public void verifyInvalidAddress(String validAddress, String invalidAddress) {

		InitialPage initialPage = new InitialPage();
		initialPage.searchAddress(invalidAddress);
		initialPage.ifAddressErrorDisplayed();

	}







}
