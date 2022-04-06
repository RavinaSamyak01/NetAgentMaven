package netAgent_OperationsTab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class CycleCount extends BaseInit {
	@Test
	public static void cycleCount() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		logger.info("=======CycleCount Test Start=======");
		msg.append("=======CycleCount Test Start=======" + "\n\n");

		// Go to CycleCount screen
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		Driver.findElement(By.id("idOperations")).click();
		logger.info("Click on Operations");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idCycle")));
		Driver.findElement(By.id("idCycle")).click();
		logger.info("Click on CycleCount");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "CycleCount");

		// Start Cycle

		// --if Action column is not empty for 1st row
		if (!Driver.findElements(By.xpath("//a[@class=\"dx-link\"]")).isEmpty()) {
			logger.info("If action column is not empty");
			getScreenshot(Driver, "CycleCountStart");

			// --Click on start button of first row
			Driver.findElement(By.xpath("//a[@class=\"dx-link\"]")).click();
			logger.info("Click on Start button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Wait for workOrder ID
			WebElement WorkOID = Driver.findElement(By.id("workid"));
			wait.until(ExpectedConditions.visibilityOf(WorkOID));
			logger.info("WorkOrderID after start==" + WorkOID.getText());
		}

		// --if Reset button is exist
		if (!Driver.findElements(By.xpath("//a[@class='dx-link' and text()='Reset']")).isEmpty()) {
			logger.info("If Reset button is exist");
			getScreenshot(Driver, "CycleCountReset");
			// --Click on Reset button
			Driver.findElement(By.xpath("//a[@class='dx-link' and text()='Reset']")).click();
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Wait for workOrder ID
			WebElement WorkOID = Driver.findElement(By.id("woid"));
			wait.until(ExpectedConditions.visibilityOf(WorkOID));
			logger.info("WorkOrderID after Reset==" + WorkOID.getText());
		}

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======CycleCount Test End=======");
		msg.append("=======CycleCount Test End=======" + "\n\n");
	}
}
