package utility;

 

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

 

import OrdersAPI.Enums.Enums.DriveBrowser;
import OrdersAPI.Enums.Enums.Locator;
import lombok.Setter;

 

public class DriverManager extends CommonUtility
{
    @Setter
    private static WebDriver driver;

 

    public void initialiseDriver() throws Exception
    {
        String driverPath = System.getProperty("user.dir") + Constants.driverPath;

 

        if (Constants.browser == DriveBrowser.Chrome)
        {
            System.setProperty("webdriver.chrome.driver", driverPath);
            ChromeOptions chromeOptions = new ChromeOptions();
            if (Constants.headlessMode)
            {
                chromeOptions.addArguments("--headless");
            }
            driver = new ChromeDriver(chromeOptions);
            driver.manage().window().maximize();
        }
        else
        {
            throw new Exception("Browser value does not exist");
        }
    }

 

    protected DriverManager()
    {
        if (driver == null)
        {
            try
            {
                initialiseDriver();
            }
            catch (Exception e)
            {
                printMessage(e.getMessage());
            }
        }
    }

 

    public static WebDriver getDriver()
    {
        if (driver == null)
        {
            new DriverManager();
        }
        return driver;
    }

 

    public static void closeDriver()
    {
        try
        {
            driver.close();
            driver.quit();
            driver = null;
        }
        catch (Exception e)
        {
            driver = null;
        }
    }

 

    public WebElement findByLocator(Locator locatorType, String locator)
    {
        switch (locatorType)

 

        {
            case Classname:
                return WaitExtension.waitUntilClassname(locator);

 

            case CssSelector:
                return driver.findElement(By.cssSelector(locator));

 

            case Id:
                return WaitExtension.waitUntilId(locator);

 

            case LinkText:
                return driver.findElement(By.linkText(locator));

 

            case Name:
                return driver.findElement(By.name(locator));

 

            case PartialLinkText:
                return driver.findElement(By.partialLinkText(locator));

 

            case TagName:
                return WaitExtension.waitUntilTagName(locator);

 

            case XPath:
                return WaitExtension.waitUntilXpath(locator);
        }

 

        return null;

 

    }

 

}
