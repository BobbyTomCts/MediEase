package org.example.core.utils;

import org.openqa.selenium.remote.RemoteWebDriver;

public final class DriverManager {
    private static final ThreadLocal<RemoteWebDriver> TL = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void set(RemoteWebDriver driver) {
        TL.set(driver);
    }

    public static RemoteWebDriver get() {
        return TL.get();
    }

    public static void unload() {
        TL.get().quit();

        TL.remove();
    }
}

