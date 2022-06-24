package netAgent_OperationsTab;

import java.awt.AWTException;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;
import netAgent_OrderProcessing.LOC_OrderProcess;
import netAgent_OrderProcessing.SD_OrderProcess;

public class OrderProcessing extends BaseInit {

	@Test
	public void orderProcessing()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException, AWTException {

		// --LOC
		LOC_OrderProcess LOC = new LOC_OrderProcess();
		LOC.orderProcessLOCJOB();

		// --SD
		SD_OrderProcess SD = new SD_OrderProcess();
		SD.orderProcessSDJOB();
	}

}
