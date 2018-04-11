package com.generic.tests.Registration_Done;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.Cart;
import com.generic.page.CheckOut;
import com.generic.page.PDP;
import com.generic.page.Registration;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.RandomUtilities;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.SelectorUtil;
import com.generic.util.TestUtilities;
import com.generic.util.dataProviderUtils;

public class RegistrationFormValidation extends SelTestCase {
	private static  LinkedHashMap<String, Object> users =null ;

	// possible scenarios
	public static final String freshUser = "fresh";
	public static final String existingUser = "existing";
	public static final String emptyData = "empty";
	public static final String invalidUserID = "UserID";
	public static final String passwordMismatch = "Mismatch";
	public static final String invalidPassword = "UserPassword";
	
	//messagesValidations
	public static final String successMessage = "success";
	public static final String invalidEmail = "invalidEmail";
	public static final String checkEmailError = "checkEmailError";
	public static final String postalCodeError = "postalError";
	public static final String countryError = "countryError";
	public static final String firstNameError = "firstNameError";
	public static final String lastNameError = "lastNameError";
	public static final String passwordError = "passwordError";
	public static final String confPasswordError = "confirmPasswordError";
	public static final String passwordRulesError = "passwordRuleError";
	public static final String passwordMisatchError = "passwordMismatchError";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.registrationRegressionSheet;

	private static XmlTest testObject;
	
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>() ; 
	
	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		users = Common.readUsers();
	}

	@DataProvider(name = "Registration", parallel = true)
	//concurrency maintenance on sheet reading 
	public static Object[][] loadTestData() throws Exception {
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked") // avoid warning from linked hashmap
	@Test(dataProvider = "Registration")
	public void registrationRegressionTest(String caseId, String runTest, String desc, String proprties, String fieldsValidation) throws Exception {
		Testlogs.set(new SASLogger("registration "+getBrowserName()));
		//Important to add this for logging/reporting 
		setTestCaseReportName("Registration Case");
		//Testlogs.get().debug("Case Browser: "  + testObject.getParameter("browserName") );
		logCaseDetailds(MessageFormat.format(LoggingMsg.REGISTRATIONDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- ")));
		
		try {
			if (proprties.contains(freshUser)) {
				String firstName = RandomUtilities.getRandomName();
				String lastName = RandomUtilities.getRandomName();
				String password = RandomUtilities.getRandomPassword(7);
				String email = RandomUtilities.getRandomEmail();
				String country = "United States";
				String postalCode = "10167";
				
				Registration.fillAndClickRegister(email, email, firstName, lastName,
						country, postalCode, password,
						password, true);
			}
			if (proprties.contains(existingUser)) {
				// take any user as template
				
				LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.entrySet().iterator()
						.next().getValue();
				
				String firstName = RandomUtilities.getRandomName();
				String lastName = RandomUtilities.getRandomName();
				String password = RandomUtilities.getRandomPassword(7);
				String country = "United States";
				String postalCode = "10167";
				//TODO: move this to sheet
				String email = (String) userdetails.get(Registration.keys.email);
						//(String) userdetails.get(Registration.keys.email);
				logs.debug("Registration mail: "+email);
				Registration.fillAndClickRegister(email, email, firstName, lastName,
						country, postalCode, password,
						password, true);
			}
			if (proprties.contains(emptyData)) {
				Registration.clickRegistration();
			}
			if (proprties.contains(invalidUserID)) {
				String firstName = RandomUtilities.getRandomName();
				String lastName = RandomUtilities.getRandomName();
				String password = RandomUtilities.getRandomPassword(7);
				String email = "invalid@valid";
				String country = "United States";
				String postalCode = "10167";
				Registration.fillAndClickRegister(email, email, firstName, lastName,
						country, postalCode, password,
						password, true);
			}
			if (proprties.contains(passwordMismatch)) {
				String firstName = RandomUtilities.getRandomName();
				String lastName = RandomUtilities.getRandomName();
				String password = RandomUtilities.getRandomPassword(7);
				String confPassword = RandomUtilities.getRandomPassword(7);
				String email = RandomUtilities.getRandomEmail();
				String country = "United States";
				String postalCode = "10167";
				Registration.fillAndClickRegister(email, email, firstName, lastName,
						country, postalCode, password,
						confPassword, true);
			}
			if (proprties.contains(invalidPassword)) {
				String firstName = RandomUtilities.getRandomName();
				String lastName = RandomUtilities.getRandomName();
				String password = RandomUtilities.getRandomPassword(5);
				String email = RandomUtilities.getRandomEmail();
				String country = "United States";
				String postalCode = "10167";
				Registration.fillAndClickRegister(email, email, firstName, lastName,
						country, postalCode, password,
						password, true);
			}
			
			Thread.sleep(2000);
			
			for (String message : fieldsValidation.split("\n")) {
				String key = message.split(":")[0];
				String messageText = message.split(":")[1];
				Testlogs.get().debug(MessageFormat.format(LoggingMsg.REGISTRATION_FIELDS_ERRORS, key));
				if (key.equalsIgnoreCase(successMessage)) {
					String actualMessage = Registration.getRegistrationSuccessMessage();
					String expectedMessage = messageText;
					boolean isIncluded = actualMessage.contains(expectedMessage);
					sassert().assertEquals(isIncluded, true);
				}
				if (key.equalsIgnoreCase(invalidEmail)) {
					String actualMessage =Registration.getEmailAddressError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(checkEmailError)) {
					String actualMessage = Registration.getConfirmEmailAddressError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(firstNameError)) {
					String actualMessage =Registration.getFirstNameError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(lastNameError)) {
					String actualMessage =Registration.getLastNameError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(countryError)) {
					String actualMessage = Registration.getCountryError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(postalCodeError)) {
					String actualMessage = Registration.getPostalCodeError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(passwordRulesError)) {
					String actualMessage =Registration.getPasswordRulesError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(passwordError)) {
					String actualMessage =Registration.getPasswordError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(confPasswordError)) {
					String actualMessage =Registration.getConfirmPasswordError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
				}
				if (key.equalsIgnoreCase(passwordMisatchError)) {
					String actualMessage = Registration.getPasswordMatchError();
					String expectedMessage = messageText;
					sassert().assertEquals(actualMessage, expectedMessage);
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
}
