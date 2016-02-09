package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsiseAPI<T> {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        ConsiseAPI.driver = driver;
    }

    public static <V> V assertThat(ExpectedCondition<V> condition, int timeout){
        return new WebDriverWait(driver, timeout).until(condition);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition){
        return assertThat(condition, Configuration.timeout);
    }

    public static WebElement $(String cssSelector){
        return $(byCss(cssSelector));
    }

    public static WebElement $(By locator){
        return assertThat(visibilityOfElementLocated(locator));
    }

    public static WebElement $(WebElement element){
        return assertThat(visibilityOf(element));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, String innerElementCssSelector){
        return assertThat(conditionToWaitParentElement).findElement(byCss(innerElementCssSelector));
    }

    public static List<WebElement> $$(String cssSelector){
        return $$(byCss(cssSelector));
    }

    public static List<WebElement> $$(By locator){
        return driver.findElements(locator);
    }

    public static By byCss(String cssSelector){
        return By.cssSelector(cssSelector);
    }

    public static By byText(String text) {
        return (By.xpath("//div[contains(text(), '" + text + "')]"));
    }

    public static void open(String url){
        driver.get(url);
    }

    public static String title(){
        return driver.getTitle();
    }

    public static void doubleClick(WebElement element){
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();
    }

}
