package cn.edu.zut.mfs.exception;

public class ApiException extends BaseException {

    public ApiException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public ApiException(String exceptionMessage, Object data) {
        super(exceptionMessage, data);
    }
}
