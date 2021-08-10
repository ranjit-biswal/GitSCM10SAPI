#@Ignore
Feature: Validating User is able to logout from the application

@Logout @Sanity @Regression
Scenario Outline: Verify if User gets logout from the application
	Given User enters Valid LoginID and PassWord and AccountNumber and get the get the Login Token
	When user calls "GetLogOut" API with "POST" http request
	Then user validates API call got success with status code "<StatusCode>"
	And "Message" in response body is "Successful!!"

	
	  Examples: 
      | StatusCode |
      |     200 	 |
      
      
      
