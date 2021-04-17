package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends BasePage {

    String xpathLogoutButton = "//div[@id='logoutDiv']//button";

    @FindBy(xpath = "//div[@id='logoutDiv']//button")
    WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    WebElement navTabFiles;

    @FindBy(id = "nav-notes-tab")
    WebElement navTabNotes;

    @FindBy(id = "nav-credentials-tab")
    WebElement navTabCredentials;

    @FindBy(id = "nav-files")
    WebElement tabPaneFiles;

    @FindBy(id = "nav-notes")
    WebElement tabPaneNotes;

    @FindBy(id = "nav-credentials")
    WebElement tabPaneCredentials;

    @FindBy(id = "fileTable")
    WebElement fileTable;

    @FindBy(id = "userTable")
    WebElement userTable;

    @FindBy(id = "credentialTable")
    WebElement credentialTable;

    @FindBy(id = "noteModal")
    WebElement noteModal;

    @FindBy(id = "note-id")
    WebElement inputNoteId;

    @FindBy(id = "note-title")
    WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    WebElement inputNoteDescription;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void doLogout() {
        logoutButton.submit();
    }

    public void openTabFiles() {
        navTabFiles.click();
    }

    public void openTabNotes() {
        navTabNotes.click();
    }

    public void openTabCredentials() {
        navTabCredentials.click();
    }

    public Integer getUserTableItemCount() {
        return userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).size();
    }

    public boolean isTabUserNoteDisplayed(WebDriver driver) {
        WebElement marker = waitUntilVisible(driver, "//div[@id='nav-notes']");
        return marker.isDisplayed();
    }

    public boolean isModalUserNoteDisplayed(WebDriver driver) {
        WebElement marker = waitUntilVisible(driver, "//div[@id='noteModal']");
        return marker.isDisplayed();
    }

    public void openFormUserNote() {
        tabPaneNotes.findElement(By.xpath("//button[@type='button']")).click();
    }

    public void submitFormUserNote(String title, String description) {
        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.clear();
        inputNoteDescription.sendKeys(description);
        inputNoteTitle.submit();
    }

    public boolean isNoteExistsInTable(String title, String description) {
        List<WebElement> rows= userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            String rowTitle = row.findElement(By.xpath("//td[2]")).getText();
            String rowDesc = row.findElement(By.xpath("//td[3]")).getText();

            if (rowTitle.equals(title) && rowDesc.equals(description)) {
                return true;
            }
        }

        return false;
    }

    public void clickEditUserNoteByTitle(String title) {
        List<WebElement> rows= userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            String rowTitle = row.findElement(By.xpath("//td[2]")).getText();

            if (rowTitle.equals(title)) {
                WebElement btn = row.findElement(By.xpath("//td[1]/button[@type='button']"));
                btn.click();
                break;
            }
        }
    }

    public void clickDeleteUserNoteByTitle(String title) {
        List<WebElement> rows= userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            String rowTitle = row.findElement(By.xpath("//td[2]")).getText();

            if (rowTitle.equals(title)) {
                WebElement btn = row.findElement(By.tagName("a"));
                btn.click();
                break;
            }
        }
    }

}
