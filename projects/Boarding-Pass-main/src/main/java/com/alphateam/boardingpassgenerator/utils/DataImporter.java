package com.alphateam.boardingpassgenerator.utils;

// this class will handle importing the correctly formatted and exported data to be
// sent to the TicketGenerator class where it will be finalized

import com.alphateam.boardingpassgenerator.enums.InputField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataImporter {

    private final Path inputPath = Path.of(
            System.getProperty("user.dir") + "//src//main//resources//purchased");

    public Map<InputField,String> importFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(inputPath + "//" + fileName));
        Map<InputField, String> data = new LinkedHashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String info;
            int idx = lines.get(i).indexOf(':');
            idx = idx + 2;
            info = lines.get(i).substring(idx);
            data.put(InputField.values()[i], info);
        }
        return data;
    }
}
