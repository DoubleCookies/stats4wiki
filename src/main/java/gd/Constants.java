package gd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    /* Song data */
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TITLE = 2;
    public static final int INDEX_SONG_AUTHOR = 4;
    public static final int INDEX_SONG_SIZE = 5;
    public static final int INDEX_SONG_URL = 10;

    /* Level data */
    public static final int INDEX_LEVEL_AUDIO_TRACK = 12;
    public static final int INDEX_LEVEL_SONG_ID = 35;

    /* Списки для замены */
    public static final Map<String, String> levelsExceptions = initLevelExceptions();

    private static Map<String, String> initLevelExceptions() {
        Map<String, String> map = new HashMap<>();
        map.put("Dreamland", "Dreamland (Scanbrux)");
        map.put("auto play area", "Auto Play Area");
        map.put("flappy hexagon", "Flappy Hexagon");
        map.put("THE LIGHTNING ROAD", "The Lightning Road");
        map.put("stereo madness v2", "Stereo Madness v2 (Neptune)");
        map.put("lucid dream", "Lucid Dream");
        map.put("auto cycles", "Auto Cycles");
        map.put("DARKNESS", "Darkness");
        map.put("simple cloud", "Simple Cloud");
        map.put("ThE WorLd", "The World");
        map.put("Promises", "Promises (Adiale)");
        map.put("endless", "Endless (FlappySheepy)");
        map.put("moon adventure", "Moon Adventure");
        map.put("Theory of SkriLLex", "Theory of Skrillex");
        map.put("Jawbreaker", "Jawbreaker (ZenthicAlpha)");

        return map;
    }

    public static final List<String> allowedCreatorsNames = initNames();

    private static List<String> initNames() {
        List<String> names = new ArrayList<>();
        names.add("JerkRat");
        names.add("Jax");
        names.add("Scanbrux");
        names.add("Adiale");
        names.add("Creator Cloud");
        names.add("ZenthicAlpha");
        names.add("Berkoo");
        names.add("Riot");
        names.add("Mixroid");
        names.add("TrueNature");
        names.add("Zobros");
        names.add("MrCheeseTigrr");
        names.add("SirHadoken");
        names.add("Mitchell");
        names.add("FunnyGame");
        names.add("Serponge");
        names.add("TamaN");
        names.add("Jeyzor");
        names.add("Glittershroom43");
        names.add("Lyod");
        names.add("Rob Buck");
        names.add("TriAxis");
        names.add("Ggb0y");
        names.add("Caustic");
        names.add("Experience D");
        names.add("Jerry Bronze V");
        names.add("Rek3dge");
        names.add("WOOGI1411");
        names.add("TheRealSalad");
        names.add("Minesap");
        names.add("ChaSe");
        return names;
    }

    public static final Map<String, String> PopularLevelsCreators = initPopularLevelCreators();

    private static Map<String, String> initPopularLevelCreators() {
        Map<String, String> map = new HashMap<>();
        map.put("DiMaViKuLov26", "DimaVikulov26");
        map.put("DORABAE", "Dorabae");
        map.put("danolex", "Danolex");
        map.put("alkali", "Alkali");
        map.put("TheRealDarnoc", "Darnoc");
        map.put("IIINePtunEIII", "Neptune");
        map.put("timeless real", "Timeless real");
        map.put("noobas", "Noobas");
        map.put("haoN", "HaoN");
        map.put("TrusTa", "TrusTa (игрок)|Trusta");
        map.put("IRabb2tI", "Rabb2t");
        map.put("lSunix", "Cyclic");
        return map;
    }



}
