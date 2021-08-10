Feature: Register user and validate that if entry is created in user table and login.

  # In this feature we are testing registration with help of API and also
  # validating the entry of registered user in user table and also log in
  # with the registered user.
  #@Registration @Sanity @Regression
  Scenario Outline: User should be a able to register with valid residential account with opting all preferences.
    Given User register with details "<UtilityAccountNumber>" and "<ZipCode>" and "<EmailAddress>" and "<FirstName>" and "<LastName>" and "<UserName>" and "<Password>" and "<PrimaryPhone>" and "<StreetAddress>" and "<Preferences>"
    When User calls "RegisterUser" with "POST" http request
    Then user validates API call got success with status code "<StatusCode>"
    And "Message" in response body is "Successful!!"
    And User gets registered in user table with details "<UserName>" and "<EmailAddress>" and "<PrimaryPhone>" and "<FirstName" and "<LastName>" and "<Status>" and "<Preferences>"
    And Able to login with "<UserName>" and "<Password>"

    Examples:
      | UtilityAccountNumber | ZipCode  | EmailAddress | FirstName     | LastName    | UserName     | Password     | PrimaryPhone | StreetAddress  | Preferences | Status | StatusCode |
      | utility_acc_num      | zip_code | api_test     | Tony          | Stark       | tony_st@rk-  | Demouser@1   | 9999999999   | street_address | both_pref   |  0     |  200       |


  #@Registration @Sanity @Regression
  Scenario Outline: User should be a able to register with valid commercial account with opting only notification preference.
    Given User register with details "<UtilityAccountNumber>" and "<ZipCode>" and "<EmailAddress>" and "<FirstName>" and "<LastName>" and "<UserName>" and "<Password>" and "<PrimaryPhone>" and "<StreetAddress>" and "<Preferences>"
    When User calls "RegisterUser" with "POST" http request
    Then user validates API call got success with status code "<StatusCode>"
    And "Message" in response body is "Successful!!"
    And User gets registered in user table with details "<UserName>" and "<EmailAddress>" and "<PrimaryPhone>" and "<FirstName" and "<LastName>" and "<Status>" and "<Preferences>"
    And Able to login with "<UserName>" and "<Password>"

    Examples:
      | UtilityAccountNumber | ZipCode  | EmailAddress | FirstName     | LastName    | UserName     | Password     | PrimaryPhone | StreetAddress  | Preferences | Status | StatusCode |
      | utility_acc_num      | zip_code | api_test     | Tony          | Stark       | tony_st@rk-  | Demouser@1   | 9999999999   | street_address | noti_pref   |  0     |  200       |


  #@Registration @Sanity @Regression
  Scenario Outline: User should be a able to register with valid residential account with opting only paperless preference.
    Given User register with details "<UtilityAccountNumber>" and "<ZipCode>" and "<EmailAddress>" and "<FirstName>" and "<LastName>" and "<UserName>" and "<Password>" and "<PrimaryPhone>" and "<StreetAddress>" and "<Preferences>"
    When User calls "RegisterUser" with "POST" http request
    Then user validates API call got success with status code "<StatusCode>"
    And "Message" in response body is "Successful!!"
    And User gets registered in user table with details "<UserName>" and "<EmailAddress>" and "<PrimaryPhone>" and "<FirstName" and "<LastName>" and "<Status>" and "<Preferences>"
    And Able to login with "<UserName>" and "<Password>"

    Examples:
      | UtilityAccountNumber | ZipCode  | EmailAddress | FirstName     | LastName    | UserName     | Password     | PrimaryPhone | StreetAddress  | Preferences | Status | StatusCode |
      | utility_acc_num      | zip_code | api_test     | Tony          | Stark       | tony_st@rk-  | Demouser@1   | 9999999999   | street_address | paper_pref  |  0     |  200       |


  #@Registration @Sanity @Regression
  Scenario Outline: User should be a able to register with valid commercial account without opting any preferences.
    Given User register with details "<UtilityAccountNumber>" and "<ZipCode>" and "<EmailAddress>" and "<FirstName>" and "<LastName>" and "<UserName>" and "<Password>" and "<PrimaryPhone>" and "<StreetAddress>" and "<Preferences>"
    When User calls "RegisterUser" with "POST" http request
    Then user validates API call got success with status code "<StatusCode>"
    And "Message" in response body is "Successful!!"
    And User gets registered in user table with details "<UserName>" and "<EmailAddress>" and "<PrimaryPhone>" and "<FirstName" and "<LastName>" and "<Status>" and "<Preferences>"
    And Able to login with "<UserName>" and "<Password>"

    Examples:
      | UtilityAccountNumber | ZipCode  | EmailAddress | FirstName     | LastName    | UserName     | Password     | PrimaryPhone | StreetAddress  | Preferences | Status | StatusCode |
      | utility_acc_num      | zip_code | api_test     | Tony          | Stark       | tony_st@rk-  | Demouser@1   | 9999999999   | street_address | no_pref     |  0     |  200       |