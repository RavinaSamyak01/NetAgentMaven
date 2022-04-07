package netAgent_Reports;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class QuarantineReport extends BaseInit {
	@Test
	public void quarantineReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		logger.info("=======Quarantine Report Test Start=======");
		msg.append("=======Quarantine Report Test Start=======" + "\n\n");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idReports")));
		Driver.findElement(By.id("idReports")).click();
		logger.info("Clicked on Reports");

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Quarantine Report")));
		Driver.findElement(By.linkText("Quarantine Report")).click();
		logger.info("Clicked on Quarantine Report");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "QuarantineReportScreen");

		// --client select
		Select client = new Select(Driver.findElement(By.id("ddlClient")));
		client.selectByVisibleText("AUTOMATION - SPRINT #950656");
		System.out.println("Client selected");
		logger.info("Selected Client");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --FSL Name select
		Select fsl = new Select(Driver.findElement(By.id("ddlfsl")));
		fsl.selectByVisibleText("AUTOMATION-FSL(F5386)");
		System.out.println("FSL selected");
		logger.info("Selected FSL");

		// click on view report
		Driver.findElement(By.id("btnView")).click();
		logger.info("Click on View button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --wait to get the notification message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
		String WaitMsg = Driver.findElement(By.xpath("//label[@id=\"idwait\"]")).getText();
		System.out.println("Wait Message is==" + WaitMsg);
		logger.info("Wait Message is==" + WaitMsg);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
		getScreenshot(Driver, "QuarantineReport");

		boolean AvArRp = Driver.findElement(By.xpath("//iframe[@id=\"myIframe\"]")).isDisplayed();

		if (AvArRp == false) {
			throw new Error("Error: Agent Activity Report grid not display");
		}

		// --click on Reset button
		Driver.findElement(By.id("btnReset")).click();
		System.out.println("Clicked on Reset button");
		logger.info("Click on Reset button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Click on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======Quarantine Report Test End=======");
		msg.append("=======Quarantine Report Test End=======" + "\n\n");

	}
}
