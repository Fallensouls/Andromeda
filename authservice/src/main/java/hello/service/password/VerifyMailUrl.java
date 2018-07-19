package hello.service.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VerifyMailUrl {
    @Autowired
    private MailService mailService;

    public boolean verifyUrl(String username,String sid){
        Mail mail = mailService.findMailByUsername(username);
        mailService.removeRowByID(String.valueOf(mail.getId())); // 验证时应删除原记录，防止二次验证
        return !mail.getOut_time().before(new Date()) && mail.getSid().equals(sid);
    }
}
