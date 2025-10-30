package org.example.pages;

import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "/html/body/app-root/app-login/div/div/form/button")
    private WebElement loginButton;

    @FindBy(xpath = "//a[text() = 'Create Account']")
    private WebElement createAccountLink;

    @FindBy(xpath = "//div[@class='message error' and contains(text(), 'Invalid password')]")
    private WebElement invalidPasswordError;

    @FindBy(xpath = "//div[@class='message error' and contains(text(), 'User not found')]")
    private WebElement userNotFoundError;

    public LoginPage() {
        PageFactory.initElements(DriverManager.get(), this);
    }

    public void login(String email, String password) {
        webWait().until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public void clickCreateAccount() {
        click(createAccountLink);
    }

    public String getCurrentUrl() {
        return DriverManager.get().getCurrentUrl();
    }

    public boolean isInvalidPasswordErrorDisplayed() {
        try {
            return webWait().until(ExpectedConditions.visibilityOf(invalidPasswordError)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isUserNotFoundErrorDisplayed() {
        try {
            return webWait().until(ExpectedConditions.visibilityOf(userNotFoundError)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}


