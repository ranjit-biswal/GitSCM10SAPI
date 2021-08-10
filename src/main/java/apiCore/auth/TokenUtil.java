package apiCore.auth;

import static org.testng.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import resources.Routes;
import utils.Base64EncryptionUtil;
import utils.RestUtils;

public class TokenUtil extends RestUtils {
	public String getTokenEndPoint = "GetLoginID";

	public String getTokenKey() {
		String tokenKey = null;
		Routes routes = Routes.valueOf(getTokenEndPoint);
		String getTokenKeyIdURI = baseUri + routes.getResource();
		Response res = RestAssured.given().when().post(getTokenKeyIdURI);
		assertEquals(res.getStatusCode(), 200);
		assertEquals(RestUtils.getJsonPath(res, "result.Message"),
				"Successful!!");
		String token = getJsonPath(res, "result.Data.tokenId").trim();
		String key = getJsonPath(res, "result.Data.key").trim();
		tokenKey = key + ":" + token;
		return tokenKey;
	}

	public String getEncryptedTokenKey() {
		String tokenKey = getTokenKey();
		String encryptedTokenKey = Base64EncryptionUtil.getBase64EncryptedString(tokenKey);
		return encryptedTokenKey;
	}

	public String getAuthorizationHeader() {
		String encryptedTokenKey = getEncryptedTokenKey();
		String authorizationHeader = "Basic " + encryptedTokenKey;
		return authorizationHeader;
	}
}
