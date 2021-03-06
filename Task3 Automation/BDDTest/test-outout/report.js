$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Mytheresa_Use_Case.feature");
formatter.feature({
  "line": 2,
  "name": "Testing Mytheresa Website",
  "description": "",
  "id": "testing-mytheresa-website",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@Mytheresa"
    }
  ]
});
formatter.scenario({
  "line": 4,
  "name": "User loading the Mytheresa website URL and verifies the error code",
  "description": "",
  "id": "testing-mytheresa-website;user-loading-the-mytheresa-website-url-and-verifies-the-error-code",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "comments": [
    {
      "line": 5,
      "value": "#Test case 1"
    },
    {
      "line": 6,
      "value": "#Given user opens the browser"
    },
    {
      "line": 7,
      "value": "#Then user loads the URL"
    },
    {
      "line": 8,
      "value": "#Then user validate Href links using status code"
    },
    {
      "line": 9,
      "value": "#Test case 2"
    },
    {
      "line": 10,
      "value": "# Then user enters login details"
    },
    {
      "line": 11,
      "value": "#Test case 3"
    }
  ],
  "line": 12,
  "name": "user is getting pull request as CSV",
  "keyword": "Then "
});
formatter.match({
  "location": "UseCaseMethod.user_is_getting_pull_request_as_CSV()"
});
formatter.result({
  "duration": 2936693100,
  "status": "passed"
});
});