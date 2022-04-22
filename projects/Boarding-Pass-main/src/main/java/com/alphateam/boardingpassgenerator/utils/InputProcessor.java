package com.alphateam.boardingpassgenerator.utils;

// this class will handle the receiving, formatting, and storing of user input data
import com.alphateam.boardingpassgenerator.enums.Airport;
import com.alphateam.boardingpassgenerator.enums.InputField;

import static com.alphateam.boardingpassgenerator.gui.GuiInitVariables.*;
import static com.alphateam.boardingpassgenerator.gui.utils.AirportDataImporter.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InputProcessor {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String age;
    private String boardingPassNumber;
    private String date;
    private String origin;
    private String destination;
    private String departureTime;
    private String estimatedTimeArrival;
    private String ticketPrice;
    private Map<InputField, String> detailHashMap;

    public void extractInputs(int age, LocalDateTime date, String g) {
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        email = emailField.getText();
        phoneNumber = phoneField.getText();
        gender = g;
        this.age = String.valueOf(age);
        boardingPassNumber = String.valueOf(UUID.randomUUID());
        this.date = date.toLocalDate().toString();
        origin = originField.getSelectedItem().toString();
        destination = destinationField.getSelectedItem().toString();
        calculateDepartureArrival(date);
        ticketPrice = String.format("$%.2f",priceCalculator());
        generateHashMap();
    }
    private void nullify() {
        firstName = null;
        lastName = null;
        email = null;
        phoneNumber = null;
        gender = null;
        age = null;
        boardingPassNumber = null;
        date = null;
        origin = null;
        destination = null;
        departureTime = null;
        detailHashMap = null;
        ticketPrice = null;
    }

    private void generateHashMap() {
        detailHashMap = new LinkedHashMap<>();
        detailHashMap.put(InputField.FIRST_NAME, firstName);
        detailHashMap.put(InputField.LAST_NAME, lastName);
        detailHashMap.put(InputField.EMAIL, email);
        detailHashMap.put(InputField.PHONE_NUMBER, phoneNumber);
        detailHashMap.put(InputField.GENDER, gender);
        detailHashMap.put(InputField.AGE, age);
        detailHashMap.put(InputField.BOARDING_NUMBER, boardingPassNumber);
        detailHashMap.put(InputField.DATE, date);
        detailHashMap.put(InputField.ORIGIN, origin);
        detailHashMap.put(InputField.DESTINATION, destination);
        detailHashMap.put(InputField.DEPARTURE_TIME, departureTime);
        detailHashMap.put(InputField.ETA, estimatedTimeArrival);
        detailHashMap.put(InputField.PRICE, ticketPrice);
    }
    private float priceCalculator() {
        int age = Integer.parseInt(this.age);
        float price = Float.parseFloat(ticketPrice);
        price = age <= 12f
                ? price * .5f
                : age >= 60f
                ? price * .6f
                : price;

        price = gender.equals("F")
                ? price * .25f
                : price;

        return price;
    }

    private void calculateDepartureArrival(LocalDateTime date) {
        String departureTime;
        String arrivalTime;
        String originSuffix;
        String arrivalSuffix;

        if (date.getHour() >= 12) {
            departureTime = date.toLocalTime()
                    .minusHours(12)
                    .format(DateTimeFormatter.ofPattern("h:mm"));
            originSuffix = "pm";
        } else {
            departureTime = date.toLocalTime().format(DateTimeFormatter.ofPattern("h:mm"));
            originSuffix = "am";
        }
        int originOffset = Integer.parseInt(airports.get(origin).get(Airport.TIMEZONE));
        int arrivalOffset = Integer.parseInt(airports.get(destination).get(Airport.TIMEZONE));
        int difference = Math.abs((originOffset - arrivalOffset));
        LocalDateTime then = date.plusHours(difference);

        if (then.getHour() >= 12) {
            arrivalTime = then.toLocalTime()
                    .minusHours(12)
                    .format(DateTimeFormatter.ofPattern("h:mm"));
            arrivalSuffix = "pm";
        } else {
            arrivalTime = then.toLocalTime().format(DateTimeFormatter.ofPattern("h:mm"));
            arrivalSuffix = "am";
        }

        this.departureTime = departureTime + originSuffix;
        this.estimatedTimeArrival = arrivalTime + arrivalSuffix;
        ticketPrice = String.valueOf(difference * 40);
    }

    public Map<InputField, String> getDetailsHashMap() {
        Map<InputField, String> newValues = new LinkedHashMap<>(detailHashMap);
        nullify();
        return newValues;
    }
}

