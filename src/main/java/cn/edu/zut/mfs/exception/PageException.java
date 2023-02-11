package cn.edu.zut.mfs.exception;

import cn.edu.zut.mfs.pojo.ResultCode;
import lombok.Getter;

/**
 * 页面异常
 */
@Getter
public class PageException extends BaseException {

    public PageException(ResultCode resultCode) {
        super(resultCode);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
