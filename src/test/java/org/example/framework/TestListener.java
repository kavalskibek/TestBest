package org.example.framework;

import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * TestNG listener that takes a screenshot on failure
 * and attaches it to Allure.
 */
public class TestListener implements ITestListener {

    // grab the driver from your singleton manager
    private final WebDriver driver = DriverManager.getInstance().getDriver();

    @Override
    public void onTestFailure(ITestResult result) {
        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
        saveScreenshot(screenshot);
    }

    // other ITestListener methods can stay empty
    @Override public void onTestStart(ITestResult result) { }
    @Override public void onTestSuccess(ITestResult result) { }
    @Override public void onTestSkipped(ITestResult result) { }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
    @Override public void onStart(ITestContext context) { }
    @Override public void onFinish(ITestContext context) { }

    /**
     * Allure attachment annotation.
     */
    @Attachment(value = "Screenshot on Failure", type = "image/png")
    private byte[] saveScreenshot(byte[] data) {
        return data;
    }
}
