#@Ignore
Feature: Validating Create Connect Me program type from 10S SCM API

  @ConnectMe @Sanity @Regression
  Scenario Outline: User validate Create connect Me after successfully getting Login token
    Given User enters Valid LoginID and PassWord and AccountNumber and get the get the Login Token
    When User enters details for Connect me "SetConnectMeRequest" and "POST" the request for API
    Then user validates API call got success with status code "<StatusCode>"

    Examples: 
      | StatusCode |
      |     200    |
