package com.generic.tests.checkout;

import java.text.MessageFormat;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.LinkedHashMap;

import com.generic.page.PDP;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;
import com.generic.util.dataProviderUtils;
import com.generic.util.RandomUtilities;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class Base_checkout_negativeCases extends SelTestCase {

	private static LinkedHashMap<String, Object> addresses = null;
	private static LinkedHashMap<String, Object> invintory = null;
	private static LinkedHashMap<String, Object> paymentCards = null;

	// used sheet in test
	public static final String testDataSheet = SheetVariables.checkoutNegativeCasesSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger("checkout_setup"));
		testObject = test;
		addresses = Common.readAddresses();
		invintory = Common.readLocalInventory();
		paymentCards = Common.readPaymentcards();
	}

	@DataProvider(name = "Checkout", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency mentainance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "Checkout")
	public void checkOutBaseTest(String caseId, String runTest, String desc, String proprties, String products,
			String shippingMethod, String payment, String shippingAddress, String billingAddress, String globalAlerts,
			String ValidationMsg) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("checkout_" + getBrowserName()));
		setTestCaseReportName("Checkout_Negative Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));

		try {

			for (String product : products.split("\n")) {
				Testlogs.get().debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
				LinkedHashMap<String, Object> productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
				PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url),
						(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
						(String) productDetails.get(PDP.keys.qty));
			}

			Cart.clickCheckout();

			String submailEmail = RandomUtilities.getRandomEmail();
			CheckOut.guestCheckout.fillAndClickGuestCheckout(submailEmail);

			Thread.sleep(1000);

			LinkedHashMap<String, Object> addressDetails = (LinkedHashMap<String, Object>) addresses
					.get(shippingAddress);


			String countery  = (String) addressDetails.get(CheckOut.shippingAddress.keys.countery);
			String state = (String) addressDetails.get(CheckOut.shippingAddress.keys.state);
			String firstName = (String) addressDetails.get(CheckOut.shippingAddress.keys.firstName);
			String lastName = (String) addressDetails.get(CheckOut.shippingAddress.keys.lastName);
			String adddressLine = (String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine);
			String city = (String) addressDetails.get(CheckOut.shippingAddress.keys.city);
			String postal = (String) addressDetails.get(CheckOut.shippingAddress.keys.postal);
			String phone = (String) addressDetails.get(CheckOut.shippingAddress.keys.phone);
			
			
			// in case guest the save shipping checkbox is not exist
			if (proprties.contains("Shipping_Without country")) {

				CheckOut.shippingAddress.clickNext();

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);

				String currentEerrorsMsg = CheckOut.shippingAddress.getCountryError();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
			}

			if (proprties.contains("Shipping_Without firstName")) {

				CheckOut.shippingAddress.fillAndClickNext(
						"",
						lastName,
						adddressLine,
						city,
						state,
						postal,
						phone);

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getTitelError();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
			}
			if (proprties.contains("Shipping_Without last name")) {

				CheckOut.shippingAddress.fillAndClickNext(
						firstName,
						"",
						adddressLine,
						city,
						state,
						postal,
						phone);

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getFirstNameError();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
			}
			if (proprties.contains("Shipping_Without address line")) {

				CheckOut.shippingAddress.fillAndClickNext(
						firstName,
						lastName,
						"",
						city,
						state,
						postal,
						phone);

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getLastNameError();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);

			}
			if (proprties.contains("Shipping_Without city")) {

				CheckOut.shippingAddress.fillAndClickNext(
						firstName,
						lastName,
						adddressLine,
						"",
						state,
						postal,
						phone);

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getAddress1Error();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
			}
			if (proprties.contains("Shipping_Without state")) {

				CheckOut.shippingAddress.fillAndClickNext(
						firstName,
						lastName,
						adddressLine,
						city,
						"",
						postal,
						phone);;

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getCityError();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);

			}
			if (proprties.contains("Shipping_Without postcode")) {

				CheckOut.shippingAddress.fillAndClickNext(
						firstName,
						lastName,
						adddressLine,
						city,
						state,
						"",
						phone);

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getPostCodeEerror();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);

			}
			
			if (proprties.contains("Shipping_Without phone")) {

				CheckOut.shippingAddress.fillAndClickNext(
						firstName,
						lastName,
						adddressLine,
						city,
						state,
						postal,
						"");

				String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						CheckOut.shippingAddress.getAlertInfo(), globalAlerts);
				Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
				String currentEerrorsMsg = CheckOut.shippingAddress.getPostCodeEerror();
				String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
						ValidationMsg);
				sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);

			}

//			if (proprties.contains("Payment")) {
//				CheckOut.shippingAddress.fillAndClickNext(
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.title),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.firstName),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
//						(String) addressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//				// Shipping method
//				CheckOut.shippingMethod.fillAndclickNext(shippingMethod);
//
//				// checkout- payment
//
//				LinkedHashMap<String, Object> paymentDetails = (LinkedHashMap<String, Object>) paymentCards
//						.get(payment);
//				LinkedHashMap<String, Object> billAddressDetails = (LinkedHashMap<String, Object>) addresses.get("A4");
//
//				if (proprties.contains("Payment_Without CardType")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext("",
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getCardTypeError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//
//				}
//
//				if (proprties.contains("Payment_Without CardNumber")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext(payment,
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name), "",
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getCardNumberError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//				}
//
//				if (proprties.contains("Payment_With invalid CardNumber")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext(payment,
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name), "411111111111111",
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getCardNumberError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//				}
//
//				if (proprties.contains("Payment_Without expiry month")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext(payment,
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number), "",
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getExpirationMonthError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//				}
//
//				if (proprties.contains("Payment_Without expiry year")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext(payment,
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth), "",
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getExpirationYearError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//				}
//
//				if (proprties.contains("Payment_Without cvNumber")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext(payment,
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear), "", false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getCVNumberError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//				}
//
//				if (proprties.contains("Payment_With invalid cvNumber")) {
//
//					CheckOut.paymentInnformation.fillAndclickNext(payment,
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
//							(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear), "12", false,
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.title),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal),
//							(String) billAddressDetails.get(CheckOut.shippingAddress.keys.phone));
//
//					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//							CheckOut.paymentInnformation.getAlertInfo(), globalAlerts);
//					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
//					String currentEerrorsMsg = CheckOut.paymentInnformation.getCVNumberError();
//					String ErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR, currentEerrorsMsg,
//							ValidationMsg);
//					sassert().assertTrue(currentEerrorsMsg.contains(ValidationMsg), ErrorMsg);
//				}
//			}
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
}// class
