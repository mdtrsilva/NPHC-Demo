package stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.sikuli.script.FindFailed;
import pageObjects.HomePage;
import utilities.ColorUtils;
import utilities.FileUpload;
import utilities.WaitHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import java.text.ParseException;
import java.util.Properties;

public class Steps extends BaseClass {

    public WebDriver driver;
    public HomePage hp;
    //public static Logger logger;

    @Before
    public void setup() throws IOException {

        configProp=new Properties();
        FileInputStream configPropfile=new FileInputStream("config.properties");
        configProp.load(configPropfile);


        logger = Logger.getLogger("MPHC Application");
        PropertyConfigurator.configure("log4j.properties");

        String os=configProp.getProperty("os");
        String browser=configProp.getProperty("browser");
        if (browser.equals("chrome")) {
            if(os.equals("mac"))
                System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath_mac"));
            else if(os.equals("windows")){
                System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath_windows"));
            }
        }
        else
        {
            System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
        }
        logger.info("***** Launcing Browser *****");
        driver = new ChromeDriver();

    }

    @Given("launch the browser")
    public void launch_the_browser() {

        hp = new HomePage(driver);

    }

    @When("open the url")
    public void open_the_url() {
        String url=configProp.getProperty("url");
        logger.info("***** Opening URL *****");
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Then("Page title should be {string}")
    public void page_title_should_be(String title) {
        logger.info("***** Verifying Home Page *****");
        Assert.assertEquals(title, driver.getTitle());
    }

    @When("click browse button")
    public void click_browse_button() {
        logger.info("***** Clicking the browse button *****");
        hp.clickBrowseButton();

    }

    @When("upload the csv file")
    public void upload_the_csv_file() throws IOException, InterruptedException, FindFailed {
        logger.info("***** Uploading CSV file *****");
        String os=configProp.getProperty("os");
        if(os.equals("mac")) {
            //Need to keep a csv file with the name "persons.csv" on the desktop
            Thread.sleep(10000);
            Runtime.getRuntime().exec("osascript " + ".//resources/persons.scpt");
        }
        if(os.equals("windows")){
            FileUpload.fileUpload();
        }
    }

    @Then("verify the file upload status")
    public void verify_file_upload() throws InterruptedException {
        logger.info("***** Verifying file upload*****");
        System.out.println("%%%%%%");
        Thread.sleep(10000);
        hp.clickTaxReliefButton();
        WebElement element=driver.findElement(By.xpath("//table[@class='table table-hover table-dark']"));
        WaitHelper wait=new WaitHelper(driver);
        wait.waitForElement(element,10);
        hp.checkTaxPayerstable();


    }


    @When("close the browser")
    public void close_the_browser(){
        logger.info("***** Closing the Browser *****");
        driver.quit();
    }


    // Book Keeper


    @Then("click TaxReliefTable button")
    public void click_TaxReliefTable_button(){
        logger.info("***** Clicking  the Tax Relief Table Button *****");
        hp.clickTaxReliefButton();

    }


    @Then("tax players details should be displayed")
    public void Taxplayer_info_validation(){

        hp.checkTaxPayersAttributes();
    }


    @Then("natid should be masked")
    public void Taxplayer_natId_maskingCheck(){

        hp.checkNatIDMasking();
    }

    @Then("tax amount should be accurate {string}, {double}")
    public void tax_amount_should_be_accurate(String natid, Double relief) {
        hp.taxReliefValidation(natid, relief);
    }

    @Then("the button should be displayed correctly")
    public void despense_button_verfification(){
        WebElement element=driver.findElement(By.xpath("//a[@class='btn btn-danger btn-block']"));
        String innerText=element.getText();
        Assert.assertEquals(innerText.equals("Dispense Now"),true);

        String color_rbga=element.getCssValue("background-color");
        String hexColor = Color.fromString(color_rbga).asHex();

        java.awt.Color c = ColorUtils.stringToColor(hexColor);
        String colorName=ColorUtils.getColorNameFromColor(c);

        Assert.assertEquals(colorName.equals("Crimson"),true);


    }

    @When("click on the button")
    public void click_on_the_button() {
        hp.clickBtnDespense();
    }

    @When("the landing page should be correct")
    public void the_landingpage_verification() {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Text not found!", bodyText.contains("Cash dispensed"));
    }




//    @Then("tax amount should be accurate")
//    public void Taxplayer_amount() throws ParseException {
//
//       // hp.checkTaxReleifCal("019-$$$$$$$",1100000.00,10000.00, "F", "15121940");
//        hp.taxReliefValidation("019-$$$$$$$", 1592000.00);
//
//    }




}
