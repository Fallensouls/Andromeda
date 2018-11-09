package com.fallensouls.messageservice.service.mailservice;

import com.fallensouls.messageservice.MessageServiceApplicationTests;
import com.fallensouls.messageservice.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
public class MailServiceImplTest extends MessageServiceApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        String to = "122584482@qq.com";
        String subject = "测试邮件";
        String content = "test mail service.";
        mailService.sendSimpleMail(to, subject, content);
    }

    @Test
    public void sendHtmlMail() {
        String to = "122584482@qq.com";
        String subject = "账号激活";
        //创建邮件正文
        Context context = new Context();
        context.setVariable("username", "HatsuneMiku");
        String emailContent = templateEngine.process("emailTemplate", context);
        try {
            mailService.sendHtmlMail(to, subject, emailContent);
        }catch (MessageException e){
            log.error(e.getMessage());
        }
    }

    @Test
    public void sendAttachmentsMail() {
        String to = "122584482@qq.com";
        String subject = "测试附件";
        String content = "本邮件带有附件，请查收。";
        String filePath = "/home/hatsunemiku/Pictures/11th.bmp";
        try {
            mailService.sendAttachmentsMail(to, subject, content, filePath);
        }catch (MessageException e){
            log.error(e.getMessage());
        }
    }

    @Test
    public void sendInlineResourceMail() {
        String to = "122584482@qq.com";
        String subject = "测试静态资源";
        String rscId = "001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String rscPath = "/home/hatsunemiku/Pictures/11th.bmp";
        try{
            mailService.sendInlineResourceMail(to, subject, content, rscPath, rscId);
        }catch (MessageException e){
            log.error(e.getMessage());
        }
    }
}