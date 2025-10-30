package org.example.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example",
        tags = "@regression or @smoke or @e2e",
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-html-report-alltests.html", "json:target/cucumber-reports/cucumber-alltests.json"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests{

}

