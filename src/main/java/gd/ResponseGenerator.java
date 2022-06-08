package gd;

import jdash.client.GDClient;
import jdash.common.LevelBrowseMode;
import jdash.common.entity.GDLevel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for generating level lists
 */
public class ResponseGenerator {
    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance();
    private static final int LIST_SIZE = 50;
    private static final int GD_PAGE_SIZE = 10;
    private static final GDClient client = GDClient.create();
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
        getListForType(LevelBrowseMode.MOST_DOWNLOADED);
        getListForType(LevelBrowseMode.MOST_LIKED);
    }

    static String createShortList(ListType type) {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(ARRAY_START);
        List<GDLevel> levelsForList = type == ListType.DOWNLOAD_LEVELS ? mostDownloadedLevels : mostDownloadedDemons;
        for (GDLevel level : levelsForList) {
            counter++;
            builder.append(smallWikiString(level)).append(counter == LIST_SIZE ? "\n" : ",\n");
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
            builder.append(WIKITABLE_NEWLINE).append(wikiString(level, counter)).append("\n");
            counter++;
        }
        builder.append(WIKITABLE_END);
        return builder.toString();
    }

    private static List<GDLevel> selectLevelsList(ListType listType) {
        switch (listType) {
            case DOWNLOAD_DEMONS: {
                return mostDownloadedDemons;
            }
            case LIKED_LEVELS: {
                return mostLikedLevels;
            }
            case LIKED_DEMONS: {
                return mostLikedDemons;
            }
            default: {
                return mostDownloadedLevels;
            }
        }
    }

    private static void getListForType(LevelBrowseMode levelBrowseMode) {
        List<GDLevel> list = fillListWithLevels(levelBrowseMode);
        fillTypedList(levelBrowseMode, list);
    }

    private static List<GDLevel> fillListWithLevels(LevelBrowseMode levelBrowseMode) {
        List<GDLevel> list = new ArrayList<>();
        int demonsCount = 0;
        int i = 0;
        try {
            while (demonsCount < LIST_SIZE) {
                List<GDLevel> levels = client.browseLevels(levelBrowseMode,null, null, i)
                        .collectList().block();
                if (levels != null) {
                    for (int j = 0; j < GD_PAGE_SIZE; j++) {
                        GDLevel level = levels.get(j);
                        list.add(level);
                        if (level.isDemon())
                            demonsCount++;
                        if (demonsCount >= LIST_SIZE)
                            break;
                    }
                    i++;
                } else {
                    throw new Exception("Can't load levels!");
                }
            }
        } catch (Exception e) {
            System.out.println("Limit reached for " + levelBrowseMode.name() + " levels. Message: " + e.getMessage());
        }
        return list;
    }

    private static void fillTypedList(LevelBrowseMode levelBrowseMode, List<GDLevel> list) {
        switch (levelBrowseMode) {
            case MOST_DOWNLOADED: {
                mostDownloadedLevels = list.subList(0, LIST_SIZE);
                mostDownloadedDemons = list.stream().filter(GDLevel::isDemon).collect(Collectors.toList());
                break;
            }
            case MOST_LIKED: {
                mostLikedLevels = list.subList(0, LIST_SIZE);
                mostLikedDemons = list.stream().filter(GDLevel::isDemon).collect(Collectors.toList());
                break;
            }
        }
    }

    private static String smallWikiString(GDLevel level) {
        String data = Constants.LEVELS_WITH_DIFFERENT_NAME.containsKey(level.name())
                ? Constants.LEVELS_WITH_DIFFERENT_NAME.get(level.name()).trim() : level.name().trim();
        data = data.substring(0, 1).toUpperCase() + data.substring(1);
        return data;
    }

    /**
     * Generate string for pages
     *
     * @param count number of row in table
     * @return string with information about level in MediaWiki table row format
     */
    private static String wikiString(GDLevel level, int count) {
        String levelName = Constants.LEVELS_WITH_DIFFERENT_NAME.containsKey(level.name())
                ? Constants.LEVELS_WITH_DIFFERENT_NAME.get(level.name()).trim() : level.name().trim();
        String prefix = getPrefix(level);
        String difficultyOutput = getDifficultyOutput(level);
        String creatorName = getCreatorName(level, levelName);
        return "! " + (count + 1) + "\n" + "| [[" + levelName + "]]\n| "
                + creatorName + "\n| <center>{{" + prefix + difficultyOutput
                + "}}</center>\n| " + numberFormatter.format(level.downloads())
                + "\n| " + numberFormatter.format(level.likes());
    }

    private static String basicString(GDLevel level) {
        String creatorName = level.creatorName().isPresent() ? level.creatorName().get() : "-";
        return "\"" + level.name() + "\" by " + creatorName + " (" + level.id()
                + ") — likes: " + level.likes() + ", downloads: " + level.downloads();
    }

    private static String getPrefix(GDLevel level) {
        if (level.isEpic())
            return "Эпический ";
        if (level.featuredScore() > 0)
            return "Featured ";
        return "";
    }

    private static String getDifficultyOutput(GDLevel level) {
        return level.isDemon()
                ? Constants.demonDifficultyStringMap.get(level.demonDifficulty())
                : Constants.difficultyStringMap.get(level.difficulty());
    }

    private static String getCreatorName(GDLevel level, String levelName) {
        String creator = level.name();
        String creatorOutput;
        if (Constants.allowedCreatorsNames.contains(creator)) {
            creatorOutput = "[[" + creator + "]]";
        } else if (Constants.allowedCreatorsNamesWithReplacement.containsKey(creator)) {
            creatorOutput = "[[" + Constants.allowedCreatorsNamesWithReplacement.get(creator) + "]]";
        } else if (Constants.specialCreatorsNamesForLevels.containsKey(levelName)) {
            creatorOutput = "[[" + Constants.specialCreatorsNamesForLevels.get(levelName) + "]]";
        } else {
            creatorOutput = creator == null ? "—" : creator;
        }
        return creatorOutput;
    }
}
