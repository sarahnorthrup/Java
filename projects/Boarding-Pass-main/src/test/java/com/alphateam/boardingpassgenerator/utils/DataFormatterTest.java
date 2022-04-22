package com.alphateam.boardingpassgenerator.utils;

import com.alphateam.boardingpassgenerator.enums.InputField;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataFormatterTest {
    @Test
    public void dataFormatterTest() {
        //string builder
        StringBuilder data = new StringBuilder();
//        data.append("");
        //hashmap
        Map<InputField, Object> customerDetails = new LinkedHashMap<>();
        //details
        customerDetails.put(InputField.FIRST_NAME, "Dylan");
        customerDetails.put(InputField.LAST_NAME, "Yurjevich");
        customerDetails.put(InputField.PHONE_NUMBER, "(123) 123-1234");
        customerDetails.put(InputField.EMAIL, "123@yahoo.com");
        customerDetails.put(InputField.GENDER, "M");
        customerDetails.put(InputField.AGE, "27");
        customerDetails.put(InputField.BOARDING_NUMBER, "12345");
        customerDetails.put(InputField.DATE, "4/18/2022");
        customerDetails.put(InputField.ORIGIN, "America/New York");
        customerDetails.put(InputField.DESTINATION, "India/New Delhi");
        customerDetails.put(InputField.DEPARTURE_TIME, "12345");
        customerDetails.put(InputField.ETA, "12345");
        customerDetails.put(InputField.PRICE, "$150.50");
        //making string

        for (var entry : customerDetails.entrySet()) {
            data.append(entry.getKey().getValue() + entry.getValue() + "\r\n");
        }
        String result = data.toString();
        //end making string
    }


    @Test
    public void formatterTest() {
        System.out.println(10 + 5 + 10);
        assertTrue(true);
    }

    @Test
    public void formatterTest1() {

    }

}