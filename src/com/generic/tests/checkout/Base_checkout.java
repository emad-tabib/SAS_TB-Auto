package com.generic.tests.checkout;

import static org.testng.Assert.assertEquals;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.LinkedHashMap;

import com.generic.page.PDP;
import com.generic.page.Registration;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;
import com.generic.util.RandomUtilities;

@RunWith(Parameterized.class)
public class Base_checkout extends SelTestCase {

	public static final LinkedHashMap<String, Object> addresses = Common.readAddresses();
	public static final LinkedHashMap<String, Object> invintory = Common.readLocalInventory();
	public static final LinkedHashMap<String, Object> paymentCards = Common.readPaymentcards();
	public static final LinkedHashMap<String, Object> users = Common.readUsers();

	// user types
	public static final String guestUser = "guest";
	public static final String freshUser = "fresh";
	public static final String loggedInUser = "loggedin";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.checkoutSheet;

	private String caseId;
	private int caseIndexInDatasheet;
	private String runTest;
	private String desc;
	private String proprties;
	private String[] products;
	private String shippingMethod;
	private String payment;
	private String shippingAddress;
	private String billingAddress;
	private String coupon;
	private String email;
	private String orderId;
	private String orderTotal;
	private String orderSubtotal;
	private String orderTax;
	private String orderShipping;

	@BeforeClass
	public static void initialSetUp() throws Exception {
		testCaseRepotId = SheetVariables.checkoutTestCaseId;
	}

	public Base_checkout(String caseId, String runTest, String desc, String proprties, String products,
			String shippingMethod, String payment, String shippingAddress, String billingAddress, String coupon,
			String email, String orderId, String orderTotal, String orderSubtotal, String orderTax,
			String orderShipping) {

		this.caseId = caseId;
		// moving variables from parameterize module to class variables
		this.runTest = runTest;
		this.desc = desc;
		this.proprties = proprties;

		// get all products need to be added in case
		this.products = products.split("\n");

		this.shippingMethod = shippingMethod;
		this.payment = payment;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.coupon = coupon;
		this.email = email;
		this.orderId = orderId;
		this.orderTotal = orderTotal;
		this.orderSubtotal = orderSubtotal;
		this.orderTax = orderTax;
		this.orderShipping = orderShipping;

	}

