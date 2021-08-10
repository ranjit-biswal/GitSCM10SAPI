package apiCore.auth;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import apiCore.Request.Login.Login;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.Routes;
import utils.Base64EncryptionUtil;
import utils.DataBaseUtil;
import utils.SqlQuery;
import utils.RestUtils;
import apiCore.Request.Login.LoginPayload;

public class LoginUtils extends RestUtils {
	public static RequestSpecification reqSpec;
	static Response response;

	public static HashMap<String, String> getUserLoginKeyToken() {
		HashMap<String, String> userLoginResponseData = new HashMap<>();
		Routes routes = Routes.valueOf("GetLoginID");
		try {
			reqSpec = Login.generateRequestLoginID();
			response = RestUtils.getResponse(reqSpec, "POST", routes.getResource());
			Assert.assertEquals(response.getStatusCode(), 200);
			userLoginResponseData.put("tokenId", RestUtils.getJsonPath(response,
					"result.Data.tokenId"));
			userLoginResponseData.put("key", RestUtils.getJsonPath(response,
					"result.Data.key"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userLoginResponseData;
	}

	public static HashMap<String, String> getUserIdSessionTokenValidateUserLogin(Map<String, String> headerMap) {
		HashMap<String, String> loginValidateResponseData = new HashMap<>();
		Routes routes = Routes.valueOf("ValidateUserLogin");
		TestRequestBuilder testDataBuild = new TestRequestBuilder();
		try {
		/*	ResultSet resultSet = DataBaseUtil.getResultSet(SqlQuery
					.getDefaultAccount(userName));
			resultSet.next();
			String defaultAccount = resultSet.getString("UtilityAccountNumber");*/
			LoginPayload validateUserLogin = testDataBuild.getLoginPayLoad(RestUtils.getGlobalValue("UserName"), RestUtils.getGlobalValue("Password"),
					RestUtils.getGlobalValue("AccountNumber"));
			reqSpec = Login.generateRequestValidateUserLoginID(headerMap, validateUserLogin);
			response = RestUtils.getResponse(reqSpec, "POST", routes.getResource());
			Assert.assertEquals(response.getStatusCode(), 200);
			loginValidateResponseData.put("UserID", RestUtils.getJsonPath(response,
					"result.Data.Table[0].UserID"));
			loginValidateResponseData.put("SessionToken",
					RestUtils.getJsonPath(response, "result.Data.Table[0].SessionToken"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginValidateResponseData;
	}
	
	/*public static HashMap<String, String> getUserIdSessionTokenValidateUserLogin(Map<String, String> headerMap,
			String userName, String passWord, String AccountNumber) {
		HashMap<String, String> loginValidateResponseData = new HashMap<>();
		Routes routes = Routes.valueOf("ValidateUserLogin");
		TestRequestBuilder testDataBuild = new TestRequestBuilder();
		try {
			ResultSet resultSet = DataBaseUtil.getResultSet(SqlQuery
					.getDefaultAccount(userName));
			resultSet.next();
			String defaultAccount = resultSet.getString("UtilityAccountNumber");
			LoginPayload validateUserLogin = testDataBuild.getLoginPayLoad(userName, passWord,
					AccountNumber);
			reqSpec = Login.generateRequestValidateUserLoginID(headerMap, validateUserLogin);
			response = RestUtils.getResponse(reqSpec, "POST", routes.getResource());
			Assert.assertEquals(response.getStatusCode(), 200);
			loginValidateResponseData.put("UserID", RestUtils.getJsonPath(response,
					"result.Data.Table[0].UserID"));
			loginValidateResponseData.put("SessionToken",
					RestUtils.getJsonPath(response, "result.Data.Table[0].SessionToken"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginValidateResponseData;
	}*/
}
