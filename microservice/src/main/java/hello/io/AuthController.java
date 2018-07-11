package hello.io;

import hello.service.auth.AuthService;
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


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
//    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    @Autowired
    private AuthService authService;

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
        JwtResponse jwtResponse = authService.refresh(token);
        if(jwtResponse == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(jwtResponse);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws AuthenticationException{
        return authService.register(addedUser);
    }
}
