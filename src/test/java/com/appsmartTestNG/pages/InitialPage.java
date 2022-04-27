package com.appsmartTestNG.pages;

import com.appsmartTestNG.utils.PropertyReader;
import com.appsmartTestNG.utils.UtilityMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


public class InitialPage extends BasePage {

	@FindBy(id = "react-select-2-input")
	private WebElement searchBox;

	@FindBy(id = "react-select-2-option-0")
	private WebElement firstOption;

	@FindBy(css = "p.error")
	private WebElement errorMessage;

	@FindBy(xpath = "(//button)[2]")
	private WebElement enjoyPizzaBremen;

	@FindBy(xpath = "//button//p[text()='Back']")
	private WebElement backButton;

	@FindBy(xpath = "//div[text()='Browse the menu']/..")
	private WebElement browseTheMenuBtn;

	//navigate to home page
	public void goInitialPage() {
//		driver.get(PropertyReader.getProperty("url"));
	}

	//click enjoy pizza bremen
	public void clickCompany(String company) {
		switch (company) {
			case "Enjoy Pizza Bremen":
				driver.findElement(By.xpath("(//button)[2]")).click();
				break;
			case "Enjoy Pizza Delmenhorst":
				driver.findElement(By.xpath("(//button)[3]")).click();
				break;
		}

//      if any unexpected page opens
//		try {
//			UtilityMethods.waitClickability(backButton,2);
//			backButton.click();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//      if night - company is closed
//		try {
//			UtilityMethods.waitClickability(browseTheMenuBtn, 2);
//			browseTheMenuBtn.click();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	//search for an address
	public void searchAddress(String address) {
		searchBox.sendKeys(address);
		UtilityMethods.waitForVisibility(firstOption, 3);
		firstOption.click();
	}

	public void ifAddressErrorDisplayed() {
		UtilityMethods.waitForVisibility(errorMessage, 2);
		Assert.assertTrue(errorMessage.isDisplayed());
	}

	public void ifAddressErrorNotDisplayed() {
		UtilityMethods.waitForVisibility(errorMessage, 2);
		Assert.assertFalse(errorMessage.isDisplayed());
	}

}
