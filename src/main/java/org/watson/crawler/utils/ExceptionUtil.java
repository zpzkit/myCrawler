package org.watson.crawler.utils;

/**
 * Created by watson zhang on 16/9/27.
 */
public class ExceptionUtil extends RuntimeException {

    public static final ExceptionUtil connectError = new ExceptionUtil("连接出错", 1001);

    String message;
    int code;

    public ExceptionUtil(String message, int code){
        this.code = code;
        this.message = message;
    }

    public String errorMessageFormat(){
        return "code: " + this.code + ", message: " + this.message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
