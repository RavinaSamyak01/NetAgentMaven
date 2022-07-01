package netAgent_OrderProcessing;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class H3P_OrderProcess extends BaseInit {

	@Test
	public void orderProcessH3PJOB() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=====H3P Order Processing Test Start=====");
		msg.append("=====H3P Order Processing Test Start=====" + "\n\n");

		int rowNum = getTotalRow("OrderProcessing");
		logger.info("total No of Rows=" + rowNum);

		int colNum = getTotalCol("OrderProcessing");
		logger.info("total No of Columns=" + colNum);

		// Go To TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idOperations\"]")));
		WebElement OperationMenu = Driver.findElement(By.xpath("//a[@id=\"idOperations\"]"));
		act.moveToElement(OperationMenu).build().perform();
		js.executeScript("arguments[0].click();", OperationMenu);

		logger.info("Click on Operations");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idTask\"]")));
		WebElement TaskLogMenu = Driver.findElement(By.xpath("//a[@id=\"idTask\"]"));
		act.moveToElement(TaskLogMenu).build().perform();
		js.executeScript("arguments[0].click();", TaskLogMenu);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		logger.info("Click on Task Log");

		getScreenshot(Driver, "TaskLog_OperationsH3P");

		String ServiceID = getData("OrderProcessing", 4, 0);
		logger.info("ServiceID is==" + ServiceID);
		msg.append("ServiceID==" + ServiceID + "\n");
		String PUID = getData("OrderProcessing", 4, 1);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active')]")));
			logger.info("Operation tab is already selected");

		} catch (Exception Operation) {
			logger.info("Operation tab is not selected");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\" and  text()='Operations']")));
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"operation\" and  text()='Operations']")));
			Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
			logger.info("Click on Operation Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
			Driver.findElement(By.id("txtBasicSearch2")).clear();
			logger.info("Clear search input");
			Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
			logger.info("Enter PickUpID in Search input");
			WebElement OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
			act.moveToElement(OPSearch).build().perform();
			js.executeScript("arguments[0].click();", OPSearch);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// Ship Label
			OrderProcess OP = new OrderProcess();

			OP.shipLabel(PUID);

			// --Memo
			OP.memo(PUID);

			// -Notification
			OP.notification(PUID);

			// Upload
			OP.upload(PUID);

			// --Get current stage of the order
			String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
			logger.info("Current stage of the order is=" + Orderstage);
			msg.append("Stage==" + Orderstage + "\n");
			getScreenshot(Driver, "H3POrderEditor_" + PUID);

			if (Orderstage.contains("Confirm Pull and Apply Return Pack(s)")
					|| Orderstage.contains("CONF PULL ALERT")) {
				WebElement Form = Driver.findElement(By.name("ConfPullAlertForm"));
				if (Form.isDisplayed()) {
					logger.info("Searched Job is displayed in edit mode");
				}
				// --Confirm Pull and Apply Return Pack(s) stage
				String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
				logger.info("stage=" + stage);
				getScreenshot(Driver, "ConfirmPullandApplyReturnPack(s)_" + PUID);

				try {
					try {
						WebElement Accept = Driver.findElement(By.id("idiconaccept"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						act.moveToElement(Accept).click().perform();
						logger.info("Clicked on Accept button");

					} catch (Exception saveee) {
						WebElement Accept = Driver.findElement(By.id("idiconaccept"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						js.executeScript("arguments[0].click();", Accept);
						logger.info("Clicked on Accept button");

					}
				} catch (Exception Saveb) {
					try {
						WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
						act.moveToElement(Accept).click().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						act.moveToElement(Accept).click().perform();
						logger.info("Clicked on Accept button");

					} catch (Exception saveee) {
						WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						js.executeScript("arguments[0].click();", Accept);
						logger.info("Clicked on Accept button");
					}

				}

				try {
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
					logger.info("Validation Message is=="
							+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());

					// --Spoke with
					WebElement SpokeWith = Driver.findElement(By.id("idConfPullAlertForm"));
					act.moveToElement(SpokeWith).build().perform();
					SpokeWith.sendKeys("Ravina Oza");
					logger.info("Entered spoke with");

					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconaccept"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception saveee) {
							WebElement Accept = Driver.findElement(By.id("idiconaccept"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveb) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).click().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception saveee) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}

					}
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to Confirm Pull stage successfully");
						}
					} catch (Exception e1) {
						logger.info("Job is moved to Confirm Pull stage successfully");

						// --Confirm Pull stage
						stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						msg.append("Stage==" + stage + "\n");
						getScreenshot(Driver, "ConfirmPull_" + PUID);

						// --Label generation
						WebElement LabelG = Driver.findElement(By.id("idiconprint"));
						act.moveToElement(LabelG).build().perform();
						js.executeScript("arguments[0].click();", LabelG);
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Save button
						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
							String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
							logger.info("Validation Message=" + ValMsg);

							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							act.moveToElement(PartPullDate).build().perform();
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							act.moveToElement(PartPullTime).build().perform();
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));

							// --Save button
							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveee) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}
							}
						} catch (Exception e) {
							logger.info("Validation Message is not displayed");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							Thread.sleep(2000);

							// --Get the serial NO
							String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
									.getText();
							logger.info("Serial No of Part is==" + SerialNo + "\n");
							// enter serial number in scan
							WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
							act.moveToElement(SerialNoBar).build().perform();
							SerialNoBar.clear();
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");

							/*
							 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
							 * Thread.sleep(2000);
							 */

							// --Save button
							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveee) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}
							}
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

								/*
								 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
								 * Thread.sleep(2000);
								 */

								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								act.moveToElement(PartPullDate).build().perform();
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								act.moveToElement(PartPullTime).build().perform();
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

								/*
								 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
								 * Thread.sleep(2000);
								 */

								try {
									try {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");

									}
								} catch (Exception Saveee) {
									try {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");
									}
								}
							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}

							/*
							 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
							 * Thread.sleep(2000);
							 */

							// --Checking Error issue
							/*
							 * OrderProcess OPClass = new OrderProcess(); OPClass.dbNullError();
							 */

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", 4, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
							act.moveToElement(OPSearch).build().perform();
							js.executeScript("arguments[0].click();", OPSearch);
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");

								stage = Driver.findElement(By.xpath("//span[contains(@ng-bind,'CurrentStatus')]"))
										.getText();
								logger.info("stage=" + stage);
								msg.append("Stage==" + stage + "\n");
								getScreenshot(Driver, "TenderTo3P_" + PUID);

								WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
								js.executeScript("arguments[0].scrollIntoView();", DelAddress);
								Thread.sleep(2000);

								// --Enter Drop Time
								String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								act.moveToElement(DropTime).build().perform();
								DropTime.clear();
								Date date = new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DropTime.sendKeys(dateFormat.format(date));

								// --Click on Tender To 3P
								try {
									WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Delivery).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Delivery));
									js.executeScript("arguments[0].click();", Delivery);
									logger.info("Clicked on Tender to 3P");
								} catch (Exception DelSave) {
									WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Delivery).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Delivery));
									act.moveToElement(Delivery).click().perform();
									logger.info("Clicked on Tender to 3P");
								}

								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									WebElement DOK = Driver.findElement(By.id("iddataok"));
									js.executeScript("arguments[0].click();", DOK);
									logger.info("Click on OK of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception SPickup) {
									logger.info("Dialogue is not present");
								}

								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
									String Validation = Driver.findElement(By.id("errorid")).getText();
									logger.info("Validation is displayed==" + Validation);

									// --Enter Drop Time
									ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
									act.moveToElement(DropTime).build().perform();
									DropTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									DropTime.sendKeys(dateFormat.format(date));

									// --Click on Tender To 3P
									try {
										WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
										act.moveToElement(Delivery).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Delivery));
										js.executeScript("arguments[0].click();", Delivery);
										logger.info("Clicked on Tender to 3P");
									} catch (Exception DelSave) {
										WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
										act.moveToElement(Delivery).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Delivery));
										act.moveToElement(Delivery).click().perform();
										logger.info("Clicked on Tender to 3P");
									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
										WebElement DOK = Driver.findElement(By.id("iddataok"));
										js.executeScript("arguments[0].click();", DOK);
										logger.info("Click on OK of Dialogue box");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception SPickup) {
										logger.info("Dialogue is not present");
									}

								} catch (Exception Time) {
									logger.info("Time validation is not displayed-Time is as per timeZone");
								}

								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
									String Validation = Driver.findElement(By.id("errorid")).getText();
									logger.info("Validation is displayed==" + Validation);
									WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
									js.executeScript("arguments[0].click();", BtnCLose);
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception POD) {
									logger.info("Validation is not displayed for Package Tracking No");

								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", 4, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
								act.moveToElement(OPSearch).build().perform();
								js.executeScript("arguments[0].click();", OPSearch);
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");
										msg.append("Job is Delivered successfully" + "\n\n");

									}
								} catch (Exception Deliver) {
									logger.info("Job is not Delivered successfully");
									msg.append("Job is not Delivered successfully" + "\n\n");

									WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
									js.executeScript("arguments[0].click();", BtnCLose);
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								}

							}
						} catch (Exception errmsg) {
							logger.info("Validation message is not displayed");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", 4, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
							act.moveToElement(OPSearch).build().perform();
							js.executeScript("arguments[0].click();", OPSearch);
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");
							}
						}
					}

				} catch (Exception e) {
					logger.info("Spoke with validation is not displayed");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 4, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to Confirm Pull stage successfully");
						}
					} catch (Exception Data) {
						logger.info("Job is moved to Confirm Pull successfully");
					}
				}

			} else if (Orderstage.contains("Confirm Pull")) {
				logger.info("Job is moved to Confirm Pull stage successfully");

				// --Confirm Pull stage
				String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
				logger.info("stage=" + stage);
				msg.append("Stage==" + stage + "\n");
				getScreenshot(Driver, "ConfirmPull_" + PUID);

				// --Label generation
				WebElement LabelG = Driver.findElement(By.id("idiconprint"));
				act.moveToElement(LabelG).build().perform();
				js.executeScript("arguments[0].click();", LabelG);
				logger.info("Clicked on Label Generation");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// Handle Label Generation window
				String WindowHandlebefore = Driver.getWindowHandle();
				for (String windHandle : Driver.getWindowHandles()) {
					Driver.switchTo().window(windHandle);
					logger.info("Switched to Label generation window");
					Thread.sleep(5000);
					getScreenshot(Driver, "Labelgeneration" + PUID);
				}
				Driver.close();
				logger.info("Closed Label generation window");

				Driver.switchTo().window(WindowHandlebefore);
				logger.info("Switched to main window");

				// --Save button
				try {
					try {
						WebElement Accept = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						act.moveToElement(Accept).click().perform();
						logger.info("Clicked on Accept button");

					} catch (Exception Saveb) {
						WebElement Accept = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						js.executeScript("arguments[0].click();", Accept);
						logger.info("Clicked on Accept button");

					}
				} catch (Exception Saveee) {
					try {
						WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						act.moveToElement(Accept).click().perform();
						logger.info("Clicked on Accept button");

					} catch (Exception Saveb) {
						WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						js.executeScript("arguments[0].click();", Accept);
						logger.info("Clicked on Accept button");
					}
				}
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
					String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
					logger.info("Validation Message=" + ValMsg);

					// --ZoneID
					String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
					logger.info("ZoneID of is==" + ZOneID);
					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";
					}

					// --Part Pull Date
					WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
					act.moveToElement(PartPullDate).build().perform();
					PartPullDate.clear();
					Date date = new Date();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullDate.sendKeys(dateFormat.format(date));
					PartPullDate.sendKeys(Keys.TAB);

					// --Part Pull Time
					WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
					act.moveToElement(PartPullTime).build().perform();
					PartPullTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullTime.sendKeys(dateFormat.format(date));

					// --Save button
					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveee) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}
					}
				} catch (Exception e) {
					logger.info("Validation Message is not displayed");
				}

				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
					logger.info("ErroMsg is Displayed="
							+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
					Thread.sleep(2000);

					// --Get the serial NO
					String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]")).getText();
					logger.info("Serial No of Part is==" + SerialNo + "\n");
					// enter serial number in scan
					WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
					act.moveToElement(SerialNoBar).build().perform();
					SerialNoBar.clear();
					Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
					Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					logger.info("Entered serial No in scan barcode");

					/*
					 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
					 * Thread.sleep(2000);
					 */

					// --Save button
					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveee) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}
					}
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
						logger.info("ErroMsg is Displayed="
								+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

						/*
						 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
						 * Thread.sleep(2000);
						 */

						// --ZoneID
						String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
								.getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}

						// --Part Pull Date
						WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
						act.moveToElement(PartPullDate).build().perform();
						PartPullDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PartPullDate.sendKeys(dateFormat.format(date));
						PartPullDate.sendKeys(Keys.TAB);

						// --Part Pull Time
						WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
						act.moveToElement(PartPullTime).build().perform();
						PartPullTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PartPullTime.sendKeys(dateFormat.format(date));

						/*
						 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
						 * Thread.sleep(2000);
						 */

						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
					} catch (Exception Time) {
						logger.info("Time validation is not displayed-Time is as per timeZone");
					}

					/*
					 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
					 * Thread.sleep(2000);
					 */

					// --Checking Error issue
					/*
					 * OrderProcess OPClass = new OrderProcess(); OPClass.dbNullError();
					 */

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 4, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to TENDER TO 3P stage successfully");
						}
					} catch (Exception e) {
						logger.info("Job is moved to TENDER TO 3P stage successfully");

						stage = Driver.findElement(By.xpath("//span[contains(@ng-bind,'CurrentStatus')]")).getText();
						logger.info("stage=" + stage);
						msg.append("Stage==" + stage + "\n");
						getScreenshot(Driver, "TenderTo3P_" + PUID);

						WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", DelAddress);
						Thread.sleep(2000);

						// --Enter Drop Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						act.moveToElement(DropTime).build().perform();
						DropTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DropTime.sendKeys(dateFormat.format(date));

						// --Click on Tender To 3P
						try {
							WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Delivery).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Delivery));
							js.executeScript("arguments[0].click();", Delivery);
							logger.info("Clicked on Tender to 3P");
						} catch (Exception DelSave) {
							WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Delivery).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Delivery));
							act.moveToElement(Delivery).click().perform();
							logger.info("Clicked on Tender to 3P");
						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							WebElement DOK = Driver.findElement(By.id("iddataok"));
							js.executeScript("arguments[0].click();", DOK);
							logger.info("Click on OK of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception SPickup) {
							logger.info("Dialogue is not present");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String Validation = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation is displayed==" + Validation);

							// --Enter Drop Time
							ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							act.moveToElement(DropTime).build().perform();
							DropTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DropTime.sendKeys(dateFormat.format(date));

							// --Click on Tender To 3P
							try {
								WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Delivery).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Delivery));
								js.executeScript("arguments[0].click();", Delivery);
								logger.info("Clicked on Tender to 3P");
							} catch (Exception DelSave) {
								WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Delivery).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Delivery));
								act.moveToElement(Delivery).click().perform();
								logger.info("Clicked on Tender to 3P");
							}

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
								WebElement DOK = Driver.findElement(By.id("iddataok"));
								js.executeScript("arguments[0].click();", DOK);
								logger.info("Click on OK of Dialogue box");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception SPickup) {
								logger.info("Dialogue is not present");
							}

						} catch (Exception Time) {
							logger.info("Time validation is not displayed-Time is as per timeZone");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String Validation = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation is displayed==" + Validation);
							WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
							js.executeScript("arguments[0].click();", BtnCLose);
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception POD) {
							logger.info("Validation is not displayed for Package Tracking No");

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", 4, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
						act.moveToElement(OPSearch).build().perform();
						js.executeScript("arguments[0].click();", OPSearch);
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is Delivered successfully");
								msg.append("Job is Delivered successfully" + "\n\n");

							}
						} catch (Exception Deliver) {
							logger.info("Job is not Delivered successfully");
							msg.append("Job is not Delivered successfully" + "\n\n");

							WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
							js.executeScript("arguments[0].click();", BtnCLose);
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}

					}
				} catch (Exception errmsg) {
					logger.info("Validation message is not displayed");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 4, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to TENDER TO 3P stage successfully");
						}
					} catch (Exception e) {
						logger.info("Job is moved to TENDER TO 3P stage successfully");
					}
				}
			} else if (Orderstage.contains("Pull Inventory and Apply Return Pack(s)")) {
				logger.info("Job is moved to Confirm Pull stage successfully");

				// --Confirm Pull stage
				String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
				logger.info("stage=" + stage);
				msg.append("Stage==" + stage + "\n");
				getScreenshot(Driver, "ConfirmPull_" + PUID);

				// --Label generation
				WebElement LabelG = Driver.findElement(By.id("idiconprint"));
				act.moveToElement(LabelG).build().perform();
				js.executeScript("arguments[0].click();", LabelG);
				logger.info("Clicked on Label Generation");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// Handle Label Generation window
				String WindowHandlebefore = Driver.getWindowHandle();
				for (String windHandle : Driver.getWindowHandles()) {
					Driver.switchTo().window(windHandle);
					logger.info("Switched to Label generation window");
					Thread.sleep(5000);
					getScreenshot(Driver, "Labelgeneration" + PUID);
				}
				Driver.close();
				logger.info("Closed Label generation window");

				Driver.switchTo().window(WindowHandlebefore);
				logger.info("Switched to main window");

				// --Save button
				try {
					try {
						WebElement Accept = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						act.moveToElement(Accept).click().perform();
						logger.info("Clicked on Accept button");

					} catch (Exception Saveb) {
						WebElement Accept = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						js.executeScript("arguments[0].click();", Accept);
						logger.info("Clicked on Accept button");

					}
				} catch (Exception Saveee) {
					try {
						WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						act.moveToElement(Accept).click().perform();
						logger.info("Clicked on Accept button");

					} catch (Exception Saveb) {
						WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
						act.moveToElement(Accept).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Accept));
						js.executeScript("arguments[0].click();", Accept);
						logger.info("Clicked on Accept button");
					}
				}
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
					String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
					logger.info("Validation Message=" + ValMsg);

					// --ZoneID
					String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
					logger.info("ZoneID of is==" + ZOneID);
					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";
					}

					// --Part Pull Date
					WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
					act.moveToElement(PartPullDate).build().perform();
					PartPullDate.clear();
					Date date = new Date();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullDate.sendKeys(dateFormat.format(date));
					PartPullDate.sendKeys(Keys.TAB);

					// --Part Pull Time
					WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
					act.moveToElement(PartPullTime).build().perform();
					PartPullTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullTime.sendKeys(dateFormat.format(date));

					// --Save button
					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveee) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}
					}
				} catch (Exception e) {
					logger.info("Validation Message is not displayed");
				}

				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
					logger.info("ErroMsg is Displayed="
							+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
					Thread.sleep(2000);

					// --Get the serial NO
					String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]")).getText();
					logger.info("Serial No of Part is==" + SerialNo + "\n");
					// enter serial number in scan
					WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
					act.moveToElement(SerialNoBar).build().perform();
					SerialNoBar.clear();
					Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
					Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					logger.info("Entered serial No in scan barcode");

					/*
					 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
					 * Thread.sleep(2000);
					 */

					// --Save button
					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveee) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}
					}
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
						logger.info("ErroMsg is Displayed="
								+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

						/*
						 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
						 * Thread.sleep(2000);
						 */

						// --ZoneID
						String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
								.getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}

						// --Part Pull Date
						WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
						act.moveToElement(PartPullDate).build().perform();
						PartPullDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PartPullDate.sendKeys(dateFormat.format(date));
						PartPullDate.sendKeys(Keys.TAB);

						// --Part Pull Time
						WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
						act.moveToElement(PartPullTime).build().perform();
						PartPullTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PartPullTime.sendKeys(dateFormat.format(date));

						/*
						 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
						 * Thread.sleep(2000);
						 */

						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
					} catch (Exception Time) {
						logger.info("Time validation is not displayed-Time is as per timeZone");
					}

					/*
					 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
					 * Thread.sleep(2000);
					 */

					// --Checking Error issue
					/*
					 * OrderProcess OPClass = new OrderProcess(); OPClass.dbNullError();
					 */

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 4, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to TENDER TO 3P stage successfully");
						}
					} catch (Exception e) {
						logger.info("Job is moved to TENDER TO 3P stage successfully");

						stage = Driver.findElement(By.xpath("//span[contains(@ng-bind,'CurrentStatus')]")).getText();
						logger.info("stage=" + stage);
						msg.append("Stage==" + stage + "\n");
						getScreenshot(Driver, "TenderTo3P_" + PUID);

						WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", DelAddress);
						Thread.sleep(2000);

						// --Enter Drop Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						act.moveToElement(DropTime).build().perform();
						DropTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DropTime.sendKeys(dateFormat.format(date));

						// --Click on Tender To 3P
						try {
							WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Delivery).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Delivery));
							js.executeScript("arguments[0].click();", Delivery);
							logger.info("Clicked on Tender to 3P");
						} catch (Exception DelSave) {
							WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Delivery).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Delivery));
							act.moveToElement(Delivery).click().perform();
							logger.info("Clicked on Tender to 3P");
						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							WebElement DOK = Driver.findElement(By.id("iddataok"));
							js.executeScript("arguments[0].click();", DOK);
							logger.info("Click on OK of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception SPickup) {
							logger.info("Dialogue is not present");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String Validation = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation is displayed==" + Validation);

							// --Enter Drop Time
							ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							act.moveToElement(DropTime).build().perform();
							DropTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DropTime.sendKeys(dateFormat.format(date));

							// --Click on Tender To 3P
							try {
								WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Delivery).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Delivery));
								js.executeScript("arguments[0].click();", Delivery);
								logger.info("Clicked on Tender to 3P");
							} catch (Exception DelSave) {
								WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Delivery).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Delivery));
								act.moveToElement(Delivery).click().perform();
								logger.info("Clicked on Tender to 3P");
							}

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
								WebElement DOK = Driver.findElement(By.id("iddataok"));
								js.executeScript("arguments[0].click();", DOK);
								logger.info("Click on OK of Dialogue box");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception SPickup) {
								logger.info("Dialogue is not present");
							}

						} catch (Exception Time) {
							logger.info("Time validation is not displayed-Time is as per timeZone");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String Validation = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation is displayed==" + Validation);
							WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
							js.executeScript("arguments[0].click();", BtnCLose);
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception POD) {
							logger.info("Validation is not displayed for Package Tracking No");

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", 4, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
						act.moveToElement(OPSearch).build().perform();
						js.executeScript("arguments[0].click();", OPSearch);
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is Delivered successfully");
								msg.append("Job is Delivered successfully" + "\n\n");

							}
						} catch (Exception Deliver) {
							logger.info("Job is not Delivered successfully");
							msg.append("Job is not Delivered successfully" + "\n\n");

							WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
							js.executeScript("arguments[0].click();", BtnCLose);
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}

					}
				} catch (Exception errmsg) {
					logger.info("Validation message is not displayed");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 4, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to TENDER TO 3P stage successfully");
						}
					} catch (Exception e) {
						logger.info("Job is moved to TENDER TO 3P stage successfully");
					}
				}
			} else if (Orderstage.contains("Tender to 3P")) {
				logger.info("Job is moved to TENDER TO 3P stage successfully");

				String stage = Driver.findElement(By.xpath("//span[contains(@ng-bind,'CurrentStatus')]")).getText();
				logger.info("stage=" + stage);
				msg.append("Stage==" + stage + "\n");
				getScreenshot(Driver, "TenderTo3P_" + PUID);

				WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
				js.executeScript("arguments[0].scrollIntoView();", DelAddress);
				Thread.sleep(2000);

				// --Enter Drop Time
				String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
				logger.info("ZoneID of is==" + ZOneID);
				if (ZOneID.equalsIgnoreCase("EDT")) {
					ZOneID = "America/New_York";
				} else if (ZOneID.equalsIgnoreCase("CDT")) {
					ZOneID = "CST";
				}
				WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
				act.moveToElement(DropTime).build().perform();
				DropTime.clear();
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
				logger.info(dateFormat.format(date));
				DropTime.sendKeys(dateFormat.format(date));

				// --Click on Tender To 3P
				try {
					WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
					act.moveToElement(Delivery).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Delivery));
					js.executeScript("arguments[0].click();", Delivery);
					logger.info("Clicked on Tender to 3P");
				} catch (Exception DelSave) {
					WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
					act.moveToElement(Delivery).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Delivery));
					act.moveToElement(Delivery).click().perform();
					logger.info("Clicked on Tender to 3P");
				}

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
					WebElement DOK = Driver.findElement(By.id("iddataok"));
					js.executeScript("arguments[0].click();", DOK);
					logger.info("Click on OK of Dialogue box");

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception SPickup) {
					logger.info("Dialogue is not present");
				}

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
					String Validation = Driver.findElement(By.id("errorid")).getText();
					logger.info("Validation is displayed==" + Validation);

					// --Enter Drop Time
					ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
					logger.info("ZoneID of is==" + ZOneID);
					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";
					}
					DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
					act.moveToElement(DropTime).build().perform();
					DropTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					DropTime.sendKeys(dateFormat.format(date));

					// --Click on Tender To 3P
					try {
						WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Delivery).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Delivery));
						js.executeScript("arguments[0].click();", Delivery);
						logger.info("Clicked on Tender to 3P");
					} catch (Exception DelSave) {
						WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Delivery).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Delivery));
						act.moveToElement(Delivery).click().perform();
						logger.info("Clicked on Tender to 3P");
					}

					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
						WebElement DOK = Driver.findElement(By.id("iddataok"));
						js.executeScript("arguments[0].click();", DOK);
						logger.info("Click on OK of Dialogue box");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception SPickup) {
						logger.info("Dialogue is not present");
					}

				} catch (Exception Time) {
					logger.info("Time validation is not displayed-Time is as per timeZone");
				}

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
					String Validation = Driver.findElement(By.id("errorid")).getText();
					logger.info("Validation is displayed==" + Validation);
					WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
					js.executeScript("arguments[0].click();", BtnCLose);
					logger.info("Click on Close button");

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception POD) {
					logger.info("Validation is not displayed for Package Tracking No");

				}
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
				PUID = getData("OrderProcessing", 4, 1);
				Driver.findElement(By.id("txtBasicSearch2")).clear();
				Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
				OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
				act.moveToElement(OPSearch).build().perform();
				js.executeScript("arguments[0].click();", OPSearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is Delivered successfully");
						msg.append("Job is Delivered successfully" + "\n\n");

					}
				} catch (Exception Deliver) {
					logger.info("Job is not Delivered successfully");
					msg.append("Job is not Delivered successfully" + "\n\n");

					WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
					js.executeScript("arguments[0].click();", BtnCLose);
					logger.info("Click on Close button");

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				}

			} else {
				logger.info("unknown stage found for H3P service");
				msg.append("unknown stage found for H3P service" + "\n\n");

			}
		} catch (

		Exception NoData1) {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not exist with the search parameters");
					msg.append("Job is not exist with the search parameters" + "\n\n");

				}
			} catch (Exception OnBoard) {

				String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
				logger.info("Current stage of the order is=" + Orderstage);
				msg.append("Current stage of the order is=" + Orderstage + "\n");
				logger.info("Issue in Order stage==" + Orderstage);
				msg.append("Issue in Order stage==" + Orderstage + "\n\n");
				getScreenshot(Driver, "H3PStageIssue_" + Orderstage);

			}
		}

		Driver.findElement(By.id("imgNGLLogo")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

		logger.info("=====H3P Order Processing Test End=====");
		msg.append("=====H3P Order Processing Test End=====" + "\n\n");

	}

}
