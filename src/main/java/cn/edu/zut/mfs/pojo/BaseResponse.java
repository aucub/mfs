package cn.edu.zut.mfs.pojo;

/**
 * @param <T>
 */
public class BaseResponse<T> {

    /**
     * 成功返回
     * @param data
     * @param <T>
     * @return
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
     *
     * @param <T>
     * @return
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
     *
     * @param <T>
     * @return
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
     *
     * @param <T>
     * @return
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
     *
     * @param <T>
     * @return
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
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> baseException(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResultCode.BaseException.getCode());
        response.setMessage(ResultCode.BaseException.getMessage());
        response.setData(data);
        return response;
    }


    public void setCode(Integer code) {
        /*
          响应状态码 200表示成功，500表示失败
         */
    }

    public void setMessage(String message) {
        /*
          响应结果描述
         */
    }

    public void setData(T data) {
        /*
          返回的数据
         */
    }
}
