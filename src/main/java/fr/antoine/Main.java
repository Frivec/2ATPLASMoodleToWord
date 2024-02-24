package fr.antoine;

import fr.antoine.files.FileManager;

public class Main {

    private static Main instance;

    private final FileManager fileManager;

    public static void main(String[] args) { new Main(); }

    public Main() {

        instance = this;

        this.fileManager = new FileManager();
        this.fileManager.registerFiles();
        this.fileManager.readFiles();

    }

    public static Main getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
