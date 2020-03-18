package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utility.Log;

public class Kufar_Home_Page {

    private static WebElement element = null;
    public static final By KUFAR_SEARCHBAR = By.id("kufar-searchbar-header");
    public static final By KUFAR_MACHINERY_CATEGORY = By.cssSelector(".k-gU-ec1e1.sb-KA-beac6");


    public static WebElement searchbar (WebDriver driver){
        driver.findElement(KUFAR_SEARCHBAR).click();
        element = driver.findElement(KUFAR_SEARCHBAR);
        Log.info("Нашли поле поиска");
        return element;
    }

    public static WebElement lnk_machineryCategory (WebDriver driver){
        element = driver.findElement(KUFAR_MACHINERY_CATEGORY);
        Log.info("Нашли категорию Станки и оборудование");
        return element;
    }
}