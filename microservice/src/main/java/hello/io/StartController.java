package hello.io;

import hello.service.start.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import hello.service.user.User;

@RestController
public class StartController {
//    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    @Autowired
    private StartService startService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody User user) throws AuthenticationException{
        final String token = startService.login(user);
        // Return the token
        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = startService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(refreshedToken);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException{
        return startService.register(addedUser);
    }
}
