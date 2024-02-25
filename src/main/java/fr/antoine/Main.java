package fr.antoine;

import fr.antoine.files.FileManager;
import fr.antoine.questions.Question;
import fr.antoine.word.ListType;
import jakarta.xml.bind.DatatypeConverter;
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
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Main instance;

    private final FileManager fileManager;
    private final List<Question> questions = new ArrayList<>();

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

    public static void main(String[] args) throws Exception { new Main(); }

    public Main() {

        instance = this;

        this.fileManager = new FileManager();
        this.fileManager.registerFiles();
        this.fileManager.readFiles();

        try (FileOutputStream out = new FileOutputStream("C:/Users/antoi/Desktop/Colle.docx")) {

            final Path wordDocument = Paths.get("C:/Users/antoi/Desktop/Colle.docx");
            final FileInputStream inputStream = new FileInputStream(wordDocument.toFile());

            if(Files.notExists(wordDocument)) Files.createFile(wordDocument);

            final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            final InputStream wordModelStream = classloader.getResourceAsStream("subject.dotx");

            assert wordModelStream != null;

            final OPCPackage opcPackage = OPCPackage.open(wordModelStream);

            opcPackage.replaceContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");
            opcPackage.save(wordDocument.toFile());

            final XWPFDocument document = new XWPFDocument(inputStream);
            final XWPFNumbering numbering = document.createNumbering();
            XWPFParagraph paragraph;

            int listID = 1;

            for(Question question : this.getQuestions()) {

                final BigInteger numID = getNewDecimalNumberingId(numbering, BigInteger.valueOf(0), ListType.QCM);

                paragraph = document.createParagraph();
                paragraph.setNumID(numID);
                paragraph.setStyle("QuestionQCM");

                XWPFRun run = paragraph.createRun();
                run.setText(question.getStatement());

                for (String compressedImage : question.getStatementImage()) {

                    paragraph = document.createParagraph();
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                    run = paragraph.createRun();

                    byte[] imageBytes = Base64.decodeBase64(compressedImage);
                    final Dimension dimension = getDimension(imageBytes);
                    final ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);

                    if (dimension != null)

                        run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, "image.png", Units.toEMU(dimension.getWidth()), Units.toEMU(dimension.getHeight()));

                    imageStream.close();

                }

                final BigInteger propositionNumID = getNewDecimalNumberingId(numbering, BigInteger.valueOf(listID), ListType.ITEM);

                for(Question.Proposition proposition : question.getPropositions()) {

                    paragraph = document.createParagraph();

                    paragraph.setNumID(propositionNumID);
                    paragraph.setStyle("ItemQCM");

                    run = paragraph.createRun();

                    if(!proposition.text().isEmpty())

                        run.setText(proposition.text());

                    for (String compressedImage : proposition.propositionImage()) {

                        byte[] imageBytes = Base64.decodeBase64(compressedImage);
                        final Dimension dimension = getDimension(imageBytes);
                        final ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);

                        if (dimension != null)

                            run.addPicture(imageStream, XWPFDocument.PICTURE_TYPE_PNG, "image.png", Units.toEMU(dimension.getWidth()), Units.toEMU(dimension.getHeight()));

                        imageStream.close();

                    }

                    run = paragraph.createRun();
                    run.setText(proposition.text());

                }

                listID++;

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
     * Return the Dimension of an image from bytes
     * @param imageBytes
     * @return
     * @throws IOException
     */
    private Dimension getDimension(final byte[] imageBytes) throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        final BufferedImage image = ImageIO.read(inputStream);

        if(image == null)

            return null;

        inputStream.close();

        return new Dimension(image.getWidth(), image.getHeight());

    }

    public static Main getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
