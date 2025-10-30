package org.example.pages;

import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class NetworkHospitalsPage extends BasePage {

    @FindBy(css = "h1")
    private WebElement pageTitle;

    @FindBy(css = ".back-btn")
    private WebElement backButton;

    @FindBy(css = ".hospital-count")
    private WebElement hospitalCountHeader;

    @FindBy(css = ".search-input")
    private WebElement searchInput;

    @FindBy(css = "select:nth-of-type(1)")
    private WebElement stateFilterSelect;

    @FindBy(css = "select:nth-of-type(2)")
    private WebElement cityFilterSelect;

    @FindBy(css = ".clear-btn")
    private WebElement clearFiltersButton;

    @FindBy(css = ".hospitals-grid .hospital-card")
    private List<WebElement> hospitalCards;

    @FindBy(css = ".no-results")
    private WebElement noResultsMessage;

    public NetworkHospitalsPage() {
        PageFactory.initElements(DriverManager.get(), this);
    }

    public void enterSearchTerm(String searchTerm) {
        webWait().until(ExpectedConditions.visibilityOf(searchInput)).clear();
        searchInput.sendKeys(searchTerm);
    }

    public void selectState(String state) {
        webWait().until(ExpectedConditions.visibilityOf(stateFilterSelect));
        Select select = new Select(stateFilterSelect);
        select.selectByVisibleText(state);
    }

    public void selectCity(String city) {
        webWait().until(ExpectedConditions.visibilityOf(cityFilterSelect));
        Select select = new Select(cityFilterSelect);
        select.selectByVisibleText(city);
    }

    public void clickClearFilters() {
        click(clearFiltersButton);
    }

    public void clickBackButton() {
        click(backButton);
    }

    public String getHospitalCountText() {
        return webWait().until(ExpectedConditions.visibilityOf(hospitalCountHeader)).getText().trim();
    }

    public int getDisplayedHospitalCount() {
        return hospitalCards.size();
    }

    public String getSearchInputValue() {
        return searchInput.getAttribute("value");
    }

    public String getSelectedStateValue() {
        Select select = new Select(stateFilterSelect);
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedCityValue() {
        Select select = new Select(cityFilterSelect);
        return select.getFirstSelectedOption().getText();
    }

    public boolean isHospitalNameVisible(String hospitalName) {
        String xpath = String.format("//h3[@class='hospital-name' and text()='%s']", hospitalName);
        try {
            return DriverManager.get().findElement(By.xpath(xpath)).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            return webWait().until(ExpectedConditions.visibilityOf(noResultsMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return DriverManager.get().getCurrentUrl();
    }
}
