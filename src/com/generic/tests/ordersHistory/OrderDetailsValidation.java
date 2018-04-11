package com.generic.tests.ordersHistory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import com.generic.page.SignIn;
import com.generic.setup.ActionDriver;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.OrderDetails;
import com.generic.page.OrderHistory;
import com.generic.page.PDP;
import com.generic.page.Registration;

public class OrderDetailsValidation extends SelTestCase {
	
	private static LinkedHashMap<String, Object> addresses = null;
	private static LinkedHashMap<String, Object> invintory = null;
	private static LinkedHashMap<String, Object> paymentCards = null;
	private static  LinkedHashMap<String, Object> users =null ;
	LinkedHashMap<String, Object> productDetails = null;
	// used sheet in test
	public static final String testDataSheet = SheetVariables.orderDetailsSheet;
	private int caseIndexInDatasheet;

	private String email;
	private String orderTotal;
	private String orderSubtotal;
	private String orderTax;
	private String orderShipping;
	private String orderId;
	private String billingAddrerss;
	private String shippingAddrerss;
	private String itemTotalPrice;
	
	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger("Order Details Setup"));
		testObject = test;
		addresses = Common.readAddresses();
		invintory = Common.readLocalInventory();
		paymentCards = Common.readPaymentcards();
		users = Common.readUsers();
	}

	@DataProvider(name = "ordersDetails", parallel = true)
	// concurrency maintenance on sheet reading
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "ordersDetails")
	public void verifyOrdeDetails(String caseId, String runTest, String desc, String email,String products, String shippingMethod, String payment, String shippingAddress, String billingAddress, String orderNumToBeClicked) throws Exception {

		Testlogs.set(new SASLogger("ordersDetails" + getBrowserName()));
		setTestCaseReportName("ordersDetails Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));
		this.email = getSubMailAccount(email);
		caseIndexInDatasheet = getDatatable().getCellRowNum(testDataSheet, OrderDetails.keys.caseId, caseId);
		String url = PagesURLs.getOrderHistoryPage();
		try {

			LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.get(email);
			Testlogs.get().debug(this.email);
			Testlogs.get().debug((String) userdetails.get(Registration.keys.password) );
			SignIn.logIn(this.email, (String) userdetails.get(Registration.keys.password));
			
			
			for (String product : products.split("\n")) {
				Testlogs.get().debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
				productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
				PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url),
						(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
						(String) productDetails.get(PDP.keys.qty));
			}

			Cart.clickCheckout();

			// checkout- shipping address
			LinkedHashMap<String, Object> addressDetails = (LinkedHashMap<String, Object>) addresses
					.get(shippingAddress);

			CheckOut.shippingAddress.fillAndClickNext(
					(String) addressDetails.get(CheckOut.shippingAddress.keys.firstName),
					(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
					(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
					(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
					(String) addressDetails.get(CheckOut.shippingAddress.keys.state),
					(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
					(String) addressDetails.get(CheckOut.shippingAddress.keys.phone), true);

			// Shipping method
			CheckOut.shippingMethod.fillAndclickNext(shippingMethod);

			// checkout- payment
			LinkedHashMap<String, Object> paymentDetails = (LinkedHashMap<String, Object>) paymentCards
					.get(payment);
			LinkedHashMap<String, Object> billAddressDetails = (LinkedHashMap<String, Object>) addresses
					.get(billingAddress);

			logs.debug(Arrays.asList(paymentDetails)+"");
			
					CheckOut.paymentInnformation.fillAndclickNext(
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), true,
							billingAddress.equalsIgnoreCase(shippingAddress),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.state),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal));

			CheckOut.reviewInformation.acceptTerms(true);
			CheckOut.reviewInformation.placeOrder();
			this.orderSubtotal = CheckOut.orderConfirmation.getSubTotal();
			this.orderTotal = CheckOut.orderConfirmation.getOrderTotal();
			this.orderShipping = CheckOut.orderConfirmation.getShippingCost();
			this.orderId = CheckOut.orderConfirmation.getOrderId();
			this.billingAddrerss = CheckOut.orderConfirmation.getBillingAddrerss();
			this.shippingAddrerss = CheckOut.orderConfirmation.getShippingAddrerss();
			
			// Go to Order History Page.
			Testlogs.get().debug("wait to be sure that the new order is diplayed in Order History page");
			Thread.sleep(2000);
			getDriver().get(url);
			//Click on first order on order history page
			OrderHistory.clickNthResponsiveTableItemTableCellAnchor("1",
					Integer.parseInt(orderNumToBeClicked));
			//OrderDetails.getOrderId();			
			sassert().assertEquals(OrderDetails.getOrderId(),orderId);
			OrderDetails.getOrderStatus();
			OrderDetails.getOrderDatePlaced();
			OrderDetails.getOrderTotal();
			sassert().assertTrue(OrderDetails.vifyCancelOrderBtnIsDisplayed(),
					"<font color=#f442cb>Cancel Order Btn is Displayed as expected</font>");
			sassert().assertTrue(OrderDetails.vifyReturnOrderBtnIsNotDisplayed(),
					"<font color=#f442cb>Return Order Btn is Not Displayed as expected</font>");
			sassert().assertTrue(OrderDetails.itemsTable.checkItemImage(), "<font color=#f442cb>NOT All product images are ok</font>");
			sassert().assertTrue(OrderDetails.itemsTable.checkProductLink((String) productDetails.get(PDP.keys.url)),
					"<font color=#f442cb>Product link is Not ok</font>");
			sassert().assertEquals(OrderDetails.itemsTable.getItemQty(),(String) productDetails.get(PDP.keys.qty));
			this.itemTotalPrice = OrderDetails.itemsTable.getItemPrice();
			sassert().assertTrue(itemTotalPrice.contains(OrderDetails.orderSumary.getOrderSubtotal()),
					"<font color=#f442cb>Product Subtotal is Not ok</font>");
			sassert().assertEquals(OrderDetails.orderSumary.getOrderSubtotal(),orderSubtotal);
			sassert().assertEquals(OrderDetails.orderSumary.getShippingCost(),orderTotal);
			sassert().assertEquals(OrderDetails.orderSumary.getOrderTotal(),orderShipping);
			OrderDetails.orderSumary.getOrderTax();

			sassert().assertTrue(shippingAddrerss.contains(OrderDetails.getShippingAddrerss()),
					"<font color=#f442cb>Shipping Addrerss is Not ok</font>");
			sassert().assertTrue(billingAddrerss.contains(OrderDetails.getBillingAddrerss()),
					"<font color=#f442cb>Billing Addrerss is Not ok</font>");
			sassert().assertTrue(payment.contains(OrderDetails.getPaymentDetails()),
					"<font color=#f442cb>Payment Details is Not ok</font>");
			// Shipping Method is only available for the shipped orders.
			// sassert().assertTrue(shippingMethod.contains(OrderDetails.getDeliveryMethod()),
			// "<font color=#f442cb>Delivery Method is Not ok</font>");
			
		    //Order Cancellation period has been updated to 1 minute, after that the order will be shipped.
			//and the user will not be able to cancel it.
			//and will be able to return the order.
			logs.debug("Wating session to be done and order to have return button ");
			Thread.sleep(80000);
		    Common.refreshBrowser();
		    Thread.sleep(4000);
			sassert().assertTrue(shippingMethod.contains(OrderDetails.getDeliveryMethod()),
					"<font color=#f442cb>Delivery Method is Not ok</font>");
			sassert().assertTrue(OrderDetails.vifyCancelOrderBtnIsNotDisplayed(),
					"<font color=#f442cb>Cancel Order Btn is Not Displayed as expected</font>");
			sassert().assertTrue(OrderDetails.vifyReturnOrderBtnIsDisplayed(),
					"<font color=#f442cb>Return Order Btn is Displayed as expected</font>");
			 Thread.sleep(7000);
			ActionDriver.returnPreviousPage();
			
			Common.testPass();
		} catch (Throwable t) {
			setTestCaseDescription(getTestCaseDescription());
			Testlogs.get().debug(MessageFormat.format(LoggingMsg.DEBUGGING_TEXT, t.getMessage()));
			t.printStackTrace();
			String temp = getTestCaseReportName();
			Common.testFail(t, temp);
			ReportUtil.takeScreenShot(getDriver());
			Assert.assertTrue(false, t.getMessage());
		}

	}

}
