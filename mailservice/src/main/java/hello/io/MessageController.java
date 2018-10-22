package hello.io;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import hello.MailService.MailService;
import hello.ShortMessageService.Sms;
import hello.dataobject.MessageRequest;
import hello.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/message")
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;

    @Autowired
    private Sms sms;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<?> SendMessage(@RequestBody MessageRequest messageRequest){
        if(messageRequest.getType() == MessageTypeEnum.MAIL){
            return SendEmail(messageRequest);
        }
        else if(messageRequest.getType() == MessageTypeEnum.SHORTMESSAGE){
            return SendSMS(messageRequest);
        }
        else{
            return null;
        }
    }

    private ResponseEntity<?> SendEmail(@RequestBody MessageRequest mailRequest){
        try {
            mailService.sendEmail(mailRequest.getTo(),mailRequest.getSubject(),mailRequest.getContent());
            return ResponseEntity.ok().body(null);
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    private ResponseEntity<?> SendSMS(@RequestBody MessageRequest smsRequest){
        SendSmsResponse response = new SendSmsResponse();
        try{
            response = sms.sendSms(smsRequest.getTo().get(0),smsRequest.getSubject(),smsRequest.getContent());
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


//    private ResponseEntity<?> SendWeChat(@RequestBody MessageRequest wechatRequest){
//        String status = mailService.sendSimpleMail(wechatRequest.getTo(),wechatRequest.getSubject(),wechatRequest.getContent());
//        if(status.equals("success")){
//            return ResponseEntity.ok().body("send wechat message successfully!");
//        }
//        else{
//            return ResponseEntity.badRequest().body("fail to send wechat message!");
//        }
//    }
}
