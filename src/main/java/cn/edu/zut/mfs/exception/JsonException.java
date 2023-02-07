package cn.edu.zut.mfs.exception;

import cn.edu.zut.mfs.config.Status;
import lombok.Getter;

/**
 * <p>
 * JSON异常
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-02 21:18
 */
@Getter
public class JsonException extends BaseException {

    public JsonException(Status status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
