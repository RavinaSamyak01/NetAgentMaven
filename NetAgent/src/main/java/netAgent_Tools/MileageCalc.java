package netAgent_Tools;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class MileageCalc extends BaseInit {
	@Test
	public void mileageCalc() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Tools")));
		Driver.findElement(By.partialLinkText("Tools")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("MileageCalc")));
		Driver.findElement(By.linkText("MileageCalc")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		System.out.println("Title of the screen is==" + Driver.getTitle());

		// For Record not Found Testing.
		// PUId = "1234567";
		// JobId = "123456789";

		// Search with Pickup

		Driver.findElement(By.id("txtpickupid")).clear();
		Driver.findElement(By.id("txtpickupid")).sendKeys("1234567");

		Driver.findElement(By.id("btngetdetails")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String RNF = Driver.findElement(By.id("errorid")).getText();
		System.out.println("RNF : " + RNF);

		String CalMsg = Driver.findElement(By.xpath("//label/strong")).getText();
		/// html/body/div[2]/section/div[2]/div/div/div[2]/div[1]/form/div[2]/div/div[3]/div/div/label/strong

		// Reset
		Driver.findElement(By.id("btnReset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// Search with JobId
		Driver.findElement(By.id("txtJobid")).clear();
		Driver.findElement(By.id("txtJobid")).sendKeys(JobId);

		Driver.findElement(By.id("btngetdetails")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String CalMsg2 = Driver.findElement(By.xpath("//label/strong")).getText();

		Driver.findElement(By.id("btnReset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String RNF2 = Driver.findElement(By.id("errorid")).getText();
		System.out.println("RNF2 : " + RNF2);

		Driver.findElement(By.id("txtpickupid")).clear();
		Driver.findElement(By.id("txtpickupid")).sendKeys(PUId);

		Driver.findElement(By.id("btngetdetails")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		String act = Driver.findElement(By.xpath("//label/strong/span")).getText();
		System.out.println(act);

		File src0 = new File(".\\NA_DEV.xls");
		FileInputStream fis0 = new FileInputStream(src0);
		Workbook workbook = WorkbookFactory.create(fis0);
		Sheet sh0 = workbook.getSheet("Sheet1");
		// int rcount = sh0.getLastRowNum();

		DataFormatter formatter = new DataFormatter();

		String Exp = formatter.formatCellValue(sh0.getRow(2).getCell(21));

		if (act.contains(Exp)) {
			System.out.println("Miles Comparison is PASS");
		}

		else {
			System.out.println("Miles Comparison is FAIL");
		}

		// Click on Calculate
		// Driver.findElement(By.id("btCalculate")).click();
		// Thread.sleep(10000);
		System.out.println("Mileage Calculation : " + CalMsg);
		System.out.println("Mileage Calculation : " + CalMsg2);
		// Click on Get Direction
		Driver.findElement(By.id("btnDirection")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		boolean mapcheck = Driver.findElement(By.xpath(".//*[@id='mapLoad']")).isDisplayed();

		if (mapcheck == false) {
			throw new Error("Error: Map Not Display on Get Direction");
		}

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "MileageCalc");

		// Expand and Collapse Note
		Driver.findElement(By.id("imgData")).click();
		Thread.sleep(2000);

		Driver.findElement(By.id("imgData")).click();

		// Reset
		Driver.findElement(By.id("btnReset")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}

}
