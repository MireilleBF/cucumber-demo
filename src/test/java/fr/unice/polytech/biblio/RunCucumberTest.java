package fr.unice.polytech.biblio;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")

//Specifies the location of the feature files. The feature files are stored in the "features/biblio" directory within the classpath.
@SelectClasspathResource("features/biblio")

@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
//Specifies the package where the step definitions are located.
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fr.unice.polytech.biblio")

public class RunCucumberTest {
    // will run all features found on the classpath
    // in the same package as this class (can be changed with @SelectPackages)
}