package com.generic.selector;

public class MyAccount_Selectors_InProccess {
	public static final String Name = "css,sub-section.small>dl>dd";
	public static final String Email = "css,sub-section.small>dl>dd";
	public static final String changePasswordBtn = "css,sub-section.small>dl>dd";
	
	public static final String currentPasswordInput = "profile.currentPassword";
	public static final String newPasswordInput = "profile-newPassword";
	public static final String checkNewPasswordInput = "profile.checkNewPassword";
	public static final String cancelPasswordForm = "css,#updatePasswordForm>div>.btn.btn-primary.small-text.modal-exit";
	public static final String savePasswordForm ="changePasswordButton";
	public static final String globalAlerts= "css,#globalMessages>.alert.positive";   // Your password has been changed
	
	//edit-profile
	public static final String editProfileLink = "edit-profile";
	public static final String profileFName = "profile-first-name";
	public static final String profileLName = "profile-last-name";
	public static final String profileNewEmail = "profile-new-email";
	public static final String profileCheckEmail = "profile-check-email";
	public static final String profilePassword = "profile-pwd";
	public static final String saveEditProfileBtn = "#TBUpdateProfileForm>.buttons>button";
	public static final String cancelEditProfileLink = "#TBUpdateProfileForm>.buttons>a";
	//public static final String globalAlerts= "css,#globalMessages>.alert.positive";  //Your profile has been updated
	
	//Order Details
	public static final String orderLink = "account-order-links";
	public static final String orderDate = "css,.orders>dd";
	public static final String orderTrackingNumber = "css,.orders>dd";
	
	//Payment Details
	public static final String paymentCard = "css,.payment-details>.payment-card";
	public static final String removePaymentCardBtn = "css,.payment-anchors-form1>a";
	public static final String removePaymentBtn = "css,#cboxLoadedContent>div>div>div>div>.btn.btn-primary.small-text.remove-payment";
	//public static final String globalAlerts= "css,#globalMessages>.alert.positive";   // Payment Card removed successfully
	public static final String cancelRemovePaymentBtn = "css,#cboxLoadedContent>div>div>div>div>.btn.btn-primary.small-text.modal-exit";
	public static final String setPaymentCardAsDefaultBtn = "set-default-payment btn btn-accent2-dark-gray small-text";
	
	//Address Book Details
	public static final String addAddress = "css,.add-address>input";
	public static final String addressDetails = "css,.address-detail>.address-line";
	public static final String editAddress = "btn btn-accent2-dark-gray small-text edit-address";
	public static final String removeAddress = "btn btn-accent2-dark-gray small-text tb-modal remove-address";
	public static final String removeAddressBtn = "css,#cboxLoadedContent>div>div>div>div>.btn.btn-primary.small-text.remove-address";
	public static final String cancelRemoveAddressBtn = "css,#cboxLoadedContent>div>div>div>div>.btn.btn-primary.small-text.remove-payment-exit";
	public static final String firstName = "address-first-name";
	public static final String lastName = "address-surname";
	public static final String address= "address-line1";
	public static final String city = "address-town-city";
	public static final String countery = "address-country";
	public static final String state = "address-region";
	public static final String postal = "address-postcode";
	public static final String phone = "address-phone";
	public static final String defaultAddressBox = "default-address";
	public static final String saveAddressDetails = "show_processing_message btn btn-accent2-dark-gray small-text";
	public static final String cancelAddressDetails ="btn btn-accent2-dark-gray small-text";
	public static final String fnameError = "firstName.errors";
	public static final String lnameError = "lastName.errors";
	public static final String address1Error = "line1.errors";
	public static final String cityError = "townCity.errors";
	public static final String countryError = "countryIso.errors";
	public static final String stateError = "regionIso.errors";
	public static final String postcodeEerror = "postcode.errors";
	public static final String alertInfo = "alert negative";
	//public static final String globalAlerts= "css,#globalMessages>.alert.positive";   // Your address was updated. 
	
	public static final String emailInput = "profile.email";
	public static final String confirmEmailInput = "profile.checkEmail";
	public static final String passwordInput = "profile.pwd";
	public static final String updateBtn = "btn btn-primary btn-block";
	public static final String cancelBtn = "btn btn-default btn-block backToHome";
	public static final String emailError = "email.errors";
	public static final String confirmEmailError = "chkEmail.errors";
	public static final String passwordError = "password.errors";
	
}