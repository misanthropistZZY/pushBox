package pushBox.beans.errorInfo;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 16:01
 * @Description:
 */
public enum ErrorCodeEnum {
    SUCCESS("1", "操作成功！"),

    MAP_EXIST_ERROR("Error 20001", "地图信息有误！");

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
