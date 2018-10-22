package hello.dataobject;

import hello.enums.MessageTypeEnum;
import hello.enums.TemplateCodeEnum;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
public class MessageRequest {
    @NonNull
    private String from;
    @NonNull
    private List<String> to;
    @NonNull
    private String subject;
    @NonNull
    private String content;
    @NonNull
    private TemplateCodeEnum templatecode;
    @NonNull
    private MessageTypeEnum type;
}
