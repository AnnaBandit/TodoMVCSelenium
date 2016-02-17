package testconfigs;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.firefox.FirefoxDriver;

import static core.ConsiseAPI.*;

public class BaseTest {

    @Before
    public void setUp(){
        setDriver(new FirefoxDriver());
    }

    @After
    public void tearDown(){
        getDriver().quit();
    }
}
