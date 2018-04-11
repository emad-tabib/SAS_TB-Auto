/**
 * this generic test for PDP regression that will pull tests from PDPRegression tab from
 * datasheet.xlsx. 
*/
package com.generic.tests.PDP_Done;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.generic.page.PDP;
import com.generic.page.Registration;
import com.generic.page.SignIn;
import com.generic.setup.Common;
import com.generic.setup.LoggingMsg;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.ReportUtil;
import com.generic.util.SASLogger;
import com.generic.util.dataProviderUtils;

public class Base_PDP extends SelTestCase {

	private static LinkedHashMap<String, Object> invintory = null ;
	private static LinkedHashMap<String, Object> users;

	// user types
	public static final String guestUser = "guest";
	public static final String loggedInUser = "loggedin";

	// used sheet in test
	public static final String testDataSheet = SheetVariables.PDPSheet;

	private static XmlTest testObject;
	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();
	private String email;
	LinkedHashMap<String, Object> productDetails;

	@BeforeClass
	public static void initialSetUp(XmlTest test) throws Exception {
		testCaseRepotId = SheetVariables.PDPCaseId;
		Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
		testObject = test;
		invintory = Common.readLocalInventory();
		users = Common.readUsers();
	}

	@DataProvider(name = "PDPs", parallel = true)
	public static Object[][] loadTestData() throws Exception {
		// concurrency maintenance on sheet reading
		getBrowserWait(testObject.getParameter("browserName"));
		dataProviderUtils TDP = dataProviderUtils.getInstance();
		Object[][] data = TDP.getData(testDataSheet);
		Testlogs.get().debug(Arrays.deepToString(data).replace("\n", "--"));
		return data;
	}

