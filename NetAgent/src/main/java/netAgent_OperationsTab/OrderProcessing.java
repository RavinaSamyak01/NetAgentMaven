package netAgent_OperationsTab;

import java.awt.AWTException;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;
import netAgent_OrderProcessing.D3P_OrderProcess;
import netAgent_OrderProcessing.H3P_OrderProcess;
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

		// --H3P
		H3P_OrderProcess H3P = new H3P_OrderProcess();
		H3P.orderProcessH3PJOB();

		// --D3P
		D3P_OrderProcess D3P = new D3P_OrderProcess();
		D3P.orderProcessD3PJOB();
	}

}
