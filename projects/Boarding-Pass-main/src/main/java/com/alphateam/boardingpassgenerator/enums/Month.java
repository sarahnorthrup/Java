package com.alphateam.boardingpassgenerator.enums;

public enum Month {
    JAN("Jan"), FEB("Feb"), MAR("Mar"), APR("Apr"), MAY("May"), JUNE("June"),
    JULY("July"), AUG("Aug"), SEP("Sept"), OCT("Oct"), NOV("Nov"), DEC("Dec");

    private final String description;

    Month(String s) {
        this.description = s;
    }

    public String getDescription() {
        return description;
    }
}
