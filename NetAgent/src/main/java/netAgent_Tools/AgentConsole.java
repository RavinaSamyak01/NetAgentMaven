package netAgent_Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class AgentConsole extends BaseInit {
	@Test
	public void agentConsole() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		// Go to Tools - Agent Console screen
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idTools")));
		Driver.findElement(By.id("idTools")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idAgent")));
		Driver.findElement(By.id("idAgent")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Select AirlineId
		Driver.findElement(By.id("txtAirlineId")).clear();
		Driver.findElement(By.id("txtAirlineId")).sendKeys("AA");
		Thread.sleep(2000);
		WebElement alid = Driver.findElement(By.id("txtAirlineId"));
		alid.sendKeys(Keys.ENTER);

		// enter FLight No
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtFlightNo")));
		Driver.findElement(By.id("txtFlightNo")).clear();
		Driver.findElement(By.id("txtFlightNo")).sendKeys("1993");

		// Select Arriving Airport
		Driver.findElement(By.id("txtAirportID")).clear();
		Driver.findElement(By.id("txtAirportID")).sendKeys("CLT");
		Thread.sleep(2000);
		WebElement apid = Driver.findElement(By.id("txtAirportID"));
		apid.sendKeys(Keys.ENTER);

		// Click on Check Status
		Driver.findElement(By.id("btCheckStatus")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		boolean map = Driver.findElement(By.id("collFlight")).isEnabled();

		if (map == false) {
			throw new Error("Error: Map not display");
		}

		// Collapse map
		Driver.findElement(By.id("imgFlight")).click();
		Thread.sleep(10000);

		// Weather info
		Driver.findElement(By.id("txtZipCode")).clear();
		Driver.findElement(By.id("txtZipCode")).sendKeys("10019");
		Thread.sleep(10000);

		Driver.findElement(By.id("btnReset")).click();
		Thread.sleep(10000);

		Driver.findElement(By.id("txtZipCode")).clear();
		Driver.findElement(By.id("txtZipCode")).sendKeys("90019");
		Thread.sleep(10000);

		Driver.findElement(By.id("btnSubmit")).click();
		Thread.sleep(10000);

		getScreenshot(Driver, "agentconsole");

		Driver.findElement(By.id("imgWeather")).click();
		Thread.sleep(10000);

		// Click on First Doc
		String winHandleBefore1 = Driver.getWindowHandle();

		// CLick on link

		Driver.findElement(By.linkText("Click here")).click();
		Thread.sleep(10000);

		for (String winHandle : Driver.getWindowHandles()) {
			Driver.switchTo().window(winHandle);
		}
		// Close new window
		Driver.close();

		// Switch back to original browser (first window)
		Driver.switchTo().window(winHandleBefore1);
		Thread.sleep(5000);

//			//Store window
//			String winHandleBefore = Driver.getWindowHandle();
//			
//			//CLick for open new window
//			Driver.findElement(By.xpath("//a[contains(.,'Click here')]")).click();
//			Thread.sleep(10000);
//			
//			for(String winHandle : Driver.getWindowHandles())
//			{
//				Driver.switchTo().window(winHandle);
//			}
//			//Close new window
//			Driver.close();
//			
//			Thread.sleep(10000);	
//			
//			// Switch back to original browser (first window)
//			Driver.switchTo().window(winHandleBefore);
//			Thread.sleep(10000);

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
	}

}
