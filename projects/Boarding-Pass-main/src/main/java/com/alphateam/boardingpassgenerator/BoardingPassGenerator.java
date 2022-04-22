package com.alphateam.boardingpassgenerator;

// this class will act as the entry point for the program and execute the required functionality

import com.alphateam.boardingpassgenerator.gui.GUI;
import com.alphateam.boardingpassgenerator.utils.*;

public class BoardingPassGenerator {
    private final InputProcessor inputProcessor = new InputProcessor();
    private final DataFormatter dataFormatter = new DataFormatter();
    private final DataImporter dataImporter = new DataImporter();
    private final DataExporter dataExporter = new DataExporter();
    private final TicketGenerator ticketGenerator = new TicketGenerator();

    // entry point for the program
    public static void main(String[] args) {
        BoardingPassGenerator bpg = new BoardingPassGenerator();
        new GUI(bpg);
    }

    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public DataFormatter getDataFormatter() {
        return dataFormatter;
    }

    public DataImporter getDataImporter() {
        return dataImporter;
    }

    public DataExporter getDataExporter() {
        return dataExporter;
    }

    public TicketGenerator getTicketGenerator() {
        return ticketGenerator;
    }
}
