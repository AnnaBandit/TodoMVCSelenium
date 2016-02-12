package core;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.ConsiseAPI.*;

public class CustomConditions{

    public static ExpectedCondition<Boolean> textOf(final By elementLocator, final String text) {
        return new ExpectedCondition<Boolean>() {
            String actualText;
            WebElement element;

            public Boolean apply(WebDriver driver) {
                element = driver.findElement(elementLocator);
                actualText = element.getText();
                return actualText.equals(text);
            }

            public  String toString(){
                return String.format("Actual text is %s. Expected text is %s", actualText, text);
            };
        };
    }

    public static ExpectedCondition<List<WebElement>> textsOf(final By elementsLocator, final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private String[] actualTexts;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                actualTexts = getTexts(elements);

                return hasTexts(elements, texts);
            }

            public String toString(){
                return String.format("Expected texts: %s,\nActual texts: %s", Arrays.toString(texts), Arrays.toString(actualTexts));
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final By elementsLocator, final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private List<WebElement> visibleElements = new ArrayList<WebElement>();
            private List<WebElement> elements;
            private String[] actualTexts;

            public List<WebElement> apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                visibleElements = filteredByVisible(elements);
                actualTexts = getTexts(visibleElements);

                return hasTexts(visibleElements, texts);
            }

            public String toString(){
                return String.format("Expected texts: %s,\nActual texts: %s", Arrays.toString(texts), Arrays.toString(actualTexts));
            }

        };
    }

    public static ExpectedCondition<Boolean> hidden(final By elementLocator) {
        return new ExpectedCondition<Boolean>() {
            private WebElement element;

            public Boolean apply(WebDriver driver) {
                element = driver.findElement(elementLocator);
                return !element.isDisplayed();
            }

            public  String toString(){
                return String.format("Element %s is not hidden", element);
            };
        };
    }

    public static ExpectedCondition<Boolean> hidden(final String elementLocator) {
        return new ExpectedCondition<Boolean>() {
            private WebElement element;

            public Boolean apply(WebDriver driver) {
                element = driver.findElement(byCss(elementLocator));
                return !element.isDisplayed();
            }

            public  String toString(){
                return String.format("Element %s is not hidden", element);
            };
        };
    }

    public static ExpectedCondition<WebElement> listElementWithText(final By elementsLocator, final String text) {
        return new ExpectedCondition<WebElement>() {
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
                return String.format("There are no elements with text %s in the list %s", text, elements);
            };
        };
    }

    public static ExpectedCondition <List<WebElement>> minimumSizeOf(final By elementsLocator, final int expectedSize) {
        return new ExpectedCondition <List<WebElement>> () {
            private List<WebElement> elements;
            private int size;

            public List<WebElement>  apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                size = elements.size();
                return size>expectedSize ? elements : null;
            }

            public String toString() {
                return String.format("Actual List size is %s. Expected size: more than %s", size, expectedSize);
            }
        };
    }

    public static ExpectedCondition <List<WebElement>> sizeOf(final By elementsLocator, final int expectedSize) {
        return new ExpectedCondition <List<WebElement>> () {
            private List<WebElement> elements;
            private int size;

            public List<WebElement>  apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);
                size = elements.size();
                return size == expectedSize ? elements : null;
            }

            public String toString() {
                return String.format("Actual List size is %s. Expected size: %s", size, expectedSize);
            }
        };
    }

    public static ExpectedCondition <List<WebElement>> sizeOfVisible(final By elementsLocator, final int expectedSize) {
        return new ExpectedCondition <List<WebElement>> () {
            private List<WebElement> elements;
            private int size;

            public List<WebElement>  apply(WebDriver driver) {
                elements = filteredByVisible(driver.findElements(elementsLocator));
                size = elements.size();
                return size == expectedSize ? elements : null;
            }

            public String toString() {
                return String.format("Actual List size is %s. Expected size: %s", size, expectedSize);
            }
        };
    }

    public static ExpectedCondition <WebElement> elementInList(final By elementsLocator, final By elementLocator) {
        return new ExpectedCondition <WebElement> () {
            private List<WebElement> elements;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);

                try {
                    for (WebElement element: elements) {
                        return element.findElement(elementLocator);
                    }
                } catch (NoSuchElementException ex) {
                    return null;
                }
                return null;
            }

            public String toString() {
                return String.format("There is no element with %s locator in the list %s", elementLocator, elements);
            }
        };
    }

    public static ExpectedCondition<WebElement> listElementWithCssClass (final By elementsLocator, final String cssClass) {
        return new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;

            public WebElement apply(WebDriver driver) {
                elements = driver.findElements(elementsLocator);

                for (WebElement element: elements){
                    if (element.getAttribute("class").contains(cssClass)){
                        return element;
                    }
                }
                return null;
            }

            public  String toString(){
                return String.format("There are no elements have cssClass %s in the list %s", cssClass, elements);
            };
        };
    }
}