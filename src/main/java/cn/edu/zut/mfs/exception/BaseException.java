package cn.edu.zut.mfs.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常基类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private String exceptionMessage;
    private Object data;

    public BaseException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    public BaseException(String exceptionMessage, Object data) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.data = data;
    }
}
