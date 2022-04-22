package com.alphateam.boardingpassgenerator.gui.utils;

import com.alphateam.boardingpassgenerator.enums.InputField;
import com.alphateam.boardingpassgenerator.enums.Month;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.alphateam.boardingpassgenerator.gui.GuiInitVariables.*;

public class DataValidator {


    public static ArrayList<Map<?, ?>> validationResults(Container c) {

        Map<Boolean, String> validationResults = new HashMap<>();
        Map<InputField, Object> processed = new HashMap<>();
        ArrayList<Map<?, ?>> list = new ArrayList<>();
        list.add(validationResults);
        list.add(processed);

        for (var item : c.getComponents()) {
            if (item instanceof JTextField) {
                if (((JTextField) item).getText().isEmpty()) {
                    validationResults.put(false, "Fields cannot be left blank");
                    return list;
                }
            }
        }

        if (genderButtonGroup.getSelection() == null) {
            validationResults.put(false, "Must choose gender");
            return list;
        }

        LocalDate dateOfBirth = LocalDate.of(
                Integer.parseInt(dobYearField.getSelectedItem().toString()),
                Month.valueOf(dobMonthField.getSelectedItem()
                        .toString()
                        .toUpperCase())
                        .ordinal()
                        + 1,
                Integer.parseInt(dobDateField.getSelectedItem().toString()));

        LocalDate flightDate = LocalDate.of(
                Integer.parseInt(flightDateYearField.getSelectedItem().toString()),
                Month.valueOf(flightDateMonthField.getSelectedItem()
                        .toString()
                        .toUpperCase())
                        .ordinal()
                        + 1,
                Integer.parseInt(flightDateDateField.getSelectedItem().toString()));

        int amPM = departureAmOrPmField.getSelectedItem().toString().equals("AM") ? 0 : 12;

        LocalTime flightTime = LocalTime.of(
                Integer.parseInt(departureHourField.getSelectedItem().toString()) + amPM,
                Integer.parseInt(departureMinuteField.getSelectedItem().toString()));

        LocalDateTime flightDateTime = LocalDateTime.of(flightDate, flightTime);
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (flightDateTime.isBefore(currentDateTime)) {
            validationResults.put(false, "Cannot schedule a flight in the past");
            return list;
        }

        if (originField.getSelectedItem().toString()
                .equals(destinationField.getSelectedItem().toString())) {
            validationResults.put(false, "Destination and origin must be different");
            return list;
        }

        Enumeration<AbstractButton> enumerator = genderButtonGroup.getElements();
        while (enumerator.hasMoreElements()) {
            AbstractButton radioButton = enumerator.nextElement();
            if (radioButton.isSelected()) {
                processed.put(
                        InputField.GENDER,
                        String.valueOf(radioButton.getText().charAt(0)));
            }
        }

        processed.put(InputField.AGE, dateOfBirth);
        processed.put(InputField.DATE, flightDateTime);
        validationResults.put(true, null);
        return list;
    }
}
