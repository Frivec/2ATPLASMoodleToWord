package fr.antoine;

import fr.antoine.files.FileManager;
import fr.antoine.questions.tests.Test;
import fr.antoine.questions.tests.TestType;
import fr.antoine.word.WordCreator;

import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class Main {

    private static Main instance;

    private final FileManager fileManager;
    private final LinkedHashMap<TestType, Test> tests;

    public static void main(String[] args) { new Main(); }

    public Main() {

        instance = this;

        this.tests = new LinkedHashMap<>();

        this.fileManager = new FileManager();
        this.fileManager.registerFiles();
        this.fileManager.readFiles();

        System.out.println("Tests : " + this.tests.get(TestType.ANATOMY).getQuestions().size());

        final WordCreator wordCreator = new WordCreator(Paths.get("C:/Users/antoi/Desktop/"), "Colle n°2");
        wordCreator.createWordFile(false); //Creation of a subject
        wordCreator.createWordFile(true); //Creation of a correction

    }

    public static Main getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public LinkedHashMap<TestType, Test> getTests() {
        return tests;
    }
}
