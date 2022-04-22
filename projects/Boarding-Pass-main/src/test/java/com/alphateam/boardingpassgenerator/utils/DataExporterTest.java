package com.alphateam.boardingpassgenerator.utils;

import com.alphateam.boardingpassgenerator.enums.Month;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.stream.IntStream;


class DataExporterTest {
    @Test
     void writeTest() throws URISyntaxException, IOException {
        String test = "First Name: Dylan\r\nLastName: Yurjevich\r\nAge: 27\r\nGender: M";
        String fileName = "ExampleOutput.txt";
        Path path = Paths.get(System.getProperty("user.dir") + "//src//main//resources//purchased//ExampleOutput.txt");
        Files.write(path,test.getBytes(),StandardOpenOption.CREATE);
    }

    @Test
    public void testorrr() {
        String[] years = IntStream
                .rangeClosed(1900, 2022)
                .collect(
                        () -> new String[2023 - 1900],
                        (a, b) -> a[122 - (-1 * (1900 - b))] = String.valueOf(b),
                        (a, b) -> {});
        for (var item : years) {
            System.out.println(item);
        }
    }


    @Test
    public void testEnum() {
        Month[] months = Month.values();
        for (var item : months) {
            System.out.println(item.getDescription());
        }
    }

    @Test
    public void testJSON() {
        JSONObject ex;

    }
}