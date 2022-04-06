package netAgent_BasePackage;

import org.testng.annotations.Test;

import netAgent_LogOutDiv.AgentElevatesRiskTraining;
import netAgent_LogOutDiv.AgentTSATraining;
import netAgent_LogOutDiv.ContactUS;
import netAgent_OperationsTab.Courier;
import netAgent_OperationsTab.CycleCount;
import netAgent_OperationsTab.OrderSearch;
import netAgent_OperationsTab.Replenish;
import netAgent_OperationsTab.TaskLog;

public class NetAgentSmoke extends BaseInit {

	@Test
	public void netAgentSmoke() throws Exception {

		// --Contact US
		ContactUS.ContactUs();

		// --AgentTSA
		AgentTSATraining.AgentTSA();

		// --Contact US
		AgentElevatesRiskTraining.AgentRisk();

		// --Contact US
		TaskLog.taskLog();

		// --Contact US
		OrderSearch.orderSearch();

		// --Contact US
		Replenish.replenish();

		// --Contact US
		CycleCount.cycleCount();

		// --Contact US
		Courier.courier();

	}

}
