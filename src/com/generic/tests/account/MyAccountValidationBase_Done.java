package com.generic.tests.account;

import static org.testng.Assert.assertNotEquals;

import java.text.MessageFormat;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import com.generic.page.Registration;
import com.generic.page.MyAccount;
import com.generic.page.PDP;
import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class MyAccountValidationBase_Done extends SelTestCase {
	private static LinkedHashMap<String, Object> addresses = null;
	private static LinkedHashMap<String, Object> users = null;
	private static LinkedHashMap<String, Object> paymentCards = null;
	private static LinkedHashMap<String, Object> invintory = null;
	// used sheet in test
	public static final String testDataSheet = SheetVariables.MyAccountRegressionSheet;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	private static ThreadLocal<String> email = new ThreadLocal<String>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		addresses = Common.readAddresses();
		paymentCards = Common.readPaymentcards();
		users = Common.readUsers();
		invintory = Common.readLocalInventory();
		testObject = test;
	}

	@DataProvider(name = "MyAccount", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		return data;

	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "MyAccount")
	public void MyAccountTest(String caseId, String runTest, String desc, String proprties, String products,
			String payment, String shippingMethod, String shippingAddress, String billingAddress, String email)
			throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("My_Account " + getBrowserName()));
		setTestCaseReportName("My Account Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.ADDRESSPOOKDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));

		this.email.set(getSubMailAccount(email));
		String url = PagesURLs.getMyAccountPage();
		LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.get(email);
		boolean defaultAddress = proprties.contains("default Address");
		LinkedHashMap<String, Object> addressDetails = (LinkedHashMap<String, Object>) addresses.get(shippingAddress);

		try {
			Testlogs.get().debug(this.email.get());
			Testlogs.get().debug((String) userdetails.get(Registration.keys.password));
			SignIn.logIn(this.email.get(), (String) userdetails.get(Registration.keys.password));
			getDriver().get(url);

			if (!billingAddress.equals("")) {
				
			//	if()
				// checkout- payment
				LinkedHashMap<String, Object> paymentDetails = (LinkedHashMap<String, Object>) paymentCards
						.get(payment);
				LinkedHashMap<String, Object> billAddressDetails = (LinkedHashMap<String, Object>) addresses
						.get(billingAddress);

				logs.debug(Arrays.asList(paymentDetails) + "");

				for (String product : products.split("\n")) {
					Testlogs.get().debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
					LinkedHashMap<String, Object> productDetails = (LinkedHashMap<String, Object>) invintory
							.get(product);
					PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url),
							(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
							(String) productDetails.get(PDP.keys.qty));
				}
				Cart.clickCheckout();
				CheckOut.shippingAddress.clickAddAddressBtn();
				CheckOut.shippingAddress.fillAndClickNext(
						(String) addressDetails.get(CheckOut.shippingAddress.keys.firstName),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.state),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.phone));
				CheckOut.shippingMethod.selectShippingMethod(shippingMethod);
				CheckOut.shippingMethod.clickNext();
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

				CheckOut.reviewInformation.placeOrder();
				// validate order is successfully created.
				CheckOut.orderConfirmation.getOrderTotal();
				getDriver().get(url);
				String paymentCard = MyAccount.paymentDetails.getPaymentCard();
				Testlogs.get().debug("Site payment card:" + paymentCard);
				Testlogs.get().debug("Expected address line: " + (String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine));
				sassert().assertTrue(
						paymentCard.contains((String) billAddressDetails.get(CheckOut.shippingAddress.keys.adddressLine)),
						"payment details doesnot contain line1: " + paymentCard);

				if ((MyAccount.paymentDetails.getNumberOfPaymentCards() != 0) && proprties.contains("delete")) {

					MyAccount.paymentDetails.clickRemovePaymentCardBtn();
					MyAccount.paymentDetails.confirmRemovePaymentCard();
					Thread.sleep(1000);
				}

			} else {

				if (proprties.contains("new") || proprties.contains("default") || proprties.contains("delete")) {
					MyAccount.addressBookDetails.clickAddAddressBtn();
					MyAccount.addressBookDetails.fillAndClickSave(
							(String) addressDetails.get(CheckOut.shippingAddress.keys.firstName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.state),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.phone), defaultAddress);

					if (proprties.contains("new")) {
						String siteAddress = MyAccount.addressBookDetails.getFirstAddressDetails();
						Testlogs.get().debug("Site Address: " + siteAddress);
						Testlogs.get().debug("Sdddress Line: "
								+ (String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine));
						sassert().assertTrue(
								siteAddress.contains(
										(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine)),
								"Address doesnt contain line1: " + siteAddress);
					}
				}
				if (proprties.contains("edit")) {
					getDriver().get(url);
					String addressbook = MyAccount.addressBookDetails.getFirstAddressDetails();
					MyAccount.addressBookDetails.clickEditAddressBtn();
					MyAccount.addressBookDetails.fillAndClickSave("NEW_" + RandomUtils.nextInt(1000, 9000),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.state),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
							(String) addressDetails.get(CheckOut.shippingAddress.keys.phone), false);
					sassert().assertNotEquals(addressbook,
							MyAccount.addressBookDetails.getFirstAddressDetails());
				}

				// Remove the created address.
				if (proprties.contains("delete")) {
					MyAccount.addressBookDetails.clickRemoveAddressBtn();
					MyAccount.addressBookDetails.confirmRemoveAddress();
				}
			}
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
