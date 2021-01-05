package stepDefinitions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePage;

import java.util.Properties;

public class BaseClass {

    public WebDriver driver;
    public HomePage hp;
    public static Logger logger;
    public Properties configProp;

}
