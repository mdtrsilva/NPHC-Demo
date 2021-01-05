package com.apiTests;

import com.utility.Person;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matcher.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class API_InsertPerson {

    public Properties configProp;
    String uri;
    @BeforeTest
    public void setup() throws IOException {

        configProp=new Properties();
        FileInputStream configPropfile=new FileInputStream("config.properties");
        configProp.load(configPropfile);

        uri=configProp.getProperty("api_person_insert");
    }


    @Test
    public void insertPerson(){
        Person person=new Person();
        person.setBirthDay("09121987");
        person.setGender("M");
        person.setName("David Blame");
        person.setNatid("NAT1000");
        person.setSalary("100000.00");
        person.setTax("1000");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                            .post(uri)
                        .then()
                            .assertThat().statusCode(200 | 201)
                            .log().all()
                .extract().response();

        String jsonAsString=response.asString();
        Assert.assertEquals(jsonAsString.contains("Created")|jsonAsString.contains("OK"),true);

    }

    @Test
    public void insertPerson_with_blank_DOB(){
        Person person=new Person();
        person.setBirthDay("");
        person.setGender("M");
        person.setName("David Blame");
        person.setNatid("NAT1000");
        person.setSalary("100000.00");
        person.setTax("1000");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();


    }


    @Test
    public void insertPerson_with_blank_gender_value(){
        Person person=new Person();
        person.setBirthDay("09121987");
        person.setGender("");
        person.setName("David Blame");
        person.setNatid("NAT1000");
        person.setSalary("100000.00");
        person.setTax("1000");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();



    }

    @Test
    public void insertPerson_with_blank_Name(){
        Person person=new Person();
        person.setBirthDay("09121987");
        person.setGender("M");
        person.setName("");
        person.setNatid("NAT1000");
        person.setSalary("100000.00");
        person.setTax("1000");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .assertThat().statusCode(200 | 201)
                        .log().all()
                        .extract().response();

        String jsonAsString=response.asString();
        Assert.assertEquals(jsonAsString.contains("Created")|jsonAsString.contains("OK"),true);


    }

    @Test
    public void insertPerson_with_blank_NatId(){
        Person person=new Person();
        person.setBirthDay("09121987");
        person.setGender("M");
        person.setName("David");
        person.setNatid("");
        person.setSalary("100000.00");
        person.setTax("1000");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();



    }

    @Test
    public void insertPerson_with_blank_Salary(){
        Person person=new Person();
        person.setBirthDay("09121987");
        person.setGender("M");
        person.setName("David");
        person.setNatid("NAT10000000");
        person.setSalary("");
        person.setTax("1000");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();


    }

    @Test
    public void insertPerson_with_blank_tax(){
        Person person=new Person();
        person.setBirthDay("09121987");
        person.setGender("M");
        person.setName("David");
        person.setNatid("NAT0000001");
        person.setSalary("100000.00");
        person.setTax("");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();



    }

    @Test
    public void insertPerson_with_invalid_DOB(){
        Person person=new Person();
        person.setBirthDay("09-12-1987");
        person.setGender("M");
        person.setName("David");
        person.setNatid("NAT0000001");
        person.setSalary("100000.00");
        person.setTax("10000.00");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();

    }

    @Test
    public void insertPerson_with_invalid_salary_and_tax(){
        Person person=new Person();
        person.setBirthDay("09-12-1987");
        person.setGender("M");
        person.setName("David");
        person.setNatid("NAT0000001");
        person.setSalary("$100000.00");
        person.setTax("$10000.00");


        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(person)
                        .when()
                        .post(uri)
                        .then()
                        .statusCode(500)
                        .assertThat().body("error", equalTo("Internal Server Error"))
                        .log().all()
                        .extract().response();
    }

}
