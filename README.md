# Cucumber-demo: Example Code for Cucumber Tests

This GitHub repository contains example code to help you learn how to use Cucumber for testing.

**Key Features**:
- Cucumber 7 and JUnit 5 (last stable and compatible versions as of August 2025)
- Requires JDK 21 (not 25) and Maven 3.9
- Mockito 5 (last stable version as of August 2025)
- Gherkin and stepDefs in both French (FR) and English (EN), including integration of _Examples_
- GitHub Actions (Check the .github/workflows) for straightforward Maven compilation and testing.

## Execution of tests

`mvn test`

expected result:

```
...... (output shortened)

[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 31, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.871 s
[INFO] Finished at: 2025-09-08T11:51:30+02:00
[INFO] ------------------------------------------------------------------------```

## Organisation of the code

:warning: **Please use the** **pom.xml** file provided to establish a working link between JUnit 5 and Cucumber (generative AIs tend to provide outdated or incompatible dependencies).

:arrow_forward: **Features:** 
The feature description files (Gherkin files) are accessible under [test/resources/features](./src/test/resources/features)

:arrow_forward: **StepDefs:** The implementations corresponding to the steps are defined under tests and follow the same structure as the files describing the scenarios. They refer to the sources located under main.

## Enabling Cucumber Support in IntelliJ IDEA

The support has to be activated in IntelliJ IDEA. To do this, install the Cucumber for Java plugin: https://plugins.jetbrains.com/plugin/7212-cucumber-for-java
Still, as Cucumber has made many changes in recent versions, the support is not perfect.

## Warning
1. Attention: if the classes defining the steps are not public, they are not accessible during execution.
2. Don't forget to adapt **RunCucumberTest.java** classes to your needs.


## Running test with maven (@PhilippeCollet)

By default, maven use its *surefire* plugin to run tests. This plugin is especially built for running unit tests, as it will diretly fail if any test fails. 
This is a good property for preventing the build to be made (the goal *package* will typically fail).

It must be noted that *surefire* will, by default, find tests with the following names and run them during the `test` phase (i.e. just before `package`):

* `"**/Test*.java"` - includes all of its subdirectories and all Java filenames that start with "Test".
* `"**/*Test.java"` - includes all of its subdirectories and all Java filenames that end with "Test".
* `"**/*Tests.java"` - includes all of its subdirectories and all Java filenames that end with "Tests".
* `"**/*TestCase.java"` - includes all of its subdirectories and all Java filenames that end with "TestCase".`
