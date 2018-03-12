package pushBox.beans.errorInfo;

/**
 * @Author:Zzy
 * @Date: Crated in 2017-11-23 16:04
 * @Description:
 */
public class ErrorCodeException extends RuntimeException {
    
    private ErrorCodeEnum errorCode;
    private String message;

    public ErrorCodeException(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCodeException(ErrorCodeEnum errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getCode() {
        return errorCode.getCode();
    }

    @Override
    public String getMessage() {
        if (message != null) {
            return message;
        }
        return errorCode.getMessage();
    }
}
