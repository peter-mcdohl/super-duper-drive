package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    private static final int TIMEOUT = 5;

    protected WebElement waitUntil(WebDriver webDriver, String xpath) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, TIMEOUT);
        return webDriverWait.until(driver -> driver.findElement(By.xpath(xpath)));
    }
}
