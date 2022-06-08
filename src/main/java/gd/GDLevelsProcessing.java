package gd;

import jdash.client.GDClient;
import jdash.common.LevelBrowseMode;
import jdash.common.entity.GDLevel;

import java.util.ArrayList;
import java.util.List;

public class GDLevelsProcessing {

    private static final int LIST_SIZE = 50;
    private static final int GD_PAGE_SIZE = 10;
    private static final GDClient client = GDClient.create();

    public static List<GDLevel> fillListWithLevels(LevelBrowseMode levelBrowseMode) {
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
}
