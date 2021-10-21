# Backbase recruitment exercise by Maciej Mika

# 1. Manual tests, bugs & improvements

Manual test cases can be found in excel file inside 'test_cases' folder in main repo directory.
I decided to cover (and further automate) cases that are in my opinion crucial for the blog website to function - Sign-in functionality and features connected to the articles - creating, editing and removing them. They also allowed me to create API and GUI automated tests in which I could show some of the design patterns and interesting solutions that I learned through my testing journey.

Bugs that I caught along with suggestions for improvements can be found in excel file inside 'bugs&improvements' folder in main repo directory.

# 2. API automated tests
I created 2 test files with 5 tests in total, for sign-In and article features.

## How to run
run 'mvn clean test' inside 'rest-assured_tests' directory
To generate and open allure report, run:
'allure generate target/allure-results --clean'
'allure open'

## Most important tools and libraries used:
- Java 17
- Junit 5
- Rest-Assured
- Allure reports
- Java Faker

## more info about API tests
- API tests are separated from requests and helper methods as different modules, so they can be re-used in Selenium tests
- for data objects I decided to use Record entities instead of classic Classes with lots of annotations, which in my opinion creates a nice clean look
- inside the 'requests' directory I kept the order of classes which reflects the endpoints order. It later helps you find something in the thicket of endpoints more easily. I saw a lot of API automated tests repositories with test-files names that didn't correspond to the endpoints names nor to the order of them and it was a nightmare ;)
- tests execution is paralleled by classes, tests inside the class run one by one
- I wanted to show a cross-section of API tests I use to write - from atomic, integration solutions like "givenRegisteredUserWhenNoBasicAuthThen401" test which verifies one specific behaviour to e2e tests like "articleFlow" test.

# 3. GUI automated tests
I created 2 test files with 4 tests in total, for sign-In and article features.

## How to run
run 'mvn clean test' inside 'selenium_tests' directory
To generate and open allure report, run:
'allure generate target/allure-results --clean'
'allure open'

## Most important tools and libraries used:
- Java 17
- Junit 5
- Selenium
- Selenium-Jupiter
- Allure reports
- Rest-assured requests module mentioned in API Automated tests section.

## more info about GUI tests
- core of my Selenium tests is Selenium-Jupiter framework. This is very powerful Selenium extension written to cooperate with Junit 5. One of it's main features is easy, almost maintenance-free multi-browser management. In my case, I used TestTemplate functionality coupled with Selenium-Jupiter that allow me to easily manage the browsers I want to use for my tests. I will provide more info about browser management in the project in separate paragraph.
- I used classic Page Object Pattern with Page Factory.
- tests execution is paralleled by classes, tests inside the class run one by one
- I implemented possibility to run the tests headless - to enable/disable headless mode change 'headless' variable value in application.properties file in 'selenium_tests/src/main/resources' directory

## Browsers management:


### Local browsers
Inside test resources of selenium_tests module you will find browsers.json file.
This file manages the usage of browsers inside the tests. We can extend it with different types of browsers or set specific browser version. Browser "type" value without "-in-docker" suffix means test will use local browser. That's why to assure tests will run correctly, we have to have proper browsers version installed locally.
### Docker browsers
Inside test resources of selenium_tests module you can find another file, called docker-browsers.json. This file holds configuration for docker browsers.
In order to run the tests using dockerized browsers, you have to:
1. Have locally installed docker with "Expose deamon" option enabled.
2. Pull latest aerokube/selenoid image - run "docker pull aerokube/selenoid" in cmd line
3. Rename 'docker-browsers.json' file to 'browsers.json', as this is a keyword used by Selenium-Jupiter to find correct browser config file.
### Drivers
What about the drivers? Selenium-Jupiter uses webdriver-manager to download proper WebDriver for the selected browsers.

More info about TemplateTests usage with Selenium-Jupiter can be found here : https://bonigarcia.dev/selenium-jupiter/#template-tests

# 4. Additional features
- CI files (for shippable CI and Gitlab CI) are located in 'CI_files' folder in main repo directory.
- Provided gitlab_ci.yml creates 2 independent jobs in the pipeline, one for Selenium and another for Rest-Assured tests. It's configured to save allure report files as artifacts after the run.
- Shippable file is configured to first run rest-assured tests and then selenium tests. Next, it copies the allure reports to AWS s3 bucket.