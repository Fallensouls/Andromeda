package hello.service.password;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("message-service")
public interface MessageService {

    @GetMapping(value = "/mail/send")
    ResponseEntity<?> SendMessage(@RequestBody MessageRequest messageRequest);
}
