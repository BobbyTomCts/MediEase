package org.example.pages;

import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

public class UserDashboardPage extends BasePage {

    @FindBy(css = ".welcome-section h1")
    private WebElement welcomeHeader;

    @FindBy(xpath="/html/body/app-root/app-user-home/div/div/div[3]/div[2]/button")
    private WebElement submitNewClaimButton;

    @FindBy(css = ".claims-table tbody")
    private WebElement claimsTableBody;

    @FindBy(css = ".popup-overlay")
    private WebElement claimPopupOverlay;

    @FindBy(id = "hospital")
    private WebElement hospitalSelect;

    @FindBy(id = "claimAmount")
    private WebElement claimAmountInput;

    @FindBy(css = ".popup-footer .btn-primary")
    private WebElement submitClaimPopupButton;

    public UserDashboardPage() {

        PageFactory.initElements(DriverManager.get(), this);
    }

    public boolean isOnDashboardPage() {
        String expectedUrl = "http://localhost:4200/user-dashboard";
        try {
            return webWait().until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }


    public String checkClaimStatus(String claimId) {
        String statusXPath = String.format("//td[text()='#%s']/ancestor::tr/td[5]", claimId);
        webWait().until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath(statusXPath),"PENDING")));

        return DriverManager.get().findElement(By.xpath(statusXPath)).getText();
    }

    public String getWelcomeText() {
        return webWait().until(ExpectedConditions.visibilityOf(welcomeHeader)).getText();
    }

    public void clickSubmitNewClaim() throws InterruptedException {
        Thread.sleep(2000);
        //WebElement button = webWait().until(ExpectedConditions.elementToBeClickable(submitClaimPopupButton));

        //DriverManager.get().executeScript("arguments[0].click();", button);
        click(submitNewClaimButton);
        webWait().until(ExpectedConditions.visibilityOf(claimPopupOverlay));
    }

    public void fillClaimForm(String hospitalName, String amount) {
        Select hospitalDropdown = new Select(webWait().until(ExpectedConditions.visibilityOf(hospitalSelect)));

        webWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//select[@id='hospital']/option"), 1));
        hospitalDropdown.selectByVisibleText(hospitalName);

        claimAmountInput.clear();
        claimAmountInput.sendKeys(amount);
    }

    public void submitClaim() {
        click(submitClaimPopupButton);
        webWait().until(ExpectedConditions.invisibilityOf(claimPopupOverlay));
    }

    public String[] getNewestClaimDetails() {
        String amountXpath = "//div[@class='claims-table-container']//tbody/tr[1]/td[2]";
        String statusXpath = "//div[@class='claims-table-container']//tbody/tr[1]/td[5]//span[contains(@class, 'status-badge')]";

        WebElement amountElement = webWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(amountXpath)));
        WebElement statusElement = webWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(statusXpath)));

        String requestedAmount = amountElement.getText().trim();
        String status = statusElement.getText().trim();

        return new String[]{requestedAmount, status};
    }



}
