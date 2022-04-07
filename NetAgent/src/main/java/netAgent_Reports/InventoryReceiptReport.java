package netAgent_Reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class InventoryReceiptReport extends BaseInit {

	@Test
	public void ReceiptReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		logger.info("=======Receipt Report Test Start=======");
		msg.append("=======Receipt Report Test Start=======" + "\n\n");

		Actions act = new Actions(Driver);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idReports")));

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
			Driver.findElement(By.id("idReceipt")).click();
			logger.info("Clicked on Receipt Report");
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
			Driver.findElement(By.id("idReceipt")).click();
			logger.info("Clicked on Receipt Report");
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		/*
		 * 
		 * Driver.findElement(By.partialLinkText("Reports")).click();
		 * Thread.sleep(5000);
		 * Driver.findElement(By.xpath("//*[@id=\"idInventory\"]")).click();
		 * Thread.sleep(5000); Driver.findElement(By.id("idReceipt")).click();
		 * Thread.sleep(10000);
		 */
		// select FSL
		// Driver.findElement(By.xpath("/html/body/div[2]/section/div[2]/div/div/div[2]/form/div[2]/div[1]/div[1]/div/div/button")).click();

		// --Select FSL Name
		Driver.findElement(By.id("btn_ddlfslclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.xpath("//div[@id=\"ddlfsl\"]//input[@id=\"idcheckboxInput\"]")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("btn_ddlfslclass=")).click();
		logger.info("Selected FSL");

		// select client
		// Driver.findElement(By.xpath("/html/body/div[2]/section/div[2]/div/div/div[2]/form/div[2]/div[1]/div[2]/div/div/button")).click();

		Driver.findElement(By.id("btn_ddlClientclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.xpath("//div[@id=\"ddlClient\"]//input[@id=\"idcheckboxInput\"]")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("btn_ddlClientclass=")).click();
		logger.info("Selected Client");

		// from date
		DateFormat dateFormatRpln = new SimpleDateFormat("MM/dd/yyyy");
		Date frmdtRpln = new Date();
		Date frmdt1Rpln = addDays(frmdtRpln, -10);
		String FromDateRpln = dateFormatRpln.format(frmdt1Rpln);

		Driver.findElement(By.id("txtValidFrom")).click();
		Driver.findElement(By.id("txtValidFrom")).clear();
		Driver.findElement(By.id("txtValidFrom")).sendKeys(FromDateRpln);
		WebElement Rplnfdate = Driver.findElement(By.id("txtValidFrom"));
		Rplnfdate.sendKeys(Keys.TAB);
		logger.info("Selected FromDate");

		// to date
		Date todtRpln = new Date();
		String ToDateRpln = dateFormatRpln.format(todtRpln);

		Driver.findElement(By.id("txtValidTo")).clear();
		Driver.findElement(By.id("txtValidTo")).sendKeys(ToDateRpln);
		WebElement Rplntdate = Driver.findElement(By.id("txtValidTo"));
		Rplntdate.sendKeys(Keys.TAB);
		Thread.sleep(2000);
		logger.info("Selected ValidTo");

		// part name
		Driver.findElement(By.id("txtField1")).clear();
		logger.info("Clear PartName");
		Driver.findElement(By.id("txtField1")).sendKeys(Part2);
		logger.info("Enter PartName");

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
			throw new Error("Error: Replenish Report grid not display");
		}

		getScreenshot(Driver, "ReceiptReport");

		// Reset
		Driver.findElement(By.id("btnReset")).click();
		logger.info("Click on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Click on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======Receipt Report Test End=======");
		msg.append("=======Receipt Report Test End=======" + "\n\n");
	}

}
