package com.alphateam.boardingpassgenerator.gui;

import javax.swing.*;
import java.util.stream.IntStream;
import static com.alphateam.boardingpassgenerator.gui.utils.AirportDataImporter.*;

public class GuiInitVariables {

    public static final JLabel title = new JLabel("Boarding Pass");
    public static final JLabel firstNameLabel = new JLabel("First Name");
    public static final JTextField firstNameField = new JTextField();
    public static final JLabel lastNameLabel = new JLabel("Last Name");
    public static final JTextField lastNameField = new JTextField();
    public static final JLabel phoneLabel = new JLabel("Phone Number");
    public static final JTextField phoneField = new JTextField();
    public static final JLabel emailLabel = new JLabel("Email");
    public static final JTextField emailField = new JTextField();
    public static final JLabel genderLabel = new JLabel("Gender");
    public static final ButtonGroup genderButtonGroup = new ButtonGroup();
    public static final JRadioButton maleRadioButton = new JRadioButton("Male");
    public static final JRadioButton femaleRadioButton = new JRadioButton("Female");
    public static final JLabel dobLabel = new JLabel("DOB");
    public static final JLabel originLabel = new JLabel("Origin");
    public static final JLabel destinationLabel = new JLabel("Destination");
    public static final JLabel departureTimeLabel = new JLabel("Departure Time");
    public static final JLabel flightDateLabel = new JLabel("Flight Date");
    public static final JButton submitButton = new JButton("Purchase");
    public static final JButton printButton = new JButton("Print");
    public static final JLabel ticketPrice = new JLabel("Ticket Price: $0.00");

    protected static final String[] locations = airports
            .keySet()
            .stream()
            .collect( StringBuilder::new,
                    (a, b) -> a.append(b).append(","),
                    (a, b) -> {})
            .toString()
            .split(",");


    protected static final String[] dates
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };

    protected static final String[] months
            = { "Jan", "Feb", "Mar", "Apr",
            "May", "June", "July", "Aug",
            "Sep", "Oct", "Nov", "Dec" };

    protected static final String[] years = IntStream
            .rangeClosed(1900, 2022)
            .collect(
                    () -> new String[2023 - 1900],
                    (a, b) -> a[122 - (-1 * (1900 - b))] = String.valueOf(b),
                    (a, b) -> {});

    protected static final String[] hours
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12" };

    protected static final String[] mins = IntStream
            .rangeClosed(0, 11)
            .collect(
                    () -> new String[12],
                    (a, b) -> a[b] = String.valueOf(b * 5),
                    (a, b) -> {});

    static {
        mins[0] = "0" + mins[0];
        mins[1] = "0" + mins[1];
    }

    protected static final String[] amOrPm
            = { "AM","PM"};

    public static final JComboBox<String> dobDateField = new JComboBox<>(dates);
    public static final JComboBox<String> dobMonthField = new JComboBox<>(months);
    public static final JComboBox<String> dobYearField = new JComboBox<>(years);
    public static final JComboBox<String> departureHourField = new JComboBox<>(hours);
    public static final JComboBox<String> departureMinuteField = new JComboBox<>(mins);
    public static final JComboBox<String> departureAmOrPmField = new JComboBox<>(amOrPm);
    public static final JComboBox<String> flightDateDateField = new JComboBox<>(dates);
    public static final JComboBox<String> flightDateMonthField = new JComboBox<>(months);
    public static final JComboBox<String> flightDateYearField = new JComboBox<>(years);
    public static final JComboBox<String> originField = new JComboBox<>(locations);
    public static final JComboBox<String> destinationField = new JComboBox<>(locations);
}
