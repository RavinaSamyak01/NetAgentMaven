package netAgent_OperationsTab;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class TaskLog extends BaseInit {

	@Test
	public static void taskLog()
			throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=======TaskLog Test Start=======");
		msg.append("=======TaskLog Test Start=======" + "\n\n");

		// --Get total columns from the excel
		int colNum = getTotalCol("TaskLog");
		System.out.println("total No of Columns=" + colNum);
		logger.info("total No of Columns=" + colNum);

		String Reference = getData("TaskLog", 1, 4);
		logger.info("Reference=" + Reference);

		// Go To TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		isElementPresent("Operations_id").click();
		logger.info("Clicked on Operations");

		isElementPresent("TaskLog_linkText").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		getScreenshot(Driver, "TaskLog_Operations");

		// --Basic Search

		for (int col = 0; col < colNum; col++) { // --Search with PickUP ID

			String BPickUpID = getData("TaskLog", 1, col);
			logger.info("SearchID=" + BPickUpID);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
			isElementPresent("TLBasicSearch_id").clear();
			logger.info("Cleared BasicSearch");
			isElementPresent("TLBasicSearch_id").sendKeys(BPickUpID);
			logger.info("Entered value in BasicSearch");
			isElementPresent("TLBSearchBTN_id").click();
			logger.info("Clicked on Search button of BasicSearch");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --CHeck data is exist or not
			isDataExist();
		}

		// --Advance Search
		isElementPresent("TLAdSearchTab_id").click();
		logger.info("Clicked on Advance search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));
		getScreenshot(Driver, "AdvanceSearchTab");

		// --Search by Order Type
		for (int OType = 1; OType < 3; OType++) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			Select Ordertype = new Select(isElementPresent("TLADOrderType_id"));
			Ordertype.selectByIndex(OType);
			logger.info("Selected value from OrderType dropdown");
			isElementPresent("TLADSearchBTN_id").click();
			logger.info("Clicked on Search button of Advance search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				WebElement Stage = isElementPresent("TLStage_xpath");
				if (Stage.isDisplayed()) {
					System.out.println("Searched Job is displayed in edit mode");
					logger.info("Searched Job is displayed in edit mode");
					try {
						// --Click on Close button
						isElementPresent("TLCloseTab_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception close) {
						isElementPresent("TLIconClose_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					}
					// --Go to Advance Tab
					isElementPresent("TLAdSearchTab_id").click();
					logger.info("Clicked on Advance Search");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));
				}

			} catch (Exception e) {
				System.out.println("Data is not exist related search parameters");
				logger.info("Data is not exist related search parameters");

			}

		}
		// --Search by Next Task
		isElementPresent("TLNextTask_id").click();
		logger.info("Clicked on NextTask dropdown");
		Thread.sleep(2000);

		// -Select All
		isElementPresent("TLNextTaskCkAll_id").click();
		logger.info("Selected All values of NextTask dropdown");
		Thread.sleep(2000);
		if (isElementPresent("TLNextTaskCkAll_id").isSelected()) {
			System.out.println("Select All checkbox is checked");
			logger.info("Select All checkbox is checked");

		} else {
			System.out.println("Select All checkbox is not checked");
			logger.info("Select All checkbox is not checked");
		}
		// --Click on Search
		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Clicked on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		WebElement NoData = isElementPresent("NoData_className");
		if (NoData.isDisplayed()) {
			System.out.println("Data is not present related search parameter");
			logger.info("Data is not present related search parameter");
		} else {
			System.out.println("Data is present related search parameter");
			logger.info("Data is present related search parameter");
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		isElementPresent("TLNextTask_id").click();
		logger.info("Clicked on NextTask dropdown");
		Thread.sleep(2000);

		// Unselect All
		isElementPresent("TLNextTaskCkAll_id").click();
		logger.info("Deselect All values of NextTask dropdown");
		Thread.sleep(2000);
		if (isElementPresent("TLNextTaskCkAll_id").isSelected()) {
			System.out.println("Select All checkbox is checked");
			logger.info("Select All checkbox is checked");

		} else {
			System.out.println("Select All checkbox is not checked");
			logger.info("Select All checkbox is not checked");
		}
		// --Search by Service
		isElementPresent("TLService_id").sendKeys("LOC");
		logger.info("Entered Service");
		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		NoData = isElementPresent("NoData_className");
		if (NoData.isDisplayed()) {
			System.out.println("Data is not present related search parameter");
			logger.info("Data is not present related search parameter");
		} else {
			System.out.println("Data is present related search parameter");
			logger.info("Data is present related search parameter");
		}
		Thread.sleep(2000);

		// --Search by Expected From and Expected To
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date frmdt = new Date();
		System.out.println(frmdt);
		Date frmdt1 = addDays(frmdt, -20);
		System.out.println(frmdt1);
		String FromDate = dateFormat.format(frmdt1);
		System.out.println(FromDate);
		logger.info("Date value is=" + FromDate);

		// --Expected From
		isElementPresent("TLExpFrom_id").sendKeys(FromDate);
		logger.info("Entered date in Expected From");
		// --expected To
		isElementPresent("TLExpTo_id").sendKeys(FromDate);
		logger.info("Entered date in Expected To");
		isElementPresent("TLCustomer_id").click();
		logger.info("Click on Customer");
		WebElement SearchBTn = isElementPresent("TLADSearchBTN_id");
		act.moveToElement(SearchBTn).click().perform();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		if (NoData.isDisplayed()) {
			System.out.println("Data is not present related search parameter");
			logger.info("Data is not present related search parameter");
		} else {
			System.out.println("Data is present related search parameter");
			logger.info("Data is present related search parameter");
		}
		Thread.sleep(2000); // --Clear Expected From
		isElementPresent("TLExpFrom_id").clear();
		logger.info("Cleared Expected From");
		// --Clear expectedTo
		isElementPresent("TLExpTo_id").clear();
		logger.info("Cleared Expected To");
		isElementPresent("TLService_id").click();
		logger.info("Click on Search button of Advance Search");

		// --Search by Customer

		isElementPresent("TLCustomer_id").click();
		logger.info("Click on Customer");
		isElementPresent("TLCustomer_id").sendKeys("950654");
		logger.info("Enter value in Customer");
		isElementPresent("TLCustomer_id").click();
		logger.info("Click on Customer");
		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			WebElement PickuPBox = isElementPresent("TLPickUpBox_xpath");
			if (PickuPBox.isDisplayed()) {
				System.out.println("Searched Job is displayed in edit mode");
				logger.info("Searched Job is displayed in edit mode");
				// --Click on Close button
				isElementPresent("TLCloseTab_id").click();
				logger.info("Clicked on Close button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				// --Go to Advance Tab
				isElementPresent("TLAdSearchTab_id").click();
				logger.info("Click on Advance Search");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));
			}
		} catch (Exception e) {
			System.out.println("Data is not exist related search parameters");

		} // --Search by PickUp
		String PickUpID = getData("TaskLog", 1, 0);
		isElementPresent("TLPickUp_id").sendKeys(PickUpID);
		isElementPresent("TLADSearchBTN_id").click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			WebElement PickuPBox = isElementPresent("TLPickUpBox_xpath");
			if (PickuPBox.isDisplayed()) {
				System.out.println("Searched Job is displayed in edit mode");
				// --Click on Close button
				isElementPresent("TLCloseTab_id").click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				// --Go to Advance Tab
				isElementPresent("TLAdSearchTab_id").click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));
			}
		} catch (Exception e) {
			try {
				NoData = isElementPresent("NoData_className");
				if (NoData.isDisplayed()) {
					System.out.println("There is no job exist with Search parameter");
					logger.info("There is no job exist with Search parameter");

				} else {
					System.out.println("There is multiple job exist with Search parameter");
					logger.info("There is multiple job exist with Search parameter");
				}
			} catch (Exception NoData1) {
				System.out.println("There is no job exist with Search parameter");
				logger.info("There is no job exist with Search parameter");
			}

		}

		// --Search by ASN Type--PutAway
		// --Deselect OrderType
		Select Ordertype = new Select(isElementPresent("TLADOrderType_id"));
		Ordertype.selectByVisibleText("(select)");
		logger.info("Deselected OrderType");
		System.out.println("Deselected OrderType");

		// ---Remove PickUp
		isElementPresent("TLPickUp_id").clear();
		logger.info("Cleared Pickup");
		System.out.println("Cleared Pickup");

		// --Remove Service
		isElementPresent("TLService_id").clear();
		System.out.println("Cleared Service");
		logger.info("Cleared Pickup");

		// --Remove Customer
		isElementPresent("TLCustomer_id").clear();
		System.out.println("Cleared Customer");
		logger.info("Cleared Pickup");

		//// --Remove PickUp
		isElementPresent("TLPickUp_id").clear();
		System.out.println("Cleared PickUp");
		logger.info("Cleared Pickup");

		// --ASNType
		isElementPresent("TLASNType_id").click();
		logger.info("Click on ASNType dropdown");
		Thread.sleep(2000);
		// -Select All
		isElementPresent("TLASNReplenish_xpath").click();
		logger.info("Selected Replenish from ASNType dropdown");
		isElementPresent("TLASNType_id").click();
		System.out.println("Close the ASNType dropdown");
		logger.info("Cleared Pickup");

		// --Click on Search
		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			if (NoData.isDisplayed()) {
				System.out.println("Data is not present related search parameter");
				logger.info("Data is not present related search parameter");
			}

		} catch (Exception Nodata) {
			System.out.println("Data is present related search parameter");
			logger.info("Data is present related search parameter");
			String Orderstage = isElementPresent("TLStage_xpath").getText();
			System.out.println("Current stage of the order is=" + Orderstage);
			logger.info("Current stage of the order is=" + Orderstage);
			getScreenshot(Driver, "ReplenishMent_PutAway");

			// --Click on Receive to PutAway
			isElementPresent("TLRecToPutAway_linkText").click();
			System.out.println("Clicked on RECEIVE TO PUTAWAY");
			logger.info("Clicked on RECEIVE TO PUTAWAY");
			// --Enter Accepted Quantity
			// --table
			WebElement parttable = isElementPresent("TLPartTable_xpath");
			List<WebElement> Partrow = parttable.findElements(By.tagName("tr"));
			System.out.println("total No of rows in part table are==" + Partrow.size());
			logger.info("total No of rows in part table are==" + Partrow.size());

			for (int part = 0; part < Partrow.size(); part++) {
				// --Find SerialNo column
				try {
					WebElement SerialNo = Partrow.get(part).findElement(By.id("txtSerialNo"));
					act.moveToElement(SerialNo).build().perform();
					logger.info("Moved to SerialNo");
					wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
					SerialNo.clear();
					logger.info("Cleared SerialNo");
					SerialNo.sendKeys("SerialNo" + part);
					logger.info("Entered SerialNo" + part);
					System.out.println("Enetered serial Number in " + part + " part");
					logger.info("Enetered serial Number in " + part + " part");

					// --Enter Accepted Quantity
					WebElement AccQty = Partrow.get(part).findElement(By.id("txtReceivedQty"));
					act.moveToElement(AccQty).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(AccQty));
					AccQty.clear();
					logger.info("Cleared Accepted Qty");
					AccQty.sendKeys("1");
					logger.info("Entered Accepted Qty");
					AccQty.sendKeys(Keys.TAB);
					System.out.println("Enetered Accepted Quantity in " + part + " part");
					logger.info("Enetered Accepted Quantity in " + part + " part");
					// --Click on Save
					wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
					WebElement Save = isElementPresent("TLPTSave_id");
					act.moveToElement(Save).click().perform();
					logger.info("Click on Save");
					System.out.println("Clicked on the Save");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				} catch (Exception staleelement) {
					try {
						WebElement parttable1 = isElementPresent("TLPartTable_xpath");
						List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
						System.out.println("total No of rows in part table are==" + Partrow.size());
						logger.info("total No of rows in part table are==" + Partrow.size());
						for (int partR = part; partR < Partrow1.size();) {
							WebElement SerialNo = Partrow1.get(partR).findElement(By.id("txtSerialNo"));
							logger.info("Moved to SerialNo");
							wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
							SerialNo.clear();
							logger.info("Cleared SerialNo");
							SerialNo.sendKeys("SerialNo" + part);
							logger.info("Entered SerialNo" + part);
							System.out.println("Enetered serial Number in " + part + " part");
							logger.info("Enetered serial Number in " + part + " part");

							// --Enter Accepted Quantity
							WebElement AccQty = Partrow1.get(partR).findElement(By.id("txtReceivedQty"));
							act.moveToElement(AccQty).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(AccQty));
							AccQty.clear();
							logger.info("Cleared Accepted Qty");
							AccQty.sendKeys("1");
							logger.info("Entered Accepted Qty");
							AccQty.sendKeys(Keys.TAB);
							System.out.println("Enetered Accepted Quantity in " + part + " part");
							logger.info("Enetered Accepted Quantity in " + part + " part");
							// --Click on Save
							wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
							WebElement Save = isElementPresent("TLPTSave_id");
							act.moveToElement(Save).click().perform();
							logger.info("Click on Save");
							System.out.println("Clicked on the Save");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							break;
						}
					} catch (Exception StaleElement) {
						WebElement parttable1 = isElementPresent("TLPartTable_xpath");
						List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
						System.out.println("total No of rows in part table are==" + Partrow.size());
						logger.info("total No of rows in part table are==" + Partrow.size());
						for (int partRw = part; partRw < Partrow1.size();) {
							WebElement SerialNo = Partrow1.get(partRw).findElement(By.id("txtSerialNo"));
							logger.info("Moved to SerialNo");
							wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
							SerialNo.clear();
							logger.info("Cleared SerialNo");
							SerialNo.sendKeys("Entered SerialNo" + part);
							logger.info("Entered SerialNo" + part);
							System.out.println("Enetered serial Number in " + part + " part");
							logger.info("Enetered serial Number in " + part + " part");

							// --Enter Accepted Quantity
							WebElement AccQty = Partrow1.get(partRw).findElement(By.id("txtReceivedQty"));
							act.moveToElement(AccQty).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(AccQty));
							AccQty.clear();
							logger.info("Cleared Accepted Qty");
							AccQty.sendKeys("1");
							logger.info("Entered Accepted Qty");
							AccQty.sendKeys(Keys.TAB);
							System.out.println("Enetered Accepted Quantity in " + part + " part");
							logger.info("Enetered Accepted Quantity in " + part + " part");
							// --Click on Save
							wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
							WebElement Save = isElementPresent("TLPTSave_id");
							act.moveToElement(Save).click().perform();
							logger.info("Click on Save");
							System.out.println("Clicked on the Save");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							break;
						}
					}
				}
			}

			// --Click on Update
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idupdateicon")));
			WebElement update = isElementPresent("TLPTUpdate_id");
			act.moveToElement(update).click().perform();
			logger.info("Click on Update");
			System.out.println("Clicked on the update");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successid")));
			String SuccMsg = isElementPresent("TLSuccess_id").getText();
			System.out.println("Success Message==" + SuccMsg);
			logger.info("Success Message==" + SuccMsg);

			// --Close button
			WebElement Close = isElementPresent("TLIconClose_id");
			act.moveToElement(Close).click().perform();
			System.out.println("Clicked on the Close");
			logger.info("Click on Close");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Go to Advance Tab
			isElementPresent("TLAdSearchTab_id").click();
			logger.info("Click on Advance Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

		}

		// Deselect ASN Type
		isElementPresent("TLASNType_id").click();
		logger.info("Click on ASNType dropdown");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.elementToBeClickable(isElementPresent("TLASNReplenish_xpath")));
		// -Select All
		isElementPresent("TLASNReplenish_xpath").click();
		logger.info("Deselect the selected value");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.elementToBeClickable(isElementPresent("TLASNType_id")));
		isElementPresent("TLASNType_id").click();
		logger.info("Close ASNType dropdown");
		System.out.println("Deselected ASN Type");

		/*
		 * // --Search by Carrier
		 * wait.until(ExpectedConditions.elementToBeClickable(By.id("cmbCarrier1")));
		 * Driver.findElement(By.id("cmbCarrier1")).click(); Thread.sleep(2000); //
		 * -Select All Driver.findElement(By.id("chkAllcmbCarrier1")).click();
		 * Thread.sleep(2000); if
		 * (Driver.findElement(By.id("chkAllcmbCarrier1")).isSelected()) {
		 * System.out.println("Select All checkbox is checked"); } else {
		 * System.out.println("Select All checkbox is not checked"); } // --Click on
		 * Search isElementPresent("TLADSearchBTN_id").click();
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * NoData = Driver.findElement(By.className("dx-datagrid-nodata")); if
		 * (NoData.isDisplayed()) {
		 * System.out.println("Data is not present related search parameter"); } else {
		 * System.out.println("Data is present related search parameter"); }
		 * 
		 * // Unselect All Driver.findElement(By.id("cmbCarrier1")).click();
		 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbCarrier1")).click();
		 * Thread.sleep(2000); if
		 * (Driver.findElement(By.id("chkAllcmbCarrier1")).isSelected()) {
		 * System.out.println("Select All checkbox is checked"); } else {
		 * System.out.println("Select All checkbox is not checked"); }
		 */

		// --Search by Reference

		isElementPresent("TLReference_id").sendKeys(Reference);
		logger.info("Entered value in Reference");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.elementToBeClickable(isElementPresent("TLADSearchBTN_id")));
		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --CHeck data is exist or not
		isDataExist();

		// --Clear Reference
		isElementPresent("TLReference_id").clear();
		logger.info("Cleared Reference");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.elementToBeClickable(isElementPresent("TLADSearchBTN_id")));
		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		String TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job is/are==" + TotalJob);
		logger.info("Total No of job is/are==" + TotalJob);

		// --Quarantine Window
		isElementPresent("TLQuarn_linkText").click();
		logger.info("Click on Quarantine link");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idAddQuarantinePopup")));
		getScreenshot(Driver, "QuarantineWindow");

		// --FSL Default selection
		// boolean FSL = Driver.findElement(By.id("cmbFSL")).isSelected(); //
		// Assert.assertTrue(FSL);
		// --Account
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cmbAccount\"]")));
		WebElement FSLDrp = isElementPresent("TLQFSL_id");
		js.executeScript("arguments[0].click();", FSLDrp);
		logger.info("Click on FSL dropdown");
		Select FSL = new Select(isElementPresent("TLQFSL_id"));
		Thread.sleep(2000);
		FSL.selectByVisibleText("AUTOMATION-FSL (F5386)");
		logger.info("Select FSL");

		WebElement ACC = isElementPresent("TLQAccount_xpath");
		js.executeScript("arguments[0].click();", ACC);
		logger.info("Click on Account dropdown");
		Select Account = new Select(isElementPresent("TLQAccount_xpath"));
		Thread.sleep(2000);
		Account.selectByVisibleText("AUTOMATION - SPRINT #950656");
		logger.info("Select account");
		Thread.sleep(5000); // --Sprint Part Number
		isElementPresent("TLQField1_id").sendKeys("123");
		logger.info("Enter value of Field1");
		// --IMN
		isElementPresent("TLQField2_id").sendKeys("123");
		logger.info("Enter value of Field2");
		// --MFG Part Number
		isElementPresent("TLQField3_id").sendKeys("123");
		logger.info("Enter value of Field3");
		// --SP Number
		isElementPresent("TLQField4_id").sendKeys("123");
		logger.info("Enter value of Field4");
		// --MFG Serial
		isElementPresent("TLQSerailNo_id").sendKeys("123");
		logger.info("Enter value of SerialNo");

		// --FSL Default selection

		/*
		 * boolean BLoc = Driver.findElement(By.id("cmbLocation")).isSelected();
		 * Assert.assertTrue(BLoc);
		 */

		// -Quantity
		// Driver.findElement(By.id("txtAddQuarantineQty")).sendKeys("");
		// --Category
		Select Category = new Select(isElementPresent("TLQCategory_id"));
		Category.selectByVisibleText("(select)");
		logger.info("Select Category from Category dropdown");
		// --Carrier
		Select Carrier = new Select(isElementPresent("TLQCarrier_id"));
		Carrier.selectByVisibleText("NGL");
		logger.info("Select NGL from Carrier dropdown");
		// --Carrier Tracking
		isElementPresent("TLQCarrTracking_id").sendKeys("123");
		logger.info("Entered Carrier Tracking ID");
		// --Reference
		isElementPresent("TLQRef_id").sendKeys("RV123");
		logger.info("Entered Reference");

		// --Quarantine Reason
		Select QuarReas = new Select(isElementPresent("TLQReason_id"));
		QuarReas.selectByVisibleText("Expected Parts Not Received");
		logger.info("Select Quarantine reason from dropdown");

		// --Notes
		isElementPresent("TLQNotes_id")
				.sendKeys("Automation Testing:-This part is quarantine due to expected parts not Received");
		logger.info("Entered Notes");
		// --Upload files
		// --click on Upload files
		isElementPresent("UploadBTN_id").click();
		logger.info("Click on Upload button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
		// --Click on Plus sign
		isElementPresent("PlusSign_id").click();
		logger.info("Click on Plus sign");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtDocName")));
		// --Enter Doc name
		isElementPresent("DocName_id").sendKeys("AutoDocument");
		logger.info("Enter Document Name");
		WebElement UploadFile = isElementPresent("SelectFile_id");
		act.moveToElement(UploadFile).click().perform();
		logger.info("Click on Upload File button");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"userForm\"]")));
		String Fpath = "C:\\Users\\rprajapati\\git\\NetAgent\\NetAgentProcess\\Job Upload Doc STG.xls";
		WebElement InFile = isElementPresent("InputFile_id");
		InFile.sendKeys(Fpath);
		logger.info("Send file to input file");
		Thread.sleep(2000);
		// --Click on Upload btn
		isElementPresent("BTNUpload_id").click();
		logger.info("Click on Upload button");
		Thread.sleep(2000);
		try {
			String ErrorMsg = isElementPresent("ErrorMsg_xpath").getText();
			if (ErrorMsg.contains("already exists.Your file was saved as")) {
				System.out.println("File already exist in the system");
				logger.info("File already exist in the system");
			}
		} catch (Exception e) {
			System.out.println("File is uploaded successfully");
			logger.info("File is uploaded successfully");
		}
		isElementPresent("BTNOk_id").click();
		logger.info("Click on OK button");
		Thread.sleep(2000);

		// --Submit button
		isElementPresent("TLQSubmit_id").click();
		logger.info("Click on Submit button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblSuccessMsg")));

		String ActSuccMsg = isElementPresent("TLSuccMsg_id").getText();
		String ExpSucMsg = "Direct Quarantine is successfully created with";
		if (ActSuccMsg.contains(ExpSucMsg)) {
			System.out.println("Part is quarantine successfully");
			logger.info("Part is quarantine successfully");
		} else {
			System.out.println("Part is not quarantine");
			logger.info("Part is not quarantine");
		}

		getScreenshot(Driver, "PartQuarantined");

		// --Close button
		WebElement close = isElementPresent("TLQClose_id");
		js.executeScript("arguments[0].click()", close);
		logger.info("Click on Close button");
		Thread.sleep(2000);

		// --Refresh
		isElementPresent("TLRefresh_id").click();
		logger.info("Click on Refresh button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		getScreenshot(Driver, "AdvanceSearch_Refresh");

		// --MERGE RTE Tab
		isElementPresent("TLMergeRTEBTN_id").click();
		logger.info("Click on Merge RTE Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ngdialog-message")));
		getScreenshot(Driver, "MergeRTETab");
		// --Get the Messages
		String Warning = isElementPresent("TLMRTEWarning_xpath").getText();
		System.out.println("Warning==" + Warning);
		logger.info("Warning==" + Warning);
		// --Get all the conditions for merge RTE
		List<WebElement> Conditons = Driver.findElements(By.xpath("//*[@ng-show=\"isWarningRTE\"]//ul"));
		for (int i = 0; i < Conditons.size(); i++) {
			String Condition = Conditons.get(i).getText();
			System.out.println(Condition);
			logger.info("Conditions for Merge RTE==" + Condition);
		}
		// --Close the window

		WebElement MergeClose = isElementPresent("TLMergeClose_className");
		js.executeScript("arguments[0].click()", MergeClose);
		logger.info("Click on Close button of Merge RTE popup");
		Thread.sleep(2000);

		// --Merge RTE with Selection of Job
		// --Search by PU DRV Conf LOC Job

		// --Deselect OrderType
		Ordertype = new Select(isElementPresent("TLADOrderType_id"));
		Ordertype.selectByVisibleText("(select)");
		System.out.println("Deselected OrderType");
		logger.info("Deselected OrderType");

		// ---Remove PickUp
		isElementPresent("TLPickUp_id").clear();
		System.out.println("Cleared Pickup");
		logger.info("Cleared Pickup");

		// --Next task
		isElementPresent("TLNextTask_id").click();
		logger.info("Click on NextTask dropdown");
		Thread.sleep(2000);
		Driver.findElement(By.xpath("//label[contains(text(),' PU DRV CONF')]")).click();
		logger.info("Selected PU DRV CONF stage from dropdown");
		Thread.sleep(2000);
		isElementPresent("TLNextTask_id").click();
		logger.info("Close NextTask dropdown");
		System.out.println("Selected PU DRV CONF stage");

		// --Enter Service
		isElementPresent("TLService_id").clear();
		logger.info("Cleared Service");
		isElementPresent("TLService_id").sendKeys("LOC");
		logger.info("Entered Service");
		System.out.println("Entered Service");

		// --Enter Customer
		isElementPresent("TLCustomer_id").clear();
		logger.info("Cleared Customer");
		isElementPresent("TLCustomer_id").sendKeys("950655");
		logger.info("Entered Customer");
		System.out.println("Entered Customer");

		isElementPresent("TLADSearchBTN_id").click();
		logger.info("Click on Search button of Advance Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		try {
			if (NoData.isDisplayed()) {
				System.out.println("Data is not present related search parameter");
				logger.info("Data is not present related search parameter");
			} else {
				System.out.println("Data is present related search parameter");
				logger.info("Data is present related search parameter");

				Thread.sleep(2000);

				try {
					// --select 1st row
					isElementPresent("TLMRTESelectRow_xpath").click();
					logger.info("Selected 1st Row");
					String FstRow = isElementPresent("TLMRTESelectRow_xpath").getAttribute("aria-checked");
					Thread.sleep(2000);
					Assert.assertEquals(FstRow, "true");

					// --Merge RTE
					isElementPresent("TLMergeRTEBTN_id").click();
					logger.info("Click on Merge RTE");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ngdialog-message")));

					// --Route List
					wait.until(ExpectedConditions.elementToBeClickable(By.id("drpRouteList")));
					Select RList = new Select(isElementPresent("TLMRTERoutList_id"));
					RList.selectByIndex(1);
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					System.out.println("Selected RW List");
					logger.info("Selected value from Route List");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					// --Merge Button
					isElementPresent("TLMergeSave_id").click();
					logger.info("Click on Merge button");
					System.out.println("Clicked on Merge button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblSuccessMsg")));

					String SuccMsg = isElementPresent("TLSuccMsg_id").getText();
					System.out.println("Success message=" + SuccMsg + "\n");
					logger.info("Success message=" + SuccMsg + "\n");

					// Cancel button
					isElementPresent("TLMergeCancel_id").click();
					logger.info("Click on Cancel button of Merge RTE popup");
					Thread.sleep(2000);
				} catch (Exception e) {
					System.out.println("Warning message is displayed");
					logger.info("Warning message is displayed");
					MergeClose = isElementPresent("TLMergeClose_className");
					js.executeScript("arguments[0].click()", MergeClose);
					logger.info("Click on Close button of Merge RTE popup");
					Thread.sleep(2000);
				}
			}
		} catch (

		Exception editor) {
			System.out.println("There is only 1 record with search parameters");
			logger.info("There is only 1 record with search parameters");
			isElementPresent("TLIconCls_id").click();
			logger.info("Click on Close button");
			System.out.println("Clicked on Close button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}

		// --Operation Tab
		isElementPresent("TLOperationTab_id").click();
		logger.info("Click on Operation Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("divTable")));

		getScreenshot(Driver, "OperationTab");
		// --Search
		for (int col = 0; col < colNum; col++) {
			// --Search
			String SearchID = getData("TaskLog", 1, col);
			logger.info("SearchID is==" + SearchID);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
			isElementPresent("TLOSearch_id").clear();
			logger.info("Cleared Search box");
			isElementPresent("TLOSearch_id").sendKeys(SearchID);
			logger.info("Enter value in search box");
			isElementPresent("TLOSearchBTN_id").click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --CHeck data is exist or not
			isDataExist();
		}

		// --Inventory Tab
		isElementPresent("TLInventoryTab_id").click();
		logger.info("Click on Inventory Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("WO")));
		getScreenshot(Driver, "InventoryTab");
		TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in Inventory Tab is/are==" + TotalJob);
		logger.info("Total No of job in Inventory Tab is/are==" + TotalJob);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Combined Tab
		wait.until(ExpectedConditions.elementToBeClickable(By.id("combined")));
		isElementPresent("TLCombinedTab_id").click();
		logger.info("Click on Combined Tab");

		// --RTE Tab
		isElementPresent("TLRTETab_xpath").click();
		logger.info("Click on RTE Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RTE")));
		getScreenshot(Driver, "RTETab");
		TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in RTE Tab is/are==" + TotalJob);
		logger.info("Total No of job in RTE Tab is/are==" + TotalJob);
		// --Search
		String Search = getData("TaskLog", 1, 7);
		logger.info("Search value is==" + Search);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
		isElementPresent("TLRTESearch_id").clear();
		logger.info("Cleared Search box");
		isElementPresent("TLRTESearch_id").sendKeys(Search);
		logger.info("Entered value in Search box");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRTESearch2")));
		isElementPresent("TLRTESearchBTN_id").click();
		logger.info("Click on Search button of RTE tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in RTE Tab is/are==" + TotalJob);
		logger.info("Total No of job in RTE Tab is/are==" + TotalJob);

		// --Clear search
		isElementPresent("TLRTESearch_id").clear();
		logger.info("Cleared Search box");
		isElementPresent("TLRTESearchBTN_id").click();
		logger.info("Click on Search button of RTE Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in RTE Tab is/are==" + TotalJob);
		logger.info("Total No of job in RTE Tab is/are==" + TotalJob);

		if (TotalJob.startsWith("0")) {
			System.out.println("There is no job exist");
			logger.info("There is no job exist");
		} else {
			System.out.println("Job is/are exist");
			logger.info("Job is/are exist");
			// -_Horizontal scroll
			WebElement scrollArea = isElementPresent("TLRTECol15_xpath");
			act.moveToElement(scrollArea).build().perform();
			logger.info("Performed horizontal scroll");
			// --memo
			isElementPresent("TLRTEMemo_id").click();
			logger.info("Click on Memo");
			System.out.println("Clicked on Memo");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			getScreenshot(Driver, "Memo_" + Search);
			// --Enter memo
			isElementPresent("TLRTEMemoIn_id").sendKeys("This is RTE job for Automation");
			logger.info("Entered memo");
			System.out.println("Entered memo");
			// --save memo
			isElementPresent("TLRTEMSave_id").click();
			logger.info("Clicked on Save of memo");
			System.out.println("Clicked on Save");
			// --close
			close = isElementPresent("TLQClose_id");
			js.executeScript("arguments[0].click()", close);
			logger.info("Click on Close button of memo");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Print label
			// -_Horizontal scroll
			scrollArea = isElementPresent("TLRTECol15_xpath");
			act.moveToElement(scrollArea).build().perform();
			logger.info("Performed horizontal scroll");
			WebElement PLabel = isElementPresent("TLRTEPrintL_linkText");
			act.moveToElement(PLabel).click().perform();
			logger.info("Click on Print Label");
			System.out.println("Clicked on Print Label");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			// --Print Label new window
			String WindowHandlebefore = Driver.getWindowHandle();
			for (String windHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(windHandle);
				System.out.println("Switched to Print Label window");
				logger.info("Switched to Print Label window");
				Thread.sleep(5000);
				getScreenshot(Driver, "PrintLabel_" + Search);

			}
			Driver.close();
			System.out.println("Closed Print Label window");
			logger.info("Closed Print Label window");

			Driver.switchTo().window(WindowHandlebefore);
			System.out.println("Switched to main window");
			logger.info("Switched to main window");

			// --Print 4*6 label
			// -_Horizontal scroll
			scrollArea = isElementPresent("TLRTECol15_xpath");
			act.moveToElement(scrollArea).build().perform();
			logger.info("Performed horizontal scroll");
			WebElement P46Label = isElementPresent("TLRTEPL46_linkText");
			act.moveToElement(P46Label).click().perform();
			logger.info("Clicked on Print 4x6 Label");
			System.out.println("Clicked on Print 4x6 Label");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			// --Print Label new window
			WindowHandlebefore = Driver.getWindowHandle();
			for (String windHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(windHandle);
				logger.info("Switched to Print 4x6 Label window");
				System.out.println("Switched to Print 4x6 Label window");
				Thread.sleep(5000);
				getScreenshot(Driver, "Print46Label_" + Search);

			}
			Driver.close();
			System.out.println("Closed Print 4x6 Label window");
			logger.info("Closed Print 4x6 Label window");

			Driver.switchTo().window(WindowHandlebefore);
			System.out.println("Switched to main window");
			logger.info("Switched to main window");
		}

		// Go to main screen.
		isElementPresent("MNXLogo_id").click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		logger.info("=======TaskLog Test End=======");
		msg.append("=======TaskLog Test End=======" + "\n\n");

	}

	public static void isDataExist() {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		try {
			try {
				WebElement PickuPBox = isElementPresent("TLPickUpBox_xpath");
				if (PickuPBox.isDisplayed()) {
					System.out.println("Job is displayed in edit mode");
					logger.info("Job is displayed in edit mode");
					// --Click on Close button
					try {
						isElementPresent("TLCloseTab_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception close) {
						try {
							isElementPresent("TLCloseIcon_id").click();
							logger.info("Clicked on close button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception DelClose) {
							isElementPresent("TLDClose_id").click();
							logger.info("Clicked on close button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}
					}

				}
			} catch (Exception PickUp) {
				WebElement Stage = isElementPresent("TLStage_xpath");
				if (Stage.isDisplayed()) {
					System.out.println("Job is displayed in edit mode");
					logger.info(" Job is displayed in edit mode");
					// --Click on Close button
					try {
						isElementPresent("TLIconClose_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception close) {
						isElementPresent("TLCloseIcon_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					}

				}
			}

		} catch (Exception e) {
			try {
				WebElement NoData = isElementPresent("NoData_className");
				if (NoData.isDisplayed()) {
					System.out.println("There is no job exist with search parameters");
					logger.info("There is no job exist with search parameters");

				} else {
					System.out.println("There is multiple job exist with search parameters");
					logger.info("There is multiple job exist with search parameters");
				}
			} catch (Exception NoData1) {
				System.out.println("There is no job exist with search parameters ");
				logger.info("There is no job exist with search parameters");
			}

		}
	}

}
