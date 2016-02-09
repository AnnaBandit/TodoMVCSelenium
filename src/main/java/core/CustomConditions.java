package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static core.ConsiseAPI.$;
import static core.ConsiseAPI.$$;

public class CustomConditions{

    public static ExpectedCondition<Boolean> textOf(final By elementLocator, final String text) {
        return new ExpectedCondition<Boolean>() {
            String actualText;
            WebElement element;

            public Boolean apply(WebDriver driver) {
                element = $(elementLocator);
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
            private int listSize;
            private String[] actualTexts;
            private List<WebElement> elements;

            public List<WebElement> apply(WebDriver driver) {
                elements = $$(elementsLocator);
                listSize = elements.size();
                actualTexts = new String[elements.size()];

                for (int i = 0; i < listSize; i++) {
                    actualTexts[i]=(elements.get(i).getText());
                }

                if (listSize!=texts.length){
                    return null;
                }

                else {
                    for (int i = 0; i < listSize; i++) {
                        String actualText = actualTexts[i];
                        if (!actualText.contains(texts[i])) {
                            return null;
                        }
                    }
                    return elements;
                }
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
            private int listSize;
            private String[] actualTexts;

            public List<WebElement> apply(WebDriver driver) {
                elements = $$(elementsLocator);

                for (WebElement element: elements){
                    if (element.isDisplayed()){
                        visibleElements.add(element);
                    }
                }

                listSize = visibleElements.size();
                actualTexts = new String[visibleElements.size()];

                for (int i = 0; i < listSize; i++) {
                    actualTexts[i]=(visibleElements.get(i).getText());
                }

                if (listSize!=texts.length){
                    return null;
                }

                else {
                    for (int i = 0; i < listSize; i++) {
                        String actualText = actualTexts[i];
                        if (!actualText.contains(texts[i])) {
                            return null;
                        }
                    }
                    return visibleElements;
                }
            }

            public String toString(){
                return String.format("Actual texts: %s. Expected texts: %s", visibleElements, texts);
            }

        };
    }

    public static ExpectedCondition<Boolean> empty(final By elementsLocator) {
        return new ExpectedCondition<Boolean>() {
            private List<WebElement> elements;

            public Boolean apply(WebDriver driver) {
                elements = $$(elementsLocator);
                return elements.isEmpty();
            }

            public  String toString(){
                return String.format("List is not empty. List: %s", elements);
            };
        };
    }

    public static ExpectedCondition<Boolean> visibleElementsListIsEmpty(final By elementsLocator) {
        return new ExpectedCondition<Boolean>() {
            List<WebElement> visibleList;
            List<WebElement> elements;

            public Boolean apply(WebDriver driver) {
                elements = $$(elementsLocator);

                for (WebElement element: elements){
                    if (element.isDisplayed()){
                        visibleList.add(element);
                    }
                }

                return visibleList == null;
            }

            public  String toString(){
                return String.format("List is not empty. List: %s", visibleList);
            };
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

    public static ExpectedCondition<WebElement> listElementWithText(final By elementsLocator, final String text) {
        return new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;

            public WebElement apply(WebDriver driver) {
                elements = $$(elementsLocator);

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
}