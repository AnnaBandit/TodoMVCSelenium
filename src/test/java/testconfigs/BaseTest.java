package testconfigs;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConsiseAPI.*;

public class BaseTest {

    @BeforeClass
    public static void setUp(){
        setDriver(new FirefoxDriver());
    }

    @AfterClass
    public static void tearDown(){
        getDriver().quit();
    }

    @Before
    public void openApp(){
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData(){
        if (getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) getDriver())
                    .executeScript("localStorage.clear()");
        }
    }

}
