package com.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.listeners.SuiteListener;
import com.listeners.TestListener;

import calas.CmmonFunction.CmmonFunction;


public class App {
	
	public static String sN = null;
    public static String endp = null;
    public static String username = null;
    public static String password = null;
    public static String emsUrl = null;
    public static String opsUrl = null;
    public static String dbEnv = null;
    public static WebDriver driver;
    public static String dbUn = null;
    public static String dbP = null;
    public static String dataPath = null;
    public static String dbenvironment = null;
    public static String pathLog4j = null;
    public static Logger logger = null; // Logger.getLogger(App.class);
    public static double previousVersion = 19.9;
    public static double releaseVersion = 19.10;
    public static String opsEndEnv; 

 

    public static void main(String[] args) throws IOException, InterruptedException {
        logger = Logger.getLogger(App.class);
        CmmonFunction commonFunctions = new CmmonFunction();
        commonFunctions.uniqueIndexDateSec();
        String logFile = commonFunctions.uniqueIndexDateSecFolderName();
        String logpropertiesFile = "logPropertiesFile";
        pathLog4j = System.getProperty(logpropertiesFile);
        System.setProperty("log_dir", logFile);
        Properties logProperties = new Properties();
        logProperties.load(new FileInputStream(pathLog4j));
        PropertyConfigurator.configure(logProperties);
        BasicConfigurator.configure();
        if (App.logger.isInfoEnabled()) {
            logger.info("Logging initialized.");
            logger.info("Log4jDemo - message...");
        }
        Properties property = new Properties();
        try {
            InputStream inputStream = App.class.getClassLoader().getResourceAsStream("application.properties");
            if (App.logger.isInfoEnabled()) {
                App.logger.info("inputStream --- " + inputStream);
            }
            property.load(inputStream);
        } catch (Exception e) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info("Could not load the file");
            }
            e.printStackTrace();
        }

 

        String serverName = "serverName";
        sN = System.getProperty(serverName);
        opsEndEnv=sN.substring(0,sN.indexOf(".c"));
        
        // Environment
        String endpoint = "endpoint";
        
        endp = System.getProperty(endpoint);
        commonFunctions.consoleColorDisplay("Running tests in ----" + endpoint, "yellow");
        // DBDetails

 

        dbenvironment = "dbenvironment";
        dbEnv = System.getProperty(dbenvironment);

 

        String dbuserName = "dbUserName";
        dbUn = System.getProperty(dbuserName);

 

        String dbpassword = "dbPassword";
        dbP = System.getProperty(dbpassword);
        // application.properties
        username = applicationProperty(property, "Username");
        password = applicationProperty(property, "Password");
        // saveParamChanges();
        emsUrl = String.format("https://%s/EMS/", sN);
        opsUrl = String.format("https://%s/ops/", sN);
        // testDataPath
        String testDataPath = "testDataPath";
        dataPath = System.getProperty(testDataPath);
        // VM arguments print
        if (App.logger.isInfoEnabled()) {
            App.logger.info("--------------------Variables ");
            App.logger.info("serverName ---" + sN);
            App.logger.info("endpoint ---" + endp);
            App.logger.info("username ---" + username);
            App.logger.info("password ---" + password);
            App.logger.info("emsUrl ---" + emsUrl);
            App.logger.info("opsUrl ---" + opsUrl);
            App.logger.info("dbenvironment ---" + dbEnv);
            App.logger.info("dbuserName ---" + dbUn);
            App.logger.info("dbpassword ---" + dbP);
            App.logger.info("testDataPath ---" + dataPath);
            App.logger.info("log4jProperties ---" + pathLog4j);
            App.logger.info("--------------------Variables");
        }
        
        try {
            commonFunctions.consoleColorDisplay("In main class", "green");
            String testToRun = "testToRun";
            if (App.logger.isInfoEnabled()) {
                App.logger.info("TestToRun ---" + testToRun);
            }
            testToRun = System.getProperty(testToRun);
            if (App.logger.isInfoEnabled()) {
                App.logger.info(" Running tests from command prompt------" + testToRun);
            }
            List<Class<? extends ITestNGListener>> listeners = new ArrayList<Class<? extends ITestNGListener>>();
            listeners.add((Class<? extends ITestNGListener>) TestListener.class);
            listeners.add((Class<? extends ITestNGListener>) SuiteListener.class);
            TestNG testngRunner = new TestNG();
            testngRunner.setParallel(XmlSuite.DEFAULT_PARALLEL);
            testngRunner.getReporters();
            testngRunner.setVerbose(10);
            testngRunner.setThreadCount(4);
            testngRunner.setListenerClasses(listeners);
            String category = System.getProperty("testCategory");
            if (category!=null&&!category.isEmpty()) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Running mentioned test categories " + category);
                }
                testngRunner.setGroups(category);
            }
            if (testToRun.equalsIgnoreCase("ALL")) {
                commonFunctions.consoleColorDisplay("Running all tests", "yellow");
                testngRunner.setTestClasses(new Class[] { BrowserOpen.class});

 

            } else if (testToRun.contains(",") || !testToRun.equalsIgnoreCase("ALL")) {
                if (App.logger.isInfoEnabled()) {
                    App.logger.info("Running listed testcases");
                }
                String[] testsToRun = testToRun.split(",");
                Class[] testClasses = new Class[testsToRun.length];
                for (int i = 0; i < testsToRun.length; i++) {
                    Class cls = Class.forName("com.calastone.ctn." + testsToRun[i]);
                    if (App.logger.isInfoEnabled()) {
                        App.logger.info(cls.getName());
                    }
                    testClasses[i] = cls;
                }
                testngRunner.setTestClasses(testClasses);

 

            }
            testngRunner.run();

 

        } catch (Throwable t) {
            if (App.logger.isInfoEnabled()) {
                App.logger.info(t.getClass().getSimpleName() + ": " + t.getMessage());
                App.logger.info(" no test specified");
            }
        }

 

    }

 

    public static void extractStackTrace(Exception exception, Logger logger) {
        StackTraceElement[] elements = exception.getStackTrace();
        String trace = null;
        for (StackTraceElement element : elements) {
            trace = (trace == null) ? element.toString() : trace + ",\n" + element.toString();
        }
        logger.error(trace);
    }

 

    public static String applicationProperty(Properties prop, String name) {
        String response = System.getProperty(name);
        if (response == null) {
            response = prop.getProperty(name);
        }
        return response;
    }

 

    public void loadParams() {
        Properties props = new Properties();
        InputStream is = null;
        try {
            File f = new File("application.properties");
            is = new FileInputStream(f);
        } catch (Exception e) {
            is = null;
        }
        try {
            if (is == null) {
                is = getClass().getResourceAsStream("application.properties");
            }
            props.load(is);
        } catch (Exception e) {
        }
        sN = props.getProperty("SN", "changed");
    }

 

    public static void saveParamChanges() {
        try {
            Properties props = new Properties();
            props.setProperty("SN", sN);
            App.logger.info("In saveParamChanges");
            File f = new File("application.properties");
            OutputStream out = new FileOutputStream(f);
            props.store(out, "This is an optional header comment string");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}