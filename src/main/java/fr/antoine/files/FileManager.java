package fr.antoine.files;

import fr.antoine.questions.Question;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;

public class FileManager {

    private final Path folder;
    private final int questionTextIndex = 1, generalFeedbackIndex = 2,
            aIndex = 6, aFeedbackIndex = 7,
            bIndex = 8, bFeedbackIndex = 9,
            cIndex = 10, cFeedbackIndex = 11,
            dIndex = 12, dFeedbackIndex = 13,
            eIndex = 14, eFeedbackIndex = 15;
    private final LinkedHashSet<Path> questionsFiles;

    public FileManager() {

        this.folder = Paths.get("/questions/");
        this.questionsFiles = new LinkedHashSet<>();

    }

    public void registerFiles() {

        try {

            //Create repository if not exists
            if(Files.notExists(this.folder)) Files.createDirectory(this.folder);

            //Get all files in the folder
            Files.list(this.folder).forEach(this.questionsFiles::add);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void readFiles() {

        this.questionsFiles.forEach(file -> {

            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            try {

                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final Document document = documentBuilder.parse(file.toFile());
                final Question question = new Question(readTextFromXML(this.questionTextIndex, document));

                question.registerProposition(readTextFromXML(this.aIndex, document), isPropositionCorrect(this.aIndex, document));
                question.registerProposition(readTextFromXML(this.bIndex, document), isPropositionCorrect(this.bIndex, document));
                question.registerProposition(readTextFromXML(this.cIndex, document), isPropositionCorrect(this.cIndex, document));
                question.registerProposition(readTextFromXML(this.dIndex, document), isPropositionCorrect(this.dIndex, document));
                question.registerProposition(readTextFromXML(this.eIndex, document), isPropositionCorrect(this.eIndex, document));

                System.out.println(question.getPropositions().get(4));

            } catch (SAXException | IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private String readTextFromXML(final int index, final Document document) {

        String text = document.getElementsByTagName("text").item(index).getTextContent();
        text = text.replaceAll("<.*?>", "");

        return text;

    }

    private boolean isPropositionCorrect(final int index, final Document document) {

        final int answerIndex = index == 6 ? 0 : index == 8 ? 1 : index == 10 ? 2 : index == 12 ? 3 : index == 14 ? 4 : -1;
        final Element element = (Element) document.getElementsByTagName("answer").item(answerIndex);

        return !element.getAttribute("fraction").contains("-");

    }

}
