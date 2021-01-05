Feature: Clerk

  Scenario: Clerk should be able to upload CSV file with correct format
    Given launch the browser
    When open the url
    Then Page title should be "Technical Challenge for CDS"
    When click browse button
    Then upload the csv file
    Then verify the file upload status
    Then close the browser




