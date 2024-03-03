package fr.antoine;

import fr.antoine.files.FileManager;
import fr.antoine.gui.MainFrame;
import fr.antoine.questions.tests.Test;
import fr.antoine.questions.tests.TestType;

import java.util.LinkedHashMap;

public class Main {

    private static Main instance;

    private final MainFrame mainFrame;
    private final LinkedHashMap<TestType, Test> tests;

    public static void main(String[] args) { new Main(); }

    public Main() {

        instance = this;

        this.tests = new LinkedHashMap<>();

        this.mainFrame = new MainFrame(); //Start frame

        final FileManager fileManager = new FileManager();
        fileManager.registerFiles();
        fileManager.readFiles();

    }

    public static Main getInstance() {
        return instance;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public LinkedHashMap<TestType, Test> getTests() {
        return tests;
    }
}
