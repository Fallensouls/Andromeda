package com.fallensouls.messageservice.service.mailservice;

import com.fallensouls.messageservice.enums.Email.MailTemplate;

import java.util.Map;

public interface MailService {

    String createEmailContent(MailTemplate mailTemplate, Map<String, String> contentmap) throws Exception;

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content) ;

    void sendAttachmentsMail(String to, String subject, String content, String[] filePath) ;

    void sendInlineResourceMail(String to, String subject, String content,  Map<String, String> resource) ;
}
