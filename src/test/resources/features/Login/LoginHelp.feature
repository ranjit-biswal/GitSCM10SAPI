#@Ignore
Feature: Validating the LoginHelp API Functionality works 

@LoginHelp @ForgotUsername @Sanity @Regression
Scenario Outline: Verify if User recieves the Username from the LoginHelp API 

	Given User enters Valid emailid and AccountNumber and get the get the prelogin Token
	When user calls LoginHelp "GetLoginHelp" API with "POST" http request 
	Then user validates API call got success with status code "<StatusCode>" 
	And "Message" in response body is "If an account exists for nikhil.jain@mailinator.com, you will get an email having your username. If it doesnot arrive, be sure to check your spam folder." 
	
	
	Examples: 
		| StatusCode |
		|     200 	 |
