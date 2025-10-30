package org.example.pages;
import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InsuranceSelectionPage extends BasePage {

    @FindBy(css = ".btn-proceed")
    private WebElement proceedDependentsButton;

    @FindBy(css = ".welcome-text")
    private WebElement welcomeText;

    private String package_card_xpath = "//h2[text()='%s']/ancestor::div[contains(@class, 'package-card')]";
    public InsuranceSelectionPage() {
        PageFactory.initElements(DriverManager.get(), this);
    }
    public WebElement getPackageCardElement(String packageName) {
        String xpath = String.format(package_card_xpath, packageName);
        return webWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public void selectPackage(String packageName) {
        WebElement selectButton = getPackageCardElement(packageName);
        click(selectButton);
    }

    public boolean isPackageSelected(String packageName) {
        String xpath = String.format(package_card_xpath, packageName);
        try {
            webWait().until(ExpectedConditions.attributeContains(
                    By.xpath(xpath),
                    "class",
                    "selected"
            ));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
    public boolean isProceedButtonEnabled() {
        webWait().until(ExpectedConditions.visibilityOf(proceedDependentsButton));
        return proceedDependentsButton.isEnabled();
    }

    public void clickProceedToDependants() {
        click(proceedDependentsButton);
    }
    
    public String getWelcomeMessage() {
        return webWait().until(ExpectedConditions.visibilityOf(welcomeText)).getText();
    }

}
