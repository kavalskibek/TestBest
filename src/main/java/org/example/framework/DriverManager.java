package org.example.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Singleton WebDriver factory that reads “browser” from config.
 */
public class DriverManager {
    private static DriverManager instance;
    private WebDriver driver;

    private DriverManager() {
        String browser = ConfigLoader.get("browser");
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Returns the singleton DriverManager, creating it (and its WebDriver) on first call.  
     */
    public static DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    /**
     * Returns the underlying WebDriver.
     */
    public WebDriver getDriver() {
        return driver;            // ← make sure you return the driver here
    }

    /**
     * Quits the driver and resets the singleton.
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        instance = null;
    }
}
