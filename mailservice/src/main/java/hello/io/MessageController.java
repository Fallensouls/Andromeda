package hello.io;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import hello.MailService.MailService;
import hello.ShortMessageService.Sms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail")
public class MessageController {
    @Autowired
    private MailService mailService;

    @Autowired
    private Sms sms;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<?> SendMessage(@RequestBody MessageRequest messageRequest){
        if(messageRequest.getType() == 0){
            return SendEmail(messageRequest);
        }
        else if(messageRequest.getType() == 1){
            return SendSMS(messageRequest);
        }
        else{
            return SendWeChat(messageRequest);
        }
    }

    private ResponseEntity<?> SendEmail(@RequestBody MessageRequest mailRequest){
        String status = mailService.sendSimpleMail(mailRequest.getTo(),mailRequest.getSubject(),mailRequest.getContent());
        if(status.equals("success")){
            return ResponseEntity.ok().body("send email successfully!");
        }
        else{
            return ResponseEntity.badRequest().body("fail to send email!");
        }
    }

    private ResponseEntity<?> SendSMS(@RequestBody MessageRequest smsRequest){
        SendSmsResponse response = new SendSmsResponse();
        String status = "success";
        try{
            response = sms.sendSms(smsRequest.getTo(),smsRequest.getSubject(),smsRequest.getContent());
        }catch (Exception e){
            System.out.println("Error when sending shortmessage," + e);
            status = "fail";
        }
        finally {
            try{
                sms.queryDetails(response);
            }catch (Exception e){
                status = "fail to query the details.";
            }
        }
        if(status.equals("fail")){
            return ResponseEntity.badRequest().body("fail to send shortmessage!");
        }
        else{
            return ResponseEntity.ok().body("send shortmessage successfully!");
        }
    }

    private ResponseEntity<?> SendWeChat(@RequestBody MessageRequest wechatRequest){
        String status = mailService.sendSimpleMail(wechatRequest.getTo(),wechatRequest.getSubject(),wechatRequest.getContent());
        if(status.equals("success")){
            return ResponseEntity.ok().body("send wechat message successfully!");
        }
        else{
            return ResponseEntity.badRequest().body("fail to send wechat message!");
        }
    }
}
