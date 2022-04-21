package netAgent_OperationsTab;

import java.awt.AWTException;
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

public class CCAttempt extends BaseInit {
	@Test
	public void ccAttempt()
			throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException, AWTException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		logger.info("===CCAttempt Test Start===");
		msg.append("===CCAttempt Test Start===" + "\n\n");
		// --Check 5 scenarios
		for (int Scenario = 0; Scenario < 5; Scenario++) {
			// --Start the cycle
			cycleCount();

			// Go To TaskLog
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
			isElementPresent("Operations_id").click();
			logger.info("Clicked on Operations");

			isElementPresent("TaskLog_linkText").click();
			logger.info("Clicked on TaskLog");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

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

				String PageCount = isElementPresent("PageCount_className").getText();
				logger.info("Total number of records==" + PageCount);
				WebElement CycleReCOunt = isElementPresent("CCACycleRCount_id");

				// --CCAttempt 1
				if (CycleReCOunt.isDisplayed()) {
					logger.info("It is CCAttempt1");
					logger.info("==Testing CCAttempt1==");

					if (Scenario == 0) {

						// --Call common methods
						// commonMethods();

						// ==================CCAttempt 1 Scenario 1===============================

						logger.info("===CCAttempt Scenario 1 Test Start===");
						msg.append("===CCAttempt Scenario 1 Test Start===" + "\n\n");

						// --Enter value in Qty and get status
						WebElement PartTable = isElementPresent("CCACCATable_xpath");
						List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
						logger.info("Total parts are==" + Partrow.size());
						for (int part = 0; part < Partrow.size() - 1; part++) {
							System.out.println("Value of part==" + part);

							try {
								// --Get the result value
								WebElement Result = Partrow.get(part)
										.findElement(By.xpath("//td[contains(@aria-label,'Column Result')]"));
								String ResultValue = Result.getText();
								logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);
								System.out.println("Value of part==" + part);

								if (ResultValue.equalsIgnoreCase("FAIL")) {
									System.out.println("Value of part==" + part);
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									System.out.println("Value of part==" + part);

									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									System.out.println("Value of part==" + part);
									if (StockQty.equalsIgnoreCase(CCQty)) {
										logger.info(
												"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

									} else {
										logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

									}

									if (part > 1 && part < Partrow.size() - 1) {
										CCQtyxpath = Partrow.get(part)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										js.executeScript("arguments[0].click();", CCQtyxpath);
										logger.info("Clicked on CCQty");
										try {
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow.get(part).findElement(By.tagName("input"))));
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
												System.out.println("Value of part 1" + part1);
												CCQtyxpath = Partrow1.get(part1).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");

												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow1.get(part1).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow1.get(part1)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);
												logger.info("Entered CC Qty equal to Stock Qty");
												break;

											}
										}
									}

								}
							} catch (Exception stalelement) {
								WebElement PartTableEx = isElementPresent("CCACCATable_xpath");
								List<WebElement> PartrowEx = PartTableEx.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + PartrowEx.size());
								for (int partEx = part; partEx < PartrowEx.size() - 1;) {
									System.out.println("Value of part==" + part);

									// --Get the result value
									WebElement Result = PartrowEx.get(partEx)
											.findElement(By.xpath("//td[contains(@aria-label,'Column Result')]"));
									String ResultValue = Result.getText();
									logger.info("Value of Result of " + (partEx + 1) + " partEx is==" + ResultValue);
									System.out.println("Value of partEx==" + partEx);

									if (ResultValue.equalsIgnoreCase("FAIL")) {
										System.out.println("Value of partEx==" + partEx);
										WebElement StockQtyxpath = PartrowEx.get(partEx)
												.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
										String StockQty = StockQtyxpath.getText().trim();
										logger.info("Stock Qty for partEx " + (partEx + 1) + " is==" + StockQty);
										System.out.println("Value of partEx==" + partEx);

										WebElement CCQtyxpath = PartrowEx.get(partEx)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										String CCQty = CCQtyxpath.getText().trim();
										logger.info("CC Qty for partEx " + (partEx + 1) + " is==" + CCQty);
										System.out.println("Value of partEx==" + partEx);

										if (StockQty.equalsIgnoreCase(CCQty)) {
											logger.info(
													"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

										} else {
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

										}

										if (partEx > 1 && partEx < PartrowEx.size() - 1) {
											CCQtyxpath = PartrowEx.get(partEx)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											js.executeScript("arguments[0].click();", CCQtyxpath);
											logger.info("Clicked on CCQty");
											try {
												wait.until(ExpectedConditions.elementToBeClickable(
														PartrowEx.get(partEx).findElement(By.tagName("input"))));
												WebElement CCQtyInput = PartrowEx.get(partEx)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);

												logger.info("Entered CC Qty equal to Stock Qty");
											} catch (Exception NotFoun) {
												WebElement partExTable1 = isElementPresent("CCACCATable_xpath");
												List<WebElement> partExrowEx1 = partExTable1
														.findElements(By.tagName("tr"));
												logger.info("Total partExs are==" + partExrowEx1.size());
												for (int partEx1 = partEx; partEx1 < partExrowEx1.size() - 1;) {
													System.out.println("Value of partEx 1" + partEx1);
													CCQtyxpath = partExrowEx1.get(partEx1).findElement(
															By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
													js.executeScript("arguments[0].click();", CCQtyxpath);
													logger.info("Clicked on CCQty");

													wait.until(ExpectedConditions
															.elementToBeClickable(partExrowEx1.get(partEx1)));
													wait.until(ExpectedConditions.elementToBeClickable(partExrowEx1
															.get(partEx1).findElement(By.tagName("input"))));
													WebElement CCQtyInput = partExrowEx1.get(partEx1)
															.findElement(By.tagName("input"));
													// enter stock qty in cc qty
													CCQtyInput.clear();
													CCQtyInput.sendKeys(Keys.BACK_SPACE);
													CCQtyInput.sendKeys(StockQty);
													CCQtyInput.sendKeys(Keys.TAB);
													logger.info("Entered CC Qty equal to Stock Qty");
													break;

												}
											}
										}

									}
									break;
								}
							}
						}
						// --Save
						WebElement Save = isElementPresent("CCASave_id");
						act.moveToElement(Save).click().perform();
						logger.info("Click on Save button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// --CCAttempt 2
						WebElement TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
						logger.info("TaskLog stage is==" + TStage.getText());
						if (TStage.getText().equalsIgnoreCase("CC ATTEMPT 2")) {
							TStage.click();
							logger.info("Click on the Record");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							PageCount = isElementPresent("PageCount_className").getText();
							logger.info("Total number of records==" + PageCount);
							WebElement RemoveZeroQty = isElementPresent("RemoveZrQTY_id");
							if (RemoveZeroQty.isDisplayed()) {
								logger.info("It is CCAttempt2");
								// --Enter value in Qty and get status
								PartTable = isElementPresent("CCACCATable_xpath");
								Partrow = PartTable.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + Partrow.size());
								for (int partAtt2 = 0; partAtt2 < Partrow.size() - 1; partAtt2++) {

									try {
										// --Get the result value
										WebElement Result = Partrow.get(partAtt2)
												.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
										String ResultValue = Result.getText();
										logger.info("Value of Result of " + (partAtt2 + 1) + " partAtt2 is=="
												+ ResultValue);

										if (ResultValue.equalsIgnoreCase("FAIL")) {
											WebElement StockQtyxpath = Partrow.get(partAtt2).findElement(
													By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
											String StockQty = StockQtyxpath.getText().trim();
											logger.info(
													"Stock Qty for partAtt2 " + (partAtt2 + 1) + " is==" + StockQty);
											WebElement CCQtyxpath = Partrow.get(partAtt2)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											String CCQty = CCQtyxpath.getText().trim();
											logger.info("CC Qty for partAtt2 " + (partAtt2 + 1) + " is==" + CCQty);
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");
											// enter stock qty in cc qty
											CCQtyxpath = Partrow.get(partAtt2)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											js.executeScript("arguments[0].click();", CCQtyxpath);
											logger.info("Clicked on CCQty");
											try {
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow.get(partAtt2).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow.get(partAtt2)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);

												logger.info("Entered CC Qty equal to Stock Qty");
											} catch (Exception NotFoun) {
												WebElement partAtt2Table1 = isElementPresent("CCACCATable_xpath");
												List<WebElement> partAtt2row1 = partAtt2Table1
														.findElements(By.tagName("tr"));
												logger.info("Total partAtt2s are==" + partAtt2row1.size());
												for (int partAtt21 = partAtt2; partAtt21 < partAtt2row1.size() - 1;) {
													CCQtyxpath = partAtt2row1.get(partAtt21).findElement(
															By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
													js.executeScript("arguments[0].click();", CCQtyxpath);
													logger.info("Clicked on CCQty");
													wait.until(ExpectedConditions.elementToBeClickable(partAtt2row1
															.get(partAtt21).findElement(By.tagName("input"))));
													WebElement CCQtyInput = partAtt2row1.get(partAtt21)
															.findElement(By.tagName("input"));
													// enter stock qty in cc qty
													CCQtyInput.clear();
													CCQtyInput.sendKeys(Keys.BACK_SPACE);
													CCQtyInput.sendKeys(StockQty);
													CCQtyInput.sendKeys(Keys.TAB);
													logger.info("Entered CC Qty equal to Stock Qty");
													break;

												}
											}

										}
									} catch (Exception Stale) {
										PartTable = isElementPresent("CCACCATable_xpath");
										Partrow = PartTable.findElements(By.tagName("tr"));
										logger.info("Total parts are==" + Partrow.size());
										for (int part = partAtt2; part < Partrow.size() - 1;) {

											// --Get the result value
											WebElement Result = Partrow.get(part)
													.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
											String ResultValue = Result.getText();
											logger.info(
													"Value of Result of " + (part + 1) + " part is==" + ResultValue);

											if (ResultValue.equalsIgnoreCase("FAIL")) {
												WebElement StockQtyxpath = Partrow.get(part).findElement(
														By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
												String StockQty = StockQtyxpath.getText().trim();
												logger.info("Stock Qty for part " + (part + 1) + " is==" + StockQty);
												WebElement CCQtyxpath = Partrow.get(part).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												String CCQty = CCQtyxpath.getText().trim();
												logger.info("CC Qty for part " + (part + 1) + " is==" + CCQty);
												logger.info(
														"Stock Qty and CC Qty are different, Result is actually FAIL");
												// enter stock qty in cc qty
												CCQtyxpath = Partrow.get(part).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");
												try {
													wait.until(ExpectedConditions.elementToBeClickable(
															Partrow.get(part).findElement(By.tagName("input"))));
													WebElement CCQtyInput = Partrow.get(part)
															.findElement(By.tagName("input"));
													// enter stock qty in cc qty
													CCQtyInput.clear();
													CCQtyInput.sendKeys(Keys.BACK_SPACE);
													CCQtyInput.sendKeys(StockQty);
													CCQtyInput.sendKeys(Keys.TAB);

													logger.info("Entered CC Qty equal to Stock Qty");
												} catch (Exception NotFoun) {
													WebElement partTable1 = isElementPresent("CCACCATable_xpath");
													List<WebElement> partrow1 = partTable1
															.findElements(By.tagName("tr"));
													logger.info("Total parts are==" + partrow1.size());
													for (int part1 = part; part1 < partrow1.size() - 1;) {
														CCQtyxpath = partrow1.get(part1).findElement(
																By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
														js.executeScript("arguments[0].click();", CCQtyxpath);
														logger.info("Clicked on CCQty");
														wait.until(ExpectedConditions.elementToBeClickable(
																partrow1.get(part1).findElement(By.tagName("input"))));
														WebElement CCQtyInput = partrow1.get(part1)
																.findElement(By.tagName("input"));
														// enter stock qty in cc qty
														CCQtyInput.clear();
														CCQtyInput.sendKeys(Keys.BACK_SPACE);
														CCQtyInput.sendKeys(StockQty);
														CCQtyInput.sendKeys(Keys.TAB);
														logger.info("Entered CC Qty equal to Stock Qty");
														break;

													}
												}

											}
											break;
										}

									}
								}
								// --EndCycle
								WebElement EndCycle = isElementPresent("CCAEndCycle_id");
								act.moveToElement(EndCycle).click().perform();
								logger.info("Click on End Cycle button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								try {
									WebElement Dialogue = isElementPresent("CCADialogue_className");
									wait.until(ExpectedConditions.visibilityOf(Dialogue));
									String DiaMessage = isElementPresent("CCADMessage_xpath").getText();
									logger.info("Dialogue Message is==" + DiaMessage); // --Click on OK
									isElementPresent("CCADOK_id").click();
									logger.info("Click on YES button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception NoDialogue) {
									logger.info("Dialogue is not displayed");

								}
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										System.out.println("Cycle is end");
										logger.info("Cycle is end");

									}
								} catch (Exception Deliver) {
									System.out.println("Cycle is not end");
									logger.info("Cycle is not end");
									TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
									logger.info("TaskLog stage is==" + TStage.getText());

								}

							}
						}

					} else if (Scenario == 1) {
						// ===========================CCAttempt 1 Scenario 2======================
						// --CCAttempt 1 Scenario 2
						logger.info("===CCAttempt Scenario 2 Test Start===");
						msg.append("===CCAttempt Scenario 2 Test Start===" + "\n\n");

						// --Enter value in Qty and get status
						WebElement PartTable = isElementPresent("CCACCATable_xpath");
						List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
						logger.info("Total parts are==" + Partrow.size());
						for (int part = 0; part < Partrow.size() - 1; part++) {

							try {
								// --Get the result value
								WebElement Result = Partrow.get(part)
										.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
								String ResultValue = Result.getText();
								logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

								if (ResultValue.equalsIgnoreCase("FAIL")) {
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									if (StockQty.equalsIgnoreCase(CCQty)) {
										logger.info(
												"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

									} else {
										logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

									}

									if (part > 1 && part < Partrow.size() - 1) {
										CCQtyxpath = Partrow.get(part)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										js.executeScript("arguments[0].click();", CCQtyxpath);
										logger.info("Clicked on CCQty");
										try {
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow.get(part).findElement(By.tagName("input"))));
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
												CCQtyxpath = Partrow1.get(part1).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow1.get(part1).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow1.get(part1)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);
												logger.info("Entered CC Qty equal to Stock Qty");
												break;

											}
										}
									}

								}
							} catch (Exception StaleE) {
								PartTable = isElementPresent("CCACCATable_xpath");
								Partrow = PartTable.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + Partrow.size());
								for (int part2 = part; part2 < Partrow.size() - 1;) {
									// --Get the result value
									WebElement Result = Partrow.get(part2)
											.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
									String ResultValue = Result.getText();
									logger.info("Value of Result of " + (part2 + 1) + " part2is==" + ResultValue);

									if (ResultValue.equalsIgnoreCase("FAIL")) {
										WebElement StockQtyxpath = Partrow.get(part2)
												.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
										String StockQty = StockQtyxpath.getText().trim();
										logger.info("Stock Qty for part2" + (part2 + 1) + " is==" + StockQty);
										WebElement CCQtyxpath = Partrow.get(part2)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										String CCQty = CCQtyxpath.getText().trim();
										logger.info("CC Qty for part2" + (part2 + 1) + " is==" + CCQty);
										if (StockQty.equalsIgnoreCase(CCQty)) {
											logger.info(
													"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

										} else {
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

										}

										if (part2 > 1 && part2 < Partrow.size() - 1) {
											CCQtyxpath = Partrow.get(part2)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											js.executeScript("arguments[0].click();", CCQtyxpath);
											logger.info("Clicked on CCQty");
											try {
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow.get(part2).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow.get(part2)
														.findElement(By.tagName("input"));
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
												for (int part1 = part2; part1 < Partrow1.size() - 1;) {
													CCQtyxpath = Partrow1.get(part1).findElement(
															By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
													js.executeScript("arguments[0].click();", CCQtyxpath);
													logger.info("Clicked on CCQty");
													wait.until(ExpectedConditions.elementToBeClickable(
															Partrow1.get(part1).findElement(By.tagName("input"))));
													WebElement CCQtyInput = Partrow1.get(part1)
															.findElement(By.tagName("input"));
													// enter stock qty in cc qty
													CCQtyInput.clear();
													CCQtyInput.sendKeys(Keys.BACK_SPACE);
													CCQtyInput.sendKeys(StockQty);
													CCQtyInput.sendKeys(Keys.TAB);
													logger.info("Entered CC Qty equal to Stock Qty");
													break;

												}
											}
										}

									}
									break;
								}
							}
						}
						// --Save
						WebElement Save = isElementPresent("CCASave_id");
						act.moveToElement(Save).click().perform();
						logger.info("Click on Save button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// --CCAttempt 2
						WebElement TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
						logger.info("TaskLog stage is==" + TStage.getText());
						if (TStage.getText().equalsIgnoreCase("CC ATTEMPT 2")) {
							TStage.click();
							logger.info("Click on the Record");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							PageCount = isElementPresent("PageCount_className").getText();
							logger.info("Total number of records==" + PageCount);
							WebElement RemoveZeroQty = isElementPresent("RemoveZrQTY_id");
							if (RemoveZeroQty.isDisplayed()) {
								logger.info("It is CCAttempt2");
								// --Enter value in Qty and get status
								PartTable = isElementPresent("CCACCATable_xpath");
								Partrow = PartTable.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + Partrow.size());
								for (int part = 0; part < Partrow.size() - 1; part++) {
									try {
										// --Get the result value
										WebElement Result = Partrow.get(part)
												.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
										String ResultValue = Result.getText();
										logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

										if (ResultValue.equalsIgnoreCase("FAIL")) {
											WebElement StockQtyxpath = Partrow.get(part).findElement(
													By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
											String StockQty = StockQtyxpath.getText().trim();
											logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
											WebElement CCQtyxpath = Partrow.get(part)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											String CCQty = CCQtyxpath.getText().trim();
											logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

											if (part > 1 && part < Partrow.size() - 1) {
												// enter stock qty in cc qty
												CCQtyxpath = Partrow.get(part).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");
												try {
													wait.until(ExpectedConditions.elementToBeClickable(
															Partrow.get(part).findElement(By.tagName("input"))));
													WebElement CCQtyInput = Partrow.get(part)
															.findElement(By.tagName("input"));
													// enter stock qty in cc qty
													CCQtyInput.clear();
													CCQtyInput.sendKeys(Keys.BACK_SPACE);
													CCQtyInput.sendKeys(StockQty);
													CCQtyInput.sendKeys(Keys.TAB);

													logger.info("Entered CC Qty equal to Stock Qty");
												} catch (Exception NotFoun) {
													WebElement PartTable1 = isElementPresent("CCACCATable_xpath");
													List<WebElement> Partrow1 = PartTable1
															.findElements(By.tagName("tr"));
													logger.info("Total parts are==" + Partrow1.size());
													for (int part1 = part; part1 < Partrow1.size() - 1;) {
														CCQtyxpath = Partrow1.get(part1).findElement(
																By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
														js.executeScript("arguments[0].click();", CCQtyxpath);
														logger.info("Clicked on CCQty");
														wait.until(ExpectedConditions.elementToBeClickable(
																Partrow1.get(part1).findElement(By.tagName("input"))));
														WebElement CCQtyInput = Partrow1.get(part1)
																.findElement(By.tagName("input"));
														// enter stock qty in cc qty
														CCQtyInput.clear();
														CCQtyInput.sendKeys(Keys.BACK_SPACE);
														CCQtyInput.sendKeys(StockQty);
														CCQtyInput.sendKeys(Keys.TAB);
														logger.info("Entered CC Qty equal to Stock Qty");
														break;

													}
												}
											}
										}
									} catch (Exception stale) {

										PartTable = isElementPresent("CCACCATable_xpath");
										Partrow = PartTable.findElements(By.tagName("tr"));
										logger.info("Total parts are==" + Partrow.size());
										for (int partS2 = part; partS2 < Partrow.size() - 1;) {
											// --Get the result value
											WebElement Result = Partrow.get(partS2)
													.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
											String ResultValue = Result.getText();
											logger.info(
													"Value of Result of " + (partS2 + 1) + " Part is==" + ResultValue);

											if (ResultValue.equalsIgnoreCase("FAIL")) {
												WebElement StockQtyxpath = Partrow.get(partS2).findElement(
														By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
												String StockQty = StockQtyxpath.getText().trim();
												logger.info("Stock Qty for Part " + (partS2 + 1) + " is==" + StockQty);
												WebElement CCQtyxpath = Partrow.get(partS2).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												String CCQty = CCQtyxpath.getText().trim();
												logger.info("CC Qty for Part " + (partS2 + 1) + " is==" + CCQty);
												logger.info(
														"Stock Qty and CC Qty are different, Result is actually FAIL");

												if (partS2 > 1 && partS2 < Partrow.size() - 1) {
													// enter stock qty in cc qty
													CCQtyxpath = Partrow.get(partS2).findElement(
															By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
													js.executeScript("arguments[0].click();", CCQtyxpath);
													logger.info("Clicked on CCQty");
													try {
														wait.until(ExpectedConditions.elementToBeClickable(
																Partrow.get(partS2).findElement(By.tagName("input"))));
														WebElement CCQtyInput = Partrow.get(partS2)
																.findElement(By.tagName("input"));
														// enter stock qty in cc qty
														CCQtyInput.clear();
														CCQtyInput.sendKeys(Keys.BACK_SPACE);
														CCQtyInput.sendKeys(StockQty);
														CCQtyInput.sendKeys(Keys.TAB);

														logger.info("Entered CC Qty equal to Stock Qty");
													} catch (Exception NotFoun) {
														WebElement PartTable1 = isElementPresent("CCACCATable_xpath");
														List<WebElement> Partrow1 = PartTable1
																.findElements(By.tagName("tr"));
														logger.info("Total parts are==" + Partrow1.size());
														for (int part1 = partS2; part1 < Partrow1.size() - 1;) {
															CCQtyxpath = Partrow1.get(part1).findElement(By.xpath(
																	"td[contains(@aria-label,'Column CC Qty')]"));
															js.executeScript("arguments[0].click();", CCQtyxpath);
															logger.info("Clicked on CCQty");
															wait.until(ExpectedConditions.elementToBeClickable(Partrow1
																	.get(part1).findElement(By.tagName("input"))));
															WebElement CCQtyInput = Partrow1.get(part1)
																	.findElement(By.tagName("input"));
															// enter stock qty in cc qty
															CCQtyInput.clear();
															CCQtyInput.sendKeys(Keys.BACK_SPACE);
															CCQtyInput.sendKeys(StockQty);
															CCQtyInput.sendKeys(Keys.TAB);
															logger.info("Entered CC Qty equal to Stock Qty");
															break;

														}
													}
												}
											}
											break;
										}

									}
								}
								// --EndCycle
								WebElement EndCycle = isElementPresent("CCAEndCycle_id");
								act.moveToElement(EndCycle).click().perform();
								logger.info("Click on End Cycle button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								try {
									WebElement Dialogue = isElementPresent("CCADialogue_className");
									wait.until(ExpectedConditions.visibilityOf(Dialogue));
									String DiaMessage = isElementPresent("CCADMessage_xpath").getText();
									logger.info("Dialogue Message is==" + DiaMessage); // --Click on OK
									isElementPresent("CCADOK_id").click();
									logger.info("Click on YES button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception NoDialogue) {
									logger.info("Dialogue is not displayed");

								}
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										System.out.println("Cycle is end");
										logger.info("Cycle is end");

									}
								} catch (Exception Deliver) {
									System.out.println("Cycle is moved to Reconcile stage");
									logger.info("Cycle is moved to Reconcile stage");
									TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
									logger.info("TaskLog stage is==" + TStage.getText());

								}
							}
						}
					} else if (Scenario == 2) {

						// ===========================CCAttempt 1 Scenario 3=======================
						// --CCAttempt 1 Scenario 3
						logger.info("===CCAttempt Scenario 3 Test Start===");
						msg.append("===CCAttempt Scenario 3 Test Start===" + "\n\n");

						CycleReCOunt = isElementPresent("CCACycleRCount_id");
						CycleReCOunt.click();
						logger.info("Uncheck the checkbox of Mandatory Cycle Recount");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// --Enter value in Qty and get status
						WebElement PartTable = isElementPresent("CCACCATable_xpath");
						List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
						logger.info("Total parts are==" + Partrow.size());
						for (int part = 0; part < Partrow.size() - 1; part++) {

							try {
								// --Get the result value
								WebElement Result = Partrow.get(part)
										.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
								String ResultValue = Result.getText();
								logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

								if (ResultValue.equalsIgnoreCase("FAIL")) {
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									if (StockQty.equalsIgnoreCase(CCQty)) {
										logger.info(
												"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

									} else {
										logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

									}

									if (part > 1 && part < Partrow.size() - 1) {
										CCQtyxpath = Partrow.get(part)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										js.executeScript("arguments[0].click();", CCQtyxpath);
										logger.info("Clicked on CCQty");
										try {
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow.get(part).findElement(By.tagName("input"))));
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
												CCQtyxpath = Partrow1.get(part1).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow1.get(part1).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow1.get(part1)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);
												logger.info("Entered CC Qty equal to Stock Qty");
												break;

											}
										}
									}

								}
							} catch (Exception Stale) {
								PartTable = isElementPresent("CCACCATable_xpath");
								Partrow = PartTable.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + Partrow.size());
								for (int partS3 = part; partS3 < Partrow.size() - 1;) {

									// --Get the result value
									WebElement Result = Partrow.get(partS3)
											.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
									String ResultValue = Result.getText();
									logger.info("Value of Result of " + (partS3 + 1) + " Part is==" + ResultValue);

									if (ResultValue.equalsIgnoreCase("FAIL")) {
										WebElement StockQtyxpath = Partrow.get(partS3)
												.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
										String StockQty = StockQtyxpath.getText().trim();
										logger.info("Stock Qty for Part " + (partS3 + 1) + " is==" + StockQty);
										WebElement CCQtyxpath = Partrow.get(partS3)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										String CCQty = CCQtyxpath.getText().trim();
										logger.info("CC Qty for Part " + (partS3 + 1) + " is==" + CCQty);
										if (StockQty.equalsIgnoreCase(CCQty)) {
											logger.info(
													"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

										} else {
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

										}

										if (partS3 > 1 && partS3 < Partrow.size() - 1) {
											CCQtyxpath = Partrow.get(partS3)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											js.executeScript("arguments[0].click();", CCQtyxpath);
											logger.info("Clicked on CCQty");
											try {
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow.get(partS3).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow.get(partS3)
														.findElement(By.tagName("input"));
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
												for (int part1 = partS3; part1 < Partrow1.size() - 1;) {
													CCQtyxpath = Partrow1.get(part1).findElement(
															By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
													js.executeScript("arguments[0].click();", CCQtyxpath);
													logger.info("Clicked on CCQty");
													wait.until(ExpectedConditions.elementToBeClickable(
															Partrow1.get(part1).findElement(By.tagName("input"))));
													WebElement CCQtyInput = Partrow1.get(part1)
															.findElement(By.tagName("input"));
													// enter stock qty in cc qty
													CCQtyInput.clear();
													CCQtyInput.sendKeys(Keys.BACK_SPACE);
													CCQtyInput.sendKeys(StockQty);
													CCQtyInput.sendKeys(Keys.TAB);
													logger.info("Entered CC Qty equal to Stock Qty");
													break;

												}
											}
										}

									}
									break;
								}
							}
						}
						// --EndCycle
						WebElement EndCycle = isElementPresent("CCAEndCycle_id");
						act.moveToElement(EndCycle).click().perform();
						logger.info("Click on End Cycle button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							WebElement Dialogue = isElementPresent("CCADialogue_className");
							wait.until(ExpectedConditions.visibilityOf(Dialogue));
							String DiaMessage = isElementPresent("CCADMessage_xpath").getText();
							logger.info("Dialogue Message is==" + DiaMessage); // --Click on OK
							isElementPresent("CCADOK_id").click();
							logger.info("Click on YES button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception NoDialogue) {
							logger.info("Dialogue is not displayed");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								System.out.println("Cycle is end");
								logger.info("Cycle is end");

							} else {
								System.out.println("Cycle is not end");
							}
						} catch (Exception Deliver) {
							System.out.println("Cycle is not end");
							logger.info("Cycle is not end");
							WebElement TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
							logger.info("TaskLog stage is==" + TStage.getText());

							// --Reconcile
							WebElement AttemptID = isElementPresent("CCAtID_xpath");
							logger.info("Name of the stage is==" + AttemptID.getText());

							if (AttemptID.getText().equalsIgnoreCase("CONF RECONC")) {
								AttemptID.click();
								logger.info("Click on the record");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								// --Get the stage name
								String Stage2 = isElementPresent("CCATTStage_xpath").getText();
								logger.info("Stage is==" + Stage2);

								if (Stage2.equalsIgnoreCase("Confirm Reconciliation")) {
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
									PartTable = isElementPresent("CCARTableB_xpath");
									Partrow = PartTable.findElements(By.tagName("tr"));
									logger.info("Total parts are==" + Partrow.size());

									for (int partR = 0; partR < Partrow.size(); partR++) {

										// --Enter Reconcile Qty
										WebElement ReconQty = Partrow.get(partR).findElement(By.id("txtreconQty"));
										act.moveToElement(ReconQty).build().perform();
										logger.info("Moved to ReconcileQty");
										wait.until(ExpectedConditions.elementToBeClickable(ReconQty));
										/*
										 * ReconQty.clear(); logger.info("Cleared Reconcileqty"); ReconQty.clear();
										 * ReconQty.sendKeys(Keys.BACK_SPACE); ReconQty.sendKeys("0");
										 * logger.info("Entered Reconcileqty" + partR);
										 * System.out.println("Enetered Reconcileqty " + partR + " part");
										 * logger.info("Enetered Reconcileqty in " + partR + " part");
										 */
										// --Enter Remarks
										WebElement Remarks = Partrow.get(partR).findElement(By.id("txtremark"));
										act.moveToElement(Remarks).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Remarks));
										Remarks.clear();
										logger.info("Cleared Remarks");
										Remarks.sendKeys("Qty Mismatch For Part" + partR);
										logger.info("Entered Remarks");
										Remarks.sendKeys(Keys.TAB);
										System.out.println("Enetered Remarks in " + partR + " part");
										logger.info("Enetered Remarks in " + partR + " part");

										// --Select Reconcile Exception
										Select RException = new Select(
												Partrow.get(partR).findElement(By.id("drpException")));
										Thread.sleep(2000);
										logger.info("Click on Reconcile Exception dropdown");
										RException.selectByVisibleText("Cannot Be Found");
										System.out.println("Selected Reconcile Exception for " + partR + " part");
										logger.info("Selected Reconcile Exception for " + partR + " part");
										Thread.sleep(2000);

									}

									// --Save
									WebElement Save = isElementPresent("CCASave_id");
									act.moveToElement(Save).click().perform();
									logger.info("Click on Save button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											System.out.println("Cycle is end");
											logger.info("Cycle is end");

										}
									} catch (Exception End) {
										System.out.println("Cycle is not end");
										logger.info("Cycle is not end");

										TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
										logger.info("TaskLog stage is==" + TStage.getText());
									}

								}
							}
						}

					} else if (Scenario == 3) {
						// =========================CCAttempt 1 Scenario 4=========================
						// --CCAttempt 1 Scenario 4
						logger.info("===CCAttempt Scenario 4 Test Start===");
						msg.append("===CCAttempt Scenario 4 Test Start===" + "\n\n");

						// --Enter value in Qty and get status
						WebElement PartTable = isElementPresent("CCACCATable_xpath");
						List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
						logger.info("Total parts are==" + Partrow.size());
						for (int part = 0; part < Partrow.size() - 1; part++) {

							try {
								// --Get the result value
								WebElement Result = Partrow.get(part)
										.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
								String ResultValue = Result.getText();
								logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

								if (ResultValue.equalsIgnoreCase("FAIL")) {
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									if (StockQty.equalsIgnoreCase(CCQty)) {
										logger.info(
												"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

									} else {
										logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

									}

									CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									js.executeScript("arguments[0].click();", CCQtyxpath);
									logger.info("Clicked on CCQty");
									try {
										wait.until(ExpectedConditions.elementToBeClickable(
												Partrow.get(part).findElement(By.tagName("input"))));
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
											CCQtyxpath = Partrow1.get(part1)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											js.executeScript("arguments[0].click();", CCQtyxpath);
											logger.info("Clicked on CCQty");
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow1.get(part1).findElement(By.tagName("input"))));
											WebElement CCQtyInput = Partrow1.get(part1)
													.findElement(By.tagName("input"));
											// enter stock qty in cc qty
											CCQtyInput.clear();
											CCQtyInput.sendKeys(Keys.BACK_SPACE);
											CCQtyInput.sendKeys(StockQty);
											CCQtyInput.sendKeys(Keys.TAB);
											logger.info("Entered CC Qty equal to Stock Qty");
											break;

										}

									}

								}
							} catch (Exception e) {
								PartTable = isElementPresent("CCACCATable_xpath");
								Partrow = PartTable.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + Partrow.size());
								for (int part4 = part; part4 < Partrow.size() - 1;) {
									// --Get the result value
									WebElement Result = Partrow.get(part4)
											.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
									String ResultValue = Result.getText();
									logger.info("Value of Result of " + (part4 + 1) + " Part is==" + ResultValue);

									if (ResultValue.equalsIgnoreCase("FAIL")) {
										WebElement StockQtyxpath = Partrow.get(part4)
												.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
										String StockQty = StockQtyxpath.getText().trim();
										logger.info("Stock Qty for Part " + (part4 + 1) + " is==" + StockQty);
										WebElement CCQtyxpath = Partrow.get(part4)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										String CCQty = CCQtyxpath.getText().trim();
										logger.info("CC Qty for Part " + (part4 + 1) + " is==" + CCQty);
										if (StockQty.equalsIgnoreCase(CCQty)) {
											logger.info(
													"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

										} else {
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

										}

										CCQtyxpath = Partrow.get(part)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										js.executeScript("arguments[0].click();", CCQtyxpath);
										logger.info("Clicked on CCQty");
										try {
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow.get(part4).findElement(By.tagName("input"))));
											WebElement CCQtyInput = Partrow.get(part4).findElement(By.tagName("input"));
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
											for (int part1 = part4; part1 < Partrow1.size() - 1;) {
												CCQtyxpath = Partrow1.get(part1).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow1.get(part1).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow1.get(part1)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);
												logger.info("Entered CC Qty equal to Stock Qty");
												break;

											}

										}

									}
									break;
								}

							}

						}
						// --EndCycle
						WebElement EndCycle = isElementPresent("CCAEndCycle_id");
						act.moveToElement(EndCycle).click().perform();
						logger.info("Click on End Cycle button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							WebElement Dialogue = isElementPresent("CCADialogue_className");
							wait.until(ExpectedConditions.visibilityOf(Dialogue));
							String DiaMessage = isElementPresent("CCADMessage_xpath").getText();
							logger.info("Dialogue Message is==" + DiaMessage); // --Click on OK
							isElementPresent("CCADOK_id").click();
							logger.info("Click on YES button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception NoDialogue) {
							logger.info("Dialogue is not displayed");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								System.out.println("Cycle is end");
								logger.info("Cycle is end");

							}
						} catch (Exception Deliver) {
							System.out.println("Cycle is not end");
							logger.info("Cycle is not end");
							WebElement TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
							logger.info("TaskLog stage is==" + TStage.getText());

						}

					} else if (Scenario == 4) {
						// ======================CCAttempt 1 Scenario 5=========================
						// --CCAttempt 1 Scenario 5
						logger.info("===CCAttempt Scenario 5 Test Start===");
						msg.append("===CCAttempt Scenario 5 Test Start===" + "\n\n");

						// --Enter value in Qty and get status
						WebElement PartTable = isElementPresent("CCACCATable_xpath");
						List<WebElement> Partrow = PartTable.findElements(By.tagName("tr"));
						logger.info("Total parts are==" + Partrow.size());
						for (int part = 0; part < Partrow.size() - 1; part++) {

							try {
								// --Get the result value
								WebElement Result = Partrow.get(part)
										.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
								String ResultValue = Result.getText();
								logger.info("Value of Result of " + (part + 1) + " Part is==" + ResultValue);

								if (ResultValue.equalsIgnoreCase("FAIL")) {
									WebElement StockQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
									String StockQty = StockQtyxpath.getText().trim();
									logger.info("Stock Qty for Part " + (part + 1) + " is==" + StockQty);
									WebElement CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									String CCQty = CCQtyxpath.getText().trim();
									logger.info("CC Qty for Part " + (part + 1) + " is==" + CCQty);
									if (StockQty.equalsIgnoreCase(CCQty)) {
										logger.info(
												"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

									} else {
										logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

									}

									CCQtyxpath = Partrow.get(part)
											.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
									js.executeScript("arguments[0].click();", CCQtyxpath);
									logger.info("Clicked on CCQty");
									try {
										wait.until(ExpectedConditions.elementToBeClickable(
												Partrow.get(part).findElement(By.tagName("input"))));
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
											CCQtyxpath = Partrow1.get(part1)
													.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
											js.executeScript("arguments[0].click();", CCQtyxpath);
											logger.info("Clicked on CCQty");
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow1.get(part1).findElement(By.tagName("input"))));
											WebElement CCQtyInput = Partrow1.get(part1)
													.findElement(By.tagName("input"));
											// enter stock qty in cc qty
											CCQtyInput.clear();
											CCQtyInput.sendKeys(Keys.BACK_SPACE);
											CCQtyInput.sendKeys(StockQty);
											CCQtyInput.sendKeys(Keys.TAB);
											logger.info("Entered CC Qty equal to Stock Qty");
											break;
										}

									}

								}
							} catch (Exception Stale) {
								PartTable = isElementPresent("CCACCATable_xpath");
								Partrow = PartTable.findElements(By.tagName("tr"));
								logger.info("Total parts are==" + Partrow.size());
								for (int part5 = part; part5 < Partrow.size() - 1;) {
									// --Get the result value
									WebElement Result = Partrow.get(part5)
											.findElement(By.xpath("td[contains(@aria-label,'Column Result')]"));
									String ResultValue = Result.getText();
									logger.info("Value of Result of " + (part5 + 1) + " Part is==" + ResultValue);

									if (ResultValue.equalsIgnoreCase("FAIL")) {
										WebElement StockQtyxpath = Partrow.get(part5)
												.findElement(By.xpath("td[contains(@aria-label,'Column Stock Qty')]"));
										String StockQty = StockQtyxpath.getText().trim();
										logger.info("Stock Qty for Part " + (part5 + 1) + " is==" + StockQty);
										WebElement CCQtyxpath = Partrow.get(part5)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										String CCQty = CCQtyxpath.getText().trim();
										logger.info("CC Qty for Part " + (part5 + 1) + " is==" + CCQty);
										if (StockQty.equalsIgnoreCase(CCQty)) {
											logger.info(
													"Stock Qty and CC Qty are Same, Result is actually SUCCESS instead of FAIL");

										} else {
											logger.info("Stock Qty and CC Qty are different, Result is actually FAIL");

										}

										CCQtyxpath = Partrow.get(part5)
												.findElement(By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
										js.executeScript("arguments[0].click();", CCQtyxpath);
										logger.info("Clicked on CCQty");
										try {
											wait.until(ExpectedConditions.elementToBeClickable(
													Partrow.get(part5).findElement(By.tagName("input"))));
											WebElement CCQtyInput = Partrow.get(part5).findElement(By.tagName("input"));
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
											for (int part1 = part5; part1 < Partrow1.size() - 1;) {
												CCQtyxpath = Partrow1.get(part1).findElement(
														By.xpath("td[contains(@aria-label,'Column CC Qty')]"));
												js.executeScript("arguments[0].click();", CCQtyxpath);
												logger.info("Clicked on CCQty");
												wait.until(ExpectedConditions.elementToBeClickable(
														Partrow1.get(part1).findElement(By.tagName("input"))));
												WebElement CCQtyInput = Partrow1.get(part1)
														.findElement(By.tagName("input"));
												// enter stock qty in cc qty
												CCQtyInput.clear();
												CCQtyInput.sendKeys(Keys.BACK_SPACE);
												CCQtyInput.sendKeys(StockQty);
												CCQtyInput.sendKeys(Keys.TAB);
												logger.info("Entered CC Qty equal to Stock Qty");
												break;
											}

										}

									}
									break;
								}
							}
						}
						// --Save
						WebElement Save = isElementPresent("CCASave_id");
						act.moveToElement(Save).click().perform();
						logger.info("Click on Save button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							WebElement Dialogue = isElementPresent("CCADialogue_className");
							wait.until(ExpectedConditions.visibilityOf(Dialogue));
							String DiaMessage = isElementPresent("CCADMessage_xpath").getText();
							logger.info("Dialogue Message is==" + DiaMessage); // --Click on OK
							isElementPresent("CCADOK_id").click();
							logger.info("Click on YES button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception NoDialogue) {
							logger.info("Dialogue is not displayed");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								System.out.println("Cycle is end");
								logger.info("Cycle is end");

							}
						} catch (Exception Deliver) {
							System.out.println("Cycle is Moved to CCAttempt2 end");
							logger.info("Cycle is Moved to CCAttempt2 end");
							WebElement TStage = Driver.findElement(By.xpath("//div/span[@class=\"pull-left\"]"));
							logger.info("TaskLog stage is==" + TStage.getText());

						}

					}

				}

			}
		}

	}

	public void cycleCount() {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		// Go to CycleCount screen
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
		Driver.findElement(By.id("idOperations")).click();
		logger.info("Click on Operations");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("idCycle")));
		Driver.findElement(By.id("idCycle")).click();
		logger.info("Click on CycleCount");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Reset
		if (!Driver.findElements(By.xpath("//a[@class='dx-link' and text()='Reset']")).isEmpty()) {
			logger.info("If Reset button is exist");
			// --Click on Reset button
			Driver.findElement(By.xpath("//a[@class='dx-link' and text()='Reset']")).click();
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Wait for workOrder ID
			WebElement WorkOID = Driver.findElement(By.id("woid"));
			wait.until(ExpectedConditions.visibilityOf(WorkOID));
			logger.info("WorkOrderID after Reset==" + WorkOID.getText());
		}
		// --Start

		if (!Driver.findElements(By.xpath("//a[@class=\"dx-link\"]")).isEmpty()) {
			logger.info("If action column is not empty");

			WebElement BinStart = Driver.findElement(By.xpath("//*[@id=\"UpComingGD\"]//table/tbody/tr[2]//a"));
			BinStart.click();
			logger.info("Click on Start button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			try {
				// --Wait for workOrder ID
				WebElement WorkOID = Driver.findElement(By.id("workid"));
				wait.until(ExpectedConditions.visibilityOf(WorkOID));
				String[] WOID = WorkOID.getText().split(":");
				String wOID = WOID[1].trim();
				logger.info("WorkOrderID after start==" + wOID);
				setData("OrderSearch", 1, 13, wOID);
				logger.info("Inserted workorderID in excel");

			} catch (Exception Part) {
				WebElement PartError = Driver.findElement(By.id("CycleNoGrid"));
				if (PartError.isDisplayed()) {
					logger.info("Part validation is displayed==" + PartError.getText());

				} else {
					logger.info("Unable to start cycle");

				}
			}
		}

	}

	public void commonMethods() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

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
	}
}
