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

		logger.info("=======MNX Documents Test Start=======");
		msg.append("=======MNX Documents Test Start=======" + "\n\n");

		// Go to Tools - NGL Doc screen
		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Tools")));
		Driver.findElement(By.partialLinkText("Tools")).click();
		logger.info("Click on Tools");

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("MNX Documents")));
		Driver.findElement(By.linkText("MNX Documents")).click();
		logger.info("Click on MNX Documents");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "MNXDocuments");

//			//Click on First Doc
		String winHandleBefore1 = Driver.getWindowHandle();

//			//CLick on doc link
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@ng-click=\"NglDocData(doc)\"]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@ng-click=\"NglDocData(doc)\"]")));

		Driver.findElement(By.xpath("//a[@ng-click=\"NglDocData(doc)\"]")).click();
		logger.info("Click on Document");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		for (String winHandle : Driver.getWindowHandles()) {
			Driver.switchTo().window(winHandle);
			getScreenshot(Driver, "MNXDocument");
			logger.info("Switched to Document window");
		}
		// Close new window
		Driver.close();
		logger.info("Close Document window");

		Thread.sleep(5000);

		// Switch back to original browser (first window)
		Driver.switchTo().window(winHandleBefore1);
		logger.info("Switched to Main window");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Click on MNX Logo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======MNX Documents Test End=======");
		msg.append("=======MNX Documents Test End=======" + "\n\n");
	}

}
