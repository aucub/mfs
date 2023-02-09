package cn.edu.zut.mfs.config;

public enum ResultCode {

    /**
     * 操作成功
     **/
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     **/
    FAILED(500, "操作失败");


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
