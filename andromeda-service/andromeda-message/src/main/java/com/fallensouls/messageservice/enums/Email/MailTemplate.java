package com.fallensouls.messageservice.enums.Email;

import lombok.Getter;

@Getter
public enum  MailTemplate{
    NOT_USE("不使用模板", null),
    TEST("使用测试模板","TestTemplate");

    private String desc;
    private String templateName;

    MailTemplate(String desc, String templateName){
        this.desc = desc;
        this.templateName = templateName;
    }

}
