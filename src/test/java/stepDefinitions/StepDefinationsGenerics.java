package stepDefinitions;

import static org.testng.Assert.assertEquals;

import cucumber.api.java.en.Then;
import utils.RestUtils;
import utils.ScenarioContext;
import utils.TestContext;

public class StepDefinationsGenerics {
	TestContext testContext;
	ScenarioContext scenarioContext;
	
	public StepDefinationsGenerics(TestContext context, ScenarioContext scenarioContext) {
		testContext = context;
		this.scenarioContext = scenarioContext;
	}

	@Then("^user validates API call got success with status code \"([^\"]*)\"$")
	public void validateResponseStatusCode(String statusCode) {
		assertEquals(Integer.toString(scenarioContext.getResponse().getStatusCode()), 
				statusCode);
	}

	@Then("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void checkMessageResponseBody(String msg, String msgVal) throws Throwable {
		assertEquals(RestUtils.getJsonPath(scenarioContext.getResponse(), "result.Message"), 
				msgVal);
	}
}
