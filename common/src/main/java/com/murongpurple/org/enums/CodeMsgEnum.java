package com.murongpurple.org.enums;

public enum CodeMsgEnum {
    SUCCESS(200,"SUCCESS"),
    USERNUM_CANNOT_BE_EVEN(400,"用户号不能为偶数")
    ;
    private int code;
    private String msg;

    CodeMsgEnum() {
    }

    CodeMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
