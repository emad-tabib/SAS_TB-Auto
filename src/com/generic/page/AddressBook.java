package com.generic.page;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.generic.selector.AddressBookSelectors;
import com.generic.setup.SelTestCase;
import com.generic.page.CheckOut.shippingAddress;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.util.SelectorUtil;

public class AddressBook extends SelTestCase {

	// public static void clickmyaccount() throws Exception {
	// getCurrentFunctionName(true);
	// List<String> subStrArr = new ArrayList<String>();
	// List<String> valuesArr = new ArrayList<String>();
	// logs.debug(MessageFormat.format(LoggingMsg.CLICKING_MYACCOUNT_BUTTON, "My
	// Account"));
	// subStrArr.add(HomePage.myaccountBtn);
	// valuesArr.add("");
	// SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
	// getCurrentFunctionName(false);
	//
	// }

	// public static void clickaddressbook() throws Exception {
	// getCurrentFunctionName(true);
	// List<String> subStrArr = new ArrayList<String>();
	// List<String> valuesArr = new ArrayList<String>();
	// logs.debug(MessageFormat.format(LoggingMsg.CLICKING_ADDRESS_BOOK_BUTTON,
	// "Address Book"));
	// subStrArr.add(HomePage.addressbooktBtn);
	// valuesArr.add("");
	// SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
	// getCurrentFunctionName(false);
	//
	// }

	public static void clickSetAsDefault() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SETASDEFAULT_BUTTON, "Set as default"));
		subStrArr.add(AddressBookSelectors.setasdefaultBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void clickRemoveAddress(int index) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_REMOVE_BUTTON, "remove address"));
		subStrArr.add(AddressBookSelectors.removeAddress);
		valuesArr.add("index,"+index);
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void clickEditAddress() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_Edit_BUTTON, "edit address"));
		subStrArr.add(AddressBookSelectors.editAddress);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static String getFirstAddressDetails() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(AddressBookSelectors.addressDetail);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ADDRESS_DETAIL, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getAlertInfo() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		Thread.sleep(500);
		subStrArr.add(AddressBookSelectors.alertInfo);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.DEFAULT_ADDRESS_UPDATE_MESSAGE, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static void clickAddNewAddress() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_ADD_ADDRESS_BUTTON, "add address"));
		subStrArr.add(AddressBookSelectors.addNewAddress);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void updateAddress() throws Exception {
		getCurrentFunctionName(true);
		shippingAddress.selectCountery("Ardenham Court");
	//	shippingAddress.selectTitle("MR.");
		shippingAddress.typeFirstName("Accept");
		shippingAddress.typeLastName("Tester");
		shippingAddress.typeAddress("Ardenham Court");
		shippingAddress.typeCity("LONDON");
		shippingAddress.typePostalCode("HP19 3EQ");
		shippingAddress.typePhone("12345678900");
		clickSave();
		getCurrentFunctionName(false);

	}

	public static void fillAndClickSave(String Countery, String title, String firstName, String lastName,
			String address, String city, String postal, String phone, boolean defaultAddress) throws Exception {
		getCurrentFunctionName(true);

		shippingAddress.selectCountery(Countery);
	//	shippingAddress.selectTitle(title);
		shippingAddress.typeFirstName(firstName);
		shippingAddress.typeLastName(lastName);
		shippingAddress.typeAddress(address);
		shippingAddress.typeCity(city);
		shippingAddress.typePostalCode(postal);
		shippingAddress.typePhone(phone);
		if (defaultAddress)
			checkSetAsDefaultAddress(defaultAddress);
		Thread.sleep(1000);
		clickSave();

		getCurrentFunctionName(false);
	}

	public static void fillAndClickSave(String firstName, String lastName,
			String address,String Countery, String city, String state, String postal, String phone) throws Exception {
		getCurrentFunctionName(true);

		shippingAddress.selectCountery(Countery);
		shippingAddress.selectState(state);
		shippingAddress.typeFirstName(firstName);
		shippingAddress.typeLastName(lastName);
		shippingAddress.typeAddress(address);
		shippingAddress.typeCity(city);
		shippingAddress.typePostalCode(postal);
		shippingAddress.typePhone(phone);
		Thread.sleep(1000);
		clickSave();

		getCurrentFunctionName(false);
	}
	
	public static void clickSave() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_SAVE_ADDRESS_BUTTON, "save address"));
		subStrArr.add(AddressBookSelectors.saveAddress);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void clickAddressBackBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_BACK_TO_ADDRESSES_BUTTON, "back to addresses"));
		subStrArr.add(AddressBookSelectors.addressBackBtn);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static String getAddressBookList() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(AddressBookSelectors.accountAddressbookList);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static void clickDeleteBtn() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_DELETE_BUTTON, "confirm delete address"));
		subStrArr.add(AddressBookSelectors.deleteaddress);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);
	}

	public static void checkSetAsDefaultAddress(boolean check) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.CLICKING_MAKE_THIS_MY_DEFAULT_ADDRESS_CHECKBOX,
				"MAKE THIS MY DEFAULT ADDRESS"));
		subStrArr.add(AddressBookSelectors.defaultAddress);
		valuesArr.add(String.valueOf(check));
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		getCurrentFunctionName(false);

	}

	public static void clearAddress() throws Exception {
		getCurrentFunctionName(true);
		shippingAddress.typeFirstName("");
		shippingAddress.typeLastName("");
		shippingAddress.typeAddress("");
		shippingAddress.typeCity("");
		shippingAddress.typePostalCode("");
		shippingAddress.typePhone("");
		clickSave();
		getCurrentFunctionName(false);

	}

	public static void verifyAddressFormError() throws Exception {
		getCurrentFunctionName(true);
		getFirstNameError();
		getLastNameError();
		getAddress1Error();
		getCityError();
		getPostCodeEerror();
		getCurrentFunctionName(false);

	}

	private static String getFirstNameError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,AddressBookSelectors.fnameError));
		subStrArr.add(AddressBookSelectors.fnameError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	private static String getLastNameError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,AddressBookSelectors.lnameError));
		subStrArr.add(AddressBookSelectors.lnameError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	private static String getAddress1Error() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,AddressBookSelectors.address1Error));
		subStrArr.add(AddressBookSelectors.address1Error);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	private static String getCityError() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,AddressBookSelectors.cityError));
		subStrArr.add(AddressBookSelectors.cityError);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	private static String getPostCodeEerror() throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		logs.debug(MessageFormat.format(LoggingMsg.GET_ELEMENT_BY_LOCATOR,AddressBookSelectors.postcodeEerror));
		subStrArr.add(AddressBookSelectors.postcodeEerror);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ERROR_MSG, SelectorUtil.textValue.get()));
		getCurrentFunctionName(false);
		return SelectorUtil.textValue.get();
	}

	public static String getNumberOfAddresses(String selc) throws Exception {
		getCurrentFunctionName(true);
		List<String> subStrArr = new ArrayList<String>();
		List<String> valuesArr = new ArrayList<String>();
		subStrArr.add(selc);
		valuesArr.add("");
		SelectorUtil.initializeSelectorsAndDoActions(subStrArr, valuesArr);
		logs.debug(MessageFormat.format(LoggingMsg.ACTUAL_TEXT,SelectorUtil.numberOfFoundElements));
		getCurrentFunctionName(false);
		return SelectorUtil.numberOfFoundElements.get();
	}
}
