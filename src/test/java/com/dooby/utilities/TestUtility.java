package com.dooby.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;
import com.dooby.base.TestBase;

public class TestUtility extends TestBase {

	public static String screenshotName;
	public static String screenshotPath;

	public static void screenCapture() throws IOException {

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();

		screenshotName = driver.getTitle().toString().replace(" ", "_")
				+ d.toString().replace(" ", "_").replace(":", "_") + ".jpg";

		screenshotPath = System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName;

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

	}

	public static boolean isTestRunnable(String testName, ExcelReader excel) {

		String sheetName = "test_suite";

		int rows = excel.getRowCount(sheetName);
		

		for (int rNum = 2; rNum < rows; rNum++) {

			String testcase = excel.getCellData(sheetName, "TCID", rNum);

			if (testcase.equalsIgnoreCase(testName)) {

				String runmode = excel.getCellData(sheetName, "TCID", rNum);

				if (testcase.equalsIgnoreCase("Y")) {

					return true;

				} else {

					return false;
				}

			}
		}

		return false;
	}

	@DataProvider(name = "podu")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		
		System.out.println(sheetName + "  " + rows+ "  " +cols );

		Object[][] data = new Object[rows - 1][1];

		Hashtable<String,String> table = null;

		for (int rNum = 2; rNum <= rows; rNum++) {

			table = new Hashtable<String, String>();

			for (int cNum = 0; cNum < cols; cNum++) {

				table.put(excel.getCellData(sheetName, cNum, 1), excel.getCellData(sheetName, cNum, rNum));

				data[rNum - 2][0] = table;
			}
		}

		System.out.println("In Tets Utility to check data ::::: " + data[0][0]);
		return data;

	}

}
