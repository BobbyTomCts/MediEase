package org.example.pages;

import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ProfilePage extends BasePage {

    private final By cancelButtonBy = By.cssSelector(".btn-cancel");
    private final By manageDependentsButtonBy = By.cssSelector(".btn-add-dependant");

    private final By fullNameInputBy = By.xpath("//div[@class='edit-form']//label[text()='Full Name']/following-sibling::input");
    private final By phoneNumberInputBy = By.xpath("//div[@class='edit-form']//label[text()='Phone Number']/following-sibling::input");

    private final By displayedFullNameBy = By.xpath("//span[text()='Full Name']/following-sibling::span[@class='info-value']");
    private final By displayedPhoneNumberBy = By.xpath("//span[text()='Phone Number']/following-sibling::span[@class='info-value']");

    private final By successMessageBy = By.cssSelector(".success-message");
    private final By errorMessageBy = By.cssSelector(".error-message-top");


    @FindBy(css = ".btn-edit")
    private WebElement editProfileButtonBy;

    @FindBy(css = ".btn-save")
    private WebElement saveChangesButtonBy;

    public ProfilePage() {
        PageFactory.initElements(DriverManager.get(), this);
    }


    public void clickEditProfileButton() {
        //WebElement button = webWait().until(ExpectedConditions.elementToBeClickable(editProfileButtonBy));
        // Use JavaScript to force the click, bypassing potential Selenium issues
        DriverManager.get().executeScript("arguments[0].click();", editProfileButtonBy);
        //click(editProfileButtonBy);
    }

    public void clickSaveChangesButton() {
        DriverManager.get().executeScript("arguments[0].click();", saveChangesButtonBy);

//        click(saveChangesButtonBy);
    }

    public void clickCancelButton() {
        DriverManager.get().executeScript("arguments[0].click();", DriverManager.get().findElement(cancelButtonBy));

//        webWait().until(ExpectedConditions.elementToBeClickable(cancelButtonBy)).click();
    }

    public void clickManageDependentsButton() throws InterruptedException {

        WebElement button = webWait().until(ExpectedConditions.elementToBeClickable(manageDependentsButtonBy));
        DriverManager.get().executeScript("arguments[0].click();", button);
    }

    public void enterFullName(String name) {
        WebElement nameInput = webWait().until(ExpectedConditions.visibilityOfElementLocated(fullNameInputBy));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterPhoneNumber(String phone) {
        WebElement phoneInput = webWait().until(ExpectedConditions.visibilityOfElementLocated(phoneNumberInputBy));
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }


    public String getDisplayedFullName() {
        return webWait().until(ExpectedConditions.visibilityOfElementLocated(displayedFullNameBy)).getText();
    }

    public String getDisplayedPhoneNumber() {
        return webWait().until(ExpectedConditions.visibilityOfElementLocated(displayedPhoneNumberBy)).getText();
    }

    public String getSuccessMessageText() {
        return webWait().until(ExpectedConditions.visibilityOfElementLocated(successMessageBy)).getText().trim();
    }

    public boolean isEditProfileButtonVisible() {
        try {
            return webWait().until(ExpectedConditions.elementToBeClickable(editProfileButtonBy)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public String getCurrentUrl() {
        return DriverManager.get().getCurrentUrl();
    }
}
