package com.fallensouls.messageservice.enums;

public enum TemplateCodeEnum {
    NOT_USE("不使用模板", 0),
    TEST("使用测试模板",1);

    private String desc;
    private int code;

    TemplateCodeEnum(String desc, int code){
        this.desc = desc;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
