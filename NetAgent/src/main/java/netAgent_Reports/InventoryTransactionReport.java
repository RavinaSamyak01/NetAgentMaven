package netAgent_Reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class InventoryTransactionReport extends BaseInit {
	@Test
	public void TransactionReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);

		logger.info("=======Transaction Report Test Start=======");
		msg.append("=======Transaction Report Test Start=======" + "\n\n");

		try {
			WebElement Reports = Driver.findElement(By.id("idReports"));
			act.moveToElement(Reports).build().perform();
			logger.info("Clicked on Reports");
			Thread.sleep(2000);
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReports\"]")));
			WebElement Inventory = Driver.findElement(By.id("idReportInventory"));
			act.moveToElement(Inventory).build().perform();
			logger.info("Clicked on Inventory");
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReportInventory\"]")));
			Driver.findElement(By.id("idTransaction")).click();
			logger.info("Clicked on Transaction Report");
		} catch (Exception e) {
			Driver.findElement(By.id("idReports")).click();
			logger.info("Clicked on Reports");
			Thread.sleep(2000);
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReports\"]")));
			Driver.findElement(By.id("idReportInventory")).click();
			logger.info("Clicked on Inventory");
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReportInventory\"]")));
			Driver.findElement(By.id("idTransaction")).click();
			logger.info("Clicked on Transaction Report");
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// select FSL
		Driver.findElement(By.id("btn_ddlfslclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.xpath("//*[@id=\"ddlfsl\"]//input[@id=\"idcheckboxInput\"]")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("btn_ddlfslclass=")).click();
		logger.info("Selected FSL");

		// select client
		Driver.findElement(By.id("btn_ddlClientclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.xpath("//*[@id=\"ddlClient\"]//input[@id=\"idcheckboxInput\"]")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("btn_ddlClientclass=")).click();
		logger.info("Selected Client");

		// from date
		DateFormat dateFormattrns = new SimpleDateFormat("MM/dd/yyyy");
		Date frmdttrns = new Date();
		Date frmdt1trns = addDays(frmdttrns, -10);
		String FromDatetrns = dateFormattrns.format(frmdt1trns);
		logger.info("Selected FromDate");

		Driver.findElement(By.id("txtValidFrom")).click();
		Driver.findElement(By.id("txtValidFrom")).clear();
		Driver.findElement(By.id("txtValidFrom")).sendKeys(FromDatetrns);
		WebElement trnsfdate = Driver.findElement(By.id("txtValidFrom"));
		trnsfdate.sendKeys(Keys.TAB);

		// to date
		Date todttrns = new Date();
		String ToDatetrns = dateFormattrns.format(todttrns);

		Driver.findElement(By.id("txtValidTo")).clear();
		Driver.findElement(By.id("txtValidTo")).sendKeys(ToDatetrns);
		WebElement trnstdate = Driver.findElement(By.id("txtValidTo"));
		trnstdate.sendKeys(Keys.TAB);
		Thread.sleep(2000);
		logger.info("Selected ValidTo");

		// --select WO type
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ddlWOType")));
		Select clnt = new Select(Driver.findElement(By.id("ddlWOType")));
		clnt.selectByVisibleText("Work Order In");
		logger.info("Selected WorkOrderType");

		// view report
		Driver.findElement(By.id("btnView")).click();
		logger.info("Click on View button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --wait to get the notification message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
		String WaitMsg = Driver.findElement(By.xpath("//label[@id=\"idwait\"]")).getText();
		System.out.println("Wait Message is==" + WaitMsg);
		logger.info("Wait Message is==" + WaitMsg);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));

		boolean Replnsh = Driver.findElement(By.xpath("//*[@id=\"myIframe\"]")).isDisplayed();

		if (Replnsh == false) {
			throw new Error("Error: Transaction Report grid not display");
		}
		/*
		 * Driver.findElement(By.id("txtPart")).clear(); logger.info("Clear PartName");
		 * Driver.findElement(By.id("txtPart")).sendKeys(Part2);
		 * logger.info("Enter PartName");
		 * 
		 * Driver.findElement(By.id("txtSerial")).clear(); logger.info("Enter Seru");
		 * Driver.findElement(By.id("txtSerial")).sendKeys(P2Field2);
		 * logger.info("Enter PartName");
		 */

		// --Fields are not exist

		/*
		 * Driver.findElement(By.id("txtTracking")).clear();
		 * Driver.findElement(By.id("txtTracking")).sendKeys(P2Field3);
		 * 
		 * Driver.findElement(By.id("txtRevision")).clear();
		 * Driver.findElement(By.id("txtRevision")).sendKeys(P2Field4);
		 * 
		 * Driver.findElement(By.id("txtField5")).clear();
		 * Driver.findElement(By.id("txtField5")).sendKeys(P2Field5);
		 */

		/*
		 * Driver.findElement(By.id("btnView")).click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * // --wait to get the notification message
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//label[@id=\"idwait\"]"))); WaitMsg =
		 * Driver.findElement(By.xpath("//label[@id=\"idwait\"]")).getText();
		 * System.out.println("Wait Message is==" + WaitMsg);
		 * 
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//label[@id=\"idwait\"]")));
		 * 
		 * boolean Replnsh1 =
		 * Driver.findElement(By.xpath("//*[@id=\"myIframe\"]")).isDisplayed();
		 * 
		 * if (Replnsh1 == false) { throw new
		 * Error("Error: Transaction Report grid not display when no record found"); }
		 */

		getScreenshot(Driver, "TransactionReport");

		// Reset
		Driver.findElement(By.id("btnReset")).click();
		logger.info("Click on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Click on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======Transaction Report Test End=======");
		msg.append("=======Transaction Report Test End=======" + "\n\n");

	}

}
