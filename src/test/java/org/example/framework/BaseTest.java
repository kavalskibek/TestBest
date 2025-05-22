package org.example.framework;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Test setup/teardown: acquires the driver, navigates, then quits.
 */
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getInstance().getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // navigate to your login page
        String baseUrl = ConfigLoader.get("base.url");
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.getInstance().quitDriver();
    }
}
