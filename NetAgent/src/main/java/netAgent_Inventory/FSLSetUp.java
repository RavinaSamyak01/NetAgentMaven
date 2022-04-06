package netAgent_Inventory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class FSLSetUp extends BaseInit {
	@Test
	public void fSLSetup() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idInventory")));
		Driver.findElement(By.id("idInventory")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idFSLSetup")));
		Driver.findElement(By.id("idFSLSetup")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Location Code Search
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("DEFAULTBIN");
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Select DefaultBin and Try to edit
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfAct")));
		Driver.findElement(By.id("hrfAct")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Boolean locationcode = Driver.findElement(By.id("txtLocationCode")).isEnabled();

		if (locationcode == true) {
			throw new Error("Error: Location Code field is enable");
		}

		Driver.findElement(By.id("idiconsave")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idiconsave")));
		Driver.findElement(By.id("errorid")).getText();

		getScreenshot(Driver, "FSLSetup");

		// Driver.findElement(By.cssSelector(".btn.btn-primary.no-radius")).click();
		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Refresh
		Driver.findElement(By.id("hlkCancleContactsDtls")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Paging
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("1");
		Thread.sleep(10000);

		Driver.findElement(By.id("idbtnsearch")).click();
		Thread.sleep(10000);

		String pagecnt = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
		System.out.println(pagecnt);

		if (pagecnt.contains("Page 1 of 1")) {
			Driver.findElement(By.id("idbtnreset")).click();
		} else {
			Driver.findElement(By.xpath(".//*[@aria-label='Page 2']")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.xpath(".//*[@aria-label=' Next page']")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.xpath(".//*[@aria-label='Previous page']")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.id("idbtnreset")).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtFSLbinSearch")));
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("1");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("Test1234");
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.xpath("//*[@id=\"gridFSLSetup\"]/div/div[6]/span")).getText();
		Driver.findElement(By.id("idbtnreset")).click();

		// Edit and Save

		File src0 = new File(".\\NA_STG.xls");
		FileInputStream fis0 = new FileInputStream(src0);
		Workbook workbook = WorkbookFactory.create(fis0);
		Sheet sh0 = workbook.getSheet("Sheet1");
		// int rcount = sh0.getLastRowNum();

		DataFormatter formatter = new DataFormatter();

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(27)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfAct")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hrfAct")));
		Driver.findElement(By.id("hrfAct")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtLength")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Driver.findElement(By.id("txtLength")).sendKeys("10");

		Driver.findElement(By.id("txtWidth")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Driver.findElement(By.id("txtWidth")).sendKeys("2");

		Driver.findElement(By.id("txtHeight")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Driver.findElement(By.id("txtHeight")).sendKeys("5");

		Driver.findElement(By.id("hlkSaveASN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Manage FSL
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("DEFAULTBIN");
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'Manage')]")));
		Driver.findElement(By.xpath("//a[contains(.,'Manage')]")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Refresh
		Driver.findElement(By.id("idiconrefresh")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// basic search
		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(28)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String E1 = formatter.formatCellValue(sh0.getRow(2).getCell(28));
		String A1 = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]"))
				.getText();
		System.out.println(E1);
		System.out.println(A1);

		if (A1.equals(E1)) {
			System.out.println("Search Compare PASS");
		} else {
			System.out.println("Search Compare FAIL");
		}

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(29)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String E2 = formatter.formatCellValue(sh0.getRow(2).getCell(29));
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]")));
		String A2 = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]"))
				.getText();
		System.out.println(E2);
		System.out.println(A2);

		if (A2.contains(E2)) {
			System.out.println("Search Compare PASS");
		} else {
			System.out.println("Search Compare FAIL");
		}

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(30)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String E3 = formatter.formatCellValue(sh0.getRow(2).getCell(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]")));
		Thread.sleep(2000);
		String A3 = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]"))
				.getText();
		System.out.println(E3);
		System.out.println(A3);

		if (A3.contains(E3)) {
			System.out.println("Search Compare PASS");
		} else {
			System.out.println("Search Compare FAIL");
		}

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(31)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String E4 = formatter.formatCellValue(sh0.getRow(2).getCell(31));
		String A4 = Driver
				.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Part')]"))
				.getText();
		System.out.println(E4);
		System.out.println(A4);

		if (A4.contains(E4)) {
			System.out.println("Search Compare PASS");
		} else {
			System.out.println("Search Compare FAIL");
		}

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(32)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(33)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(34)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(35)));
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("txtFSLbinSearch")).sendKeys("Test1234");
		Driver.findElement(By.id("idbtnsearch")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.xpath("//*[@id=\"gridManageFSLSetup\"]/div/div[6]/span")).getText();

		Driver.findElement(By.id("idbtnreset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// click save
		Driver.findElement(By.id("hlkSaveASN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("idValidation")).getText();

		getScreenshot(Driver, "FSLSetup");

		Driver.findElement(
				By.xpath("//*[@id=\"gridManageFSLSetup\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[1]/div/div"))
				.click();
		Thread.sleep(10000);

		// Select To Location
		Driver.findElement(By.id("ddlToLocation")).sendKeys("DEFAULTBIN");

		Driver.findElement(By.id("hlkSaveASN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("iderrorcloseicon")).getText();

		Driver.findElement(By.id("ddlToLocation")).clear();

		Driver.findElement(By.id("ddlToLocation")).sendKeys("LAX");
		Driver.findElement(By.id("idbtnAdd")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		System.out.println(Driver.findElement(By.id("idsuccesscloseicon")).getText());

		getScreenshot(Driver, "FSLSetup_Location add");

		Driver.findElement(By.id("idbtnAdd")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		System.out.println(Driver.findElement(By.id("errorid")).getText());

		// Click Back
		Driver.findElement(By.id("idBack")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Add New

		// Click on add
		Driver.findElement(By.id("hlkCreateASN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Random rand = new Random();
		int num1 = rand.nextInt(20);
		if (num1 == 0) {
			num1 = num1 + 2;
		}
		String num2 = Integer.toString(num1);
		LOCCode1 = LOCCode1 + num2;

		// fill information
		Driver.findElement(By.id("txtLocationCode")).clear();
		Driver.findElement(By.id("txtLocationCode")).sendKeys(LOCCode1);

		Driver.findElement(By.id("txtLength")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Driver.findElement(By.id("txtLength")).sendKeys("15.15");

		Driver.findElement(By.id("txtWidth")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Driver.findElement(By.id("txtWidth")).sendKeys("22.22");

		Driver.findElement(By.id("txtHeight")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		Driver.findElement(By.id("txtHeight")).sendKeys("33.33");

		// click on save
		Driver.findElement(By.id("hlkSaveASN")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "FSLSetup_Save");

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
	}
}
