package calas.CmmonFunction;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.fusesource.jansi.AnsiConsole;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import com.Tests.App;
import calas.constants.FilePathConstants;

public class CmmonFunction {
	
	public static WebDriver driver;
    public static List<WebDriver> oldDriverReferences = new ArrayList<WebDriver>();
    public static String testNgFile = null;
    public static String pathLog4j = null;
    public static Logger logger = null;
    private static Random random = new Random();
	public static void browser() {
		
		String driverPath = "driverPath";
		driverPath = System.getProperty(driverPath);
		String browser = "browser";
		browser = System.getProperty(browser);
		
		if(browser.equalsIgnoreCase("firefox")) 
		{
			System.setProperty("webdriver.gecko.driver", driverPath + "\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("fireffoxxHeadLessMode"))
		{
			System.setProperty("webdriver.gecko.driver", driverPath + "\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			driver = new FirefoxDriver(options);
		}
		else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "\\chromedriver.exe");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", "downloadFiles");
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			driver= new ChromeDriver(options);
		}
		
		else if(browser.equalsIgnoreCase("chromeheadlessMode")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "\\chromedriver.exe");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", "downloadFiles");
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			options.setExperimentalOption("prefs", prefs);
			driver= new ChromeDriver(options);
		}
		
		else if(browser.equalsIgnoreCase("IE"))
				{
			System.setProperty("webdriver.ie.driver", driverPath + "\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else if(browser.contentEquals("Edge")){
			
			System.setProperty("webdriver.edge.driver", driverPath + "\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}
		
		else {
			throw new IllegalStateException("Browser is not correct");
		
			
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	
	 private static Proxy createZapProxyConfiguration() 
     { 
         Proxy proxy = new Proxy();
          proxy.setHttpProxy("127.0.0.1" + ":" + "8090");
         proxy.setSslProxy("127.0.0.1" + ":" + "8090"); 
         return proxy; 
     }
    
    public void browserWithParameter(String environment) throws InterruptedException {
        testNgFile = environment;
        if (App.logger.isInfoEnabled()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" Argument 1 :  " + environment);

 

            }
        }
        if (App.logger.isInfoEnabled()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Starting IE");

 

            }
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Starting FFold  in 46");

 

        }
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.proxy.type", 1);
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        profile.setPreference("network.proxy.http", "proxy1.uk.webscanningservice.com");
        profile.setPreference("network.proxy.http_port", 3128);
        profile.setPreference("network.proxy.ssl", "proxy1.uk.webscanningservice.com");
        profile.setPreference("network.proxy.ssl_port", 3128);
        driver = new FirefoxDriver(dc);
        String browserName = dc.getBrowserName();
        String browserVersion = dc.getVersion();
        if (App.logger.isInfoEnabled()) {
            App.logger.info(browserName + "--- " + browserVersion + "----");

 

        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Started in 46, all profiles set");

 

        }
        Thread.sleep(5000);
    }

 

    /**
     * opening browser
     */
    public void browserWithNoParameter() throws InterruptedException {
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Starting Browser");
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Starting FFold  in 46");
        }
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.proxy.type", 1);
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        profile.setPreference("network.proxy.http", "proxy1.uk.webscanningservice.com");
        profile.setPreference("network.proxy.http_port", 3128);
        profile.setPreference("network.proxy.ssl", "proxy1.uk.webscanningservice.com");
        profile.setPreference("network.proxy.ssl_port", 3128);
        driver = new FirefoxDriver(dc);
        String browserName = dc.getBrowserName();
        String browserVersion = dc.getVersion();
        if (App.logger.isInfoEnabled()) {
            App.logger.info(browserName + "--- " + browserVersion + "----");
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Started in 46, all profiles set");
        }
        Thread.sleep(5000);
    }
    public static void openServiceFacade()
    {
        try 
        {
            browser();
        } catch (Exception e) 
        {
            App.logger.info(e.getMessage());
        }
        driver.get(FilePathConstants.serviceFacade);
    }

 

    /**
     *
     */
    public static String uniqueIndexDateSecnoTrim() {
        String dategenerated = null;
        try 
        {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ft.setTimeZone(TimeZone.getTimeZone("GMT"));
            dategenerated = ft.format(dNow);
                App.logger.info("index is --" + dategenerated);
            return dategenerated;
        } 
        catch (Exception e) 
        {
                App.logger.info("Exception while taking screenshot " + e.getMessage());
        }
        return dategenerated;
    }

 

    /**
     *
     */
    public static void clickAnElementByLinkText(String text) 
    {
        WebDriverWait wait =new WebDriverWait(driver,50);
        WebElement element=  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.linkText(text))));        
        element.click();
    }

 


    public void clickAnElementByLinkText2(String text) throws InterruptedException {
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Clicking link ---- " + text);
        }
        Thread.sleep(2000);
        driver.findElement(By.linkText(text)).click();
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Clicked link is ---- " + text);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void selectDropdown(String client, String id) throws InterruptedException 
    {
        Select oSelect = new Select(driver.findElement(By.id(id)));
        oSelect.selectByVisibleText(client);
    }

 
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
    
    public void consoleColorDisplay(String textValue, String textColor) throws InterruptedException {
        textColor = textColor.toUpperCase();
        AnsiConsole.systemInstall();
        if (textColor.equals("GREEN")) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(ansi().fg(GREEN).a(textValue).reset());

 

            }
        } else if (textColor.equals("RED")) {

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info(ansi().fg(RED).a(textValue).reset());
            }
        } else if (textColor.equals("BLUE")) {
            App.logger.info(ansi().fg(BLUE).a(textValue).reset());
        } else if (textColor.equals("YELLOW")) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(ansi().fg(YELLOW).a(textValue).reset());

 

            }
        }
        AnsiConsole.systemUninstall();
    }
    
    public String readFile(String pathname) throws IOException {
        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");
        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

 

    public static String textBetween(String filepath, String filename, String content) {
        String s = content;
        String textbetween = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);
        }
        Pattern p = Pattern.compile("<Id>(.*?)</Id>");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }
            if (App.logger.isInfoEnabled()) {
                App.logger.info(m.group(1));
                // => "3"
            }
            textbetween = (m.group(1));
            return textbetween;
        } else {

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern not  found");

 

            }
        }
        return textbetween;
    }

 

    public void replaceFileword(String path, String file, String toReplace, String replacementString) throws IOException, InterruptedException {
        if (App.logger.isInfoEnabled()) {
            App.logger.info("in replaceFileword");

 

        }
        try {
            File file1 = new File(path + file);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("File path ---" + path + file);

 

            }
            BufferedReader reader = new BufferedReader(new FileReader(file1));
            String line = "", oldtext = "";
            while ((line = reader.readLine()) != null) {
                oldtext += line + "\r\n";
            }
            reader.close();
            String newtext = oldtext.replaceAll(toReplace, replacementString);
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" replace ----" + toReplace + "---- " + replacementString);

 

            }
            // FileWriter writer = new FileWriter(path + file);
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path + file));
            Thread.sleep(3000);
            writer.write(newtext);
            Thread.sleep(3000);
            writer.close();
            Thread.sleep(3000);
        } catch (IOException ioe) {
            ioe.printStackTrace();

 

        } catch (Throwable t) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("any message" + t);

 

            }
            StackTraceElement[] s = t.getStackTrace();
            for (StackTraceElement e : s) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("\tat " + e);

 

                }
            }
            Thread.sleep(3000);
        }
    }

 

    public static String uniqueIndexDateSec() 
    {
        String dategenerated = null;
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("MM-dd HH:mm:ss");
            dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "");
            dategenerated = dategenerated.replace("-", "");
            dategenerated = dategenerated.replace(" ", "");
            if (App.logger.isInfoEnabled()) {
                App.logger.info("index is --" + dategenerated);

 

            }
            return dategenerated+random.nextInt(1000);
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception while taking screenshot " + e.getMessage());

 

            }
        }
        return dategenerated;
    }

 

    public static String uniqueIndexDateSecFolderName() {
        String dategenerated = null;
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss");
            dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "_");
            dategenerated = dategenerated.replace(" ", "_");
            dategenerated = dategenerated.replace("- ", "_");
            if (App.logger.isInfoEnabled()) {
                App.logger.info("index is --" + dategenerated);

 

            }
            return dategenerated;
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception while taking screenshot " + e.getMessage());

 

            }
        }
        return dategenerated;
    }
    public static void captureScreenshot(WebDriver driver, String screenshotName) {

    	 

        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

 

            String dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "_");
            dategenerated = dategenerated.replace("-", "_");
            TakesScreenshot ts = (TakesScreenshot) driver;
             File source = ts.getScreenshotAs(OutputType.FILE);
             FileUtils.copyFile(source, new File("C:\\log\\Screenshots\\" + screenshotName + "/" + screenshotName + dategenerated + ".png"));

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("Screenshot taken --- " + source + "/" + screenshotName + dategenerated);
            }
        } catch (Exception e) {

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception while taking screenshot " + e.getMessage());

 

            }
        }
    }

 

    public static void killExcel() throws IOException, InterruptedException {
                Runtime.getRuntime().exec("taskkill /f /t /IM EXCEL.EXE");
            Thread.sleep(5000);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Excel session killed");
        }
    }

 

    public static void killfirefox() throws IOException {
      
            for (int i = 0; i < 20; i++) {
                Runtime.getRuntime().exec("taskkill /f /t /IM firefox.exe");
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Killing firefox");
                }}
            }
 
    
 

    public static void killChrome() throws IOException {
      
            for (int i = 0; i < 20; i++) {
                Runtime.getRuntime().exec("taskkill /f /t /IM chrome.exe");
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Killing chrome");
                }
            }         
    }

 

    public static void deleteFiles(File folder) throws IOException {

        if (folder.exists()) {
            FileUtils.cleanDirectory(folder);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Files deleted in ---" + folder);

 

            }
        }

 

    }

 

    public void copyAFile(File source, File dest) throws IOException {
        try 
        {
            FileUtils.copyFile(source, dest);
            App.logger.info("File copied from -- " + source + " ------ to ---" + dest);
        } 
        catch (IOException e) 
        {
            App.logger.info("File not copied from -- " + source + " ------ to ---" + dest);
            App.logger.info(e.getMessage());

 

        }
    }
    
    public void copyFileToDirectory(String source, String dest) throws IOException 
    {
        try 
        {
            File uniqueFile=new File(makeFileNameUnique(source));
            copyAFile(new File(source), uniqueFile);
            FileUtils.copyFileToDirectory(uniqueFile, new File(dest)); 
            uniqueFile.delete();
            App.logger.info("File copied from -- " + uniqueFile + " ------ to ---" + dest);
        } 
        catch (IOException e) 
        {
            App.logger.info(e.getMessage());
        }
    }
    private String makeFileNameUnique(String fileName)
    {
        String uniqueFileName="";
        int indexOfExtension=fileName.indexOf(".");
        String extension= fileName.substring(indexOfExtension, fileName.length());
        uniqueFileName=fileName.substring(0, indexOfExtension)+uniqueIndexDateSec()+random.nextInt(100000)+extension;
        
        return uniqueFileName;
    }

 

    public void reNameFilesInFolder(String path, String renameFolder, String dateGenerated) throws IOException, InterruptedException {
        String uniquenumber = dateGenerated;
        if (App.logger.isInfoEnabled()) {
            App.logger.info(uniquenumber);

 

        }
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                File f = new File(path + listOfFiles[i].getName());
                String oldname = listOfFiles[i].getName();
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Old name is --" + oldname);
                }
                f.renameTo(new File(renameFolder + oldname + "_" + uniquenumber + ".txt"));
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("File renamed ----" + (new File(renameFolder + oldname + "_" + uniquenumber + ".txt")));

 

                }
                Thread.sleep(10000);
            }
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("conversion is done");

 

        }
    }

 

    public String uniqueindexdatesecnotrimgmt() {
        String dategenerated = null;
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ft.setTimeZone(TimeZone.getTimeZone("GMT"));
            dategenerated = ft.format(dNow);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("index is --" + dategenerated);

 

            }
            return dategenerated;
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception while taking screenshot " + e.getMessage());

 

            }
        }
        return dategenerated;
    }

 

    public static void wrtieToFile(String path, String file, String content) throws IOException {
        if (App.logger.isInfoEnabled()) {
            App.logger.info("in Wrtietofile");

 

        }
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + file), "utf-8"));
            if (App.logger.isInfoEnabled()) {
                App.logger.info("writing to file");

 

            }
            writer.write(content);
        } catch (IOException ex) {
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Wrtietofile done -----" + path + file);

 

        }
    }

 

    public void assertXMLEquals(FileReader expectedXML, FileReader actualXML, String tagToCheck) throws Exception {
        XMLUnit.setXSLTVersion("2.0");
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
        XMLUnit.setIgnoreComments(Boolean.TRUE);
        XMLUnit.setNormalizeWhitespace(Boolean.TRUE);
        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));
        if (!diff.similar()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" all differernce are start ------------" + diff.getAllDifferences());

             }
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" all differernce are end ------------");


            }
            List<Difference> lDiff = diff.getAllDifferences();
            boolean tagToCheck1 = true;
            String string = lDiff.toString();
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Tagtocheck -- " + tagToCheck + "---" + string);

            }
            tagToCheck1 = string.contains(tagToCheck);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Tagtocheck -- " + tagToCheck + "---" + string.contains(tagToCheck));

             }
            if ((!tagToCheck1)) {
                throw new IllegalStateException(
                        "Xml comparison failed because of follwoing info in tag --" + tagToCheck + "--- check the string" + string);
            }
        }
    }
    
    public static String getFileName(String path) throws IOException {
        String oldname = null;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                new File(path + listOfFiles[i].getName());
                oldname = listOfFiles[i].getName();
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Old name is --" + oldname);

 

                }
                return oldname;
            }
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("conversion is done");

 

        }
        return oldname;
    }

 

    public void deleteFilesNotFolder(File dir) {
        for (File file : dir.listFiles()) {
            if (!file.isDirectory())
                file.delete();
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Files deleted in ---" + dir);

 

        }
    }

 

    public static void deleteAllFilesInAllFolder(File file) {
        if (file.isDirectory())
            for (File f : file.listFiles())
                deleteAllFilesInAllFolder(f);
        else
            file.delete();
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Files deleted in ---" + file);

 

        }
    }

 

    public static void copyAllFilesInFolder(File source, File dest) throws IOException {
        try {
            FileUtils.copyDirectory(source, dest);

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("File copied from -- " + source + " ------ to ---" + dest);

 

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 

    public static void renameFile(String path, String renameFolder, String toReName, String reNamed) throws IOException {
        if (App.logger.isInfoEnabled()) {
            App.logger.info("in renmaefile----" + path + toReName);

 

        }
        File oldfile = new File(path + toReName);
        File newfile = new File(renameFolder + reNamed);

 

        if (oldfile.renameTo(newfile)) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("File renamed to ----" + renameFolder + reNamed);

 

            }
        } else {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Sorry! the file can't be renamed");

 

            }
        }
    }
    
    public String readAndreplaceValuesInAllRowsInTextFile(String file, ArrayList<Integer> columnNumber,  ArrayList<String> columnValues) throws IOException
    {
        File fileToModify= new File(file);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));    
        ArrayList<String> allLinesInFile= new ArrayList<String>();
        ArrayList<String> allLinesInFileWithUpdatedValues= new ArrayList<String>();

 

        String fileLine = null;
        String headers= reader.readLine();
         while((fileLine =reader.readLine())!=null)
         {
             allLinesInFile.add(fileLine);
         }        

 


        for(int i =0 ; i< allLinesInFile.size() ; i++)
        {
            List<String> lineWithSplittedValues;
            lineWithSplittedValues=Arrays.asList((allLinesInFile.get(i)).split(",",-1));
            for(int y= 0 ; y<columnValues.size(); y++)
            {
                lineWithSplittedValues.set(columnNumber.get(y)-1, columnValues.get(y));
            }
            allLinesInFileWithUpdatedValues.add(String.join(",", lineWithSplittedValues));
        }

 

        allLinesInFileWithUpdatedValues.add(0, headers);
        reader.close();
        
        String updatedContent=String.join("\r\n", allLinesInFileWithUpdatedValues);
        updatedContent=updatedContent+"\r\n";

 

        return updatedContent;
    }
    
    public String readAndReplaceTagValuesFromXmlMessage(String file,ArrayList<String> tagNames, ArrayList<String> tagValues) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line =reader.readLine())!=null)
        {
            stringBuilder.append(line+"\r\n");  
        }
        
        String content=stringBuilder.toString();
        Map<String, Integer> occurencesCount = new HashMap<String, Integer>();
        for(int i=0; i< tagNames.size(); i++)
        {
            Pattern p = Pattern.compile("<"+tagNames.get(i)+">([^<|>|\\s]*)</"+tagNames.get(i)+">",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(stringBuilder.toString());
            int occurence = occurencesCount.get(tagNames.get(i))!=null? occurencesCount.get(tagNames.get(i)) : 0;
            occurencesCount.put(tagNames.get(i), occurence+1);
            for(int j = 0; j<occurence; j++) {m.find();}
            if(m.find())
            {
                String matchedTag = m.group(0);
                content=content.replace(matchedTag,"<"+tagNames.get(i)+">"+tagValues.get(i)+"</"+tagNames.get(i)+">");
            }
        }              
        reader.close();
        
        stringBuilder= new StringBuilder();
        stringBuilder.append(content);
        int lastIndex=stringBuilder.lastIndexOf("\r\n");
        content=stringBuilder.replace(lastIndex, lastIndex+"\r\n".length(), "").toString();

 

        return content;
    }
    
    public String replaceTagValuesFromXmlMessage(String file,ArrayList<String> tagNames, ArrayList<String> tagValues) throws IOException
    {
        String content =readAndReplaceTagValuesFromXmlMessage(file, tagNames,tagValues);
        createFileWithContent(content,file);

 

        return content;
    }

 

    public String readAndReplaceTagValuesIn15022Message(String file,ArrayList<String> tagNames, ArrayList<String> tagValues, boolean writeToFile) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line =reader.readLine())!=null)
        {
            stringBuilder.append(line+"\r\n");  
        }
        String content=stringBuilder.toString();
        for(int i=0; i< tagNames.size(); i++)
        {
            Pattern p = Pattern.compile(tagNames.get(i)+"(.*?)(?=(:|}))",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(stringBuilder.toString());
            if(m.find())
            {
                content=p.matcher(content).replaceFirst(tagNames.get(i)+tagValues.get(i)+"\r\n");
            }
        }              
        reader.close();
        
        stringBuilder= new StringBuilder();
        stringBuilder.append(content);
        int lastIndex=stringBuilder.lastIndexOf("\r\n");
        content=stringBuilder.replace(lastIndex, lastIndex+"\r\n".length(), "").toString();
        
        if(writeToFile){createFileWithContent(content, file);}

 

        return content;
    }
    
    public ArrayList<String> getTagValuesFromXMLMessage(ArrayList<String> tagNames, String filepath) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<String> tagValues= new ArrayList<String>();
        String line = null;

 

        while((line =reader.readLine())!=null)
        {
            stringBuffer.append(line+"\r\n");      
        }

 

        Map<String, Integer> occurencesCount = new HashMap<String, Integer>();
        for(String tagname: tagNames)
        {
            Pattern p = Pattern.compile("<"+tagname+">([^<|>|\\s]*)</"+tagname+">",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(stringBuffer.toString());
            int occurence = occurencesCount.get(tagname)!=null? occurencesCount.get(tagname) : 0;
            occurencesCount.put(tagname, occurence+1);
            for(int i = 0; i<occurence; i++) {m.find();}
            if(m.find())
            {
                String matchedTag = m.group(1);
                tagValues.add(matchedTag);
            }
        }    
        reader.close();
        return tagValues;
    }

 

    public ArrayList<String> getTagValuesFrom15022Message(ArrayList<String> tagNames, String filepath) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<String> tagValues= new ArrayList<String>();
        String line = null;

 

        while((line =reader.readLine())!=null)
        {
            stringBuffer.append(line+"\r\n");      
        }

 

        for(String tagname: tagNames)
        {
            Pattern p = Pattern.compile(tagname+"(.*?)(:|})",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(stringBuffer.toString());
            if(m.find())
            {
                String s = m.group(1);
                tagValues.add(s);
            }
        }    
        reader.close();
        return tagValues;
    }
    
    public ArrayList<String> getColumnValuesFromSingleRowInTextFile(ArrayList<Integer> columnNumber, String filepath) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        
        ArrayList<String> columnValues= new ArrayList<String>();

 

        String headers = reader.readLine();
        List<String> listWithSplittedColumns = Arrays.asList((reader.readLine()).split(",",-1));

 

        for(int i=0; i<columnNumber.size();i++)
        {
            columnValues.add(listWithSplittedColumns.get(columnNumber.get(i)-1));
        }    
        reader.close();
        return columnValues;
    }
    
    public List<String> getAllColumnValuesFromTextFileBySpecifyingRowNumber(String filepath, int row) throws IOException
    {
        File fileToModify = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        ArrayList<List<String>> rowAndColumns = new ArrayList<List<String>>();

 

        String fileLine = null;
        while((fileLine =reader.readLine())!=null)
        {
            rowAndColumns.add(Arrays.asList((fileLine.split(",",-1))));
        }        
        reader.close();
        
        return rowAndColumns.get(row-1);
    }
    
    //retrieve 1 cell value from all the rows in a file with option to ignore headerRow
    public List<String> get1ColumnValueFromAllTheRowsFromATextFile(String filepath, int column, boolean ignoreHeaderRow) throws IOException
    {
        File fileToModify = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        List<String> columnnValues = new ArrayList<String>();

 

        String fileLine = null;
        while((fileLine =reader.readLine())!=null)
        {

 

            columnnValues.add(fileLine.split(",",-1)[column-1]);
        }    
        if(ignoreHeaderRow)
        {
            columnnValues.remove(0);
        }
        reader.close();
        
        return columnnValues;
    }
    
	/*
	 * public String getStringxml(String path, String file, String node, String
	 * tagName) throws ParserConfigurationException, SAXException, IOException {
	 * String dataintag = null; try { File fXmlFile = new File(path + file);
	 * DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	 * DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); Document doc =
	 * dBuilder.parse(fXmlFile); doc.getDocumentElement().normalize(); if
	 * (App.logger.isInfoEnabled()) { App.logger.info("Root element :" +
	 * doc.getDocumentElement().getNodeName());
	 * 
	 * 
	 * 
	 * } org.w3c.dom.NodeList nList = doc.getElementsByTagName(node); if
	 * (App.logger.isInfoEnabled()) {
	 * App.logger.info("----------------------------");
	 * 
	 * 
	 * 
	 * } for (int temp = 0; temp < nList.getLength(); temp++) { Node nNode =
	 * nList.item(temp); if (App.logger.isInfoEnabled()) {
	 * App.logger.info("\nCurrent Element :" + nNode.getNodeName());
	 * 
	 * 
	 * 
	 * } if (nNode.getNodeType() == Node.ELEMENT_NODE) { Element eElement =
	 * (Element) nNode; dataintag =
	 * eElement.getElementsByTagName(tagName).item(temp).getTextContent(); if
	 * (App.logger.isInfoEnabled()) { App.logger.info("Tag data is  : " +
	 * dataintag);
	 * 
	 * 
	 * 
	 * } if (App.logger.isInfoEnabled()) { App.logger.info("Tag data is  : " +
	 * eElement.getElementsByTagName(tagName).item(temp).getTextContent());
	 * 
	 * 
	 * 
	 * } return dataintag; } } } catch (Exception e) { e.printStackTrace(); } return
	 * dataintag; }
	 * 
	 */

    public void listFileNames(String folder) throws IOException {
        File folder1 = new File(folder);
        File[] listOfFiles = folder1.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("File " + listOfFiles[i].getName());

 

                }
            }
        }
    }

 

    public void checkTextExistAllFolders(String texttosearch, String path) throws IOException {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String textcheck = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".xml")) {
                FileUtils.readFileToString(file);
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("file to read --- " + file);

 

                }
                String word = "";
                int val = 0;
                while (!word.matches(texttosearch)) {
                    word = texttosearch;
                    Scanner file1 = new Scanner(file);
                    while (file1.hasNextLine()) {
                        String line = file1.nextLine();
                        if (line.indexOf(word) != -1) {
                            if (App.logger.isInfoEnabled()) {
                                App.logger.info(texttosearch + "Word EXISTS in the file ---" + file);

 

                            }
                            val = 1;
                            textcheck = "Word EXISTS in the file";

 

                            throw new IllegalStateException("Xml comparison failed because of follwoing info in tag --" + file);
                        } else {
                            val = 0;
                            continue;
                        }
                    }
                    if (val == 0) {
                        if (App.logger.isInfoEnabled()) {
                            App.logger.info("Word does not exist---" + file);

 

                        }
                        textcheck = "Word does not exist";
                    }

 

                    if (App.logger.isInfoEnabled()) {
                        App.logger.info(texttosearch + "is present in " + file);

 

                    }
                    Assert.assertEquals(textcheck, "Word does not exist");
                }
            }
        }
    }

 

    public List<String> assertXMLEqualsreturn(FileReader expectedXML, FileReader actualXML, String tagToCheck) throws Exception {
        List<String> difference = null;
        XMLUnit.setXSLTVersion("2.0");
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
        XMLUnit.setIgnoreComments(Boolean.TRUE);
        XMLUnit.setNormalizeWhitespace(Boolean.TRUE);
        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));
        if (!diff.similar()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" all differernce are start ------------" + diff.getAllDifferences());

 

            }
            difference = diff.getAllDifferences();
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" all differernce are end ------------");

 

            }
            List<Difference> lDiff = diff.getAllDifferences();
            boolean tagToCheck1 = true;
            String string = lDiff.toString();
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Tagtocheck -- " + tagToCheck + "---" + string);

 

            }
            tagToCheck1 = string.contains(tagToCheck);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Tagtocheck -- " + tagToCheck + "---" + string.contains(tagToCheck));

 

            }
            if ((!tagToCheck1)) {
                throw new IllegalStateException(
                        "Xml comparison failed because of follwoing info in tag --" + tagToCheck + "--- check the string" + string);
            }
            return difference;
        }
        return difference;
    }
    
    public List checktextexistallfoldersretrunfilename(String texttosearch, String path) throws IOException, InterruptedException {
        List<String> myList = null;
        File dir = new File(path);
        if (App.logger.isInfoEnabled()) {
            App.logger.info("dir.getName() ---" + dir.getName());

 

        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("dir.getName() ---" + dir.getAbsolutePath());

 

        }

 

        Thread.sleep(2000);
        if (dir.exists()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Checking directory ---" + dir.getName());

 

            }
        }
        File[] listOfFiles = dir.listFiles();
        for (File f : listOfFiles) {
            if (f.exists() && f.isFile()) {
                File file = f;// listOfFiles[1];
                if (file.isDirectory()) {
                    if (App.logger.isInfoEnabled()) {
                        App.logger.info(" file is folder");

 

                    }
                    continue;
                }
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String s;
                String keyword = texttosearch;
                while ((s = br.readLine()) != null) {
                    if (s.contains(keyword)) {
                        if (App.logger.isInfoEnabled()) {
                            App.logger.info(texttosearch + "----found in----" + path + file.getName() + "-----" + +getLineNumber());

 

                        }
                        myList = new ArrayList<String>(Arrays.asList(file.getName()));
                    } else {

 

                    }

 

                }
            }
        }
        return myList;
    }

 

    public static int countLines(File filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

 

    public static void copyFiles(String source, String dest) throws IOException {
        File dir1 = new File(source);
        new File(dest);
        if (dir1.isDirectory()) {
            File[] content = dir1.listFiles();
            for (int i = 0; i < content.length; i++) {
                Path from = Paths.get(source + content[i].getName());
                Path tO = Paths.get(dest + content[i].getName());
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("From ---" + from + "To ---" + tO);

 

                }
                CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES };
                if (!Files.isDirectory(from))
                    Files.copy(from, tO, options);
            }

 

        }
    }

 

    public static void stringToXML1(String yourXML, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        try {
            out.write(yourXML);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

 

    public static int countXMLElement(String elementname, String file) throws ParserConfigurationException, SAXException, IOException {
        int totalelement = 0;
        String filepath = file;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder(); 
        Document doc = docBuilder.parse(filepath);
        org.w3c.dom.NodeList list = doc.getElementsByTagName(elementname);
        totalelement = list.getLength();
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Total of elements : " + list.getLength());

 

        }
        return totalelement;
    }

 

    public static void stringToDom(String xmlSource, File file) throws IOException {
        java.io.FileWriter fw = new java.io.FileWriter(file);
        fw.write(xmlSource);
        fw.close();
    }
    
    public File replaceFieldInFile(String fileName, String reference, int column) throws IOException {
        File fileToModify = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));

 

        String headers = reader.readLine();

 

        String values = reader.readLine();
        List<String> list2 = Arrays.asList(values.split(","));

 

        // replace OrderReference with unique value
        list2.set(column - 1, reference);
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Column " + column + " has been replaced with value " + reference);

 

        }

 

        FileWriter writer = new FileWriter(fileToModify);
        writer.write(headers);
        writer.write("\r\n"); // appends CRLF at the end of the lines
        String listString = String.join(",", list2);
        writer.write(listString);
        writer.write("\r\n");
        reader.close();
        writer.close();

 

        return fileToModify;
    }

 

    // replace one element with a value
    public void replaceBetweenXMLTag(String filepath, String reference, String tagname) throws IOException {

 

        File fileToModify = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

 

        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line + "\r\n");
        }

 

        Pattern p = Pattern.compile("<" + tagname + "[^>]*>(.*?)</" + tagname + ">", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        String content = stringBuffer.toString();
        String newReference = "<" + tagname + ">" + reference + "</" + tagname + ">";
        String pattern = p.matcher(content).replaceAll(newReference);

 

        FileWriter writer = new FileWriter(fileToModify);
        writer.write(pattern);
        writer.write("\r\n");
        writer.close();
        reader.close();
    }
    
    public void appendLineToEndOfTextFile(String filepath, String lineToBeAppended) throws IOException 
    {
        File fileToModify = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) 
        {
            stringBuffer.append(line + "\r\n");
        }
        stringBuffer.append(lineToBeAppended + "\r\n");
        FileWriter writer = new FileWriter(fileToModify);
        writer.write(stringBuffer.toString());
        writer.close();
        reader.close();
    }
    
    public void removeLineFromTextFileIfExists(String filepath, String lineToBeDeleted) throws IOException 
    {
        File fileToModify = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) 
        {
            if(!line.equals(lineToBeDeleted))
            {
                stringBuffer.append(line + "\r\n");
            }
        }
        FileWriter writer = new FileWriter(fileToModify);
        writer.write(stringBuffer.toString());
        writer.close();
        reader.close();
    }

 

    // replacing 2 elements with same value
    public void replaceBetweenXMLTag2Elements(String filepath, String reference, String tagname, String tagname2) throws IOException {

 

        File fileToModify = new File(filepath);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

 

        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line + "\r\n");
        }

 

        Pattern p = Pattern.compile("<" + tagname + "[^>]*>(.*?)</" + tagname + ">", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

 

        Pattern p2 = Pattern.compile("<" + tagname2 + "[^>]*>(.*?)</" + tagname2 + ">", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

 

        String content = stringBuffer.toString();
        String newReference = "<" + tagname + ">" + reference + "</" + tagname + ">";
        String newReference2 = "<" + tagname2 + ">" + reference + "</" + tagname2 + ">";
        String pattern = p.matcher(content).replaceAll(newReference);
        String pattern2 = p2.matcher(pattern).replaceAll(newReference2);

 

        FileWriter writer = new FileWriter(fileToModify);
        writer.write(pattern2);
        writer.write("\r\n");
        writer.close();
        reader.close();
    }

 

    // get value in tag
    public String getValueBetweenXMLTag(File fileToModify, String tagname) throws IOException 
    {

 

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileToModify));

 

            StringBuffer stringBuffer = new StringBuffer();
            String line = null;

 

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            String content = stringBuffer.toString();

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info(stringBuffer.toString());

 

            }

 

            int startIndex = content.indexOf("<" + tagname + ">") + tagname.length() + 2; // adding on
                                                                                            // length of tag
                                                                                            // and the symbols
            int endIndex = content.indexOf("</" + tagname + ">");

 

            String value = content.substring(startIndex, endIndex);

 

            reader.close();
            return value;
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
        return null;
    }

 

    public static String uniqueIndex() {

 

        String dategenerated = null;
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
            // SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd
            // HH:mm:ss");

 

            dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "");
            dategenerated = dategenerated.replace("-", "");
            dategenerated=dategenerated.replace(" ", "");

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("index is --" + dategenerated);

 

            }
            return dategenerated;
        } catch (Exception e) {

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception while taking screenshot " + e.getMessage());

 

            }
        }
        App.logger.info(uniqueIndex());
        return dategenerated;
 

    }
    
    public String readLine(int lineNumber, String filepath, String filename) {
        String line = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Reading file in  ---readLine " + filepath + "---" + filename);

 

        }
        File fileFeatures = new File(filepath + filename);
        // File fileFeatures = new
        // File("Homework1AdditionalFiles/jEdit4.3/jEdit4.3ListOfFeatureIDs.txt");
        try {
            line = FileUtils.readLines(fileFeatures).get(lineNumber);

 

            if (App.logger.isInfoEnabled()) {
                App.logger.info("Read ---" + line);

 

            }

 

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return line;

 

    }

 

    public static void replaceFileStringtxt(String path, String file, String toReplace, String replacementString) throws InterruptedException, IllegalArgumentException, IOException {
          
        if (App.logger.isInfoEnabled()) {
            App.logger.info("in replaceFileStringtxt");

 

        }
        Path path1 = Paths.get(path + file);
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Files to read  ---" + path + file);

 

        }
        Charset charset = StandardCharsets.UTF_8;
        // change on 16/08/2017
        checkTextExists(toReplace, path+file);
        String content = new String(Files.readAllBytes(path1), charset);
        Thread.sleep(5000);
        content = content.replaceAll(toReplace, replacementString);
        Thread.sleep(5000);
        if (App.logger.isInfoEnabled()) {
            App.logger.info(toReplace + "--- replaced to ---" + replacementString + " --- in -- " + path + file);

 

        }
        Thread.sleep(5000);
        Files.write(path1, content.getBytes(charset));

 

        Thread.sleep(5000);

 

        killtxtfile();

 

    }
    
    public static boolean replaceTextInFile(String filePath, String toReplace, String replacementString) throws IOException, InterruptedException 
    {
        if(!checkTextExists(toReplace, filePath))
        {
            return false;
        }
        Path path1 = Paths.get(filePath);
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path1), charset);
        content = content.replace(toReplace, replacementString);
        Files.write(path1, content.getBytes(charset));
        killtxtfile();
        return true;
    }
    
    public boolean replaceRowInFile(String filePath, String replacementText, int row) 
    {
        try
        {
            File fileToModify= new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(fileToModify));    
            ArrayList<String> lines= new ArrayList<String>();
            String fileLine = null;
            while((fileLine =reader.readLine())!=null)
            {
                lines.add(fileLine);
            }        

 

            lines.set(row-1, replacementText);
            reader.close();
            FileWriter fw = new FileWriter(fileToModify);
            final BufferedWriter bw = new BufferedWriter(fw);    

 

            lines.forEach( item -> 
            {
                try 
                { 
                    bw.write(item);
                    bw.write("\r\n");
                } 
                catch (IOException e) 
                {e.printStackTrace();

 

                }}
            );
            
            bw.close();
            fw.close();
        }
        catch(Exception e)
        {
            App.logger.info(e.getMessage());
            return false;
        }
        return true;

 

    }
    
    public void createFileWithContent(String content, String filePath)
    {
        try
        {
            File yourFile = new File(filePath);
            yourFile.createNewFile(); // if file already exists will do nothing 
            
            FileWriter writer = new FileWriter(yourFile);
            writer.write(content);
            writer.close();
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public String getFileContentWithoutSpaces(String file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while((line =reader.readLine())!=null)
        {
             stringBuffer.append(line);
        }
        String content=stringBuffer.toString();    
        reader.close();
        return content.replaceAll("\\s", "");
    }
    
    public String getFileContentWithoutChanging(String file) 
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line =reader.readLine())!=null)
            {
                stringBuilder.append(line+"\r\n");
            }
            String content=stringBuilder.toString();    
            reader.close();
            
            stringBuilder= new StringBuilder();
            stringBuilder.append(content);
            int lastIndex=stringBuilder.lastIndexOf("\r\n");
            content=stringBuilder.replace(lastIndex, lastIndex+"\r\n".length(), "").toString();
            
            return content;
        }
        catch (Exception e) 
        {
            App.logger.info(e.getMessage());
        }
        return null;
    }
    
    public boolean replaceEnvironmentInBatchFile(String bathFilePath)
    {
        boolean found=false;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(bathFilePath));
            StringBuilder builder = new StringBuilder();
            String line = null;

 

            while((line =reader.readLine())!=null)
            {
                builder.append(line+"\r\n");                            
            }
            String content=builder.toString();

 

            Pattern p = Pattern.compile("(qa-azuks-ctn[0-9]|qa-azuks-order[0-9])",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(builder.toString());
            if(m.find())
            {
                found=true;
                content=p.matcher(content).replaceAll(App.dbEnv);
            }

 

            reader.close();

 

            FileWriter fw = new FileWriter(new File(bathFilePath));
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return found;
    }
    public void runCmdProcess(String command) throws Exception
    {
        Process process = Runtime.getRuntime().exec("cmd.exe /c start "+ command);
        process.waitFor();
        process.destroyForcibly();
    }
    
    public void runProcess(String command) throws Exception
    {
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        process.destroyForcibly();
    }
    
    public static boolean checkTextExists(String textToSearch, String filePath) throws FileNotFoundException, IllegalArgumentException 
    {    
        Scanner file = new Scanner(new File(filePath));
        try
        {
            while (file.hasNextLine()) 
            {
                String line = file.nextLine();
                if (line.contains(textToSearch)) 
                {
                    return true;
                }

 

            }
        }
        finally 
        {
            file.close();
        }
        return false;
    }

 

    public static void killtxtfile() throws IOException {
    	 Runtime.getRuntime().exec("taskkill /f /t /IM notepad.EXE");
    }

 

    public String prettyFormat(String input, String indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

 

            // transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indent);
            // transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new IllegalStateException(e); // simple exception handling, please
            // review it
        }
    }

 

    public void clickAnElementByLinkTextWithException(String text) throws InterruptedException {
        Thread.sleep(10000);
        try {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception found");

 

            }
            // Add a wait timeout before this statement to make
            // sure you are not checking for the alert too soon.
            Alert alt = driver.switchTo().alert();
            alt.accept();
        } catch (NoAlertPresentException noe) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("No Exception found");

 

            }
        }
        new WebDriverWait(driver, 90);
        // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ctl00_ctl00_ModalProgress_backgroundElement")));
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pauseButton")));
        driver.findElement(By.id("pauseButton")).click();
        try {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception found");

 

            }
            // Add a wait timeout before this statement to make
            // sure you are not checking for the alert too soon.
            Alert alt = driver.switchTo().alert();
            alt.accept();
        } catch (NoAlertPresentException noe) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("No Exception found");

 

            }
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Clicking link ---- " + text);

 

        }
        try {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Exception found");

 

            }

 

            Alert alt = driver.switchTo().alert();
            alt.accept();
        } catch (NoAlertPresentException noe) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("No Exception found");

 

            }
        }

 

        // WebDriverWait wait = new WebDriverWait(driver, 90);
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(text)));
        driver.findElement(By.linkText(text)).click();
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Clicked link is ---- " + text);

 

        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

 

    }

 

    public void buttonClickById(String buttonvalue) {
        driver.findElement(By.id(buttonvalue)).click();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(buttonvalue)));

 

    }

 

    public void waitForText(String text) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (App.logger.isInfoEnabled()) {
            App.logger.info("waiting for link ---- " + text);

 

        }

 

        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(text)));

 

    }
    public void compareFontColour(String id) {
        // Locate text string element to read It's font properties.
        WebElement text = driver.findElement(By.xpath(id));

 

        // Read font-size property and print It In console.
        String fontSize = text.getCssValue("font-size");
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Font Size -> " + fontSize);

 

        }

 

        // Read color property and print It In console.
        String fontColor = text.getCssValue("color");
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Font Color -> " + fontColor);

 

        }

 

        // Read font-family property and print It In console.comparefont
        String fontFamily = text.getCssValue("font-family");
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Font Family -> " + fontFamily);

 

        }

 

        // Read text-align property and print It In console.
        String fonttxtAlign = text.getCssValue("text-align");
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Font Text Alignment -> " + fonttxtAlign);

 

        }

 

        Assert.assertEquals(fontColor, "rgba(0, 128, 0, 1)");
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Font color as expected");

 

        }
    }

 

    public void explicitWaitUntilAnElementDisappears(int time, String type, String locator) throws InterruptedException {
        /*
         * This one is useful to click on something that isn't clickable due to page
         * loading. The disappear is usually the last element used which doesnt appear
         * any longer
         */
        By disappear = null;
        if (type.equals("xpath")) {
            disappear = By.xpath(locator);
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Waiting XPATH : " + locator + " to disappear");

 

                }
            }
        }

 

        if (type.equals("id")) {
            disappear = By.id(locator);
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Waiting ID: " + locator + " to disappear");

 

                }
            }
        }

 

        if (type.equals("css")) {
            disappear = By.cssSelector(locator);
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Waiting CSS: " + locator + " to disappear");

 

                }
            }
        }
        WebDriverWait wait = new WebDriverWait(driver, time);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(disappear));

 

    }

 

    public static void createUser(String firstName, String surName, String userName, String adminType) throws InterruptedException {

 

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("Surname")).sendKeys(surName);
        driver.findElement(By.id("Username")).sendKeys(userName);
        // driver.findElement(By.id("Email")).sendKeys(userName + "@test.com");
        driver.findElement(By.id("Email")).sendKeys("shanthi.venkatesh@calastone.com");
        // driver.findElement(By.id("Password")).sendKeys("Test123!");
        // driver.findElement(By.id("ConfirmPassword")).sendKeys("Test123!");
        selectDropdown(adminType, "GlobalAdminType");
        driver.findElement(By.cssSelector("input[value=\"Save\"]")).click();
        String caturedtext = driver.findElement(By.id("page-title")).getText();
        if (App.logger.isInfoEnabled()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" Username is  ---" + userName);
                App.logger.info(" Captured text is ---" + caturedtext);

 

            }
        }
        Assert.assertEquals(caturedtext, "User account created: " + firstName + " " + surName + " (" + userName + ")");

 

    }

 

    public static void createUser4eyes(String firstName, String surName, String userName, String adminType) throws InterruptedException {

 

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("Surname")).sendKeys(surName);
        driver.findElement(By.id("Username")).sendKeys(userName);
        driver.findElement(By.id("Email")).sendKeys("QA@calastone.com");
        selectDropdown(adminType, "GlobalAdminType");
        driver.findElement(By.cssSelector("input[value=\"Save\"]")).click();
        Thread.sleep(10000);
        String caturedtext = driver.findElement(By.id("user-message")).getText();
        if (App.logger.isInfoEnabled()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" Created user  ---" + userName);
                App.logger.info(" Captured text is ---" + caturedtext);

 

            }
        }
        Assert.assertEquals(caturedtext, "Your request has been submitted. Pending approval.");

 

    }

 

    public static void throwRuntimeException() {
        throw new IllegalStateException(" expected data is not found");
    }
    public static void killProcess() throws IOException {
    	  Runtime.getRuntime().exec("taskkill /f /t /IM PgpEncryptor.exe");
    }

 

    public static void fileExist(String file1) {
        File file = new File(file1);

 

        if (file.exists()) {
            file.length();
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("File exist : test passed " + file1);

 

                }
            }
        } else {
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("File does not exists! -  test fails" + file1);

 

                }
            }
            throwRuntimeExceptionmessage("File does not exists!" + file1);
        }
    }

 

    public static void throwRuntimeExceptionmessage(String messagetodisplay) {
        throw new IllegalStateException(" messagetodisplay");
    }

 

    public static void fileNotExist(String file1) {
        File file = new File(file1);

 

        if (file.exists()) {
            double bytes = file.length();
            double kilobytes = (bytes / 1024);
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("File exist : test failed" + kilobytes);

 

                }
            }
            throwRuntimeExceptionmessage("Fileexists!" + file1);
        } else {
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("File does not exists! - test passed" + file1);

 

                }
            }
        }
    }

 

    public static void waitFor(int counter) throws InterruptedException {

 

        for (int i = counter; i > 0; i--) {
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Waiting for --- " + i + "min");

 

                }
            }
            Thread.sleep(60000);
        }
    }

 

	/*
	 * public static void copyAllFilesAndDelte(String source, String dest) throws
	 * IOException, InterruptedException {
	 * 
	 * 
	 * 
	 * Path sourceDir = Paths.get(source); Path destinationDir = Paths.get(dest);
	 * try (DirectoryStream<Path> directoryStream =
	 * Files.newDirectoryStream(sourceDir)) { for (Path path : directoryStream) {
	 * sourceDir.resolve(path.getFileName()).toFile(); File d2 =
	 * destinationDir.resolve(path.getFileName()).toFile(); File oldFile =
	 * path.toFile(); if (oldFile.renameTo(d2)) { if (App.logger.isInfoEnabled()) {
	 * if (App.logger.isInfoEnabled()) { App.logger.info("File moved from ---" +
	 * sourceDir + "--- to ---" + destinationDir);
	 * 
	 * 
	 * 
	 * } } } else { if (App.logger.isInfoEnabled()) { if
	 * (App.logger.isInfoEnabled()) { App.logger.info("Not Moved");
	 * 
	 * 
	 * 
	 * } } } } } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * }
	 */

 

    public static void compareDates(String d1, String d2) throws Exception, IllegalArgumentException {

 

        {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy HH:mm:ss");
                Date date1 = sdf.parse(d1);
                Date date2 = sdf.parse(d2);
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Date1---" + sdf.format(date1));

 

                }
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Date2---" + sdf.format(date2));

 

                }
                if (date1.after(date2)) {
                    if (App.logger.isInfoEnabled()) {
                        App.logger.info(date1 + "--- is after  ---" + date2 + "--- as expected test passed");

 

                    }
                }
                if (date1.before(date2)) {
                    throw new IllegalStateException("Date1 is before Date2 test failed");
                }
                if (date1.equals(date2)) {
                    throw new IllegalStateException("Date1 is equal Date2 test failed");
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

 

    public static String getFileLastModified(String path, String fileName) throws IOException {
        String oldName = null;
        File file = new File(path, fileName);
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Before Format : " + file.lastModified());

 

        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (App.logger.isInfoEnabled()) {
            App.logger.info("After Format : " + sdf.format(file.lastModified()));

 

        }
        oldName = sdf.format(file.lastModified());
        if (App.logger.isInfoEnabled()) {
            App.logger.info("conversion is done");

 

        }
        return oldName;
    }
    public static String uniqueIndex1() {

    	 

        String dategenerated = null;
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
            dategenerated = ft.format(dNow);
            dategenerated = dategenerated.replace(":", "");
            dategenerated = dategenerated.replace("-", "");
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("index is --" + dategenerated);

 

                }
            }
            return dategenerated;
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Exception while taking screenshot " + e.getMessage());

 

                }
            }
        }
        return dategenerated;

 

    }

 

    public void clickByXpath(String xpath) 
    {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
    }

 

    public void enterTextById(String id, String value) throws InterruptedException {
        waitForPageLoaded(driver);
            WebElement element = waitAndFindElement(By.id(id));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", element);
            element.clear();
            element.sendKeys(value);
    }

 

    public void enterTextByXpath(String xpath, String value) throws InterruptedException 
    {
        WebElement element = waitAndFindElement(By.xpath(xpath));
        element.clear();
        element.sendKeys(value);
    }

 

    public static String textBetweencsv(String filepath, String filename, String content) {
        String s = content;
        String textBetweencsv = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);

 

        }
        ;
        Pattern p = Pattern.compile("t(.*?)t");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }

 

            textBetweencsv = (m.group(1));
            return textBetweencsv;
        } else {
            if (App.logger.isInfoEnabled()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("pattern not  found");

 

                }
            }
        }
        return textBetweencsv;

 

    }

 

    public static String textBetweenOrdrRef(String filepath, String filename, String content) {
        String s = content;
        String textBetweencsv = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);

 

        }
        ;
        Pattern p = Pattern.compile("<OrdrRef>(.*?)</OrdrRef>");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }

 

            textBetweencsv = (m.group(1));
            return textBetweencsv;
        } else {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern not  found");

 

            }
        }
        return textBetweencsv;

 

    }

 

    public static String textBetweenId(String filepath, String filename, String content) {
        String s = content;
        String textBetweencsv = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);

 

        }

 

        Pattern p = Pattern.compile("<Id>(.*?)</Id>");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }

 

            textBetweencsv = (m.group(1));
            return textBetweencsv;
        } else {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern not  found");

 

            }
        }
        return textBetweencsv;

 

    }
    
    public static String textBetweenAPIR(String filepath, String filename, String content) {
        String s = content;
        String textBetweencsv = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);

 

        }

 

        Pattern p = Pattern.compile("APIROrder(.*?)R");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }

 

            textBetweencsv = (m.group(1));
            return textBetweencsv;
        } else {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern not  found");

 

            }
        }
        return textBetweencsv;

 

    }
    public static String textBetweenAcc(String filepath, String filename, String content) {
        String s = content;
        String textBetweencsv = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);

 

        }
        ;
        Pattern p = Pattern.compile("<AcctId>(.*?)</AcctId>");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }

 

            textBetweencsv = (m.group(1));
            return textBetweencsv;
        } else {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern not  found");

 

            }
        }
        return textBetweencsv;

 

    }

 

    public void quitBrowser() 
    {
        try
        {
            App.logger.info("## Quitting the browser");
            driver.quit();
        }
        catch (Exception e) 
        {
            App.logger.info(e.getCause());
        }
    }

 

    public void quitAllBrowsers() 
    {
        try
        {
            App.logger.info("## Quitting all browsers");
            driver.quit();
            oldDriverReferences.forEach(e -> e.quit());
        }
        catch (Exception e) 
        {
            App.logger.info(e.getCause());
        }
    }

 

    public void copyFile(String path, String newfilepath) throws IOException {
        System.out.println(path + " ---- " + newfilepath);
        try {
               FileUtils.copyFile(new File(path), new File(newfilepath));
               Thread.sleep(7000);
        } catch (Throwable t) {
               if (App.logger.isInfoEnabled()) {
                     App.logger.info("*****  File not copied  ********" + t);

 

               }
               StackTraceElement[] s = t.getStackTrace();
               for (StackTraceElement e : s) {
                     if (App.logger.isInfoEnabled()) {
                            App.logger.info(e);
                     }
               }
        }
    }

 

    public File replaceCellInCSVFileBySpecifyingRowNumber(String fileName, String reference, int row, int column) throws IOException 
    {
        File fileToModify = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(fileToModify));
        ArrayList<List<String>> rowAndColumns = new ArrayList<List<String>>();

 

        String fileLine = null;
        while((fileLine =reader.readLine())!=null)
        {
            rowAndColumns.add(Arrays.asList((fileLine.split(",",-1))));
        }        
        reader.close();

 

        rowAndColumns.get(row-1).set(column-1, reference);     

 

        StringBuilder stringBuilder = new StringBuilder();     
        for(List<String> singleRow : rowAndColumns)
        {
            stringBuilder.append(String.join(",", singleRow));
            stringBuilder.append("\r\n");
        }

 


        try 
        {
            FileWriter fw = new FileWriter(fileToModify);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stringBuilder.toString());
            bw.close();
            fw.close();
        } 
        catch (Exception e) 
        {
            App.logger.info(e.getMessage());
        }
        return fileToModify;

 

    }

 

    public boolean existsElement(String linkText) {
        try {
            driver.findElement(By.linkText(linkText));
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(linkText + "---- is not present");

 

            }
            return false;
        }

 

        return true;
    }

 

    public boolean existsElementID(String id) {
        try {
            driver.findElement(By.id(id));
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("linkText is not present");

 

            }
            return false;
        }

 

        return true;
    }
    public static String uniqueIndexDateSecnoTrimOneHourLess() {

    	 

        String dategenerated = null;
        try 
        {
            Calendar cal = Calendar.getInstance();

 

            // remove next line if you're always using the current time.
            cal.setTime(new Date());
            cal.add(Calendar.HOUR, -1);
            Date oneHourBack = cal.getTime();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dategenerated = ft.format(oneHourBack);
            App.logger.info("index is --" + dategenerated);
            return dategenerated;
        } 
        catch (Exception e) {
            App.logger.info("Exception while taking screenshot " + e.getMessage());
        }
        return dategenerated;

 

    }
    
	/*
	 * public File replaceMultipleColumnsInFileByLine(String fileName, String
	 * reference, int[] columns, int line) throws IOException { File fileToModify=
	 * new File(fileName); BufferedReader reader = new BufferedReader(new
	 * FileReader(fileToModify)); ArrayList<String> lines= new ArrayList<String>();
	 * String fileLine = null; while((fileLine =reader.readLine())!=null) {
	 * lines.add(fileLine); } //String headers = reader.readLine(); //String values=
	 * reader.readLine();
	 * 
	 * 
	 * 
	 * System.out.println(lines.get(line-1)); List<String> list2 =
	 * Arrays.asList(lines.get(line-1).split(","));
	 * 
	 * 
	 * 
	 * for(int i =0 ; i< columns.length ; i++) {
	 * 
	 * 
	 * 
	 * list2.set((columns[i]-1), reference); }
	 * 
	 * 
	 * 
	 * lines.set(line-1, String.join(",", list2)); reader.close(); FileWriter fw =
	 * new FileWriter(fileToModify); BufferedWriter bw = new BufferedWriter(fw); try
	 * { lines.forEach( item -> { try { bw.write(item); bw.write("\r\n"); } catch
	 * (IOException e) {e.printStackTrace();
	 * 
	 * 
	 * 
	 * }}); } catch(Exception e) { App.logger.info(e.getMessage()); } finally { try
	 * { if (bw != null) {bw.close();} if (fw != null) {fw.close();} } catch
	 * (IOException ex) { System.err.format("IOException: %s%n", ex); } }
	 * 
	 * 
	 * 
	 * return fileToModify; }
	 */
    
    public static WebElement waitAndFindElement(By locator)
    {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        App.logger.info(locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));        
    }
    
    public static List<WebElement> waitAndFindElements(By locator)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            App.logger.info(locator);
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));    
        }    
        catch (Exception e) 
        {
            App.logger.info(locator+" not found");
        }
        return new ArrayList<WebElement>();
    }
    
    public Boolean waitUntilElementIsNotFound(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));        
    }
    
    public static String textBetweenRef(String filepath, String filename, String content) {
        String s = content;
        String textBetweencsv = null;
        if (App.logger.isInfoEnabled()) {
            App.logger.info("Content to is ----" + content);

 

        }
        ;
        Pattern p = Pattern.compile("<Ref>(.*?)</Ref>");
        Matcher m = p.matcher(s);
        if (m.find()) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern found");

 

            }

 

            textBetweencsv = (m.group(1));
            return textBetweencsv;
        } else {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("pattern not  found");

 

            }
        }
        return textBetweencsv;

 

    }
    public static void assertMessageDisplayed(String messageDisplayed) throws InterruptedException {
        try {
            boolean confirmMessageDisplayed;
            confirmMessageDisplayed = (driver.getPageSource().contains(messageDisplayed));
            assertTrue(confirmMessageDisplayed == true);
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Account is ----" + messageDisplayed);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

 

}
 
 public static void assertMessageNotDisplayed(String messageDisplayed) throws InterruptedException {
        try {
            boolean confirmMessageDisplayed;
            confirmMessageDisplayed = (driver.getPageSource().contains(messageDisplayed));
            assertFalse(confirmMessageDisplayed == false);
            
            if (App.logger.isInfoEnabled()) {
                App.logger.info("No account is displayed ----" + messageDisplayed);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
}
     public void waitForPageLoaded(WebDriver webDriver) {
            ExpectedCondition<Boolean> pageLoadflag = new
                    ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                        }
                    };
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, 30);
                wait.until(pageLoadflag);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for Page Load Request to complete.");
            }
        }
     
    public void waitForLoaderToBeInvisible()
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ctl00_ctl00_ctl00_panelUpdateProgress'][contains(@style,'display: none')]")));    
    }
    public void waitForLoaderToDisappear(String idOfLoader)
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(idOfLoader)));
    }

 

     public boolean existsElementXpath(String xpath) {
            try {
                driver.findElement(By.xpath(xpath));
            } catch (Exception e) {
                return false;
            }

 

            return true;
        }
     
     
     public static String textBetweenXmlDocOrdrRef(String filepath, String filename, String content) {
            //<Doc:OrdrRef>test124</Doc:OrdrRef>
            String textbetween = null;
            App.logger.info("Content to is ----" + content);
            Pattern p = Pattern.compile("<Doc:OrdrRef>(.*?)</Doc:OrdrRef>");
            Matcher m = p.matcher(content);
            if (m.find()) {
                App.logger.info("pattern found");
                App.logger.info(m.group(1));
                    // => "3"
                textbetween = (m.group(1));
                return textbetween;
            } else {
                App.logger.info("pattern not  found");
            }
            return textbetween;
        }
     public void compareTwoTextFiles(String actualFilePath, String expectedFilePath) throws IOException
     {   
         BufferedReader reader1 = new BufferedReader(new FileReader(actualFilePath));
         BufferedReader reader2 = new BufferedReader(new FileReader(expectedFilePath));
         String actualFile = reader1.readLine();
         String expectedFile = reader2.readLine();
         boolean areEqual = true;
         int lineNum = 1;
           while (actualFile != null && expectedFile != null)
         {               
             if(!actualFile.equalsIgnoreCase(expectedFile))
             {
                 areEqual = false; 
                  break;
             }
                    
             actualFile = reader1.readLine();
              expectedFile = reader2.readLine();
               lineNum++;
           }
          if(areEqual)
         {
            Assert.assertTrue(areEqual);
            App.logger.info("Two files have same content.");
         }
         else
         {
             Assert.assertFalse(areEqual);
             App.logger.info("Two files have different content. They differ at line "+lineNum);
               App.logger.info("File1 has "+actualFile+" and File2 has "+expectedFile+" at line "+lineNum);
         }
         reader1.close();
         reader2.close();
     }
     
     public String removeBlankSpaces(String a)
     {
         return a.replaceAll("\\s", "");
     }
}
