## Automated REST API tests for [shop.bugred.ru](http://shop.bugred.ru/)

<p>
  <img src="media/screens/Shopscreen.png" alt="Swag Labs logo">
</p>

## Content

- [Stack of technologies](#stack-of-technologies)
- [Description](#description)
- [List of REST API tests](#list-of-ui-tests)
- [Executing tests through the command line](#executing-tests-through-the-command-line)
- [Running tests in Jenkins](#running-tests-in-jenkins)
- [Test results report in Allure Report](#test-results-report-in-allure-report)
- [Allure TestOps integration](#allure-testops-integration)
- [Jira integration](#jira-integration)
- [Telegram notifications](#telegram-notifications)

---

## Stack of technologies

<div style="text-align: center;">
<img width="5%" title="Java" src="media/icons/Java.svg" alt="Java Logo">
<img width="5%" title="Selenide" src="media/icons/Selenide.svg" alt="Selenide Logo">
<img width="5%" title="Junit5" src="media/icons/Junit5.svg" alt="JUnit5 Logo">
<img width="5%" title="REST-assured" src="media/icons/REST-assured.svg" alt="REST-assured Logo">
<img width="5%" title="Gradle" src="media/icons/Gradle.svg" alt="Gradle Logo">
<img width="5%" title="GitHub" src="media/icons/GitHub.svg" alt="GitHub Logo">
<img width="5%" title="Jenkins" src="media/icons/Jenkins_logo.svg" alt="Jenkins Logo">
<img width="5%" title="Allure Report" src="media/icons/Allure.svg" alt="Allure Report Logo">
<img width="5%" title="Allure TestOps" src="media/icons/Allure_TO.svg" alt="Allure TestOps Logo">
<img width="5%" title="IntelliJ IDEA" src="media/icons/Idea.svg" alt="IntelliJ IDEA Logo">
<img width="5%" title="Jira" src="media/icons/Jira.svg" alt="Jira Logo">
<img width="5%" title="Telegram" src="media/icons/Telegram.svg" alt="Telegram Logo">
</div>

---

## Description

This test project consists of REST API tests and includes the following features:

- **Parameterized tests**: Enables the testing of diverse scenarios through the provision of varied input data
- **Request specification**: Streamlines and centralizes API test configuration
- **Object serialization/deserialization**: Facilitates seamless data transformation for API requests and responses using the `Jackson` library.
- **Comprehensive Test Reporting and Analytics**: Integration with `Allure TestOps` for robust test reporting and
  in-depth analytical insights
- **Test Data Generation**: Utilizing the `Faker` library to generate realistic and randomized test data inputs

[Back to top](#content)

---

## List of REST API tests

### Item creation

- Create an item in the shop with all fields set
- Create an item in the shop with only required fields set
- Test unsuccessful item creation by omitting a required parameter
- Attempt to create an item with a photo exceeding 500px in width.

### Item deletion

- Delete an item from the shop
- Attempt to delete an item with an invalid or non-existent ID

[Back to top](#content)

---

## Executing tests through the command line

To run tests through the command line using Gradle, you can use the following commands:

```bash
gradle clean test
```

[Back to top](#content)

---
