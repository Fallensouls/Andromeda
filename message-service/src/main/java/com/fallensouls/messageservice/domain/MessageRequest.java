package com.fallensouls.messageservice.domain;

import com.fallensouls.messageservice.enums.MessageTypeEnum;
import com.fallensouls.messageservice.enums.TemplateCodeEnum;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Getter
public class MessageRequest {

    @NotEmpty(message = "接收人不能为空")
    private String to;
    @NotBlank(message = "信息主题不能为空")
    private String subject;
//    @NotBlank(message = "信息内容不能为空")
    private String content;
    @NotEmpty(message = "必须指定信息模板代号")
    private TemplateCodeEnum templatecode;
    @NotEmpty(message = "必须指定发送信息的类型")
    private MessageTypeEnum type;

    private Map<String, String> contentmap;

}
