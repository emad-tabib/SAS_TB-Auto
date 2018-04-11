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
import com.generic.page.Registration;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.HomePage;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;
import com.generic.util.dataProviderUtils;
import com.generic.util.RandomUtilities;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class AccountsSetupUS_Done extends SelTestCase {

	private static LinkedHashMap<String, Object> addresses = null;
	private static LinkedHashMap<String, Object> invintory = null;
	private static LinkedHashMap<String, Object> paymentCards = null;
	private static LinkedHashMap<String, Object> users = null;

	// user types
	public static final String guestUser = "guest";
	public static final String freshUser = "fresh";
	public static final String loggedInUser = "loggedin";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.USAccountSetupsheet;


	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger("Account_setup"));
		testObject = test;
		addresses = Common.readAddresses();
		invintory = Common.readLocalInventory();
		paymentCards = Common.readPaymentcards();
		users = Common.readUsers();
	}

	@DataProvider(name = "Account_Setup", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency mentainance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));

		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "Account_Setup")
	public void accountSetupUSBaseTest(String caseId, String runTest, String products, String shippingMethod, String payment,
			String shippingAddress, String billingAddress, String email) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("AccountSetup_" + getBrowserName()));
		setTestCaseReportName("AccountSetup Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.CHECKOUTDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), email, email, payment, shippingMethod));

		String Pemail = getSubMailAccount(email);
		try {
			LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.get(email);
			Testlogs.get().debug(Pemail);
			Testlogs.get().debug((String) userdetails.get(Registration.keys.password));
			Thread.sleep(3000);
			
		//close production popup	
		//	HomePage.closeSubcriptionPopup();

	//		getDriver().get("https://10.30.50.17:9002/en/login");
			Registration.fillAndClickRegister(Pemail, Pemail, (String) userdetails.get(Registration.keys.firstName), (String) userdetails.get(Registration.keys.lastName),
					(String) userdetails.get(Registration.keys.country), (String) userdetails.get(Registration.keys.postalCode), (String) userdetails.get(Registration.keys.password),
					(String) userdetails.get(Registration.keys.password), true);
			
			if(!SignIn.checkUserAccount())
			{
				Testlogs.get().debug("Registeration failed");
				throw new Exception("Registeration failed");
			}
	//		SignIn.logIn(Pemail, "passw0rd");

			Testlogs.get().debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, products.split("\n")[0]));
			LinkedHashMap<String, Object> productDetails = (LinkedHashMap<String, Object>) invintory
					.get(products.split("\n")[0]);
			PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url),
					(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
					(String) productDetails.get(PDP.keys.qty));
			
		//	String url = PagesURLs.getShoppingCartPage();
		//	getDriver().get(url);
			Cart.clickCheckout();
			Thread.sleep(1000);
			getDriver().get("https://qa-kiosk.tommybahama.com/en/cart/checkout");
		//	getDriver().get("https://10.30.50.17:9002/en/cart/checkout");
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
		//	CheckOut.shippingAddress.clickUseSuggestedAddress();
			// Shipping method
			CheckOut.shippingMethod.fillAndclickNext(shippingMethod);

			// do not save address if scenario is guest user
			boolean saveBilling = true;
			LinkedHashMap<String, Object> paymentDetails = (LinkedHashMap<String, Object>) paymentCards.get(payment);
			LinkedHashMap<String, Object> billAddressDetails = (LinkedHashMap<String, Object>) addresses
					.get(billingAddress);
			CheckOut.paymentInnformation.fillAndclickNext(
					(String) paymentDetails.get(CheckOut.paymentInnformation.keys.name),
					(String) paymentDetails.get(CheckOut.paymentInnformation.keys.number),
					(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireMonth),
					(String) paymentDetails.get(CheckOut.paymentInnformation.keys.expireYear),
					(String) paymentDetails.get(CheckOut.paymentInnformation.keys.CVCC), saveBilling,
					billingAddress.equalsIgnoreCase(shippingAddress),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.firstName),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.lastName),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.countery),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.city),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.state),
					(String) billAddressDetails.get(CheckOut.shippingAddress.keys.postal));

			//CheckOut.reviewInformation.acceptTerms(true);
			CheckOut.reviewInformation.placeOrder();
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
