package pushBox.beans.pojo;

import pushBox.beans.common.Definition;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-27 19:38
 * @Description:
 */
public class Node extends Position {
    private String nodeId; //节点标识
    //f(node)=g(node)+h(node)
    private int g; //已经走的步数
    private int h; //预计还要走的步数
    private int f; //估价值
    private Node fatherNode; //前继节点

    private String moveInfo;

    public void caculateF(Position endNode) {
        this.h = Math.abs(endNode.X - this.X) + Math.abs(endNode.Y - this.Y);
        this.f = this.g + this.h;
    }

    public void generateId() {
        this.nodeId = Definition.NODE_PREFIX + this.X + Definition.UNDER_LINE + this.Y;
    }

    public Node() {
    }

    public Node(int X, int Y) {
        this.X = X;
        this.Y = Y;
        generateId();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public String getMoveInfo() {
        return moveInfo;
    }

    public void setMoveInfo(String moveInfo) {
        this.moveInfo = moveInfo;
    }

    public Node getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(Node fatherNode) {
        this.fatherNode = fatherNode;
    }
}
