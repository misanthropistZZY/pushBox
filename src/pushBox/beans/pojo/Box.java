package pushBox.beans.pojo;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 14:14
 * @Description:
 */

public class Box extends Position {
    private String name; //箱子标识

    public Box(String name, int x, int y) {
        this.name = name;
        this.X = x;
        this.Y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
