package gd;

import gd.enums.ListType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final String TOP_LEVELS_PAGE_START = "{{Фан-статья}}\n" +
            "{{Связанный шаблон|[[Шаблон:Топ 50 популярных уровней|данном шаблоне]]}}\n" +
            "\n" +
            "В представленных ниже списках находятся 50 самых популярных уровней по количеству загрузок и по количеству лайков.\n" +
            "\n" +
            "<tabber>Топ по загрузкам=\n" +
            "<div class=\"recordboxmedium\">\n";

    private static final String TOP_LEVELS_PAGE_FINISH = "\n</div>\n" +
            "</tabber>\n" +
            "\n" +
            "== Интересные факты ==\n" +
            "* В обоих топах нет ни одного уровня со сложностью {{Средний демон}} и {{Безумный демон}}.\n" +
            "* [[Bloodbath]] — единственный {{Экстремальный демон}} в данных топах.\n" +
            "* [[Phantom]], [[Dinosaur]] и [[Shock]] — единственные [[Зал славы|эпические]] уровни в данных топах, причём [[Phantom]] и [[Dinosaur]] есть в обоих топах, а [[Shock]] есть только в топе по лайкам.\n" +
            "* [[Fernanfloo 3]] — единственный неоценённый уровень в данных топах." +
            "[[Категория:Топы]]\n" +
            "[[Категория:Пользовательские уровни]]\n" +
            "[[Категория:Уровни]]";

    private static final String TOP_DEMONS_PAGE_START = "{{Фан-статья}}\n" +
            "{{Связанный шаблон|[[Шаблон:Топ 50 популярных демонов|данном шаблоне]]}}\n" +
            "\n" +
            "В представленных ниже списках находятся 50 самых популярных демонов по количеству загрузок и по количеству лайков.\n\n" +
            "<tabber>Топ по загрузкам=\n" +
            "<div class=\"recordboxmedium\">\n";


    private static final String TOP_DEMONS_PAGE_FINISH = "\n</div>\n" +
            "</tabber>\n" +
            "\n" +
            "== Интересные факты ==\n" +
            "* [[Extinction]], [[FREEDOM]] и [[Bloodlust]] — единственные [[Зал славы|эпические]] уровни в топе.\n" +
            "* [[Theory of Skrillex]], [[Demon mixed]], [[Ultra Paracosm]] и [[Sonic Wave]] — единственные демоны из топа, не имеющие [[Featured-уровни|Featured]].\n" +
            "* В данном топе присутствуют демоны всех сложностей.\n" +
            "[[Категория:Демоны]]\n" +
            "[[Категория:Пользовательские уровни]]\n" +
            "[[Категория:Уровни]]\n" +
            "[[Категория:Топы]]";

    private static final String TOP_MEDIUM_SECTION =  "\n</div>\n" +
            "|-|Топ по лайкам=\n" +
            "<div class=\"recordboxmedium\">\n";

    static String mostDownloadedLevels;
    static String mostLikedLevels;
    static String mostDownloadedLevelsForDemons;
    static String mostLikedLevelsForDemons;

    public static void main(String[] args) {
        getLevelsForLists();
        generateTables();
        generateTextForCopy();
    }

    private static void getLevelsForLists() {
        ResponseGenerator.getLevelsForLists();
    }

    private static void generateTables() {
        generateMostDownloadedLevels();
        generateMostLikedLevels();
        generateMostDownloadedLevelsForDemons();
        generateMostLikedLevelsForDemons();
        generateMostDownloadedLevelsSmall();
        generateMostDownloadedLevelsSmallForDemons();
    }

    private static void generateTextForCopy() {
        generateMostDownloadedAndLikedCopyText();
        generateMostDownloadedDemonsCopyText();
    }

    private static void generateMostDownloadedLevels() {
        mostDownloadedLevels = ResponseGenerator.createTableForLevels(ListType.DOWNLOAD_LEVELS);
        writeToFile("Most downloaded", mostDownloadedLevels.getBytes());
    }

    private static void generateMostDownloadedLevelsForDemons() {
        mostDownloadedLevelsForDemons = ResponseGenerator.createTableForLevels(ListType.DOWNLOAD_DEMONS);
        writeToFile("Most downloaded for demons", mostDownloadedLevelsForDemons.getBytes());
    }

    private static void generateMostLikedLevels() {
        mostLikedLevels = ResponseGenerator.createTableForLevels(ListType.LIKED_LEVELS);
        writeToFile("Most liked", mostLikedLevels.getBytes());
    }

    private static void generateMostLikedLevelsForDemons() {
        mostLikedLevelsForDemons = ResponseGenerator.createTableForLevels(ListType.LIKED_DEMONS);
        writeToFile("Most liked for demons", mostLikedLevels.getBytes());
    }

    private static void generateMostDownloadedLevelsSmall() {
        String res = ResponseGenerator.createShortList(ListType.DOWNLOAD_LEVELS);
        writeToFile("Most downloaded small", res.getBytes());
    }

    private static void generateMostDownloadedLevelsSmallForDemons() {
        String res = ResponseGenerator.createShortList(ListType.DOWNLOAD_DEMONS);
        writeToFile("Most downloaded small for demons", res.getBytes());
    }

    private static void generateMostDownloadedAndLikedCopyText() {
        String result = TOP_LEVELS_PAGE_START + mostDownloadedLevels
                + TOP_MEDIUM_SECTION
                + mostLikedLevels + TOP_LEVELS_PAGE_FINISH;
        writeToFile("Copy text", result.getBytes());
    }

    private static void generateMostDownloadedDemonsCopyText() {
        String result = TOP_DEMONS_PAGE_START + mostDownloadedLevelsForDemons
                + TOP_MEDIUM_SECTION
                + mostLikedLevelsForDemons + TOP_DEMONS_PAGE_FINISH;
        writeToFile("Copy text for demons", result.getBytes());
    }

    private static void writeToFile(String prefix, byte[] data) {
        try {
            FileOutputStream out = getFileOutputStream(prefix);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileOutputStream getFileOutputStream(String prefix) throws IOException {
        String baseFolder = "Statistics";
        Path path = Paths.get(baseFolder);
        if (!Files.exists(path))
            Files.createDirectories(path);
        baseFolder += "/";
        return new FileOutputStream(baseFolder + prefix + " list.txt");
    }
}
