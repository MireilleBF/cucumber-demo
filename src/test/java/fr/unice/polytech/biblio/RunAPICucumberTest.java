package fr.unice.polytech.biblio;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")

@SelectPackages("features.biblio.api")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fr.unice.polytech.biblio.stepDefs.restAPI")

public class RunAPICucumberTest {
    /*
     This enables the servers setup (port definition, etc.) to be done only for these tests.
     A specific Hook class defines the common Before/After methods for all stepdefs in these packages.
     */
}