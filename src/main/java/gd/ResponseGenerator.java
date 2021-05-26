package gd;

import gd.enums.Difficulty;
import gd.enums.ListType;
import gd.model.GDLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for generating level lists
 */
public class ResponseGenerator {

    private static final int LIST_SIZE = 50;
    private static final int GD_PAGE_SIZE = 10;
    private static final String WIKITABLE_START = "{| class=\"wikitable\"\n" +
            "! Место\n" +
            "! Уровень\n" +
            "! Создатель\n" +
            "! [[Уровни сложности|Сложность]]\n" +
            "! Кол-во загрузок\n" +
            "! Кол-во лайков\n";
    private static final String WIKITABLE_END = "|}";
    private static final String WIKITABLE_NEWLINE = "|-\n";
    private static final String ARRAY_START = "{{#arraydefine:levels|TEST_VALUE_FOR_SHIFT,\n";
    private static final String ARRAY_END = "}}{{#vardefineecho:number|{{#expr:{{#arraysearch:levels|{{{1}}}}}}}}}<noinclude>{{doc}}[[Категория:Информационные шаблоны]]</noinclude>";

    private static List<GDLevel> mostDownloadedLevels;
    private static List<GDLevel> mostDownloadedDemons;

    private static List<GDLevel> mostLikedLevels;
    private static List<GDLevel> mostLikedDemons;

    static void getLevelsForLists() {
        getListForType(ListType.DOWNLOAD_LEVELS);
        getListForType(ListType.LIKED_LEVELS);
    }

    static String createShortList(ListType type) {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(ARRAY_START);
        List<GDLevel> levelsForList = type == ListType.DOWNLOAD_LEVELS ? mostDownloadedLevels : mostDownloadedDemons;
        for (GDLevel level : levelsForList) {
            counter++;
            builder.append(level.smallWikiString()).append(counter == LIST_SIZE ? "\n" : ",\n");
        }
        builder.append(ARRAY_END);
        return builder.toString();
    }

    static String createTableForLevels(ListType listType) {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(WIKITABLE_START);
        List<GDLevel> levelsForList = selectLevelsList(listType);
        for (GDLevel level : levelsForList) {
            builder.append(WIKITABLE_NEWLINE).append(level.wikiString(counter)).append("\n");
            counter++;
        }
        builder.append(WIKITABLE_END);
        return builder.toString();
    }

    private static List<GDLevel> selectLevelsList(ListType listType) {
        switch (listType) {
            case DOWNLOAD_DEMONS: { return mostDownloadedDemons;  }
            case LIKED_LEVELS: { return mostLikedLevels; }
            case LIKED_DEMONS: { return mostLikedDemons; }
            default: { return mostDownloadedLevels; }
        }
    }

    private static void getListForType(ListType type) {
        List<GDLevel> list = fillListWithLevels(type);
        fillTypedList(type, list);
    }

    private static void fillTypedList(ListType type, List<GDLevel> list) {
        switch (type) {
            case DOWNLOAD_LEVELS:
            case DOWNLOAD_DEMONS: {
                mostDownloadedLevels = list.subList(0, LIST_SIZE);
                mostDownloadedDemons = list.stream().filter(level -> level.getDifficulty() == Difficulty.DEMON).collect(Collectors.toList());
                break;
            }
            case LIKED_LEVELS:
            case LIKED_DEMONS: {
                mostLikedLevels = list.subList(0, LIST_SIZE);
                mostLikedDemons = list.stream().filter(level -> level.getDifficulty() == Difficulty.DEMON).collect(Collectors.toList());
                break;
            }
        }
    }

    private static List<GDLevel> fillListWithLevels(ListType type) {
        List<GDLevel> list = new ArrayList<>();
        int demonsCount = 0;
        int i = 0;
        try {
            while (demonsCount < LIST_SIZE) {
                String rawData = (type == ListType.DOWNLOAD_LEVELS || type ==  ListType.DOWNLOAD_DEMONS)
                        ? GDServer.fetchMostPopularLevels(i) : GDServer.fetchMostLikedLevels(i);
                for (int j = 0; j < GD_PAGE_SIZE; j++) {
                    GDLevel level = getLevel(rawData, j);
                    list.add(level);
                    if (level.getDifficulty() == Difficulty.DEMON)
                        demonsCount++;
                    if (demonsCount >= LIST_SIZE)
                        break;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Limit reached!");
        }
        return list;
    }

    private static GDLevel getLevel(String rawData, int index) {
        return GDLevelFactory.buildGDLevelSearchedByFilter(rawData, index);
    }
}
