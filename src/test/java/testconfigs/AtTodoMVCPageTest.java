package testconfigs;

import org.junit.Before;

import static core.ConsiseAPI.open;

public class AtTodoMVCPageTest extends BaseTest{

    @Before
    public void openToDoMVC(){
    open("https://todomvc4tasj.herokuapp.com/");
    }
}
