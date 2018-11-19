package com.fallensouls.messageservice.controller;

import com.fallensouls.messageservice.exception.RequestNotValidException;
import com.fallensouls.messageservice.request.EmailRequest;
import com.fallensouls.messageservice.request.SmsRequest;
import com.fallensouls.messageservice.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/send/email", method = RequestMethod.POST)
    public void SendEmail(@RequestBody @Valid EmailRequest emailRequest,
                            BindingResult bindingResult)throws RequestNotValidException {
        if(bindingResult.hasErrors()){
            log.error("emailRequest参数有误：{}", bindingResult.getFieldError().getDefaultMessage());
            throw new RequestNotValidException("emailRequest参数校验失败");
        }
        log.info("邮件发送中...");
        messageService.sendEmail(emailRequest);
    }

    @RequestMapping(value = "/send/sms", method = RequestMethod.POST)
    public void SendSms(@RequestBody @Valid SmsRequest smsRequset,
                            BindingResult bindingResult)throws RequestNotValidException {
        if(bindingResult.hasErrors()){
            log.error("smsRequest参数有误：{}", bindingResult.getFieldError().getDefaultMessage());
            throw new RequestNotValidException("smsRequest参数校验失败");
        }
        log.info("短信发送中...");
        messageService.sendSms(smsRequset);
    }

}
