package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Filepaths;

import static io.restassured.RestAssured.given;

public class RestUtils {

    public static String globalProp = Filepaths.globalProp;
    public static PropertiesUtil propertiesUtil = new PropertiesUtil(globalProp);
    public static String baseUri = propertiesUtil.getPropValue("baseUrl");
    public static RequestSpecification reqSpec;
    public static RequestSpecification res;
    public static ResponseSpecification resSpec;
    public static Response response;

    public static RequestSpecification requestSpecification() {
    	try {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			reqSpec = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
		} catch (Exception e) {
    		e.printStackTrace();
		}
        return reqSpec;
    }

    public static RequestSpecification requestSpecificationWithHeader(Map<String, String> headerMap) {
    	try {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			reqSpec = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addHeaders(headerMap)
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
		} catch (Exception e) {
    		e.printStackTrace();
		}
        return reqSpec;
    }

    public static RequestSpecification requestSpecWithBody(Object payloadObj) {
        RequestSpecification reqSpec = null;
        PrintStream log = null;
        try {
            log = new PrintStream(new FileOutputStream("logging.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (baseUri.equalsIgnoreCase(getGlobalValue("baseUrl"))) {
            reqSpec = new RequestSpecBuilder()
                    .setBaseUri(getGlobalValue("baseUrl"))
                    .addHeader("Content-Type", "application/json")
                    .setBody(payloadObj)
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return reqSpec;
    }

    public static RequestSpecification requestSpecWithHeaderAndBody(Map<String, String> reqHeader,
                                                                    Object payloadObj) {
        RequestSpecification reqSpec = null;
        PrintStream log = null;
        try {
            log = new PrintStream(new FileOutputStream("logging.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (baseUri.equalsIgnoreCase(getGlobalValue("baseUrl"))) {
            reqSpec = new RequestSpecBuilder()
                    .setBaseUri(getGlobalValue("baseUrl"))
                    .addHeader("Content-Type", "application/json")
                    .addHeaders(reqHeader)
                    .setBody(payloadObj)
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return reqSpec;
    }

    public static String getGlobalValue(String key) {
        String value = null;
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(Filepaths.globalProp);
            prop.load(fis);
            value = prop.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getPropValue(String key) {
        String value = null;
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(Filepaths.envProp);
            prop.load(fis);
            value = prop.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getJsonPath(Response response, String key) {
        String value = null;
        try {
            String resp = response.asString();
            JsonPath js = new JsonPath(resp);
            value = js.get(key).toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * This method is used to read data from json
     *
     * @param path
     * @return
     * @throws IOException
     * @author Souradeep.Ghosh
     */
    public static String generateStringFromResource(String path) {
    	String resource = null;
    	try {
			resource = new String(Files.readAllBytes(Paths.get(path)));
		} catch (Exception e) {
    		e.printStackTrace();
		}
        return resource;
    }

    public static JsonPath rawToJson(Response r) {
        String asString = r.asString();
        JsonPath j = new JsonPath(asString);
        return j;
    }

    /**
     * This method is used to get the response of the API
     * @param reqSpec
     * @param methodType
     * @param apiRoute
     * @return
     */
    public static Response getResponse(RequestSpecification reqSpec, String methodType,
									   String apiRoute) {
        Response resp = null;
        long responseTimeInSeconds;
        switch (methodType) {
            case "POST":
                resp = reqSpec.when().post(apiRoute);
                System.out.println(apiRoute);
                ValidatableResponse valRes = resp.then();            
                responseTimeInSeconds = resp.getTimeIn(TimeUnit.SECONDS);
                break;
            case "GET":
                resp = reqSpec.when().get(apiRoute);
                System.out.println(apiRoute);
                valRes = resp.then();
                responseTimeInSeconds = resp.getTimeIn(TimeUnit.SECONDS);
                break;
            case "PUT":
                resp = reqSpec.when().put(apiRoute);
                System.out.println(apiRoute);
                valRes = resp.then();
                responseTimeInSeconds = resp.getTimeIn(TimeUnit.SECONDS);
                break;
            case "DELETE":
                resp = reqSpec.when().delete(apiRoute);
                System.out.println(apiRoute);
                valRes = resp.then();
                responseTimeInSeconds = resp.getTimeIn(TimeUnit.SECONDS);
                break;
        }
        return resp;
    }

    public static void pause(int miliSec) {
        try {
            Thread.sleep(miliSec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
