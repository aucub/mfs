package cn.edu.zut.mfs.handler;

import cn.dev33.satoken.exception.*;
import cn.edu.zut.mfs.exception.JsonException;
import cn.edu.zut.mfs.pojo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理
 */
//@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // json 异常处理
    @ExceptionHandler(value = JsonException.class)
    public BaseResponse<JsonException> jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMessage());
        return BaseResponse.jsonError(exception);
    }


    // 未登录异常
    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<String> handlerException(NotLoginException e) {

        // 打印堆栈，以供调试
        e.printStackTrace();

        // 返回给前端
        return BaseResponse.saTokenException(e.getMessage());
    }

    // 缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public BaseResponse<String> handlerException(NotPermissionException e) {
        e.printStackTrace();
        return BaseResponse.saTokenException("缺少权限：" + e.getPermission());
    }

    // 缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public BaseResponse<String> handlerException(NotRoleException e) {
        e.printStackTrace();
        return BaseResponse.saTokenException("缺少角色：" + e.getRole());
    }

    // 二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public BaseResponse<String> handlerException(NotSafeException e) {
        e.printStackTrace();
        return BaseResponse.saTokenException("二级认证校验失败：" + e.getService());
    }

    // 服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public BaseResponse<String> handlerException(DisableServiceException e) {
        e.printStackTrace();
        return BaseResponse.saTokenException("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    // Http Basic 校验失败异常
    @ExceptionHandler(NotBasicAuthException.class)
    public BaseResponse<String> handlerException(NotBasicAuthException e) {
        e.printStackTrace();
        return BaseResponse.saTokenException(e.getMessage());
    }

    // 其它所有异常
    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handlerException(Exception e) {
        e.printStackTrace();
        return BaseResponse.baseException(e.getMessage());
    }

}

