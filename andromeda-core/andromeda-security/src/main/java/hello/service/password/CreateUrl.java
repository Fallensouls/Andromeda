package hello.service.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Service
public class CreateUrl {

    @Autowired
    private MailService mailService;

    public String createurl(String basepath,String username){
        //生成邮件url唯一地址
        Random random = new Random();
        String key = String.valueOf(random.nextInt(900000) + 100000); // 生成六位随机数
        Date outDate = new Date(System.currentTimeMillis()+(long)(30*60*1000));//30分钟后过期             //忽略毫秒数

        String sid=username+"&"+key+"&"+outDate;

        Mail mail = new Mail();
        mail.setUsername(username);
        mail.setSid(Md5Encrypt.EncoderByMd5(sid));
        mail.setOut_time(outDate);
        HashMap<String, Object> params = mailService.getProperties(mail);
        Mail findMailRetrieve=mailService.findMailByUsername(username);

        if(findMailRetrieve!=null){ // 如之前已经发送过找回密码的邮件，应将其作废
            mailService.removeRowByID(String.valueOf(findMailRetrieve.getId()));
        }
        mailService.addRowByID(Mail.class,params);
        return basepath+"?sid="+Md5Encrypt.EncoderByMd5(sid)+"&username="+username;
    }
}
