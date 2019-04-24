package com.GlobalLogic.exception;

public class CityRegisterException extends Exception {
    private String code;

    public String getCode() {
        return code;
    }

    public CityRegisterException(String code,String message) {
        super(message);
        this.code = code;
    }

    public CityRegisterException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
