package pushBox.algorithm;

import pushBox.beans.GameMap;
import pushBox.beans.boxPath.Stage;
import pushBox.beans.common.Definition;
import pushBox.beans.caches.NodeCache;
import pushBox.beans.pojo.Box;
import pushBox.beans.pojo.Node;
import pushBox.beans.pojo.People;
import pushBox.beans.pojo.Position;

import java.util.List;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-25 14:31
 * @Description: 人物移动A*算法
 */
public class PeopleAstarPath {
    //当前人的位置是否可以到指定位置，返回为null则到不了
    //public static String peopleMoveInfo(Position endNode, Stage stage, GameMap gameMap) {
    public static String peopleMoveInfo(Position endNode, Stage stage, int[][] tempMap) {
        //int[][] tempMap = gameMap.getPointMap();
        List<Box> stageBoxList = (List<Box>) stage.getBoxList();
//        for (int i = 0; i < stageBoxList.size(); i++) {
//            tempMap[stageBoxList.get(i).X][stageBoxList.get(i).Y] = Definition.BOX_PLACE;
//        }
        for (Box box : stage.getBoxList()) {
            tempMap[box.getX()][box.getY()] = Definition.BOX_PLACE;
        }
        People people = stage.getPeople();
        Node startNode = new Node(people.X, people.Y);
        startNode.setG(0);
        startNode.setMoveInfo("");
        startNode.caculateF(endNode);
        return findPath(startNode, endNode, tempMap);
    }

    private static String findPath(Node startNode, Position endNode, int[][] tempMap) {
        NodeCache.cleanNode();//清空上次求解的缓存
        NodeCache.openNodeMap.put(startNode.getNodeId(), startNode);
        int tryCount = 0;
        while (!NodeCache.openNodeMap.isEmpty()) {
            tryCount++;
            Node currenMinFNode = NodeCache.getMinFNode();//获取估价值最小的节点
            NodeCache.openNodeMap.remove(currenMinFNode.getNodeId());//节点移除
            NodeCache.closeNodeMap.put(currenMinFNode.getNodeId(), currenMinFNode);
            if (currenMinFNode.X == endNode.X && currenMinFNode.Y == endNode.Y) {
                return currenMinFNode.getMoveInfo();
            } else {
                handleChildNode(Definition.LEFT, currenMinFNode, currenMinFNode.X, currenMinFNode.Y - 1, tempMap, endNode);//左移
                handleChildNode(Definition.UP, currenMinFNode, currenMinFNode.X - 1, currenMinFNode.Y, tempMap, endNode);//上移
                handleChildNode(Definition.RIGHT, currenMinFNode, currenMinFNode.X, currenMinFNode.Y + 1, tempMap, endNode);//右移
                handleChildNode(Definition.DOWN, currenMinFNode, currenMinFNode.X + 1, currenMinFNode.Y, tempMap, endNode);//下移
            }
        }
        return null;
    }

    private static void handleChildNode(String move, Node currenMinFNode, int x, int y, int[][] tempMap, Position endNode) {
        Node childNode = null;
        if (canPass(x, y, tempMap)) {//能到达
            childNode = new Node(x, y);
            if (!isExist(childNode)) {//不存在缓存中
                switch (move) {
                    case Definition.LEFT:
                        childNode.setMoveInfo(currenMinFNode.getMoveInfo() + Definition.LEFT);
                        break;
                    case Definition.UP:
                        childNode.setMoveInfo(currenMinFNode.getMoveInfo() + Definition.UP);
                        break;
                    case Definition.RIGHT:
                        childNode.setMoveInfo(currenMinFNode.getMoveInfo() + Definition.RIGHT);
                        break;
                    case Definition.DOWN:
                        childNode.setMoveInfo(currenMinFNode.getMoveInfo() + Definition.DOWN);
                        break;
                    default:
                        break;
                }
                childNode.setG(currenMinFNode.getG() + 1);
                childNode.caculateF(endNode);
                //childNode.setFatherNode(currenMinFNode);
                NodeCache.openNodeMap.put(childNode.getNodeId(), childNode);
            }
        }
    }

    private static boolean canPass(int x, int y, int[][] tempMap) {//判断该点是否为障碍点
        if (tempMap[x][y] < Definition.BLOCK_PLACE || tempMap[x][y] == Definition.PEOPLE_PLACE) {
            return true;
        }
        return false;
    }

    private static boolean isExist(Node node) {
        if (NodeCache.openNodeMap.get(node.getNodeId()) == null && NodeCache.closeNodeMap.get(node.getNodeId()) == null) {
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        //路径测试
        int[][] pointArr = {{1, 1, 1, 1, 1, 1}, {1, 0, 0, 2, 0, 1}, {1, 2, 1, 0, 0, 1}, {1, 0, 1, 1, 0, 1}, {1, 3, 0, 0, -1, 1}, {1, 1, 1, 1, 1, 1}};
        GameMap gameMap = new GameMap();
        Stage testStage = gameMap.initStage(pointArr);
        Position endNode = new Position();
        endNode.setX(2);
        endNode.setY(3);
        //System.out.println(peopleMoveInfo(endNode, testStage, gameMap));
        System.out.println(peopleMoveInfo(endNode, testStage, pointArr));
    }
}
