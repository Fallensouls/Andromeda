package com.fallensouls.messageservice.enums;

public enum MessageTypeEnum {
    HTML_MAIL("发送html邮件"),
    ATTACHMENT_MAIL("发送带附件的邮件"),
    INLINE_MAIL("发送带静态资源的邮件"),
    SHORTMESSAGE("发送短信"),
    WECHAT("发送微信");

    private String description;

    MessageTypeEnum(String description){
        this.description = description;
    }
}
