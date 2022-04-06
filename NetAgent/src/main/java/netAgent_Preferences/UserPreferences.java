package netAgent_Preferences;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class UserPreferences extends BaseInit {
	@Test
	public void UserPreference() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);

		// --Clicked on Preferences
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idPreferences")));
		WebElement Preference = Driver.findElement(By.id("idPreferences"));
		act.moveToElement(Preference).click().perform();
		Thread.sleep(2000);

		// --UserPreference
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("User Preferences")));
		Driver.findElement(By.linkText("User Preferences")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		// Check all fields
		// User Pref
		// 1. User Name
		Boolean uname = Driver.findElement(By.id("txtUsername")).isEnabled();

		if (uname == true) {
			throw new Error("Error: User Name field is enable");
		}

		// Currency Pref
		// 1. Currency
		Boolean Currency = Driver.findElement(By.id("txtCurrency")).isEnabled();

		if (Currency == true) {
			throw new Error("Error: Currency field is enable");
		}

		// 2. Currency Symbol
		Boolean currsym = Driver.findElement(By.id("txtCurrencySymbol")).isEnabled();

		if (currsym == true) {
			throw new Error("Error: Currency Symbol field is enable");
		}

		// 3. Currency Separator
		Boolean currsep = Driver.findElement(By.id("ddlCurrencySeparator")).isEnabled();

		if (currsep == true) {
			throw new Error("Error: Currency Separator field is enable");
		}

		// Regional Preferences
		// 1. Country
		Boolean Country = Driver.findElement(By.id("ddlCountry")).isEnabled();

		if (Country == false) {
			throw new Error("Error: Country field is disable");
		}

		// 2. Culture
		Boolean Culture = Driver.findElement(By.id("ddlCULTURE")).isEnabled();

		if (Culture == false) {
			throw new Error("Error: Culture field is disable");
		}

		// 4. Time Zone
		Boolean TZ = Driver.findElement(By.id("ddlTimeZone")).isEnabled();

		if (TZ == false) {
			throw new Error("Error: Time Zone field is disable");
		}

		// 3. Date/Time Format
		Boolean dttmfor = Driver.findElement(By.id("ddlDateTimeFormat")).isEnabled();

		if (dttmfor == false) {
			throw new Error("Error: Date/Time Format field is disable");
		}

		// 2. FSL
		Boolean FSL = Driver.findElement(By.id("ddlCourierFSL")).isEnabled();

		if (FSL == false) {
			throw new Error("Error: FSL field is disable");
		}

		// --Click on save
		Driver.findElement(By.id("btnSave")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		getScreenshot(Driver, "UserPreference");

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}
}
