package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.example.core.utils.BasePage;
import org.example.core.utils.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminPage extends BasePage{

    private static final DateTimeFormatter TABLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final By logout = By.xpath("/html/body/app-root/app-admin-dashboard/div/div[1]/div[2]/button");
    private final By statusFilterDropdownLocator = By.id("statusFilter");
    private final By clearFiltersButtonLocator = By.xpath("/html/body/app-root/app-admin-dashboard/div/div[2]/div[2]/div/div/div[4]/button");

    @FindBy(id = "startDate")
    private WebElement fromDateInput;

    @FindBy(id = "endDate")
    private WebElement toDateInput;

    private By tableBody = By.xpath("//tbody");
    private By allStatusBadges = By.xpath("//tbody//span[contains(@class, 'status-badge')]");
    private By allDateCells = By.xpath("//tbody//tr/td[7]");

    public AdminPage() {
        PageFactory.initElements(DriverManager.get(), this);
    }

    public void clickApproveButton(String requestId) {
        String xpath = String.format("//td[text()='#%s']/ancestor::tr//button[contains(@class,'btn-approve')]",requestId);
        click(el(By.xpath(xpath)));
    }

    public void clickRejectButton(String requestId) {
        String xpath = String.format("//td[text()='#%s']/ancestor::tr//button[contains(@class,'btn-reject')]",requestId);
        click(el(By.xpath(xpath)));
    }

    public String getStatus(String requestId)
    {
        By locator = By.xpath(String.format("//td[text()='#%s']/ancestor::tr//span[contains(@class, 'status-badge')]", requestId));
        webWait().until(ExpectedConditions.not(ExpectedConditions.textToBe(locator,"PENDING")));
        return el(locator).getText();
    }

    public void clickLogoutButton() {
        click(el(logout));
    }

    public void selectStatusFilter(String status) {
        WebElement statusFilterDropdown = el(statusFilterDropdownLocator);
        Select statusSelect = new Select(statusFilterDropdown);
        String searchStatus = status.toUpperCase();

        boolean selected = false;
        for (WebElement option : statusSelect.getOptions()) {
            if (option.getText().toUpperCase().contains(searchStatus)) {
                statusSelect.selectByVisibleText(option.getText());
                selected = true;
                break;
            }
        }
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public void enterFromDate(String date) {
        webWait().until(ExpectedConditions.visibilityOf(fromDateInput)).sendKeys(date);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public void enterToDate(String date) {
        webWait().until(ExpectedConditions.visibilityOf(toDateInput)).sendKeys(date);
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public void clickClearFilters() {
        click(el(clearFiltersButtonLocator));
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    public boolean verifyAllStatusesAre(String expectedStatus) {
        webWait().until(ExpectedConditions.presenceOfElementLocated(tableBody));
        List<WebElement> statuses = DriverManager.get().findElements(allStatusBadges);

        if (statuses.isEmpty()) {
            return true;
        }

        for (WebElement statusElement : statuses) {
            if (!statusElement.getText().trim().equalsIgnoreCase(expectedStatus)) {
                System.err.println("Found unexpected status: " + statusElement.getText());
                return false;
            }
        }
        return true;
    }

    public boolean verifyDatesAreWithinRange(String fromDateStr, String toDateStr) {
        webWait().until(ExpectedConditions.presenceOfElementLocated(allDateCells));
        List<WebElement> dateElements = DriverManager.get().findElements(allDateCells);

        if (dateElements.isEmpty()) {
            return true;
        }

        try {
            LocalDate fromDate = LocalDate.parse(fromDateStr, INPUT_DATE_FORMATTER);
            LocalDate toDate = LocalDate.parse(toDateStr, INPUT_DATE_FORMATTER);

            for (WebElement dateElement : dateElements) {
                String fullDateText = dateElement.getText().trim();

                String dateOnlyStr = fullDateText.split(" ")[0];

                LocalDate tableDate = LocalDate.parse(dateOnlyStr, TABLE_DATE_FORMATTER);

                boolean isAfterOrEqualFrom = !tableDate.isBefore(fromDate);
                boolean isBeforeOrEqualTo = !tableDate.isAfter(toDate);

                if (!(isAfterOrEqualFrom && isBeforeOrEqualTo)) {
                    System.err.println("Found date (" + fullDateText +
                            ") outside the expected range [" + fromDateStr + " to " + toDateStr + "]");
                    return false;
                }
            }
            return true;


        } catch (Exception e) {
            System.err.println("An unexpected error occurred during date verification: " + e.getMessage());
            throw new RuntimeException("Verification error: " + e.getMessage(), e);
        }
    }

    public boolean verifyStatusFilterIsReset() {
        WebElement statusFilterDropdown = el(statusFilterDropdownLocator);
        Select statusSelect = new Select(statusFilterDropdown);
        String selectedOptionText = statusSelect.getFirstSelectedOption().getText().toUpperCase();
        return selectedOptionText.contains("ALL") || selectedOptionText.trim().isEmpty();
    }

    public boolean verifyDateInputsAreReset() {
        String fromValue = fromDateInput.getAttribute("value");
        String toValue = toDateInput.getAttribute("value");


        boolean fromReset = fromValue.isEmpty() || fromValue.equals("dd-mm-yyyy");
        boolean toReset = toValue.isEmpty() || toValue.equals("dd-mm-yyyy");

        return fromReset && toReset;
    }
}
