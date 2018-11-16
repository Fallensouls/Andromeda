package com.fallensouls.messageservice.service.messageservice;

import com.fallensouls.messageservice.domain.MessageRequest;
import com.fallensouls.messageservice.enums.TemplateCodeEnum;
import com.fallensouls.messageservice.exception.ArgumentNotValidException;
import com.fallensouls.messageservice.exception.MessageException;
import com.fallensouls.messageservice.service.mailservice.MailService;
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

    public void sendMessage(MessageRequest messageRequest)throws MessageException,ArgumentNotValidException{
        String to = messageRequest.getTo();
        String subject = messageRequest.getSubject();
        String content = messageRequest.getContent();
        TemplateCodeEnum templateCodeEnum = messageRequest.getTemplatecode();
        Map<String, String> contentmap = messageRequest.getContentmap();
        if(templateCodeEnum != TemplateCodeEnum.NOT_USE){
            try {
                content = mailService.createEmailContent(templateCodeEnum, contentmap);
            }catch (Exception e){
                log.error("填充模板失败,模板代号:{},填充内容:{}", templateCodeEnum.getCode(), Arrays.toString(contentmap.entrySet().toArray()));
                throw new MessageException("填充模板失败!");
            }
        }
        switch (messageRequest.getType()){
            case HTML_MAIL:
                mailService.sendHtmlMail(to, subject, content);
            case ATTACHMENT_MAIL:
//                mailService.sendAttachmentsMail(to, subject, content);
            case INLINE_MAIL:
//                mailService.sendInlineResourceMail(to, subject, content);
            case WECHAT:
                break;
            case SHORTMESSAGE:
//                SendSMS(messageRequest);
            default:
                log.error("message类型产生异常:{}",messageRequest.getType());
                throw new ArgumentNotValidException("指定的message类型有误");
        }
    }
}
