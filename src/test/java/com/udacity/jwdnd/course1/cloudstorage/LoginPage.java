package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id="inputUsername")
    private WebElement inputUsername;

    @FindBy(id="inputPassword")
    private WebElement inputPassword;

    @FindBy(xpath = "//input[type='submit']")
    private WebElement buttonSubmit;

    @FindBy(xpath = "//label/a")
    private WebElement anchorRegister;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void goToSignupPage() {
        anchorRegister.click();
    }

    public void doLogin(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        inputPassword.submit();
    }
}
