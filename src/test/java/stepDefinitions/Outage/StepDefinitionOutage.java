package stepDefinitions.Outage;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.Assert;
import resources.Filepaths;
import apiCore.Request.Outage.OutageAPI;
import apiCore.auth.TokenUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.Routes;
import utils.*;

public class StepDefinitionOutage extends RestUtils {
    OutageAPI outageAPI = new OutageAPI();
    String addOutageJsonFilepath = Filepaths.addOutageJsonFilepath;
    String outPayload = addOutageJsonFilepath + "outagePayLoad.json";
    RequestSpecification reqSpec;
    Response response;
    TokenUtil tokenUtil = new TokenUtil();
    TestContext testContext;
    ScenarioContext scenarioContext;
    String apiRoute = null, actOutageInfo = null, actOutageCause = null,
            actOutageMsg = null, actStartTime = null, actEndTime = null,
            payloadBody = null, outageId = "";

    public StepDefinitionOutage(TestContext context, ScenarioContext scenarioContext) {
        testContext = context;
        this.scenarioContext = scenarioContext;
    }

    @Given("^User create outage with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userCreateOutageWithDetailsAndAndAndAndAndAndAnd(String outageInfo, String outageCause,
                                                                 String outageMsg, String startTime,
                                                                 String endTime, String isResolved,
                                                                 String serviceType, int isPlanned) {
        // Write outage payload to create the outage
        outageAPI.writeOutagePayloadJson(outageInfo, outageCause, outageMsg,
                startTime, endTime, isResolved, serviceType, isPlanned);
        try {
            payloadBody = generateStringFromResource(outPayload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^User calls  \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void userCallsWithHttpRequest(String routes, String reqType) {
        // Write code here that turns the phrase above into concrete actions
        Routes outageRoutes = Routes.valueOf(routes);
        String authHeader = tokenUtil.getAuthorizationHeader();
        Map<String, String> reqHeader = new HashMap<>();
        reqHeader.put("Authorization", authHeader);
        reqHeader.put("SourceType", "0");
        apiRoute = outageRoutes.getResource();
        reqSpec = outageAPI.generateAddOutageRequest(
                reqHeader,
                payloadBody
        );
        response = getResponse(reqSpec, reqType, apiRoute);
        scenarioContext.setResponse(response);
    }

    @Then("^API call got success for creating outage with status code \"([^\"]*)\" and \"([^\"]*)\"$")
    public void apiCallGotSuccessForCreatingOutageWithStatusCodeAnd(String statusCode, String expMessage) {
        // Verify the response status code
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
        // Fetching the JSON response
        JsonPath rawToJson = RestUtils.rawToJson(response);
        String actMessage = rawToJson.getString("result.Message");
        Assert.assertEquals(actMessage, expMessage,
                "Response message not matched.");
    }

    @And("^Outage info get saved with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" in database$")
    public void outageInfoGetSavedWithDetailsAndAndAndAndInDatabase(String outageInfo, String outageCause,
                                                                    String outageMsg, String startTime,
                                                                    String endTime) {
        String query = SqlQuery.getOutageDetails(outageMsg);
        ResultSet resultSet = null;
        try {
            pause(4000);
            resultSet = DataBaseUtil.getResultSet(query);
            resultSet.next();
            actOutageInfo = resultSet.getString("outagereportinfo");
            actOutageCause = resultSet.getString("cause");
            actOutageMsg = resultSet.getString("outagemessage");
            actStartTime = resultSet.getString("starttime");
            actEndTime = resultSet.getString("endtime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actOutageInfo, outageInfo,
                "Outage info not matched.");
        Assert.assertEquals(actOutageCause, outageCause,
                "Outage cause not matched.");
        Assert.assertEquals(actOutageMsg, outageMsg,
                "Outage message not matched.");
        actStartTime = actStartTime.split(" ")[0];
        startTime = startTime.split("T")[0];
        Assert.assertEquals(actStartTime, startTime,
                "Start time not matched.");
        actEndTime = actEndTime.split(" ")[0];
        endTime = endTime.split("T")[0];
        Assert.assertEquals(actEndTime, endTime,
                "End time not matched.");
    }

    @Given("^User update outage with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userUpdateOutageWithDetailsAndAndAndAndAndAndAnd(String outageInfo, String outageCause,
                                                                 String outageMsg, String startTime,
                                                                 String endTime, String isResolved,
                                                                 String serviceType, int isPlanned) {
        String query = SqlQuery.getOutageDetailsToUpdate(serviceType);
        ResultSet resultSet = null;
        try {
            resultSet = DataBaseUtil.getResultSet(query);
            resultSet.next();
            outageId = resultSet.getString("outageid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Write outage payload to create the outage
        outageAPI.writeOutagePayloadJson(outageId, outageInfo, outageCause, outageMsg,
                startTime, endTime, isResolved, serviceType, isPlanned);
        try {
            payloadBody = generateStringFromResource(outPayload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^User calls \"([^\"]*)\" with the existing outage id and \"([^\"]*)\" http request$")
    public void userCallsWithTheExistingOutageIdAndHttpRequest(String routes, String reqType) {
        // Write code here that turns the phrase above into concrete actions
        Routes outageRoutes = Routes.valueOf(routes);
        String authHeader = tokenUtil.getAuthorizationHeader();
        Map<String, String> reqHeader = new HashMap<>();
        reqHeader.put("Authorization", authHeader);
        reqHeader.put("SourceType", "0");
        apiRoute = outageRoutes.getResource();
        reqSpec = outageAPI.generateUpdateOutageRequest(
                reqHeader,
                payloadBody
        );
        response = getResponse(reqSpec, reqType, apiRoute);
        scenarioContext.setResponse(response);
    }

    @Then("^API call got success for updating outage with status code \"([^\"]*)\" and \"([^\"]*)\"$")
    public void apiCallGotSuccessForUpdatingOutageWithStatusCodeAnd(String statusCode, String expMessage) {
        // Verify the response status code
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
        // Fetching the JSON response
        JsonPath rawToJson = RestUtils.rawToJson(response);
        String actMessage = rawToJson.getString("result.Message");
        Assert.assertEquals(actMessage, expMessage,
                "Response message not matched.");
    }

    @And("^Outage info get updated with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" in database\\.$")
    public void outageInfoGetUpdatedWithDetailsAndAndAndAndInDatabase(String outageInfo, String outageCause,
                                                                      String outageMsg, String startTime,
                                                                      String endTime) {
        String query = SqlQuery.getOutageDetailsWithOutageId(outageId);
        ResultSet resultSet = null;
        try {
            resultSet = DataBaseUtil.getResultSet(query);
            resultSet.next();
            actOutageInfo = resultSet.getString("outagereportinfo");
            actOutageCause = resultSet.getString("cause");
            actOutageMsg = resultSet.getString("outagemessage");
            actStartTime = resultSet.getString("starttime");
            actEndTime = resultSet.getString("endtime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actOutageInfo, outageInfo, "Outage info not matched after update.");
        Assert.assertEquals(actOutageCause, outageCause, "Outage cause not matched after update.");
        Assert.assertEquals(actOutageMsg, outageMsg, "Outage message not matched after update.");
        actStartTime = actStartTime.split(" ")[0];
        startTime = startTime.split("T")[0];
        Assert.assertEquals(actStartTime, startTime, "Start time not matched after update.");
        actEndTime = actEndTime.split(" ")[0];
        endTime = endTime.split("T")[0];
        Assert.assertEquals(actEndTime, endTime, "End time not matched after update.");
    }
}