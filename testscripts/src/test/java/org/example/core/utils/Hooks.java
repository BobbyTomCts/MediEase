
package org.example.core.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public class Hooks {
    private static WebDriver driver;

    @Before
    public void setUp() {

        DriverManager.set(DriverFactory.newDriver(Config.browser(), Config.headless()));

        DriverManager.get().manage().window().maximize();
        DriverManager.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {

        DriverManager.unload();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
