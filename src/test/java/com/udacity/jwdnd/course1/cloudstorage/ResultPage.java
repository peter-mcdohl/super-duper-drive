package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage extends BasePage {

    public String xpathContainer = "//div[contains(@class, 'container')]";

    @FindBy(xpath = "//div[contains(@class, 'alert')]//a")
    WebElement anchorContinue;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickContinue() {
        anchorContinue.click();
    }

}
