package gd;

import gd.enums.Difficulty;
import gd.model.GDLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for generating level lists
 */
public class ResponseGenerator {

    static String generateMostDownloadedList() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("{| class=\"wikitable\"\n" +
                "! Место\n" +
                "! Уровень\n" +
                "! Создатель\n" +
                "! [[Уровни сложности|Сложность]]\n" +
                "! Кол-во загрузок\n" +
                "! Кол-во лайков\n");
        List<GDLevel> list = getMostDownloadedLevels();
        for(GDLevel level : list)
        {
            if(counter < 50)
            {
                builder.append("|-\n");
                builder.append(level.wikiString(counter)).append("\n");
                counter++;
            }
        }
        builder.append("|}");
        return builder.toString();
    }

    static String generateMostDownloadedListSmall() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("{{#arraydefine:levels|TEST_VALUE_FOR_SHIFT,\n");
        List<GDLevel> list = getMostDownloadedLevels();
        for(GDLevel level : list)
        {
            if(counter < 50)
            {
                counter++;
                if(counter == 50)
                    builder.append(level.smallWikiString()).append("\n");
                else
                    builder.append(level.smallWikiString()).append(",\n");
            }
        }
        builder.append("}}{{#vardefineecho:number|{{#expr:{{#arraysearch:levels|{{{1}}}}}}}}}<noinclude>{{doc}}[[Категория:Информационные шаблоны]]</noinclude>");
        return builder.toString();
    }

    static String generateMostDownloadedListForDemons() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("{| class=\"wikitable\"\n" +
                "! Место\n" +
                "! Уровень\n" +
                "! Создатель\n" +
                "! [[Уровни сложности|Сложность]]\n" +
                "! Кол-во загрузок\n" +
                "! Кол-во лайков\n");
        List<GDLevel> list = getMostDownloadedLevelsForDemons();
        for(GDLevel level : list)
        {
            if(level.getDifficulty() == Difficulty.DEMON) {
                if(counter < 50)
                {
                    builder.append("|-\n");
                    builder.append(level.wikiString(counter)).append("\n");
                    counter++;
                }
            }
        }
        builder.append("|}");
        return builder.toString();
    }

    static String generateMostDownloadedListSmallForDemons() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("{{#arraydefine:levels|TEST_VALUE_FOR_SHIFT,\n");
        List<GDLevel> list = getMostDownloadedLevelsForDemons();
        for(GDLevel level : list)
        {
            if(level.getDifficulty() == Difficulty.DEMON) {
                if(counter < 50)
                {
                    counter++;
                    if(counter == 50)
                        builder.append(level.smallWikiString()).append("\n");
                    else
                        builder.append(level.smallWikiString()).append(",\n");
                }
            }
        }
        builder.append("}}{{#vardefineecho:number|{{#expr:{{#arraysearch:levels|{{{1}}}}}}}}}<noinclude>{{doc}}[[Категория:Информационные шаблоны]]</noinclude>");
        return builder.toString();
    }

    static String generateMostLikedList() {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("{| class=\"wikitable\"\n" +
                "! Место\n" +
                "! Уровень\n" +
                "! Создатель\n" +
                "! [[Уровни сложности|Сложность]]\n" +
                "! Кол-во загрузок\n" +
                "! Кол-во лайков\n");
        List<GDLevel> list = getMostLikedLevels();
        for(GDLevel level : list)
        {
            if(counter < 50)
            {
                builder.append("|-\n");
                builder.append(level.wikiString(counter)).append("\n");
                counter++;
            }
        }
        builder.append("|}");
        return builder.toString();
    }

    private static List<GDLevel> getMostDownloadedLevels() {
        List<GDLevel> list = new ArrayList<>();
        int i = 0;
        int count = 0;
        try {
            while(count < 50) {
                String res = GDServer.fetchMostPopularLevels(i);
                for (int j = 0; j < 10; j++) {
                    GDLevel level = getLevel(j, res);
                    list.add(level);
                    count++;
                    if(count > 50)
                        break;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Limit reached!");
        }
        return list;
    }

    private static List<GDLevel> getMostDownloadedLevelsForDemons() {
        List<GDLevel> list = new ArrayList<>();
        int i = 0;
        int count = 0;
        try {
            while(count < 50) {
                String res = GDServer.fetchMostPopularLevels(i);
                for (int j = 0; j < 10; j++) {
                    GDLevel level = getLevel(j, res);

                    if(level.getDifficulty() == Difficulty.DEMON) {
                        list.add(level);
                        count++;
                    }
                    if(count > 50)
                        break;
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Limit reached!");
        }
        return list;
    }

    private static List<GDLevel> getMostLikedLevels() {
        List<GDLevel> list = new ArrayList<>();
        int i = 0;
        int count = 0;
        try {
            while(count < 50) {
                String res = GDServer.fetchMostLikedLevels(i);
                for (int j = 0; j < 10; j++) {
                    GDLevel level = getLevel(j, res);
                    list.add(level);
                    count++;
                    if(count > 50)
                        break;
                }
                i++;
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
