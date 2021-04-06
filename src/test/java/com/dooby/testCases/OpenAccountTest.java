package com.dooby.testCases;



import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.dooby.base.TestBase;
import com.dooby.utilities.TestUtility;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtility.class, dataProvider = "podu")
	public void openAccountTest(Hashtable<String, String>data) throws InterruptedException {
		
		if(!TestUtility.isTestRunnable("openAccountTest", excel)) {
			
			
			throw new SkipException("Skipping the Test Case " + "openAccountTest" + " As the run mode is NO");
		}
		
		iClick("openaccount_CSS");
		System.out.println("Customer: " +  data.get("customer") + "---"+ "currency : " + data.get("currency"));
		iSelect("customer_CSS", data.get("customer"));
		iSelect("currency_CSS",  data.get("currency"));
		iClick("process_CSS");
		
		Thread.sleep(2000);
		
		Alert alert = wwait.until(ExpectedConditions.alertIsPresent());
		System.out.println("alert.getText()");
		alert.accept();
	}

}
