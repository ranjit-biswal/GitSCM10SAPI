package apiCore.Request.Login;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import io.restassured.specification.RequestSpecification;
import utils.RestUtils;

public class Login {

    public static RequestSpecification generateRequestLoginID() {
        return given().spec(RestUtils.requestSpecification()).log().all();
    }

    public static RequestSpecification generateRequestValidateUserLoginID(Map<String, String> headerMap,
                                                                          Object obj) {
        return given().spec(RestUtils.requestSpecificationWithHeader(headerMap)).body(obj).log().all();
    }
}

