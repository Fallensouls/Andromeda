package hello.enums;

public enum MessageTypeEnum {
    MAIL("发送邮件"),
    SHORTMESSAGE("发送短信"),
    WECHAT("发送微信");

    private String description;

    MessageTypeEnum(String description){
        this.description = description;
    }
}
