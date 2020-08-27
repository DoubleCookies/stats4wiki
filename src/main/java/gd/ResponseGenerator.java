package gd;

import gd.enums.Difficulty;
import gd.enums.ListType;
import gd.model.GDLevel;

import java.util.ArrayList;
import java.util.List;

import static gd.enums.ListType.*;

/**
 * Class for generating level lists
 */
public class ResponseGenerator {

    private static final String WIKITABLE_START = "{| class=\"wikitable\"\n" +
            "! Место\n" +
            "! Уровень\n" +
            "! Создатель\n" +
            "! [[Уровни сложности|Сложность]]\n" +
            "! Кол-во загрузок\n" +
            "! Кол-во лайков\n";
    private static final String ARRAY_START = "{{#arraydefine:levels|TEST_VALUE_FOR_SHIFT,\n";
    private static final String PAGE_END = "}}{{#vardefineecho:number|{{#expr:{{#arraysearch:levels|{{{1}}}}}}}}}<noinclude>{{doc}}[[Категория:Информационные шаблоны]]</noinclude>";

    private static List<GDLevel> mostDownloadedLevels;
    private static List<GDLevel> mostDownloadedDemons;

    static String generateMostDownloadedList() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(WIKITABLE_START);
        if (mostDownloadedLevels == null || mostDownloadedLevels.isEmpty()) {
            mostDownloadedLevels = processListUpdate(LEVELS_DOWNLOADS);
        }
        for (GDLevel level : mostDownloadedLevels) {
            builder.append("|-\n");
            builder.append(level.wikiString(counter)).append("\n");
            counter++;
        }
        builder.append("|}");
        return builder.toString();
    }

    static String generateMostDownloadedListSmall() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(ARRAY_START);
        for (GDLevel level : mostDownloadedLevels) {
            counter++;
            builder.append(level.smallWikiString()).append(counter == 50 ? "\n" : ",\n");
        }
        builder.append(PAGE_END);
        return builder.toString();
    }

    static String generateMostDownloadedListForDemons() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(WIKITABLE_START);
        if (mostDownloadedDemons == null || mostDownloadedDemons.isEmpty()) {
            mostDownloadedDemons = processListUpdate(DEMONS_DOWNLOADS);
        }
        for (GDLevel level : mostDownloadedDemons) {
            builder.append("|-\n");
            builder.append(level.wikiString(counter)).append("\n");
            counter++;
        }
        builder.append("|}");
        return builder.toString();
    }

    static String generateMostDownloadedListSmallForDemons() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(ARRAY_START);
        for (GDLevel level : mostDownloadedDemons) {
            counter++;
            builder.append(level.smallWikiString()).append(counter == 50 ? "\n" : ",\n");
        }
        builder.append(PAGE_END);
        return builder.toString();
    }

    static String generateMostLikedList() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(WIKITABLE_START);
        List<GDLevel> list = processListUpdate(LEVELS_LIKES);
        for (GDLevel level : list) {
            builder.append("|-\n");
            builder.append(level.wikiString(counter)).append("\n");
            counter++;
        }
        builder.append("|}");
        return builder.toString();
    }

    private static List<GDLevel> processListUpdate(ListType type) {
        List<GDLevel> list = new ArrayList<>();
        int i = 0;
        int count = 0;
        try {
            while (count < 50) {
                switch (type) {
                    case LEVELS_LIKES:
                    case LEVELS_DOWNLOADS: {
                        String res = type == LEVELS_LIKES ? GDServer.fetchMostLikedLevels(i) : GDServer.fetchMostPopularLevels(i);
                        for (int j = 0; j < 10; j++) {
                            GDLevel level = getLevel(j, res);
                            list.add(level);
                            count++;
                            if (count >= 50)
                                break;
                        }
                        i++;
                        break;
                    }
                    case DEMONS_DOWNLOADS: {
                        String res = GDServer.fetchMostPopularLevels(i);
                        for (int j = 0; j < 10; j++) {
                            GDLevel level = getLevel(j, res);
                            if (level.getDifficulty() == Difficulty.DEMON) {
                                list.add(level);
                                count++;
                            }
                            if (count >= 50)
                                break;
                        }
                        i++;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Limit reached!");
        }
        return list;
    }

    private static GDLevel getLevel(int j, String res) {
        return GDLevelFactory.buildGDLevelSearchedByFilter(res, j, false);
    }
}
