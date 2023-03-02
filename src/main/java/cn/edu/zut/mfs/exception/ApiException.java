package cn.edu.zut.mfs.exception;

/**
 * API异常
 */
public class ApiException extends BaseException {

    public ApiException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public ApiException(String exceptionMessage, Object data) {
        super(exceptionMessage, data);
    }
}
