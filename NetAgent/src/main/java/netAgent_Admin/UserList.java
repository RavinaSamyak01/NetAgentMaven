package netAgent_Admin;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class UserList extends BaseInit {
	@Test
	public void userlist() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		// -- Go to admin
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idAdmin")));
		Driver.findElement(By.id("idAdmin")).click();

		// --UserList
		Driver.findElement(By.linkText("User List")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("UserlistSearchGD")));

		// search with first name
		Driver.findElement(By.id("txtFirstName")).clear();

		File src0 = new File(".\\NA_STG.xls");
		FileInputStream fis0 = new FileInputStream(src0);
		Workbook workbook = WorkbookFactory.create(fis0);
		Sheet sh0 = workbook.getSheet("Sheet1");
		// int rcount = sh0.getLastRowNum();

		DataFormatter formatter = new DataFormatter();

		Driver.findElement(By.id("txtFirstName")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(36)));

		// --Click on Search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		Driver.findElement(By.id("btnSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String FNexp = formatter.formatCellValue(sh0.getRow(2).getCell(36));
		String FNact = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'First Name')]"))
				.getText();
		System.out.println(FNexp);
		System.out.println(FNact);

		if (FNexp.contentEquals(FNact)) {
			System.out.println("First Name Search Compare is PASS");
		} else {
			System.out.println("First Name Search Compare is FAIL");
		}

		Driver.findElement(By.id("btnReset")).click();

		// search with last name
		Driver.findElement(By.id("txtLastName")).clear();
		Driver.findElement(By.id("txtLastName")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(37)));

		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		Driver.findElement(By.id("btnSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String LNexp = Driver.findElement(By.id("txtLastName")).getText();
		String LNact = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Last Name')]"))
				.getText();
		System.out.println(LNexp);
		System.out.println(LNact);

		if (LNexp.contentEquals(LNact)) {
			System.out.println("Last Name Search Compare is PASS");
		} else {
			System.out.println("Last Name Search Compare is FAIL");
		}

		Driver.findElement(By.id("btnReset")).click();

		Driver.findElement(By.id("txtFirstName")).sendKeys("Test1234");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		Driver.findElement(By.id("btnSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String s1 = Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
		System.out.println(s1);

		Driver.findElement(By.id("btnReset")).click();

		Driver.findElement(By.id("txtLastName")).sendKeys("Test1234");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		Driver.findElement(By.id("btnSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String s2 = Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
		System.out.println(s2);

		Driver.findElement(By.id("btnReset")).click();

		Driver.findElement(By.id("txtLoginId")).sendKeys("Test1234");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		Driver.findElement(By.id("btnSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String s3 = Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
		System.out.println(s3);

		Driver.findElement(By.id("btnReset")).click();

		// search with login id
		Driver.findElement(By.id("txtLoginId")).clear();
		Driver.findElement(By.id("txtLoginId")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(38)));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		Driver.findElement(By.id("btnSearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String Loginidexp = Driver.findElement(By.id("txtLoginId")).getText();
		String Loginidact = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Login Id')]"))
				.getText();
		System.out.println(Loginidexp);
		System.out.println(Loginidact);

		if (Loginidexp.contentEquals(Loginidact)) {
			System.out.println("Login ID Search Compare is PASS");
		} else {
			System.out.println("Login ID Search Compare is FAIL");
		}

		// Click on Edit
		Driver.findElement(By.id("imgUserListEdit_1")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		// wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'well-sm')]")));

		// check all editable fields
		// 1. Login Type
		Boolean logintype = Driver.findElement(By.id("txtLoginType")).isEnabled();

		if (logintype == true) {
			throw new Error("Error: Login Type field is enable");
		}

		// 2. Reporting To
		Boolean RepoTo = Driver.findElement(By.id("txtReportingTo")).isEnabled();

		if (RepoTo == false) {
			throw new Error("Error: Reporting To field is disable");
		}

		// 3. Login ID
		Boolean loginid = Driver.findElement(By.id("txtLoginId")).isEnabled();

		if (loginid == true) {
			throw new Error("Error: Login ID field is enable");
		}

		// 4. Password
		Boolean pwd = Driver.findElement(By.id("txtPwd")).isEnabled();

		if (pwd == true) {
			throw new Error("Error: Password field is enable");
		}

		// 5. Confirm Password
		Boolean cnfpwd = Driver.findElement(By.id("txtConfPwd")).isEnabled();

		if (cnfpwd == true) {
			throw new Error("Error: Confirm Password field is enable");
		}

		// 6. First Name
		Boolean fname = Driver.findElement(By.id("txtFirstName")).isEnabled();

		if (fname == false) {
			throw new Error("Error: First Name field is disable");
		}

		// 7. Middle Name
		Boolean mname = Driver.findElement(By.id("txtMiddleName")).isEnabled();

		if (mname == false) {
			throw new Error("Error: Middle Name field is disable");
		}

		// 8. Last Name
		Boolean lname = Driver.findElement(By.id("txtLastName")).isEnabled();

		if (lname == false) {
			throw new Error("Error: Last Name field is disable");
		}

		// 9. Title
		Boolean title = Driver.findElement(By.id("txtTitle")).isEnabled();

		if (title == false) {
			throw new Error("Error: Title field is disable");
		}

		// 10. Portal Type
		Boolean porttype = Driver.findElement(By.id("txtPortalType")).isEnabled();

		if (porttype == true) {
			throw new Error("Error: Portal Type  field is enable");
		}

		// 11. Password Last Set
		Boolean pwdlastset = Driver.findElement(By.id("txtPwdLastSet")).isEnabled();

		if (pwdlastset == true) {
			throw new Error("Error: Password Last Set field is enable");
		}

		// 12. Valid From
		Boolean vfrom = Driver.findElement(By.id("txtvalidfrom")).isEnabled();

		if (vfrom == false) {
			throw new Error("Error: Valid From field is disable");
		}

		// 13. Valid To
		Boolean vto = Driver.findElement(By.id("txtValidto")).isEnabled();

		if (vto == false) {
			throw new Error("Error: Valid To field is disable");
		}

		// 14. Description
		Boolean desc = Driver.findElement(By.id("txtDescription")).isEnabled();

		if (desc == false) {
			throw new Error("Error: Description field is disable");
		}

		// User Contact grid
		// 1. Country
		Boolean asdasd = Driver.findElement(By.id("drpCountry")).isEnabled();

		if (asdasd == false) {
			throw new Error("Error: Country field is disable");
		}

		// 2. Zip/Postal Code
		Boolean Zip = Driver.findElement(By.id("txtZipCode")).isEnabled();

		if (Zip == false) {
			throw new Error("Error: Zip/Postal Code field is disable");
		}

		// 3. City
		Boolean City = Driver.findElement(By.id("txtCity")).isEnabled();

		if (City == false) {
			throw new Error("Error: City field is disable");
		}

		// 4. State
		Boolean State = Driver.findElement(By.id("txtState")).isEnabled();

		if (State == true) {
			throw new Error("Error: State field is enable");
		}

		// 5. Address Line 1
		Boolean add1 = Driver.findElement(By.id("txtAddr1")).isEnabled();

		if (add1 == false) {
			throw new Error("Error: Address Line 1 field is disable");
		}

		// 6. Dept/Suite
		Boolean Dept = Driver.findElement(By.id("txtDept")).isEnabled();

		if (Dept == false) {
			throw new Error("Error: Dept/Suite field is disable");
		}

		// 7. Main Phone
		Boolean mphone = Driver.findElement(By.id("txtMain")).isEnabled();

		if (mphone == false) {
			throw new Error("Error: Main Phone field is disable");
		}

		// 8. Main Phone ext
		Boolean mphoneext = Driver.findElement(By.id("txtExt")).isEnabled();

		if (mphoneext == false) {
			throw new Error("Error: Main Phone ext field is disable");
		}

		// 9. Fax
		Boolean Fax = Driver.findElement(By.id("txtFax")).isEnabled();

		if (Fax == false) {
			throw new Error("Error: Fax field is disable");
		}

		// 10. Email
		Boolean Email = Driver.findElement(By.id("txtEmail")).isEnabled();

		if (Email == false) {
			throw new Error("Error: Email field is disable");
		}

		// 11. Work Phone
		Boolean wphone = Driver.findElement(By.id("txtUserWorkphone")).isEnabled();

		if (wphone == false) {
			throw new Error("Error: Work Phone field is disable");
		}

		// 12. Work Phone Ext
		Boolean wphoneext = Driver.findElement(By.id("txtWorkphoneExt")).isEnabled();

		if (wphoneext == false) {
			throw new Error("Error: Work Phone Ext field is disable");
		}

		// 13. Call Phone
		Boolean cphone = Driver.findElement(By.id("txtCallphone")).isEnabled();

		if (cphone == false) {
			throw new Error("Error: Call Phone field is disable");
		}

		// 14. Home Phone
		Boolean hphone = Driver.findElement(By.id("txtHomephone")).isEnabled();

		if (hphone == false) {
			throw new Error("Error: Home Phone field is disable");
		}

		// 15. Web Address
		Boolean wadd = Driver.findElement(By.id("txtWebaddress")).isEnabled();

		if (wadd == false) {
			throw new Error("Error: Web Address field is disable");
		}

		// 16. Security Question
		Boolean sque = Driver.findElement(By.id("txtSecQue")).isEnabled();

		if (sque == false) {
			throw new Error("Error: Security Question field is disable");
		}

		// 17. Response
		// Driver.findElement(By.id("chkShowResponse")).click();
		// Thread.sleep(5000);

		// Boolean Response = Driver.findElement(By.id("txtSecAns")).isEnabled();

		// if(Response == false)
		// {
		// throw new Error("Error: Response field is disable");
		// }

		// Click on save
		// Driver.findElement(By.id("imgSaveUserMaster")).click();
		// Thread.sleep(5000);

		// add and delete user role
		JavascriptExecutor js = ((JavascriptExecutor) Driver);
		js.executeScript("window.scrollTo(2, document.body.scrollHeight);");

		// select all
		Driver.findElement(By.id("chkRoleItemsSelectAll")).click();

		// -Delete
		Driver.findElement(By.id("imgCancelIcon")).click();

		Driver.switchTo().alert();
		Driver.switchTo().alert().accept();

		Driver.findElement(By.id("imgSaveUserMaster")).click();

		getScreenshot(Driver, "UserList_Validation");

		String Message1 = Driver.findElement(By.id("idValidation")).getText();

		if (Message1.equals("Please assign atleast one Role to User.")) {
			Message1 = "*****Validation message is matched*****";
			System.out.println(Message1);
		}

		// --Assign Role
		Driver.findElement(By.id("imgPlusIcon")).click();
		js.executeScript("window.scrollTo(2, document.body.scrollHeight);");

		// --Select Role
		Driver.findElement(By.id("drpRole")).click();
		Thread.sleep(2000);
		Select Rolename = new Select(Driver.findElement(By.id("drpRole")));
		Rolename.selectByVisibleText("NETAGENT W/Inventory");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgSaveUserMaster")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("UserlistSearchGD")));

		// enter valid to for newly added
		/*
		 * DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); Date Todate = new
		 * Date(); String ValidToDate = dateFormat.format(Todate);
		 * 
		 * Driver.findElement(By.id("txtCalValidTo")).clear();
		 * Driver.findElement(By.id("txtCalValidTo")).sendKeys(ValidToDate);
		 * Thread.sleep(7000);
		 */

		// Select added record and click on delete
		/*
		 * Driver.findElement(By.xpath(
		 * "//*[@id=\"RoleDetailsTable\"]/tbody/tr[7]/td[1]/input")).click();
		 * Thread.sleep(7000);
		 * 
		 * Driver.findElement(By.id("imgCancelIcon")).click(); Thread.sleep(7000);
		 * 
		 * Driver.switchTo().alert(); Driver.switchTo().alert().accept();
		 * Thread.sleep(7000);
		 */

		getScreenshot(Driver, "UserList");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("imgNGLLogo")));
		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
	}
}
