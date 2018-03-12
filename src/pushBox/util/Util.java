package pushBox.util;

import pushBox.beans.GameMap;
import pushBox.beans.boxPath.Stage;
import pushBox.beans.common.Definition;
import pushBox.beans.pojo.Box;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 16:05
 * @Description:
 */
public class Util {
    //hashString fomart: x_y/x_y/...
    public static boolean compareHashString(String str1, String str2) {
        String[] str1Arr = str1.split(Definition.SEPERATE_REGEX);
        String[] str2Arr = str2.split(Definition.SEPERATE_REGEX);
        for (int i = 0; i < str1Arr.length; i++) {
            boolean isNotExist = false;
            for (int j = 0; j < str2Arr.length; j++) {
                if (str1Arr[i].equals(str2Arr[j])) {
                    isNotExist = true;
                }
            }
            if (!isNotExist) {
                return false;
            }
        }
        return true; //两个hashString相同
    }

    public static int[][] generateTempMap(Stage stage) {
        int[][] tempMap = tempMapClone();
        for (Box box : stage.getBoxList()) {
            tempMap[box.X][box.Y] = Definition.BOX_PLACE;
        }
        return tempMap;
    }

    public static int[][] tempMapClone() {
        int[][] cloneMap = new int[GameMap.height][GameMap.length];
        for (int i = 0; i < GameMap.height; i++) {
            for (int j = 0; j < GameMap.length; j++) {
                cloneMap[i][j] = GameMap.emptyMap[i][j];
            }
        }
        return cloneMap;
    }

    public static int[] getList(String boxString) {
        String temp = boxString.substring(1, boxString.length() - 1);
        System.out.println(temp);
        String[] strings = temp.split(",");
        System.out.println(strings);
        int[] boxList = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            boxList[i] = Integer.valueOf(strings[i]);
        }
        return boxList;
    }

    public static int changeValue(int origin) {
        switch (origin) {
            case 0:
                return 1;

            case 1:
                return 1;

            case 2:
                return 0;

            case 3:
                return -1;

            default:
                break;
        }
        return 0;
    }
}
