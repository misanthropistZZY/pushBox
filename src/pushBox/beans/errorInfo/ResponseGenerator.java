package pushBox.beans.errorInfo;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 14:11
 * @Description:
 */

public class ResponseGenerator {

    private String code;
    private String message;
    private Object body;

    public String getCode() {
        return code;
    }

    public ResponseGenerator setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseGenerator setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getBody() {
        return body;
    }

    public ResponseGenerator setBody(Object body) {
        this.body = body;
        return this;
    }
}
