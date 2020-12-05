import java.io.File;

 

import org.junit.AfterClass;
import org.junit.runner.RunWith;

 

import com.cucumber.listener.Reporter;

 

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import utility.DriverManager;

 

@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:UI_Features" }, glue = { "classpath:UI_StepDefinitions", "classpath:UI_Hooks" }, plugin = { "pretty", "html:HTML-Reports", "com.cucumber.listener.ExtentCucumberFormatter:ExtentReports/OrdersAPI_UI_TestAutomation.html" }, monochrome = true, tags = { "@smokeTest", "~@ignore" })

 

public class TestRunner1
{
    private static String extentConfig = "src/main/java/utility/extent-config.xml";

 

    @AfterClass
    public static void setup()
    {
        Reporter.loadXMLConfig(new File(extentConfig));
        Reporter.setSystemInfo("User Name", "Pirashanth");
        Reporter.setSystemInfo("Application Name", "OrdersAPI_UI");
        Reporter.setSystemInfo("Operating System Type", System.getProperty("os.name").toString());
        Reporter.setSystemInfo("Environment", "QA");
        Reporter.setTestRunnerOutput("OrdersAPI_UI");

 

        DriverManager.getDriver().close();
    }
}
