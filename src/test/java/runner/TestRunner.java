package runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import utils.DataBaseUtil;
import java.sql.Connection;

@CucumberOptions(
		features = "src/test/resources/features",
		glue = {"stepDefinitions"},
		/*
		 * tags = { "~@Ignore", "~@Billing, @Login, @ConnectMe, ~@Outage, ~@Logout" },
		 */
			tags = {  "@Login, @ConnectMe, @Logout, @LoginHelp, @Outage" },
			
		plugin = {
				"json:target/cucumber-reports/CucumberTestReport.json",
				"pretty",
				"com.epam.reportportal.cucumber.ScenarioReporter",
				"com.epam.reportportal.cucumber.StepReporter"
		},
		monochrome = false,
		dryRun = false
)
public class TestRunner {
	private TestNGCucumberRunner testNGCucumberRunner;
	Connection connection;
	Connection paymentConn;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		connection = DataBaseUtil.initSqlConnection();
		paymentConn = DataBaseUtil.initSqlPaymentConn();
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		testNGCucumberRunner.finish();
		try {
			connection.close();
			paymentConn.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}