package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.generic.selector.MyAccount_Selectors_InProccess;
import com.generic.setup.SelTestCase;
import com.generic.setup.LoggingMsg;
import com.generic.util.SelectorUtil;

public class MyAccount extends SelTestCase {

	public static class keys {
		public static final String caseId = "caseId";
	}

	public static String getNameValue() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.emailInput));
		subStrArr.add(MyAccount_Selectors_InProccess.Name);
		valuesArr.add("");
		String name = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		name = SelectorUtil.textValue.get();
		logs.debug("The user name found in my account page: " + name);
		getCurrentFunctionName(false);
		return name;
	}

	public static String getEmailValue() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.Email));
		subStrArr.add(MyAccount_Selectors_InProccess.Email);
		valuesArr.add("index,1");
		String email = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		email = SelectorUtil.textValue.get();
		logs.debug("The user email found in my account page: " + email);
		getCurrentFunctionName(false);
		return email;
	}

	public static void clickChangePasswordBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.changePasswordBtn));
		subStrArr.add(MyAccount_Selectors_InProccess.changePasswordBtn);
		valuesArr.add("index,2");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static class passwordForm {

		public static void typeCurrentPassword(String password) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.currentPasswordInput, password));
			subStrArr.add(MyAccount_Selectors_InProccess.currentPasswordInput);
			valuesArr.add(password);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void typeNewPassword(String password) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.newPasswordInput, password));
			subStrArr.add(MyAccount_Selectors_InProccess.newPasswordInput);
			valuesArr.add(password);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void typeCheckNewPassword(String password) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.checkNewPasswordInput, password));
			subStrArr.add(MyAccount_Selectors_InProccess.checkNewPasswordInput);
			valuesArr.add(password);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void clickCancelBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.cancelPasswordForm));
			subStrArr.add(MyAccount_Selectors_InProccess.cancelPasswordForm);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void clickSaveBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.savePasswordForm));
			subStrArr.add(MyAccount_Selectors_InProccess.savePasswordForm);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
	}

	public static class editProfile {
		
		public static void typeFirstName(String fName) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.profileFName, fName));
			subStrArr.add(MyAccount_Selectors_InProccess.profileFName);
			valuesArr.add(fName);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static void typeLasttName(String lName) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.profileLName, lName));
			subStrArr.add(MyAccount_Selectors_InProccess.profileLName);
			valuesArr.add(lName);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static void typeNewEmail(String email) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.profileNewEmail, email));
			subStrArr.add(MyAccount_Selectors_InProccess.profileNewEmail);
			valuesArr.add(email);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static void typeCheckEmail(String email) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.profileCheckEmail, email));
			subStrArr.add(MyAccount_Selectors_InProccess.profileCheckEmail);
			valuesArr.add(email);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static void typePassword(String password) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
					MyAccount_Selectors_InProccess.profilePassword, password));
			subStrArr.add(MyAccount_Selectors_InProccess.profilePassword);
			valuesArr.add(password);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
	
		public static void clickCancelBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.cancelEditProfileLink));
			subStrArr.add(MyAccount_Selectors_InProccess.cancelEditProfileLink);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void clickSaveBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.saveEditProfileBtn));
			subStrArr.add(MyAccount_Selectors_InProccess.saveEditProfileBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
	}
	public static class orderDetails {

		public static String getOrderNumber() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.orderLink));
			subStrArr.add(MyAccount_Selectors_InProccess.orderLink);
			valuesArr.add("");
			String orderLink = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			orderLink = SelectorUtil.textValue.get();
			logs.debug("The firt order found in my account page: " + orderLink);
			getCurrentFunctionName(false);
			return orderLink;
		}

		public static String getOrderDate() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.orderDate));
			subStrArr.add(MyAccount_Selectors_InProccess.orderDate);
			valuesArr.add("indes,4");
			String orderDate = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			orderDate = SelectorUtil.textValue.get();
			logs.debug("The date of firt order found in my account page: " + orderDate);
			getCurrentFunctionName(false);
			return orderDate;
		}

		public static String getOrderTrackingNumber() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_Selectors_InProccess.orderTrackingNumber));
			subStrArr.add(MyAccount_Selectors_InProccess.orderTrackingNumber);
			valuesArr.add("indes,5");
			String orderTrackingNumber = "";
			try {
				SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
				orderTrackingNumber = SelectorUtil.textValue.get();
				logs.debug("The tracking number of firt order found in my account page: " + orderTrackingNumber);
			} catch (Exception e) {
				logs.debug("The order is not yet shipped");
			}
			getCurrentFunctionName(false);
			return orderTrackingNumber;
		}
	}

	public static class paymentDetails {

		public static String getPaymentCard() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_Selectors_InProccess.paymentCard));
			subStrArr.add(MyAccount_Selectors_InProccess.paymentCard);
			valuesArr.add("");
			String paymentCard = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			paymentCard = SelectorUtil.textValue.get();
			logs.debug("The first payment card displayed in my account page: " + paymentCard);
			getCurrentFunctionName(false);
			return paymentCard;
		}
		
		public static int getNumberOfPaymentCards() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_Selectors_InProccess.paymentCard));
			subStrArr.add(MyAccount_Selectors_InProccess.paymentCard);
			valuesArr.add("");
			int numberOfPaymentCards;
			try {

				SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
				logs.debug(MessageFormat.format(LoggingMsg.ACTUAL_TEXT, SelectorUtil.numberOfFoundElements.get()));
				numberOfPaymentCards = Integer.parseInt(SelectorUtil.numberOfFoundElements.get());
			} catch (Exception e) {
				logs.debug("No Saved Cards");
				numberOfPaymentCards = 0;
			}
			getCurrentFunctionName(false);
			return numberOfPaymentCards;
		}

		public static void clickRemovePaymentCardBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.removePaymentCardBtn));
			subStrArr.add(MyAccount_Selectors_InProccess.removePaymentCardBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static void confirmRemovePaymentCard() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(
					MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.removePaymentBtn));
			subStrArr.add(MyAccount_Selectors_InProccess.removePaymentBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		public static void setPaymentCardAsDefaultBtnBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL,
					MyAccount_Selectors_InProccess.setPaymentCardAsDefaultBtn));
			subStrArr.add(MyAccount_Selectors_InProccess.setPaymentCardAsDefaultBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

	}

	public static class addressBookDetails {

		public static void clickAddAddressBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.addAddress));
			subStrArr.add(MyAccount_Selectors_InProccess.addAddress);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static String getFirstAddressDetails() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
					MyAccount_Selectors_InProccess.addressDetails));
			subStrArr.add(MyAccount_Selectors_InProccess.addressDetails);
			valuesArr.add("");
			String addressDetails = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			addressDetails = SelectorUtil.textValue.get();
			logs.debug("The first shipping address displayed in my account page: " + addressDetails);
			getCurrentFunctionName(false);
			return addressDetails;
		}

		public static void clickEditAddressBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.editAddress));
			subStrArr.add(MyAccount_Selectors_InProccess.editAddress);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void clickRemoveAddressBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.removeAddress));
			subStrArr.add(MyAccount_Selectors_InProccess.removeAddress);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static void confirmRemoveAddress() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.removeAddressBtn));
			subStrArr.add(MyAccount_Selectors_InProccess.removeAddressBtn);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		public static void typeFirstName(String firstName) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "firstname ", firstName));
			subStrArr.add(MyAccount_Selectors_InProccess.firstName);
			valuesArr.add(firstName);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);

		}

		public static void typeAddress(String address) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "address ", address));
			subStrArr.add(MyAccount_Selectors_InProccess.address);
			valuesArr.add(address);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);

		}

		public static void typeLastName(String lastName) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "lastname ", lastName));
			subStrArr.add(MyAccount_Selectors_InProccess.lastName);
			valuesArr.add(lastName);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);

		}

		public static void typeCity(String city) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "city ", city));
			subStrArr.add(MyAccount_Selectors_InProccess.city);
			valuesArr.add(city);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);

		}

		public static void selectCountery(String countery) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.SELECTING_ELEMENT_VALUE, "Countery ", countery));
			subStrArr.add(MyAccount_Selectors_InProccess.countery);
			valuesArr.add(countery);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			Thread.sleep(1500);
			getCurrentFunctionName(false);

		}

		public static void selectState(String state) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.SELECTING_ELEMENT_VALUE, "state ", state));
			subStrArr.add(MyAccount_Selectors_InProccess.state);
			valuesArr.add(state);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void typePostalCode(String postal) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "postal", postal));
			subStrArr.add(MyAccount_Selectors_InProccess.postal);
			valuesArr.add(postal);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);

		}

		public static void typePhone(String phone) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, "phone", phone));
			subStrArr.add(MyAccount_Selectors_InProccess.phone);
			valuesArr.add(phone);
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);

		}

		public static void checkDefaultAddress(boolean check) throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(LoggingMsg.SAVING_ADDRESS);
			subStrArr.add(MyAccount_Selectors_InProccess.defaultAddressBox);
			valuesArr.add(String.valueOf(check));
			try {
				SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			} catch (Exception e) {
				logs.debug("This the default address address");
			}
			getCurrentFunctionName(false);

		}

		public static void clickSaveBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.saveAddressDetails));
			subStrArr.add(MyAccount_Selectors_InProccess.saveAddressDetails);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}

		public static void clickCancelBtn() throws Exception {
			getCurrentFunctionName(true);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.cancelAddressDetails));
			subStrArr.add(MyAccount_Selectors_InProccess.cancelAddressDetails);
			valuesArr.add("");
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			getCurrentFunctionName(false);
		}
		
		public static String getAlertMsg() throws Exception {
			getCurrentFunctionName(true);
			Thread.sleep(1000);
			List<String> subStrArr = new ArrayList<String>();
			List<String> valuesArr = new ArrayList<String>();
			logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.alertInfo));
			subStrArr.add(MyAccount_Selectors_InProccess.alertInfo);
			valuesArr.add("");
			String alertMsg = "";
			SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
			alertMsg = SelectorUtil.textValue.get();
			getCurrentFunctionName(false);
			return alertMsg;
		}

		public static void fillAndClickSave(String firstName, String lastName,String addressLine, String city, String countery,
				String state, String postal, String phone, boolean defaultAddress) throws Exception {
			
			typeFirstName(firstName);
			typeLastName(lastName);
			typeAddress(addressLine);
			typeCity(city);
            selectCountery(countery);
			selectState(state);
			typePostalCode(postal);
		    typePhone(phone);
			checkDefaultAddress(defaultAddress);
			clickSaveBtn();
			
		}
	}
	
	
	
	
	
	
	
	
	
	public static void clickUpdateBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SEL, MyAccount_Selectors_InProccess.updateBtn));
		subStrArr.add(MyAccount_Selectors_InProccess.updateBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typeEmail(String email) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, MyAccount_Selectors_InProccess.emailInput,
				email));
		subStrArr.add(MyAccount_Selectors_InProccess.emailInput);
		valuesArr.add(email);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typeConfirmEmail(String checkEmail) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE,
				MyAccount_Selectors_InProccess.confirmEmailInput, checkEmail));
		subStrArr.add(MyAccount_Selectors_InProccess.confirmEmailInput);
		valuesArr.add(checkEmail);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void typePassword(String password) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.TYPING_ELEMENT_VALUE, MyAccount_Selectors_InProccess.passwordInput,
				password));
		subStrArr.add(MyAccount_Selectors_InProccess.passwordInput);
		valuesArr.add(password);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void fillInNewValuesAndClickUpdateOrCancel(String newEmail, String confirmEmail, String password,
			boolean doUpdate, boolean doCancel) throws Exception {
		getCurrentFunctionName(true);
		typeEmail(newEmail);
		typeConfirmEmail(confirmEmail);
		typePassword(password);
		if (doUpdate) {
			clickUpdateBtn();
		}
		if (doCancel) {
			clickCancelBtn();
		}
		getCurrentFunctionName(false);
	}

	private static void clickCancelBtn() {
		// TODO Auto-generated method stub
		
	}

	public static String getEmailErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.emailError));
		subStrArr.add(MyAccount_Selectors_InProccess.emailError);
		valuesArr.add("noClick");
		String emailErrorMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		emailErrorMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return emailErrorMsg;
	}

	public static String getConfirmEmailErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,
				MyAccount_Selectors_InProccess.confirmEmailError));
		subStrArr.add(MyAccount_Selectors_InProccess.confirmEmailError);
		valuesArr.add("noClick");
		String confirmEmailErrorMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		confirmEmailErrorMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return confirmEmailErrorMsg;
	}

	public static String getPasswordErrorMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(
				MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.passwordError));
		subStrArr.add(MyAccount_Selectors_InProccess.passwordError);
		valuesArr.add("noClick");
		String passwordErrorMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		passwordErrorMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return passwordErrorMsg;
	}

	public static String getGlobalAlertsMsg() throws Exception {
		getCurrentFunctionName(true);
		Thread.sleep(1000);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(
				MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR, MyAccount_Selectors_InProccess.globalAlerts));
		subStrArr.add(MyAccount_Selectors_InProccess.globalAlerts);
		valuesArr.add("");
		String globalAlertMsg = "";
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		globalAlertMsg = SelectorUtil.textValue.get();
		getCurrentFunctionName(false);
		return globalAlertMsg;
	}
}
