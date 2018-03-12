package pushBox.beans.pojo;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-29 17:47
 * @Description:
 */
public class Result {
    private String path;
    private int stepCount;
    private int searchCount;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }
}
