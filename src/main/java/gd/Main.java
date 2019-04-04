package gd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static String mdl;
    static String mll;
    static String mdld;

    public static void main(String[] args) {
        generateMostDownloadedLevels();
        generateMostLikedLevels();
        generateMostDownloadedLevelsSmall();
        generateMostDownloadedLevelsForDemons();
        generateMostDownloadedLevelsSmallForDemons();
        generateMostDownloadedAndLikedCopyText();
        generateMostDownloadedAndLikedDemonsCopyText();
    }

    private static void generateMostDownloadedLevels() {
        mdl = ResponseGenerator.generateMostDownloadedList();
        writeToFile(0, "Most downloaded", mdl.getBytes(), ".txt");
    }

    private static void generateMostDownloadedLevelsSmall() {
        String res = ResponseGenerator.generateMostDownloadedListSmall();
        writeToFile(0, "Most downloaded small", res.getBytes(), ".txt");
    }

    private static void generateMostDownloadedLevelsForDemons() {
        mdld = ResponseGenerator.generateMostDownloadedListForDemons();
        writeToFile(0, "Most downloaded for demons", mdld.getBytes(), ".txt");
    }

    private static void generateMostDownloadedLevelsSmallForDemons() {
        String res = ResponseGenerator.generateMostDownloadedListSmallForDemons();
        writeToFile(0, "Most downloaded small for demons", res.getBytes(), ".txt");
    }

    private static void generateMostLikedLevels() {
        mll = ResponseGenerator.generateMostLikedList();
        writeToFile(0, "Most liked", mll.getBytes(), ".txt");
    }

    //Special method for faster copy-paste process
    private static void generateMostDownloadedAndLikedCopyText() {
        String start = "{{Фан-статья}}\n" +
                "{{Связанный шаблон|[[Шаблон:Топ 50 популярных уровней|данном шаблоне]]}}\n" +
                "\n" +
                "В представленных ниже списках находятся 50 самых популярных уровней по количеству загрузок и по количеству лайков.\n" +
                "\n" +
                "<tabber>Топ по загрузкам=\n" +
                "<div class=\"recordboxmedium\">\n";

        String medium = "\n</div>\n" +
                "|-|Топ по лайкам=\n" +
                "<div class=\"recordboxmedium\">\n";

        String finish = "\n</div>\n" +
                "</tabber>\n" +
                "\n" +
                "== Интересные факты ==\n" +
                "* В обоих топах нет ни одного уровня со сложностью {{Средний демон}} и {{Безумный демон}}.\n" +
                "* [[Bloodbath]] — единственный {{Экстремальный демон}} в данных топах.\n" +
                "* [[Phantom]] и [[Dinosaur]] — единственные [[Зал славы|эпические]] уровни, которые есть в этих топах, причём [[Phantom]] находится только в топе по лайкам, а [[Dinosaur]] — и в топе по загрузкам, и по лайкам.";
        String result = start + mdl + medium + mll + finish;
        writeToFile(0, "copytext", result.getBytes(), ".txt");
    }

    //Special method for faster copy-paste process (demons page)
    private static void generateMostDownloadedAndLikedDemonsCopyText() {
        String start = "{{Фан-статья}}\n" +
                "{{Связанный шаблон|[[Шаблон:Топ 50 популярных демонов|данном шаблоне]]}}\n" +
                "\n" +
                "В представленном ниже списке находятся 50 самых популярных {{Демон}} уровней в [[Geometry Dash]] '''по количеству загрузок'''.\n\n" +
                "<div class=\"recordboxmedium\">\n";

        String finish = "\n</div>\n" +
                "\n" +
                "== Интересные факты ==\n" +
                "* [[Windy Landscape]] — единственный {{Безумный демон}} в топе.\n" +
                "* [[Extinction]] и [[Bloodlust]] — единственные [[Зал славы|эпические]] уровни в топе.\n" +
                "* В данном топе присутствуют демоны всех сложностей.";
        String result = start + mdld + finish;
        writeToFile(0, "copytext demons", result.getBytes(), ".txt");
    }


    private static void writeToFile(int sortingCode, String prefix, byte[] data, String filetype) {
        FileOutputStream out;
        try {
            out = getFileOutputStream(sortingCode, prefix, filetype);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileOutputStream getFileOutputStream(int sortingCode, String prefix, String filetype) throws IOException {
        FileOutputStream out;
        String baseFolder = "Statistics";
        Path path = Paths.get(baseFolder);
        if(!Files.exists(path))
            Files.createDirectories(path);
        baseFolder +="/";

        switch (sortingCode)
        {
            case 1: { out = new FileOutputStream(baseFolder + prefix + " list with descending likes" + filetype); break;}
            case 2: { out = new FileOutputStream(baseFolder + prefix + " list with ascending likes" + filetype); break;}
            case 3: { out = new FileOutputStream(baseFolder + prefix + " list with descending downloads" + filetype); break;}
            case 4: { out = new FileOutputStream(baseFolder + prefix + " list with ascending downloads" + filetype); break;}
            case 5: { out = new FileOutputStream(baseFolder + prefix + " list with longest descriptions" + filetype); break;}
            default: { out = new FileOutputStream(baseFolder + prefix + " list" + filetype); break;}
        }
        return out;
    }
}
