package com.generic.tests.checkout;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.generic.page.PDP;
import com.generic.page.cart;
import com.generic.page.checkOut;
import com.generic.page.signIn;
import com.generic.report.ReportUtil;
import com.generic.setup.Common;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.TestUtilities;

public class checkout_validation extends SelTestCase {

	private static int testCaseID;

	static List<String> subStrArr = new ArrayList<String>();
	static List<String> valuesArr = new ArrayList<String>();

	@BeforeClass
	public static void initialSetUp() throws Exception {
		tempTCID = SheetVariables.checkoutTestCaseId + "_" + testCaseID;
		caseIndex = 2;
		TestUtilities.initialize();
	}

	
	@Test
	public void signIn() throws Exception {
		setStartTime(ReportUtil.now(time_date_format));

		try {
			
			/*//PDP
			//getDriver().get("https://hybrisdemo.conexus.co.uk:9002/yacceleratorstorefront/en/Categories/Bags%2BBoardbags/Bags/Seizure-Satchel/p/300613490");
			getDriver().get("https://hybrisdemo.conexus.co.uk:9002/yacceleratorstorefront/en/Brands/Toko/Snowboard-Ski-Tool-Toko-Waxremover-HC3-500ml/p/45572");
			//PDP.addProductsToCart("brown", "                                                 SIZE UNI, �34.79  15", "5"); 
			PDP.addProductsToCart("","", "5");
			//PDP */
			
			//CART
			//getDriver().get("https://hybrisdemo.conexus.co.uk:9002/yacceleratorstorefront/en/cart");
			//cart.clickCheckout();
			//cart.clickContinueShopiing();
			//cart.getNumberOfproducts();
			//cart.ordarTotal();
			//cart.ordarSubTotal();
			//cart.applyCoupon("Coupon name");
			
			//signin
			//getDriver().get("https://hybrisdemo.conexus.co.uk:9002/yacceleratorstorefront/en/login");
			//signIn.logIn("ibatta@dbi.com", "1234567");
			
			//checkout pages - shipping Address
			signIn.logIn("ibatta@dbi.com", "1234567");
			getDriver().get("https://hybrisdemo.conexus.co.uk:9002/yacceleratorstorefront/en/checkout/");
			signIn.logIn("ibatta@dbi.com", "1234567");
			
			checkOut.shippingAddress.fillAndClickNext("United Kingdom", "Mr.","Accept", "Tester",
					"49 Featherstone Street", "LONDON", "EC1Y 8SY", "545452154");
			
			//checkout pages- shipping method
			checkOut.shippingMethod.fillAndclickNext("PREMIUM DELIVERY");
			
			//checkout pages - payment information
			checkOut.paymentInnformation.fillAndclickNext("VISA", "Accept", "4111111111111111", "4", "2020", "333",true);
			
			//checkout pages- review information/ place order
			checkOut.reviewInformation.getSubtotal();
			checkOut.reviewInformation.shippingCost();
			checkOut.reviewInformation.gettotal(); 
			checkOut.reviewInformation.acceptTerms(true);
			checkOut.reviewInformation.placeOrder();
			
			Common.testPass();
		} catch (Throwable t) {
			setTestCaseDescription(getTestCaseDescription());
			logs.debug(t.getMessage());
			t.printStackTrace();
			String temp = getTestCaseId();
			Common.testFail(t, temp);
			Common.takeScreenShot();
			Assert.assertTrue(t.getMessage(), false);
		}

	}

}