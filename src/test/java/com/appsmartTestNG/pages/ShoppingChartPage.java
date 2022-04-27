package com.appsmartTestNG.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ShoppingChartPage extends BasePage {


	@FindBy(css = "div#shopping-card div.order-price div.value")
	private WebElement allTotalWithoutDiscount;

	@FindBy(css = "div#shopping-card div.total-price div.value")
	private WebElement allTotalWithDiscount;

	@FindBy(css = "div#shopping-card div.discount div.title")
	private WebElement discountRate;


	public String getShoplistText(){
		String shoppingList = driver.findElement(By.cssSelector("div.shopping-list")).getText();
		return shoppingList;
	}


	public void verifyOrders(String actualList, List<Map<String,Object>> expectedList){

		System.out.println(actualList.replace(",","."));
		System.out.println("allTotalWithDiscount: " + allTotalWithDiscount.getText());
		System.out.println("allTotalWithoutDiscount: " + allTotalWithoutDiscount.getText());

		for (int i = 0; i < expectedList.size()-2; i++) {
			Assert.assertTrue(actualList.replace(",",".").contains(String.valueOf(expectedList.get(i).get("name"))));
			Assert.assertTrue(actualList.replace(",",".").contains(String.valueOf(expectedList.get(i).get("itemTotalPrice"))));
		}

		Assert.assertEquals( new BigDecimal(String.valueOf(expectedList.get(expectedList.size() - 1).get("allTotalWithoutDiscount"))).stripTrailingZeros(), new BigDecimal(allTotalWithoutDiscount.getText().replaceAll("[^\\d,]", "").replace(",", ".")).stripTrailingZeros());
		Assert.assertEquals( new BigDecimal(String.valueOf(expectedList.get(expectedList.size() - 1).get("allTotalWithDiscount"))).stripTrailingZeros(), new BigDecimal(allTotalWithDiscount.getText().replaceAll("[^\\d,]", "").replace(",", ".")).stripTrailingZeros());
	}








}
