package automationFramework;


import appModules.Mail_SignIn_Action;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.Kufar_SearchResults_Page;
import pageObjects.Mail_LogIn_Page;
import pageObjects.Kufar_Home_Page;
import utility.Constant;
import utility.ExcelUtils;
import utility.Log;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

public class Kufar_Robot_docker {

    public RemoteWebDriver driver;
    private WebDriverWait wait;
    private int numberOfSearchResults;
    private String a;
    private String filingDate;
    private String filingTime;
    private String currentDate;
    private Boolean isPresent;
    private WebElement searchPoint;
    private ArrayList<String> tabs;
    private List<WebElement> searchResults;  
    Actions actions;

    @BeforeClass
    public void start() throws Exception {//
        ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"Лист1");
        Log.info("Модуль чтения excel-файлов подключен");

        //запуск WebDriver в режиме RemoteWebDriver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("80.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);

        driver = new RemoteWebDriver(
                URI.create("http://192.168.1.6:4444/wd/hub").toURL(),
                capabilities
        );
        Log.info("RemoteWebDriver инициализирован");
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().setSize(new Dimension(1920,1080));
        Log.info("Установили заданный размер окна браузера");
    }

    @Test //(retryAnalyzer = Retry.class)
    public void getBasicInfo() throws Exception {
        driver.get(Constant.URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(Kufar_Home_Page.KUFAR_SEARCHBAR));
        Log.info("Открыли страницу Kufar");
        Kufar_Home_Page.searchbar(driver).sendKeys("форматно" + Keys.ENTER);
        Log.info("Ввели поисковый запрос");
        //Thread.sleep используется только в целях дебага
        Thread.sleep(2000);
        Kufar_Home_Page.lnk_machineryCategory(driver).click();
        Log.info("Кликнули по ссылке Станки и оборудование");
        //Thread.sleep используется только в целях дебага
        Thread.sleep(2000);
        //определение списка выдач на странице
        searchResults = driver.findElements(Kufar_SearchResults_Page.KUFAR_SEARCH_RESULT_ITEM);
        //сохраняем количество выдач
        numberOfSearchResults = searchResults.size();
        //проходим по пунктам меню
        for (int i = 0; i < numberOfSearchResults; i++) {
            searchResults = driver.findElements(Kufar_SearchResults_Page.KUFAR_SEARCH_RESULT_ITEM);
            searchPoint = searchResults.get(i);
            a = searchPoint.findElement(By.cssSelector(".k-Hixp-3cfa3")).getText();
            //разделяем дату и время
            int idx = a.lastIndexOf(' ');
            if (idx == -1)
                throw new IllegalArgumentException("Only a single name: " + a);
            filingDate = a.substring(0, idx);
            filingTime = a.substring(idx + 1);

            //проверяем filingDate на наличие лишнего лишнего символа и удаляем
            try{
                Scanner in = new Scanner(System.in);
                String delete;
                delete = ",";
                filingDate = filingDate.replace (delete, "");
            }catch (Exception e){
                throw(e);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM.");
            Calendar calendar = new GregorianCalendar();
            currentDate = dateFormat.format(calendar.getTime());
            System.out.println(filingDate);
            System.out.println(currentDate);
            System.out.println();

//            //кликаем по меню
//            searchPoint.click();
//
//            tabs = new ArrayList<String>(driver.getWindowHandles());
//            driver.switchTo().window(tabs.get(1));
//            Thread.sleep(2000);
//            driver.close();
//            driver.switchTo().window(tabs.get(0));


//            driver.navigate().back();
//            Thread.sleep(2000);


//            Boolean isPresent = driver.findElements(Hubspot_Contacts_Page.HUBSPOT_NUMBER_OF_CONTACTS).size() > 0;
//            if (isPresent)
//            {
//                driver.findElement(Hubspot_Contacts_Page.HUBSPOT_NUMBER_OF_CONTACTS).click();
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-selenium-test=profile-settings-actions-btn]")));
//                driver.findElement(By.cssSelector("[data-selenium-test=profile-settings-actions-btn]")).click();
//
//                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-selenium-test*=delete]")));
//                Thread.sleep(500);
//                driver.findElement(By.cssSelector("[data-selenium-test*=delete]")).click();
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-selenium-test=delete-dialog-confirm-button]")));
//                driver.findElement(By.cssSelector("[data-selenium-test=delete-dialog-confirm-button]")).click();
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".empty-state-message, .private-link.uiLinkWithoutUnderline.uiLinkDark.truncate-text.align-center")));
//
//            } else {
//
//            }
        }

        //Thread.sleep используется только в целях дебага
        Thread.sleep(3000);


        ((JavascriptExecutor) driver).executeScript("window.open('https://mail.ru/','_blank')");
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Log.info("Выполнили скрипт для открытия новой вкладки для Mail.ru");
        wait.until(ExpectedConditions.presenceOfElementLocated(Mail_LogIn_Page.MAIL_USER_NAME));
        Mail_SignIn_Action.Execute(driver);
        Thread.sleep(4000);
    }


    @AfterMethod
    public void finish(ITestResult result) {
        try
        {
            if(result.getStatus() == ITestResult.SUCCESS)
            {

                System.out.println("passed **********");
            }
            else if(result.getStatus() == ITestResult.FAILURE)
            {
                File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File screen = new File("screen-" + System.currentTimeMillis() + ".png");
                try {
                    Files.copy(tempFile,screen);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Failed ***********" + screen);
            }
            else if(result.getStatus() == ITestResult.SKIP ){
                System.out.println("Skiped***********");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.DRIVER);
        for (LogEntry logEntry : logEntries) {
//            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            System.out.println(logEntry.getMessage());
            //do something useful with the data
        }
    }

    @AfterClass
    public void end () {
        driver.quit();
        driver = null;
    }
}