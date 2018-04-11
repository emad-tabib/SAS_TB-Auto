package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.generic.selector.CartSelectors;
import com.generic.selector.CheckOutSelectors;
import com.generic.setup.SelTestCase;
import com.generic.setup.ExceptionMsg;
import com.generic.setup.LoggingMsg;
import com.generic.util.SelectorUtil;

public class Cart extends SelTestCase {

	public static class keys {

		public static final String invalidCoupon = "invalid";

	}
	
	public static void clickCheckout() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_CART_BUTTON, "checkout"));
		subStrArr.add(CartSelectors.checkoutBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void clickContinueShoping() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_CART_BUTTON, "continue shopping"));
		subStrArr.add(CartSelectors.continueShopping);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static String getNumberOfproducts() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.numberOfProducts);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, SelectorUtil.numberOfFoundElements.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.numberOfFoundElements.get();
	}
	
	public static String getProductQty() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.selectedQty);
		valuesArr.add("getCurrentValue");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
	
	public static String checkProductsExsist() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.numberOfProducts);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.NUMBER_OF_PRODUCTS, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
	
	public static String getOrderTotal() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.cartOrderTotal);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ORDER_TOTAL, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getOrderSubTotal() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.OrderSubTotal);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ORDER_SUBTOTAL, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();

	}

	public static String getOrderTax() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.cartOrderTax);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug("Order tax: " + SelectorUtil.textValue.get());
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();

	}

	public static void applyCoupon(String coupon) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		if (!"".equals(coupon)) {
			logs.debug(MessageFormat.format(LoggingMsg.APPLYING_COUPON, "Applying", coupon));
			writeCoupon(coupon);
			clickApplycoupon();
		} else {
			logs.debug(MessageFormat.format(LoggingMsg.APPLYING_COUPON, "cannot apply", coupon));
		}
		getCurrentFunctionName(false);
	}
	
	public static void removeCoupon() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.removeCoupon);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	private static void clickApplycoupon() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.applyCouponButton);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	private static void writeCoupon(String coupon) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.couponField);
		valuesArr.add(coupon);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
	
	public static String validateCoupon() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.promoText);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		logs.debug(MessageFormat.format(LoggingMsg.COUPON_MSG, SelectorUtil.textValue.get()));
		return SelectorUtil.textValue.get();
	}
	
	public static String getGlobalMessage() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.globalAlert);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		logs.debug(MessageFormat.format(LoggingMsg.COUPON_MSG, SelectorUtil.textValue.get()));
		return SelectorUtil.textValue.get();
	}

	public static void selectQty(String newQty) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.qty);
		valuesArr.add(newQty);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
	
//	public static void updateQuantityValue(String browser, String lineOrder, String qty) throws Exception {
//		// Limited to edit first product qty
//		getCurrentFunctionName(true);
//		writeNewQunatity(browser, lineOrder, qty+",pressEnter");
//		getCurrentFunctionName(false);
//	}
//
//	private static void writeNewQunatity(String browser, String lineOrder, String qty) throws Exception {
//		getCurrentFunctionName(true);
//		List<String> subStrArr = new ArrayList<String>();
//		List<String> valuesArr = new ArrayList<String>();
//		if (browser.contains("mobile"))
//			subStrArr.add(CartSelectors.productQtyBoxMobile+lineOrder);
//		else
//			subStrArr.add(CartSelectors.productQtyBox+lineOrder);
//		valuesArr.add(qty);
//		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
//		getCurrentFunctionName(false);
//	}

	public static String getErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		try {
			subStrArr.add(CartSelectors.errorMessage);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		} catch (Exception e) {
			// to make sure the application is throwing the correct exception
			if (ExceptionMsg.noValidSelector.contains(e.getMessage()))
				throw new Exception(ExceptionMsg.noErrorMsg);
			else
				throw new Exception(e);
		}
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getCartMsg() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		try {
			subStrArr.add(CartSelectors.errorMessage);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		} catch (Exception e) {
			// to make sure the application is throwing the correct exception
			if (ExceptionMsg.noValidSelector.contains(e.getMessage()))
				throw new Exception(ExceptionMsg.noErrorMsg);
			else
				throw new Exception(e);
		}
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static boolean isCartEmpty() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.cartContent);
		valuesArr.add("");
		try {
			SelectorUtil.textValue.set("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		} catch (Exception e) {
			logs.debug(MessageFormat.format(LoggingMsg.EMPTY_CART_ERROR_MSG, e.getMessage()));
		}
		getCurrentFunctionName(false);
		return (SelectorUtil.textValue.get().contains("empty") ? true : false);
	}

	public static void removeAllItemsFromCart() throws Exception {
		getCurrentFunctionName(true);

		if (!isCartEmpty()) {
			int numberOfItems = Integer.parseInt(getNumberOfproducts());
			logs.debug(LoggingMsg.REMOVE_ALL_ITEMS_FROM_CART);
			for (int itemIndex = numberOfItems - 1; itemIndex >= 0; itemIndex--)
				removeItemFromCart(0); // keep always remove the first item
		} else {
			logs.debug(LoggingMsg.NO_ITEMS_TO_BE_REMOVED);
		}

		getCurrentFunctionName(false);
	}

	public static void removeItemFromCart(int itemIndex) throws Exception {
		getCurrentFunctionName(true);
	//	clickOnactionMenue(itemIndex);
		clickOnRemove(itemIndex);
		getCartMsg();
		getCurrentFunctionName(false);
	}

	private static void clickOnRemove(int itemIndex) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.removeItem);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	private static void clickOnactionMenue(int itemIndex) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.actionMenue + itemIndex);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static String getOrdarshipping() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.cartOrderShipping);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug("Order shipping: " + SelectorUtil.textValue.get());
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}
	
	public static boolean checkItemImage() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.itemImages);
		
		boolean isDisplayed = SelectorUtil.isDisplayed(subStrArr);
		logs.debug("images check result is " + isDisplayed);
		getCurrentFunctionName(false);
		return isDisplayed;
	}
	
	public static boolean checkProductLink(String PLink) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.itemLink);
		String attrValue =SelectorUtil.getAttr(subStrArr, "src");
		logs.debug("link check result is: " + attrValue);
		logs.debug("Expected link is: " + PLink);
		getCurrentFunctionName(false);
		return PLink.contains(attrValue);
	}

	public static String getProductUnitPrice() throws Exception{
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.unitPrice);
		valuesArr.add("");
		String salePrice = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		String unitPrice = SelectorUtil.textValue.get();
		logs.debug ( "unit price is: "+ unitPrice);
		salePrice = unitPrice.split("$")[(unitPrice.split("$").length)-1];
		logs.debug ( "sale price is: "+ salePrice);
		getCurrentFunctionName(false);
		return salePrice;
	}

	public static String getProductSubtotal() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.productSubtotal);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug ( "product subtotal is: "+SelectorUtil.textValue.get());
		String totals = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return totals;
	}

	public static String getOrderDiscount() throws Exception {
		getCurrentFunctionName(true);
		String totals = getTotals();
		getCurrentFunctionName(false);
		return totals.split("\n")[3].trim();

	}
	
	public static String getPromotionalDiscount() throws Exception {
		getCurrentFunctionName(true);
		String totals = getTotals();
		getCurrentFunctionName(false);
		return totals.split("\n")[5].trim();

	}
	
	public static String getTotals() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(CartSelectors.totals);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ORDER_TOTAL, SelectorUtil.textValue.get()));
		String totals = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return totals;
	}

}
