package fr.antoine;

import fr.antoine.files.FileManager;
import fr.antoine.questions.Question;
import fr.antoine.word.WordCreator;

import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {

    private static Main instance;

    private final FileManager fileManager;
    private final LinkedList<Question> questions;

    public static void main(String[] args) { new Main(); }

    public Main() {

        instance = this;

        this.questions = new LinkedList<>();

        this.fileManager = new FileManager();
        this.fileManager.registerFiles();
        this.fileManager.readFiles();

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

    public LinkedList<Question> getQuestions() {
        return questions;
    }
}
