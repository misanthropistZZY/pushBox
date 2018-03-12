package pushBox.algorithm;

import pushBox.beans.GameMap;
import pushBox.beans.boxPath.Stage;
import pushBox.beans.caches.StageCach;
import pushBox.beans.boxPath.StageQueue;
import pushBox.beans.common.Definition;
import pushBox.beans.pojo.Box;
import pushBox.beans.pojo.People;
import pushBox.beans.pojo.Position;
import pushBox.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 16:28
 * @Description:
 */
public class BoxAstarPath extends Algorithm {
    @Override
    public String getPath(GameMap gameMap) {
        return null;
    }

    public static Stage peopleMoveInfo() {
        //System.out.println("开始寻求路径！");
        Stage targetStage = null;
        Stage startStage = GameMap.startStage;
        StageQueue.stageQueue.add(startStage);
        while (StageQueue.stageQueue.peek() != null) {
            if (targetStage != null) {
                break;
            }
            Stage currenStage = StageQueue.stageQueue.peek();
            //System.out.println("未归位的箱子数：" + currenStage.getUnPlacedBoxCount());
            //System.out.println("场景id：" + currenStage.getStageId());
            if (currenStage.getUnPlacedBoxCount() == 0) {//所有箱子已归位
                //System.out.println("找到最佳路径！");
                return currenStage;
            }
            handleChildStage(currenStage, Util.generateTempMap(currenStage));
            StageQueue.stageQueue.remove();//移除顶部元素
        }
        System.out.println("无解！");
        return targetStage;
    }

    private static void handleChildStage(Stage currenStage, int[][] tempMap) {
        int index = 0;
        List<Stage> childStageList = new ArrayList<>();
        for (Box box : currenStage.getBoxList()) {
            //List<Box> currenStageBoxList = (List<Box>) currenStage.getBoxList();
            //for (int i = 0; i < currenStageBoxList.size(); i++) {
            //Box box = currenStageBoxList.get(i);
            Stage leftChild = moveBox(Definition.LEFT, currenStage, box.X, box.Y - 1, tempMap, index, box.X, box.Y + 1);//左移箱子
            Stage upChild = moveBox(Definition.UP, currenStage, box.X - 1, box.Y, tempMap, index, box.X + 1, box.Y);//上移
            Stage rightChild = moveBox(Definition.RIGHT, currenStage, box.X, box.Y + 1, tempMap, index, box.X, box.Y - 1);//右移
            Stage downChild = moveBox(Definition.DOWN, currenStage, box.X + 1, box.Y, tempMap, index, box.X - 1, box.Y);//下移
            index++;
            if (leftChild != null) {
                childStageList.add(leftChild);
            }
            if (upChild != null) {
                childStageList.add(upChild);
            }
            if (rightChild != null) {
                childStageList.add(rightChild);
            }
            if (downChild != null) {
                childStageList.add(downChild);
            }
        }
        Collections.sort(childStageList, new Comparator<Stage>() {
            @Override
            public int compare(Stage o1, Stage o2) {
                return o1.getStepCount() > o2.getStepCount() ? 1 : -1;//升序排列，确保步数小的场景先被解析
            }
        });
        for (Stage stage : childStageList) {
            StageCach.stageCache.put(stage.getStageId(), StageQueue.stageCount);
            StageQueue.putToQueue(stage);
        }
    }

