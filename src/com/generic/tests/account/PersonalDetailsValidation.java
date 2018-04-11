package com.generic.tests.account;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.PagesURLs;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;
import com.generic.util.dataProviderUtils;

import sun.util.logging.resources.logging;

import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.page.CheckOut;
import com.generic.page.PersonalDetails;
import com.generic.page.Registration;

public class PersonalDetailsValidation extends SelTestCase {

	// used sheet in test
	public static final String testDataSheet = SheetVariables.personalDetailsSheet;
	private static ThreadLocal<String> email = new ThreadLocal<String>();

	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	private static LinkedHashMap<String, Object> users;

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		users = Common.readUsers();
	}

	@DataProvider(name = "personalDetails", parallel = true)
	// concurrency maintenance on sheet reading
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));

		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "personalDetails")
	public void verifyPersonalDetails(String caseId, String runTest, String desc, String proprties, String email,
			String firstName, String lastName, String globalAlerts, String firstNameErrors,
			String lastNameErrors) throws Exception {

		boolean doVerifyCurrent =  proprties.contains("Verify current user");
		boolean doClickCancelBtn = proprties.contains("click cancel");
		boolean doClickUpdateBtn = proprties.contains("click update");
		boolean revertChanges = proprties.contains("revert changes");
		
		LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>) users.get(email);

		Testlogs.set(new SASLogger("personalDetails_" + getBrowserName()));
		// Important to add this for logging/reporting
		setTestCaseReportName("personalDetails Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.TEST_CASE_DESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc));

		this.email.set(getSubMailAccount(email));
		String url =  PagesURLs.getPersonalDetailsPage();
		
		try {

			SignIn.logIn(this.email.get(), (String)userDetails.get(Registration.keys.password));
			getDriver().get(url);
			
			if (doVerifyCurrent) {
				
				String profileTitle = PersonalDetails.getTitleValue().toLowerCase().trim() ;
				String profileFirstName =PersonalDetails.getFirstNameValue().toLowerCase().trim();
				String profileLastname = PersonalDetails.getLastNameValue().toLowerCase().trim();
				
//				String incorrectTitleErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
//						profileTitle, ((String) userDetails.get(Registration.keys.title)).toLowerCase().trim());
				String incorrectFirstNameErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						profileFirstName, ((String) userDetails.get(Registration.keys.firstName)).toLowerCase().trim());
				String incorrectlastNameErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						profileLastname,   ((String) userDetails.get(Registration.keys.lastName)).toLowerCase().trim());
				
//				sassert().assertTrue(profileTitle.contains(((String) userDetails.get(Registration.keys.title)).toLowerCase()), incorrectTitleErrorMsg);
				sassert().assertTrue(profileFirstName.contains(((String) userDetails.get(Registration.keys.firstName)).toLowerCase()),incorrectFirstNameErrorMsg);
				sassert().assertTrue(profileLastname.contains(((String) userDetails.get(Registration.keys.lastName)).toLowerCase()),incorrectlastNameErrorMsg);
			} else {
//				PersonalDetails.fillInNewValuesAndClickUpdateOrCancel((String) userDetails.get(Registration.keys.title)
//						, firstName, lastName, doClickUpdateBtn,
//						doClickCancelBtn);
				Thread.sleep(3000);
				if (doClickUpdateBtn) {
					String globalAlertMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
							PersonalDetails.getGlobalAlertsMsg(), globalAlerts);
					Assert.assertTrue(globalAlertMsg.contains(globalAlerts), globalAlertMsg);
					if (!firstNameErrors.equals("")) {
						String currentMsg = PersonalDetails.getFirstNameErrorMsg();
						String firstNameErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,currentMsg, lastNameErrors);
						sassert().assertTrue(currentMsg.contains(firstNameErrors), firstNameErrorMsg );
					}
					if (!lastNameErrors.equals("")) {
						String currentMsg = PersonalDetails.getLastNameErrorMsg();
						logs.debug(currentMsg);
						String lastNameErrorMsg = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
								currentMsg, lastNameErrors);
						sassert().assertTrue(currentMsg.contains(lastNameErrors) , lastNameErrorMsg);
					}
				}
			}
			
			if(revertChanges)
			{
//				getDriver().get(url);
//				PersonalDetails.fillInNewValuesAndClickUpdateOrCancel((String) userDetails.get(Registration.keys.title),
//						(String) userDetails.get(Registration.keys.firstName),
//						(String) userDetails.get(Registration.keys.lastName), doClickUpdateBtn, doClickCancelBtn);
//				Thread.sleep(2000);
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
		}

	}

}
