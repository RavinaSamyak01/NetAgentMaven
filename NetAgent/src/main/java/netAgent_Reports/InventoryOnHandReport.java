package netAgent_Reports;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class InventoryOnHandReport extends BaseInit {
	@Test
	public void OnHandReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);

		logger.info("=======OnHand Report Test Start=======");
		msg.append("=======OnHand Report Test Start=======" + "\n\n");

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
			Driver.findElement(By.id("idOn")).click();
			logger.info("Clicked on OnHand Report");
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
			Driver.findElement(By.id("idOn")).click();
			logger.info("Clicked on OnHand Report");
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// select FSL
		Driver.findElement(By.id("btn_ddlfslclass=")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"ddlfsl\"]//input[@id=\"idcheckboxInput\"]")));
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

		// part num
		Driver.findElement(By.id("txtField1")).clear();
		logger.info("Clear PartNum");
		Driver.findElement(By.id("txtField1")).sendKeys(Part2);
		logger.info("Enter PartNum");

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
			throw new Error("Error: On Hand Report grid not display");
		} else {
			System.out.println("On Hand Report is displayed");
		}

		getScreenshot(Driver, "OnHandReport");

		// Reset
		Driver.findElement(By.id("btnReset")).click();
		logger.info("Click on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Click on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======OnHand Report Test End=======");
		msg.append("=======OnHand Report Test End=======" + "\n\n");

	}

}
