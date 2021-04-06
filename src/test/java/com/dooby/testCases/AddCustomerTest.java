package com.dooby.testCases;

import java.util.Hashtable;

import org.junit.Assert;
import org.openqa.selenium.Alert;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.dooby.base.TestBase;
import com.dooby.utilities.TestUtility;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtility.class, dataProvider = "podu")
	public void addCustomerTest(Hashtable<String,String>hashData)
			throws InterruptedException {
		Thread.sleep(2000);
		System.out.println("Getting hashData from HashTable ::::: " +hashData.get("runmode") +" : " + hashData.get("Fname")+" : " + hashData.get("Lastname") +" : "+  hashData.get("Postcode") +" : "+ hashData.get("AlertText") );
		
		if(!hashData.get("runmode").equals("Y")) {
			
			throw new SkipException("Skipping the test case as the Run mode for data set is NO !!!");
			
		}
		
	
		iClick("addCustbtn_CSS");
		iType("firstname_CSS", hashData.get("Fname"));  
		iType("postcode_CSS", hashData.get("Postcode"));
		iType("lastname_CSS", hashData.get("Lastname"));
		iClick("addBtn_XPATH");
		Thread.sleep(3000);
		Alert alert = wwait.until(ExpectedConditions.alertIsPresent());

		Assert.assertTrue(alert.getText().contains(hashData.get("AlertText")));
		Thread.sleep(2000);
		alert.accept();
		
	//	Assert.fail("Failing intentionally");	
		
	//	Thread.sleep(2000);

	

	}

}
