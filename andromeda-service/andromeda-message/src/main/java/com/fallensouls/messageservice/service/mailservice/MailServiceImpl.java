package com.fallensouls.messageservice.service.mailservice;

import com.fallensouls.messageservice.enums.Email.MailTemplate;
import com.fallensouls.messageservice.exception.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Component
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 根据模板代号创建相应的邮件内容
     * @param mailTemplate
     * @param contentmap
     * @return
     * @throws Exception
     */
    public String createEmailContent(MailTemplate mailTemplate, Map<String, String>contentmap)throws Exception{
        //创建邮件正文
        Context context = new Context();
        for(Map.Entry<String, String> entry : contentmap.entrySet()){
            context.setVariable(entry.getKey(), entry.getValue());
        }
         return templateEngine.process(mailTemplate.getTemplateName(), context);
    }

    /**
     * 用于发送最简单的非HTML邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            logger.info("成功向{}发送简单邮件", to);
        } catch (Exception e) {
            logger.error("发送简单邮件至{}时发生异常,异常信息为:{}", to, e.getMessage());
        }
    }

    /**
     * 用于发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessageException
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessageException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("成功向{}发送html邮件", to);
        } catch (MessagingException e) {
            logger.error("发送html邮件至{}时发生异常,异常信息为:{}", to, e.getMessage());
            throw new MessageException("发送html邮件失败");
        }
    }


    /**
     * 用于发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessageException
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessageException{
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);
            mailSender.send(message);
            logger.info("成功向{}发送带附件的邮件", to);
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件至{}时发生异常,异常信息为:{}", to, e.getMessage());
            throw new MessageException("发送带附件的邮件失败");
        }
    }

    /**
     * 用于发送正文中有静态资源（图片）的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessageException
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessageException{
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送至{}", to);
        } catch (MessagingException e) {
            logger.error("向{}发送嵌入静态资源的邮件时发生异常,异常信息为:{}", to, e.getMessage());
            throw new MessageException("发送带有静态资源的邮件失败");
        }
    }
}

