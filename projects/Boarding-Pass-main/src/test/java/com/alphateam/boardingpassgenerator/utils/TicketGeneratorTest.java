package com.alphateam.boardingpassgenerator.utils;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.junit.jupiter.api.Test;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

class TicketGeneratorTest {

    TicketGenerator gen = new TicketGenerator();

    @Test
    public void openPDFTest() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(System.getProperty("user.dir") + "//src//main//resources//generated_tickets//hello.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void generatePDFTest() throws IOException {
//        gen.generatePDF();
    }

    @Test
    public void testDateTimeAPI() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        LocalDate localDate = LocalDate.of(2022, 10, 1);
        String formatted = formatter.format(localDate);


        System.out.println(localDateTime);
        System.out.println(localTime);
        System.out.println(localDate);
        System.out.println(formatted);
    }

    @Test
    public void testPDFCreation() throws URISyntaxException, IOException, BarcodeException, OutputException {

        UUID flightNumber = UUID.randomUUID();
        Barcode barcode = BarcodeFactory.createCode128(flightNumber.toString());
        barcode.setDrawingText(false);
        System.out.println(barcode.getData());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BarcodeImageHandler.writePNG(barcode, outputStream);

        InputStream logoIS = this.getClass().getResourceAsStream("/images/logo.png");
        DataInputStream dataInputStream = new DataInputStream(logoIS);
        byte[] logoByteArray = dataInputStream.readAllBytes();


        Path pdfTestFolderPath = Path.of(System.getProperty("user.dir") + "//pdfTestFolder");
        String name = "//pdfFile.pdf";

        int width = 500;
        int height = 200;

        PDDocument pdfDocument = new PDDocument();
        PDRectangle size = new PDRectangle(width, height);
        PDPage page = new PDPage(size);

        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

        PDDocumentCatalog catalog = pdfDocument.getDocumentCatalog();
        PDPageXYZDestination dest = new PDPageXYZDestination();
        dest.setPage(page);
        dest.setZoom(1f);
        PDActionGoTo action = new PDActionGoTo();
        action.setDestination(dest);
        catalog.setActions(null);
        catalog.setOpenAction(action);

        PDImageXObject pdfImage = PDImageXObject.createFromByteArray(pdfDocument, outputStream.toByteArray(), "PDF417 Barcode");
        int barcodeImageWidth = 350;
        int barcodeImageHeight = 30;
        int xyOffset = 10;
        contentStream.drawImage(pdfImage, width - barcodeImageWidth - xyOffset, xyOffset, barcodeImageWidth, barcodeImageHeight);

        PDImageXObject logoImage = PDImageXObject.createFromByteArray(pdfDocument, logoByteArray, "Airlines Logo");
        int logoWidth = 91;
        int logoHeight = 44;
        contentStream.drawImage(logoImage, width - logoWidth - xyOffset, height - logoHeight - xyOffset, logoWidth, logoHeight);


        int blockSpacing = 10;
        int blockHeight = 28;
        int lineYCord = 15;
        int fieldYCord = 5;
        int dataYCord = 20;


        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(.6f);

        int lineX1 = 10;
        int lineX2 = 130;
        int lineY1 = blockSpacing * 1 + lineYCord + blockHeight * 0;
        int lineY2 = blockSpacing * 2 + lineYCord + blockHeight * 1;
        int lineY3 = blockSpacing * 3 + lineYCord + blockHeight * 2;
        int lineY4 = blockSpacing * 4 + lineYCord + blockHeight * 3;
        int lineY5 = blockSpacing * 5 + lineYCord + blockHeight * 4;


        int fieldX1 = 10;
        int fieldY1 = blockSpacing * 1 + fieldYCord + blockHeight * 0;
        int fieldY2 = blockSpacing * 2 + fieldYCord + blockHeight * 1;
        int fieldY3 = blockSpacing * 3 + fieldYCord + blockHeight * 2;
        int fieldY4 = blockSpacing * 4 + fieldYCord + blockHeight * 3;
        int fieldY5 = blockSpacing * 5 + fieldYCord + blockHeight * 4;

        int dataX1 = 10;
        int dataY1 = blockSpacing * 1 + dataYCord + blockHeight * 0;
        int dataY2 = blockSpacing * 2 + dataYCord + blockHeight * 1;
        int dataY3 = blockSpacing * 3 + dataYCord + blockHeight * 2;
        int dataY4 = blockSpacing * 4 + dataYCord + blockHeight * 3;
        int dataY5 = blockSpacing * 5 + dataYCord + blockHeight * 4;

        PDFont helveticaReg = PDType1Font.HELVETICA;
        PDFont helveticaBold = PDType1Font.HELVETICA_BOLD;
        PDFont courierReg = PDType1Font.COURIER;
        PDFont courierBold = PDType1Font.COURIER_BOLD;
        String text;
        String text1;
        float stringWidth;
        float x;
        int lineWidth;
        int firstColBound = 120;

        // block one - price
        contentStream.moveTo(lineX1, lineY1);
        contentStream.lineTo(lineX2, lineY1);
        contentStream.stroke();


        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text = "price";
        contentStream.newLineAtOffset(fieldX1, fieldY1);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 10);
        text1 = "$450.00";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY1);
        contentStream.showText(text1);

        contentStream.endText();

        // block two - phone number
        contentStream.moveTo(lineX1, lineY2);
        contentStream.lineTo(lineX2, lineY2);
        contentStream.stroke();

        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text = "phone number";
        contentStream.newLineAtOffset(fieldX1, fieldY2);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 10);
        text1 = "(941) 123-1234";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY2);
        contentStream.showText(text1);

        contentStream.endText();


        // block three - gender
        contentStream.moveTo(lineX1, lineY3);
        contentStream.lineTo(lineX2, lineY3);
        contentStream.stroke();

        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text = "demographic";
        contentStream.newLineAtOffset(fieldX1, fieldY3);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 10);
        text1 = "M-27";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY3);
        contentStream.showText(text1);

        contentStream.endText();

        // block four - age
        contentStream.moveTo(lineX1, lineY4);
        contentStream.lineTo(lineX2, lineY4);
        contentStream.stroke();

        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text = "email";
        contentStream.newLineAtOffset(fieldX1, fieldY4);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 10);
        text1 = "dylan@gmail.com";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY4);
        contentStream.showText(text1);
        contentStream.endText();

        // block four - name
        contentStream.moveTo(lineX1, lineY5);
        contentStream.lineTo(lineX2, lineY5);
        contentStream.stroke();

        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text = "name";
        contentStream.newLineAtOffset(fieldX1, fieldY5);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 10);
        text1 = "Yurjevich, Dylan";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY5);
        contentStream.showText(text1);
        contentStream.endText();




        // rectangle
        contentStream.setNonStrokingColor(Color.BLACK);
        int rectWidth = 250;
        int rectHeight = 70;
        int rectX = (barcodeImageWidth - rectWidth) / 2 + 140;
        int rectY = 60;
        contentStream.addRect(rectX, rectY, rectWidth, rectHeight);
        contentStream.stroke();

        int flightInfoLineX1 = rectX + 20;
        int flightInfoLineX2 = flightInfoLineX1 + 210;
        int bottomMargin = 15;

        int arrivalLineY = rectY + bottomMargin;
        contentStream.moveTo(flightInfoLineX1, arrivalLineY);
        contentStream.lineTo(flightInfoLineX2, arrivalLineY);
        contentStream.stroke();

        int departureLineY = rectY + (rectHeight / 2) + bottomMargin;
        contentStream.moveTo(flightInfoLineX1, departureLineY);
        contentStream.lineTo(flightInfoLineX2, departureLineY);
        contentStream.stroke();









        // departure block
        contentStream.beginText();

        contentStream.setFont(courierReg, 6);
        text = "departure";
        contentStream.newLineAtOffset(flightInfoLineX1, rectY + (rectHeight / 2f) + 10);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text1 = "America/New York @ 10:53p";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 8;
        x = 210 + (210 - stringWidth) / 2;
        contentStream.newLineAtOffset( x,rectY + (rectHeight / 2f) + bottomMargin + 5);
        contentStream.showText(text1);

        contentStream.endText();

        // arrival block

        contentStream.beginText();

        contentStream.setFont(courierReg, 6);
        text = "arrival";
        contentStream.newLineAtOffset(flightInfoLineX1, rectY + 10);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();

        contentStream.setFont(courierReg, 8);
        text1 = "India/New Delhi @ 7:23a";
        stringWidth = courierReg.getStringWidth(text1) / 1000 * 8;
        x = 210 + (210 - stringWidth) / 2;
        contentStream.newLineAtOffset( x,rectY + bottomMargin + 5);
        contentStream.showText(text1);

        contentStream.endText();

        // flightNumberText
        contentStream.beginText();
        contentStream.setFont(courierReg, 6);
        stringWidth = courierReg.getStringWidth(flightNumber.toString()) / 1000 * 6;
        x = 140 + (350 - stringWidth) / 2;
        contentStream.newLineAtOffset(x, 3.5f);
        contentStream.showText(flightNumber.toString());

        contentStream.endText();

        // title
        contentStream.beginText();
        String title = "Boarding Pass";
        contentStream.setFont(courierBold, 10);
        stringWidth = courierBold.getStringWidth(title) / 1000 * 10;
        x = (500 - stringWidth) / 2;
        contentStream.newLineAtOffset(x, 200 - 15);
        contentStream.showText(title);
        contentStream.endText();

        // date
        contentStream.beginText();
        String date = "4/19/22";
        contentStream.setFont(courierBold, 10);
        stringWidth = courierBold.getStringWidth(date) / 1000 * 10;
        x = (500 - stringWidth) / 2;
        contentStream.newLineAtOffset(x, 200 - 30);
        contentStream.showText(date);
        contentStream.endText();


        contentStream.close();
        pdfDocument.addPage(page);
        pdfDocument.save(pdfTestFolderPath.toString() + name);
        pdfDocument.close();
        // load pdf file: PDDocument.load(new File());
        // save pdf file: document.save(file);
        // document.close();
        // nPages = document.getNumberOfPages();
        // remove page: document.removePage(2);
        // alter information:
        // PDDocumentInformation pdd = document.getDocumentInformation();
        // pdd.setAuthor("Dylan");
        // pdd.getAuthor();

    }


    /**
     *     @RepeatedTest(value = 50, name = "#{displayName} {currentRepetition}/{totalRepetitions}")
     *     @DisplayName("wordGeneratorTest")
     *     void computerWordGeneratorTest() throws IOException {
     *         importData();
     *         assertNotNull(resources);
     *     }
     */


    /**
     * 1
     * setAuthor(String author)
     *
     * This method is used to set the value for the property of the PDF document named Author.
     *
     * 2
     * setTitle(String title)
     *
     * This method is used to set the value for the property of the PDF document named Title.
     *
     * 3
     * setCreator(String creator)
     *
     * This method is used to set the value for the property of the PDF document named Creator.
     *
     * 4
     * setSubject(String subject)
     *
     * This method is used to set the value for the property of the PDF document named Subject.
     *
     * 5
     * setCreationDate(Calendar date)
     *
     * This method is used to set the value for the property of the PDF document named CreationDate.
     *
     * 6
     * setModificationDate(Calendar date)
     *
     * This method is used to set the value for the property of the PDF document named ModificationDate.
     *
     * 7
     * setKeywords(String keywords list)
     *
     * This method is used to set the value for the property of the PDF document named Keywords.
     */
}