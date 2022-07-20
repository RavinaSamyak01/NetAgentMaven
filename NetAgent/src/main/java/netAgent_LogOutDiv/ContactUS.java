package netAgent_LogOutDiv;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class ContactUS extends BaseInit {
	@Test
	public static void ContactUs() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		logger.info("=======ContactUS Test Start=======");
		msg.append("=======ContactUS Test Start=======" + "\n\n");

		// Go to Welcome - Contact Us screen
		isElementPresent("LogOutDiv_xpath").click();
		isElementPresent("ContactUS_linkText").click();
		logger.info("Clicked on ContactUS");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Contact Us screen and Add comment and submit.
		System.out.println(Driver.getTitle());
		isElementPresent("Comments_name").clear();
		isElementPresent("Comments_name").sendKeys("Test Note");
		logger.info("Entered Comments");
		isElementPresent("Submit_id").click();
		logger.info("Clicked on Submit button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[@class=\"ng-binding\"]")));
			WebElement SuccMsg = isElementPresent("SuccessMsg_xpath");
			if (SuccMsg.isDisplayed()) {
				System.out.println("Success Message is displayed==" + SuccMsg.getText() + "\n\n");
				logger.info("Success Message is displayed==" + SuccMsg.getText() + "\n\n");
				msg.append("Success Message is displayed==" + SuccMsg.getText() + "\n\n");
				logger.info("Success Message is displayed==PASS");

			}
		} catch (Exception SUccMsg) {
			logger.error(SUccMsg);
			System.out.println("Success Message is not displayed" + "\n\n");
			logger.info("Success Message is not displayed" + "\n\n");
			msg.append("Success Message is not displayed" + "\n\n");
			logger.info("Success Message is not displayed==FAIL");

		}

		// Take screen-shot.
		getScreenshot(Driver, "ContactUs");

		// Go to main screen.
		isElementPresent("MNXLogo_id").click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======ContactUS Test End=======" + "\n\n");
		msg.append("=======ContactUS Test End=======" + "\n\n");
	}

}
