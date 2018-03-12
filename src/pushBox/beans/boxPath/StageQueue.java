package pushBox.beans.boxPath;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-27 16:09
 * @Description:
 */
public class StageQueue {
    public static Queue<Stage> stageQueue = new LinkedList<>();
    public static int stageCount = 0;

    public static void putToQueue(Stage stage) {
        if (stageQueue.add(stage)) {
            stageCount++;
        }
    }

    public static void clean() {
        stageQueue.clear();
        stageCount = 0;
    }
}
