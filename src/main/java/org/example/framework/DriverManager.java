package org.example.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Singleton WebDriver factory that reads "browser" from config.
 * Enhanced for CI/CD environments with headless mode.
 */
public class DriverManager {
    private static volatile DriverManager instance;
    private WebDriver driver;

    private DriverManager() {
        initializeDriver();
    }

    private void initializeDriver() {
        String browser = ConfigLoader.get("browser");
        boolean isHeadless = Boolean.parseBoolean(ConfigLoader.get("headless"));

        // Default to headless in CI environments
        if (System.getenv("CI") != null || System.getenv("GITHUB_ACTIONS") != null) {
            isHeadless = true;
        }

        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
            }

            driver = new ChromeDriver(options);

        } else if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");
            }

            driver = new FirefoxDriver(options);

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        System.out.println("Driver initialized: " + browser + " (headless: " + isHeadless + ")");
    }

    /**
     * Returns the singleton DriverManager, creating it (and its WebDriver) on first call.
     * Thread-safe implementation using double-checked locking.
     */
    public static DriverManager getInstance() {
        if (instance == null) {
            synchronized (DriverManager.class) {
                if (instance == null) {
                    instance = new DriverManager();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the underlying WebDriver.
     */
    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call getInstance() first.");
        }
        return driver;
    }

    /**
     * Quits the driver and resets the singleton.
     */
    public void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Driver quit successfully");
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
        instance = null;
    }

    /**
     * Check if driver is initialized and active
     */
    public boolean isDriverActive() {
        try {
            return driver != null && driver.getTitle() != null;
        } catch (Exception e) {
            return false;
        }
    }
}