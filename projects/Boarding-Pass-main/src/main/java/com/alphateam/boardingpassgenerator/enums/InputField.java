package com.alphateam.boardingpassgenerator.enums;

public enum InputField {
    FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, GENDER, AGE, BOARDING_NUMBER, DATE, ORIGIN, DESTINATION, DEPARTURE_TIME, ETA, PRICE;

    public String getValue() {
        switch (this) {
            case FIRST_NAME:
                return "First Name: ";
            case LAST_NAME:
                return "Last Name: ";
            case EMAIL:
                return "Email: ";
            case PHONE_NUMBER:
                return "Phone Number: ";
            case AGE:
                return "Age: ";
            case GENDER:
                return "Gender: ";
            case DATE:
                return "Date: ";
            case ORIGIN:
                return "Origin: ";
            case DESTINATION:
                return "Destination: ";
            case DEPARTURE_TIME:
                return "Departure Time: ";
            case PRICE:
                return "Price: ";
            case ETA:
                return "ETA: ";
            case BOARDING_NUMBER:
                return "Boarding Number: ";
        }
        return null;
    }
}
