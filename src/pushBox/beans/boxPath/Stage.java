package pushBox.beans.boxPath;

import pushBox.beans.GameMap;
import pushBox.beans.common.Definition;
import pushBox.beans.pojo.Box;
import pushBox.beans.pojo.BoxPlace;
import pushBox.beans.pojo.People;
import pushBox.util.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 18:57
 * @Description: 场景类，用于记录当前地图中人与箱子的位置
 */
public class Stage {
    private String stageId; //场景唯一编号
    private int stepCount; //步数
    private boolean isOver; //是否结束
    private int unPlacedBoxCount; //已经安放好的箱子数
    private String stepDetail; // "asdw":
    private People people; //人物位置信息
    private List<Box> boxList; //箱子位子信息
    private int expectStep; //期望完成步数

    public Stage(People people, List<Box> boxList) {
        this.people = people;
        this.boxList = boxList;
        this.stageId = "";
        generateHashStringId();
    }

    //判断是否陷入死局
    public boolean judgeStageIsOver() {//判断当前场景是否结束游戏了
        if (caculateUnPlaceBoxCount(GameMap.boxPlaceList) == 0) {//如果箱子已全部归位，加入队列
            System.out.println("箱子已全部归位！");
            return false;
        }
        int[][] tempMap = Util.tempMapClone();
        boolean left_up_over = false;
        boolean up_right_over = false;
        boolean right_down_over = false;
        boolean down_left_over = false;
        for (Box box : boxList) {
            tempMap[box.X][box.Y] = Definition.BOX_PLACE;
        }
        System.out.println("stage:");
        for (int i = 0; i < GameMap.height; i++) {
            for (int j = 0; j < GameMap.length; j++) {
                System.out.print(tempMap[i][j] + "   ");
            }
            System.out.println();
        }
        for (Box box : boxList) {
            if ((tempMap[box.X][box.Y - 1] == Definition.BLOCK_PLACE) && (tempMap[box.X - 1][box.Y] == Definition.BLOCK_PLACE) && (tempMap[box.X][box.Y] != Definition.BOX_REQUIRE_PLACE)) {
                left_up_over = true;
                System.out.println("left_up_over!");
            }
            if ((tempMap[box.X - 1][box.Y] == Definition.BLOCK_PLACE) && (tempMap[box.X][box.Y + 1] == Definition.BLOCK_PLACE) && (tempMap[box.X][box.Y] != Definition.BOX_REQUIRE_PLACE)) {
                up_right_over = true;
                System.out.println("up_right_over!");
            }
            if ((tempMap[box.X][box.Y + 1]) == Definition.BLOCK_PLACE && (tempMap[box.X + 1][box.Y] == Definition.BLOCK_PLACE) && (tempMap[box.X][box.Y] != Definition.BOX_REQUIRE_PLACE)) {
                right_down_over = true;
                System.out.println("right_down_over!");
                // System.out.println("box:" + "  " + box.X + "   " + box.Y);
                //System.out.println("right:" + tempMap[box.X][box.Y + 1] + "   down:" + tempMap[box.X + 1][box.Y]);
            }
            if ((tempMap[box.X + 1][box.Y] == Definition.BLOCK_PLACE) && (tempMap[box.X][box.Y - 1] == Definition.BLOCK_PLACE) && (tempMap[box.X][box.Y] != Definition.BOX_REQUIRE_PLACE)) {
                down_left_over = true;
                System.out.println("down_left_over");
            }
            if (left_up_over || up_right_over || right_down_over || down_left_over) {
                this.isOver = true;
                return true;
            }
        }
        this.isOver = false;
        return false;
    }

    //生成场景指纹
    public void generateHashStringId() {
        Collections.sort(boxList, new Comparator<Box>() {//将list中的对象按降序排列，用于生成指纹
            @Override
            public int compare(Box o1, Box o2) {
                if (o1.X > o2.X) {
                    return 1;
                }
                if (o1.X == o2.X) {
                    if (o1.Y > o2.Y) {
                        return 1;
                    }
                }
                return -1;
            }
        });
        for (Box box : this.boxList) {
            this.stageId += box.X + Definition.UNDER_LINE + box.Y + Definition.SEPERATE_REGEX;
        }
    }

    public int caculateUnPlaceBoxCount(List<BoxPlace> boxPlaceList) {
        int count = 0;//归位箱子数
        for (Box box : boxList) {
            for (BoxPlace boxPlace : boxPlaceList) {
                //System.out.println("boxPlace X:" + boxPlace.X + "   Y:" + boxPlace.Y);
                if (box.X == boxPlace.X && box.Y == boxPlace.Y) {
                    count++;
                }
            }
        }
        this.unPlacedBoxCount = boxPlaceList.size() - count;
        return unPlacedBoxCount;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public int getUnPlacedBoxCount() {
        return unPlacedBoxCount;
    }

    public void setUnPlacedBoxCount(int unPlacedBoxCount) {
        this.unPlacedBoxCount = unPlacedBoxCount;
    }

    public String getStepDetail() {
        return stepDetail;
    }

    public void setStepDetail(String stepDetail) {
        this.stepDetail = stepDetail;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }

    public int getExpectStep() {
        return expectStep;
    }

    public void setExpectStep(int expectStep) {
        this.expectStep = expectStep;
    }
}
