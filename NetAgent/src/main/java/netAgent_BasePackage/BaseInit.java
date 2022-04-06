package netAgent_BasePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseInit {

	public static ResourceBundle rb = ResourceBundle.getBundle("config");
	public static StringBuilder msg = new StringBuilder();
	public static WebDriver Driver;
	public static Properties storage = new Properties();
	String baseUrl = rb.getString("URL");

	// public static GenerateData genData;
	public static String SuccMsgReplnsh;
	public static String WOID;
	public static String WOTP;

	public static String PUId, JobId, Client, FSLName, Agent;
	public static String Part1, Part1Name, Part2, Part2Name, P2Field2, P2Field3, P2Field4, P2Field5;
	public static String LOCCode1, LOC1LEN, LOC1WID, LOC1HGT, LOCCode2, LOC2Part;

	public static Logger logger;
	public static ExtentReports report;
	public static ExtentTest test;

	@BeforeSuite
	public void beforeMethod() throws IOException {
		if (Driver == null) {
			String logFilename = this.getClass().getSimpleName();
			logger = Logger.getLogger(logFilename);
			startTest();
			storage = new Properties();
			FileInputStream fi = new FileInputStream(".\\src\\main\\resources\\config.properties");
			storage.load(fi);
			logger.info("initialization of the properties file is done");

			// --Opening Chrome Browser
			DesiredCapabilities capabilities = new DesiredCapabilities();
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			// options.addArguments("headless");
			options.addArguments("--incognito");
			options.addArguments("--test-type");
			options.addArguments("--no-proxy-server");
			options.addArguments("--proxy-bypass-list=*");
			options.addArguments("--disable-extensions");
			options.addArguments("--no-sandbox");
			options.addArguments("--start-maximized");

			// options.addArguments("--headless");
			// options.addArguments("window-size=1366x788");
			capabilities.setPlatform(Platform.ANY);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			Driver = new ChromeDriver(options);
			// Default size
			Dimension currentDimension = Driver.manage().window().getSize();
			int height = currentDimension.getHeight();
			int width = currentDimension.getWidth();
			System.out.println("Current height: " + height);
			System.out.println("Current width: " + width);
			System.out.println("window size==" + Driver.manage().window().getSize());

		}
	}

	@BeforeMethod
	public void testMethodName(Method method) {

		String testName = method.getName();
		test = report.startTest(testName);

	}

	public static void startTest() {
		// You could find the xml file below. Create xml file in your project and copy
		// past the code mentioned below

		System.setProperty("extent.reporter.pdf.start", "true");
		System.setProperty("extent.reporter.pdf.out", "./Report/PDFExtentReport/ExtentPDF.pdf");

		// report.loadConfig(new File(System.getProperty("user.dir")
		// +"\\extent-config.xml"));
		report = new ExtentReports("./Report/ExtentReport/ExtentReportResults.html", true);
		// test = report.startTest();
	}

	public static void endTest() {
		report.endTest(test);
		report.flush();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Report/NA_Screenshot/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static String getFailScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Report/FailedTestsScreenshots/" + screenshotName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static WebElement isElementPresent(String propkey) {

		try {

			if (propkey.contains("xpath")) {

				return Driver.findElement(By.xpath(storage.getProperty(propkey)));

			} else if (propkey.contains("id")) {

				return Driver.findElement(By.id(storage.getProperty(propkey)));

			} else if (propkey.contains("name")) {

				return Driver.findElement(By.name(storage.getProperty(propkey)));

			} else if (propkey.contains("linkText")) {

				return Driver.findElement(By.linkText(storage.getProperty(propkey)));

			} else if (propkey.contains("className")) {

				return Driver.findElement(By.className(storage.getProperty(propkey)));

			} else if (propkey.contains("cssSelector")) {

				return Driver.findElement(By.cssSelector(storage.getProperty(propkey)));

			} else {

				System.out.println("propkey is not defined");

				logger.info("prop key is not defined");
			}

		} catch (Exception e) {

		}
		return null;

	}

	public static void highLight(WebElement element, WebDriver driver) {
		// for (int i = 0; i < 2; i++) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: black; border: 4px solid red;");
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			// test.log(LogStatus.FAIL, "Test Case Failed is " +
			// result.getThrowable().getMessage());
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this mehtod in to the extent reports using
			// "logger.addScreenCapture" method.
			String screenshotPath = getFailScreenshot(Driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case Pass is " + result.getName());
			String screenshotPath = getScreenshot(Driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.PASS, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
	}

	// --Updated by Ravina
	@BeforeTest
	public void Login() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Driver.get(baseUrl);
		logger.info("Url opened");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
		getScreenshot(Driver, "LoginPage");
		String UserName = storage.getProperty("UserName");
		String password = storage.getProperty("Password");

		// Enter User_name and Password and click on Login
		isElementPresent("UserName_id").clear();
		isElementPresent("UserName_id").sendKeys(UserName);
		isElementPresent("Password_id").clear();
		isElementPresent("Password_id").sendKeys(password);

		isElementPresent("Login_id").click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

		// --Get data from excel by GetData Method
		PUId = getData("Sheet1", 2, 0);
		JobId = getData("Sheet1", 2, 1);
		Client = getData("Sheet1", 2, 2);
		FSLName = getData("Sheet1", 2, 3);
		Agent = getData("Sheet1", 2, 18);

		Part1 = getData("Sheet1", 2, 4);
		Part1Name = getData("Sheet1", 2, 5);
		Part2 = getData("Sheet1", 2, 6);
		Part2Name = getData("Sheet1", 2, 7);
		P2Field2 = getData("Sheet1", 2, 8);
		P2Field3 = getData("Sheet1", 2, 9);
		P2Field4 = getData("Sheet1", 2, 10);
		P2Field5 = getData("Sheet1", 2, 11);

		LOCCode1 = getData("Sheet1", 2, 12);
		LOC1LEN = getData("Sheet1", 2, 13);
		LOC1WID = getData("Sheet1", 2, 14);
		LOC1HGT = getData("Sheet1", 2, 15);
		LOCCode2 = getData("Sheet1", 2, 16);
		LOC2Part = getData("Sheet1", 2, 17);

		Thread.sleep(2000);
		getScreenshot(Driver, "HomeScreen");

	}

	@AfterTest
	public void logOut() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 5000);
		isElementPresent("LogOutDiv_xpath").click();
		isElementPresent("LogOut_linkText").click();
		logger.info("Clicked on LogOut");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "LogOutDiv");
	}

	public void Complete() throws Exception {
		Driver.close();
		Driver.quit();

	}

	public String CuDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current Date :- " + date1);
		return date1;
	}

	public static String getDate(Calendar cal) {
		return "" + cal.get(Calendar.MONTH) + "/" + (cal.get(Calendar.DATE) + 1) + "/" + cal.get(Calendar.YEAR);
	}

	public static Date addDays(Date d, int days) {
		d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
		return d;
	}

	public void scrollToElement(WebElement element, WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void pagination() {
		Actions act = new Actions(Driver);
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		// Check paging
		List<WebElement> pagination = Driver
				.findElements(By.xpath("//*[@class=\"dx-pages\"]//div[contains(@aria-label,'Page')]"));
		System.out.println("size of pagination is==" + pagination.size());

		if (pagination.size() > 0) {
			WebElement pageinfo = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]"));
			System.out.println("page info is==" + pageinfo.getText());
			WebElement pagerdiv = Driver.findElement(By.className("dx-pages"));
			WebElement secndpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 2\"]"));
			WebElement prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
			WebElement nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
			WebElement firstpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 1\"]"));
			// Scroll
			js.executeScript("arguments[0].scrollIntoView();", pagerdiv);

			if (pagination.size() > 1) {
				// click on page 2
				secndpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 2\"]"));
				act.moveToElement(secndpage).click().perform();
				System.out.println("Clicked on page 2");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// click on previous button
				prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
				prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
				act.moveToElement(prevpage).click().perform();
				System.out.println("clicked on previous page");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// click on next button
				nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				act.moveToElement(nextpage).click().perform();
				System.out.println("clicked on next page");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				firstpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 1\"]"));
				act.moveToElement(firstpage).click().perform();
				System.out.println("Clicked on page 1");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} else {
				System.out.println("Only 1 page is exist");
			}

		} else {
			System.out.println("pagination is not exist");
		}
	}

	public static String getData(String sheetName, int row, int col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String FilePath = storage.getProperty("File");

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();
		String Cell = formatter.formatCellValue(sh1.getRow(row).getCell(col));

		return Cell;
	}

	public static int getTotalRow(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String FilePath = storage.getProperty("File");

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		int rowNum = sh1.getLastRowNum() + 1;
		return rowNum;

	}

	public static int getTotalCol(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String FilePath = storage.getProperty("File");

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		Row row = sh1.getRow(0);
		int colNum = row.getLastCellNum();
		return colNum;

	}

	@AfterSuite
	public void SendEmail() throws Exception {
		report.flush();
		// --Close browser
		Complete();
		System.out.println("====Sending Email=====");
		logger.info("====Sending Email=====");
		// Send Details email

		msg.append("*** This is automated generated email and send through automation script ***" + "\n");
		msg.append("Process URL : " + baseUrl+"\n");
		msg.append("Please find attached file of Report and Log");

		String subject = "Selenium Automation Script: Staging NetAgent Portal";
		String File = ".\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgentLog.html";

		try {
//			/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
			SendEmail.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

			// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
			// ,parth.doshi@samyak.com", subject, msg.toString(), File);

		} catch (Exception ex) {
			logger.error(ex);
		}
	}
}