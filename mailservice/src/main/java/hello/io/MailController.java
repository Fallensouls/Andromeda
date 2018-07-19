package hello.io;

import hello.MailService.MailRequest;
import hello.MailService.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<?> SendEmail(@RequestBody MailRequest mailRequest){
        String status = mailService.sendSimpleMail(mailRequest.getEmail(),mailRequest.getSubject(),mailRequest.getContent());
        if(status.equals("success")){
            return ResponseEntity.ok().body("send email successfully!");
        }
         else{
            return ResponseEntity.badRequest().body("fail to send email!");
        }
    }
}
