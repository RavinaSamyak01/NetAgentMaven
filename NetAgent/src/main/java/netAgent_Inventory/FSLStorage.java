package netAgent_Inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class FSLStorage extends BaseInit {
	@Test
	public void fSLStorage() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		// --Click on Inventory
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idInventory")));
		Driver.findElement(By.id("idInventory")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

		// --Click on FSL Storage
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idFSLStorage")));
		Driver.findElement(By.id("idFSLStorage")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		System.out.println(Driver.getTitle());

		// --Month dropdown
		Select monthname = new Select(Driver.findElement(By.id("ddlmonth")));
		String selectedComboValue = monthname.getFirstSelectedOption().getText();
		System.out.println("Default Current Month Displayed in Combo : " + selectedComboValue);

		WebElement month = Driver.findElement(By.id("ddlmonth"));
		Select opt = new Select(month);
		opt.selectByVisibleText("August");
		Driver.findElement(By.id("idSearchFsl")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Year dropdown
		Select yrname = new Select(Driver.findElement(By.id("ddlyear")));
		String selectedComboValue1 = yrname.getFirstSelectedOption().getText();
		System.out.println("Default Current Year Displayed in Combo : " + selectedComboValue1);

		getScreenshot(Driver, "FSLStorage");

		WebElement year = Driver.findElement(By.id("ddlyear"));
		Select opt1 = new Select(year);
		opt1.selectByVisibleText("2019");
		Driver.findElement(By.id("idSearchFsl")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --nothing to edit and save in the screen
		/*
		 * Driver.findElement(By.xpath(
		 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]"
		 * )) .click(); Driver.findElement( By.xpath(
		 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]/div/div/input"
		 * )) .clear(); Thread.sleep(5000); Driver.findElement(By.xpath(
		 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]"
		 * )) .click(); Driver.findElement( By.xpath(
		 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]/div/div/input"
		 * )) .sendKeys("21"); Thread.sleep(5000);
		 * Driver.findElement(By.id("idSaveFSlStorage")).click(); Thread.sleep(5000);
		 * 
		 * Driver.findElement(By.id("success")).getText(); Thread.sleep(5000);
		 */

		getScreenshot(Driver, "FSLStorage");

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}
}