	@SuppressWarnings("unchecked")
	@Test(dataProvider = "PDPs")
	public void checkOutBaseTest(String caseId, String runTest, String desc, String proprties, String product,
			String email, String ValidationMsg) throws Exception {
		// Important to add this for logging/reporting
		Testlogs.set(new SASLogger("PDP_" + getBrowserName()));
		setTestCaseReportName("PDP Case");
		logCaseDetailds(MessageFormat.format(LoggingMsg.CARTDESC, testDataSheet + "." + caseId,
				this.getClass().getCanonicalName(), desc, proprties.replace("\n", "<br>- ")));
		this.email = getSubMailAccount(email);
		try {

			if (proprties.contains("Loggedin")) {
				LinkedHashMap<String, Object> userdetails = (LinkedHashMap<String, Object>) users.get(email);
				Testlogs.get().debug("Used mail to login: "+this.email);
				Testlogs.get().debug("Used Password to login: "+(String) userdetails.get(Registration.keys.password) );
				SignIn.logIn(this.email, (String) userdetails.get(Registration.keys.password));
			}
			LinkedHashMap<String, Object> productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
			Testlogs.get().debug("productDetails" + Arrays.asList(productDetails));
			Testlogs.get().debug("url key " + PDP.keys.url);
			Testlogs.get().debug("url key value " + (String) productDetails.get(PDP.keys.url));
			getDriver().get((String) productDetails.get(PDP.keys.url));

			String Pdesc = PDP.getDesc();
			
			if (proprties.contains("desc") ){
				logs.debug("Expected Product desc: " + (String) productDetails.get(PDP.keys.desc));
				logs.debug("Actual Product desc: " + Pdesc);
				logs.debug("checking PDP desc");
				sassert().assertTrue(Pdesc.contains((String) productDetails.get(PDP.keys.desc)), "<font color=#f442cb>product desc is not expected ("+Pdesc +"\n"+(String) productDetails.get(PDP.keys.desc)+")</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//desc check

			if (proprties.contains("id") ){
				logs.debug("checking PDP ID");
				String Id = Pdesc.split("\n")[7];
				sassert().assertTrue(Id.contains((String) productDetails.get(PDP.keys.id)), "<font color=#f442cb>product id is not expected ("+Id+"\n"+(String) productDetails.get(PDP.keys.id)+")</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//id check
			

			if (proprties.contains("name") ){
				logs.debug("checking PDP title");
				String title = PDP.getTitle();
				sassert().assertTrue(title.contains((String) productDetails.get(PDP.keys.title)), "<font color=#f442cb>product title is not expected("+title+"\n"+(String) productDetails.get(PDP.keys.title)+")</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//title check
			
			if (proprties.contains("price") ){
				logs.debug("checking PDP price");
				String price = PDP.getPrice();
				sassert().assertTrue(price.contains((String) productDetails.get(PDP.keys.price)), "<font color=#f442cb>product price is not expected</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//price check
			
			if (proprties.contains("summary") ){
				logs.debug("checking PDP summary");
				String summary = PDP.getSummary();
				sassert().assertTrue(summary.contains((String) productDetails.get(PDP.keys.summary)), "<font color=#f442cb>product summary is not expected</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//summary check
			
			
			if (proprties.contains("add to cart button") ){
				logs.debug("checking PDP add to cart button");
				sassert().assertTrue(PDP.checkAddToCartButton(), "<font color=#f442cb>product desc is not expected</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//add to cart button check
			
			if (proprties.contains("reviews") ){
				logs.debug("checking PDP reviews");
				String  PReviewsNumber = PDP.getRating();
				Testlogs.get().debug("Actual Number of reviews: "+PReviewsNumber);
				String expectedNumberOfReviews = (String) productDetails.get(PDP.keys.reviews);
				Testlogs.get().debug("Expected Number of reviews: "+ expectedNumberOfReviews);
				sassert().assertTrue(expectedNumberOfReviews.equals(PReviewsNumber), "<font color=#f442cb>product reviews expected/get</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//reviews check
			
			if (proprties.contains("max number of prod") ){
				logs.debug("checking maximum order quantity for a product");
				String expectedQtyTobeAdded = "4";
				String QtyToAdd = "8";
				Testlogs.get().debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
				PDP.addProductsToCart((String) productDetails.get(PDP.keys.url),
						(String) productDetails.get(PDP.keys.color), (String) productDetails.get(PDP.keys.size),
						QtyToAdd);
				String ActualErrorMsg = PDP.getCartPopupError();
				String ActualQtyAdded = PDP.getProductQtyInCartPopyp();
				String failureMessage = MessageFormat.format(LoggingMsg.ACTUAL_EXPECTED_ERROR,
						ActualErrorMsg, ValidationMsg);
				Testlogs.get().debug("Actual error msg: " + ActualErrorMsg);
				Testlogs.get().debug("Actual qty was added: "+ ActualQtyAdded);
				sassert().assertTrue(ActualErrorMsg.contains(ValidationMsg), failureMessage);
				sassert().assertTrue((ActualQtyAdded.charAt(ActualQtyAdded.length()-1)) == expectedQtyTobeAdded.charAt(0), "<font color=#f442cb>product qty is not expected</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//max number of prod check

			if (proprties.contains("product color") ){
				logs.debug("checking product color");
				
				PDP.selectcolor((String) productDetails.get(PDP.keys.color));
				PDP.selectsize((String) productDetails.get(PDP.keys.size));
				
				String displayedColorName = PDP.getVariantSelectedStyleName();	
				String displayedSizeName = PDP.getDisplayedSizeName();
				
				Testlogs.get().debug("Displayed Color Name: " + displayedColorName);
				Testlogs.get().debug("Displayed Size Name: " + displayedSizeName);

				sassert().assertTrue(!displayedSizeName.contains((String) productDetails.get(PDP.keys.size)), "<font color=#f442cb>product size is not updated as expeected</font>");
				sassert().assertTrue(!displayedColorName.contains((String) productDetails.get(PDP.keys.color)), "<font color=#f442cb>product color is not as expeected</font>");
				ReportUtil.takeScreenShot(getDriver());
			}//product size check
			
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

	public void prepareCartNotLoggedInUser(String product) throws Exception {
		logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
		productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
		PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url), (String) productDetails.get(PDP.keys.color),
				(String) productDetails.get(PDP.keys.size), (String) productDetails.get(PDP.keys.qty));
	}

	public void prepareCartLoggedInUser(String user, String product) throws Exception {
		logs.debug(MessageFormat.format(LoggingMsg.SEL_TEXT, user));
		LinkedHashMap<String, Object> userDetails = (LinkedHashMap<String, Object>) users.get(user);
		logs.debug((String) userDetails.get("password"));
		logs.debug((String) userDetails.get("mail"));
		SignIn.logIn(getSubMailAccount((String) userDetails.get("mail")), (String) userDetails.get("password"));

		logs.debug(MessageFormat.format(LoggingMsg.ADDING_PRODUCT, product));
		productDetails = (LinkedHashMap<String, Object>) invintory.get(product);
		PDP.addProductsToCartAndClickCheckOut((String) productDetails.get(PDP.keys.url), (String) productDetails.get(PDP.keys.color),
				(String) productDetails.get(PDP.keys.size), (String) productDetails.get(PDP.keys.qty));

	}

}
