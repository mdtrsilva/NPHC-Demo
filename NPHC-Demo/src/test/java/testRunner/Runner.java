package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        (


              features = ".//features/TC003_Governor.feature",
                glue="stepDefinitions",
                dryRun=false,
                monochrome = true,
                plugin= {"pretty", "html:target/test-reports","json:target/reports/cucumber.json"}






        )
public class Runner {


}
