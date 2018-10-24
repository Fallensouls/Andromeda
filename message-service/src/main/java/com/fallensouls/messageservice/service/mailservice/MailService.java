package com.fallensouls.messageservice.service.mailservice;

import com.fallensouls.messageservice.enums.TemplateCodeEnum;
import java.util.Map;

public interface MailService {

    String createEmailContent(TemplateCodeEnum templateCodeEnum, Map<String, String> contentmap);

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
