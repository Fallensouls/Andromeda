package hello.MailService;

import java.util.List;

public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendEmail(List<String>to, String subject, String content, String... args)throws Exception;

    void sendHtmlMail(String[] to, String subject, String content);

    void sendAttachmentsMail(String[] to, String subject, String content, String filePath);

    void sendInlineResourceMail(String[] to, String subject, String content, String rscPath, String rscId);
}
