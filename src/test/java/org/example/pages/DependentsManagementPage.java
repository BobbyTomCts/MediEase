package org.example.pages;

import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class DependentsManagementPage extends BasePage {

    @FindBy(css = ".add-btn")
    private WebElement addDependentButton;

    @FindBy(css = ".skip-btn")
    private WebElement skipAndCompleteSetupButton;

    @FindBy(css = ".submit-btn")
    private WebElement completeSetupButton;

    @FindBy(css = ".modal-overlay")
    private WebElement dependentForm;

    @FindBy(css = "//h3/span[text() = 'Edit Dependant']")
    private WebElement editDependentFormHeader;

    @FindBy(xpath = "//h3/span[text()='Add Dependant']")
    private WebElement dependentFormHeader;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "age")
    private WebElement ageInput;

    @FindBy(id = "gender")
    private WebElement genderSelect;

    @FindBy(id = "relation")
    private WebElement relationSelect;

    @FindBy(xpath = "//form//button[@class = 'save-btn']")
    private WebElement addDependentFormSubmitButton;

    @FindBy(css = ".delete-btn")
    private WebElement deleteDependentButton;

    @FindBy(css = ".edit-btn")
    private WebElement editDependentButton;


    public DependentsManagementPage() {
        PageFactory.initElements(DriverManager.get(), this);
    }

    public void clickAddDependant() {

        click(addDependentButton);    }

    public void clickSkipAndCompleteSetup() {
        click(skipAndCompleteSetupButton);
    }

    public void clickCompleteSetup() {
        click(completeSetupButton);
    }

    public boolean isDependantFormVisible() {
        try {
            return webWait().until(ExpectedConditions.visibilityOf(dependentFormHeader)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
    public boolean isEditDependantFormVisible() {
        try {
            return webWait().until(ExpectedConditions.visibilityOf(editDependentFormHeader)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void fillAndSubmitDependentForm(String name, int age, String gender, String relation) {
        WebElement nameField = webWait().until(ExpectedConditions.visibilityOf(nameInput));
        nameField.clear();
        nameField.click();
        nameField.sendKeys(name);

        WebElement ageField = ageInput;
        ageField.clear();
        ageField.click();
        ageField.sendKeys(String.valueOf(age));

        new Select(genderSelect).selectByVisibleText(gender);
        new Select(relationSelect).selectByVisibleText(relation);
        click(addDependentFormSubmitButton);
    }


    public boolean isSkipButtonEnabled() {
        return webWait().until(ExpectedConditions.visibilityOf(skipAndCompleteSetupButton)).isEnabled();
    }

    public boolean isCompleteSetupButtonEnabled() {
        try {
            webWait().until(ExpectedConditions.elementToBeClickable(completeSetupButton));
            return completeSetupButton.isEnabled();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isDependantDisplayed(String dependantName) {
        String xpath = String.format("//h3[text()='%s']", dependantName);
        try {
            return webWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isInsuranceSetupComplete() {
        String expectedUrl = "http://localhost:4200/user-dashboard";

        try {
            return webWait().until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void modifyAndSubmitDependentForm(int age, String relation) {
        WebElement ageField = webWait().until(ExpectedConditions.visibilityOf(ageInput));
        ageField.clear();
        ageField.click();
        ageField.sendKeys(String.valueOf(age));
        WebElement relationField = webWait().until(ExpectedConditions.visibilityOf(relationSelect));
        new Select(relationSelect).selectByVisibleText(relation);

        click(addDependentFormSubmitButton);
    }
    public boolean isDependantRemoved(String dependantName, String relation) {
        String xpath = String.format(
                "//div[contains(@class, 'dependant-card')]//h3[text()='%s']/following-sibling::p[text()='%s']",
                dependantName, relation
        );
        try {
            return webWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void confirmRemoval() throws InterruptedException {

        Alert alert = webWait().until(ExpectedConditions.alertIsPresent());

        DriverManager.get().switchTo().alert();
        alert.accept();
        DriverManager.get().navigate().refresh();
        Thread.sleep(3000);

    }

    public void clickEditButtonForDependent(String dependantName) {
        By editButtonLocator = getDependentButtonLocator(dependantName, "Edit");
        WebElement editButton = el(editButtonLocator);
        click(editButton);
        webWait().until(ExpectedConditions.visibilityOf(dependentFormHeader));
    }

    public void clickRemoveButtonForDependent(String dependantName) {
        By removeButtonLocator = getDependentButtonLocator(dependantName, "Remove");
        WebElement removeButton = el(removeButtonLocator);
        click(removeButton);
    }

    private By getDependentCardXpath(String dependantName) {
        return By.xpath(String.format("//h3[text()='%s']/ancestor::div[contains(@class, 'dependant-card')]", dependantName));
    }

    private By getDependentButtonLocator(String dependantName, String buttonText) {
        return By.xpath(String.format("//h3[text()='%s']/ancestor::div[contains(@class, 'card')]//button[normalize-space(text())='%s']", dependantName, buttonText));
    }

    public boolean isDependantDetailsCorrect(String dependantName, int age, String relation) {
        String nameXpath = String.format("//h3[text()='%s']", dependantName);
        String detailsXpath = String.format(
                "//h3[text()='%s']/ancestor::div[contains(@class, 'dependent-card')]//*[text()='%s years'] | //h3[text()='%s']/ancestor::div[contains(@class, 'dependent-card')]//*[text()='%s']",
                dependantName, age, dependantName, relation
        );

        try {
            webWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nameXpath)));
            webWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(detailsXpath)));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

}