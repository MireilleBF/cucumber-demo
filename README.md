# Cucumber-demo: Example Code for Cucumber Tests

In this GitHub repository, you will find example code to help you understand how to use Cucumber for testing.

- Cucumber 7 et JUnit 5
- cucumber-picocontainer 7.8
- Maven compatible
- JDK 17
- Gherkin and stepDefs FR and EN (integrate _Examples_)
- Github Actions (See in .github/workflows) to simply make a maven+test compilation

The branch _failure_on_CI_ shows tests that do not pass.


## Execution of tests

`mvn test`

expected result:

```
[INFO] Results:
[INFO] 
[INFO] Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.456 s
[INFO] Finished at: 2023-08-04T16:39:55+02:00
[INFO] ------------------------------------------------------------------------

```

## Organisation of the code

:warning: **Please make sure to use the** **pom.xml** file that has been provided to establish the link between JUnit 5, PicoContainer, and Cucumber.


:arrow_forward: Two packages are used to organize the tests:
- One contains tests for [stock management](./src/test/java/fr/unice/polytech/cf/demo/store/README.md) and demonstrates the basic usage of PicoContainerwith Cucumber
- The other presents a simplified [version of a library](./src/test/java/fr/unice/polytech/biblio) [demonstration developed by Philippe Collet](https://github.com/collet/cucumber-demo) 


:arrow_forward: **Features:** 
The feature description files (Gherkin files) are accessible under [test/resources/features](./src/test/resources/features)

:arrow_forward: **StepDefs:** The implementations corresponding to the steps are defined under tests and follow the same structure as the files describing the scenarios. They refer to the sources located under main

## Enabling Cucumber Support in IntelliJ IDEA

https://www.jetbrains.com/help/idea/enabling-cucumber-support-in-project.html

## Warning
1. Attention, if the classes defining the steps are not public, they are not accessible during execution.
2. Don't forget to adapt **RunCucumberTest.java** classes to your needs.

