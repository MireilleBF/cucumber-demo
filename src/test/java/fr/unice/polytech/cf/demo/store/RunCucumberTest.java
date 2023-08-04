package fr.unice.polytech.cf.demo.store;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
//Specifies the location of the feature files. The feature files are stored in the "features/store" directory within the classpath.
@SelectClasspathResource("features/store")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
//glue = "fr.unice.polytech.cf.demo.store": Specifies the package where the step definitions are located. Cucumber will look for the step definitions in this package.
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fr.unice.polytech.cf.demo.store")

public class RunCucumberTest {
    // will run all features found on the classpath
    // in the same package as this class
}
