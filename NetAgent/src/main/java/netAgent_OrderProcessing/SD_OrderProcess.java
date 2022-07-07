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

public class SD_OrderProcess extends BaseInit {

	@Test
	public void orderProcessSDJOB()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=====SD Order Processing Test Start=====");
		msg.append("=====SD Order Processing Test Start=====" + "\n\n");

		int rowNum = getTotalRow("OrderProcessing");
		logger.info("total No of Rows=" + rowNum);

		int colNum = getTotalCol("OrderProcessing");
		logger.info("total No of Columns=" + colNum);

		// Go To TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idOperations\"]")));
		WebElement OperationMenu = Driver.findElement(By.xpath("//a[@id=\"idOperations\"]"));
		// act.moveToElement(OperationMenu).build().perform();
		act.moveToElement(OperationMenu).click().perform();
		logger.info("Click on Operations");

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idTask\"]")));
		WebElement TaskLogMenu = Driver.findElement(By.xpath("//a[@id=\"idTask\"]"));
		act.moveToElement(TaskLogMenu).click().perform();
		// js.executeScript("arguments[0].click();", TaskLogMenu);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		logger.info("Click on Task Log");

		getScreenshot(Driver, "TaskLog_OperationsSD");

		for (int row = 2; row < 4; row++) {
			if (row == 3) {
				msg.append("\n\n");
				msg.append("==Confirm DEL Alert Scenario start=="+"\n");
			}
			String ServiceID = getData("OrderProcessing", row, 0);
			logger.info("ServiceID is==" + ServiceID);
			msg.append("ServiceID==" + ServiceID + "\n");
			String PUID = getData("OrderProcessing", row, 1);
			logger.info("PickUpID is==" + PUID);
			msg.append("PickUpID==" + PUID + "\n");
			Thread.sleep(2000);
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
				Driver.findElement(By.id("txtBasicSearch2")).clear();
				logger.info("Clear search input");
				Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
				logger.info("Enter PickUpID in Search input");
				WebElement OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
				act.moveToElement(OPSearch).build().perform();
				js.executeScript("arguments[0].click();", OPSearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				try {
					WebElement PickuPBox = Driver.findElement(By.xpath("//*[contains(@class,'pickupbx')]"));
					if (PickuPBox.isDisplayed()) {
						logger.info("Searched Job is displayed in edit mode");
						getScreenshot(Driver, "SDOrderEditor_" + PUID);
					}
				} catch (Exception ee) {
					WebElement PickuPBox = Driver.findElement(By.xpath("//*[@name=\"ConfPullAlertForm\"]"));
					if (PickuPBox.isDisplayed()) {
						logger.info("Searched Job is displayed in edit mode");
						getScreenshot(Driver, "SDOrderEditor_" + PUID);
					}
				}

				if (row == 2) {
					msg.append("\n\n");
					// --Memo

					OrderProcess OP = new OrderProcess();
					OP.memo(PUID);

					// -Notification
					OP.notification(PUID);

					// Upload
					OP.upload(PUID);

					// Map
					OP.map(PUID);
				}

				// --Get current stage of the order
				String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
				logger.info("Current stage of the order is=" + Orderstage);
				msg.append("Stage==" + Orderstage + "\n");

				if (Orderstage.equalsIgnoreCase("Confirm Pu Alert")) {

					// --Confirm button
					WebElement ConfPU = Driver.findElement(By.id("lnkConfPick"));
					js.executeScript("arguments[0].scrollIntoView();", ConfPU);
					act.moveToElement(ConfPU).build().perform();
					js.executeScript("arguments[0].click();", ConfPU);
					logger.info("Clicked on CONFIRM button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						// --Click on Close button //
						WebElement Closebtn = Driver.findElement(By.id("idclosetab"));
						act.moveToElement(Closebtn).build().perform();
						js.executeScript("arguments[0].click();", Closebtn);
						logger.info("Clicked on Close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception close) {
						logger.info("Editor is already closed");

					}

					// --Search again
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
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
							logger.info("Job is not moved to PICKUP stage successfully");

						}
					} catch (Exception e1) {
						logger.info("Job is moved to PICKUP stage successfully");

						// --Pickup
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Stage==" + Orderstage + "\n");

						// js.executeScript("document.body.style.zoom = '80%';");

						WebElement DArrtime = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", DArrtime);
						Thread.sleep(2000);
						// js.executeScript("arguments[0].scrollIntoView();", DArrtime);

						// --Enter PickUp Time

						String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
						logger.info("ZoneID of is==" + ZOneID);

						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";

						}

						WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
						act.moveToElement(PUPTime).build().perform();
						PUPTime.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PUPTime.sendKeys(dateFormat.format(date));
						PUPTime.sendKeys(Keys.TAB);

						// Scroll up
						// js.executeScript("window.scrollBy(0,-250)");

						// PickUp
						try {
							WebElement Save = Driver.findElement(By.id("lnksave"));
							act.moveToElement(Save).build().perform();
							js.executeScript("arguments[0].click();", Save);
							logger.info("Clicked on PICKUP button");
						} catch (Exception saveeee) {
							WebElement Save = Driver.findElement(By.id("lnksave"));
							act.moveToElement(Save).build().perform();
							act.moveToElement(Save).click().perform();
							logger.info("Clicked on PICKUP button");
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
							WebElement Dyes = Driver.findElement(By.id("iddataok"));
							js.executeScript("arguments[0].click();", Dyes);
							logger.info("Clicked on Yes button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception e) {
							logger.info("Dialogue is not exist");

						}
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
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
								logger.info("Job is not moved to Drop@Origin stage successfully");

							}
						} catch (Exception DNoData) {

							logger.info("Job is moved to Drop@Origin stage successfully");
							Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);
							msg.append("Moved to Stage==" + Orderstage + "\n");

							WebElement DelAtt = Driver.findElement(By.id("lblAddress"));
							js.executeScript("arguments[0].scrollIntoView();", DelAtt);
							Thread.sleep(2000);

							// --Drop Time

							ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							act.moveToElement(DelTime).build().perform();
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DelTime.sendKeys(dateFormat.format(date));
							DelTime.sendKeys(Keys.TAB);

							// --Click on Drop

							try {
								WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Drop).build().perform();
								js.executeScript("arguments[0].click();", Drop);
								logger.info("Clicked on Drop button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception Dropp) {
								try {
									WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
									wait.until(ExpectedConditions.visibilityOf(Drop));
									wait.until(ExpectedConditions.elementToBeClickable(Drop));
									act.moveToElement(Drop).build().perform();
									act.moveToElement(Drop).click().perform();
									logger.info("Clicked on Drop button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception dropee) {
									logger.info("Finally Clicked on Drop button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							}

							try {
								WebElement ErrorID = Driver.findElement(By.id("errorid"));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
								if (ErrorID.getText().contains("The Air Bill is required")) {
									logger.info("Message:-" + ErrorID.getText());

									// --Add Airbill
									WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
									js.executeScript("arguments[0].scrollIntoView();", AirBill);

									WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
									js.executeScript("arguments[0].click();", AddAirBill);
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
									logger.info("AirBill editor is opened");

									/// --Enter AirBill

									WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
									act.moveToElement(Airbill).build().perform();
									Airbill.sendKeys("11111111");
									logger.info("Entered AirBill");

									/// --Enter Description
									WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
									act.moveToElement(Desc).build().perform();
									Desc.sendKeys("SD Service Automation");
									logger.info("Entered Description");

									/// --Enter NoOFPieces
									WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
									act.moveToElement(NoOFP).build().perform();
									NoOFP.sendKeys("2");
									logger.info("Entered NoOFPieces");

									/// --Enter Total Weight
									WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
									act.moveToElement(TWght).build().perform();
									TWght.sendKeys("10");
									logger.info("Entered Total Weight");

									// --Track
									wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
									WebElement Track = Driver.findElement(By.linkText("Track"));
									js.executeScript("arguments[0].click();", Track);
									logger.info("Clicked on Track button");

									// --AIrbill new window
									String WindowHandlebefore = Driver.getWindowHandle();
									for (String windHandle : Driver.getWindowHandles()) {
										Driver.switchTo().window(windHandle);
										logger.info("Switched to Track window");

										Thread.sleep(5000);
										getScreenshot(Driver, "Track" + PUID);

									}
									Driver.close();
									logger.info("Closed Track window");

									Driver.switchTo().window(WindowHandlebefore);
									logger.info("Switched to main window");

									// Scroll up
									js.executeScript("window.scrollBy(0,-250)");

									// --Click on Drop
									try {
										WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
										act.moveToElement(Drop).build().perform();
										js.executeScript("arguments[0].click();", Drop);
										logger.info("Clicked on Drop button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									} catch (Exception Dropp) {
										try {
											WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
											wait.until(ExpectedConditions.visibilityOf(Drop));
											wait.until(ExpectedConditions.elementToBeClickable(Drop));
											act.moveToElement(Drop).build().perform();
											act.moveToElement(Drop).click().perform();
											logger.info("Clicked on Drop button");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} catch (Exception dropee) {
											logger.info("Finally Clicked on Drop button");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));
										}
									}

									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
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
											logger.info("Job is moved to Send Del Alert successfully");
											msg.append("Moved to Stage==Send Del Alert" + "\n\n");

										}
									} catch (Exception dNoData) {
										logger.info("Job is not moved to Send Del Alert successfully");

									}

								}
							} catch (Exception AB) {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
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
										logger.info("Job is moved to Send Del Alert successfully");
										msg.append("Moved to Stage==Send Del Alert" + "\n\n");

									}
								} catch (Exception NoDataD) {
									logger.info("Job is not moved Send Del Alert yet");

								}
							}

						}

					}

				} else if (Orderstage.equalsIgnoreCase("PICKUP")) {
					logger.info("Job is moved to PICKUP stage successfully");

					// --Pickup
					Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Stage==" + Orderstage + "\n");

					// js.executeScript("document.body.style.zoom = '80%';");

					WebElement DArrtime = Driver.findElement(By.id("lblAddress"));
					js.executeScript("arguments[0].scrollIntoView();", DArrtime);
					Thread.sleep(2000);
					// js.executeScript("arguments[0].scrollIntoView();", DArrtime);

					// --Enter PickUp Time

					String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
					logger.info("ZoneID of is==" + ZOneID);

					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";

					}

					WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
					act.moveToElement(PUPTime).build().perform();
					PUPTime.clear();
					Date date = new Date();
					DateFormat dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PUPTime.sendKeys(dateFormat.format(date));
					PUPTime.sendKeys(Keys.TAB);

					// Scroll up
					// js.executeScript("window.scrollBy(0,-250)");

					// PickUp
					try {
						WebElement Save = Driver.findElement(By.id("lnksave"));
						act.moveToElement(Save).build().perform();
						js.executeScript("arguments[0].click();", Save);
						logger.info("Clicked on PICKUP button");
					} catch (Exception saveeee) {
						WebElement Save = Driver.findElement(By.id("lnksave"));
						act.moveToElement(Save).build().perform();
						act.moveToElement(Save).click().perform();
						logger.info("Clicked on PICKUP button");
					}
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
						WebElement Dyes = Driver.findElement(By.id("iddataok"));
						js.executeScript("arguments[0].click();", Dyes);
						logger.info("Clicked on Yes button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception e) {
						logger.info("Dialogue is not exist");

					}
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
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
							logger.info("Job is not moved to Drop@Origin stage successfully");

						}
					} catch (Exception DNoData) {

						logger.info("Job is moved to Drop@Origin stage successfully");
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Moved to Stage==" + Orderstage + "\n");

						WebElement DelAtt = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", DelAtt);
						Thread.sleep(2000);

						// --Drop Time

						ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}

						WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						act.moveToElement(DelTime).build().perform();
						DelTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DelTime.sendKeys(dateFormat.format(date));
						DelTime.sendKeys(Keys.TAB);

						// --Click on Drop

						try {
							WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Drop).build().perform();
							js.executeScript("arguments[0].click();", Drop);
							logger.info("Clicked on Drop button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception Dropp) {
							try {
								WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
								wait.until(ExpectedConditions.visibilityOf(Drop));
								wait.until(ExpectedConditions.elementToBeClickable(Drop));
								act.moveToElement(Drop).build().perform();
								act.moveToElement(Drop).click().perform();
								logger.info("Clicked on Drop button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception dropee) {
								logger.info("Finally Clicked on Drop button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						}

						try {
							WebElement ErrorID = Driver.findElement(By.id("errorid"));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
							if (ErrorID.getText().contains("The Air Bill is required")) {
								logger.info("Message:-" + ErrorID.getText());

								// --Add Airbill
								WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
								js.executeScript("arguments[0].scrollIntoView();", AirBill);

								WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
								js.executeScript("arguments[0].click();", AddAirBill);
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
										By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
								logger.info("AirBill editor is opened");

								/// --Enter AirBill

								WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
								act.moveToElement(Airbill).build().perform();
								Airbill.sendKeys("11111111");
								logger.info("Entered AirBill");

								/// --Enter Description
								WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
								act.moveToElement(Desc).build().perform();
								Desc.sendKeys("SD Service Automation");
								logger.info("Entered Description");

								/// --Enter NoOFPieces
								WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
								act.moveToElement(NoOFP).build().perform();
								NoOFP.sendKeys("2");
								logger.info("Entered NoOFPieces");

								/// --Enter Total Weight
								WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
								act.moveToElement(TWght).build().perform();
								TWght.sendKeys("10");
								logger.info("Entered Total Weight");

								// --Track
								wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
								WebElement Track = Driver.findElement(By.linkText("Track"));
								js.executeScript("arguments[0].click();", Track);
								logger.info("Clicked on Track button");

								// --AIrbill new window
								String WindowHandlebefore = Driver.getWindowHandle();
								for (String windHandle : Driver.getWindowHandles()) {
									Driver.switchTo().window(windHandle);
									logger.info("Switched to Track window");

									Thread.sleep(5000);
									getScreenshot(Driver, "Track" + PUID);

								}
								Driver.close();
								logger.info("Closed Track window");

								Driver.switchTo().window(WindowHandlebefore);
								logger.info("Switched to main window");

								// Scroll up
								js.executeScript("window.scrollBy(0,-250)");

								// --Click on Drop
								try {
									WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Drop).build().perform();
									js.executeScript("arguments[0].click();", Drop);
									logger.info("Clicked on Drop button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								} catch (Exception Dropp) {
									try {
										WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
										wait.until(ExpectedConditions.visibilityOf(Drop));
										wait.until(ExpectedConditions.elementToBeClickable(Drop));
										act.moveToElement(Drop).build().perform();
										act.moveToElement(Drop).click().perform();
										logger.info("Clicked on Drop button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception dropee) {
										logger.info("Finally Clicked on Drop button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									}
								}

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
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
										logger.info("Job is moved to Send Del Alert successfully");
										msg.append("Moved to Stage==Send Del Alert" + "\n\n");

									}
								} catch (Exception dNoData) {
									logger.info("Job is not moved to Send Del Alert successfully");

								}

							}
						} catch (Exception AB) {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
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
									logger.info("Job is moved to Send Del Alert successfully");
									msg.append("Moved to Stage==Send Del Alert" + "\n\n");

								}
							} catch (Exception NoDataD) {
								logger.info("Job is not moved Send Del Alert yet");

							}
						}

					}

				} else if (Orderstage.equalsIgnoreCase("Drop @ Origin")) {

					logger.info("Job is moved to Drop@Origin stage successfully");
					Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Moved to Stage==" + Orderstage + "\n");

					WebElement DelAtt = Driver.findElement(By.id("lblAddress"));
					js.executeScript("arguments[0].scrollIntoView();", DelAtt);
					Thread.sleep(2000);

					// --Drop Time

					String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
					logger.info("ZoneID of is==" + ZOneID);
					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";
					}

					WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
					act.moveToElement(DelTime).build().perform();
					DelTime.clear();
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					DelTime.sendKeys(dateFormat.format(date));
					DelTime.sendKeys(Keys.TAB);

					// --Click on Drop

					try {
						WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Drop).build().perform();
						js.executeScript("arguments[0].click();", Drop);
						logger.info("Clicked on Drop button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception Dropp) {
						try {
							WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
							wait.until(ExpectedConditions.visibilityOf(Drop));
							wait.until(ExpectedConditions.elementToBeClickable(Drop));
							act.moveToElement(Drop).build().perform();
							act.moveToElement(Drop).click().perform();
							logger.info("Clicked on Drop button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception dropee) {
							logger.info("Finally Clicked on Drop button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					}

					try {
						WebElement ErrorID = Driver.findElement(By.id("errorid"));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
						if (ErrorID.getText().contains("The Air Bill is required")) {
							logger.info("Message:-" + ErrorID.getText());

							// --Add Airbill
							WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
							js.executeScript("arguments[0].scrollIntoView();", AirBill);

							WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
							js.executeScript("arguments[0].click();", AddAirBill);
							wait.until(ExpectedConditions
									.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
							logger.info("AirBill editor is opened");

							/// --Enter AirBill

							WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
							act.moveToElement(Airbill).build().perform();
							Airbill.sendKeys("11111111");
							logger.info("Entered AirBill");

							/// --Enter Description
							WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
							act.moveToElement(Desc).build().perform();
							Desc.sendKeys("SD Service Automation");
							logger.info("Entered Description");

							/// --Enter NoOFPieces
							WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
							act.moveToElement(NoOFP).build().perform();
							NoOFP.sendKeys("2");
							logger.info("Entered NoOFPieces");

							/// --Enter Total Weight
							WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
							act.moveToElement(TWght).build().perform();
							TWght.sendKeys("10");
							logger.info("Entered Total Weight");

							// --Track
							wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
							WebElement Track = Driver.findElement(By.linkText("Track"));
							js.executeScript("arguments[0].click();", Track);
							logger.info("Clicked on Track button");

							// --AIrbill new window
							String WindowHandlebefore = Driver.getWindowHandle();
							for (String windHandle : Driver.getWindowHandles()) {
								Driver.switchTo().window(windHandle);
								logger.info("Switched to Track window");

								Thread.sleep(5000);
								getScreenshot(Driver, "Track" + PUID);

							}
							Driver.close();
							logger.info("Closed Track window");

							Driver.switchTo().window(WindowHandlebefore);
							logger.info("Switched to main window");

							// Scroll up
							js.executeScript("window.scrollBy(0,-250)");

							// --Click on Drop
							try {
								WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Drop).build().perform();
								js.executeScript("arguments[0].click();", Drop);
								logger.info("Clicked on Drop button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception Dropp) {
								try {
									WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
									wait.until(ExpectedConditions.visibilityOf(Drop));
									wait.until(ExpectedConditions.elementToBeClickable(Drop));
									act.moveToElement(Drop).build().perform();
									act.moveToElement(Drop).click().perform();
									logger.info("Clicked on Drop button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception dropee) {
									logger.info("Finally Clicked on Drop button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							}

							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
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
									logger.info("Job is moved to Send Del Alert successfully");
									msg.append("Moved to Stage==Send Del Alert" + "\n\n");

								}
							} catch (Exception dNoData) {
								logger.info("Job is not moved to Send Del Alert successfully");

							}

						}
					} catch (Exception AB) {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
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
								logger.info("Job is moved to Send Del Alert successfully");
								msg.append("Moved to Stage==Send Del Alert" + "\n\n");

							}
						} catch (Exception NoDataD) {
							logger.info("Job is not moved Send Del Alert yet");

						}
					}

				} else if (Orderstage.equalsIgnoreCase("Confirm Del Alert")) {
					Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");

					// --Confirm button
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

					// --Search again
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
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
							logger.info("Job is not moved to ON BOARD stage successfully");

						}
					} catch (Exception e1) {
						logger.info("Job is moved to ON BOARD stage successfully");
						msg.append("Moved to Stage==ON BOARD" + "\n\n");

					}

				} else {
					logger.info("Unknown stage found");
					getScreenshot(Driver, "SD_UnknowStage");

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
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@class=\"pull-left\"]/strong")));
						WebElement ONBOARD = Driver.findElement(By.xpath("//*[@class=\"pull-left\"]/strong"));
						if (ONBOARD.getText().equalsIgnoreCase("ON BOARD")) {
							logger.info("SD Job is on OnBoard stage");
							msg.append("Moved to Stage==ON BOARD" + "\n\n");

						}
					} catch (Exception stage) {
						String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
								.getText();
						logger.info("Current stage of the order is=" + Orderstage);
						logger.info("Issue in Order stage==" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
						msg.append("Issue in Order stage==" + Orderstage + "\n\n");
						getScreenshot(Driver, "SDStageIssue_" + Orderstage);

					}
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

		logger.info("=====SD Order Processing Test End=====");
		msg.append("=====SD Order Processing Test End=====" + "\n\n");

	}
}
