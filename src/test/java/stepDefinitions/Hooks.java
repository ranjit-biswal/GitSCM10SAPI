package stepDefinitions;

import java.sql.ResultSet;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import resources.Filepaths;
import stepDefinitions.Billing.StepDefinitionCreateCardProfile;
import utils.JsonUtil;

public class Hooks {
	static JsonUtil jsUtil_paymentProfile_CC = new JsonUtil(Filepaths.testDataScpJsonFilepath,
			"createProfileCC.json");
	ResultSet resultSet;

	@Before("@DeleteCreditCard")
	public static void beforeScenario_DeleteCard() throws Exception {
		StepDefinitionCreateCardProfile ccp = new StepDefinitionCreateCardProfile();
		ccp.deleteCardProfile(jsUtil_paymentProfile_CC.getStringJsonValue("UserID"));
	}
}
