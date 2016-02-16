package testconfigs;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConsiseAPI.*;

public class BaseTest {

    @BeforeClass
    public static void setUp(){

    }

    @AfterClass
    public static void tearDown(){
    }

    @Before
    public void openApp(){
        setDriver(new FirefoxDriver());
        open("https://todomvc4tasj.herokuapp.com/");
    }

    @After
    public void clearData(){
        executeJavaScript("localStorage.clear()");
        getDriver().quit();
    }
}
