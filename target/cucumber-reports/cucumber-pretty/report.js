$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("ConnectMe/ConnectMe.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#@Ignore"
    }
  ],
  "line": 2,
  "name": "Validating Create Connect Me program type from 10S SCM API",
  "description": "",
  "id": "validating-create-connect-me-program-type-from-10s-scm-api",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 5,
  "name": "User validate Create connect Me after successfully getting Login token",
  "description": "",
  "id": "validating-create-connect-me-program-type-from-10s-scm-api;user-validate-create-connect-me-after-successfully-getting-login-token",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@ConnectMe"
    },
    {
      "line": 4,
      "name": "@Sanity"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User enters Valid LoginID \"\u003cUserName\u003e\" and \"\u003cPassWord\u003e\" and \"\u003cAccountNumber\u003e\" and get the get the Login Token",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "User enters details for Connect me \"SetConnectMeRequest\" and \"POST\" the request for API",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "user validates API call got success with status code \"\u003cStatusCode\u003e\"",
  "keyword": "Then "
});
formatter.examples({
  "line": 10,
  "name": "",
  "description": "",
  "id": "validating-create-connect-me-program-type-from-10s-scm-api;user-validate-create-connect-me-after-successfully-getting-login-token;",
  "rows": [
    {
      "cells": [
        "UserName",
        "PassWord",
        "AccountNumber",
        "StatusCode"
      ],
      "line": 11,
      "id": "validating-create-connect-me-program-type-from-10s-scm-api;user-validate-create-connect-me-after-successfully-getting-login-token;;1"
    },
    {
      "cells": [
        "nikhil@12",
        "Demo@12345",
        "411002248606",
        "200"
      ],
      "line": 12,
      "id": "validating-create-connect-me-program-type-from-10s-scm-api;user-validate-create-connect-me-after-successfully-getting-login-token;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 12,
  "name": "User validate Create connect Me after successfully getting Login token",
  "description": "",
  "id": "validating-create-connect-me-program-type-from-10s-scm-api;user-validate-create-connect-me-after-successfully-getting-login-token;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@Sanity"
    },
    {
      "line": 4,
      "name": "@ConnectMe"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User enters Valid LoginID \"nikhil@12\" and \"Demo@12345\" and \"411002248606\" and get the get the Login Token",
  "matchedColumns": [
    0,
    1,
    2
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "User enters details for Connect me \"SetConnectMeRequest\" and \"POST\" the request for API",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "user validates API call got success with status code \"200\"",
  "matchedColumns": [
    3
  ],
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "nikhil@12",
      "offset": 27
    },
    {
      "val": "Demo@12345",
      "offset": 43
    },
    {
      "val": "411002248606",
      "offset": 60
    }
  ],
  "location": "StepDefinationLoginApi.user_enters_Valid_LoginID_and_and_and_get_the_get_the_Login_Token(String,String,String)"
});
formatter.result({
  "duration": 31844729000,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "SetConnectMeRequest",
      "offset": 36
    },
    {
      "val": "POST",
      "offset": 62
    }
  ],
  "location": "StepDefinationConnectMeApi.user_enters_details_for_Connect_me_and_the_request_for_API(String,String)"
});
formatter.result({
  "duration": 3133997800,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "200",
      "offset": 54
    }
  ],
  "location": "StepDefinationLoginApi.user_validates_API_call_got_success_with_status_code(String)"
});
formatter.result({
  "duration": 606300,
  "status": "passed"
});
formatter.uri("Login/Login.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#@Ignore"
    }
  ],
  "line": 2,
  "name": "Validating Login Api with UserID",
  "description": "",
  "id": "validating-login-api-with-userid",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 5,
  "name": "User validate user with userName and Password and",
  "description": "",
  "id": "validating-login-api-with-userid;user-validate-user-with-username-and-password-and",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@Login"
    },
    {
      "line": 4,
      "name": "@Sanity"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User enters Valid LoginID \"\u003cUserName\u003e\" and \"\u003cPassWord\u003e\" and \"\u003cAccountNumber\u003e\" and get the get the Login Token",
  "keyword": "Given "
});
formatter.examples({
  "line": 9,
  "name": "",
  "description": "",
  "id": "validating-login-api-with-userid;user-validate-user-with-username-and-password-and;",
  "rows": [
    {
      "cells": [
        "UserName",
        "PassWord",
        "AccountNumber"
      ],
      "line": 10,
      "id": "validating-login-api-with-userid;user-validate-user-with-username-and-password-and;;1"
    },
    {
      "cells": [
        "nikhil@12",
        "Demo@12345",
        "411002248606"
      ],
      "line": 11,
      "id": "validating-login-api-with-userid;user-validate-user-with-username-and-password-and;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 11,
  "name": "User validate user with userName and Password and",
  "description": "",
  "id": "validating-login-api-with-userid;user-validate-user-with-username-and-password-and;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@Login"
    },
    {
      "line": 4,
      "name": "@Sanity"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User enters Valid LoginID \"nikhil@12\" and \"Demo@12345\" and \"411002248606\" and get the get the Login Token",
  "matchedColumns": [
    0,
    1,
    2
  ],
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "nikhil@12",
      "offset": 27
    },
    {
      "val": "Demo@12345",
      "offset": 43
    },
    {
      "val": "411002248606",
      "offset": 60
    }
  ],
  "location": "StepDefinationLoginApi.user_enters_Valid_LoginID_and_and_and_get_the_get_the_Login_Token(String,String,String)"
});
formatter.result({
  "duration": 8124548400,
  "status": "passed"
});
formatter.uri("Login/Logout.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#@Ignore"
    }
  ],
  "line": 2,
  "name": "Validating User is able to logout from the application",
  "description": "",
  "id": "validating-user-is-able-to-logout-from-the-application",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 5,
  "name": "Verify if User gets logout from the application",
  "description": "",
  "id": "validating-user-is-able-to-logout-from-the-application;verify-if-user-gets-logout-from-the-application",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@Logout"
    },
    {
      "line": 4,
      "name": "@Sanity"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User enters Valid LoginID \"\u003cUserName\u003e\" and \"\u003cPassWord\u003e\" and \"\u003cAccountNumber\u003e\" and get the get the Login Token",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "user calls \"GetLogOut\" API with \"POST\" http request",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "user validates API call got success with status code \"\u003cStatusCode\u003e\"",
  "keyword": "Then "
});
formatter.step({
  "line": 9,
  "name": "\"Message\" in response body is \"Successful!!\"",
  "keyword": "And "
});
formatter.examples({
  "line": 12,
  "name": "",
  "description": "",
  "id": "validating-user-is-able-to-logout-from-the-application;verify-if-user-gets-logout-from-the-application;",
  "rows": [
    {
      "cells": [
        "UserName",
        "PassWord",
        "AccountNumber",
        "StatusCode"
      ],
      "line": 13,
      "id": "validating-user-is-able-to-logout-from-the-application;verify-if-user-gets-logout-from-the-application;;1"
    },
    {
      "cells": [
        "nikhil@12",
        "Demo@12345",
        "411002248606",
        "200"
      ],
      "line": 14,
      "id": "validating-user-is-able-to-logout-from-the-application;verify-if-user-gets-logout-from-the-application;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.scenario({
  "line": 14,
  "name": "Verify if User gets logout from the application",
  "description": "",
  "id": "validating-user-is-able-to-logout-from-the-application;verify-if-user-gets-logout-from-the-application;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 4,
      "name": "@Logout"
    },
    {
      "line": 4,
      "name": "@Sanity"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "User enters Valid LoginID \"nikhil@12\" and \"Demo@12345\" and \"411002248606\" and get the get the Login Token",
  "matchedColumns": [
    0,
    1,
    2
  ],
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "user calls \"GetLogOut\" API with \"POST\" http request",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "user validates API call got success with status code \"200\"",
  "matchedColumns": [
    3
  ],
  "keyword": "Then "
});
formatter.step({
  "line": 9,
  "name": "\"Message\" in response body is \"Successful!!\"",
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "nikhil@12",
      "offset": 27
    },
    {
      "val": "Demo@12345",
      "offset": 43
    },
    {
      "val": "411002248606",
      "offset": 60
    }
  ],
  "location": "StepDefinationLoginApi.user_enters_Valid_LoginID_and_and_and_get_the_get_the_Login_Token(String,String,String)"
});
formatter.result({
  "duration": 7524148500,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "GetLogOut",
      "offset": 12
    },
    {
      "val": "POST",
      "offset": 33
    }
  ],
  "location": "StepDefinationLogoutApi.user_calls_API_with_http_request(String,String)"
});
formatter.result({
  "duration": 1933422500,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "200",
      "offset": 54
    }
  ],
  "location": "StepDefinationLoginApi.user_validates_API_call_got_success_with_status_code(String)"
});
formatter.result({
  "duration": 246000,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Message",
      "offset": 1
    },
    {
      "val": "Successful!!",
      "offset": 31
    }
  ],
  "location": "StepDefinationLogoutApi.in_response_body_is(String,String)"
});
formatter.result({
  "duration": 53720900,
  "status": "passed"
});
});