package stepDefinitions.Billing;

import static io.restassured.RestAssured.given;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

import resources.Filepaths;
import apiCore.Request.Payments.OneTimePaymentAPI;
import apiCore.auth.TestRequestBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import resources.Routes;
import utils.JsonUtil;
import utils.RestUtils;

public class StepDefinitionBank extends RestUtils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	String transID = null;
	String fileBank = "OTPBank.json";
	JsonUtil jsUtil_Bank = new JsonUtil(Filepaths.testDataScpJsonFilepath, fileBank);

	@Given("^Add OneTimePayment Payload with  \"(.*)\"$")
	public void add_Place_Payload_withBank(String amount) {
		res = given().spec(requestSpecification()).body(data
				.oneTimePaymentBankPayLoad(amount));
	}

	@When("^user calls \"(.*)\" with \"(.*)\"  http request$")
	public void user_calls_with_http_requestBank(String resource, String method) {
		Routes resourceAPI = Routes.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("^the API call got success with status code  \"(.*)\"$")
	public void the_API_call_got_success_with_status_codeBank(String int1) {
		assertEquals(Integer.toString(response.getStatusCode()), int1);
	}

	@Then("^\"([^\"]*)\" in response body is  \"([^\"]*)\"$")
	public void in_response_body_is(String keyValue, String expectedValue) {
		// Write code here that turns the phrase above into concrete actions
		Assert.assertEquals(expectedValue, getJsonPath(response, keyValue));
	}

	@Then("^User validate  \"([^\"]*)\" for \"([^\"]*)\" with DB details \"([^\"]*)\"$")
	public void user_validate_for_with_DB_details(String keyValue, String statusCode,
												  String amt) {
		if (statusCode.equals("200")) {
			transID = getJsonPath(response, keyValue);
			Assert.assertEquals(amt, OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get("Amount"));
		} else if (statusCode.equals("400")) {
			transID = getJsonPath(response, keyValue);
			Assert.assertEquals(transID, null);
		}
	}

	@Then("^User  validate the \"([^\"]*)\" from Response with the DataBase paymentRecons Table with column \"([^\"]*)\"$")
	public void user_validate_the_from_Response_with_the_DataBase_paymentRecons_Table_with_column(String keyValue,
			String dataBaseNameCol) {
		Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get(dataBaseNameCol),
				getJsonPath(response, keyValue));
	}

	@Then("^User  validate the customerName with the DataBase paymentRecons Table with column \"([^\"]*)\"$")
	public void user_validate_the_customerName_with_the_DataBase_paymentRecons_Table_with_column(String dataBaseNameCol) {
		Assert.assertEquals(OneTimePaymentAPI.getPaymentFrom_ChaseDB(transID).get(dataBaseNameCol),
				jsUtil_Bank.getStringJsonValue("customerName"));
	}

}
