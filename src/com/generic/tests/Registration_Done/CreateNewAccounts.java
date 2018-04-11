package com.generic.tests.Registration_Done;

import java.text.MessageFormat;
import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.util.LinkedHashMap;
import com.generic.page.Registration;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.dataProviderUtils;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;

public class CreateNewAccounts extends SelTestCase {

	private static LinkedHashMap<String, Object> users = null;

	// used sheet in test
	public static final String testDataSheet = SheetVariables.USAccountSetupsheet;
	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger("Account_setup"));
		testObject = test;
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
