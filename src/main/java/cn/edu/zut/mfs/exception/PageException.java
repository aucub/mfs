package cn.edu.zut.mfs.exception;

import cn.edu.zut.mfs.config.Status;
import lombok.Getter;

/**
 * <p>
 * 页面异常
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-10-02 21:18
 */
@Getter
public class PageException extends BaseException {

    public PageException(Status status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
