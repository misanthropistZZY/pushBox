package pushBox.beans.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 19:18
 * @Description:
 */
public class StageCach {
    //key为索引，value为队列中的位置
    public static Map stageCache = new HashMap<String, Integer>();

    public static boolean isExist(String stageId) {
        return stageCache.get(stageId) == null ? false : true;
    }

    public static void clean(){
        stageCache.clear();
    }
}
