package org.example.pages;
import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class NavigationBar extends BasePage {

    private final By homeBy = By.xpath("//div[@class='nav-right']//span[text()='Home']");
    private final By hospitalsBy = By.xpath("//div[@class='nav-right']//span[text()='Hospitals']");
    private final By profileBy = By.xpath("//div[@class='nav-right']//span[text()='Profile']");
    private final By logoutBy = By.xpath("//div[@class='nav-right']//span[text()='Logout']");
    private final By profileHeaderBy = By.xpath("/html/body/app-root/app-user-home/div/app-user-profile/div/div/div[1]/h1");

    @FindBy(css = ".back-btn")
    private WebElement hospitalBackButton;

    public NavigationBar() {
        PageFactory.initElements(DriverManager.get(), this);
    }

    public void clickHomeButton() {
        webWait().until(ExpectedConditions.elementToBeClickable(homeBy)).click();
    }

    public void clickHospitalsButton() {
        webWait().until(ExpectedConditions.elementToBeClickable(hospitalsBy)).click();
    }

    public void clickProfileButton() {
        webWait().until(ExpectedConditions.elementToBeClickable(profileBy)).click();
    }

    public void clickLogoutButton() {
        webWait().until(ExpectedConditions.elementToBeClickable(logoutBy)).click();
    }

    public void clickBackButton() {
        DriverManager.get().executeScript("arguments[0].click();",hospitalBackButton);
        //webWait().until(ExpectedConditions.elementToBeClickable(hospitalBackButton)).click();
    }

    public String getCurrentUrlPath() {
        return DriverManager.get().getCurrentUrl();
    }

    public String getProfileHeaderText() {
        return webWait().until(ExpectedConditions.visibilityOfElementLocated(profileHeaderBy)).getText().trim();
    }


}

