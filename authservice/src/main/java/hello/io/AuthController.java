package hello.io;

import hello.service.auth.AuthService;
import hello.service.password.MailRequest;
import hello.service.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import hello.service.user.User;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
//    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    @Autowired
    private AuthService authService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody User user) throws AuthenticationException{
        JwtResponse jwtResponse = authService.login(user);
        return ResponseEntity.ok().body(jwtResponse);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String newtoken = authService.refresh(token);
        if(newtoken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(newtoken);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }

    @RequestMapping(value = "/sendemail", method = RequestMethod.POST)
    public ResponseEntity<?> sendEmail(@RequestBody User user){
        String username = user.getUsername();
        String email = authService.findEmail(username);
        if(email != null){
            MailRequest mailRequest = new MailRequest();
            mailRequest.setUsername(username);
            mailRequest.setEmail(email);
            mailRequest.setSubject("更改密码");
            mailRequest.setContent("点击链接以更改您的密码");
            return restTemplate.postForEntity("http://MAILSERVICE/mail/send",mailRequest,String.class);
        }
        return ResponseEntity.badRequest().body("The user does not exist.");
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        authService.changePassword(username,password);
        return ResponseEntity.ok().body(null);
    }
}
