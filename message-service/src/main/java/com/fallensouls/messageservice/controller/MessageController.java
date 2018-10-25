package com.fallensouls.messageservice.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fallensouls.messageservice.exception.ArgumentNotValidException;
import com.fallensouls.messageservice.exception.MessageException;
import com.fallensouls.messageservice.service.messageservice.MessageService;
import com.fallensouls.messageservice.service.sms.Sms;
import com.fallensouls.messageservice.domain.MessageRequest;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping(value = "/message")
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageService messageService;

    @Autowired
    private Sms sms;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void SendMessage(@RequestBody @Valid MessageRequest messageRequest,
                            BindingResult bindingResult)throws MessageException,ArgumentNotValidException {
        if(bindingResult.hasErrors()){
            logger.error("messageRequest参数有误：{}", bindingResult.getFieldError().getDefaultMessage());
            throw new ArgumentNotValidException("messageRequest参数校验失败");
        }
        log.info("信息发送中...");
        messageService.sendMessage(messageRequest);
    }


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
