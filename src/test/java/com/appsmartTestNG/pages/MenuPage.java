package com.appsmartTestNG.pages;

import com.appsmartTestNG.utils.UtilityMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MenuPage extends BasePage {

	@FindBy(css = "button[type='submit'].button-order.positive-action")
	private WebElement confirmBtn;

	@FindBy(css = "div#order-total div.total-price div.value")
	private WebElement totalPrice1;

	private List<Map<String, Object>> allOrders = new ArrayList<>();
	private Map<String, Object> singleOrder;

	private BigDecimal menuTotalPrice;


	//pizza i.e
	public void selectMenu(String menu) {
		String menuLocator = "//span[text()='" + menu + "']";
		UtilityMethods.waitClickability(By.xpath(menuLocator), 2);
		driver.findElement(By.xpath(menuLocator)).click();
	}

	//---"pizza salami" i.e---
	public void selectFoodItem(String name, String size) {
		//select pizza
		String menuNameLocator = "//div[@class='product-small-picture-container']//h5[text()='" + name + "']";
		UtilityMethods.waitClickability(By.xpath(menuNameLocator), 2);
		driver.findElement(By.xpath(menuNameLocator)).click();

		//select size
		String priceString = "";

		if (!size.equals("")) {
			String sizeLocator = "//div[@class='size-text'][contains(text(),'" + size + "')]";
			UtilityMethods.waitClickability(By.xpath(sizeLocator), 2);
			driver.findElement(By.xpath(sizeLocator)).click();

			//get the price
			menuTotalPrice = new BigDecimal("0");
			priceString = driver.findElement(By.xpath(sizeLocator + "/..//div[@class='price']")).getText();

			//assign all values and put to the map
			setValues(1, name, priceString);
			singleOrder.put("size", size);
		} else if(size.isEmpty()) {
			//get the price
			menuTotalPrice = new BigDecimal("0");
			priceString = driver.findElement(By.xpath(menuNameLocator + "/../../../..//div[@data-testid='product-price-on-btn']")).getText();

			//assign all values and put to the map
			setValues(1, name, priceString);
		}
	}

	//---"Extrazutaten 1 (0)" i.e---
	public void selectExtraZutaten(String zutatenHeader) {
		//ingredient main menu name click
		String zutatenHeaderLocator = "//div[@data-testid='extra-group']//*[contains(text(), '" + zutatenHeader + "')]";
		UtilityMethods.waitClickability(By.xpath(zutatenHeaderLocator), 2);
		driver.findElement(By.xpath(zutatenHeaderLocator)).click();
	}

	//---add ananas i.e---
	public void addIngredient(String ingredientName, int quantity) {
		//click on + buttuon
		String addIngredientBtn = "//*[text()='" + ingredientName + "']";
		for (int i = 0; i < quantity; i++) {
			UtilityMethods.waitClickability(By.xpath(addIngredientBtn), 2);
			driver.findElement(By.xpath(addIngredientBtn)).click();
		}

		//single ingredient price
		String ingredientPriceLocator = addIngredientBtn + "/..//div[contains(@class,'price')]";
		String priceString = driver.findElement(By.xpath(ingredientPriceLocator)).getText();

		//assign all values and put to the map
		setValues(quantity, ingredientName, priceString);
	}


	//---remove ananas i.e---
	public void removeIngredient(String ingredientName, int quantity1) {
		//click on - buttuon
		String removeIngredientBtn = "(//*[text()='" + ingredientName + "']/../../div/i)[1]";
		for (int i = 0; i < quantity1; i++) {
			UtilityMethods.waitClickability(By.xpath(removeIngredientBtn), 2);
			driver.findElement(By.xpath(removeIngredientBtn)).click();
		}

		//update the values
		for (Map<String, Object> individualMenuList : allOrders) {
			if (individualMenuList.get("name").equals(ingredientName)) {

				individualMenuList.replace("quantity", ((BigDecimal) individualMenuList.get("quantity")).subtract(new BigDecimal(quantity1)));
				individualMenuList.replace("itemTotalPrice", ((BigDecimal) individualMenuList.get("quantity")).multiply(((BigDecimal) individualMenuList.get("itemSinglePrice"))));
//				individualMenuList.replace("menuTotalPrice", ((BigDecimal) individualMenuList.get("menuTotalPrice")).subtract(new BigDecimal(quantity1).multiply((BigDecimal) individualMenuList.get("itemSinglePrice"))));
//				individualMenuList.replace("quantity",  new BigDecimal(String.valueOf(individualMenuList.get("quantity"))).subtract(new BigDecimal(quantity1)));
//				individualMenuList.replace("itemTotalPrice", new BigDecimal(String.valueOf(individualMenuList.get("quantity"))).multiply( new BigDecimal(String.valueOf(individualMenuList.get("singlePrice")))));
//				individualMenuList.replace("menuTotalPrice", new BigDecimal(String.valueOf(individualMenuList.get("menuTotalPrice"))).subtract(new BigDecimal(quantity1).multiply((BigDecimal) individualMenuList.get("singlePrice"))));
				this.menuTotalPrice = this.menuTotalPrice.subtract(new BigDecimal(quantity1).multiply((BigDecimal) individualMenuList.get("itemSinglePrice")));
			}
		}
	}

	//set all the values
	public void setValues(int quantity, String name, String singlePrice) {
		singleOrder = new LinkedHashMap<>();

		//update the value
		BigDecimal quantity1 = new BigDecimal(quantity);
		BigDecimal itemSinglePrice = priceConverter(singlePrice);
		BigDecimal itemTotalPrice = itemSinglePrice.multiply(quantity1);
		this.menuTotalPrice = this.menuTotalPrice.add(itemTotalPrice);

		//add to map
		singleOrder.put("name", name);
		singleOrder.put("quantity", new BigDecimal(quantity));
		singleOrder.put("itemSinglePrice", itemSinglePrice);
		singleOrder.put("itemTotalPrice", itemTotalPrice);

		//add map to list
		allOrders.add(singleOrder);
	}

	//convert the price from String into double
	public BigDecimal priceConverter(String priceString) {
		priceString = priceString.replaceAll("[^\\d,]", "");
		priceString = priceString.replace(",", ".");
		System.out.println(Double.parseDouble(priceString));
		return new BigDecimal(priceString);
	}

	//get allOrder list
	public List<Map<String, Object>> getAllOrders() {
		BigDecimal allTotalWithoutDiscount = new BigDecimal("0");
		singleOrder = new LinkedHashMap<>();
		List<String> prices = new ArrayList<>();

		for (int i = 0; i < allOrders.size(); i++) {
			if (allOrders.get(i).get("itemTotalPrice") != null) {
//				allTotalWithoutDiscount = allTotalWithoutDiscount.add(new BigDecimal((String)allOrders.get(i).get("itemTotalPrice")));
				allTotalWithoutDiscount = allTotalWithoutDiscount.add((BigDecimal)(allOrders.get(i).get("itemTotalPrice")));
			}
		}

		singleOrder.put("allTotalWithoutDiscount", allTotalWithoutDiscount);
		singleOrder.put("allTotalWithDiscount", allTotalWithoutDiscount.subtract(allTotalWithoutDiscount.multiply(new BigDecimal("0.1"))));
		allOrders.add(singleOrder);

		return allOrders;
	}

	//click confirm
	public void clickConfirm(){
		UtilityMethods.waitClickability(confirmBtn,2);
		confirmBtn.click();
	}

	 public String getTotalPriceFromShoppingCart(){
		 String price = this.totalPrice1.getText();
		 try {
			 price = price.replace("€", "").trim();
		 } catch (Exception e) {
		 	e.printStackTrace();
		 }

		 System.out.println(price);
		 return price;
	 }




}
