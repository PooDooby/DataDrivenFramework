package com.dooby.testCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dooby.base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		
	//	verifyEquals("xyx", "yzy");

		System.out.println("inside try catche");

		Thread.sleep(1000);

		log.debug("Inside the Login test !!!");

		iClick("bmlBtn_CSS");

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustbtn_CSS"))),
				"Login is Not successful");

		log.debug("the Login test  executed successfully !!!");

	//	Assert.fail("Failing the test to test Liteners");

		Thread.sleep(1000);

	}
}
