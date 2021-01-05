package pageObjects;

import org.decimal4j.util.DoubleRounder;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;

public class HomePage {

    public WebDriver ldriver;

    public HomePage(WebDriver rdriver){
        ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    @FindBy(xpath = "//div[@class='custom-file']")
    @CacheLookup
    WebElement btnBrowse;

    @FindBy(xpath = "//button[text()='Refresh Tax Relief Table']")
    WebElement btnTaxReliefTable;

    @FindBy(xpath = "//table[@class='table table-hover table-dark']")
    WebElement table;

    @FindBy(xpath = "//table[@class='table table-hover table-dark']/thead/tr/th")
    List<WebElement> tableColumns;

    @FindBy(xpath = "//table[@class='table table-hover table-dark']/tbody/tr")
    List<WebElement> tableRows;

    @FindBy(xpath = "//a[@class='btn btn-danger btn-block']")
    WebElement btnDispense;



    //action methods

    public void clickBrowseButton(){
        System.out.println("******");
        btnBrowse.click();
    }

    public void clickTaxReliefButton(){

        btnTaxReliefTable.click();
    }

    public int noOfRows(){
        return tableRows.size();
    }

    public int noOfColumns(){
        return tableColumns.size();
    }

    public void clickBtnDespense(){
        btnDispense.click();
    }


    public void checkTaxPayersAttributes(){
        for(int i=0; i<noOfColumns(); i++ ) {
            String header = tableColumns.get(i).getText();
            if(i==0){
                Assert.assertTrue(header.equals("NatId"));
            }
            else if (i == 1){
                Assert.assertTrue(header.equals("Relief"));
            }
//           else
//               Assert.assertTrue(header.equals("Name"));

        }

    }

    public void checkTaxPayerstable(){
        for(int i=0; i<noOfColumns(); i++ ) {
            String header = tableColumns.get(i).getText();
            if(i==0){
                Assert.assertTrue(header.equals("NatId"));
            }
            else if (i == 1){
                Assert.assertTrue(header.equals("Relief"));
            }


        }

    }

    public void checkNatIDMasking() {
        for (int i = 1; i < noOfRows(); i++) {
            String natId = table.findElement(By.xpath("//table[@class='table table-hover table-dark']/tbody/tr[" + i + "]/td[1]")).getText();
            int natIdLength=natId.length();
                for(int j=6; j<natIdLength; j++){
                    char aChar=natId.charAt(j);
                    Assert.assertTrue(aChar == '$');

            }


        }
    }

    public void taxReliefValidation(String natID, double tax_relif){
        HashMap<String, Double>map = new HashMap<String, Double>();

        for (int i = 0; i < noOfRows(); i++) {

            int q=i+1;
            String natId = table.findElement(By.xpath("//table[@class='table table-hover table-dark']/tbody/tr[" + q + "]/td[1]")).getText();

            if (natId.equals(natID))
            {
                double rel=Double.parseDouble(table.findElement(By.xpath("//table[@class='table table-hover table-dark']/tbody/tr[" + q + "]/td[2]")).getText());
                System.out.println(natID+":"+natId+":"+rel);
                map.put(natId, rel);
            }


        }
       Double relief=map.get(natID);
        //System.out.println("Relief"+relief);
        Assert.assertEquals(relief == tax_relif, true);
    }

    public void checkTaxReleifCal(String natID, double salary, double taxPaid, String gender, String dob) throws ParseException {
        HashMap<String, Double>map = new HashMap<String, Double>();
        for (int i = 1; i < noOfRows(); i++) {
            String natId = table.findElement(By.xpath("//table[@class='table table-hover table-dark']/tbody/tr[" + i + "]/td[1]")).getText();
            if (natId.equals(natID))
            {
                double rel=Double.parseDouble(table.findElement(By.xpath("//table[@class='table table-hover table-dark']/tbody/tr[" + i + "]/td[2]")).getText());

                map.put(natId, rel);
            }


        }
        System.out.println(map.entrySet());
        System.out.println(map.values());

        //convert dob as per csv
        SimpleDateFormat formatter1=new SimpleDateFormat("ddMMyyyy");
        Date _dob=formatter1.parse(dob);
        Calendar c = Calendar.getInstance();
        c.setTime(_dob);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int currentdate = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, currentdate);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);
        int age=diff1.getYears();
        System.out.println("age:" + age + "years");

        double sal=reliefCal(salary, taxPaid, gender, age);

        System.out.println("Relief:"+sal);


    }

    public double reliefCal(double salary, double taxPaid, String gender, int age){
        //relief calculation
        double variable;
        if(age<=18){
            variable=1;
        }
        else if(age<=35){
            variable=0.8;
        }
        else if(age<=50){
            variable=0.5;
        }
        else if(age<=75){
            variable=0.367;
        }
        else
        {
            variable=0.05;
        }

        int genderBonus;
        if(gender.equals("M"))
        {
            genderBonus=0;
        }
        else{
            genderBonus=500;
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        double relief=((salary-taxPaid)*variable)+genderBonus;
        double result = new Double(df.format(relief));
        double relief_rounding1= DoubleRounder.round(result, 0);
        int relief_final=(int) relief_rounding1;

        if (relief_final>0 && relief_final<50){
            relief_final=50;
        }

        return relief_final;
    }
}
