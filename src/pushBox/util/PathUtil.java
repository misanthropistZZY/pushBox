package pushBox.util;

import pushBox.algorithm.BoxAstarPath;
import pushBox.beans.GameMap;
import pushBox.beans.boxPath.Stage;
import pushBox.beans.common.Definition;
import pushBox.beans.pojo.People;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-30 12:39
 * @Description:
 */
public class PathUtil {
    public static int[][] formulateMap(int[][] originalMap, int[] boxList, People people) {
        int height = originalMap.length;
        int length = originalMap[0].length;
        int[][] map = new int[height][length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                map[i][j] = Util.changeValue(originalMap[i][j]);
            }
        }
        for (int i = 0; i < boxList.length; i++) {
            int boxX = boxList[i];
            int boxY = boxList[++i];
            map[boxX][boxY] = Definition.BOX_PLACE;
        }
        map[people.X][people.Y] = Definition.PEOPLE_PLACE;
        return map;
    }

    public static void initMap(int[][] realMap) {
        GameMap.initStage(realMap);
    }

    public static Stage caculatePath() {
        return BoxAstarPath.peopleMoveInfo();
    }
}
