package com.fallensouls.messageservice.enums.Email;

import lombok.Getter;

@Getter
public enum MailType {
    HTML_MAIL("发送html邮件"),
    ATTACHMENT_MAIL("发送带附件的邮件"),
    INLINE_MAIL("发送带静态资源的邮件");

    private String description;

    MailType(String description){
        this.description = description;
    }
}
