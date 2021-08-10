package stepDefinitions.Login;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import apiCore.Request.ConnectMe.ConnectMePayLoad;
import apiCore.auth.TestRequestBuilder;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

public class StepDefinitionLogoutApi extends RestUtils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	TestContext testContext;
	ScenarioContext scenarioContext;

	public StepDefinitionLogoutApi(TestContext context, ScenarioContext scenarioContext) {
		testContext = context;
		this.scenarioContext = scenarioContext;
	}

	@When("^user calls \"([^\"]*)\" API with \"([^\"]*)\" http request$")
	public void user_calls_API_with_http_request(String resource, String methodType) throws Throwable {
		String loginToken = (String) testContext.scenarioContext.getContext(Context.Final_Login_Token);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", loginToken);
		headerMap.put("SourceType", "1");
		res = given().spec(requestSpecificationWithHeader(headerMap));
		Routes routes = Routes.valueOf(resource);
		resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();	
		response = RestUtils.getResponse(res, "POST", routes.getResource());
		scenarioContext.setResponse(response);
	}
}
