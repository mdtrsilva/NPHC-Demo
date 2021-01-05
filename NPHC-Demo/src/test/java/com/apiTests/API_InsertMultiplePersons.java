package com.apiTests;

import com.utility.Person;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import static org.hamcrest.Matcher.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class API_InsertMultiplePersons {

    public Properties configProp;
    String uri;
    @BeforeTest
    public void setup() throws IOException {

        configProp=new Properties();
        FileInputStream configPropfile=new FileInputStream("config.properties");
        configProp.load(configPropfile);

        uri=configProp.getProperty("api_multiple_insert");
    }

    @Test
    public void insertMultiplePerson(){
        ArrayList<Person> persons=new ArrayList<Person>() ;


        Person person1=new Person();
        person1.setBirthDay("19121987");
        person1.setGender("M");
        person1.setName("David Blame");
        person1.setNatid("NAT1000");
        person1.setSalary("100000.00");
        person1.setTax("1000");

        Person person2=new Person();
        person2.setBirthDay("29121987");
        person2.setGender("F");
        person2.setName("Ann Davidson");
        person2.setNatid("NAT1002");
        person2.setSalary("100000.00");
        person2.setTax("1000");

        persons.add(person1);
        persons.add(person2);

        given()
                .contentType(ContentType.JSON)
                .body(persons)
                .when()
                    .post(uri)
                .then()
                    //.statusCode(202)
                    .assertThat().statusCode(200 | 201)
                    .log().all();




    }
}
