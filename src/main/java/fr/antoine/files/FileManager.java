package fr.antoine.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class FileManager {

    private final Path folder;
    private final HashSet<Path> questionsFiles;

    public FileManager() {

        this.folder = Paths.get("/questions/");
        this.questionsFiles = new HashSet<>();

    }

    public void readFiles() {

        try {

            //Create repository if not exists
            if(Files.notExists(this.folder)) Files.createDirectory(this.folder);

            //Get all files in the folder
            Files.list(this.folder).forEach(this.questionsFiles::add);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
