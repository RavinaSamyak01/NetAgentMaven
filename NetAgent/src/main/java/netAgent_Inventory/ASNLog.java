package netAgent_Inventory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class ASNLog extends BaseInit {
	@Test
	public void aSNLog() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);

		// --Inventory
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idInventory")));
		Driver.findElement(By.id("idInventory")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

		// --ASN
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idASN")));
		Driver.findElement(By.id("idASN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		File src0 = new File(".\\NA_STG.xls");
		FileInputStream fis0 = new FileInputStream(src0);
		Workbook workbook = WorkbookFactory.create(fis0);
		Sheet sh0 = workbook.getSheet("Sheet1");
		// int rcount = sh0.getLastRowNum();

		DataFormatter formatter = new DataFormatter();

		/*
		 * // Search with Tracking#
		 * Driver.findElement(By.id("txtTracking")).sendKeys(formatter.formatCellValue(
		 * sh0.getRow(2).getCell(19))); Thread.sleep(2000);
		 * Driver.findElement(By.id("idbtnRunSearch")).click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * // -Created ASN Doesnt have tracking ID
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * System.out.println("Title of the screen is" + Driver.getTitle()); String
		 * Trackexp = formatter.formatCellValue(sh0.getRow(2).getCell(19)); String
		 * Trackact = Driver.findElement(By.
		 * xpath("//td[@role=\"gridcell\" and contains(@aria-label,'Tracking')]"))
		 * .getText(); System.out.println(Trackexp); System.out.println(Trackact);
		 * 
		 * if (Trackexp.equals(Trackact)) {
		 * System.out.println("Tracking Number search result is PASS"); } else {
		 * System.out.println("Tracking Number search result is FAIL"); } // Back to
		 * screen Driver.findElement(By.id("hlkBackToScreen")).click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * Driver.findElement(By.id("txtTracking")).clear();
		 */

		// Search with ASN# and go to ASN Details screen
		Driver.findElement(By.id("txtASN")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(20)));
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		System.out.println("Title of the screen is" + Driver.getTitle());

		String ASNexp = formatter.formatCellValue(sh0.getRow(2).getCell(20));
		String ASNact = Driver.findElement(By.id("txtasnno")).getText();
		System.out.println(ASNexp);
		System.out.println(ASNact);

		if (ASNexp.equals(ASNact)) {
			System.out.println("ASN Number search result is PASS");
		} else {
			System.out.println("ASN Number search result is FAIL");
		}

		// Expand and Collapse in ASN details screen
		Driver.findElement(By.id("expandId")).click();
		Thread.sleep(2000);

		getScreenshot(Driver, "ASN Log Details");

		Driver.findElement(By.id("collapseId")).click();
		Thread.sleep(2000);

		// Go back to ASNLog screen
		Driver.findElement(By.id("hlkBackToScreen")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("txtASN")).clear();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Search with ASN Type
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnTypeclass=")));
		Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("chkAllcmbAsnType")).click();
		Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnTypeclass=")));
		Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("chkAllcmbAsnType")).click();
		Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Search with ASN Status
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnStatusclass=")));
		Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("chkAllcmbAsnStatus")).click();
		Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnStatusclass=")));
		Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
		Thread.sleep(3000);
		Driver.findElement(By.id("chkAllcmbAsnStatus")).click();
		Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Search with Carrier - Fedex
		/*
		 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(
		 * "btn_cmbAsnCarrierclass=")));
		 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
		 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbAsnCarrier")).click();
		 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
		 * Driver.findElement(By.id("idbtnRunSearch")).click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(
		 * "hlkBackToScreen"))); Driver.findElement(By.id("hlkBackToScreen")).click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
		 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbAsnCarrier")).click();
		 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbAsnCarrier")).click();
		 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
		 * Driver.findElement(By.id("idbtnRunSearch")).click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 */

		// Search with Location
		Select Location = new Select(Driver.findElement(By.id("ddlFsl")));
		Location.selectByIndex(1);
		Thread.sleep(2000);
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String Locationexp = formatter.formatCellValue(sh0.getRow(2).getCell(39));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//td[@role=\"gridcell\" and contains(@aria-label,'Location')]")));
		String Locationact = Driver
				.findElement(By.xpath("//td[@role=\"gridcell\" and contains(@aria-label,'Location')]")).getText();
		System.out.println(Locationexp);
		System.out.println(Locationact);

		if (Locationexp.equals(Locationact)) {
			System.out.println("Location search result is PASS");
		} else {
			System.out.println("Location search result is FAIL");
		}

		Select Account = new Select(Driver.findElement(By.id("drpAccount")));
		Account.selectByIndex(1);
		Thread.sleep(2000);
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Select Location1 = new Select(Driver.findElement(By.id("ddlFsl")));
		Location1.selectByIndex(0);
		Thread.sleep(2000);
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// From date and To date selection
		Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, -30);
		String ValiFrom = getDate(cal);
		System.out.println("Valid From Date :- " + ValiFrom);
		String ValiTo = getDate(cal);
		System.out.println("Valid To Date :- " + ValiTo);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtFromEstArrival")));
		Driver.findElement(By.id("txtFromEstArrival")).sendKeys(ValiFrom);
		Driver.findElement(By.id("txtToEstArrival")).sendKeys(ValiTo);

		Driver.findElement(By.id("txtASN")).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idbtnRunSearch")));
		WebElement RSearch = Driver.findElement(By.id("idbtnRunSearch"));
		act.moveToElement(RSearch).build().perform();
		RSearch.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			Driver.findElement(By.id("hlkBackToScreen")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		} catch (Exception e) {
			System.out.println("There is no data with enetered date");
		}
		Driver.findElement(By.id("txtFromEstArrival")).clear();
		Driver.findElement(By.id("txtToEstArrival")).clear();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtAsnFromDate")).sendKeys(ValiFrom);
		Driver.findElement(By.id("txtAsnToDate")).sendKeys(ValiTo);
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			Driver.findElement(By.id("hlkBackToScreen")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		} catch (Exception e) {
			System.out.println("There is no data with enetered date");
		}
		Driver.findElement(By.id("txtAsnFromDate")).clear();
		Driver.findElement(By.id("txtAsnToDate")).clear();
		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "ASNLog_1");

		Driver.findElement(By.id("txtTracking")).sendKeys("Test1234");
		Driver.findElement(By.id("idbtnRunSearch")).click();
		Driver.findElement(By.xpath("//*[@id=\"ASNLogGD\"]/div/div[6]/span")).getText();
		Thread.sleep(8000);
		Driver.findElement(By.id("txtTracking")).clear();
		Thread.sleep(3000);

		Driver.findElement(By.id("txtWorkOrder")).sendKeys("1234567890");
		Driver.findElement(By.id("idbtnRunSearch")).click();
		Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
		Driver.findElement(By.id("txtWorkOrder")).clear();

		Driver.findElement(By.id("txtASN")).sendKeys("1234567890");
		Driver.findElement(By.id("idbtnRunSearch")).click();
		Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
		Driver.findElement(By.id("txtASN")).clear();

		Driver.findElement(By.id("txtAsnRef")).sendKeys("Test1234");
		Driver.findElement(By.id("idbtnRunSearch")).click();
		Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
		Driver.findElement(By.id("txtAsnRef")).clear();

		Driver.findElement(By.id("idbtnRunSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Check paging
		pagination();

		// Click on ASN No.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfAct")));
		Driver.findElement(By.id("hrfAct")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Boolean ASNNo = Driver.findElement(By.id("txtasnno")).getAttribute("readonly").equals("");
		if (ASNNo == true) {
			System.out.println("ASNNo. field is Editable");
		} else {
			System.out.println("ASNNo. field is Non-editable");
		}

		Boolean ASNType = Driver.findElement(By.id("txtASNType")).getAttribute("readonly").equals("");
		if (ASNType == true) {
			System.out.println("ASNType field is Editable");
		} else {
			System.out.println("ASNType field is Non-editable");
		}

		Boolean AccountNum = Driver.findElement(By.id("txtacc")).getAttribute("readonly").equals("");
		if (AccountNum == true) {
			System.out.println("Account Number field is Editable");
		} else {
			System.out.println("Account Number field is Non-editable");
		}

		Boolean Locfield = Driver.findElement(By.id("txtfsl")).getAttribute("readonly").equals("");
		if (Locfield == true) {
			System.out.println("Location field is Editable");
		} else {
			System.out.println("Location field is Non-editable");
		}

		Boolean ASNRef = Driver.findElement(By.id("txtasnref")).getAttribute("readonly").equals("");
		if (ASNRef == true) {
			System.out.println("ASN Reference field is Editable");
		} else {
			System.out.println("ASN Reference field is Non-editable");
		}

		Boolean CarrierName = Driver.findElement(By.id("txtcarriername")).getAttribute("readonly").equals("");
		if (CarrierName == true) {
			System.out.println("Carrier Name field is Editable");
		} else {
			System.out.println("Carrier Name field is Non-editable");
		}

		Boolean TrackingNum = Driver.findElement(By.id("txttrackingno")).getAttribute("readonly").equals("");
		if (TrackingNum == true) {
			System.out.println("Tracking Number field is Editable");
		} else {
			System.out.println("Tracking Number field is Non-editable");
		}

		Boolean RefURL = Driver.findElement(By.id("txtreferenceurl")).getAttribute("readonly").equals("");
		if (RefURL == true) {
			System.out.println("RefURL field is Editable");
		} else {
			System.out.println("RefURL field is Non-editable");
		}

		Boolean Note = Driver.findElement(By.id("txtnotes")).getAttribute("readonly").equals("");
		if (Note == true) {
			System.out.println("Note field is Editable");
		} else {
			System.out.println("Note field is Non-editable");
		}

		Driver.findElement(By.id("hlkBackToScreen")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		WebElement nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
		act.moveToElement(nextpage).click().perform();
		System.out.println("clicked on next page");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String P1 = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
		System.out.println(P1);

		WebElement prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
		act.moveToElement(prevpage).click().perform();
		System.out.println("clicked on previous page");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String P2 = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
		System.out.println(P2);

		// Export Action
		Driver.findElement(By.id("idbtnexport")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Thread.sleep(5000);

		getScreenshot(Driver, "ASN Log_2");

		// Go to main screen
		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}

	// --Not exist in new NetAgent app
	/*
	 * @Test public void NetlinkInv() throws Exception { WebDriverWait wait = new
	 * WebDriverWait(Driver, 50);
	 * 
	 * Thread.sleep(10000);
	 * Driver.findElement(By.partialLinkText("Inventory")).click();
	 * 
	 * Driver.findElement(By.linkText("Netlink Inventory")).click();
	 * Thread.sleep(10000);
	 * 
	 * Driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/a/img")).click();
	 * Thread.sleep(10000); }
	 */
}
