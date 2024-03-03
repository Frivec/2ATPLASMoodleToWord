package fr.antoine.files;

import fr.antoine.Main;
import fr.antoine.gui.ProgressBar;
import fr.antoine.questions.Question;
import fr.antoine.questions.tests.Test;
import fr.antoine.questions.tests.TestType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileManager {

    private final Path folder;
    private ProgressBar bar;

    private final LinkedHashMap<String, LinkedList<Path>> questionsFiles;

    public FileManager() {

        this.folder = Paths.get("questions");
        this.questionsFiles = new LinkedHashMap<>();

    }

    public void registerFiles() {

        try {

            //Create repository if not exists
            if(Files.notExists(this.folder)) {

                Files.createDirectory(this.folder);

                for(TestType types : TestType.values())

                    Files.createDirectory(Paths.get(this.folder + "/" + types.getFrenchName()));

                JOptionPane.showMessageDialog(Main.getInstance().getMainFrame(), "Le dossier \"questions\" a été créé. \nTu peux ajouter les fichiers Moodle et relancer l'application !");
                System.exit(0);

            }

            //Get all files in the folder
            Files.list(this.folder).forEach(folder -> {

                if(Files.isDirectory(folder)) {

                    final String testName = folder.toFile().getName();

                    Main.getInstance().getTests().put(TestType.getTypeByName(testName), new Test(testName, TestType.getTypeByName(testName)));
                    this.questionsFiles.put(testName, new LinkedList<>());

                    try {

                        Files.list(folder).forEach(file -> this.questionsFiles.get(testName).add(file));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            });

            boolean areAllEmpty = true;

            for(Map.Entry<String, LinkedList<Path>> entries : this.questionsFiles.entrySet())

                if(!entries.getValue().isEmpty()) areAllEmpty = false;

            if(areAllEmpty) {

                JOptionPane.showMessageDialog(Main.getInstance().getMainFrame(), "Aucune question n'a été trouvée.\nAjoute tes fichiers Moodle et relance l'application !");
                System.exit(0);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void readFiles() {

        final int questionTextIndex = 1, generalFeedbackIndex = 2,
                aIndex = 6, aFeedbackIndex = 7,
                bIndex = 8, bFeedbackIndex = 9,
                cIndex = 10, cFeedbackIndex = 11,
                dIndex = 12, dFeedbackIndex = 13,
                eIndex = 14, eFeedbackIndex = 15;

        this.bar = new ProgressBar("Enregistrement des questions", 100);
        this.bar.showProgressBar();

        for(Map.Entry<String, LinkedList<Path>> entries : this.questionsFiles.entrySet()) {

            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final Test test = Main.getInstance().getTests().get(TestType.getTypeByName(entries.getKey()));

            this.bar.setTitle("Enregistrement : " + entries.getKey());
            this.bar.setMaximum(entries.getValue().size());

            int index = 0;

            for(Path files : entries.getValue()) {

                try {

                    final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    final Document document = documentBuilder.parse(files.toFile());
                    final Question question = new Question(readTextFromXML(questionTextIndex, document), readTextFromXML(generalFeedbackIndex, document)
                            , getImagesFromXML(document, Balise.QUESTION_TEXT), getImagesFromXML(document, Balise.GENERAL_FEEDBACK));

                    question.registerProposition(readTextFromXML(aIndex, document), readTextFromXML(aFeedbackIndex, document),
                            getImagesFromXML(document, Balise.ANSWER, 0), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 0), isPropositionCorrect(aIndex, document));
                    question.registerProposition(readTextFromXML(bIndex, document), readTextFromXML(bFeedbackIndex, document),
                            getImagesFromXML(document, Balise.ANSWER, 1), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 1), isPropositionCorrect(bIndex, document));
                    question.registerProposition(readTextFromXML(cIndex, document), readTextFromXML(cFeedbackIndex, document),
                            getImagesFromXML(document, Balise.ANSWER, 2), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 2), isPropositionCorrect(cIndex, document));
                    question.registerProposition(readTextFromXML(dIndex, document), readTextFromXML(dFeedbackIndex, document),
                            getImagesFromXML(document, Balise.ANSWER, 3), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 3), isPropositionCorrect(dIndex, document));
                    question.registerProposition(readTextFromXML(eIndex, document), readTextFromXML(eFeedbackIndex, document),
                            getImagesFromXML(document, Balise.ANSWER, 4), getImagesFromXML(document, Balise.ANSWER_FEEDBACK, 4), isPropositionCorrect(eIndex, document));

                    test.getQuestions().add(question);

                    Thread.sleep(10);

                } catch (SAXException | IOException | ParserConfigurationException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                this.bar.updateBar(index);
                index++;

            }

        }

        this.bar.getjDialog().dispose();
        JOptionPane.showMessageDialog(Main.getInstance().getMainFrame(), "Toutes Les questions ont été enregistrées !\nTu peux générer la colle !");

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
