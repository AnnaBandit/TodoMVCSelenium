package core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static core.CustomConditions.minimumSizeOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class ConsiseAPI {
    static HashMap<Integer, WebDriver> drivers = new HashMap<Integer, WebDriver>();

    public static WebDriver getDriver() {
        return drivers.get(Thread.currentThread().hashCode());
    }

    public static void setDriver(WebDriver driver) {
        drivers.put(Thread.currentThread().hashCode(), driver);
    }

    public static <V> V assertThat(ExpectedCondition<V> condition, int timeout){
        return new WebDriverWait(getDriver(), timeout).until(condition);
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
        return $(conditionToWaitParentElement, byCss(innerElementCssSelector));
    }

    public static WebElement $(ExpectedCondition<WebElement> conditionToWaitParentElement, By innerElementLocator){
        return assertThat(conditionToWaitParentElement).findElement(innerElementLocator);
    }

    public static WebElement $(By locatorOfParentElement, String... cssSelectorsOfInnerElements){
        WebElement element = $(locatorOfParentElement);
        for(int i=0;i<cssSelectorsOfInnerElements.length;i++){
            element = element.findElement(byCss(cssSelectorsOfInnerElements[i]));
        }
        return element;
    }

    public static WebElement $(String cssSelectorOfParentElement, String... cssSelectorsOfInnerElements){
        return $(byCss(cssSelectorOfParentElement), cssSelectorsOfInnerElements);
    }

    public static List<WebElement> $$(String cssSelector){
        return $$(byCss(cssSelector));
    }

    public static List<WebElement> $$(By locator){
        return getDriver().findElements(locator);
    }

    public static WebElement get(By listLocator, int index){
        return assertThat(minimumSizeOf(listLocator, index + 1)).get(index);
    }

    public static By byCss(String cssSelector){
        return By.cssSelector(cssSelector);
    }

    public static By byText(String text) {
        return (By.xpath("//div[contains(text(), '" + text + "')]"));
    }

    public static void open(String url){
        getDriver().get(url);
    }

    public static String title(){
        return getDriver().getTitle();
    }

    public static void doubleClick(WebElement element){
        Actions action = new Actions(getDriver());
        action.doubleClick(element).perform();
    }

    public static void executeJavaScript(String javaScript){
        if (getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) getDriver())
                    .executeScript(javaScript);
        }
    }

    public static WebElement setValue(WebElement element, String value){
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public static List<WebElement> filteredByVisible(List<WebElement> elements){
        List<WebElement> visibleList = new ArrayList<WebElement>();

        for (WebElement element: elements){
            if (element.isDisplayed()){
                visibleList.add(element);
            }
        }

        return visibleList;
    }

    public static boolean hasTexts(List<WebElement> elements, String... expectedTexts){
        int listSize = elements.size();
        String[] actualTexts = getTexts(elements);

        if (listSize!=expectedTexts.length){
            return false;
        }

        for (int i = 0; i < listSize; i++) {
            String actualText = actualTexts[i];
            if (!actualText.contains(expectedTexts[i])) {
                return false;
            }
        }
        return true;
    }

    public static String[] getTexts(List<WebElement> elements){
        String[]texts = new String[elements.size()];
        for (int i=0;i<elements.size();i++){
            texts[i]=elements.get(i).getText();
        }
        return texts;
    }
}


