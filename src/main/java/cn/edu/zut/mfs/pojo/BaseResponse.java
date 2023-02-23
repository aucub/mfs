package cn.edu.zut.mfs.pojo;

/**
 * @param <T>
 */
public class BaseResponse<T> {

    /**
     * 成功返回
     *
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
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> fail(Integer code, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);
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
