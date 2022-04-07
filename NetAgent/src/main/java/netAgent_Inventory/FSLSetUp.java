package netAgent_Inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class FSLSetUp extends BaseInit {
	@Test
	public void fSLSetup() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		logger.info("=======FSL SetUp Test Start=======");
		msg.append("=======FSL SetUp Test Start=======" + "\n\n");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idInventory")));
		Driver.findElement(By.id("idInventory")).click();
		logger.info("Clicked on Inventory");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idFSLSetup")));
		Driver.findElement(By.id("idFSLSetup")).click();
		logger.info("Clicked on FSL SetUp");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "FSLSetup");

		// Location Code Search
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("DEFAULTBIN");
		logger.info("Entered Location Code");
		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Select DefaultBin and Try to edit
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfAct")));
		Driver.findElement(By.id("hrfAct")).click();
		logger.info("Clicked on Edit button of DefaultBin");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Boolean locationcode = Driver.findElement(By.id("txtLocationCode")).isEnabled();

		if (locationcode == true) {
			throw new Error("Error: Location Code field is enable");
		}

		Driver.findElement(By.id("idiconsave")).click();
		logger.info("Clicked on Save button");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idiconsave")));
		String Errormsg = Driver.findElement(By.id("errorid")).getText();
		logger.info("Validation message displayed==" + Errormsg);

		getScreenshot(Driver, "FSLSetup");

		// Driver.findElement(By.cssSelector(".btn.btn-primary.no-radius")).click();
		Driver.findElement(By.id("idbtnreset")).click();
		logger.info("Clicked on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Refresh
		Driver.findElement(By.id("hlkCancleContactsDtls")).click();
		logger.info("Clicked on Refresh button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Paging
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("1");
		logger.info("Entered Location Code");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String pagecnt = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
		System.out.println(pagecnt);
		logger.info("Records==" + pagecnt);

		if (pagecnt.contains("Page 1 of 1")) {
			Driver.findElement(By.id("idbtnreset")).click();
			logger.info("If page size=1....");
			logger.info("Clicked on Reset button");
		} else {
			logger.info("If page size>1....");
			Driver.findElement(By.xpath(".//*[@aria-label='Page 2']")).click();
			logger.info("Clicked on Page 2");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.xpath(".//*[@aria-label=' Next page']")).click();
			logger.info("Clicked on Next Page");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.xpath(".//*[@aria-label='Previous page']")).click();
			logger.info("Clicked on Previous page");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.id("idbtnreset")).click();
			logger.info("Clicked on Reset button");
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtFSLbinSearch")));
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("1");
		logger.info("Entered value in Location Code");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("Test1234");
		logger.info("Send invalid value to Location Code");
		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		WebElement Nodata = Driver.findElement(By.className("dx-datagrid-nodata"));
		if (Nodata.isDisplayed()) {
			logger.info("There is no data with entered Location Code");

		} else {
			logger.info("Data is/are exist with  entered Location Code");

		}
		Driver.findElement(By.id("idbtnreset")).click();
		logger.info("Clicked on Reset button");

		// Edit and Save
		// --Get data from excel
		String LocationCode = getData("Sheet1", 2, 27);

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(LocationCode);
		logger.info("Entered Location Code");
		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfAct")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hrfAct")));
		Driver.findElement(By.id("hrfAct")).click();
		logger.info("Select Location Code");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtLength")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logger.info("Select Length");
		Driver.findElement(By.id("txtLength")).sendKeys("10");
		logger.info("Enter value in Length");

		Driver.findElement(By.id("txtWidth")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logger.info("Select Width");
		Driver.findElement(By.id("txtWidth")).sendKeys("2");
		logger.info("Enter Width");

		Driver.findElement(By.id("txtHeight")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logger.info("Select Height");
		Driver.findElement(By.id("txtHeight")).sendKeys("5");
		logger.info("Enter Height");

		Driver.findElement(By.id("hlkSaveASN")).click();
		logger.info("Clicked on Save button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		WebElement SuccMsg = Driver.findElement(By.id("success"));
		if (SuccMsg.isDisplayed()) {
			String SuccMessage = SuccMsg.getText();
			logger.info("Success Message==" + SuccMessage);
		} else {
			logger.info("Record is not saved");
		}

		Driver.findElement(By.id("idbtnreset")).click();
		logger.info("Clicked on Reset");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Manage FSL
		logger.info("==Testing Manage FSL==");
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("DEFAULTBIN");
		logger.info("Entered Location Code=DEFAULTBIN");
		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'Manage')]")));
		Driver.findElement(By.xpath("//a[contains(.,'Manage')]")).click();
		logger.info("Clicked on Manage button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Refresh
		Driver.findElement(By.id("idiconrefresh")).click();
		logger.info("Clicked on Refresh button of Manage FSL page");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// basic search

		for (int col = 28; col < 32; col++) {
			String LocCode = getData("Sheet1", 2, col);
			Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(LocCode);
			logger.info("Enter value in Basic Search");
			Driver.findElement(By.id("idbtnsearch")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String A1 = Driver
					.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]"))
					.getText();
			System.out.println(LocCode);
			logger.info("Entered Location Code==" + LocCode);
			System.out.println(A1);
			logger.info("Searched Location Code==" + A1);

			if (A1.equals(LocCode)) {
				System.out.println("Search Compare PASS");
				logger.info("Search Compare PASS");
			} else {
				System.out.println("Search Compare FAIL");
				logger.info("Search Compare FAIL");
			}

			Driver.findElement(By.id("idbtnreset")).click();
			logger.info("Clicked on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}

		for (int col = 32; col < 36; col++) {
			String LocCode = getData("Sheet1", 2, col);
			Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(LocCode);
			logger.info("Enter value in Basic Search");
			Driver.findElement(By.id("idbtnsearch")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			// --Check data
			Nodata = Driver.findElement(By.className("dx-datagrid-nodata"));
			if (Nodata.isDisplayed()) {
				logger.info("There is no data with entered value");

			} else {
				logger.info("Data is/are exist with  entered value");

			}
			Driver.findElement(By.id("idbtnreset")).click();
			logger.info("Clicked on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}
		// --Enter invalid value
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("Test1234");
		logger.info("Enter invalid value in Basic Search");
		Driver.findElement(By.id("idbtnsearch")).click();
		logger.info("Clicked on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Nodata = Driver.findElement(By.className("dx-datagrid-nodata"));
		if (Nodata.isDisplayed()) {
			logger.info("There is no data with entered value");

		} else {
			logger.info("Data is/are exist with  entered value");

		}

		Driver.findElement(By.id("idbtnreset")).click();
		logger.info("Clicked on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// click save
		Driver.findElement(By.id("hlkSaveASN")).click();
		logger.info("Clicked on Save ");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String Validations = Driver.findElement(By.id("idValidation")).getText();
		logger.info("Validation Messages==" + Validations);

		getScreenshot(Driver, "FSLSetup");

		Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//div[@class=\"dx-checkbox-container\"]"))
				.click();
		logger.info("Check the checkbox of 1st record");
		Thread.sleep(10000);

		// Select To Location
		Driver.findElement(By.id("ddlToLocation")).sendKeys("DEFAULTBIN");
		logger.info("Entered To Location same as current location");

		Driver.findElement(By.id("hlkSaveASN")).click();
		logger.info("Clicked on Save button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String erromsg = Driver.findElement(By.id("errorid")).getText();
		logger.info("Validation is displayed=" + erromsg);

		// --Add valid To Location
		Driver.findElement(By.id("ddlToLocation")).clear();
		logger.info("Cleared To Location");

		Driver.findElement(By.id("ddlToLocation")).sendKeys("ToLocation" + System.currentTimeMillis());
		logger.info("Entered To Location");
		Driver.findElement(By.id("idbtnAdd")).click();
		logger.info("Clicked on Add button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Add Location with blank value
		Driver.findElement(By.id("ddlToLocation")).clear();
		logger.info("Cleared To Location");

		Driver.findElement(By.id("idbtnAdd")).click();
		logger.info("Clicked on Add button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		String errorid = Driver.findElement(By.id("idValidation")).getText();
		logger.info("Validation message is displayed==" + errorid);
		getScreenshot(Driver, "FSLSetup_Locadd_Blank");

		// --Add Duplicate Location
		Driver.findElement(By.id("ddlToLocation")).clear();
		logger.info("Cleared To Location");
		Driver.findElement(By.id("ddlToLocation")).sendKeys("DEFAULTBIN");
		logger.info("Entered duplicate To Location");
		Driver.findElement(By.id("idbtnAdd")).click();
		logger.info("Clicked on Add button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		errorid = Driver.findElement(By.id("errorid")).getText();
		logger.info("Validation message is displayed==" + errorid);
		getScreenshot(Driver, "FSLSetup_Locadd_Duplicate");

		// --Enter valid To Location
		Driver.findElement(By.id("ddlToLocation")).clear();
		logger.info("Cleared To Location");
		Driver.findElement(By.id("ddlToLocation")).sendKeys("AUTOMATION");
		logger.info("Entered To Location");
		Driver.findElement(By.id("hlkSaveASN")).click();
		logger.info("Clicked on Save button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		SuccMsg = Driver.findElement(By.id("success"));
		if (SuccMsg.isDisplayed()) {
			String SuccMessage = SuccMsg.getText();
			logger.info("Success Message==" + SuccMessage);
		} else {
			logger.info("Record is not saved");
		}

		// --Print button
		Driver.findElement(By.id("idPrint")).click();
		logger.info("Clicked on Print button of Manage FSL");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String winHandleBefore = Driver.getWindowHandle();
		for (String winHandle : Driver.getWindowHandles()) {
			Driver.switchTo().window(winHandle);
			logger.info("Switched to Print Label window");
			getScreenshot(Driver, "ManageFSL_PrintLabel");
		}
		Thread.sleep(2000);

		Driver.close();
		logger.info("Closed Print Label window");
		Driver.switchTo().window(winHandleBefore);
		logger.info("Switched to main window");
		Thread.sleep(2000);

		// Click Back
		Driver.findElement(By.id("idBack")).click();
		logger.info("Clicked on Back button of Manage FSL");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Add New

		// Click on add
		Driver.findElement(By.id("hlkCreateASN")).click();
		logger.info("Clicked on Add FSL");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		/*
		 * Random rand = new Random(); int num1 = rand.nextInt(20); if (num1 == 0) {
		 * num1 = num1 + 2; } String num2 = Integer.toString(num1);
		 */
		LOCCode1 = "LC" + System.currentTimeMillis();

		// fill information
		Driver.findElement(By.id("txtLocationCode")).clear();
		logger.info("Cleared Location Code");
		Driver.findElement(By.id("txtLocationCode")).sendKeys(LOCCode1);
		logger.info("Enter Location Code");

		Driver.findElement(By.id("txtLength")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logger.info("Select Length");
		Driver.findElement(By.id("txtLength")).sendKeys("15.15");
		logger.info("Entered Length");

		Driver.findElement(By.id("txtWidth")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logger.info("Select Width");
		Driver.findElement(By.id("txtWidth")).sendKeys("22.22");
		logger.info("Entered Width");

		Driver.findElement(By.id("txtHeight")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		logger.info("Select Height");
		Driver.findElement(By.id("txtHeight")).sendKeys("33.33");
		logger.info("Entered Height");

		// click on save
		Driver.findElement(By.id("hlkSaveASN")).click();
		logger.info("Clicked on Save button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		SuccMsg = Driver.findElement(By.id("success"));
		if (SuccMsg.isDisplayed()) {
			String SuccMessage = SuccMsg.getText();
			logger.info("Success Message==" + SuccMessage);
		} else {
			logger.info("Record is not saved");
		}
		getScreenshot(Driver, "FSLSetup_Save");

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Clicked on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======FSL SetUp Test End=======");
		msg.append("=======FSL SetUp Test End=======" + "\n\n");
	}
}
