package netAgent_Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class MNXDocuments extends BaseInit {
	@Test
	public void MNXDoc() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		// Go to Tools - NGL Doc screen
		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Tools")));
		Driver.findElement(By.partialLinkText("Tools")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("MNX Documents")));
		Driver.findElement(By.linkText("MNX Documents")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "MNXDoc");

//			//Click on First Doc
		String winHandleBefore1 = Driver.getWindowHandle();

//			//CLick on doc link
		Driver.findElement(By.xpath("//a[@ng-click=\"NglDocData(doc)\"]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		for (String winHandle : Driver.getWindowHandles()) {
			Driver.switchTo().window(winHandle);
		}
		// Close new window
		Driver.close();

		Thread.sleep(5000);

		// Switch back to original browser (first window)
		Driver.switchTo().window(winHandleBefore1);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}

}
