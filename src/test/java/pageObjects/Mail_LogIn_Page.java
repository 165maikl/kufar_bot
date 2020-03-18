package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Mail_LogIn_Page {

    private static WebElement element = null;
    public static final By MAIL_USER_NAME = By.id("mailbox:login");
    public static final By MAIL_USERNAME_NEXT = By.id("mailbox:submit");
    public static final By MAIL_PASS = By.id("mailbox:password");
    public static final By MAIL_PASS_NEXT = By.id("mailbox:submit");





    public static WebElement txtbx_UserName(WebDriver driver){
        element = driver.findElement(MAIL_USER_NAME);
        return element;
    }

    public static WebElement btn_UserName_Next(WebDriver driver){
        element = driver.findElement(MAIL_USERNAME_NEXT);
        return element;
    }

    public static WebElement txtbx_Password(WebDriver driver){
        driver.findElement(MAIL_PASS).click();
        element = driver.findElement(MAIL_PASS);
        return element;
    }

    public static WebElement btn_Password_Next(WebDriver driver){
        element = driver.findElement(MAIL_PASS_NEXT);
        return element;
    }
}