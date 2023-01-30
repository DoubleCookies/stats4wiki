package gd;

import jdash.common.Difficulty;
import jdash.common.LevelBrowseMode;
import jdash.common.entity.GDLevel;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import static gd.Constants.*;

/**
 * Class for generating level lists
 */
public class ResponseGenerator {
    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance();

    private static List<GDLevel> mostDownloadedLevels;
    private static List<GDLevel> mostDownloadedDemons;

    private static List<GDLevel> mostLikedLevels;
    private static List<GDLevel> mostLikedDemons;

    static void getLevelsForLists() {
        getListForType(LevelBrowseMode.MOST_DOWNLOADED);
        getListForType(LevelBrowseMode.MOST_LIKED);
    }

    private static void getListForType(LevelBrowseMode levelBrowseMode) {
        System.out.println("Generating list for this type: " + levelBrowseMode.name());
        List<GDLevel> list = GDLevelsProcessing.fillListWithLevels(levelBrowseMode);
        fillTypedList(levelBrowseMode, list);
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

    private static String smallWikiString(GDLevel level) {
        String data = LEVELS_WITH_DIFFERENT_NAME.containsKey(level.name())
                ? LEVELS_WITH_DIFFERENT_NAME.get(level.name()).trim() : level.name().trim();
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
        String levelName = LEVELS_WITH_DIFFERENT_NAME.containsKey(level.name())
                ? LEVELS_WITH_DIFFERENT_NAME.get(level.name()).trim() : level.name().trim();
        String prefix = getPrefix(level);
        String difficultyOutput = getDifficultyOutput(level);
        String creatorName = getCreatorName(level, levelName);
        return "! " + (count + 1) + "\n" + "| [[" + levelName + "]]\n| "
                + creatorName + "\n| <center>{{" + prefix + difficultyOutput
                + "}}</center>\n| " + numberFormatter.format(level.downloads())
                + "\n| " + numberFormatter.format(level.likes());
    }

    private static String getPrefix(GDLevel level) {
        if (level.isEpic())
            return "Эпический ";
        if (level.featuredScore() > 0)
            return "Featured ";
        return "";
    }

    private static String getDifficultyOutput(GDLevel level) {
        if (level.isAuto()) {
            return difficultyStringMap.get(Difficulty.AUTO);
        } else {
            return level.isDemon()
                    ? demonDifficultyStringMap.get(level.demonDifficulty())
                    : difficultyStringMap.get(level.difficulty());
        }
    }

    private static String getCreatorName(GDLevel level, String levelName) {
        String creator = level.name();
        String creatorOutput;
        if (allowedCreatorsNames.contains(creator)) {
            creatorOutput = "[[" + creator + "]]";
        } else if (allowedCreatorsNamesWithReplacement.containsKey(creator)) {
            creatorOutput = "[[" + allowedCreatorsNamesWithReplacement.get(creator) + "]]";
        } else if (specialCreatorsNamesForLevels.containsKey(levelName)) {
            creatorOutput = "[[" + specialCreatorsNamesForLevels.get(levelName) + "]]";
        } else {
            creatorOutput = creator == null ? "—" : creator;
        }
        return creatorOutput;
    }
}
