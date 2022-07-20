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

public class LOC_OrderProcess extends BaseInit {

	@Test
	public void orderProcessLOCJOB() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=====LOC Order Processing Test Start=====");
		msg.append("=====LOC Order Processing Test Start=====" + "\n\n");

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

		getScreenshot(Driver, "TaskLog_OperationsLOC");

		String ServiceID = getData("OrderProcessing", 1, 0);
		logger.info("ServiceID is==" + ServiceID);
		msg.append("ServiceID==" + ServiceID + "\n");
		String PUID = getData("OrderProcessing", 1, 1);
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
			WebElement PickuPBox = Driver.findElement(By.xpath("//*[contains(@class,'pickupbx')]"));
			if (PickuPBox.isDisplayed()) {
				logger.info("Searched Job is displayed in edit mode");
				getScreenshot(Driver, "LOCOrderEditor_" + PUID);
			}
			// --Memo
			OrderProcess OP = new OrderProcess();
			OP.memo(PUID);

			// -Notification
			OP.notification(PUID);

			// Upload
			OP.upload(PUID);

			// Map
			OP.map(PUID);

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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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

					WebElement Deliver = Driver.findElement(By.id("lblAddress"));
					js.executeScript("arguments[0].scrollIntoView();", Deliver);
					Thread.sleep(2000);

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
					WebElement Save = Driver.findElement(By.id("lnksave"));
					act.moveToElement(Save).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Save));
					js.executeScript("arguments[0].click();", Save);
					logger.info("Clicked on PICKUP button");
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
							logger.info("Job is not moved to DELIVER stage successfully");

						}
					} catch (Exception DNoData) {
						logger.info("Job is moved to DELIVER stage successfully");
						ServiceID = getData("OrderProcessing", 1, 0);
						logger.info("Service ID is==" + ServiceID);

						// --Deliver Stage
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Stage==" + Orderstage + "\n");

						Deliver = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", Deliver);
						Thread.sleep(2000);

						// --Deliver Time

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

						// --Signature
						WebElement Sign = Driver.findElement(By.id("txtSignature"));
						act.moveToElement(Sign).build().perform();
						Sign.clear();
						Sign.sendKeys("Ravina Prajapati");

						logger.info("Entered signature");

						// --Click on Deliver
						WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Del).build().perform();
						js.executeScript("arguments[0].click();", Del);
						logger.info("Clicked on Deliver button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
							WebElement DOK = Driver.findElement(By.id("iddataok"));
							js.executeScript("arguments[0].click();", DOK);
							logger.info("Click on OK of Dialogue box");
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
								logger.info("Job is Delivered successfully");
								msg.append("Moved to Stage==Delivered" + "\n\n");

							}
						} catch (Exception NoData) {
							logger.info("Job is not delivered yet");

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

				WebElement Deliver = Driver.findElement(By.id("lblAddress"));
				js.executeScript("arguments[0].scrollIntoView();", Deliver);
				Thread.sleep(2000);

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
				WebElement Save = Driver.findElement(By.id("lnksave"));
				act.moveToElement(Save).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(Save));
				js.executeScript("arguments[0].click();", Save);
				logger.info("Clicked on PICKUP button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not moved to DELIVER stage successfully");

					}
				} catch (Exception DNoData) {
					logger.info("Job is moved to DELIVER stage successfully");
					ServiceID = getData("OrderProcessing", 1, 0);
					logger.info("Service ID is==" + ServiceID);

					// --Deliver Stage
					Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Stage==" + Orderstage + "\n");

					Deliver = Driver.findElement(By.id("lblAddress"));
					js.executeScript("arguments[0].scrollIntoView();", Deliver);
					Thread.sleep(2000);

					// --Deliver Time

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

					// --Signature
					WebElement Sign = Driver.findElement(By.id("txtSignature"));
					act.moveToElement(Sign).build().perform();
					Sign.clear();
					Sign.sendKeys("Ravina Prajapati");

					logger.info("Entered signature");

					// --Click on Deliver
					WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
					act.moveToElement(Del).build().perform();
					js.executeScript("arguments[0].click();", Del);
					logger.info("Clicked on Deliver button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
						WebElement DOK = Driver.findElement(By.id("iddataok"));
						js.executeScript("arguments[0].click();", DOK);
						logger.info("Click on OK of Dialogue box");
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
							logger.info("Job is Delivered successfully");
							msg.append("Moved to Stage==Delivered" + "\n\n");

						}
					} catch (Exception NoData) {
						logger.info("Job is not delivered yet");

					}

				}

			} else if (Orderstage.equalsIgnoreCase("Deliver")) {
				logger.info("Job is moved to DELIVER stage successfully");
				ServiceID = getData("OrderProcessing", 1, 0);
				logger.info("Service ID is==" + ServiceID);

				// --Deliver Stage
				Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
				logger.info("Current stage of the order is=" + Orderstage);
				msg.append("Stage==" + Orderstage + "\n");

				WebElement Deliver = Driver.findElement(By.id("lblAddress"));
				js.executeScript("arguments[0].scrollIntoView();", Deliver);
				Thread.sleep(2000);

				// --Deliver Time

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

				// --Signature
				WebElement Sign = Driver.findElement(By.id("txtSignature"));
				act.moveToElement(Sign).build().perform();
				Sign.clear();
				Sign.sendKeys("Ravina Prajapati");

				logger.info("Entered signature");

				// --Click on Deliver
				WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
				act.moveToElement(Del).build().perform();
				js.executeScript("arguments[0].click();", Del);
				logger.info("Clicked on Deliver button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
					WebElement DOK = Driver.findElement(By.id("iddataok"));
					js.executeScript("arguments[0].click();", DOK);
					logger.info("Click on OK of Dialogue box");
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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is Delivered successfully");
						msg.append("Moved to Stage==Delivered" + "\n\n");

					}
				} catch (Exception NoData) {
					logger.info("Job is not delivered yet");

				}

			} else {
				logger.info("Unknown stage found");
				getScreenshot(Driver, "LOC_UnknowStage");

			}

		} catch (Exception NoData1) {
			logger.error(NoData1);

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not exist with the search parameters");
					msg.append("Job is not exist with the search parameters" + "\n\n");

				}
			} catch (Exception OnBoard) {
				logger.error(OnBoard);

				String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
				logger.info("Current stage of the order is=" + Orderstage);
				msg.append("Current stage of the order is=" + Orderstage + "\n");
				logger.info("Issue in Order stage==" + Orderstage);
				msg.append("Issue in Order stage==" + Orderstage + "\n\n");
				getScreenshot(Driver, "LOCStageIssue_" + Orderstage);

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

		logger.info("=====LOC Order Processing Test End=====");
		msg.append("=====LOC Order Processing Test End=====" + "\n\n");

	}
}
