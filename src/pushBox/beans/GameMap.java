package pushBox.beans;

import pushBox.beans.boxPath.Stage;
import pushBox.beans.common.Definition;
import pushBox.beans.errorInfo.ErrorCodeEnum;
import pushBox.beans.errorInfo.ErrorCodeException;
import pushBox.beans.pojo.Box;
import pushBox.beans.pojo.BoxPlace;
import pushBox.beans.pojo.People;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 16:09
 * @Description: 地图类
 */
public class GameMap {
    public static int length; //地图长
    public static int height; //地图宽
    public static List<BoxPlace> boxPlaceList = new ArrayList<>(); //箱子需要放置位置
    public static Stage startStage; //初始场景

    private int[][] pointMap; //地图每个点信息,-1：箱子放置点；0:空地；1：路障点；2：箱子所在点；3：人所在点

    public static int[][] emptyMap;

    public static Stage initStage(int[][] pointMap) {
        boxPlaceList = new ArrayList<>();
        People people = null;
        List<Box> boxList = new ArrayList<>();

        height = pointMap.length;
        length = pointMap[0].length;

        emptyMap = new int[height][length];
        //pointMap = pointMap;

        int index = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (!isCorrectInfo(pointMap[i][j])) {
                    //System.out.println(pointMap[i][j]);
                    throw new ErrorCodeException(ErrorCodeEnum.MAP_EXIST_ERROR);
                } else {
                    boolean flag = false;//是否是障碍点
                    if (pointMap[i][j] == Definition.BOX_REQUIRE_PLACE) {
                        BoxPlace boxPlace = new BoxPlace(i, j);
                        boxPlaceList.add(boxPlace);
                    }
                    if (pointMap[i][j] == Definition.PEOPLE_PLACE) {
                        people = new People(i, j);
                    }
                    if (pointMap[i][j] == Definition.BOX_PLACE) {
                        Box box = new Box("box_" + index++, i, j);
                        boxList.add(box);
                    }
                    if (pointMap[i][j] == Definition.BLOCK_PLACE) {
                        flag = true;
                    }
                    if (flag) {
                        emptyMap[i][j] = Definition.BLOCK_PLACE;
                    } else {
                        if (pointMap[i][j] == Definition.BOX_REQUIRE_PLACE) {
                            emptyMap[i][j] = Definition.BOX_REQUIRE_PLACE;
                        } else {
                            emptyMap[i][j] = Definition.EMPTY_PLACE;
                        }
                    }

                }
            }
        }
        startStage = new Stage(people, boxList);
        startStage.setStepDetail("");
        startStage.setStepCount(0);
        startStage.caculateUnPlaceBoxCount(boxPlaceList);
        startStage.judgeStageIsOver();
        return startStage;
    }

    private static boolean isCorrectInfo(int i) {
        if (i < -1 || i > 3) {
            return false;
        }
        return true;
    }
}
