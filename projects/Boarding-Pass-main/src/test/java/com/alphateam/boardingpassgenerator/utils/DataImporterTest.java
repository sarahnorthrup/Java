package com.alphateam.boardingpassgenerator.utils;

import com.alphateam.boardingpassgenerator.enums.InputField;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class DataImporterTest {
    //creating path to the file
    private File example = new File("target/classes/example-output-format.txt");
    //Method to check if file exists
    public boolean checkForFiles(File example){
        if(example.exists()){
            return true;
        }else return false;
    }

    @Test
    public void test() {
        String example = "First Name: Dylan";
        String output;
        int index = example.indexOf(':');
        index = index + 2;
        output = example.substring(index);
        System.out.println(output);
    }
    @Test
    public HashMap<InputField, String> importFileTest() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(String.valueOf(example)));
        HashMap<InputField,String> data = new LinkedHashMap<>();
        for(int i = 0; i < lines.size();i++) {
            String info;
            int idx = lines.get(i).indexOf(':');
            idx = idx + 2;
            info = lines.get(i).substring(idx);
            data.put(InputField.values()[i],info);
        }
        //System.out.println(data);
        return data;
    }

    @Test
    public void importAllTest() throws IOException {
       Set<Map.Entry<InputField,String>> entrySet = importFileTest().entrySet();
        ArrayList<Map.Entry<InputField,String>> myList = new ArrayList<>(entrySet);
        System.out.println(myList);
    }

}