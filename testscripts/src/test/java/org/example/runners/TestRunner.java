package org.example.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/features/insuranceapi.feature",
        glue = "org.example",
        tags = "",
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-html-report-insuranceapi.html", "json:target/cucumber-reports/cucumber-insuranceapi.json"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests{

}

