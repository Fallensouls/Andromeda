package com.fallensouls.messageservice.request;

import com.fallensouls.messageservice.enums.Email.MailTemplate;
import com.fallensouls.messageservice.enums.Email.MailType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class EmailRequest {

    @Email(message = "收件地址应符合邮件格式")
    private String to;
    @NotBlank(message = "信息主题不能为空")
    private String subject;
    //    @NotBlank(message = "信息内容不能为空")
    private String content;
    @NotNull(message = "必须指定信息模板代号")
    private MailTemplate mailTemplate;
    @NotNull(message = "必须指定发送信息的类型")
    private MailType mailType;

    private Map<String, String> contentmap;
}
