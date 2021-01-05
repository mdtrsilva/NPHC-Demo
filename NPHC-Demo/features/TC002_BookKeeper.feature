Feature: Bookkeeper

  Scenario Outline: BookKeeper should be able to get tax relief details
    Given launch the browser
    When open the url
    Then Page title should be "Technical Challenge for CDS"
    Then click TaxReliefTable button
    Then tax players details should be displayed
    Then natid should be masked
    Then tax amount should be accurate "<natid>", <tax_relief>
    Then close the browser

    Examples:
    |natid|tax_relief|
    | 019-$$$$$$$ | 872000.00 |
    | 029-$$$$$$$ | 872500.00 |
    | 001-$$$$$$$ | 1090500.00 |
    | 002-$$$$$$$ | 1090500.00 |
    | 003-$$$$$$$ | 872500.00 |
    | 004-$$$$$$$ | 872500.00 |
    | 005-$$$$$$$ | 545500.00 |
    | 006-$$$$$$$ | 545500.00 |
    | 007-$$$$$$$ | 400530.00 |
    | 008-$$$$$$$ | 400530.00 |
    | 009-$$$$$$$ | 55000.00 |
    | 010-$$$$$$$ | 55000.00 |


