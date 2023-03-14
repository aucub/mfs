package cn.edu.zut.mfs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private Integer code;
    private String message;
    private T data;

    /**
     * 成功返回
     */
    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.SUCCESS.getCode());
        response.setMessage(ResultCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }


    /**
     * 失败返回
     */
    public static <T> BaseResponse<T> fail(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.FAILED.getCode());
        response.setMessage(ResultCode.FAILED.getMessage());
        response.setData(data);
        return response;
    }

    /**
     * JSON 解析错误返回
     */
    public static <T> BaseResponse<T> jsonError(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.JSONError.getCode());
        response.setMessage(ResultCode.JSONError.getMessage());
        response.setData(data);
        return response;
    }


    /**
     * Api异常 返回
     */
    public static <T> BaseResponse<T> apiException(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.APIException.getCode());
        response.setMessage(ResultCode.APIException.getMessage());
        response.setData(data);
        return response;
    }

    /**
     * SaToken异常 返回
     */
    public static <T> BaseResponse<T> saTokenException(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.SaTokenException.getCode());
        response.setMessage(ResultCode.SaTokenException.getMessage());
        response.setData(data);
        return response;
    }

    /**
     * 异常 返回
     */
    public static <T> BaseResponse<T> baseException(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.BaseException.getCode());
        response.setMessage(ResultCode.BaseException.getMessage());
        response.setData(data);
        return response;
    }
}
