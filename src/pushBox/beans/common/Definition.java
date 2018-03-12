package pushBox.beans.common;

public interface Definition {
    public static final int EMPTY_PLACE = 0; //空地
    public static final int BLOCK_PLACE = 1; //路障点
    public static final int BOX_REQUIRE_PLACE = -1; //箱子要求放置点
    public static final int BOX_PLACE = 2; //箱子目前放置点
    public static final int PEOPLE_PLACE = 3; //人所正在点
    public static final String LEFT = "L"; //左移
    public static final String UP = "U"; //上移
    public static final String RIGHT = "R"; //右移
    public static final String DOWN = "D"; //下移
    public static final String SEPERATE_REGEX = "/";
    public static final String UNDER_LINE = "_";
    public static final String NODE_PREFIX = "node_";
    public static final int EDGE_SIZE = 8;
}
