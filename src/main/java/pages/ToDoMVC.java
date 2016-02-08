package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static core.ConsiseAPI.*;
import static core.CustomConditions.*;

public class ToDoMVC {
    public static By tasksList = byCss("#todo-list li");
    public static By activeTasks = byCss("#todo-list li.active");

    public static void add(String... tasksTexts){
        for(String taskText: tasksTexts){
            $("#new-todo").sendKeys(taskText + Keys.ENTER);
        }
    }

    public static void assertVisibleTasks(String... tasksTexts){
        assertThat(visibleTextsOf($$(activeTasks), tasksTexts));
    }

    public static void assertVisibleTasksListIsEmpty(){
        assertThat(listIsEmpty($$(activeTasks)));
    }

    public static void assertExistingTasks(String... tasksTexts){
        textsOf(tasksList, tasksTexts);
    }

    public static void assertExistingTasksListIsEmpty(){
        assertThat(isEmpty($$(tasksList)));
    }

    public static WebElement startEdit(String taskText, String newTaskText){
        Actions action = new Actions(getDriver());
        action.doubleClick($(listElementWithText($$(tasksList), taskText), "label")).perform();
        getDriver().findElement(byCss(".editing")).findElement(byCss(".edit")).clear();
        getDriver().findElement(byCss(".editing")).findElement(byCss(".edit")).sendKeys(newTaskText);
        return getDriver().findElement(byCss(".editing")).findElement(byCss(".edit"));
    }

    public static void delete(String taskText){
        $(listElementWithText($$(tasksList), taskText), ".destroy").click();
    }

    public static void toggle(String taskText){
        $(listElementWithText($$(tasksList), taskText), ".toggle").click();
    }

    public static void toggleAll(){
        $("#toggle-all").click();
    }

    public static void clearCompleted(){
        $("#clear-completed").click();
        assertThat(hidden($("#clear-completed")));
    }

    public static void assertItemsLeft(Integer counter){
        assertThat(textOf($("#todo-count>strong"), String.valueOf(counter)));
    }

    public static void openAllFilter(){
        $(By.linkText("All")).click();
    }

    public static void openActiveFilter(){
        $(By.linkText("Active")).click();
    }

    public static void openCompletedFilter(){
        $(By.linkText("Completed")).click();
    }
}
