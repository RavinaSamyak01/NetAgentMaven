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

public class T3PLAST_OrderProcess extends BaseInit {

	@Test
	public void orderProcess3PLASTJOB() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=====3PLAST Order Processing Test Start=====");
		msg.append("=====3PLAST Order Processing Test Start=====" + "\n\n");

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

		getScreenshot(Driver, "TaskLog_Operations3PLAST");

		for (int row = 6; row < 8; row++) {
			if (row == 7) {
				msg.append("\n\n");
				msg.append("==Confirm DEL Alert Scenario start==" + "\n");
			}

			String ServiceID = getData("OrderProcessing", row, 0);
			logger.info("ServiceID is==" + ServiceID);
			msg.append("ServiceID==" + ServiceID + "\n");
			String PUID = getData("OrderProcessing", row, 1);
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

				// --Checking editor
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"hlkMemo\"][contains(text(),'Memo')]")));

				// Ship Label
				OrderProcess OP = new OrderProcess();

				OP.shipLabel(PUID);

				// --Memo
				OP.memo(PUID);

				// -Notification
				OP.notification(PUID);

				// Upload
				OP.upload(PUID);

				// --Print Pull
				OP.printPull(PUID);

				String Orderstage = null;
				try {
					String Orderstage1 = Driver.findElement(By.xpath("//div/h3[contains(@class,\"ng-binding\")]"))
							.getText();
					Orderstage = Orderstage1;
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");
				} catch (Exception stage) {
					try {
						String Orderstage2 = Driver.findElement(By.xpath("//Strong/span[@class=\"ng-binding\"]"))
								.getText();
						Orderstage = Orderstage2;
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
					} catch (Exception SName) {
						String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]")).getText();
						Orderstage = StageName;
						logger.info("Stage is==" + StageName);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
					}
				}
				if (Orderstage.equalsIgnoreCase("CONF PULL ALERT")) {
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");

					// --Click on Accept button //
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

					WebElement SpokeWithVal = Driver.findElement(By.id("idValidation"));
					if (SpokeWithVal.isDisplayed()) {
						logger.info("Spoke with validation is displayed==" + SpokeWithVal.getText());

					} else {
						logger.info("Spoke with validation is not displayed");

					}
					// --Spoke with
					Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("RV");
					logger.info("Enter Spoke with");

					// --Click on Accept button //
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
					// --Search again
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", row, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
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

						// --Confirm Pull
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						logger.info("If order stage is Confirm Pull.....");
						getScreenshot(Driver, "3PLAST_ConfirmPull_" + PUID);

						// --Label generation
						Driver.findElement(By.id("idiconprint")).click();
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "3PLAST_Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Click on Accept
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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							// --Part Pull DateTime is required. Validation
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//li[contains(text(),'Part Pull DateTime is required.')]")));
							/*
							 * wait.until( ExpectedConditions.visibilityOfElementLocated(By.id(
							 * "idPartPullDttmValidation")));
							 */
							String ValMsg = Driver
									.findElement(By.xpath("//li[contains(text(),'Part Pull DateTime is required.')]"))
									.getText();
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
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							PartPullTime.sendKeys(Keys.TAB);

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
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception e) {
							logger.info("Validation Message is not displayed, Part Pull DateTime is required");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

							String ErrorMsg = Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
									.getText();
							logger.info("Error Message=" + ErrorMsg);

							if (ErrorMsg.contains("Verify all parts before proceeding")) {
								logger.info("Error Message is displayed=" + ErrorMsg);
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

							} else if (ErrorMsg.contains("Actual Datetime can not be greater than Current Datetime.")) {
								logger.info("Error Message is displayed=" + ErrorMsg);
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
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));
								PartPullTime.sendKeys(Keys.TAB);
							} else {
								logger.info("Unexpected validation message displayed, " + ErrorMsg);

							}

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
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception e) {
							logger.info("Error Message is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

							String ErrorMsg = Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
									.getText();
							logger.info("Error Message=" + ErrorMsg);

							if (ErrorMsg.contains("Verify all parts before proceeding")) {
								logger.info("Error Message is displayed=" + ErrorMsg);
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

							} else if (ErrorMsg.contains("Actual Datetime can not be greater than Current Datetime.")) {
								logger.info("Error Message is displayed=" + ErrorMsg);
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
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));
								PartPullTime.sendKeys(Keys.TAB);
							} else {
								logger.info("Unexpected validation message displayed, " + ErrorMsg);

							}

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
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception e) {
							logger.info("Error Message is not displayed");

						}

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
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
								logger.info("Job is not moved to TENDER TO 3P stage successfully");
							}
						} catch (Exception e) {
							logger.info("Job is moved to TENDER TO 3P stage successfully");
							getScreenshot(Driver, "3PLAST_TenderTo3P_" + PUID);

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
							DropTime.sendKeys(Keys.TAB);

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
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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
								DropTime.sendKeys(Keys.TAB);

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
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
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
									logger.info("Job is moved to Send DEL Alert stage successfully");
									msg.append("Job is moved to Send DEL Alert stage successfully" + "\n\n");

								}
							} catch (Exception Deliver) {
								logger.info("Job is not moved to Send DEL Alert stage successfully");
								msg.append("Job is not moved to Send DEL Alert stage successfully" + "\n\n");

								WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
								js.executeScript("arguments[0].click();", BtnCLose);
								logger.info("Click on Close button");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							}

						}

					}

				} else if (Orderstage.equalsIgnoreCase("Confirm Pull")) {
					logger.info("Job is moved to Confirm Pull stage successfully");

					// --Confirm Pull
					String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
					logger.info("stage=" + stage);
					logger.info("If order stage is Confirm Pull.....");
					getScreenshot(Driver, "3PLAST_ConfirmPull_" + PUID);

					// --Label generation
					Driver.findElement(By.id("idiconprint")).click();
					logger.info("Clicked on Label Generation");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					// Handle Label Generation window
					String WindowHandlebefore = Driver.getWindowHandle();
					for (String windHandle : Driver.getWindowHandles()) {
						Driver.switchTo().window(windHandle);
						logger.info("Switched to Label generation window");
						Thread.sleep(5000);
						getScreenshot(Driver, "3PLAST_Labelgeneration" + PUID);
					}
					Driver.close();
					logger.info("Closed Label generation window");

					Driver.switchTo().window(WindowHandlebefore);
					logger.info("Switched to main window");

					// --Click on Accept
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
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						// --Part Pull DateTime is required. Validation
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//li[contains(text(),'Part Pull DateTime is required.')]")));
						/*
						 * wait.until( ExpectedConditions.visibilityOfElementLocated(By.id(
						 * "idPartPullDttmValidation")));
						 */
						String ValMsg = Driver
								.findElement(By.xpath("//li[contains(text(),'Part Pull DateTime is required.')]"))
								.getText();
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
						PartPullDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PartPullDate.sendKeys(dateFormat.format(date));
						PartPullDate.sendKeys(Keys.TAB);

						// --Part Pull Time
						WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
						PartPullTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PartPullTime.sendKeys(dateFormat.format(date));
						PartPullTime.sendKeys(Keys.TAB);

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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception e) {
						logger.info("Validation Message is not displayed, Part Pull DateTime is required");

					}

					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
						logger.info("ErroMsg is Displayed="
								+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

						String ErrorMsg = Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
								.getText();
						logger.info("Error Message=" + ErrorMsg);

						if (ErrorMsg.contains("Verify all parts before proceeding")) {
							logger.info("Error Message is displayed=" + ErrorMsg);
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

						} else if (ErrorMsg.contains("Actual Datetime can not be greater than Current Datetime.")) {
							logger.info("Error Message is displayed=" + ErrorMsg);
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
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							PartPullTime.sendKeys(Keys.TAB);
						} else {
							logger.info("Unexpected validation message displayed, " + ErrorMsg);

						}

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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception e) {
						logger.info("Error Message is not displayed");

					}

					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
						logger.info("ErroMsg is Displayed="
								+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

						String ErrorMsg = Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
								.getText();
						logger.info("Error Message=" + ErrorMsg);

						if (ErrorMsg.contains("Verify all parts before proceeding")) {
							logger.info("Error Message is displayed=" + ErrorMsg);
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

						} else if (ErrorMsg.contains("Actual Datetime can not be greater than Current Datetime.")) {
							logger.info("Error Message is displayed=" + ErrorMsg);
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
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							PartPullTime.sendKeys(Keys.TAB);
						} else {
							logger.info("Unexpected validation message displayed, " + ErrorMsg);

						}

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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception e) {
						logger.info("Error Message is not displayed");

					}

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", row, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Cleared search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter pickupID in search input");
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
						getScreenshot(Driver, "3PLAST_TenderTo3P_" + PUID);

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
						DropTime.sendKeys(Keys.TAB);

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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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
							DropTime.sendKeys(Keys.TAB);

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
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
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
								logger.info("Job is moved to Send DEL Alert stage successfully");
								msg.append("Job is moved to Send DEL Alert stage successfully" + "\n\n");

							}
						} catch (Exception Deliver) {
							logger.info("Job is not moved to Send DEL Alert stage successfully");
							msg.append("Job is not moved to Send DEL Alert stage successfully" + "\n\n");

							WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
							js.executeScript("arguments[0].click();", BtnCLose);
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}

					}

				} else if (Orderstage.equalsIgnoreCase("TENDER TO 3P")) {
					logger.info("Job is moved to TENDER TO 3P stage successfully");
					getScreenshot(Driver, "3PLAST_TenderTo3P_" + PUID);

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
					DropTime.sendKeys(Keys.TAB);

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
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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
						DropTime.sendKeys(Keys.TAB);

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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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

					} catch (Exception Time) {
						logger.info("Time validation is not displayed-Time is as per timeZone");
					}

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", row, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Cleared search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter pickupID in search input");
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
							logger.info("Job is moved to Send DEL Alert stage successfully");
							msg.append("Job is moved to Send DEL Alert stage successfully" + "\n\n");

						}
					} catch (Exception Deliver) {
						logger.info("Job is not moved to Send DEL Alert stage successfully");
						msg.append("Job is not moved to Send DEL Alert stage successfully" + "\n\n");

						WebElement BtnCLose = Driver.findElement(By.id("btnclsdelivery"));
						js.executeScript("arguments[0].click();", BtnCLose);
						logger.info("Click on Close button");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					}

				} else if (Orderstage.equalsIgnoreCase("Confirm Del Alert")) {

					// --Confirm Del Alert
					logger.info("Job is moved to Confirm Del Alert stage successfully");
					getScreenshot(Driver, "3PLAST_ConfDelAlert_" + PUID);

					// --Click on Confirm
					WebElement Delivery = Driver.findElement(By.id("lblDeliverAddress"));
					js.executeScript("arguments[0].scrollIntoView();", Delivery);
					Thread.sleep(2000);

					WebElement CondirmP = Driver.findElement(By.id("lnkConfPick"));
					act.moveToElement(CondirmP).build().perform();
					js.executeScript("arguments[0].click();", CondirmP);
					logger.info("Clicked on CONFIRM button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						// --Click on Close button //
						WebElement DelClose = Driver.findElement(By.id("idclosetab"));
						act.moveToElement(DelClose).build().perform();
						js.executeScript("arguments[0].click();", DelClose);
						logger.info("Clicked on Close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception close) {
						logger.info("Editor is already closed");

					}

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", row, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Cleared search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter pickupID in search input");
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
							logger.info("Job is not moved to 3rd Party Delivery stage successfully");
							msg.append("Job is not moved to 3rd Party Delivery stage successfully" + "\n");

						}
					} catch (Exception Recover) {
						logger.info("Job is moved to 3rd Party Delivery stage successfully");
						msg.append("Job is moved to 3rd Party Delivery stage successfully" + "\n");

						// ----3rd Party Delivery stage
						String StageName = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
								.getText();
						logger.info("Stage is==" + StageName);
						msg.append("Stage is==" + StageName);
						getScreenshot(Driver, "3PLAST_3rdPartyDelivery_" + PUID);

						// --Click on Close
						WebElement CLS = Driver.findElement(By.id("btnclsdelivery"));
						wait.until(ExpectedConditions.elementToBeClickable(CLS));
						js.executeScript("arguments[0].click();", CLS);
						logger.info("Clicked on Close");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					}

				} else if (Orderstage.equalsIgnoreCase("3rd Party Delivery")) {

					// ----3rd Party Delivery stage
					String StageName = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Stage is==" + StageName);
					getScreenshot(Driver, "3PLAST_3rdPartyDelivery_" + PUID);

					// --Click on Close
					WebElement CLS = Driver.findElement(By.id("btnclsdelivery"));
					wait.until(ExpectedConditions.elementToBeClickable(CLS));
					js.executeScript("arguments[0].click();", CLS);
					logger.info("Clicked on Close");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				}

			} catch (Exception NoData1) {
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
					getScreenshot(Driver, "3PLASTStageIssue_" + Orderstage);

				}
			}
		}

		try {
			WebElement NGLLOgo = Driver.findElement(By.id("imgNGLLogo"));
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		} catch (Exception refresh) {
			WebElement NGLLOgo = Driver.findElement(By.id("aNGLLogo"));
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			act.moveToElement(NGLLOgo).click().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		}

		logger.info("=====3PLAST Order Processing Test End=====");
		msg.append("=====3PLAST Order Processing Test End=====" + "\n\n");

	}

}
