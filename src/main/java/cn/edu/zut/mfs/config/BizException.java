package cn.edu.zut.mfs.config;

public class BizException extends RuntimeException {

    private String retCode;

    private String retMessage;

    public BizException() {
        super();
    }

    public BizException(String retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }
}
