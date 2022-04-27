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

public class SearchTest extends TestBase {


	@DataProvider
	public Object[][] searchDataProvider() {

		Sheet workSheet;
		try {
			FileInputStream ExcelFile = new FileInputStream("src/test/resources/appSmartTestData.xlsx");
			Workbook workBook = WorkbookFactory.create(ExcelFile);
			workSheet = workBook.getSheet("SearchTestData");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String[][] dataArray = UtilityMethods.getDataFromExcel(workSheet);

		return dataArray;
	}


	@Test(dataProvider = "searchDataProvider")
	public void test1(String fullSearch, String partialSearch) {
		InitialPage initialPage = new InitialPage();
		HomePage homePage = new HomePage();

		initialPage.clickCompany("Enjoy Pizza Bremen");
		homePage.searchItem(fullSearch);
		homePage.verifyExactSearch(fullSearch);
	}

	@Test(dataProvider = "searchDataProvider")
	public void test2(String fullSearch, String partialSearch) {
		InitialPage initialPage = new InitialPage();
		HomePage homePage = new HomePage();

		initialPage.clickCompany("Enjoy Pizza Bremen");
		homePage.searchItem(partialSearch);
		homePage.verifyPartialSearch(partialSearch);
	}


}
