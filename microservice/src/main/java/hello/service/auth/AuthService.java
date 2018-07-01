package hello.service.auth;

import hello.service.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;

import hello.service.security.JwtTokenUtil;
import hello.service.user.User;
import hello.service.user.UserService;
import hello.service.security.JwtUser;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

//    @Value("${jwt.tokenHead}")
    private String tokenHead = "Bearer ";


    public User register(User userToAdd) {
        String username = userToAdd.getUsername();
        if(userService.findUserByUsername(username)!=null) {
            System.out.println("该用户名已被注册！");
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        HashMap<String, Object> params = userService.getProperties(userToAdd);
        String userid = (String)params.get("id");
        userService.addAuthForUser(userid,1); //新注册的用户设为普通用户
        return userService.addRowByUUID(User.class,params);
    }


    public String login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }


    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getUpddate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}

