package netAgent_OperationsTab;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

@Test
public class CCAttempt extends BaseInit {
	public void ccAttempt()
			throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException, AWTException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		Robot robot = new Robot();

		// Go To TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		isElementPresent("Operations_id").click();
		logger.info("Clicked on Operations");

		isElementPresent("TaskLog_linkText").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

		logger.info("===CCAttempt Test Start===");
		msg.append("===CCAttempt Test Start===" + "\n\n");
		// --Inventory Tab
		isElementPresent("TLInventoryTab_id").click();
		logger.info("Click on Inventory Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("WO")));
		getScreenshot(Driver, "InventoryTab");
		String TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in Inventory Tab is/are==" + TotalJob);
		logger.info("Total No of job in Inventory Tab is/are==" + TotalJob);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --CCAttempt 1 Scenario
		// --Search job
		String WorderID = getData("OrderSearch", 1, 13);
		logger.info("WorkOrderID is==" + WorderID);
		isElementPresent("TLInvSearch_id").clear();
		logger.info("Clear Search box");
		isElementPresent("TLInvSearch_id").sendKeys(WorderID);
		logger.info("Enter value in search input");
		isElementPresent("TLInvSearchBTN_id").click();
		logger.info("Clicked on Search");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Get the stage name
		// try {
		String Stage = isElementPresent("CCATTStage_xpath").getText();
		logger.info("Stage is==" + Stage);
		String TotalParts = null;
		// --Checking total parts
		try {
			String TotalParts1 = isElementPresent("CCATotalParts_xpath").getText();
			TotalParts = TotalParts1;
		} catch (Exception TotalPartEx) {
			String TotalParts1 = isElementPresent("CCATotalParts2_xpath").getText();
			TotalParts = TotalParts1;
		}
		String[] TotalPts = TotalParts.split(":");
		String TotalPart = TotalPts[1].trim();
		int TParts = Integer.parseInt(TotalPart);
		// --if parts >0 check page count and status
		if (TParts > 0) {
			logger.info("Parts is available, Total Parts==" + TParts);

			// try {
			String PageCount = isElementPresent("PageCount_className").getText();
			logger.info("Total number of records==" + PageCount);
			WebElement CycleReCOunt = isElementPresent("CCACycleRCount_id");
			if (CycleReCOunt.isDisplayed()) {
				logger.info("It is CCAttempt1");
				logger.info("==Testing CCAttempt1==");
				// --Export
				isElementPresent("CCAExport_id").click();
				logger.info("Clicked on Export button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Import
				isElementPresent("CCAImport_id").click();
				logger.info("Clicked on Import button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Click on Select File
				String Fpath = "C:\\Users\\rprajapati\\git\\NetAgentMaven\\NetAgent\\src\\main\\resources\\CycleCount-1685201-13Apr22044656300.xlsx";
				WebElement InFile = isElementPresent("InputFile_id");
				InFile.sendKeys(Fpath);
				logger.info("Send file to input file");
				Thread.sleep(2000);
				// --Click on Upload btn
				isElementPresent("BTNUpload_id").click();
				logger.info("Click on Upload button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
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

				// --Exception View
				WebElement CCEX = isElementPresent("CCAExcepView_id");
				wait.until(ExpectedConditions.elementToBeClickable(CCEX));
				js.executeScript("arguments[0].click();", CCEX);
				logger.info("Clicked on Exception View");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("gridExceptionView")));

				// --Send Email with Blank Address
				isElementPresent("CCAEVEmail_id").clear();
				logger.info("Clear Email input");
				isElementPresent("CCAEVEmail_id").sendKeys("Ravik.com");
				logger.info("Enter value in Email input");
				// --Click on send button
				isElementPresent("CCAEVESend_id").click();
				logger.info("Click on Send button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				WebElement EmailFail = isElementPresent("CCAEEFail_id");
				if (EmailFail.isDisplayed()) {
					logger.info("EmailID validation is displayed==" + EmailFail.getText());

				} else {
					logger.info("EmailID validation is not displayed");

				}

				// --Send Email with Invalid Address
				isElementPresent("CCAEVEmail_id").clear();
				logger.info("Clear Email input");
				isElementPresent("CCAEVEmail_id").sendKeys("Ravik.com");
				logger.info("Enter value in Email input");
				// --Click on send button
				isElementPresent("CCAEVESend_id").click();
				logger.info("Click on Send button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				EmailFail = isElementPresent("CCAEEFail_id");
				if (EmailFail.isDisplayed()) {
					logger.info("Valid EmailID validation is displayed==" + EmailFail.getText());

				} else {
					logger.info("Valid EmailID validation is not displayed");

				}

				// --Send Email with valid Address
				isElementPresent("CCAEVEmail_id").clear();
				logger.info("Clear Email input");
				isElementPresent("CCAEVEmail_id").sendKeys("Ravina.prajapati@samyak.com");
				logger.info("Enter value in Email input");
				// --Click on send button
				isElementPresent("CCAEVESend_id").click();
				logger.info("Click on Send button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				WebElement EmailSUccess = isElementPresent("CCAEVESucc_id");
				if (EmailSUccess.isDisplayed()) {
					logger.info("Email successfully send==" + EmailSUccess.getText());

				} else {
					logger.info("Email is not send successfully");

				}

				// Export
				// --Click on send button
				isElementPresent("CCAEExport_id").click();
				logger.info("Click on Export button of Exception View");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				Thread.sleep(7000);
				System.out.println("Windows==" + Driver.getWindowHandle());
				// --Close Exception View pop up
				WebElement EXClose = isElementPresent("TLMergeClose_className");
				js.executeScript("arguments[0].click();", EXClose);
				logger.info("Click on Close button of Exception View");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Enter value in Qty and get status
				WebElement PartTable = isElementPresent("CCACCATable_xpath");
				List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
				logger.info("Total parts are==" + Partrow.size());
				for (int part = 0; part < Partrow.size() - 1; part++) {

					// --Get the result value
					WebElement Result = Partrow.get(part)
							.findElement(By.xpath("//*[contains(@aria-label,'Column Result')]"));
					String ResultValue = Result.getText();
					logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

					if (ResultValue.equalsIgnoreCase("FAIL")) {
						WebElement StockQtyxpath = Partrow.get(part)
								.findElement(By.xpath("//*[contains(@aria-label,'Column Stock Qty')]"));
						String StockQty = StockQtyxpath.getText().trim();
						logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
						WebElement CCQtyxpath = Partrow.get(part)
								.findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]"));
						String CCQty = CCQtyxpath.getText().trim();
						logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
						if (StockQty.equalsIgnoreCase(CCQty)) {
							logger.info("Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

						} else {
							logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

						}

						CCQtyxpath = Partrow.get(part)
								.findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]"));
						js.executeScript("arguments[0].click();", CCQtyxpath);
						logger.info("Clicked on CCQty");
						try {
							wait.until(ExpectedConditions
									.elementToBeClickable(Partrow.get(part).findElement(By.tagName("input"))));
							WebElement CCQtyInput = Partrow.get(part).findElement(By.tagName("input"));
							// enter stock qty in cc qty
							CCQtyInput.clear();
							CCQtyInput.sendKeys(Keys.BACK_SPACE);
							CCQtyInput.sendKeys(StockQty);
							CCQtyInput.sendKeys(Keys.TAB);

							logger.info("Entered CC Qty equal to Stock Qty");
						} catch (Exception NotFoun) {
							WebElement PartTable1 = isElementPresent("CCACCATable_xpath");
							List<WebElement> Partrow1 = PartTable1.findElements(By.tagName("tr"));
							logger.info("Total parts are==" + Partrow1.size());
							for (int part1 = part; part1 < Partrow1.size() - 1;) {
								WebElement Result1 = Partrow1.get(part1)
										.findElement(By.xpath("//*[contains(@aria-label,'Column Result')]"));
								String ResultValue1 = Result1.getText();
								CCQtyxpath = Partrow1.get(part1)
										.findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]"));
								js.executeScript("arguments[0].click();", CCQtyxpath);
								logger.info("Clicked on CCQty");
								wait.until(ExpectedConditions
										.elementToBeClickable(Partrow1.get(part1).findElement(By.tagName("input"))));
								WebElement CCQtyInput = Partrow1.get(part1).findElement(By.tagName("input"));
								// enter stock qty in cc qty
								CCQtyInput.clear();
								CCQtyInput.sendKeys(Keys.BACK_SPACE);
								CCQtyInput.sendKeys(StockQty);
								CCQtyInput.sendKeys(Keys.TAB);
								logger.info("Entered CC Qty equal to Stock Qty");

								logger.info("Value of Result of " + (part1 + 1) + " Part is==" + ResultValue1);
								if (ResultValue.equalsIgnoreCase("SUCCESS")) {
									logger.info("Stock Qty and CC Qty are same, Result is actually SUCCESS");

								} else {
									logger.info("Stock Qty and CC Qty are same, Result must be SUCCESS, but it is=="
											+ ResultValue1);

								}
								break;
							}
						}

					} else if (ResultValue.equalsIgnoreCase("SUCCESS")) {
						WebElement StockQtyxpath = Partrow.get(part)
								.findElement(By.xpath("//*[contains(@aria-label,'Column Stock Qty')]"));
						String StockQty = StockQtyxpath.getText().trim();
						logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
						WebElement CCQtyxpath = Partrow.get(part)
								.findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]"));
						String CCQty = CCQtyxpath.getText().trim();
						logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
						if (StockQty.equalsIgnoreCase(CCQty)) {
							logger.info("Stock Qty and CC Qty are Same, Result is actually SUCCESS");

						} else {
							logger.info("Stock Qty and CC Qty are different, Result is FAIL instead of SUCCESS");

						}
						logger.info("Stock Qty and CC Qty are same, Result is actually SUCCESS");
						// enter stock qty in cc qty
						WebElement CCQtyInput = CCQtyxpath.findElement(By.tagName("input"));
						CCQtyInput.sendKeys("0");
						CCQtyInput.sendKeys(Keys.TAB);
						logger.info("Entered CC Qty different from Stock Qty");
						ResultValue = Result.getText();
						logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);
						if (ResultValue.equalsIgnoreCase("FAIL")) {
							logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

						} else {
							logger.info("Stock Qty and CC Qty are different, Result must be FAIL, but it is=="
									+ ResultValue);

						}

					}
					break;

				}

				// --Save
				WebElement Save = isElementPresent("CCASave_id");
				act.moveToElement(Save).click().perform();
				logger.info("Click on Save button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			/*
			 * } catch (Exception CycleReCount) { try { String PageCount =
			 * isElementPresent("PageCount_className").getText();
			 * logger.info("Total number of records==" + PageCount); WebElement
			 * RemoveZeroQty = isElementPresent("RemoveZrQTY_id"); if
			 * (RemoveZeroQty.isDisplayed()) { logger.info("It is CCAttempt2"); // --Enter
			 * value in Qty and get status WebElement PartTable =
			 * isElementPresent("CCACCATable_xpath"); List<WebElement> Partrow =
			 * PartTable.findElements(By.tagName("tr")); logger.info("Total parts are==" +
			 * Partrow.size()); for (int part = 0; part < Partrow.size(); part++) {
			 * 
			 * // --Get the result value WebElement Result = Partrow.get(part)
			 * .findElement(By.xpath("//*[contains(@aria-label,'Column Result')]")); String
			 * ResultValue = Result.getText(); logger.info("Value of Result of " + (part +
			 * 1) + " Part is==" + ResultValue);
			 * 
			 * if (ResultValue.equalsIgnoreCase("FAIL")) { WebElement StockQtyxpath =
			 * Partrow.get(part)
			 * .findElement(By.xpath("//*[contains(@aria-label,'Column Stock Qty')]"));
			 * String StockQty = StockQtyxpath.getText().trim();
			 * logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
			 * WebElement CCQtyxpath = Partrow.get(part)
			 * .findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]")); String
			 * CCQty = CCQtyxpath.getText().trim(); logger.info("CC Qty for Part " + (part +
			 * 1) + " is==" + CCQty);
			 * logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");
			 * // enter stock qty in cc qty WebElement CCQtyInput =
			 * CCQtyxpath.findElement(By.tagName("input")); CCQtyInput.sendKeys(StockQty);
			 * CCQtyInput.sendKeys(Keys.TAB); //
			 * Driver.findElement(By.xpath(CCQtyxpath)).sendKeys(StockQty);
			 * logger.info("Entered CC Qty equal to Stock Qty"); ResultValue =
			 * Result.getText(); logger.info("Value of Result of " + (part + 1) +
			 * " Part is==" + ResultValue); if (ResultValue.equalsIgnoreCase("SUCCESS")) {
			 * logger.info("Stock Qty and CC Qty are same, Result is actually SUCCESS");
			 * 
			 * } else { logger.
			 * info("Stock Qty and CC Qty are same, Result must be SUCCESS, but it is==" +
			 * ResultValue);
			 * 
			 * }
			 * 
			 * } else if (ResultValue.equalsIgnoreCase("SUCCESS")) { WebElement
			 * StockQtyxpath = Partrow.get(part)
			 * .findElement(By.xpath("//*[contains(@aria-label,'Column Stock Qty')]"));
			 * String StockQty = StockQtyxpath.getText().trim();
			 * logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
			 * WebElement CCQtyxpath = Partrow.get(part)
			 * .findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]")); String
			 * CCQty = CCQtyxpath.getText().trim(); logger.info("CC Qty for Part " + (part +
			 * 1) + " is==" + CCQty);
			 * logger.info("Stock Qty and CC Qty are same, Result is actually SUCCESS"); //
			 * enter stock qty in cc qty WebElement CCQtyInput =
			 * CCQtyxpath.findElement(By.tagName("input")); CCQtyInput.sendKeys("0");
			 * CCQtyInput.sendKeys(Keys.TAB); //
			 * Driver.findElement(By.xpath(CCQtyxpath)).sendKeys(StockQty);
			 * logger.info("Entered CC Qty different from Stock Qty"); ResultValue =
			 * Result.getText(); logger.info("Value of Result of " + (part + 1) +
			 * " Part is==" + ResultValue); if (ResultValue.equalsIgnoreCase("FAIL")) {
			 * logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");
			 * 
			 * } else { logger.info(
			 * "Stock Qty and CC Qty are different, Result must be FAIL, but it is==" +
			 * ResultValue);
			 * 
			 * }
			 * 
			 * }
			 * 
			 * } WebElement Save = isElementPresent("CCASave_id");
			 * js.executeScript("arguments[0].click();", Save);
			 * logger.info("Click on Save button"); wait.until(ExpectedConditions
			 * .invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			 * 
			 * try { WebElement Dialogue = isElementPresent("CCADialogue_className");
			 * wait.until(ExpectedConditions.visibilityOf(Dialogue)); String DiaMessage =
			 * isElementPresent("CCADMessage_xpath").getText();
			 * logger.info("Dialogue Message is==" + DiaMessage); // --Click on OK
			 * isElementPresent("CCADOK_id").click(); logger.info("Click on YES button");
			 * 
			 * } catch (Exception NoDialogue) { logger.info("Dialogue is not displayed");
			 * 
			 * } }
			 * 
			 * } catch (Exception RemoveZeroQty) { if
			 * (Stage.equalsIgnoreCase("Confirm Reconciliation")) {
			 * logger.info("IT is Reconcilation");
			 * 
			 * // --Expand All isElementPresent("CCARExpandAll_id").click();
			 * logger.info("Click on Expand All button"); Thread.sleep(2000);
			 * 
			 * // --Collapse All isElementPresent("CCARCollapseAll_id").click();
			 * logger.info("Click on Collapse All button"); Thread.sleep(2000);
			 * 
			 * // --Collapse All by plus img isElementPresent("CCARCollImag_id").click();
			 * logger.info("Click on Collapse All Img"); Thread.sleep(2000);
			 * 
			 * // --Stored all Reconcilation Qty WebElement PartTable =
			 * isElementPresent("CCARTableB_xpath"); List<WebElement> Partrow =
			 * PartTable.findElements(By.tagName("tr")); logger.info("Total parts are==" +
			 * Partrow.size());
			 * 
			 * for (int part = 0; part < Partrow.size(); part++) {
			 * 
			 * // --Enter Reconcile Qty WebElement ReconQty =
			 * Partrow.get(part).findElement(By.id("txtreconQty"));
			 * act.moveToElement(ReconQty).build().perform();
			 * logger.info("Moved to ReconcileQty");
			 * wait.until(ExpectedConditions.elementToBeClickable(ReconQty));
			 * ReconQty.clear(); logger.info("Cleared Reconcileqty"); ReconQty.clear();
			 * ReconQty.sendKeys(Keys.BACK_SPACE); ReconQty.sendKeys("0");
			 * logger.info("Entered Reconcileqty" + part);
			 * System.out.println("Enetered Reconcileqty " + part + " part");
			 * logger.info("Enetered Reconcileqty in " + part + " part");
			 * 
			 * // --Enter Remarks WebElement Remarks =
			 * Partrow.get(part).findElement(By.id("txtremark"));
			 * act.moveToElement(Remarks).build().perform();
			 * wait.until(ExpectedConditions.elementToBeClickable(Remarks));
			 * Remarks.clear(); logger.info("Cleared Remarks");
			 * Remarks.sendKeys("Qty Mismatch For Part" + part);
			 * logger.info("Entered Remarks"); Remarks.sendKeys(Keys.TAB);
			 * System.out.println("Enetered Remarks in " + part + " part");
			 * logger.info("Enetered Remarks in " + part + " part");
			 * 
			 * // --Select Reconcile Exception Select RException = new
			 * Select(Partrow.get(part).findElement(By.id("drpException")));
			 * Thread.sleep(2000); logger.info("Click on Reconcile Exception dropdown");
			 * RException.selectByVisibleText("Cannot Be Found");
			 * System.out.println("Selected Reconcile Exception for " + part + " part");
			 * logger.info("Selected Reconcile Exception for " + part + " part");
			 * Thread.sleep(2000);
			 * 
			 * }
			 * 
			 * // --Save WebElement Save = isElementPresent("CCASave_id");
			 * act.moveToElement(Save).click().perform();
			 * logger.info("Click on Save button"); wait.until(ExpectedConditions
			 * .invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			 * 
			 * } }
			 * 
			 * }
			 */
		}
		// --if parts <0 uncheck Remove Zero Qty checkbox and check the part
		else {
			try {
				isElementPresent("RemoveZrQTY_id").click();
				logger.info("Uncheck the checkbox of Remove Zero Stock Parts");
			} catch (Exception NoRZQty) {
				logger.info("Checkbox of Remove Zero Stock Parts is not available for stage==" + Stage);

			}
		}
// -------------------------CCATTEMPT 2-----------------------------

		try {
			logger.info("==Testing CCAttempt2==");
			WebElement AttemptID = isElementPresent("CCAtID_xpath");
			logger.info("Name of the stage is==" + AttemptID.getText());

			if (AttemptID.getText().equalsIgnoreCase("CC ATTEMPT 2")) {
				AttemptID.click();
				logger.info("Click on the record");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Get the stage name
				String Stage2 = isElementPresent("CCATTStage_xpath").getText();
				logger.info("Stage is==" + Stage2);
				// --Checking total parts
				String TotalParts2 = isElementPresent("CCATotalParts_xpath").getText();
				String[] TotalPts2 = TotalParts2.split(":");
				String TotalPart2 = TotalPts2[1].trim();
				int TParts2 = Integer.parseInt(TotalPart2);
				// --if parts >0 check page count and status
				if (TParts > 0) {
					logger.info("Parts is available, Total Parts==" + TParts2);
					String PageCount = isElementPresent("PageCount_className").getText();
					logger.info("Total number of records==" + PageCount);

					try {
						PageCount = isElementPresent("PageCount_className").getText();
						logger.info("Total number of records==" + PageCount);
						WebElement RemoveZeroQty = isElementPresent("RemoveZrQTY_id");
						if (RemoveZeroQty.isDisplayed()) {
							logger.info("It is CCAttempt2");
							// --Enter value in Qty and get status
							WebElement PartTable = isElementPresent("CCACCATable_xpath");
							List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
							logger.info("Total parts are==" + Partrow.size());
							for (int part = 0; part < Partrow.size(); part++) {

								// --Get the result value
								WebElement Result = Partrow.get(part)
										.findElement(By.xpath("//*[contains(@aria-label,'Column Result')]"));
								String ResultValue = Result.getText();
								logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

								if (ResultValue.equalsIgnoreCase("FAIL")) {
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("//*[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");
									// enter stock qty in cc qty
									WebElement CCQtyInput = CCQtyxpath.findElement(By.tagName("input"));
									CCQtyInput.sendKeys(StockQty);
									CCQtyInput.sendKeys(Keys.TAB);
									// Driver.findElement(By.xpath(CCQtyxpath)).sendKeys(StockQty);
									logger.info("Entered CC Qty equal to Stock Qty");
									ResultValue = Result.getText();
									logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);
									if (ResultValue.equalsIgnoreCase("SUCCESS")) {
										logger.info("Stock Qty and CC Qty are same, Result is actually SUCCESS");

									} else {
										logger.info("Stock Qty and CC Qty are same, Result must be SUCCESS, but it is=="
												+ ResultValue);

									}

								} else if (ResultValue.equalsIgnoreCase("SUCCESS")) {
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("//*[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("//*[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									logger.info("Stock Qty and CC Qty are same, Result is actually SUCCESS");
									// enter stock qty in cc qty
									WebElement CCQtyInput = CCQtyxpath.findElement(By.tagName("input"));
									CCQtyInput.sendKeys("0");
									CCQtyInput.sendKeys(Keys.TAB);
									// Driver.findElement(By.xpath(CCQtyxpath)).sendKeys(StockQty);
									logger.info("Entered CC Qty different from Stock Qty");
									ResultValue = Result.getText();
									logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);
									if (ResultValue.equalsIgnoreCase("FAIL")) {
										logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

									} else {
										logger.info(
												"Stock Qty and CC Qty are different, Result must be FAIL, but it is=="
														+ ResultValue);

									}

								}

							}
							WebElement Save = isElementPresent("CCASave_id");
							js.executeScript("arguments[0].click();", Save);
							logger.info("Click on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							try {
								WebElement Dialogue = isElementPresent("CCADialogue_className");
								wait.until(ExpectedConditions.visibilityOf(Dialogue));
								String DiaMessage = isElementPresent("CCADMessage_xpath").getText();
								logger.info("Dialogue Message is==" + DiaMessage);
								// --Click on OK
								isElementPresent("CCADOK_id").click();
								logger.info("Click on YES button");

							} catch (Exception NoDialogue) {
								logger.info("Dialogue is not displayed");

							}
						}

					} catch (Exception RemoveZeroQty) {
						if (Stage.equalsIgnoreCase("Confirm Reconciliation")) {
							logger.info("IT is Reconcilation");

							// --Expand All
							isElementPresent("CCARExpandAll_id").click();
							logger.info("Click on Expand All button");
							Thread.sleep(2000);

							// --Collapse All
							isElementPresent("CCARCollapseAll_id").click();
							logger.info("Click on Collapse All button");
							Thread.sleep(2000);

							// --Collapse All by plus img
							isElementPresent("CCARCollImag_id").click();
							logger.info("Click on Collapse All Img");
							Thread.sleep(2000);

							// --Stored all Reconcilation Qty
							WebElement PartTable = isElementPresent("CCARTableB_xpath");
							List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
							logger.info("Total parts are==" + Partrow.size());

							for (int part = 0; part < Partrow.size(); part++) {

								// --Enter Reconcile Qty
								WebElement ReconQty = Partrow.get(part).findElement(By.id("txtreconQty"));
								act.moveToElement(ReconQty).build().perform();
								logger.info("Moved to ReconcileQty");
								wait.until(ExpectedConditions.elementToBeClickable(ReconQty));
								ReconQty.clear();
								logger.info("Cleared Reconcileqty");
								ReconQty.clear();
								ReconQty.sendKeys(Keys.BACK_SPACE);
								ReconQty.sendKeys("0");
								logger.info("Entered Reconcileqty" + part);
								System.out.println("Enetered Reconcileqty " + part + " part");
								logger.info("Enetered Reconcileqty in " + part + " part");

								// --Enter Remarks
								WebElement Remarks = Partrow.get(part).findElement(By.id("txtremark"));
								act.moveToElement(Remarks).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Remarks));
								Remarks.clear();
								logger.info("Cleared Remarks");
								Remarks.sendKeys("Qty Mismatch For Part" + part);
								logger.info("Entered Remarks");
								Remarks.sendKeys(Keys.TAB);
								System.out.println("Enetered Remarks in " + part + " part");
								logger.info("Enetered Remarks in " + part + " part");

								// --Select Reconcile Exception
								Select RException = new Select(Partrow.get(part).findElement(By.id("drpException")));
								Thread.sleep(2000);
								logger.info("Click on Reconcile Exception dropdown");
								RException.selectByVisibleText("Cannot Be Found");
								System.out.println("Selected Reconcile Exception for " + part + " part");
								logger.info("Selected Reconcile Exception for " + part + " part");
								Thread.sleep(2000);

							}

							// --Save
							WebElement Save = isElementPresent("CCASave_id");
							act.moveToElement(Save).click().perform();
							logger.info("Click on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}

						// --if parts <0 uncheck Remove Zero Qty checkbox and check the part

					}
				} else {
					isElementPresent("RemoveZrQTY_id").click();
					logger.info("Uncheck the checkbox of Remove Zero Stock Parts");
				}
			} else {
				logger.info("Stage is not CCAttempt2");

			}
		} catch (Exception CCAttempt2) {
			logger.info("Stage is not CCAttempt2");
		}

//----------------------------Reconcilation-------------------------------
		try {
			logger.info("==Testing CONF RECONC==");
			WebElement AttemptID = isElementPresent("CCAtID_xpath");
			logger.info("Name of the stage is==" + AttemptID.getText());

			if (AttemptID.getText().equalsIgnoreCase("CONF RECONC")) {
				AttemptID.click();
				logger.info("Click on the record");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Get the stage name
				String Stage2 = isElementPresent("CCATTStage_xpath").getText();
				logger.info("Stage is==" + Stage2);

				if (Stage.equalsIgnoreCase("Confirm Reconciliation")) {
					logger.info("IT is Reconcilation");

					// --Expand All
					isElementPresent("CCARExpandAll_id").click();
					logger.info("Click on Expand All button");
					Thread.sleep(2000);

					// --Collapse All
					isElementPresent("CCARCollapseAll_id").click();
					logger.info("Click on Collapse All button");
					Thread.sleep(2000);

					// --Collapse All by plus img
					isElementPresent("CCARCollImag_id").click();
					logger.info("Click on Collapse All Img");
					Thread.sleep(2000);

					// --Stored all Reconcilation Qty
					WebElement PartTable = isElementPresent("CCARTableB_xpath");
					List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
					logger.info("Total parts are==" + Partrow.size());

					for (int part = 0; part < Partrow.size(); part++) {

						// --Enter Reconcile Qty
						WebElement ReconQty = Partrow.get(part).findElement(By.id("txtreconQty"));
						act.moveToElement(ReconQty).build().perform();
						logger.info("Moved to ReconcileQty");
						wait.until(ExpectedConditions.elementToBeClickable(ReconQty));
						ReconQty.clear();
						logger.info("Cleared Reconcileqty");
						ReconQty.clear();
						ReconQty.sendKeys(Keys.BACK_SPACE);
						ReconQty.sendKeys("0");
						logger.info("Entered Reconcileqty" + part);
						System.out.println("Enetered Reconcileqty " + part + " part");
						logger.info("Enetered Reconcileqty in " + part + " part");

						// --Enter Remarks
						WebElement Remarks = Partrow.get(part).findElement(By.id("txtremark"));
						act.moveToElement(Remarks).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Remarks));
						Remarks.clear();
						logger.info("Cleared Remarks");
						Remarks.sendKeys("Qty Mismatch For Part" + part);
						logger.info("Entered Remarks");
						Remarks.sendKeys(Keys.TAB);
						System.out.println("Enetered Remarks in " + part + " part");
						logger.info("Enetered Remarks in " + part + " part");

						// --Select Reconcile Exception
						Select RException = new Select(Partrow.get(part).findElement(By.id("drpException")));
						Thread.sleep(2000);
						logger.info("Click on Reconcile Exception dropdown");
						RException.selectByVisibleText("Cannot Be Found");
						System.out.println("Selected Reconcile Exception for " + part + " part");
						logger.info("Selected Reconcile Exception for " + part + " part");
						Thread.sleep(2000);

					}

					// --Save
					WebElement Save = isElementPresent("CCASave_id");
					act.moveToElement(Save).click().perform();
					logger.info("Click on Save button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				}
			}
		} catch (Exception CCReconcile) {
			logger.info("Stage is not Reconcile");
		}

		/*
		 * } catch (Exception Data) { WebElement NoData =
		 * isElementPresent("NoData_className"); if (NoData.isDisplayed()) {
		 * System.out.println("There is no job exist with Search parameter");
		 * logger.info("There is no job exist with Search parameter");
		 * 
		 * } else {
		 * System.out.println("There is multiple job exist with Search parameter ");
		 * logger.info("There is multiple job exist with Search parameter "); } }
		 */
	}
}
