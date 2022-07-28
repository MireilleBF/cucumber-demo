package fr.unice.polytech.cf.demo.store;

/**
 * Copy/Paste from Philippe Collet Demonstation
 */

//import io.cucumber.junit.CucumberOptions;
//import io.cucumber.junit.Cucumber;
import org.junit.platform.suite.api.ConfigurationParameter;
//import org.junit.runner.RunWith;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
//@SelectClasspathResource("fr/unice/polytech/cf/demo/store")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fr.unice.polytech.cf.demo.store")

//@RunWith(value = Cucumber.class)
//@CucumberOptions(plugin = {"pretty"}, features = "src/test/resources/features")
public class RunCucumberTest { // will run all features found on the classpath
    // in the same package as this class
}