    /**
     * 箱子移动
     *
     * @param move        移动方向
     * @param currenStage 当前场景
     * @param x           箱子移动后的X
     * @param y           箱子移动后的Y
     * @param tempMap     临时地图
     * @param index       箱子索引
     * @param endX        人到达推点的X
     * @param endY        推点Y
     * @return
     */
    private static Stage moveBox(String move, Stage currenStage, int x, int y, int[][] tempMap, int index, int endX, int endY) {
        System.out.println(move);
        Stage childStage = null;
        Position endNode = new Position();
        endNode.setX(endX);
        endNode.setY(endY);
        //String peopleMovedPath = PeopleAstarPath.peopleMoveInfo(endNode, currenStage, tempMap);
        if (canBoxPut(x, y, tempMap)) {//箱子可以放置
            List<Box> currentBoxList = (List<Box>) currenStage.getBoxList();
            List<Box> childBoxList = new ArrayList<>(currentBoxList.size());
            for (int i = 0; i < currentBoxList.size(); i++) {
                Box currenBox = currentBoxList.get(i);
                if (i == index) {
                    Box movedBox = new Box(currenBox.getName(), x, y);
                    childBoxList.add(movedBox);
                } else {
                    Box staticBox = new Box(currenBox.getName(), currenBox.X, currenBox.Y);
                    childBoxList.add(staticBox);
                }
            }
            childStage = new Stage(null, childBoxList);
            if (!childStage.judgeStageIsOver() && !isStageExist(childStage)) { //子场景不陷入死局并且不存在缓存中
                String peopleMovedPath = PeopleAstarPath.peopleMoveInfo(endNode, currenStage, tempMap);
                if (peopleMovedPath != null) {//人能够到达推点
                    System.out.println("人能够到达推点");
                    People people = null;
                    switch (move) {
                        case Definition.LEFT:
                            people = new People(endX, endY - 1);
                            peopleMovedPath += Definition.LEFT;
                            break;
                        case Definition.UP:
                            people = new People(endX - 1, endY);
                            peopleMovedPath += Definition.UP;
                            break;
                        case Definition.RIGHT:
                            people = new People(endX, endY + 1);
                            peopleMovedPath += Definition.RIGHT;
                            break;
                        case Definition.DOWN:
                            people = new People(endX + 1, endY);
                            peopleMovedPath += Definition.DOWN;
                            break;
                        default:
                            break;
                    }
                    childStage.setPeople(people);
                    childStage.setOver(false);
                    childStage.setStepDetail(currenStage.getStepDetail() + peopleMovedPath);
                    childStage.caculateUnPlaceBoxCount(GameMap.boxPlaceList);
                    childStage.setStepCount(currenStage.getStepCount() + peopleMovedPath.length());
                    return childStage;
                    //StageCach.stageCache.put(childStage.getStageId(), StageQueue.stageCount);
                    //System.out.println("将场景id=" + childStage.getStageId() + " 放入队列！");
                    //StageQueue.putToQueue(childStage);
                } else {
                    System.out.println("人不能到达！");
                }
            } else {
                if (childStage.isOver()) {
                    System.out.println("子场景无解！");
                }
                if (isStageExist(childStage)) {
                    System.out.println("子场景已存在！");
                }
            }
        } else {
            System.out.println("无法往该方向推！");
        }
        return null;
    }

    private static boolean canBoxPut(int x, int y, int[][] tempMap) {//判断该点是否能放置箱子
        if (tempMap[x][y] < Definition.BLOCK_PLACE || tempMap[x][y] == Definition.PEOPLE_PLACE) {
            return true;
        }
        return false;
    }

    private static boolean isStageExist(Stage stage) {//判断场景是否已存在
        return StageCach.isExist(stage.getStageId());
    }

    public static void main(String args[]) {
        //一个箱子
        //int[][] testMap = {{1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 1}, {1, 0, 0, 2, 0, 1}, {1, 0, 1, 0, 0, 1}, {1, 0, 1, 1, 0, 1}, {1, 3, 0, 0, -1, 1}, {1, 1, 1, 1, 1, 1}};
        //两个箱子
        int[][] testMap = {{1, 1, 1, 1, 1, 1}, {1, 0, 2, 0, -1, 1}, {1, 0, 1, 0, 0, 1}, {1, 0, 0, 1, 0, 1}, {1, 3, 2, 0, 0, 1}, {1, 0, 0, -1, 0, 1}, {1, 1, 1, 1, 1, 1}};
        //四个箱子
        //int[][] testMap = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1}, {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1}, {1, 0, 0, 2, 0, 1, 1, 0, 2, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 1, 1, -1, 0, 0, 0, 0, 0, 1},
        //        {1, 0, 2, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1}, {1, 0, 0, 0, 0, 1, 1, 1, 0, -1, 1, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1}, {1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

        //int[][] testMap = {{1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, -1, -1, 1, 1, 1, 1}, {1, 1, 0, 0, -1, 0, 0, 1}, {1, 0, 0, 0, 1, 2, 1, 1}, {1, 0, 2, 0, 0, -1, 1, 1}, {1, 0, 0, 2, 2, 0, 0, 1}, {1, 1, 0, 0, 3, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1}};

        //int[][] testMap = {{1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, -1, -1, 1, 1, 1, 1}, {1, 1, 0, 0, -1, 0, 0, 1}, {1, 0, 0, 0, 1, 2, 1, 1}, {1, 0, 2, 0, 0, 0, 1, 1}, {1, 0, 0, 2, 0, 0, 0, 1}, {1, 1, 0, 0, 3, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1}};
        //int[][] testMap = {{1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, -1, -1, 1, 1, 1, 1}, {1, 1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 1, 2, 0, 1}, {1, 0, 2, 1, 0, -1, 0, 1}, {1, 0, 0, 2, 2, 0, 0, 1}, {1, -1, 0, 0, 3, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1}};
        GameMap.initStage(testMap);
        Stage targetStage = peopleMoveInfo();
        System.out.println();
        System.out.println();
        System.out.println("查找结果：");
        if (targetStage == null) {
            System.out.println("无解！");
        } else {
            System.out.println("找到最优路径：" + targetStage.getStepDetail());
            System.out.println("共计步数：" + targetStage.getStepCount());
            System.out.println("共计搜寻数：" + StageQueue.stageCount);
        }

    }
}
