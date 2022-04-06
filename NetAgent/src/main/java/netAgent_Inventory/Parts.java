package netAgent_Inventory;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Parts extends BaseInit {
	@Test
	public void parts() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Inventory")));
		Driver.findElement(By.partialLinkText("Inventory")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Parts")));
		Driver.findElement(By.linkText("Parts")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		logger.info("Parts Screen: Parts screen opened.");
		System.out.println(Driver.getTitle());

		// parts
		WebElement clientprt = Driver.findElement(By.id("ddlClient"));
		Select optprt = new Select(clientprt);
		optprt.selectByVisibleText(Client);
		logger.info("Parts Screen: Client selected");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// -Search button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		logger.info("Parts Screen: Click on search button.");

		// Select Include Zero Qty
		Driver.findElement(By.id("IncludeZeroQty")).click();
		logger.info("Parts Screen: Tick on Include zero qty checkbox.");
		// --Search
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		logger.info("Parts Screen: Click on search button.");

		// DeSelect Include Zero Qty
		WebElement element1 = Driver.findElement(By.id("IncludeZeroQty"));
		Actions actions1 = new Actions(Driver);
		actions1.moveToElement(element1).click().build().perform();
		logger.info("Parts Screen: UnTick on Include zero qty checkbox.");
		// -search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		logger.info("Parts Screen: Click on search button.");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "Part");

		// partDetail

		// Click on Part# and Return

		File src0 = new File(".\\NA_STG.xls");
		FileInputStream fis0 = new FileInputStream(src0);
		Workbook workbook = WorkbookFactory.create(fis0);
		Sheet sh0 = workbook.getSheet("Sheet1");
		// int rcount = sh0.getLastRowNum();

		DataFormatter formatter = new DataFormatter();

		Driver.findElement(By.id("txtField1")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(22)));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Thread.sleep(2000);

		Driver.findElement(By.xpath("//*[@id='PartMasterGD']//tbody//a")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Thread.sleep(2000);

		logger.info("Parts Screen: Click on part and go to part details screen.");
		System.out.println(Driver.getTitle());

		getScreenshot(Driver, "Part_PartEditor");

		Driver.findElement(By.id("idreturntoitem")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		logger.info("Parts Screen: Return from part details screen.");
		Thread.sleep(2000);

		String exp1 = formatter.formatCellValue(sh0.getRow(2).getCell(22));
		System.out.println("Expected Sprint part Number is=" + exp1);
		String act1 = Driver.findElement(By.xpath("//*[@id='PartMasterGD']//tbody//a")).getText();
		System.out.println("Actual Sprint part Number is=" + act1);

		if (act1.contains(exp1)) {
			System.out.println("Field1 Search Compare is - PASS");
		}

		else {
			System.out.println("Field1 Search Compare is - FAIL");
		}

		// --Search with second field
		Driver.findElement(By.id("txtField1")).clear();
		Driver.findElement(By.id("txtField2")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(23)));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("txtField2")).clear();

		// --Search with third field
		Driver.findElement(By.id("txtField3")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(24)));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Search with fourth field
		Driver.findElement(By.id("txtField3")).clear();
		Driver.findElement(By.id("txtField4")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(25)));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// -Search with fifth field
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtField4")));
		Driver.findElement(By.id("txtField4")).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtSrAliasName")));
		Driver.findElement(By.id("txtSrAliasName")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(26)));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Invalid text
		Driver.findElement(By.id("txtSrAliasName")).clear();

		// Search with invalid text
		Driver.findElement(By.id("txtField1")).sendKeys("Test123");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("PartNoGrid")).getText();
		Driver.findElement(By.id("txtField1")).clear();

		Driver.findElement(By.id("txtField2")).sendKeys("Test123");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("PartNoGrid")).getText();
		Driver.findElement(By.id("txtField2")).clear();

		Driver.findElement(By.id("txtField3")).sendKeys("Test123");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("PartNoGrid")).getText();
		Driver.findElement(By.id("txtField3")).clear();

		Driver.findElement(By.id("txtField4")).sendKeys("Test123");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("PartNoGrid")).getText();
		Driver.findElement(By.id("txtField4")).clear();

		Driver.findElement(By.id("txtSrAliasName")).sendKeys("Test123");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Driver.findElement(By.id("PartNoGrid")).getText();
		Driver.findElement(By.id("txtSrAliasName")).clear();

		// Select Bin and Search
		Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
		Thread.sleep(2000);
		logger.info("Parts Screen: User has clicked on Bin dropdown box.");

		Driver.findElement(By.xpath("//label[contains(.,'All')]")).click();
		Thread.sleep(2000);
		logger.info("Parts Screen: User has tick All checkbox.");

		Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		logger.info("Parts Screen: User has click on search button.");

		Driver.findElement(By.id("idResetbutton")).click();
		logger.info("Parts Screen: User has click Reset button.");

		WebElement clientstck = Driver.findElement(By.id("ddlClient"));
		Select optstck = new Select(clientstck);
		optstck.selectByVisibleText(Client);
		Thread.sleep(2000);

		// WebElement fslstck = Driver.findElement(By.id("ddlfsl"));
		// Select opt1stck = new Select(fslstck);
		// opt1stck.selectByVisibleText(FSLName);
		// Thread.sleep(10000);

		// Stock Details

		// Click on Stock
		Driver.findElement(By.id("txtField1")).clear();
		Driver.findElement(By.id("txtField1")).sendKeys(formatter.formatCellValue(sh0.getRow(2).getCell(22)));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
		Driver.findElement(By.id("idsearchbutton")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Thread.sleep(2000);
		String stock_partDetails = Driver.findElement(By.xpath(".//*[@id=\"PartMasterGD\"]//tr[1]/td[4]/a")).getText();
		String[] list = stock_partDetails.split(" ");
		String a = list[1].replaceAll("[^0-9]", "");
		String stock_partDetails1 = a;
		System.out.println("First : " + stock_partDetails1);

		// System.out.println("First :: "+stock_partDetails);

		Driver.findElement(By.xpath(".//*[@id=\"PartMasterGD\"]//tr[1]/td[4]/a")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String stock_Details = Driver.findElement(By.xpath("//div[@style=\"text-align:right\"]/strong")).getText();
		System.out.println("Second : " + stock_Details);

		if (stock_partDetails1.equals(stock_Details)) {
			System.out.println("Total Records Matched");
		} else {
			System.out.println("Total Records Not Matched");
		}

		logger.info("Parts Screen: Go to stock details screen.");
		System.out.println(Driver.getTitle());

		getScreenshot(Driver, "Part_StockDetails");

		// --Click on PrintLabel button
		Driver.findElement(By.linkText("Print Label")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		logger.info("Parts Screen: User has performed print label.");

		String winHandleBefore = Driver.getWindowHandle();
		for (String winHandle : Driver.getWindowHandles()) {
			Driver.switchTo().window(winHandle);
		}
		Thread.sleep(2000);

		Driver.close();
		Driver.switchTo().window(winHandleBefore);
		Thread.sleep(2000);

		// --Return to Item
		Driver.findElement(By.id("idreturntoitem")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("btnPrint")).click();
		Thread.sleep(10000);
		boolean plabel = Driver.findElement(By.id("PartNoGrid")).getText()
				.contains("Please select atleast one record.");

		if (plabel == true) {
			Driver.findElement(By.xpath("//td[@role=\"gridcell\"]//span[@class=\"dx-checkbox-icon\"]")).click();

			Driver.findElement(By.id("btnPrint")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String winHandleBefore1 = Driver.getWindowHandle();
			for (String winHandle1 : Driver.getWindowHandles()) {
				Driver.switchTo().window(winHandle1);
			}

			Driver.close();
			Thread.sleep(2000);
			Driver.switchTo().window(winHandleBefore1);
			Thread.sleep(2000);

		}
		getScreenshot(Driver, "PartswithSelection");

		Driver.findElement(By.id("idResetbutton")).click();
		logger.info("Parts Screen: User has click reset button.");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		logger.info("Parts Screen: Going to main screen.");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}
}
