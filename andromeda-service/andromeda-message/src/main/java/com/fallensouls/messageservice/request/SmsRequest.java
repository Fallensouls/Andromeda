package com.fallensouls.messageservice.request;

import com.fallensouls.messageservice.enums.SMS.SmsTemplate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SmsRequest {
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号应为以1开始的11位数字")
    private String phoneNumbers;
    @NotBlank(message = "必须提供短信签名")
    private String signName;
    @NotNull(message = "必须指定短信模板")
    private SmsTemplate smsTemplate;

    private String templateParam;
}
