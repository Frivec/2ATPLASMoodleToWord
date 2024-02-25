package fr.antoine;

import fr.antoine.files.FileManager;
import fr.antoine.questions.Question;
import fr.antoine.word.ListType;
import fr.antoine.word.WordCreator;
import jakarta.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static Main instance;

    private final FileManager fileManager;
    private final LinkedList<Question> questions;

    public static void main(String[] args) throws Exception { new Main(); }

    public Main() {

        instance = this;

        this.questions = new LinkedList<>();

        this.fileManager = new FileManager();
        this.fileManager.registerFiles();
        this.fileManager.readFiles();

        final WordCreator wordCreator = new WordCreator(Paths.get("C:/Users/antoi/Desktop/"), "Colle n°2");
        wordCreator.createWordFile();

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
