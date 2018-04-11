package com.generic.tests.account;

import static org.testng.Assert.assertNotEquals;

import java.text.MessageFormat;

import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import java.util.LinkedHashMap;

import com.generic.page.Registration;
import com.generic.page.AddressBook;
import com.generic.page.CheckOut;
import com.generic.page.SignIn;
import com.generic.selector.AddressBookSelectors;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.SelectorUtil;

public class AddressBookValidation extends SelTestCase {
	private static LinkedHashMap<String, Object> addresses = null;
	private static LinkedHashMap<String, Object> users = null;
	boolean defaultAddress = true;
	private String addressbook;
	private String numberofaddresses;
	// used sheet in test
	public static final String testDataSheet = SheetVariables.AddressBookSheet;

	private String caseId;
	private String runTest;
	private String url;
	private String desc;
	private String newaddress;
	private String email;

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		addresses = Common.readAddresses();
		users = Common.readUsers();
		testObject = test;
	}

	@DataProvider(name = "AddressBook", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		return data;
		
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "AddressBook")
	public void BookAddressTest(String caseId, String runTest,String desc, String prop, String email, String newaddress)
			throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("Address_Book " + getBrowserName()));
		setTestCaseReportName("Address Book Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.ADDRESSPOOKDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));

		this.email = getSubMailAccount(email);
		String url = PagesURLs.getAddressBookPage();
		
		try {
			LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.get(email);
			Testlogs.get().debug(this.email);
			Testlogs.get().debug((String) userdetails.get(Registration.keys.password));
			
			if (prop.contains("edit")) {
				SignIn.logIn(this.email, (String) userdetails.get(Registration.keys.password));
				getDriver().get(url);
				addressbook = AddressBook.getFirstAddressDetails();
				AddressBook.clickEditAddress();
				LinkedHashMap<String, Object> addressDetails = (LinkedHashMap<String, Object>) addresses
						.get(newaddress);
				AddressBook.fillAndClickSave(
						"NEW_" + RandomUtils.nextInt(1000, 9000),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.state),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.phone), true);
				AddressBook.clickAddressBackBtn();
				assertNotEquals(addressbook, AddressBook.getFirstAddressDetails());
			}
			if (prop.contains("form validation")) {
				SignIn.logIn(this.email, (String) userdetails.get(Registration.keys.password));
				getDriver().get(url);
				addressbook = AddressBook.getFirstAddressDetails();
				AddressBook.clickEditAddress();
				AddressBook.clearAddress();
				AddressBook.verifyAddressFormError();
			}
			if (prop.contains("new") || prop.contains("default") || prop.contains("delete")) {
				SignIn.logIn(this.email, (String) userdetails.get(Registration.keys.password));
				getDriver().get(url);
				addressbook = AddressBook.getFirstAddressDetails();
				Thread.sleep(1000);
				AddressBook.clickAddNewAddress();
				getDriver().get(AddressBookSelectors.addaddressurl);
				LinkedHashMap<String, Object> addressDetails = (LinkedHashMap<String, Object>) addresses
						.get(newaddress);
				AddressBook.fillAndClickSave(
						"NEW_" + RandomUtils.nextInt(1000, 9000),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.lastName),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.adddressLine),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.countery),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.city),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.state),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.postal),
						(String) addressDetails.get(CheckOut.shippingAddress.keys.phone), defaultAddress);
				AddressBook.clickAddressBackBtn();
				if (prop.contains("new")) {
					sassert().assertNotEquals(addressbook, AddressBook.getFirstAddressDetails());
					//Remove the created address.
					AddressBook.clickRemoveAddress(0);
					AddressBook.clickDeleteBtn();
				}
				if (prop.contains("default")) {
					getDriver().get(url);
					addressbook = AddressBook.getFirstAddressDetails();
					AddressBook.clickSetAsDefault();
					AddressBook.getAlertInfo();
					sassert().assertNotEquals(addressbook, AddressBook.getFirstAddressDetails());
					//Remove the created address.
					AddressBook.clickRemoveAddress(1);
					AddressBook.clickDeleteBtn();
				}
				if (prop.contains("delete")) {
					getDriver().get(url);
					String numberofaddresses = AddressBook.getNumberOfAddresses(AddressBookSelectors.accountAddressbookList);
					AddressBook.clickRemoveAddress(0);
					AddressBook.clickDeleteBtn();
					Thread.sleep(1000);
					logs.debug("number of Saved addresses before deleting any address: "+numberofaddresses);
					sassert().assertNotEquals(numberofaddresses,AddressBook.getNumberOfAddresses(AddressBookSelectors.accountAddressbookList));
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
