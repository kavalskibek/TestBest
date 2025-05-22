package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.NoSuchElementException;
import java.time.Duration;
import java.util.function.Function;

/**
 * Helper for fluent-style explicit waits.
 */
public class WaitUtils {

    /**
     * Waits until the given element is displayed, up to the specified timeout.
     *
     * @param driver            the WebDriver instance
     * @param element           the WebElement to wait for
     * @param timeoutInSeconds  maximum seconds to wait
     */
    public static void waitForElement(WebDriver driver, WebElement element, int timeoutInSeconds) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .until((Function<WebDriver, Boolean>) d -> element.isDisplayed());
    }
}