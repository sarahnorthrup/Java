package com.alphateam.boardingpassgenerator.gui;

import com.alphateam.boardingpassgenerator.BoardingPassGenerator;
import com.alphateam.boardingpassgenerator.enums.InputField;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import static com.alphateam.boardingpassgenerator.gui.GuiInitVariables.*;
import static com.alphateam.boardingpassgenerator.gui.utils.DataValidator.validationResults;

public class GUI extends JFrame implements ActionListener {

    private final Container c;
    private final ActionListener submitActionListener;
    private final ActionListener printActionListener;
    private final BoardingPassGenerator boardingPassGenerator;
    private String fileName = null;

    {
        submitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<Map<?, ?>> results = validationResults(c);
                if (fileName == null) {
                    if (results.get(0).containsKey(true)) {
                        LocalDate ageDate = (LocalDate) results.get(1).get(InputField.AGE);
                        int age = ageDate.until(LocalDate.now()).getYears();
                        String gender = (String) results.get(1).get(InputField.GENDER);
                        boardingPassGenerator.getInputProcessor().extractInputs(age,
                                (LocalDateTime) results.get(1).get(InputField.DATE),gender);
                        Map<InputField, String> map = boardingPassGenerator.getInputProcessor().getDetailsHashMap();
                        fileName = map.get(InputField.LAST_NAME)
                                + map.get(InputField.BOARDING_NUMBER)
                                .substring(map.get(InputField.BOARDING_NUMBER).lastIndexOf("-"));
                        String toExport = boardingPassGenerator.getDataFormatter().executeFormatter(map);
                        boardingPassGenerator.getDataExporter().export(toExport, fileName);
                        JOptionPane.showMessageDialog(c, "Ticket has been purchased!");
                    } else {
                        String error = (String) results.get(0).get(false);
                        JOptionPane.showMessageDialog(
                                c,
                                String.format("Error: %s", error));
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            c,
                            "You must print your ticket before purchasing a new one");
                }
            }
        };

        printActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (fileName != null) {
                    Map<InputField, String> data;
                    try {
                        data = boardingPassGenerator.getDataImporter().importFile(fileName);
                        boardingPassGenerator.getTicketGenerator().generatePDF(data, fileName);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    firstNameField.setText("");
                    lastNameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    dobDateField.setSelectedIndex(0);
                    dobMonthField.setSelectedIndex(0);
                    dobYearField.setSelectedIndex(0);
                    genderButtonGroup.clearSelection();
                    originField.setSelectedIndex(0);
                    destinationField.setSelectedIndex(4);
                    departureHourField.setSelectedIndex(0);
                    departureMinuteField.setSelectedIndex(0);
                    departureAmOrPmField.setSelectedIndex(0);
                    flightDateMonthField.setSelectedIndex(0);
                    flightDateDateField.setSelectedIndex(0);
                    flightDateYearField.setSelectedIndex(0);
                    fileName = null;
                } else {
                    JOptionPane.showMessageDialog(c, "You must purchase a ticket first");
                }
            }
        };
    }

    public GUI(BoardingPassGenerator bpg) {

        this.boardingPassGenerator = bpg;

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Boarding Pass");
        setSize(800, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        c = getContentPane();
        c.setLayout(null);

        int scrollBarWidth = ((int) UIManager.get("ScrollBar.width"));

        int inputItemHeight = 25;
        int row1Y = 80;
        int row2Y = row1Y + (30);
        int row3Y = row1Y + (30 * 2);
        int row4Y = row1Y + (30 * 3);
        int row5Y = row1Y + (30 * 4);
        int row7Y = row1Y + (30 * 7) - 35;

        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(title.getPreferredSize().width + 10, title.getPreferredSize().height);
        title.setLocation((getWidth() - title.getWidth()) / 2 - (scrollBarWidth / 2), 20);
        c.add(title);

        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        firstNameLabel.setSize(150, inputItemHeight);
        firstNameLabel.setLocation(50, row1Y);
        c.add(firstNameLabel);

        firstNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        firstNameField.setSize(190, inputItemHeight);
        firstNameField.setLocation(190, row1Y);
        c.add(firstNameField);

        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lastNameLabel.setSize(150, inputItemHeight);
        lastNameLabel.setLocation(400, row1Y);
        c.add(lastNameLabel);

        lastNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        lastNameField.setSize(190, inputItemHeight);
        lastNameField.setLocation(540, row1Y);
        c.add(lastNameField);


        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        phoneLabel.setSize(150, inputItemHeight);
        phoneLabel.setLocation(50, row2Y);
        c.add(phoneLabel);

        phoneField.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneField.setSize(190, inputItemHeight);
        phoneField.setLocation(190, row2Y);
        c.add(phoneField);


        emailLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        emailLabel.setSize(150, inputItemHeight);
        emailLabel.setLocation(400, row2Y);
        c.add(emailLabel);

        emailField.setFont(new Font("Arial", Font.PLAIN, 15));
        emailField.setSize(190, inputItemHeight);
        emailField.setLocation(540, row2Y);
        c.add(emailField);

        genderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        genderLabel.setSize(100, inputItemHeight);
        genderLabel.setLocation(50, row3Y);
        c.add(genderLabel);

        maleRadioButton.setFont(new Font("Arial", Font.PLAIN, 15));
        maleRadioButton.setSelected(true);
        maleRadioButton.setSize(75, inputItemHeight);
        maleRadioButton.setLocation(210, row3Y);
        c.add(maleRadioButton);

        femaleRadioButton.setFont(new Font("Arial", Font.PLAIN, 15));
        femaleRadioButton.setSelected(false);
        femaleRadioButton.setSize(80, inputItemHeight);
        femaleRadioButton.setLocation(285, row3Y);
        c.add(femaleRadioButton);

        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        genderButtonGroup.clearSelection();

        dobLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dobLabel.setSize(100, inputItemHeight);
        dobLabel.setLocation(400, row3Y);
        c.add(dobLabel);

        dobMonthField.setFont(new Font("Arial", Font.PLAIN, 15));
        dobMonthField.setSize(50, inputItemHeight);
        dobMonthField.setLocation(550, row3Y);
        c.add(dobMonthField);

        dobDateField.setFont(new Font("Arial", Font.PLAIN, 15));
        dobDateField.setSize(50, inputItemHeight);
        dobDateField.setLocation(600, row3Y);
        c.add(dobDateField);

        dobYearField.setFont(new Font("Arial", Font.PLAIN, 15));
        dobYearField.setSize(70, inputItemHeight);
        dobYearField.setLocation(650, row3Y);
        c.add(dobYearField);

        originLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        originLabel.setSize(150, inputItemHeight);
        originLabel.setLocation(50, row4Y);
        c.add(originLabel);

        originField.setFont(new Font("Arial", Font.PLAIN, 15));
        originField.setSize(190, inputItemHeight);
        originField.setLocation(190, row4Y);
        c.add(originField);

        destinationLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        destinationLabel.setSize(150, inputItemHeight);
        destinationLabel.setLocation(400, row4Y);
        c.add(destinationLabel);

        destinationField.setFont(new Font("Arial", Font.PLAIN, 15));
        destinationField.setSize(190, inputItemHeight);
        destinationField.setLocation(540, row4Y);
        destinationField.setSelectedIndex(1);
        c.add(destinationField);

        departureTimeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        departureTimeLabel.setSize(150, inputItemHeight);
        departureTimeLabel.setLocation(50, row5Y);
        c.add(departureTimeLabel);

        departureHourField.setFont(new Font("Arial", Font.PLAIN, 15));
        departureHourField.setSize(50, inputItemHeight);
        departureHourField.setLocation(210, row5Y);
        c.add(departureHourField);

        departureMinuteField.setFont(new Font("Arial", Font.PLAIN, 15));
        departureMinuteField.setSize(50, inputItemHeight);
        departureMinuteField.setLocation(260, row5Y);
        c.add(departureMinuteField);

        departureAmOrPmField.setFont(new Font("Arial", Font.PLAIN, 15));
        departureAmOrPmField.setSize(50, inputItemHeight);
        departureAmOrPmField.setLocation(310, row5Y);
        c.add(departureAmOrPmField);

        flightDateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        flightDateLabel.setSize(150, inputItemHeight);
        flightDateLabel.setLocation(400, row5Y);
        c.add(flightDateLabel);

        flightDateMonthField.setFont(new Font("Arial", Font.PLAIN, 15));
        flightDateMonthField.setSize(50, inputItemHeight);
        flightDateMonthField.setLocation(550, row5Y);
        c.add(flightDateMonthField);

        flightDateDateField.setFont(new Font("Arial", Font.PLAIN, 15));
        flightDateDateField.setSize(50, inputItemHeight);
        flightDateDateField.setLocation(600, row5Y);
        c.add(flightDateDateField);

        flightDateYearField.setFont(new Font("Arial", Font.PLAIN, 15));
        flightDateYearField.setSize(70, inputItemHeight);
        flightDateYearField.setLocation(650, row5Y);
        c.add(flightDateYearField);

        submitButton.setFont(new Font("Arial", Font.PLAIN, 15));
        submitButton.setSize(100, inputItemHeight);
        submitButton.setLocation(280, row7Y);
        submitButton.addActionListener(submitActionListener);
        c.add(submitButton);

        printButton.setFont(new Font("Arial", Font.PLAIN, 15));
        printButton.setSize(100, inputItemHeight);
        printButton.setLocation(400, row7Y);
        printButton.addActionListener(printActionListener);
        c.add(printButton);

        for (var item : c.getComponents()) {
            if (item instanceof JComboBox<?>) {
                JComboBox<?> box = (JComboBox<?>) item;
                BasicComboBoxRenderer renderer = (BasicComboBoxRenderer) box.getRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public Container getC() {
        return c;
    }
}
