package gd.model;

import gd.Constants;
import gd.enums.DemonDifficulty;
import gd.enums.Difficulty;

import java.text.NumberFormat;

/**
 * Represents one level in Geometry Dash. All statistics and attributes of a Geometry
 * Dash level are defined here (name, creator, difficulty, stars, length, etc)
 *
 * @author Alex1304
 */
public class GDLevel {

    private static final NumberFormat numberFormatter = NumberFormat.getNumberInstance();
    /**
     * The ID of the level
     */
    private final long id;

    /**
     * The name of the level
     */
    private final String name;

    /**
     * The name of the creator who created this level
     */
    private final String creator;

    /**
     * The level difficulty
     */
    private final Difficulty difficulty;

    /**
     * If it's a Demon, the type of Demon difficulty
     */
    private final DemonDifficulty demonDifficulty;

    /**
     * The featured score of the level, or a value &lt;= 0 if not featured
     */
    private final int featuredScore;

    /**
     * Whether the level is marked as Epic
     */
    private final boolean epic;

    /**
     * Amount of downloads for the level
     */
    private final long downloads;

    /**
     * Amount of likes for the level
     */
    private final long likes;

    /**
     * Constructs an instance of gd.model.GDLevel by providing all of its attributes at
     * once.
     *
     * @param id              - the ID of the level
     * @param name            - the name of the level
     * @param creator         - the name of the user who created this level
     * @param difficulty      - the level difficulty
     * @param demonDifficulty - if it's a Demon, the type of Demon difficulty
     * @param featuredScore   - the featured score of the level, or a value &lt;= 0 if not
     *                        featured
     * @param epic            - whether the level is marked as Epic
     * @param downloads       - amount of downloads for the level
     * @param likes           - amount of likes for the level
     */
    public GDLevel(long id, String name, String creator, Difficulty difficulty,
                   DemonDifficulty demonDifficulty, int featuredScore, boolean epic, long downloads,
                   long likes) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.difficulty = difficulty;
        this.demonDifficulty = demonDifficulty;
        this.featuredScore = featuredScore;
        this.epic = epic;
        this.downloads = downloads;
        this.likes = likes;
    }

    /**
     * Gets the level difficulty
     *
     * @return gd.enums.Difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Generate string for templates
     *
     * @return string with only level name
     */
    public String smallWikiString() {
        String data = Constants.LEVELS_WITH_DIFFERENT_NAME.containsKey(name) ? Constants.LEVELS_WITH_DIFFERENT_NAME.get(name).trim() : name.trim();
        data = data.substring(0, 1).toUpperCase() + data.substring(1);
        return data;
    }

    /**
     * Generate string for pages
     *
     * @param count number of row in table
     * @return string with information about level in MediaWiki table row format
     */
    public String wikiString(int count) {
        String levelName = Constants.LEVELS_WITH_DIFFERENT_NAME.containsKey(name) ? Constants.LEVELS_WITH_DIFFERENT_NAME.get(name).trim() : name.trim();
        String prefix = getPrefix();
        String difficultyOutput = getDifficultyOutput();
        String creatorName = getCreatorName(levelName);
        return "! " + (count + 1) + "\n" + "| [[" + levelName + "]]\n| " + creatorName + "\n| <center>{{" + prefix + difficultyOutput + "}}</center>\n| " + numberFormatter.format(downloads) + "\n| " + numberFormatter.format(likes);
    }

    private String getPrefix() {
        if (epic) {
            return "Эпический ";
        } else if (featuredScore != 0 && !name.equals("Sonic Wave")) {
            return "Featured ";
        }
        return "";
    }

    private String getDifficultyOutput() {
        return difficulty == Difficulty.DEMON
                ? Constants.demonDifficultyStringMap.get(demonDifficulty) : Constants.difficultyStringMap.get(difficulty);
    }

    private String getCreatorName(String levelName) {
        String creatorOutput;
        if (Constants.allowedCreatorsNames.contains(creator)) {
            creatorOutput = "[[" + creator + "]]";
            if (creator.equals("Riot"))
                creatorOutput += " и др.";
        } else if (Constants.allowedCreatorsNamesWithReplacement.containsKey(creator)) {
            creatorOutput = "[[" + Constants.allowedCreatorsNamesWithReplacement.get(creator) + "]]";
        } else {
            creatorOutput = creator == null ? "—" : creator;
        }
        if (levelName.equals("Beautiful Chaos"))
            creatorOutput = "Darnoc2";
        if (levelName.equals("Level Easy"))
            creatorOutput = "[[Cody]]";
        return creatorOutput;
    }

    @Override
    public String toString() {
        return "\"" + name + "\" by " + creator + " (" + id + ") — likes: " + likes + ", downloads: " + downloads;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    /**
     * Two levels are considered as equal if and only if they have both
     * the same ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GDLevel other = (GDLevel) obj;
        return id == other.id;
    }

}
