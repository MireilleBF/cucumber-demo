# Cucumber-demo: Example Code for Cucumber Tests

This GitHub repository contains example code to help you learn how to use Cucumber for testing.

**Key Features**:
- Cucumber 7 and JUnit 5
- cucumber-picocontainer 7.8
- Compatible with Maven
- Requires JDK 17
- Mockito 4.8
- Gherkin and stepDefs in both French (FR) and English (EN), including integration of _Examples_
- GitHub Actions (Check the .github/workflows) for straightforward Maven compilation and testing.

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

:warning: **Please use the** **pom.xml** file provided to establish the link between JUnit 5, PicoContainer, and Cucumber.


:arrow_forward: Packages are used to organize the demonstrations
- In the [_martin_fowler_](./src/main/java/martin_fowler/README.md) package, you will find Martin Fowler's example of injection by constructor and by fields.
- In [stock management](./src/test/resources/features/store/README.md), tests demonstrate the basic usage of PicoContainer with Cucumber
- The other presents a simplified [version of a library](./src/test/java/fr/unice/polytech/biblio) [demonstration developed by Philippe Collet](https://github.com/collet/cucumber-demo) 


:arrow_forward: **Features:** 
The feature description files (Gherkin files) are accessible under [test/resources/features](./src/test/resources/features)

:arrow_forward: **StepDefs:** The implementations corresponding to the steps are defined under tests and follow the same structure as the files describing the scenarios. They refer to the sources located under main.

## Enabling Cucumber Support in IntelliJ IDEA

https://www.jetbrains.com/help/idea/enabling-cucumber-support-in-project.html

In the martin_fowler package, you will find Martin Fowler's example of injection by constructor and by fields.
## Warning
1. Attention: if the classes defining the steps are not public, they are not accessible during execution.
2. Don't forget to adapt **RunCucumberTest.java** classes to your needs.

