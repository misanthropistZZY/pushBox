package pushBox.beans.caches;

import pushBox.beans.pojo.Node;

import java.util.*;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-27 19:56
 * @Description:
 */
public class NodeCache {
    public static Map openNodeMap = new TreeMap<String, Node>(); //将被访问的节点

    public static Map closeNodeMap = new HashMap<String, Node>(); //已被访问的节点

    //清空节点缓存
    public static void cleanNode() {
        openNodeMap.clear();
        closeNodeMap.clear();
    }

    public static Node mapSort() {
        List<Map.Entry<String, Node>> list = new ArrayList<Map.Entry<String, Node>>(openNodeMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Node>>() {
            @Override
            public int compare(Map.Entry<String, Node> o1, Map.Entry<String, Node> o2) {
                return o1.getValue().getF() > o2.getValue().getF() ? 1 : -1;//升序排列，第一个为最小元素
            }
        });
        return list.get(0).getValue();
    }

    public static Node getMinFNode() { //获得代价最低节点
        return mapSort();
    }

    public static void main(String[] args) {
        //节点排序测试
        Node node1 = new Node();
        node1.setF(4);
        node1.setNodeId("1");
        Node node2 = new Node();
        node2.setF(2);
        node2.setNodeId("2");
        Node node3 = new Node();
        node3.setNodeId("3");
        node3.setF(8);

        openNodeMap.put("1", node1);
        openNodeMap.put("2", node2);
        openNodeMap.put("3", node3);

        mapSort();
    }
}
