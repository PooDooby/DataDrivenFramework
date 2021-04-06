package com.dooby.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.dooby.utilities.ExcelReader;
import com.dooby.utilities.ExtentManager;
import com.dooby.utilities.TestUtility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.bytebuddy.description.annotation.AnnotationList.Empty;

public class TestBase {

	public static WebDriver driver;
	public static WebDriverWait wwait;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			"C:\\Users\\Dooby\\eclipse-workspace\\Final-Projects\\DataDrivenFramework\\src\\test\\resources\\excel\\testData.xlsx");
	public ExtentReports exrepo = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");

			} catch (IOException e) {

				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		/// browser
		/// Details***********************************************************************************************
		
		if(System.getenv("browser")!= null && !System.getenv("browser").isEmpty()) {
			
			
			browser = System.getenv("browser");
			
		}else {
			
			browser = config.getProperty("browser");
		}
		
		config.setProperty("browser", browser);
		
		if (config.getProperty("browser").equals("firefox")) {

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");

			driver = new FirefoxDriver();
			log.debug("Firefox launched !!!");

		} else if (config.getProperty("browser").equals("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("Chrome launched !!!");
		}

		else if (config.getProperty("browser").equals("ie")) {

			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");

			driver = new InternetExplorerDriver();
			log.debug("ie launched !!!");
		}

		else if (config.getProperty("browser").equals("edge")) {

			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\msedgedriver.exe");

			driver = new EdgeDriver();
			log.debug("edge launched !!!");
		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigateed to :" + config.getProperty("testsiteurl"));

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);

		wwait = new WebDriverWait(driver, 10);

	}

	public void iClick(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();

		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();

		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();

		}

		test.log(LogStatus.INFO, "Clicking on Locator: " + locator);

	}

	public void iType(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);

		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);

		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);

		}

		test.log(LogStatus.INFO, "Typing in : " + locator + "enter the value as " + value);
	}
	
	static WebElement dropdown;
	
	
	
	
	public void iSelect(String locator, String value) {

		if (locator.endsWith("_CSS")) {
		
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			
			System.out.println("in drop down" + dropdown.getText());
			

		} else if (locator.endsWith("_XPATH")) {
			
			dropdown =  driver.findElement(By.xpath(OR.getProperty(locator)));

		} else if (locator.endsWith("_ID")) {
			
			dropdown =  driver.findElement(By.id(OR.getProperty(locator)));

		}
			
		Select select = new Select(dropdown);
		
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown: " + locator + " value as " + value);
	}

	

	public boolean isElementPresent(By by) {

		try {
			System.out.println();

			driver.findElement(by);

			return true;

		} catch (NoSuchElementException e) {

			e.printStackTrace();
			return false;
		}

	}

	public static void verifyEquals(String expected, String Actual) throws IOException {

		try {
			Assert.assertEquals(Actual, expected);
		} catch (Throwable t) {

			TestUtility.screenCapture();

			// This is for Report NG
			Reporter.log("Click to see the Screenshot");
			Reporter.log("<br>" + "Verification Failure: " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtility.screenshotPath + "><img src="
					+ TestUtility.screenshotPath + " height = 400 width = 600></img></a>");

			Reporter.log("<br>");
			Reporter.log("<br>");

			// For ExtentReport

			test.log(LogStatus.FAIL, "Verification failed with Exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtility.screenshotName));
		}

	}

	@AfterSuite
	public void tearDown() throws InterruptedException {

		if (driver != null) {
			driver.quit();
		}

		log.debug("the Login test  execution sucks !!!");

	}

}
