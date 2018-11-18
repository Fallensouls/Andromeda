package com.fallensouls.messageservice.service;

import com.aliyuncs.exceptions.ClientException;
import com.fallensouls.messageservice.enums.Email.MailTemplate;
import com.fallensouls.messageservice.request.EmailRequest;
import com.fallensouls.messageservice.exception.ArgumentNotValidException;
import com.fallensouls.messageservice.exception.MessageException;
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

    public void sendEmail(EmailRequest emailRequest)throws MessageException,ArgumentNotValidException{
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String content = emailRequest.getContent();
        MailTemplate mailTemplate = emailRequest.getMailTemplate();
        Map<String, String> contentmap = emailRequest.getContentmap();
        if(mailTemplate != MailTemplate.NOT_USE){
            try {
                content = mailService.createEmailContent(mailTemplate, contentmap);
            }catch (Exception e){
                log.error("填充模板失败,模板名称:{},填充内容:{}", mailTemplate.getTemplateName(), Arrays.toString(contentmap.entrySet().toArray()));
                throw new MessageException("填充模板失败!");
            }
        }
        switch (emailRequest.getMailType()){
            case HTML_MAIL:
                mailService.sendHtmlMail(to, subject, content);
            case ATTACHMENT_MAIL:
//                mailService.sendAttachmentsMail(to, subject, content);
            case INLINE_MAIL:
//                mailService.sendInlineResourceMail(to, subject, content);
            default:
                log.error("邮件类型产生异常:{}",emailRequest.getMailType());
                throw new ArgumentNotValidException("指定的邮件类型有误");
        }
    }

    public void sendSms(SmsRequest smsRequest) throws ClientException {
        sms.sendSms(smsRequest.getPhoneNumbers(), smsRequest.getSignName(), smsRequest.getSmsTemplate().getCode());
    }
}
