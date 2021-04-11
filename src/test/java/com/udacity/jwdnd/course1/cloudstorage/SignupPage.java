package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//div[@contains(@class, 'alert')]")
    private WebElement alert;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getAlert() {
        return alert;
    }

    public void doSignup(String firstname, String lastname, String username, String password) {
        inputFirstName.sendKeys(firstname);
        inputLastName.sendKeys(lastname);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        inputPassword.submit();
    }

    public void goToLoginPage() {
        alert.findElement(By.xpath("//a")).click();
    }
}