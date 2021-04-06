package com.dooby.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.dooby.base.TestBase;
import com.dooby.utilities.MonitoringMail;
import com.dooby.utilities.TestConfig;
import com.dooby.utilities.TestUtility;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener,ISuiteListener {
	
	public String msgBody;

	public void onTestStart(ITestResult result) {

		test = exrepo.startTest(result.getName().toUpperCase());

	}

	public void onTestSuccess(ITestResult result) {

		test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
		exrepo.endTest(test);
		exrepo.flush();

		try {
			TestUtility.screenCapture();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	//	System.out.println("Inside fuk3");
		
		/*
		 * Reporter.log("Click to see the Screenshot");
		 * Reporter.log("<a target=\"_blank\" href=" + TestUtility.screenshotName +
		 * ">Screenshot</a>"); Reporter.log("<br>"); Reporter.log("<br>");
		 * Reporter.log("<a target=\"_blank\" href=" + TestUtility.screenshotName +
		 * "><img src=" + TestUtility.screenshotName +
		 * " height = 400 width = 600></img></a>");
		 */
		 

	}

	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		try {
			TestUtility.screenCapture();
			//System.out.println("Where aare u" + " " + TestUtility.screenshotName + " : Path :" + TestUtility.screenshotPath);
		} catch (IOException e) {

			e.printStackTrace();
		}

		test.log(LogStatus.FAIL, result.getName().toUpperCase() + " Failed with Exception: " + result.getThrowable());

		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtility.screenshotName));
		

		Reporter.log("Click to see the Screenshot");
		Reporter.log("<a target=\"_blank\" href=" + TestUtility.screenshotPath + ">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=" + TestUtility.screenshotPath + "><img src="
				+ TestUtility.screenshotPath + " height = 500 width = 700></img></a>");

		exrepo.endTest(test);
		exrepo.flush();

	}

	public void onTestSkipped(ITestResult result) {
		
		test.log(LogStatus.SKIP, result.getName().toUpperCase() + " The Test is skipped because flag is set to NO");
	//	System.out.println("Inside Test Skipped" );
		exrepo.endTest(test);
		exrepo.flush();
	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {
		
		MonitoringMail mail = new MonitoringMail();

		
		try {
			msgBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenLiveProject/Extent_20Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, msgBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
