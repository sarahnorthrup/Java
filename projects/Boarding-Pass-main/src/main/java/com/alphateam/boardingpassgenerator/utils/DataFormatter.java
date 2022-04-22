package com.alphateam.boardingpassgenerator.utils;

// this class will receive a HashMap and convert all the data to a single String
// to be sent to the DataExporter class

import com.alphateam.boardingpassgenerator.enums.InputField;

import java.util.Map;

public class DataFormatter {
    private String buildString(Map<InputField, String> data){
        StringBuilder stringBuilder = new StringBuilder();
        for (var entry : data.entrySet()) {
            stringBuilder.append(entry.getKey().getValue()).append(entry.getValue()).append("\r\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    public String executeFormatter(Map<InputField, String> data){
        return buildString(data);
    }
}
