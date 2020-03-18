package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utility.Log;

import java.util.List;

public class Kufar_SearchResults_Page {

    private static WebElement element = null;
    public static final By KUFAR_SEARCH_RESULT_ITEM = By.cssSelector(".k-HCxq-40555");

    public static WebElement searchResults (WebDriver driver){
        element = driver.findElement(KUFAR_SEARCH_RESULT_ITEM);
        Log.info("Сохранили в переменную searchResults список результатов поска");
        return element ;
    }
}