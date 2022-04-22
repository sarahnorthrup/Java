package com.alphateam.boardingpassgenerator.utils;

import com.alphateam.boardingpassgenerator.enums.Airport;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;


import static com.alphateam.boardingpassgenerator.gui.utils.AirportDataImporter.*;

class InputProcessorTest {


    @Test
    public void timeZoneTester() {
        String originLocation = "Chile/Concepcion";
        int originOffset = Integer.parseInt(airports.get(originLocation).get(Airport.TIMEZONE));
        LocalDateTime now = LocalDateTime.now();
        System.out.println(originOffset);
        System.out.println(now);
        String arrivalLocation = "Greece/Athens";
        int arrivalOffset = Integer.parseInt(airports.get(arrivalLocation).get(Airport.TIMEZONE));
        System.out.println(arrivalOffset);
        int difference = (originOffset - arrivalOffset);
        LocalDateTime then = now.plusHours(difference);
        System.out.println(then);
    }

}