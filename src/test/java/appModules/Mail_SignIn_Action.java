package appModules;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Mail_LogIn_Page;
import utility.ExcelUtils;
import utility.Log;


public class Mail_SignIn_Action {


    public static void Execute(WebDriver driver) throws Exception{

        //This is to get the values from Excel sheet, passing parameters (Row num &amp; Col num)to getCellData method

        String sUserName = ExcelUtils.getCellData(1,1);

        String sPassword = ExcelUtils.getCellData(1,2);

        WebDriverWait wait = new WebDriverWait(driver, 10);

//        Home_Page.lnk_MyAccount(driver).click();

        // Enter sUsername variable in place of hardcoded value
        Mail_LogIn_Page.txtbx_UserName(driver).sendKeys(sUserName);
        wait.until(ExpectedConditions.presenceOfElementLocated(Mail_LogIn_Page.MAIL_USERNAME_NEXT));
        Log.info("Ввели имя пользователя на странице логина Mail.ru");


        Mail_LogIn_Page.btn_UserName_Next(driver).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Mail_LogIn_Page.MAIL_PASS));
        Log.info("Нажали на кнопку Ввести пароль");

        // Enter sPassword variable in place of hardcoded value
        Mail_LogIn_Page.txtbx_Password(driver).sendKeys(sPassword);
        Log.info("Ввели пароль на странице логина Mail.ru");

        Mail_LogIn_Page.btn_Password_Next(driver).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(Mail_LogIn_Page.MAIL_PASS));
        Log.info("Нажали на кнопку Войти");
    }
}