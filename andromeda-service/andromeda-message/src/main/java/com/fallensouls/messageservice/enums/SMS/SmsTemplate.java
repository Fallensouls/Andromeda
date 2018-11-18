package com.fallensouls.messageservice.enums.SMS;

import lombok.Getter;

@Getter
public enum SmsTemplate {
    TEST("测试模板", "1231241");

    private String desc;
    private String code;

    SmsTemplate(String desc, String code) {
        this.desc = desc;
        this.code = code;
    }
}
