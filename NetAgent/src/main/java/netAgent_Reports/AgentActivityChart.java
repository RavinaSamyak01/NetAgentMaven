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

public class AgentActivityChart extends BaseInit {
	@Test
	public void AgentActivityChartReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idReports")));
		Driver.findElement(By.id("idReports")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Agent Activity Chart")));
		Driver.findElement(By.linkText("Agent Activity Chart")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// select all agent
		Driver.findElement(By.id("btn_ddlCourierclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("chkAllddlCourier")).click();
		Driver.findElement(By.id("btn_ddlCourierclass=")).click();

		// from date
		DateFormat dateFormatAgAcChrtRp = new SimpleDateFormat("MM/dd/yyyy");
		Date frmdtAgAcChrtRp = new Date();
		Date frmdt1AgAcChrtRp = addDays(frmdtAgAcChrtRp, -10);
		String FromDateAgAcChrtRp = dateFormatAgAcChrtRp.format(frmdt1AgAcChrtRp);

		Driver.findElement(By.id("txtValidFrom")).clear();
		Driver.findElement(By.id("txtValidFrom")).sendKeys(FromDateAgAcChrtRp);
		WebElement AgAcChrtRpfdate = Driver.findElement(By.id("txtValidFrom"));
		AgAcChrtRpfdate.sendKeys(Keys.TAB);

		// to date
		Date todtAgAcChrtRp = new Date();
		String ToDateAgAcChrtRp = dateFormatAgAcChrtRp.format(todtAgAcChrtRp);

		Driver.findElement(By.id("txtValidTo")).clear();
		Driver.findElement(By.id("txtValidTo")).sendKeys(ToDateAgAcChrtRp);
		WebElement AgAcChrtRptdate = Driver.findElement(By.id("txtValidTo"));
		AgAcChrtRptdate.sendKeys(Keys.TAB);

		// click on view report

		Driver.findElement(By.id("btnView")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --wait to get the notification message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
		String WaitMsg = Driver.findElement(By.xpath("//label[@id=\"idwait\"]")).getText();
		System.out.println("Wait Message is==" + WaitMsg);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));

		boolean AvArChrtRp = Driver.findElement(By.xpath("//iframe[@id=\"myIframe\"]")).isDisplayed();

		if (AvArChrtRp == false) {
			throw new Error("Error: Agent Activity Chart Report grid not display");
		}

		getScreenshot(Driver, "AgentActivityChartReport");

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
	}
}
