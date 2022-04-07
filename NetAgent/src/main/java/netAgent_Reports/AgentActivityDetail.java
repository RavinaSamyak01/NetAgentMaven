package netAgent_Reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class AgentActivityDetail extends BaseInit {
	@Test
	public void agentActivityDetailReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		logger.info("=======Agent Activity Report Test Start=======");
		msg.append("=======Agent Activity Report Test Start=======" + "\n\n");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idReports")));
		Driver.findElement(By.id("idReports")).click();
		logger.info("Clicked on Reports");

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Agent Activity Detail")));
		Driver.findElement(By.linkText("Agent Activity Detail")).click();
		logger.info("Clicked on Agent Activity Details");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Select Agent
		Driver.findElement(By.id("btn_ddlCourierclass=")).click();
		logger.info("Clicked on Courier dropdown");
		Thread.sleep(2000);
		Driver.findElement(By.id("chkAllddlCourier")).click();
		logger.info("Select all");
		Driver.findElement(By.id("btn_ddlCourierclass=")).click();
		logger.info("Close Courier dropdown");

		// from date
		DateFormat dateFormatAgAcRp = new SimpleDateFormat("MM/dd/yyyy");
		Date frmdtAgAcRp = new Date();
		Date frmdt1AgAcRp = addDays(frmdtAgAcRp, -10);
		String FromDateAgAcRp = dateFormatAgAcRp.format(frmdt1AgAcRp);

		Driver.findElement(By.id("txtValidFrom")).clear();
		logger.info("Cleared Valid From");
		Driver.findElement(By.id("txtValidFrom")).sendKeys(FromDateAgAcRp);
		logger.info("Enter Valid From");
		WebElement AgAcRpfdate = Driver.findElement(By.id("txtValidFrom"));
		AgAcRpfdate.sendKeys(Keys.TAB);

		// to date
		Date todtAgAcRp = new Date();
		String ToDateAgAcRp = dateFormatAgAcRp.format(todtAgAcRp);

		Driver.findElement(By.id("txtValidTo")).clear();
		logger.info("Cleared Valid To");
		Driver.findElement(By.id("txtValidTo")).sendKeys(ToDateAgAcRp);
		logger.info("Cleared Valid To");
		WebElement AgAcRptdate = Driver.findElement(By.id("txtValidTo"));
		AgAcRptdate.sendKeys(Keys.TAB);

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

		boolean AvArRp = Driver.findElement(By.xpath("//iframe[@id=\"myIframe\"]")).isDisplayed();

		if (AvArRp == false) {
			throw new Error("Error: Agent Activity Report grid not display");
		}

		getScreenshot(Driver, "AgentActivityDetailReport");

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Click on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======Agent Activity Details Report Test End=======");
		msg.append("=======Agent Activity Details Report Test End=======" + "\n\n");
	}

}
