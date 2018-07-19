package hello.io;

import hello.service.auth.AuthService;
import hello.service.password.CreateUrl;
import hello.service.password.MailRequest;
import hello.service.password.VerifyMailUrl;
import hello.service.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.service.user.User;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
//    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    @Autowired
    private AuthService authService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CreateUrl createUrl;

    @Autowired
    private VerifyMailUrl verifyMailUrl;

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
            mailRequest.setContent("点击链接以更改您的密码"+createUrl.createurl("http://localhost:8090/auth/verify",username));
            return restTemplate.postForEntity("http://MAILSERVICE/mail/send",mailRequest,String.class);
        }
        return ResponseEntity.badRequest().body("The user does not exist.");
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        authService.changePassword(username,password);
        return ResponseEntity.ok().body(null);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public ResponseEntity<?> verify(@RequestParam("sid") String sid,@RequestParam("username")String username,HttpServletResponse response)throws IOException{
        sid = sid.replace(' ','+');
        if(verifyMailUrl.verifyUrl(username,sid)){
            response.sendRedirect("http://localhost:8080/resetpassword");
            return ResponseEntity.ok().body(null);
        }
        else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
