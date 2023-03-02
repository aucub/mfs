package cn.edu.zut.mfs.exception;

import lombok.Getter;

/**
 * JSON异常
 */
@Getter
public class JsonException extends BaseException {
    public JsonException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
