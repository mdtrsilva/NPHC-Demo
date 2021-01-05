Feature: Governor

  Scenario: The Dispense button should be as given design
   Given launch the browser
   When open the url
   Then the button should be displayed correctly

  Scenario: Naviagion page should be correct after click Dispense button
    Given launch the browser
    When open the url
    When click on the button
    Then the landing page should be correct
    #Then close the browser