package cn.edu.zut.mfs.handler;

import cn.edu.zut.mfs.exception.JsonException;
import cn.edu.zut.mfs.pojo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // json 异常处理
    @ExceptionHandler(value = JsonException.class)
    public BaseResponse<JsonException> jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMessage());
        return BaseResponse.jsonError(exception);
    }

    // 其它所有异常
    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handlerException(Exception e) {
        e.printStackTrace();
        return BaseResponse.baseException(e.getMessage());
    }

}

