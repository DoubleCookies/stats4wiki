package gd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    static String mostDownloadedLevels;
    static String mostLikedLevels;
    static String mostDownloadedLevelsForDemons;

    public static void main(String[] args) {
        generateMostDownloadedLevels();
        generateMostLikedLevels();
        generateMostDownloadedLevelsForDemons();
        generateMostDownloadedLevelsSmall();
        generateMostDownloadedLevelsSmallForDemons();
        generateMostDownloadedAndLikedCopyText();
        generateMostDownloadedAndLikedDemonsCopyText();
    }

    private static void generateMostDownloadedLevels() {
        mostDownloadedLevels = ResponseGenerator.generateMostDownloadedList();
        writeToFile("Most downloaded", mostDownloadedLevels.getBytes());
    }

    private static void generateMostDownloadedLevelsForDemons() {
        mostDownloadedLevelsForDemons = ResponseGenerator.generateMostDownloadedListForDemons();
        writeToFile("Most downloaded for demons", mostDownloadedLevelsForDemons.getBytes());
    }

    private static void generateMostLikedLevels() {
        mostLikedLevels = ResponseGenerator.generateMostLikedList();
        writeToFile("Most liked", mostLikedLevels.getBytes());
    }

    private static void generateMostDownloadedLevelsSmall() {
        String res = ResponseGenerator.generateMostDownloadedListSmall();
        writeToFile("Most downloaded small", res.getBytes());
    }

    private static void generateMostDownloadedLevelsSmallForDemons() {
        String res = ResponseGenerator.generateMostDownloadedListSmallForDemons();
        writeToFile("Most downloaded small for demons", res.getBytes());
    }

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
                "* [[Phantom]], [[Dinosaur]] и [[Shock]] — единственные [[Зал славы|эпические]] уровни в данных топах, причём [[Phantom]] и [[Dinosaur]] есть в обоих топах, а [[Shock]] есть только в топе по лайкам.\n" +
                "* [[Fernanfloo 3]] — единственный неоценённый уровень в данных топах.";
        String result = start + mostDownloadedLevels + medium + mostLikedLevels + finish;
        writeToFile("Copy text", result.getBytes());
    }

    private static void generateMostDownloadedAndLikedDemonsCopyText() {
        String start = "{{Фан-статья}}\n" +
                "{{Связанный шаблон|[[Шаблон:Топ 50 популярных демонов|данном шаблоне]]}}\n" +
                "\n" +
                "В представленном ниже списке находятся 50 самых популярных {{Демон}} уровней в [[Geometry Dash]] '''по количеству загрузок'''.\n\n" +
                "<div class=\"recordboxmedium\">\n";

        String finish = "\n</div>\n" +
                "\n" +
                "== Интересные факты ==\n" +
                "* [[Extinction]], [[FREEDOM]] и [[Bloodlust]] — единственные [[Зал славы|эпические]] уровни в топе.\n" +
                "* В данном топе присутствуют демоны всех сложностей.";
        String result = start + mostDownloadedLevelsForDemons + finish;
        writeToFile("Copy text for demons", result.getBytes());
    }

    private static void writeToFile(String prefix, byte[] data) {
        FileOutputStream out;
        try {
            out = getFileOutputStream(prefix);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileOutputStream getFileOutputStream(String prefix) throws IOException {
        FileOutputStream out;
        String baseFolder = "Statistics";
        Path path = Paths.get(baseFolder);
        if (!Files.exists(path))
            Files.createDirectories(path);
        baseFolder += "/";
        out = new FileOutputStream(baseFolder + prefix + " list.txt");
        return out;
    }
}
