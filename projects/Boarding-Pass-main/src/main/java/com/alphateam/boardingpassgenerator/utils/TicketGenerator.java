package com.alphateam.boardingpassgenerator.utils;

// this class will handle generating the final boarding pass into a PDF document

import com.alphateam.boardingpassgenerator.enums.Airport;
import com.alphateam.boardingpassgenerator.enums.InputField;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;

import static com.alphateam.boardingpassgenerator.gui.utils.AirportDataImporter.*;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class TicketGenerator {

    private final Path generatedTicketOutputPath;

    {
        generatedTicketOutputPath = Path.of(System.getProperty("user.dir")
                + "//src//main//resources//generated_tickets");
    }


    /**
     * generates a barcode in .png format and writes it to an output stream where it is then
     * converted into a PDImageXObject to be placed as an image on a PDF document.
     *
     * @param flightNumber
     *        the customer's unique flight identification number
     * @param pdfDocument
     *        the pdf object created by PDFBox
     *
     * @return
     *        the barcode image cast to a data type that can be used by the PDF renderer
     */

    public PDImageXObject generateBarcode(String flightNumber, PDDocument pdfDocument) {
        try {
            Barcode barcode = BarcodeFactory.createCode128(flightNumber);
            barcode.setDrawingText(false);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BarcodeImageHandler.writePNG(barcode, outputStream);
            return PDImageXObject.createFromByteArray(
                    pdfDocument,
                    outputStream.toByteArray(),
                    "PDF417 Barcode");
        } catch (OutputException | BarcodeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  loads the airline's logo image from the resources' folder into a DataInputStream
     *  where it is then converted to a byte array to be converted into a PDImageXObject
     *  and placed as an image on a PDF document
     *
     * @param pdfDocument
     *          the pdf object created by PDFBox
     * @return
     *        the airline logo cast to a data type that can be rendered onto a pdf document
     */

    public PDImageXObject generateAirlineLogo(PDDocument pdfDocument) {
        InputStream logoInputStream = this.getClass().getResourceAsStream("/images/logo.png");
        DataInputStream dataInputStream = new DataInputStream(logoInputStream);
        try {
            byte[] logoByteArray = dataInputStream.readAllBytes();
            return PDImageXObject.createFromByteArray(
                    pdfDocument,
                    logoByteArray,
                    "Alpha Airlines logo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the customers personal and flight information and renders the contents
     * onto a 500x200 single-paged PDF document with an Alpha Airlines logo and scan-able
     * barcode containing the flight's unique identifier
     *
     * @throws IOException
     *          if there is an error writing to the page
     *
     */

    public void generatePDF(Map<InputField, String> data, String fileName) throws IOException {

        PDDocument pdfDocument = new PDDocument();

        int pageWidth = 500;
        int pageHeight = 200;

        PDRectangle pageSize = new PDRectangle(
                pageWidth,
                pageHeight);

        PDPage page = new PDPage(pageSize);

        setOpenActions(
                pdfDocument,
                page);

        PDPageContentStream contentStream = new PDPageContentStream(
                    pdfDocument,
                    page);

        renderData(
                contentStream,
                pdfDocument,
                data);

        contentStream.close();
        pdfDocument.addPage(page);
        pdfDocument.save(generatedTicketOutputPath + "//" + fileName + ".pdf");
        pdfDocument.close();
        openFile(fileName);
        moveOldData(fileName);
    }

    public void renderData(PDPageContentStream contentStream,
                           PDDocument pdfDocument,
                           Map<InputField, String> data) throws IOException {

        int pageWidth = 500;
        int pageHeight = 200;

        int logoImageWidth = 91;
        int logoImageHeight = 44;
        int barcodeImageWidth = 350;
        int barcodeImageHeight = 30;
        int xyOffset = 10;

        int blockSpacing = 10;
        int blockHeight = 28;
        int lineYCord = 15;
        int fieldYCord = 5;
        int dataYCord = 20;

        int lineX1 = 10;
        int lineX2 = 130;
        int lineY1 = blockSpacing + lineYCord;
        int lineY2 = blockSpacing * 2 + lineYCord + blockHeight;
        int lineY3 = blockSpacing * 3 + lineYCord + blockHeight * 2;
        int lineY4 = blockSpacing * 4 + lineYCord + blockHeight * 3;
        int lineY5 = blockSpacing * 5 + lineYCord + blockHeight * 4;

        int fieldX1 = 10;
        int fieldY1 = blockSpacing + fieldYCord;
        int fieldY2 = blockSpacing * 2 + fieldYCord + blockHeight;
        int fieldY3 = blockSpacing * 3 + fieldYCord + blockHeight * 2;
        int fieldY4 = blockSpacing * 4 + fieldYCord + blockHeight * 3;
        int fieldY5 = blockSpacing * 5 + fieldYCord + blockHeight * 4;

        int dataY1 = blockSpacing + dataYCord;
        int dataY2 = blockSpacing * 2 + dataYCord + blockHeight;
        int dataY3 = blockSpacing * 3 + dataYCord + blockHeight * 2;
        int dataY4 = blockSpacing * 4 + dataYCord + blockHeight * 3;
        int dataY5 = blockSpacing * 5 + dataYCord + blockHeight * 4;

        PDFont courierReg = PDType1Font.COURIER;
        PDFont courierBold = PDType1Font.COURIER_BOLD;
        String text;
        float stringWidth;
        float x;
        int firstColBound = 120;

        // render barcode and logo
        PDImageXObject barcodeImage = generateBarcode(
                data.get(
                        InputField.BOARDING_NUMBER),
                pdfDocument);
        PDImageXObject logoImage = generateAirlineLogo(pdfDocument);

        contentStream.drawImage(
                barcodeImage,
                pageWidth - barcodeImageWidth - xyOffset,
                xyOffset,
                barcodeImageWidth,
                barcodeImageHeight);

        contentStream.drawImage(
                logoImage,
                pageWidth - logoImageWidth - xyOffset,
                pageHeight - logoImageHeight - xyOffset,
                logoImageWidth,
                logoImageHeight);
        // end image render

        contentStream.setStrokingColor(Color.BLACK);
        contentStream.setLineWidth(.6f);

        // block one - demographic
        contentStream.moveTo(lineX1, lineY1);
        contentStream.lineTo(lineX2, lineY1);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(courierReg, 8);
        contentStream.newLineAtOffset(fieldX1, fieldY1);
        contentStream.showText("demographic");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(courierReg, 10);
        text = data.get(InputField.GENDER)
                + "-"
                + data.get(InputField.AGE);

        stringWidth = courierReg.getStringWidth(text) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY1);
        contentStream.showText(text);
        contentStream.endText();

        // block two - price
        contentStream.moveTo(lineX1, lineY2);
        contentStream.lineTo(lineX2, lineY2);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(courierReg, 8);
        text = "price";
        contentStream.newLineAtOffset(fieldX1, fieldY2);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(courierReg, 10);
        text = data.get(InputField.PRICE);
        stringWidth = courierReg.getStringWidth(text) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY2);
        contentStream.showText(text);
        contentStream.endText();

        // block three - email
        contentStream.moveTo(lineX1, lineY3);
        contentStream.lineTo(lineX2, lineY3);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(courierReg, 8);
        text = "email";
        contentStream.newLineAtOffset(fieldX1, fieldY3);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(courierReg, 10);
        text = data.get(InputField.EMAIL);
        stringWidth = courierReg.getStringWidth(text) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY3);
        contentStream.showText(text);
        contentStream.endText();

        // block four - phone number
        contentStream.moveTo(lineX1, lineY4);
        contentStream.lineTo(lineX2, lineY4);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(courierReg, 8);
        text = "phone number";
        contentStream.newLineAtOffset(fieldX1, fieldY4);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(courierReg, 10);
        text = data.get(InputField.PHONE_NUMBER);
        stringWidth = courierReg.getStringWidth(text) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY4);
        contentStream.showText(text);
        contentStream.endText();

        // block five - name
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
        text = data.get(InputField.LAST_NAME)
                + ", "
                + data.get(InputField.FIRST_NAME);

        stringWidth = courierReg.getStringWidth(text) / 1000 * 10;
        x = 10 + (firstColBound - stringWidth) / 2;
        contentStream.newLineAtOffset(x, dataY5);
        contentStream.showText(text);
        contentStream.endText();


        // flight information
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

        // departure block
        int departureLineY = rectY + (rectHeight / 2) + bottomMargin;
        contentStream.moveTo(flightInfoLineX1, departureLineY);
        contentStream.lineTo(flightInfoLineX2, departureLineY);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(courierReg, 6);
        text = "departure";
        contentStream.newLineAtOffset(flightInfoLineX1, rectY + (rectHeight / 2f) + 10);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(courierReg, 8);
        text = airports.get(data.get(InputField.ORIGIN)).get(Airport.ABBREVIATION)
                + " - "
                + data.get(InputField.ORIGIN)
                + " @ "
                + data.get(InputField.DEPARTURE_TIME);

        stringWidth = courierReg.getStringWidth(text) / 1000 * 8;
        x = 210 + (210 - stringWidth) / 2;
        contentStream.newLineAtOffset( x,rectY + (rectHeight / 2f) + bottomMargin + 5);
        contentStream.showText(text);
        contentStream.endText();

        // arrival block
        int arrivalLineY = rectY + bottomMargin;
        contentStream.moveTo(flightInfoLineX1, arrivalLineY);
        contentStream.lineTo(flightInfoLineX2, arrivalLineY);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.setFont(courierReg, 6);
        text = "arrival";
        contentStream.newLineAtOffset(flightInfoLineX1, rectY + 10);
        contentStream.showText(text);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(courierReg, 8);
        text = airports.get(data.get(InputField.DESTINATION)).get(Airport.ABBREVIATION)
                + " - "
                + data.get(InputField.DESTINATION)
                + " @ "
                + data.get(InputField.ETA);

        stringWidth = courierReg.getStringWidth(text) / 1000 * 8;
        x = 210 + (210 - stringWidth) / 2;
        contentStream.newLineAtOffset( x,rectY + bottomMargin + 5);
        contentStream.showText(text);
        contentStream.endText();

        // flightNumberText
        contentStream.beginText();
        contentStream.setFont(courierReg, 6);
        text = data.get(InputField.BOARDING_NUMBER);
        stringWidth = courierReg.getStringWidth(text) / 1000 * 6;
        x = 140 + (350 - stringWidth) / 2;
        contentStream.newLineAtOffset(x, 3.5f);
        contentStream.showText(text);

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
        String date = data.get(InputField.DATE);
        contentStream.setFont(courierBold, 10);
        stringWidth = courierBold.getStringWidth(date) / 1000 * 10;
        x = (500 - stringWidth) / 2;
        contentStream.newLineAtOffset(x, 200 - 30);
        contentStream.showText(date);
        contentStream.endText();
    }

    /**
     * sets the open action of the pdf document, that is the event that takes place
     * when the PDF is opened by the user. In this case, the PDF will be zoomed to 100%
     * which will allow it to be viewed at its normal size
     *
     * @param pdfDocument
     *          the pdf document object created by PDFBox
     *
     * @param page
     *          the page of the pdf document for the action to be applied to
     */

    public void setOpenActions(PDDocument pdfDocument, PDPage page) {
        PDDocumentCatalog catalog = pdfDocument.getDocumentCatalog();
        PDPageXYZDestination dest = new PDPageXYZDestination();
        dest.setPage(page);
        dest.setZoom(1f);
        PDActionGoTo action = new PDActionGoTo();
        action.setDestination(dest);
        catalog.setActions(null);
        catalog.setOpenAction(action);
    }

    public void moveOldData(String fileName) throws IOException {
        Path oldPath = Path.of(
                System.getProperty("user.dir")
                        + "//src//main//resources//purchased//"
                        + fileName);
        Path newPath = Path.of(
                System.getProperty("user.dir"),
                "//src//main//resources//old_data//",
                fileName);
        Files.move(oldPath, newPath);
    }

    public void openFile(String fileName) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(generatedTicketOutputPath + "//" + fileName + ".pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
