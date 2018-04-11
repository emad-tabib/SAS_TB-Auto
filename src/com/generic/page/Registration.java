package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.generic.selector.CartSelectors;
import com.generic.selector.RegistrationSelectors;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.util.SelectorUtil;

public class Registration extends SelTestCase {
	public static class keys {
		public static final String caseId = "caseId";
		//public static final String title = "title";
		public static final String name = "name";
		public static final String userName = "userName";
		public static final String firstName = "firstName";
		public static final String lastName = "lastName";
		public static final String password = "password";
		public static final String email = "mail";
		public static final String state = "state";
		public static final String country = "country";
		public static final String postalCode = "postalCode";
	}

	public static void loginToKioskAccount(String email) throws Exception {
		getCurrentFunctionName(true);
		typeKioskEmailAddress(email);
		pickFirstSuggestedUser();
		clickStartSessionBtn();
		if (!checkSessionIsStarted()) {
			logs.debug("Error: Session is not started as expected");
			throw new Exception("Error: Session is not started as expected");
		}
		getCurrentFunctionName(false);

	}
	public static void typeKioskEmailAddress(String address) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "emailAddress ", address));
		subStrArr.add(RegistrationSelectors.kioskEmailAddress);
		valuesArr.add(address);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}
	
	public static void typeKioskFullName(String fName) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "emailAddress ", fName));
		subStrArr.add(RegistrationSelectors.kioskFullName);
		valuesArr.add(fName);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}
	
	public static void pickFirstSuggestedUser() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
	//	logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, "Register btn"));
		subStrArr.add(RegistrationSelectors.kioskSuggestedUsers);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}
	
	public static void clickStartSessionBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, "Register btn"));
		subStrArr.add(RegistrationSelectors.startSessionBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}
	
	public static boolean checkSessionIsStarted() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		subStrArr.add(RegistrationSelectors.endSessionBtn);
		boolean isDisplayed = SelectorUtil.isDisplayed(subStrArr);
		logs.debug("End Session button is displayed: " + isDisplayed);
		getCurrentFunctionName(false);
		return isDisplayed;
	}
	
	public static void typeEmailAddress(String address) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "emailAddress ", address));
		subStrArr.add(RegistrationSelectors.emailAddress);
		valuesArr.add(address);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}
	
	public static void typeConfirmEmailAddress(String confirmAddress) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "confirmEmail ", confirmAddress));
		subStrArr.add(RegistrationSelectors.confirmEmail);
		valuesArr.add(confirmAddress);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}
	
	public static void typeFirstName(String firstName) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "firstname ", firstName));
		subStrArr.add(RegistrationSelectors.firstName);
		valuesArr.add(firstName);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void typeLastName(String lastName) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "lastname ", lastName));
		subStrArr.add(RegistrationSelectors.lastName);
		valuesArr.add(lastName);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void selectCountry(String country) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.SELECTING_ELEMENT_VALUE, "country ", country));
		subStrArr.add(RegistrationSelectors.country);
		valuesArr.add(country);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}
	
	public static void typePostalCode(String postalCode) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "postalCode ", postalCode));
		subStrArr.add(RegistrationSelectors.postalCode);
		valuesArr.add(postalCode);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typePassword(String password) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "password ", password));
		subStrArr.add(RegistrationSelectors.password);
		valuesArr.add(password);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typeConfirmPassword(String confPassword) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "confirmPassword", confPassword));
		subStrArr.add(RegistrationSelectors.confirmPassword);
		valuesArr.add(confPassword);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void checkSignMeUp(boolean isSignMeUpChecked) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CHECKBOX_SEL_VAL, "isSignMeUpChecked", isSignMeUpChecked));
		subStrArr.add(RegistrationSelectors.signMeUp);
		valuesArr.add(String.valueOf(isSignMeUpChecked));
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void clickRegistration() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, "Register btn"));
		subStrArr.add(RegistrationSelectors.register);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void fillAndClickRegister(String email, String confirmEmail, String fName, String lName, String country, String postalCode,  String pass,
			String confPass, boolean isSignMeUpChecked) throws Exception {
		getCurrentFunctionName(true);
		if (!"".equals(email))
			typeEmailAddress(email);
		
		if (!"".equals(confirmEmail))
			typeConfirmEmailAddress(confirmEmail);

		if (!"".equals(fName))
			typeFirstName(fName);

		if (!"".equals(lName))
			typeLastName(lName);

		if (!"".equals(country))
			selectCountry(country);

		if (!"".equals(postalCode))
			typePostalCode(postalCode);
		
		if (!"".equals(pass))
			typePassword(pass);

		if (!"".equals(confPass))
			typeConfirmPassword(confPass);

		checkSignMeUp(isSignMeUpChecked);

		clickRegistration();
		getCurrentFunctionName(false);

	}

	public static String getEmailAddressError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Email Address Error"));
		subStrArr.add(RegistrationSelectors.emailAddressError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getConfirmEmailAddressError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Confirm Email Address Error"));
		subStrArr.add(RegistrationSelectors.checkEmailError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	
	public static String getFirstNameError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "First Name Error"));
		subStrArr.add(RegistrationSelectors.firstNameError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();

	}

	public static String getLastNameError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Last Name Error"));
		subStrArr.add(RegistrationSelectors.lastNameError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();

	}

	public static String getCountryError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Country Error"));
		subStrArr.add(RegistrationSelectors.countryError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getPostalCodeError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Postal Code Error"));
		subStrArr.add(RegistrationSelectors.postalCodeError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getPasswordError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Password Error"));
		subStrArr.add(RegistrationSelectors.passwordError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getConfirmPasswordError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Confirm Password Error"));
		subStrArr.add(RegistrationSelectors.confirmPasswordError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getPasswordRulesError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Password Rules Error"));
		subStrArr.add(RegistrationSelectors.passwordRulesError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getPasswordMatchError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Password Mismatch Error"));
		subStrArr.add(RegistrationSelectors.passwordMatchError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getRandomTitle() {
		String[] titles = { "Mr.", "Mrs.", "Miss", "Ms.", "Dr.", "Rev." };
		Random random = new Random();
		return titles[random.nextInt(titles.length)];
	}

	public static void verifyRegistrationFormErrors() throws Exception {
		getCurrentFunctionName(true);
		getEmailAddressError();
		getConfirmEmailAddressError();
		getFirstNameError();
		getLastNameError();
		getCountryError();
		getPostalCodeError();
		getPasswordError();
		getConfirmPasswordError();
		getPasswordRulesError();
		getCurrentFunctionName(false);
	}

	public static String getRegistrationSuccessMessage() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GETTING_TEXT, "Account created success message"));
		subStrArr.add(RegistrationSelectors.registrationSuccess);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();

	}
}
