package com.generic.test_runners;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.util.Strings;
import org.testng.xml.XmlTest;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

import com.generic.setup.Common;
import com.generic.setup.SelTestCase;
import com.generic.setup.SheetVariables;
import com.generic.util.SASLogger;
import com.generic.util.TestUtilities;

public class BuildFullRegression extends SelTestCase {
	private static String[] runners = null;
	private static String[] browsers = null;
	private static ArrayList<String> regressionsPathes = new ArrayList<String>();
	private static int testCaseID;
	// used sheet in test
	public static final String testDataSheet = SheetVariables.RunnersRegressionSheet;
	private static XmlTest testObject;

	private static ThreadLocal<SASLogger> Testlogs = new ThreadLocal<SASLogger>();

	@BeforeTest
	public static void initialSetUp(XmlTest test) throws Exception {
		try {
			Testlogs.set(new SASLogger(test.getName() + test.getIndex()));
			testObject = test;
			
			try {
			runners = System.getenv("Runners").split(",");
			}
			catch(Exception e)
			{
				logs.debug("The evn var is not set for runners ... pulling data from sheet");
				runners=Common.readRunners();
			}
			
			if (runners[0].equals("all") )
				runners = "checkoutRegression,addressBookRegression,PaymentDetailsRegression,PDPRegression,PLPRegression,RegistrationRegression,LoginRegression,cartRregression".split(",");
			
			Testlogs.get().debug("Started in Regression");
			
			try
			{
				browsers = System.getenv("Browsers").split(",");
			}catch(Exception e)
			{
				logs.debug("The evn var is not set for browsers ... pulling data from sheet");
				browsers =Common.readBrowsers();
			}
			
			for (String regressionName : runners) {
				logs.debug("Adding runner path to paths: " + regressionName);
				regressionsPathes.add(TestUtilities.searchSpecificFile(
						new File(System.getProperty("user.dir") + "\\src\\com\\generic\\test_runners"),
						regressionName));
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void generateFinalRegressionXML() {
		try {
			TestUtilities.writeFinalXMLRegression(regressionsPathes, browsers);
			Common.testPass();
		} catch (SAXException | IOException | ParserConfigurationException | TransformerFactoryConfigurationError
				| TransformerException e) {
			e.printStackTrace();
		}
	}

}
