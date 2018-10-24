package com.fallensouls.messageservice.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fallensouls.messageservice.enums.TemplateCodeEnum;
import com.fallensouls.messageservice.exception.ArgumentErrorException;
import com.fallensouls.messageservice.service.mailservice.MailService;
import com.fallensouls.messageservice.service.sms.Sms;
import com.fallensouls.messageservice.domain.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/message")
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;

    @Autowired
    private Sms sms;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void SendMessage(@RequestBody @Valid MessageRequest messageRequest,
                                         BindingResult bindingResult)throws ArgumentErrorException{
        if(bindingResult.hasErrors()){
            logger.error("messageRequest参数有误：{}", bindingResult.getFieldError().getDefaultMessage());
            throw new ArgumentErrorException("messageRequest参数校验失败");
        }
        String to = messageRequest.getTo();
        String subject = messageRequest.getSubject();
        String content = messageRequest.getContent();
        TemplateCodeEnum templateCodeEnum = messageRequest.getTemplatecode();
        if(templateCodeEnum != TemplateCodeEnum.NOT_USE){
            content = mailService.createEmailContent(templateCodeEnum, messageRequest.getContentmap());
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
                SendSMS(messageRequest);
            default:
                throw new ArgumentErrorException("指定的message类型有误");
        }
    }

//    private ResponseEntity<?> SendEmail(MessageRequest mailRequest){
//        try {
////            mailService.sendEmail(mailRequest.getTo(),mailRequest.getSubject(),mailRequest.getContent());
//            return ResponseEntity.ok().body(null);
//        }catch (Exception e){
//            logger.error(e.getMessage());
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

    private ResponseEntity<?> SendSMS(MessageRequest smsRequest){
        SendSmsResponse response = new SendSmsResponse();
        try{
            response = sms.sendSms(smsRequest.getTo(),smsRequest.getSubject(),smsRequest.getContent());
            return ResponseEntity.ok().body(null);
        }catch (Exception e){
            logger.error("Error when sending shortmessage," + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
//        finally {
//            try{
//                sms.queryDetails(response);
//            }catch (Exception e){
//                logger.info("fail to query the details.");
//                status = "fail to query the details.";
//            }
//        }

    }

}
