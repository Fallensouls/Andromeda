package com.fallensouls.messageservice.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fallensouls.messageservice.enums.Email.MailTemplate;
import com.fallensouls.messageservice.request.EmailRequest;
import com.fallensouls.messageservice.exception.RequestNotValidException;
import com.fallensouls.messageservice.request.SmsRequest;
import com.fallensouls.messageservice.service.mailservice.MailService;
import com.fallensouls.messageservice.service.sms.Sms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
public class MessageService {
    @Autowired
    private MailService mailService;

    @Autowired
    private Sms sms;

    public void sendEmail(EmailRequest emailRequest)throws RequestNotValidException {
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        MailTemplate mailTemplate = emailRequest.getMailTemplate();
        Map<String, String> contentMap = emailRequest.getContent();
        String content = "欢迎使用邮件服务！";
        if(mailTemplate != MailTemplate.NOT_USE){
            try {
                content = mailService.createEmailContent(mailTemplate, contentMap);
            }catch (Exception e){
                log.error("填充模板失败,模板名称:{},填充内容:{}", mailTemplate.getTemplateName(), Arrays.toString(contentMap.entrySet().toArray()));
                return;
            }
        }
        switch (emailRequest.getMailType()){
            case HTML_MAIL:
                mailService.sendHtmlMail(to, subject, content);
            case ATTACHMENT_MAIL:
                String[] path = new String[emailRequest.getFilePath().size()];
                emailRequest.getFilePath().toArray(path);
                mailService.sendAttachmentsMail(to, subject, content, path);
            case INLINE_MAIL:
                mailService.sendInlineResourceMail(to, subject, content, emailRequest.getResource());
            default:
                log.error("邮件类型产生异常:{}",emailRequest.getMailType());
                throw new RequestNotValidException("指定的邮件类型有误");
        }
    }

    public void sendSms(SmsRequest smsRequest) {
        SendSmsResponse response = sms.sendSms(smsRequest.getPhoneNumbers(), smsRequest.getSignName(), smsRequest.getSmsTemplate().getCode());
        if(response == null){
            log.error("发送短信失败！");
        }else {
            log.info("发送短信返回的响应为: {}", response.getMessage());
        }
    }
}
