package stepDefinitions.Registration;

import apiCore.auth.TokenUtil;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import utils.ScenarioContext;
import utils.TestContext;

public class StepDefsRegistration {
    TokenUtil tokenUtil = new TokenUtil();
    TestContext testContext;
    ScenarioContext scenarioContext;

    public StepDefsRegistration(TestContext context, ScenarioContext scenarioContext) {
        testContext = context;
        this.scenarioContext = scenarioContext;
    }

    @Given("^User register with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userRegisterWithDetailsAndAndAndAndAndAndAndAndAnd(String utilityAccNo, String zipCode,
                                                                   String emailId, String firstName,
                                                                   String lastName, String userName,
                                                                   String password, String primaryPhone,
                                                                   String streetAdd, String preferences) {

    }

    @When("^User calls \"([^\"]*)\" with \"([^\"]*)\" http request$")
    public void userCallsWithHttpRequest(String routes, String reqType) {

    }

    @And("^User gets registered in user table with details \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userGetsRegisteredInUserTableWithDetailsAndAndAndAndAndAnd(String userName, String emailAdd,
                                                                           String primaryPhone, String firstName,
                                                                           String lastName, String status,
                                                                           String preferences) {

    }

    @And("^Able to login with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void ableToLoginWithAnd(String userName, String pass) {

    }
}
