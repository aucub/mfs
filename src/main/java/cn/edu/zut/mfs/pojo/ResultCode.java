package cn.edu.zut.mfs.pojo;


public enum ResultCode {

    /**
     * 操作成功
     **/
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     **/
    FAILED(500, "操作失败"),
    /**
     * JSON解析错误
     **/
    JSONError(301, "JSON解析错误"),
    /**
     * Api异常
     **/
    APIException(302, "API异常"),
    /**
     * SaToken异常
     **/
    SaTokenException(303, "SaToken异常"),
    /**
     * 异常
     **/
    BaseException(300, "异常");

    /**
     * 自定义状态码
     **/
    private final Integer code;
    /**
     * 自定义描述
     **/
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
