package netAgent_Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class UserProfile extends BaseInit {
	@Test
	public void userprofile() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		// -- Go to admin
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idAdmin")));
		// WebElement Admin=Driver.findElement(By.id("idAdmin"));
		// act.moveToElement(Admin).build().perform();
		Driver.findElement(By.partialLinkText("Admin")).click();

		// --Click on UserProfile
		Driver.findElement(By.linkText("User Profile")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		Thread.sleep(2000);
		// Check all fields

		// --UserDetails
		// --1.Login ID
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtLoginId")));
		Boolean LognID = Driver.findElement(By.id("txtLoginId")).isDisplayed();

		if (LognID == false) {
			System.out.println("Error: Login ID field is not display");
		}

		// --2.Email
		Boolean Email = Driver.findElement(By.id("txtEmail")).isDisplayed();

		if (Email == false) {
			System.out.println("Error: Email field is not display");
		}
		// --3. Password
		Boolean paswd = Driver.findElement(By.id("txtPwd")).isDisplayed();

		if (paswd == false) {
			System.out.println("Error: Password field is not display");
		}

		// --4. Confirm Password
		Boolean Confpaswd = Driver.findElement(By.id("txtConfpassword")).isDisplayed();

		if (Confpaswd == false) {
			System.out.println("Error: Confirm Password field is not display");
		}

		// --UserContact

		// 5. Title
		Boolean Title = Driver.findElement(By.id("txtTitle")).isDisplayed();

		if (Title == false) {
			System.out.println("Error: Title  field is not display");
		}

		// 6. First Name
		Boolean fname = Driver.findElement(By.id("txtFirstname")).isDisplayed();

		if (fname == false) {
			System.out.println("Error: First Name field is not display");
		}

		// 7. Middle Name
		Boolean mname = Driver.findElement(By.id("txtMiddleName")).isDisplayed();

		if (mname == false) {
			System.out.println("Error: Middle Name field is not display");
		}

		// 8. Last Name
		Boolean lname = Driver.findElement(By.id("txtLastName")).isDisplayed();

		if (lname == false) {
			System.out.println("Error: Last Name field is not display");
		}

		// 9. Address Line 1
		Boolean Add1UP = Driver.findElement(By.id("txtAddrline1")).isDisplayed();

		if (Add1UP == false) {
			System.out.println("Error: Address Line 1 field is not display");
		}

		// 10. Dept/Suite
		Boolean deptUP = Driver.findElement(By.id("txtAddrline2")).isDisplayed();

		if (deptUP == false) {
			System.out.println("Error: Dept/Suite  field is not display");
		}

		// 11. CityUP
		Boolean CityUP = Driver.findElement(By.id("txtCity")).isDisplayed();

		if (CityUP == false) {
			System.out.println("Error: City field is not display");
		}

		// 12. StateUP
		Boolean StateUP = Driver.findElement(By.id("txtState")).isDisplayed();

		if (StateUP == false) {
			System.out.println("Error: State field is not display");
		}

		// 13. Zip/Postal Code
		Boolean zippost = Driver.findElement(By.id("txtZipcode")).isDisplayed();

		if (zippost == false) {
			System.out.println("Error: Zip/Postal Code field is not display");
		}

		// 14. Country
		Boolean Country = Driver.findElement(By.id("txtCountryId")).isDisplayed();

		if (Country == false) {
			System.out.println("Error: Country field is not display");
		}

		// 15. Main Phone
		Boolean MainPhone = Driver.findElement(By.id("txtUserMainphone")).isDisplayed();

		if (MainPhone == false) {
			System.out.println("Error: Main Phone field is not display");
		}

		// 16. Work Phone
		Boolean WorkPhone = Driver.findElement(By.id("txtUserworkphone")).isDisplayed();

		if (WorkPhone == false) {
			System.out.println("Error: Work Phone field is not display");
		}

		// 17. Cell Phone
		Boolean CellPhone = Driver.findElement(By.id("txtCallphone")).isDisplayed();

		if (CellPhone == false) {
			System.out.println("Error: Cell Phone field is not display");
		}

		// 18. Home Phone
		Boolean HomePhone = Driver.findElement(By.id("txtHomephone")).isDisplayed();

		if (HomePhone == false) {
			System.out.println("Error: Home Phone field is not display");
		}

		getScreenshot(Driver, "UserProfile");

		// --Click on MNX Logo
		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}
}
