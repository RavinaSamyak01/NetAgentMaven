package netAgent_Inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class CycleCountBin extends BaseInit {
	@Test
	public void cycleCountBIN() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idInventory")));
		Driver.findElement(By.id("idInventory")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cycle Count BIN")));
		Driver.findElement(By.linkText("Cycle Count BIN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Select Client from dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ddlClient")));
		Driver.findElement(By.id("ddlClient")).click();
		Select client = new Select(Driver.findElement(By.id("ddlClient")));
		client.selectByVisibleText("AUTOMATION - SPRINT #950656");

		// Select Bin name from dropdown list.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn_cmbLocationBinclass=")));
		Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
		Thread.sleep(2000);
		Driver.findElement(By.id("idcheckboxInput")).click();
		Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();

		// Click on Start button
		Driver.findElement(By.id("btnstart")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		System.out.println((Driver.findElement(By.id("lblSuccessMsg")).getText()));

		getScreenshot(Driver, "CycleCountBIN");

		Driver.findElement(By.id("btnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
	}

}
