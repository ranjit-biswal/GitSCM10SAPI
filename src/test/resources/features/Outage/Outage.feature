Feature: Add and update outage and validate if the outage inserted and updated successfully.

  # In this feature we are creating and updating outage and validating the response
  # as well as the database whether it is created and updating in the database or
  # not
 @Outage @Sanity @Regression
  Scenario Outline: User should be able to add outage and validate outage inserted successfully.
    Given User create outage with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" and "<IsResolved>" and "<ServiceType>" and "<IsPlanned>"
    When User calls  "AddUpdateOutage" with "POST" http request
    Then API call got success for creating outage with status code "<StatusCode>" and "<Message>"
    And Outage info get saved with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" in database

    Examples: 
      |OutageInfo    |OutageCause  |OutageMessage                |StartTime           |EndTime             |IsResolved |ServiceType |IsPlanned |StatusCode |Message                      |
      |Power Outage  |Power Failure|Outage due to windstorm.     |2021-08-31T11:17:32 |2021-09-01T11:17:55 |         0 |          1 |        0 |       200 |Outage inserted successfully |
      |Water Outage  |No Water     |Outage due to water shortage.|2021-08-31T11:17:32 |2021-09-01T11:17:55 |         1 |          2 |        1 |       200 |Outage inserted successfully |
      |Gas Outage    |No Gas       |Outage due to gas leakage.   |2021-08-31T11:17:32 |2021-09-01T11:17:55 |         0 |          3 |        1 |       200 |Outage inserted successfully |

 @Outage @Sanity @Regression
  Scenario Outline: User should be able to update outage and validate outage updated successfully.
    Given User update outage with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" and "<IsResolved>" and "<ServiceType>" and "<IsPlanned>"
    When User calls "AddUpdateOutage" with the existing outage id and "POST" http request
    Then API call got success for updating outage with status code "<StatusCode>" and "<Message>"
    And Outage info get updated with details "<OutageInfo>" and "<OutageCause>" and "<OutageMessage>" and "<StartTime>" and "<EndTime>" in database.

    Examples: 
      |OutageInfo         |OutageCause    |OutageMessage                 |StartTime           |EndTime             |IsResolved |ServiceType |IsPlanned |StatusCode |Message                     |
      |Power Outage 23455 |Power Failure  |Outage due to windstorm 23455 |2020-09-26T13:36:55 |2020-10-27T13:36:55 |         0 |          1 |        0 |       200 |Outage updated successfully |
      |Water Outage       |Water Shortage |Outage due to water shortage  |2020-09-26T13:36:55 |2020-10-27T13:36:55 |         0 |          2 |        1 |       200 |Outage updated successfully |
      |Gas Outage         |Gas Shortage   |Outage due to gas leakage     |2020-09-26T13:36:55 |2020-10-27T13:36:55 |         0 |          3 |        1 |       200 |Outage updated successfully |