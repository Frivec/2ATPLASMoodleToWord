package fr.antoine;

import fr.antoine.files.FileManager;

public class Main {

    private static Main instance;

    public static void main(String[] args) { new Main(); }

    public Main() {

        instance = this;

        new FileManager().readFiles();

    }

    public static Main getInstance() {
        return instance;
    }
}
