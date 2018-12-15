package gd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        generateMostDownloadedLevels();
        generateMostLikedLevels();
        generateMostDownloadedLevelsSmall();
        generateMostDownloadedLevelsForDemons();
        generateMostDownloadedLevelsSmallForDemons();
    }

    private static void generateMostDownloadedLevels() {
        String res = ResponseGenerator.generateMostDownloadedList();
        writeToFile(0, "Most downloaded", 0, res.getBytes(), ".txt");
    }

    private static void generateMostDownloadedLevelsSmall() {
        String res = ResponseGenerator.generateMostDownloadedListSmall();
        writeToFile(0, "Most downloaded small", 0, res.getBytes(), ".txt");
    }

    private static void generateMostDownloadedLevelsForDemons() {
        String res = ResponseGenerator.generateMostDownloadedListForDemons();
        writeToFile(0, "Most downloaded for demons", 0, res.getBytes(), ".txt");
    }

    private static void generateMostDownloadedLevelsSmallForDemons() {
        String res = ResponseGenerator.generateMostDownloadedListSmallForDemons();
        writeToFile(0, "Most downloaded small for demons", 0, res.getBytes(), ".txt");
    }

    private static void generateMostLikedLevels() {
        String res = ResponseGenerator.generateMostLikedList();
        writeToFile(0, "Most liked", 0, res.getBytes(), ".txt");
    }


    private static void writeToFile(int sortingCode, String prefix, int diffCode, byte[] data, String filetype) {
        FileOutputStream out;
        try {
            out = getFileOutputStream(sortingCode, prefix, diffCode, filetype);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileOutputStream getFileOutputStream(int sortingCode, String prefix, int diffcode, String filetype) throws IOException {
        FileOutputStream out;
        String baseFolder = "Statistics";
        Path path = Paths.get(baseFolder);
        if(!Files.exists(path))
            Files.createDirectories(path);
        baseFolder +="/";

        switch (sortingCode)
        {
            case 1: { out = new FileOutputStream(baseFolder + prefix + " list with descending likes" + filetype); break;}
            case 2: { out = new FileOutputStream(baseFolder + prefix + " list with ascending likes"+ filetype); break;}
            case 3: { out = new FileOutputStream(baseFolder + prefix + " list with descending downloads" + filetype); break;}
            case 4: { out = new FileOutputStream(baseFolder + prefix + " list with ascending downloads" + filetype); break;}
            case 5: { out = new FileOutputStream(baseFolder + prefix + " list with longest descriptions" + filetype); break;}
            default: {out = new FileOutputStream(baseFolder + prefix + " list" + filetype); break;}
        }
        return out;
    }
}
