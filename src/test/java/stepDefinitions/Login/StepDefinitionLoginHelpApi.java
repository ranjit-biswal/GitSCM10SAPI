package stepDefinitions.Login;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import apiCore.Request.Login.LoginHelpPayload;
import apiCore.Request.Login.LoginPayload;
import apiCore.auth.LoginUtils;
import apiCore.auth.TestRequestBuilder;
import apiCore.auth.TokenUtil;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Routes;
import utils.Base64EncryptionUtil;
import utils.Context;
import utils.DataBaseUtil;
import utils.ScenarioContext;
import utils.SqlQuery;
import utils.TestContext;
import utils.RestUtils;

public class StepDefinitionLoginHelpApi extends RestUtils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	TestContext testContext;
	ScenarioContext scenarioContext;
	TokenUtil tokenUtil = new TokenUtil();

	public StepDefinitionLoginHelpApi(TestContext context, ScenarioContext scenarioContext) {
		testContext = context;
		this.scenarioContext = scenarioContext;
	}
	
	@Given("^User enters Valid emailid and AccountNumber and get the get the prelogin Token$")
    public void user_enters_valid_emailid_and_accountnumber_and_get_the_get_the_prelogin_token() throws Throwable {
    		String authToken = tokenUtil.getAuthorizationHeader();
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", authToken);
		headerMap.put("SourceType", "0");
		TestRequestBuilder testDataBuild = new TestRequestBuilder();
		LoginHelpPayload loginHelpPayload = testDataBuild.getLoginHelpPayLoad("nikhil.jain@mailinator.com", 0,
				RestUtils.getGlobalValue("AccountNumber"), 2);
		res = given().spec(requestSpecWithHeaderAndBody(headerMap, loginHelpPayload)).log().all();
	}

	@When("^user calls LoginHelp \"([^\"]*)\" API with \"([^\"]*)\" http request$")
	public void user_calls_LoginHelp_API_with_http_request(String resource, String methodType) throws Throwable {
		Routes routes = Routes.valueOf(resource);
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		response = RestUtils.getResponse(res, "POST", routes.getResource());
		scenarioContext.setResponse(response);
	}
}
