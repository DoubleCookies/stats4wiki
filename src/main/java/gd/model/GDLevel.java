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

    @Override
    public String toString() {
        return "\"" + name + "\" by " + creator + " (" + id + ") — likes: " + likes + ", downloads: " + downloads;
    }

    /**
     * Generate string for small lists
     *
     * @return string with only level name
     */
    public String smallWikiString() {
        String data = Constants.levelsExceptions.containsKey(name) ? Constants.levelsExceptions.get(name).trim() : name.trim();
        data = data.trim();
        data = data.substring(0, 1).toUpperCase() + data.substring(1);
        return data;
    }

    /**
     * Generate string for big lists
     *
     * @param count number of row in table
     * @return string with information about level in MediaWiki table row format
     */
    public String wikiString(int count) {
        String levelName = Constants.levelsExceptions.containsKey(name) ? Constants.levelsExceptions.get(name).trim() : name.trim();
        String diffTemplate = "";
        if (epic) {
            diffTemplate += "Эпический ";
        } else {
            if (featuredScore != 0 && !name.equals("Sonic Wave"))
                diffTemplate += "Featured ";
        }
        if (difficulty == Difficulty.DEMON) {
            switch (demonDifficulty) {
                case EASY: {
                    diffTemplate += "лёгкий демон";
                    break;
                }
                case MEDIUM: {
                    diffTemplate += "средний демон";
                    break;
                }
                case HARD: {
                    diffTemplate += "демон";
                    break;
                }
                case INSANE: {
                    diffTemplate += "безумный демон";
                    break;
                }
                case EXTREME: {
                    diffTemplate += "экстремальный демон";
                    break;
                }
            }
        } else {
            switch (difficulty) {
                case EASY: {
                    diffTemplate += "лёгкий";
                    break;
                }
                case NORMAL: {
                    diffTemplate += "нормальный";
                    break;
                }
                case HARD: {
                    diffTemplate += "сложный";
                    break;
                }
                case HARDER: {
                    diffTemplate += "очень сложный";
                    break;
                }
                case INSANE: {
                    diffTemplate += "безумный";
                    break;
                }
                case AUTO: {
                    diffTemplate += "авто";
                    break;
                }
            }
        }
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        String creatorString = "";
        if (Constants.allowedCreatorsNames.contains(creator)) {
            creatorString = "[[" + creator + "]]";
            if (creator.equals("Riot"))
                creatorString += " и др.";
        } else if (Constants.allowedCreatorsNamesWithReplacement.containsKey(creator)) {
            creatorString = "[[" + Constants.allowedCreatorsNamesWithReplacement.get(creator) + "]]";
        } else {
            creatorString = creator == null ? "—" : creator;
        }
        if (levelName.equals("Beautiful Chaos"))
            creatorString = "Darnoc2";
        if (levelName.equals("Level Easy"))
            creatorString = "[[Cody]]";
        return "! " + (count + 1) + "\n" + "| [[" + levelName + "]]\n| " + creatorString + "\n| <center>{{" + diffTemplate + "}}</center>\n| " + numberFormatter.format(downloads) + "\n| " + numberFormatter.format(likes);
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
