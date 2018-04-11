/**
 * this generic test for cart regression that will pull tests from cartRegression tab from
 * datasheet.xlsx. 
*/
package com.generic.tests.Cart;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.Cart;
import com.generic.page.PDP;
import com.generic.page.Registration;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

public class Base_cart extends SelTestCase {

	private static LinkedHashMap<String, Object> invintory;
	private static LinkedHashMap<String, Object> users;

	// user types
	public static final String guestUser = "guest";
	public static final String loggedInUser = "loggedin";
	// used sheet in test
	public static final String testDataSheet = SheetVariables.cart2Sheet;
	private static ThreadLocal<String> products = new ThreadLocal<String>();
	private static XmlTest testObject;
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	LinkedHashMap<String, Object> productDetails;

	@BeforeClass
	public static void initialSetUp(XmlTest test) throws Exception {
		testCaseRepotId = SheetVariables.cartCaseId;
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		invintory = Common.readLocalInventory();
		users = Common.readUsers();
	}

	@DataProvider(name = "Carts", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency mentainance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "Carts")
	public void CartBaseTest(String caseId, String runTest, String desc, String proprties, String products,
			String email, String newQty, String promotion, String OrderSubtotal, String OrderTotal, String ValidationMSG)
			throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("cart_" + getBrowserName()));
		setTestCaseReportName("cart Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.CARTDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- "), newQty, promotion, OrderTotal));
		
		String CaseMail = "";
		LinkedHashMap<String, Object> userdetails = null; 
		String url = PagesURLs.getShoppingCartPage();
		if (!email.equals(""))
		{
			userdetails = (LinkedHashMap<String, Object>) users.get(email);
			CaseMail = (String) userdetails.get(Registration.keys.email);
			Testlogs.get().debug("Mail will be used is: " + CaseMail);
		}
		try {

			if (proprties.contains("Loggedin"))
			{
				for (String product : products.split("\n"))
					prepareCartLoggedInUser(userdetails, product);
			}
			else
				for (String product : products.split("\n"))
					prepareCartNotLoggedInUser(product);
			
			getDriver().get(url);
			
			// Excluded from Bronze package
			if (proprties.contains("cart UI")) {
				productDetails = (LinkedHashMap<String, Object>) invintory.get(products);
				logs.debug("checking the cart UI");
				sassert().assertTrue(Cart.checkItemImage(), "<font color=#f442cb>NOT All product images are ok</font>");
				sassert().assertTrue(Cart.checkProductLink((String) productDetails.get(PDP.keys.scene7Image)),
						"<font color=#f442cb>Product link is Not ok</font>");
			}

			// flow to support coupon validation
			if (!"".equals(promotion)) {
				Cart.applyCoupon(promotion);
				ReportUtil.takeScreenShot(getDriver());
				String validationMessage = Cart.getGlobalMessage();
				if (!ValidationMSG.equals(""))
					sassert().assertTrue(ValidationMSG.contains(validationMessage),
							"<font color=#f442cb>coupon messgae is not same:  " + ValidationMSG + "</font>");
				ReportUtil.takeScreenShot(getDriver());
			}

			// quantity validation- excluded from bronze
			if (!newQty.equals("")) {// just to make sure that code will not flow down in this
				productDetails = (LinkedHashMap<String, Object>) invintory.get(products);
				// verifying that no new lines being added to cart
				double subtotal = Double.parseDouble(Cart.getOrderSubTotal().replace("$", ""));
				String numberOfItems = Cart.getNumberOfproducts();
				String productQty = (String) productDetails.get(PDP.keys.qty);
				logs.debug("Current product qty is: " + productQty);
				Cart.selectQty(newQty);
				if (!newQty.equals("0") && !Cart.isCartEmpty()) {
					double subtotalAfter = Double.parseDouble(Cart.getOrderSubTotal().replace("$", ""));
					String numberOfItemsAfterUpdate = Cart.getNumberOfproducts();
					String validationMessage = Cart.getCartMsg();
					String productQtyAfter = newQty;
					logs.debug("New product qty is: " + productQtyAfter);
					double expectedSubtotal = subtotal * Double.parseDouble(productQtyAfter)
							/ Double.parseDouble(productQty);

					logs.debug("<font color=#f442cb>update MSG:" + validationMessage + "</font>");
					sassert().assertTrue(validationMessage.contains(ValidationMSG),
							"<font color=#f442cb>QTY it not updated correctely, MSG:" + validationMessage + "</font>");

					logs.debug("<font color=#f442cb>number of items before: " + numberOfItems + " after "
							+ numberOfItemsAfterUpdate + "</font>");
					sassert().assertTrue(numberOfItemsAfterUpdate.equals(numberOfItems),
							"<font color=#f442cb>number of items before: " + numberOfItems
									+ " in cart is not same after " + numberOfItemsAfterUpdate + "</font>");

					logs.debug("<font color=#f442cb>subtotal before " + subtotal + "subtotal after " + subtotalAfter
							+ "</font>");
					sassert().assertTrue(expectedSubtotal == subtotalAfter, "<font color=#f442cb>subtotal before "
							+ subtotal + "subtotal after " + subtotalAfter + "</font>");
				} // Quantity check
			}//QTY !=0

			if (proprties.contains("Verify unit Price")) {
				String ProductUnitPrice = Cart.getProductUnitPrice();
				productDetails = (LinkedHashMap<String, Object>) invintory.get(products);
				String expectedProductUnitPrice = productDetails.get(PDP.keys.price).toString().split("was")[0];
				String ErrorMsg = "<font color=#f442cb>expected unit price is: "
						+ expectedProductUnitPrice.split("was")[0] + "actual unit price: " + ProductUnitPrice + "</font>";
				sassert().assertTrue(ProductUnitPrice.trim().contains(expectedProductUnitPrice.trim()), ErrorMsg);
			}//verify unit price 
			
			if (proprties.contains("Verify subtotal")) {
				//the case that to get unit price and multiply it by the qty from product details and,
				//then compare it with product subtotal and with order subtotal since we have just one product
				logs.debug("test" + Cart.getProductUnitPrice().replace("$", "T").trim());
				double calculatedProductSubtotal = Double.parseDouble((String) productDetails.get(PDP.keys.qty))
						* Double.parseDouble(Cart.getProductUnitPrice().replace("$", "").trim());
				
				double siteProductSubtotal = Double.parseDouble(Cart.getProductSubtotal().replace("$", "").trim());
				double siteOrdersubtotal  = Double.parseDouble(Cart.getOrderSubTotal().replace("$", "").trim());
				double SheetOrderSubtotal = Double.parseDouble(OrderSubtotal.replace("$", "").trim());
				
				String subtotalMSG = "<font color=#f442cb>Subtotal from sheet: " + SheetOrderSubtotal +
						"<br>calculated subtotal: "+ calculatedProductSubtotal+
						"<br>site product subtotal: " + siteProductSubtotal+
						"<br>site order subtotal: "+ siteOrdersubtotal+ "</font>" ; 
				
				logs.debug(subtotalMSG);
				sassert().assertTrue(calculatedProductSubtotal == SheetOrderSubtotal ||
						siteOrdersubtotal ==SheetOrderSubtotal ||
						siteProductSubtotal == SheetOrderSubtotal, "FAILED: the subtotals should be matched: <br>"+subtotalMSG);
			}//verify subtotal
  					
			if (proprties.contains("Verify total")) {
				double siteOrderTotal = Double.parseDouble(Cart.getOrderTotal().replace("$", "").trim());
				double SheetOrderTotal = Double.parseDouble(OrderTotal.replace("$", "").trim());
				String orderTotalMsg = "<font color=#f442cb>siteOrderTotal: " + siteOrderTotal +
						"<br>SheetorderTotal: "+ SheetOrderTotal+"</font>" ; 
				
				logs.debug(orderTotalMsg);
				sassert().assertTrue(siteOrderTotal == SheetOrderTotal , "FAILED: the totals should be matched: <br>"+orderTotalMsg);
			}//verify order total
			
			ReportUtil.takeScreenShot(getDriver());
			
			if (proprties.contains("remove Promotion")) {
				Cart.removeCoupon();
				productDetails = (LinkedHashMap<String, Object>) invintory.get(products);
			Double expectedProductSubtotal = Double.parseDouble(productDetails.get(PDP.keys.price).toString().split("was")[0].replace("$", "").trim());
			double siteOrdersubtotal  = Double.parseDouble(Cart.getOrderSubTotal().replace("$", "").trim());
			logs.debug("Order subtotal: " + siteOrdersubtotal);
			double siteOrderTotal = Double.parseDouble(Cart.getOrderTotal().replace("$", "").trim());
			logs.debug("Order total: " + siteOrderTotal);
			sassert().assertTrue(expectedProductSubtotal == siteOrdersubtotal , "FAILED: voucher code is not removed correctly: <br>");
			ReportUtil.takeScreenShot(getDriver());
			}//Remove Promotion.
			
			if (proprties.contains("click checkout")) {
				Cart.clickCheckout();
			}

			ReportUtil.takeScreenShot(getDriver());
			
			if (proprties.contains("Loggedin")) {
				// navigate back to cart
				getDriver().get(url);
				Cart.removeAllItemsFromCart();
			}
			ReportUtil.takeScreenShot(getDriver());
			sassert().assertAll();
			Common.testPass();
		} catch (Throwable t) {
			setTestCaseDescription(getTestCaseDescription());
			Testlogs.get().debug(MessageFormat.format(LoggingMsg.DEBUGGING_TEXT, t.getMessage()));
			t.printStackTrace();
			String temp = getTestCaseReportName();
			Common.testFail(t, temp);
			ReportUtil.takeScreenShot(getDriver());
			Assert.assertTrue(false, t.getMessage());
		} // catch

	}// test

	@SuppressWarnings("unchecked")
	public void prepareCartNotLoggedInUser(String product) throws Exception {
		logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
		productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
		PDP.addProductsToCart((String) productDetails.get(PDP.keys.url),
				(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
				(String) productDetails.get(PDP.keys.qty));
	}

	public void prepareCartLoggedInUser(LinkedHashMap<String, Object> userdetails, String product) throws Exception {
		logs.debug(MessageFormat.format(LoggingMsg.SEL_TEXT, userdetails));
		logs.debug((String) userdetails.get(Registration.keys.email));
		logs.debug((String) userdetails.get(Registration.keys.password));
		SignIn.logIn((String) userdetails.get(Registration.keys.email),
				(String) userdetails.get(Registration.keys.password));

		prepareCartNotLoggedInUser(product);

	}

}
