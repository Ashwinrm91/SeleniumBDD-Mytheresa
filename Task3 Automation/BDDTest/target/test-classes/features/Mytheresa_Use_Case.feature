@Mytheresa
Feature: Testing Mytheresa Website

  Scenario: User loading the Mytheresa website URL and verifies the error code
    #Test case 1
    Given user opens the browser
    Then user loads the URL
    Then user validate Href links using status code
   # Test case 2
     Then user enters login details
   # Test case 3
    Then user is getting pull request as CSV
