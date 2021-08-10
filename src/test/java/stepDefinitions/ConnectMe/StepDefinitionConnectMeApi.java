package stepDefinitions.ConnectMe;

import static io.restassured.RestAssured.given;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;

import apiCore.Request.ConnectMe.ConnectMePayLoad;
import apiCore.auth.TestRequestBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Routes;
import utils.Context;
import utils.ScenarioContext;
import utils.TestContext;
import utils.RestUtils;

public class StepDefinitionConnectMeApi extends RestUtils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	TestContext testContext;
	ScenarioContext scenarioContext;

	public StepDefinitionConnectMeApi(TestContext context, ScenarioContext scenarioContext) {
		testContext = context;
		this.scenarioContext = scenarioContext;
	}

	@When("^User enters details for Connect me \"([^\"]*)\" and \"([^\"]*)\" the request for API$")
	public void user_enters_details_for_Connect_me_and_the_request_for_API(String resource, String methodType) {
		String loginToken = (String) testContext.scenarioContext.getContext(Context.Final_Login_Token);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", loginToken);
		headerMap.put("SourceType", "1");
		res = given().spec(requestSpecWithHeaderAndBody(headerMap, ConnectMePayLoad.createConnectMeProgramPayload()));
		Routes routes = Routes.valueOf(resource);
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		response=RestUtils.getResponse(res, "POST", routes.getResource());
		scenarioContext.setResponse(response);
	}
}