	@Parameters(name = "{index}_:{2}")
	public static Collection<Object[]> loadTestData() throws Exception {
		Object[][] data = TestUtilities.getData(testDataSheet);
		return Arrays.asList(data);
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test
	public void checkOutBaseTest() throws Exception {
		setTestCaseDescription(MessageFormat.format(LoggingMsg.CHECKOUTDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- "),payment,shippingMethod));
		
		caseIndexInDatasheet = getDatatable().getCellRowNum(testDataSheet, CheckOut.keys.caseId, caseId);
		initializeTestResults(testDataSheet, caseIndexInDatasheet);
		try {
			if (proprties.contains(loggedInUser)) {
				LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.get(email);
				SignIn.logIn(email, (String) userdetails.get(Registration.keys.password));
			}
			if (proprties.contains(freshUser)) {
				email = RandomUtilities.getRandomEmail();

				// take any user as template
				LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.entrySet().iterator()
						.next().getValue();
				// userdetails.put(Registration.keys.email,email); //TODO: remove it

				boolean acceptRegTerm = true;

				Registration.fillAndClickRegister((String) userdetails.get(Registration.keys.title),
						(String) userdetails.get(Registration.keys.firstName),
						(String) userdetails.get(Registration.keys.lastName), email,
						(String) userdetails.get(Registration.keys.password),
						(String) userdetails.get(Registration.keys.password), acceptRegTerm);
			}

			for (String product : products) {
				logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
				LinkedHashMap<String, Object> productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
				PDP.addProductsToCart((String) productDetails.get(PDP.keys.url),
						(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
						(String) productDetails.get(PDP.keys.qty));

			}

			// flow to support coupon validation
			if (!"".equals(coupon)) {
				Cart.applyCoupon(coupon);
				if (coupon.contains(Cart.keys.invalidCoupon)) {
					Cart.validateinvaldcoupon();
				}
			}
			Cart.getNumberOfproducts();
			orderSubtotal = Cart.getOrderSubTotal();
			orderTax = Cart.getOrderTax();

			Cart.clickCheckout();

			if (proprties.contains(guestUser)) {
				email = RandomUtilities.getRandomEmail();
				CheckOut.guestCheckout.fillAndClickGuestCheckout(email);
			}

			// Validate the order sub total in shipping address form section
			assertEquals(CheckOut.shippingAddress.getOrdersubTotal(), orderSubtotal);

			// checkout- shipping address
			if (proprties.contains(CheckOut.shippingAddress.keys.isSavedShipping) && !proprties.contains(freshUser)
					&& !proprties.contains(guestUser)) {
				CheckOut.shippingAddress.fillAndClickNext(true);
			} else {
				LinkedHashMap<String, Object> addressDetails = (LinkedHashMap<String, Object>) addresses
						.get(shippingAddress);

				boolean saveShipping = !proprties.contains(guestUser);

				// in case guest the save shipping checkbox is not exist
				if (saveShipping) {
					CheckOut.shippingAddress.fillAndClickNext(
							(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.title),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.firstName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.phone), saveShipping);
				} else {
					CheckOut.shippingAddress.fillAndClickNext(
							(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.title),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.firstName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.phone));
				}
			}

			// Validate the order sub total in shipping method section
			assertEquals(CheckOut.shippingMethod.getOrderSubTotal(), orderSubtotal);

			// Shipping method
			CheckOut.shippingMethod.fillAndclickNext(shippingMethod);

			// Validate the order sub total in billing form section
			assertEquals(CheckOut.paymentInnformation.getOrderSubTotal(), orderSubtotal);

			// checkout- payment
			if (proprties.contains(CheckOut.paymentInnformation.keys.isSavedPayement) && !proprties.contains(freshUser)
					&& !proprties.contains(guestUser)) {
				CheckOut.paymentInnformation.fillAndclickNext(true);
			} else {

				// do not save address if scenario is guest user
				boolean saveBilling = !proprties.contains(guestUser);
				LinkedHashMap<String, Object> paymentDetails = (LinkedHashMap<String, Object>) paymentCards
						.get(payment);
				LinkedHashMap<String, Object> billAddressDetails = (LinkedHashMap<String, Object>) addresses
						.get(billingAddress);

				if (saveBilling) {
					CheckOut.paymentInnformation.fillAndclickNext(payment,
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), saveBilling,
							billingAddress.equalsIgnoreCase(shippingAddress),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
				} else {
					CheckOut.paymentInnformation.fillAndclickNext(payment,
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC),
							billingAddress.equalsIgnoreCase(shippingAddress),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
				}
			}

			// Validate the order subtotal in order review section
			assertEquals(CheckOut.reviewInformation.getSubtotal(), orderSubtotal);

			CheckOut.reviewInformation.acceptTerms(true);
			CheckOut.reviewInformation.placeOrder();

			// Validate the order sub total in order review section
			assertEquals(CheckOut.orderConfirmation.getSubTotal(), orderSubtotal);

			orderTotal = CheckOut.orderConfirmation.getOrderTotal();
			orderShipping = CheckOut.orderConfirmation.getShippingCost();
			orderId = CheckOut.orderConfirmation.getOrderId();

			// TODO: compare addresses
			CheckOut.orderConfirmation.getBillingAddrerss();
			CheckOut.orderConfirmation.getShippingAddrerss();

			if (proprties.contains(guestUser) && proprties.contains("register-guest")) {
				CheckOut.guestCheckout.fillPreRegFormAndClickRegBtn("1234567", false);
			}

			writeResultsToTestDatasheet(testDataSheet, caseIndexInDatasheet);

			Common.testPass();
		} catch (Throwable t) {
			setTestCaseDescription(getTestCaseDescription());
			logs.debug(MessageFormat.format(LoggingMsg.DEBUGGING_TEXT, t.getMessage()));
			t.printStackTrace();
			String temp = getTestCaseId();
			Common.testFail(t, temp);
			Common.takeScreenShot();
			Assert.assertTrue(t.getMessage(), false);
		} // catch
	}// test

	private void writeResultsToTestDatasheet(String sheetName, int row) {
		getCurrentFunctionName(true);
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderId, row, orderId);
		if (email.contains("random")) {
			getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.email, row, email);
		}
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderSubtotal, row, orderSubtotal);
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderShipping, row, orderShipping);
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderTax, row, orderTax);
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderTotal, row, orderTotal);
		getCurrentFunctionName(false);
	}// write results

	private void initializeTestResults(String sheetName, int row) {
		getCurrentFunctionName(true);
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderId, row, "");
		if (email.contains("random")) {
			SelTestCase.getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.email, row, "");
		}
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderShipping, row, "");
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderSubtotal, row, "");
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderTax, row, "");
		getDatatable().setCellData(sheetName, CheckOut.orderConfirmation.keys.orderTotal, row, "");
		getCurrentFunctionName(false);
	}// initializeTestResults
}// class
