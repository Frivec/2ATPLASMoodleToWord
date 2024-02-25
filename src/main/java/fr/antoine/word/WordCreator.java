package fr.antoine.word;

import fr.antoine.Main;
import fr.antoine.questions.Question;
import fr.antoine.questions.tests.Test;
import fr.antoine.questions.tests.TestType;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class WordCreator {

    private Path folderDestination;
    private final String documentName;

    public WordCreator(final Path folderDestination, final String documentName) {

        this.folderDestination = folderDestination;
        this.documentName = documentName;

    }

    public void createWordFile(final boolean correction) {

        final Path destination = Paths.get(this.folderDestination + "/" + documentName + " - " + (correction ? "Correction" : "Sujet") + ".docx");

        //Create the file on the destination folder
        if(Files.notExists(destination)) {

            try {

                Files.createFile(destination);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        try (FileOutputStream out = new FileOutputStream(destination.toFile())) {

            final FileInputStream inputStream = new FileInputStream(destination.toFile());

            /*
             * Set the model on the new word document
             */
            final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            final InputStream wordModelStream = classloader.getResourceAsStream(correction ? "correction.dotx" : "subject.dotx");

            assert wordModelStream != null;

            final OPCPackage opcPackage = OPCPackage.open(wordModelStream);

            opcPackage.replaceContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");
            opcPackage.save(destination.toFile());

            /*
             * Starting of the document's writing
             */
            final XWPFDocument document = new XWPFDocument(inputStream);
            final XWPFNumbering numbering = document.createNumbering();
            XWPFParagraph paragraph;

            int listID = 1; //Allows to restart a new list for each question

            for(Map.Entry<TestType, Test> tests : Main.getInstance().getTests().entrySet()) {

                for (Question question : tests.getValue().getQuestions()) {

                    final BigInteger numID = getNewDecimalNumberingId(numbering, BigInteger.valueOf(0), ListType.QCM); //Get the list format

                    paragraph = document.createParagraph();
                    paragraph.setNumID(numID); //Set the list format
                    paragraph.setStyle("QuestionQCM"); //Set the style of the text inside the paragraph

                    XWPFRun run = paragraph.createRun(); //The run is mandatory to write text ou to addan image (ie)
                    run.setText(question.getStatement());

                    /*
                     * Add all images for the statement
                     */
                    for (String compressedImage : question.getStatementImage()) {

                        paragraph = document.createParagraph();
                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                        run = paragraph.createRun();

                        addImage(run, compressedImage);

                    }

                    final BigInteger propositionNumID = getNewDecimalNumberingId(numbering, BigInteger.valueOf(listID), ListType.ITEM); //Get the style for MCQ's items

                    for (Question.Proposition proposition : question.getPropositions()) { //Add all the propositions for the MCQ

                        paragraph = document.createParagraph();

                        paragraph.setNumID(propositionNumID);
                        paragraph.setStyle("ItemQCM");

                        run = paragraph.createRun();

                        if (!proposition.text().isEmpty())

                            run.setText(proposition.text());

                        for (String compressedImage : proposition.propositionImage())

                            this.addImage(run, compressedImage);

                        if (correction) {

                            run = paragraph.createRun();
                            run.setBold(true);
                            run.setColor(proposition.correct() ? "00b050" : "ff0000");
                            run.setText(" " + (proposition.correct() ? "VRAI" : "FAUX") + " " + proposition.feedback());

                        }

                    }

                    /*
                     * Add general feedback
                     */
                    if (correction) {

                        paragraph = document.createParagraph(); //Paragraph for text correction
                        paragraph.setStyle("Normal");

                        run = paragraph.createRun();
                        run.setUnderline(UnderlinePatterns.WORDS);
                        run.setText("Correction");

                        if (!question.getGeneralFeedback().isEmpty()) {

                            paragraph = document.createParagraph(); //Paragraph for text correction
                            paragraph.setStyle("Normal");

                            run = paragraph.createRun();
                            run.setText(question.getGeneralFeedback());

                        }

                        //Paragraph for images
                        paragraph = document.createParagraph();
                        paragraph.setStyle("Normal");
                        paragraph.setAlignment(ParagraphAlignment.CENTER);

                        run = paragraph.createRun();
                        run.setText(question.getGeneralFeedback());

                        for (String compressedImage : question.getGeneralFbImage())

                            this.addImage(run, compressedImage);

                    }

                    listID++;

                }

            }

            document.write(out);
            inputStream.close();
            wordModelStream.close();

            System.out.println("Colle générée avec succès !");

        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Add an image in the word document using a compressed image in base 64
     */
    private void addImage(XWPFRun run, String compressedImage) throws IOException, InvalidFormatException {
        byte[] imageBytes = Base64.decodeBase64(compressedImage);
        final Dimension dimension = getDimension(imageBytes);
        final ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);

        if (dimension != null)

            run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, "image.png", Units.toEMU(dimension.getWidth()), Units.toEMU(dimension.getHeight()));

        imageStream.close();
    }

    /**
     * Return the Dimension of an image from bytes
     */
    private Dimension getDimension(final byte[] imageBytes) throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        final BufferedImage image = ImageIO.read(inputStream);

        if(image == null)

            return null;

        inputStream.close();

        return new Dimension(image.getWidth() / 2, image.getHeight() / 2); //Divided by two for the images to be smaller in the document

    }

    /**
     * Create new list format and restart indexation
     */
    private BigInteger getNewDecimalNumberingId(final XWPFNumbering numbering, BigInteger abstractNumID, final ListType listType) {

        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(abstractNumID);

        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.setIlvl(BigInteger.valueOf(0)); // set indent level 0
        cTLvl.addNewNumFmt().setVal(listType.getNumberFormat());
        cTLvl.addNewLvlText().setVal(listType.getFormat());
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);

        abstractNumID = numbering.addAbstractNum(abstractNum);

        return numbering.addNum(abstractNumID);
    }

}
