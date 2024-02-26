package fr.antoine;

import fr.antoine.files.FileManager;
import fr.antoine.gui.MainFrame;
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

        new MainFrame(); //Start frame

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
