package utility;

 

import java.util.function.Function;

 

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

 

import UI_Pages.BasePage;

 

public class WaitExtension extends CommonUtility
{
    private static int timeOutSeconds = 80;

 

    public static WebElement waitUntilId(String locator)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));

 

        return DriverManager.getDriver().findElement(By.id(locator));
    }

 

    public static WebElement waitUntilXpath(String locator)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));

 

        return DriverManager.getDriver().findElement(By.xpath(locator));
    }

 

    public static WebElement waitUntilLinkText(String locator)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator)));

 

        return DriverManager.getDriver().findElement(By.linkText(locator));
    }

 

    public static WebElement waitUntilClassname(String locator)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        System.out.println(DriverManager.getDriver().findElements(By.className("dxgvControl_CTN")).size());
        System.out
                .println(DriverManager.getDriver().findElements(By.xpath("//*[text()='15/10/2019 11:00:09']")).size());
        System.out.println(DriverManager.getDriver().findElements(By.className("dxp-comboBox")).size());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(locator)));

 

        return DriverManager.getDriver().findElement(By.className(locator));
    }

 

    public static WebElement waitUntilTagName(String locator)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(locator)));

 

        return DriverManager.getDriver().findElement(By.tagName(locator));
    }

 

    public static void waitUntilDisappeared(String locator)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName(locator)));
    }

 

    public static void waitUntilJavascriptIsFinished()
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                .executeScript("return document.readyState").equals("complete"));
    }

 

    public static void waitUntilUrlContains(String url)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.urlContains(url));
    }

 

    public static void waitUntilUrlEndsWith(String url)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(new Function<WebDriver, Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                return driver.getCurrentUrl().endsWith(url);
            }
        });
    }
    
    public static void waitAndRetrySendingKeys(By locator, String key)
    {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeOutSeconds);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        DriverManager.getDriver().findElement(locator).sendKeys(key);
    }

 

    public static void refreshPageAndWaitUntilXpathAndClick(String xpath, BasePage page)
    {
        for (int a = 10; a > 0; a--)
        {
            page.refreshPage();
            try
            {
                DriverManager.getDriver().findElement(By.xpath(xpath)).click();
                break;
            }
            catch (Exception e)
            {
                printMessage(e.getMessage());
            }
        }

 

    }

 

    public static void refreshPageAndWaitUntilXpath(String xpath, BasePage page)
    {
        for (int a = 60; a > 0; a--)
        {
            page.refreshPage();
            try
            {
                DriverManager.getDriver().findElement(By.xpath(xpath));
                break;
            }
            catch (Exception e)
            {
            }
        }

 

    }

 

}
