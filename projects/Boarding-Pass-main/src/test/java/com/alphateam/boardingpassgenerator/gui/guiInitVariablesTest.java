package com.alphateam.boardingpassgenerator.gui;

import com.alphateam.boardingpassgenerator.enums.Airport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class guiInitVariablesTest {

    @Test
    public void testJsonImport() {
        byte[] arr;
        JSONObject ex;
        JSONArray json;
        List<Map<Airport, String>> importedData = new ArrayList<>();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/airports.json");
            arr = inputStream.readAllBytes();
            ex = new JSONObject(new String(arr));
            JSONObject inside = ex.getJSONObject("Data");
            Map<String, Object> map = inside.toMap();
            for (var item : map.entrySet()) {
                String trimmed = item.getValue().toString().substring(1, item.getValue().toString().length() - 1);
                String[] strArray = trimmed.split(", ");
                String country = strArray[0].substring(strArray[0].indexOf('=') + 1);
                String city = strArray[1].substring(strArray[1].indexOf('=') + 1);
                String tz = strArray[2].substring(strArray[2].indexOf('=') + 4);
                String abbr = strArray[3].substring(strArray[3].indexOf('=') + 1);
                Map<Airport, String> mapo = new HashMap<>();
                mapo.put(Airport.LOCATION, String.format("%s/%s", country, city));
                mapo.put(Airport.TIMEZONE, tz);
                mapo.put(Airport.ABBREVIATION, abbr);
                importedData.add(new HashMap<>(mapo));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}