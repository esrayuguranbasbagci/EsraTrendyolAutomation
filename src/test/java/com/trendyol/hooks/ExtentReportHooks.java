package com.trendyol.hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportHooks {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static WebDriver driver;

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    @Before
    public void setUp(Scenario scenario) {
        if (extent == null) {
            initializeExtentReports();
        }
        
        test = extent.createTest(scenario.getName());
        test.log(Status.INFO, "Starting scenario: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            test.log(Status.FAIL, "Scenario failed: " + scenario.getName());
            
            // Take screenshot on failure
            if (driver != null) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    String screenshotPath = takeScreenshot(scenario.getName());
                    test.addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    test.log(Status.WARNING, "Could not take screenshot: " + e.getMessage());
                }
            }
        } else {
            test.log(Status.PASS, "Scenario passed: " + scenario.getName());
        }
        
        extent.flush();
    }

    private void initializeExtentReports() {
        extent = new ExtentReports();
        
        // Create reports directory
        String reportsDir = "target/extent-reports";
        new File(reportsDir).mkdirs();
        
        // Spark Reporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportsDir + "/SparkReport.html");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Trendyol Automation Test Report");
        sparkReporter.config().setReportName("Trendyol Shopping Test Report");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        
        extent.attachReporter(sparkReporter);
        
        // System Info
        extent.setSystemInfo("OS", "MacOS");
        extent.setSystemInfo("User", "Test User");
        extent.setSystemInfo("Environment", "Test Environment");
        extent.setSystemInfo("Build", "1.0");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Framework", "Cucumber + Selenium");
    }

    private String takeScreenshot(String scenarioName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName = scenarioName.replaceAll("\\s+", "_") + "_" + timestamp + ".png";
        String screenshotPath = "target/screenshots/" + screenshotName;
        
        // Create screenshots directory
        new File("target/screenshots").mkdirs();
        
        // Save screenshot
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Files.write(Paths.get(screenshotPath), screenshot);
        
        return screenshotPath;
    }

    public static void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
    }

    public static void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, message);
        }
    }

    public static void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, message);
        }
    }

    public static void logWarning(String message) {
        if (test != null) {
            test.log(Status.WARNING, message);
        }
    }
}
