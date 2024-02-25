package fr.antoine.files;

import fr.antoine.Main;
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
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

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
                final Question question = new Question(readTextFromXML(this.questionTextIndex, document), readTextFromXML(this.generalFeedbackIndex, document)
                , getImagesFromXML(document, Balise.QUESTION_TEXT), getImagesFromXML(document, Balise.GENERAL_FEEDBACK));

                question.registerProposition(readTextFromXML(this.aIndex, document), readTextFromXML(this.aIndex, document),
                        getImagesFromXML(document, Balise.ANSWER, 0), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 0), isPropositionCorrect(this.aIndex, document));
                question.registerProposition(readTextFromXML(this.bIndex, document), readTextFromXML(this.bIndex, document),
                        getImagesFromXML(document, Balise.ANSWER, 1), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 1), isPropositionCorrect(this.aIndex, document));
                question.registerProposition(readTextFromXML(this.cIndex, document), readTextFromXML(this.cIndex, document),
                        getImagesFromXML(document, Balise.ANSWER, 2), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 2), isPropositionCorrect(this.aIndex, document));
                question.registerProposition(readTextFromXML(this.dIndex, document), readTextFromXML(this.dIndex, document),
                        getImagesFromXML(document, Balise.ANSWER, 3), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 3), isPropositionCorrect(this.aIndex, document));
                question.registerProposition(readTextFromXML(this.eIndex, document), readTextFromXML(this.eIndex, document),
                        getImagesFromXML(document, Balise.ANSWER, 4), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 4), isPropositionCorrect(this.aIndex, document));

                Main.getInstance().getQuestions().add(question);

            } catch (SAXException | IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private String[] getImagesFromXML(final Document document, final Balise balise) {

        return getImagesFromXML(document, balise, 0);

    }

    private String[] getImagesFromXML(final Document document, final Balise balise, final int index) {

        NodeList list = document.getElementsByTagName(balise.getName()),
                        childList = null;

        if(list.item(index) != null)

            childList = list.item(index).getChildNodes();

        if(childList == null)

            return new String[] {};

        final StringBuilder images = new StringBuilder();

        for(int i = 0; i < childList.getLength(); i++) {

            if (childList.item(i).getTextContent().matches("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$"))

                images.append(childList.item(i).getTextContent());

        }

        final String[] base64Images = images.toString().split("=");

        for(int i = 0; i < base64Images.length; i++)

            base64Images[i] = base64Images[i] + "=";

        return base64Images;

    }

    private String readTextFromXML(final int index, final Document document) {

        String text = document.getElementsByTagName("text").item(index).getTextContent();
        text = text.replaceAll("<.*?>", "");
        text = text.replaceAll("QCM\\s\\d+\\.", "");
        text = text.replaceAll("QCM\\s\\d+\\s-\\s", "");

        return text;

    }

    private boolean isPropositionCorrect(final int index, final Document document) {

        final int answerIndex = index == 6 ? 0 : index == 8 ? 1 : index == 10 ? 2 : index == 12 ? 3 : index == 14 ? 4 : -1;
        final Element element = (Element) document.getElementsByTagName("answer").item(answerIndex);

        return !element.getAttribute("fraction").contains("-");

    }

    private enum Balise {

        QUESTION_TEXT("questiontext"),
        GENERAL_FEEDBACK("generalfeedback"),
        ANSWER("answer"),
        ANSWER_FEEDBACK("answer");

        private final String name;

        Balise(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
