///**
// * this generic test for cart regression that will pull tests from cartRegression tab from
// * datasheet.xlsx. 
//*/
//package com.generic.tests.Cart;
//
//import java.text.MessageFormat;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import org.testng.xml.XmlTest;
//
//import com.generic.page.Cart;
//import com.generic.page.PDP;
//import com.generic.page.SignIn;
//import com.generic.setup.Common;
//import com.generic.setup.LoggingMsg;
//import com.generic.setup.SelTestCase;
//import com.generic.setup.SheetVariables;
//import com.generic.util.ReportUtil;
//import com.generic.util.SASLogger;
//import com.generic.util.dataProviderUtils;
//
//public class Base_cart1 extends SelTestCase {
//
//	private static LinkedHashMap<String, Object> invintory;
//	private static LinkedHashMap<String, Object> users;
//
//	// user types
//	public static final String guestUser = "guest";
//	public static final String loggedInUser = "loggedin";
//
//	// used sheet in test
//	public static final String testDataSheet = SheetVariables.cartSheet;
//
//	private static XmlTest testObject;
//	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
//	private String email;
//	LinkedHashMap<String, Object> productDetails;
//
//	@BeforeClass
//	public static void initialSetUp(XmlTest test) throws Exception {
//		testCaseRepotId = SheetVariables.cartCaseId;
//		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
//		testObject = test;
//		invintory = Common.readLocalInventory();
//		users = Common.readUsers();
//	}
//
//	@DataProvider(name = "Carts", parallel = true)
//	public static Object[][] loadTestData() throws Exception {
//		// concurrency mentainance on sheet reading
//		getBrowserWait(testObject.getParameter("browserName"));
//		dataProviderUtils TDP = dataProviderUtils.getInstance();
//		Object[][] data = TDP.getData(testDataSheet);
//		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
//		return data;
//	}
//
//	@Test(dataProvider = "Carts")
//	public void CartBaseTest(String caseId, String runTest, String desc, String proprties, String products,
//			String email, String newQTY, String coupon, String ValidationMsg) throws Exception {
//		// Important to add this for logging/reporting
//		Testlogs.set(new SASLogger("cart_" + getBrowserName()));
//		setTestCaseReportName("cart Case");
//		logCaseDetailds(MessageFormat.format(LoggingMsg.CARTDESC, testDataSheet + "." + caseId,
//				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- "), coupon, newQTY));
//		this.email = email;
//	//	this.caseIndexInDatasheet = getDatatable().getCellRowNum(testDataSheet, CheckOut.keys.caseId, caseId);
//		try {
//
//			if (proprties.contains("Loggedin"))
//				for (String product : products.split("\n"))
//					prepareCartLoggedInUser(this.email, product);
//			else
//				for (String product : products.split("\n"))
//					prepareCartNotLoggedInUser(product);
//
//			if (proprties.contains("cart UI") ) {
//				logs.debug("checking the cart UI");
//				sassert().assertTrue(Cart.checkItemImage(), "<font color=#f442cb>NOT All product images are ok</font>");
//				sassert().assertTrue(Cart.checkProductLink((String) productDetails.get(PDP.keys.url)),
//						"<font color=#f442cb>Product link is Not ok</font>");
//			}
//
//			// flow to support coupon validation
//			if (!"".equals(coupon)) {
//				ReportUtil.takeScreenShot(getDriver());
//				Cart.applyCoupon(coupon);
//				ReportUtil.takeScreenShot(getDriver());
//				String validationMessage = Cart.validateCoupon();
//				if (!ValidationMsg.equals(""))
//					sassert().assertTrue(ValidationMsg.contains(validationMessage), "<font color=#f442cb>coupon messgae is not same:  "+ValidationMsg+"</font>");
//				ReportUtil.takeScreenShot(getDriver());
//			}
//			
//			//quantity validation
//			if (!newQTY.equals("")) {
//				// verifying that no new lines being added to cart
//				double subtotal = Double.parseDouble(Cart.getOrderSubTotal().replace("£", ""));
//				String numberOfItems = Cart.getNumberOfproducts().split("item")[0].trim();
//				String productQty = Cart.getProductQty(getBrowserName(), 0);
//		//		Cart.updateQuantityValue(getBrowserName(), "0", newQTY);
//				if (!newQTY.equals("0") && !Cart.isCartEmpty()) {
//					double subtotalAfter = Double.parseDouble(Cart.getOrderSubTotal().replace("£", ""));
//					String numberOfItemsAfterUpdate = Cart.getNumberOfproducts().split("item")[0].trim();
//					String validationMessage = Cart.getCartMsg();
//					String productQtyAfter = Cart.getProductQty(getBrowserName(), 0);
//
//					double expectedSubtotal = subtotal * Double.parseDouble(productQtyAfter)
//							/ Double.parseDouble(productQty);
//					
//					logs.debug("<font color=#f442cb>update MSG:" + validationMessage + "</font>");
//					sassert().assertTrue(validationMessage.contains(ValidationMsg),
//							"<font color=#f442cb>QTY it not updated correctely, MSG:" + validationMessage + "</font>");
//					
//					logs.debug("<font color=#f442cb>number of items before: " + numberOfItems
//							+ " after " + numberOfItemsAfterUpdate + "</font>");
//					sassert().assertTrue(numberOfItemsAfterUpdate.contains(numberOfItems),
//							"<font color=#f442cb>number of items before: " + numberOfItems
//									+ " in cart is not same after " + numberOfItemsAfterUpdate + "</font>");
//					
//					logs.debug("<font color=#f442cb>product new QTY"+newQTY+"appearing in cart " + productQtyAfter+ "</font>");
//					sassert().assertTrue(productQtyAfter.equals(newQTY),
//							"<font color=#f442cb>product new QTY is not appearing in cart " + productQtyAfter
//									+ "</font>");
//					
//					logs.debug("<font color=#f442cb>subtotal before "+ subtotal + "subtotal after " + subtotalAfter + "</font>");
//					sassert().assertTrue(expectedSubtotal == subtotalAfter, "<font color=#f442cb>subtotal before "
//							+ subtotal + "subtotal after " + subtotalAfter + "</font>");
//				}//Quantity check 
//				
//				if (proprties.contains("click checkout"))
//				{
//					Cart.clickCheckout();
//					//TODO: verify if you are in checkout page 
//				}else
//				{
//					Cart.clickContinueShoping();
//					//TODO: verify if you are in guest checkout page 
//				}
//				
//				if (proprties.contains("loggedin"))
//				{
//					//navigate back to cart
//					getDriver().get("https://hybrisdemo.conexus.co.uk:9002/yacceleratorstorefront/en/cart");
//					Cart.removeAllItemsFromCart();
//				}
//				if (proprties.contains("remove coupon"))
//					Cart.removeCoupon();
//			}
//
//			ReportUtil.takeScreenShot(getDriver());
//
//			sassert().assertAll();
//			Common.testPass();
//		} catch (Throwable t) {
//			setTestCaseDescription(getTestCaseDescription());
//			Testlogs.get().debug(MessageFormat.format(LoggingMsg.DEBUGGING_TEXT, t.getMessage()));
//			t.printStackTrace();
//			String temp = getTestCaseReportName();
//			Common.testFail(t, temp);
//			ReportUtil.takeScreenShot(getDriver());
//			Assert.assertTrue(false, t.getMessage());
//		} // catch
//
//	}// test
//
//	public void prepareCartNotLoggedInUser(String product) throws Exception {
//		logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
//		productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
//		PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url), (String) productDetails.get(PDP.keys.color),
//				(String) productDetails.get(PDP.keys.size), (String) productDetails.get(PDP.keys.qty));
//	}
//
//	public void prepareCartLoggedInUser(String user, String product) throws Exception {
//		logs.debug(MessageFormat.format(LoggingMsg.SEL_TEXT, user));
//		LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>) users.get(user);
//		logs.debug((String) userDetails.get("password"));
//		logs.debug((String) userDetails.get("mail"));
//		SignIn.logIn(getSubMailAccount((String) userDetails.get("mail")), (String) userDetails.get("password"));
//
//		logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
//		productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
//		PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url), (String) productDetails.get(PDP.keys.color),
//				(String) productDetails.get(PDP.keys.size), (String) productDetails.get(PDP.keys.qty));
//
//	}
//
//	public void prepareCartNotLoggedInUser() {
//		// TODO Auto-generated method stub
//
//	}
//
//	public void prepareCartLoggedInUser() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
