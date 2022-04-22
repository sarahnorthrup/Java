package com.alphateam.boardingpassgenerator.utils;

// this class will handle the exporting of customer information to a text file

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DataExporter {

    public void export(String toExport, String fileName) {
        Path path = Paths.get(System.getProperty(
                "user.dir") + "//src//main//resources//purchased//" + fileName);

        try {
            Files.write(path,toExport.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
