package stepDefinitions.Login;

import static org.testng.Assert.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import apiCore.auth.LoginUtils;
import apiCore.auth.TestRequestBuilder;
import apiCore.auth.TokenUtil;
import io.restassured.response.Response;
import utils.Base64EncryptionUtil;
import utils.Context;
import utils.ScenarioContext;
import utils.TestContext;
import utils.RestUtils;

public class StepDefinitionLoginApi extends RestUtils {
	Response response;
	TestRequestBuilder data = new TestRequestBuilder();
	TestContext testContext;
	ScenarioContext scenarioContext;
	TokenUtil tokenUtil = new TokenUtil();

	public StepDefinitionLoginApi(TestContext context, ScenarioContext scenarioContext) {
		this.testContext = context;
		this.scenarioContext = scenarioContext;
	}
	
	
	@Given("^User enters Valid LoginID and PassWord and AccountNumber and get the get the Login Token$")
    public void user_enters_valid_loginid_and_password_and_accountnumber_and_get_the_get_the_login_token() throws Throwable {
		String authToken = tokenUtil.getAuthorizationHeader();
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", authToken);
		headerMap.put("SourceType", "0");
		HashMap<String, String> loginSessionTokenUserId = LoginUtils
				.getUserIdSessionTokenValidateUserLogin(headerMap);
		String userId = loginSessionTokenUserId.get("UserID");
		String sessionToken = loginSessionTokenUserId.get("SessionToken");
		String base64Encrypted = Base64EncryptionUtil.getBase64EncryptedString(userId + ":" + sessionToken);
		String loginToken = "Basic" + " " + base64Encrypted;
		testContext.scenarioContext.setContext(Context.Final_Login_Token, loginToken);
    }


	/*@Given("^User enters Valid LoginID \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and get the get the Login Token$")
	public void user_enters_Valid_LoginID_and_and_and_get_the_get_the_Login_Token(String userName, String password,
			String accountNumber) {
		String authToken = tokenUtil.getAuthorizationHeader();
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", authToken);
		headerMap.put("SourceType", "0");
		HashMap<String, String> loginSessionTokenUserId = LoginUtils
				.getUserIdSessionTokenValidateUserLogin(headerMap, userName, password, accountNumber);
		String userId = loginSessionTokenUserId.get("UserID");
		String sessionToken = loginSessionTokenUserId.get("SessionToken");
		String base64Encrypted = Base64EncryptionUtil.getBase64EncryptedString(userId + ":" + sessionToken);
		String loginToken = "Basic" + " " + base64Encrypted;
		testContext.scenarioContext.setContext(Context.Final_Login_Token, loginToken);
	}*/

	
}
