import org.junit.Test;
import org.openqa.selenium.Keys;
import testconfigs.BaseTest;

import static pages.ToDoMVC.*;


public class ToDoMVCTest extends BaseTest {

    @Test
    public void testDeleteWhileEditing(){
        add("1", "2");
        startEdit("1", "").sendKeys(Keys.ENTER);
        assertExistingTasks("2");
        assertItemsLeft(1);
    }

    @Test
    public void testCancelEditingByESC(){
        add("1");
        startEdit("1", "1-edited").sendKeys(Keys.ESCAPE);
        assertExistingTasks("1");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAndSaveByClickingOutside(){
        add("1");
        startEdit("1", "1-edited");
        add("2");
        assertExistingTasks("1-edited", "2");
        assertItemsLeft(2);
    }

    @Test
    public void testActivateAllOnCompletedFilter(){
        // given on completed filter with all tasks completed
        add("1", "2");
        toggleAll();
        openCompletedFilter();
        assertVisibleTasks("1", "2");
        assertItemsLeft(0);

        // activate All
        toggleAll();
        assertVisibleTasksListIsEmpty();
        assertItemsLeft(2);
        openActiveFilter();
        assertVisibleTasks("1", "2");
    }

    @Test
    public void testTasksMainFlow(){
        add("1");
        toggle("1");

        openActiveFilter();
        assertVisibleTasksListIsEmpty();

        add("2");
        startEdit("2", "2-edited").sendKeys(Keys.ENTER);
        assertVisibleTasks("2-edited");

        //complete All
        toggleAll();
        assertVisibleTasksListIsEmpty();
        openCompletedFilter();
        assertVisibleTasks("1", "2-edited");

        //activate
        toggle("2-edited");
        assertVisibleTasks("1");

        clearCompleted();
        assertVisibleTasksListIsEmpty();

        openAllFilter();
        delete("2-edited");
        assertExistingTasksListIsEmpty();
    }

}