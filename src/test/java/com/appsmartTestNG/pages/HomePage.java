package com.appsmartTestNG.pages;

import com.appsmartTestNG.utils.UtilityMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class HomePage extends BasePage {


	@FindBy(css = "svg.search-input-field-icon")
	private WebElement searchIcon;

	@FindBy(css = "input.ant-input.ant-input-sm.search-input.active")
	private WebElement searchBox;

	@FindBy(css = "div.productNameWrap h5")
	private List<WebElement> itemsDisplayed;

	@FindBy(css = "div.product-empty-list-message")
	private WebElement noItemFoundMessage;

	@FindBy(css = "button[data-testid='basket-order-btn']")
	private WebElement orderNowBtn;


	public void searchItem(String item) {
		UtilityMethods.waitClickability(By.cssSelector("svg.search-input-field-icon"), 5);
		searchIcon.click();
		UtilityMethods.waitForVisibility(searchBox, 2);
		searchBox.sendKeys(item);
	}

	public void verifyExactSearch(String item) {

		if (itemsDisplayed.size() == 0) {
			Assert.assertTrue(noItemFoundMessage.isDisplayed());
		} else {
			for (WebElement items : itemsDisplayed) {
				UtilityMethods.waitForVisibility(items, 1);
				Assert.assertTrue(items.getText().equals(item));
			}
		}

	}

	public void verifyPartialSearch(String item) {
		if (itemsDisplayed.size() == 0) {
			Assert.assertTrue(noItemFoundMessage.isDisplayed());
		} else {
			System.out.println(itemsDisplayed.size());
			for (WebElement items : itemsDisplayed) {
				UtilityMethods.waitForVisibility(items, 1);
				Assert.assertTrue(items.getText().toLowerCase().contains(item.toLowerCase()));
			}
		}
	}

	//---click on order now btn
	public void clickOrderNow() {
		UtilityMethods.waitClickability(orderNowBtn, 2);
		orderNowBtn.click();
	}


}

