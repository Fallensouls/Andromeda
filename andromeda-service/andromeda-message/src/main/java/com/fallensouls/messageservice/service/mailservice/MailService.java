package com.fallensouls.messageservice.service.mailservice;

import com.fallensouls.messageservice.enums.Email.MailTemplate;
import com.fallensouls.messageservice.exception.MessageException;

import java.util.Map;

public interface MailService {

    String createEmailContent(MailTemplate mailTemplate, Map<String, String> contentmap) throws Exception;

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content) throws MessageException;

    void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessageException;

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessageException;
}
