package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

import static core.ConsiseAPI.$$;
import static core.ConsiseAPI.assertThat;

public class CustomConditions{

    public static ExpectedCondition<WebElement> listNthElementHasText(final By elementsLocator, final int index, final String expectedText) {
        return new ExpectedCondition<WebElement>() {
            private List<WebElement> elements;
            private WebElement element;
            private String actualText;

            public WebElement apply(WebDriver driver) {
                try {
                    elements = driver.findElements(elementsLocator);
                    element = elements.get(index);
                    actualText = element.getText();

                    return element.getText().contains(expectedText) ? element : null;
                } catch (IndexOutOfBoundsException ex){
                    return null;
                }
            }

            public String toString() {
                return String.format("Actual text on %s element is %s, \nExpected text is %s", index, actualText, expectedText);
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> sizeOf(final List<WebElement> elements, final int expectedSize) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;

            public List<WebElement> apply(WebDriver driver) {
                listSize = elements.size();
                return elements.size() == expectedSize ? elements : null;
            }

            public String toString(){
                return String.format("Actual size of the list is %s,\nexpected size is %s", listSize, expectedSize);
            }
        };
    }

    public static ExpectedCondition<List<WebElement>> textsOf(final List<WebElement> elements, final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            private int listSize;
            private String[] actualTexts;

            public List<WebElement> apply(WebDriver driver) {
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

    public static ExpectedCondition<List<WebElement>> textsOf(final By elementsLocator, final String... texts) {
        return textsOf($$(elementsLocator), texts);
    }

    public static ExpectedCondition<List<WebElement>> visibleTextsOf(final List<WebElement> elements, final String... texts) {
        return new ExpectedCondition<List<WebElement>>() {
            List<WebElement> visibleList = null;

            public List<WebElement> apply(WebDriver driver) {

                try{
                    for (WebElement element: elements){
                        if (element.isDisplayed()){
                            visibleList.add(element);
                        }
                    }

                    return assertThat(textsOf(visibleList, texts));
                } catch (NullPointerException ex){
                    return null;
                }
            }

            public  String toString(){
                return String.format("Actual texts: %s. Expected texts: %s", visibleList, texts);
            }

        };
    }


    public static ExpectedCondition <WebElement> visible(final By locator) {
        return ExpectedConditions.visibilityOfElementLocated(locator);
    }


    public static ExpectedCondition<Boolean> isEmpty(final List<WebElement> elements) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return elements.isEmpty();
            }

            public  String toString(){
                return String.format("List is not empty. List: %s", elements);
            };
        };
    }

    public static ExpectedCondition<Boolean> hidden(final WebElement element) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return !element.isDisplayed();
            }

            public  String toString(){
                return String.format("Element %s is not hidden", element);
            };
        };
    }

    public static ExpectedCondition<Boolean> textOf(final WebElement element, final String text) {
        return new ExpectedCondition<Boolean>() {
            String actualText;

            public Boolean apply(WebDriver driver) {
                actualText = element.getText();
                return actualText.equals(text);
            }

            public  String toString(){
                return String.format("Actual text is %s. Expected text is %s", actualText, text);
            };
        };
    }

    public static ExpectedCondition<WebElement> listElementWithText(final List<WebElement> elements, final String text) {
        return new ExpectedCondition<WebElement>() {

            public WebElement apply(WebDriver driver) {
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

    public static ExpectedCondition<Boolean> listIsEmpty(final List<WebElement> elements) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return elements.size() == 0;
            }

            public String toString(){
                return String.format("List %s is not empty!", elements);
            }
        };
    }

}