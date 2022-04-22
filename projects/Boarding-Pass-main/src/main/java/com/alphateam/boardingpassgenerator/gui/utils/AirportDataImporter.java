package com.alphateam.boardingpassgenerator.gui.utils;

import com.alphateam.boardingpassgenerator.enums.Airport;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AirportDataImporter {
    public static final Map<String, Map<Airport, String>> airports = new HashMap<>();

    static {
        airports.putAll(importData());
    }

    public static Map<String, Map<Airport, String>> importData() {
        byte[] arr;
        JSONObject ex;
        Map<String, Map<Airport, String>> importedData = new HashMap<>();
        try {
            InputStream inputStream = AirportDataImporter.class.getResourceAsStream("/airports.json");
            arr = inputStream.readAllBytes();
            ex = new JSONObject(new String(arr));
            JSONObject inside = ex.getJSONObject("Data");
            Map<String, Object> map = inside.toMap();
            for (var item : map.entrySet()) {
                String trimmed = item.getValue()
                        .toString()
                        .substring(
                                1,
                                item.getValue().toString().length() - 1);
                String[] strArray = trimmed.split(", ");
                String country = strArray[0].substring(strArray[0].indexOf('=') + 1);
                String city = strArray[1].substring(strArray[1].indexOf('=') + 1);
                String tz = strArray[2].substring(strArray[2].indexOf('=') + 4);
                String abbr = strArray[3].substring(strArray[3].indexOf('=') + 1);
                Map<String, Map<Airport, String>> mapo = new HashMap<>();
                Map<Airport, String> innerMap = new HashMap<>();
                innerMap.put(Airport.TIMEZONE, tz);
                innerMap.put(Airport.ABBREVIATION, abbr);
                mapo.put(String.format("%s/%s", country, city), innerMap);
                importedData.putAll(new HashMap<>(mapo));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return importedData;
    }
}
