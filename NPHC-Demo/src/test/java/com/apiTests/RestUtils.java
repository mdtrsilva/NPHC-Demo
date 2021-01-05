package com.apiTests;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RestUtils {

    public static int getId(){
        int generateId= ThreadLocalRandom.current().nextInt(1000,10000);
        return generateId;
    }
    public static String getCustomerName(){
        String generateString= RandomStringUtils.randomAlphabetic(3);
        return("Tharindu"+generateString);
    }

    public static int generateQty(){
        int generateQty=ThreadLocalRandom.current().nextInt(1, 100);
        return generateQty;
    }

    public static double geneatePrice(){
        double generatePrice=ThreadLocalRandom.current().nextDouble(1.00, 10000.00);
        return generatePrice;
    }


}
