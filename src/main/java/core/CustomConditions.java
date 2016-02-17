package core;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.ConsiseAPI.*;

public class CustomConditions{

    public static ExpectedCondition<Boolean> textOf(final By elementLocator, final String text) {

        return elementExceptionsCatcher(new ExpectedCondition<Boolean>() {
            String actualText;
            WebElement element;

            public Boolean apply (WebDriver driver){
                element = driver.findElement(elementLocator);
                actualText = element.getText();
                return actualText.equals(text);
            }

            public  String toString(){
                return String.format("Expected text is %s.\nActual text is %s.", text, actualText);
            };
        });
    }

    public static ExpectedCondition<List<WebElement>> textsOf(final By elementsLocator, final String... texts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private String[] actualTexts;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                actualTexts = getTexts(elements);

                if (hasTexts(elements, texts)) {
                    return elements;
                }

                return null;
            }

            public String toString() {
                return String.format("Expected texts: %s,\nActual texts: %s", Arrays.toString(texts), Arrays.toString(actualTexts));
            }
        });
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final By elementsLocator, final String... texts) {
        return elementExceptionsCatcher(new ExpectedCondition<List<WebElement>>() {
            private List<WebElement> visibleElements = new ArrayList<WebElement>();
            private List<WebElement> elements;
            private String[] actualTexts;

            public List<WebElement> apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                visibleElements = filteredByVisible(elements);
                actualTexts = getTexts(visibleElements);

                if (hasTexts(visibleElements, texts)){
                    return visibleElements;
                }
                return null;
            }

            public String toString(){
                return String.format("Expected texts: %s,\nActual texts: %s", Arrays.toString(texts), Arrays.toString(actualTexts));
            }
        });
    }

    public static ExpectedCondition<Boolean> hidden(final By elementLocator) {
        return elementExceptionsCatcher(new ExpectedCondition<Boolean>() {
            private WebElement element;

            public Boolean apply(WebDriver driver) {
                element = driver.findElement(elementLocator);
                return !element.isDisplayed();
            }

            public  String toString(){
                return String.format("Element found by locator \"%s\" is not hidden", elementLocator);
            };
        });
    }

    public static ExpectedCondition<Boolean> hidden(final String elementLocator) {
        return hidden(byCss(elementLocator));
    }

    public static ExpectedCondition<WebElement> listElementWithText(final By elementsLocator, final String text) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);

                for (WebElement element: elements){
                    if (element.getText().equals(text)){
                        return element;
                    }
                }
                return null;
            }

            public  String toString(){
                return String.format("There are no elements with text \"%s\" in the list found by \"%s\"", text, elementsLocator);
            };
        });
    }

    public static ExpectedCondition <List<WebElement>> minimumSizeOf(final By elementsLocator, final int expectedSize) {
        return elementExceptionsCatcher(new ExpectedCondition <List<WebElement>> () {
            private List<WebElement> elements;
            private int size;

            public List<WebElement>  apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                size = elements.size();
                return size>expectedSize ? elements : null;
            }

            public String toString() {
                return String.format("Expected size: more than %s.\nActual List size is %s.", expectedSize, size);
            }
        });
    }

    public static ExpectedCondition <List<WebElement>> sizeOf(final By elementsLocator, final int expectedSize) {
        return elementExceptionsCatcher(new ExpectedCondition <List<WebElement>> () {
            private List<WebElement> elements;
            private int size;

            public List<WebElement>  apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                size = elements.size();
                return size == expectedSize ? elements : null;
            }

            public String toString() {
                return String.format("Expected size: %s.\nActual List size is %s.", expectedSize, size);
            }
        });
    }

    public static ExpectedCondition <List<WebElement>> empty(final By elementsLocator){
        return sizeOf(elementsLocator, 0);
    }

    public static ExpectedCondition <List<WebElement>> sizeOfVisible(final By elementsLocator, final int expectedSize) {
        return elementExceptionsCatcher(new ExpectedCondition <List<WebElement>> () {
            private List<WebElement> elements;
            private int size;

            public List<WebElement>  apply(WebDriver driver) {
                elements = filteredByVisible(driver.findElements(elementsLocator));
                size = elements.size();
                return size == expectedSize ? elements : null;
            }

            public String toString() {
                return String.format("Expected size: %s.\nActual List size is %s.", expectedSize, size);
            }
        });
    }


    public static ExpectedCondition<WebElement> listElementWithCssClass (final By elementsLocator, final String cssClass) {
        return elementExceptionsCatcher(new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);

                for (WebElement element: elements){
                    String[] classAtributes = element.getAttribute("class").split(" ");
                    for(int i=0;i<classAtributes.length;i++){
                        if (classAtributes[i].equals(cssClass)){
                            return element;
                        }
                    }
                }
                return null;
            }

            public  String toString(){
                return String.format("There are no elements have cssClass \"%s\" in the list found by \"%s\" locator", cssClass, elementsLocator);
            }
        });
    }

    public static <V> ExpectedCondition<V> elementExceptionsCatcher(final Function<? super WebDriver, V> condition){
        return new ExpectedCondition<V>() {
            public V apply(WebDriver input) {
                try {
                    return condition.apply(input);
                } catch (StaleElementReferenceException e) {
                    return null;
                } catch (ElementNotVisibleException e){
                    return null;
                }
            }

            public String toString(){
                return condition.toString();
            }
        };
    }
}