package cn.edu.zut.mfs.exception;

import cn.edu.zut.mfs.config.ResultCode;
import lombok.Getter;

/**
 * JSON异常
 */
@Getter
public class JsonException extends BaseException {

    public JsonException(ResultCode resultCode) {
        super(resultCode);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
