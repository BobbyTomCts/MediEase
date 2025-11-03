package org.example.pages;

import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    @FindBy(xpath = "//input[@name = 'name']")
    private WebElement nameInput;

    @FindBy(xpath = "//input[@name = 'phone']")
    private WebElement phoneInput;

    @FindBy(xpath = "//input[@name = 'email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name = 'password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//html/body/app-root/app-register/div/div/form/button")
    private WebElement registerButton;

    @FindBy(xpath = "/html/body/app-root/app-register/div/div/div[2]")
    private WebElement errorMessage;

    public RegisterPage() {
        PageFactory.initElements(DriverManager.get(), this);
    }


    public void registerUser(String name, String phone, String email, String password) {
        webWait().until(ExpectedConditions.visibilityOf(nameInput)).sendKeys(name);
        phoneInput.sendKeys(phone);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        registerButton.click();
    }


    public String getCurrentUrl() {
        return DriverManager.get().getCurrentUrl();
    }

    public String getRegistrationErrorMessage() {
        return webWait().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }
}

