package com.utility;

public class Person {

    public String birthday;
    public String gender;
    public String name;
    public String natid;
    public String salary;
    public String tax;

    public String getBirthday(){
        return birthday;
    }

    public void setBirthDay(String birthday){
        this.birthday=birthday;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNatid(){
        return natid;
    }

    public void setNatid(String ID){
        this.natid = ID;
    }

    public String getSalary(){
        return salary;
    }

    public void setSalary(String salary){
        this.salary = salary;
    }

    public String getTax(){
        return tax;
    }

    public void setTax(String tax){
        this.tax = tax;
    }

}
