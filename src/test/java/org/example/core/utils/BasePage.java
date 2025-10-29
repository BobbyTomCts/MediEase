package org.example.core.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.example.core.utils.DriverManager;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriverWait webWait() {
        return new WebDriverWait(DriverManager.get(), Duration.ofSeconds(Config.explicitWaitSec()));
    }

    protected WebElement el(By locator) {
        return webWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(WebElement element) {
        webWait().until(ExpectedConditions.elementToBeClickable(element)).click();
    }

}

