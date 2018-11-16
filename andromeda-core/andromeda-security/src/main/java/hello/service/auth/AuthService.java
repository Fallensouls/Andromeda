package hello.service.auth;

import hello.service.security.JwtResponse;
import hello.service.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    public JwtResponse login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUser jwtUser = (JwtUser)userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(jwtUser);
        String refreshtoken = jwtTokenUtil.generateRefreshToken(jwtUser);
        userService.changeLastLogin(jwtUser.getUsername(),new Date()); // 修改最后登录时间
        return new JwtResponse(jwtUser.getUsername(),
                jwtUser.getTelphone(),
                jwtUser.getEmail(),
                jwtUser.getCrtdate(),
                jwtUser.getUpddate(),
                jwtUser.getLastlogin()
                ,new ArrayList<>(jwtUser.getAuthorities()),
                token, refreshtoken);
    }

    public String refresh(String RefreshToken) {
        final String token = RefreshToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromRefreshToken(token); // 如果refreshtoken失效，会直接返回401
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, jwtUser.getUpddate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    public String findEmail(String username){
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
        if(jwtUser != null){
            return jwtUser.getEmail();
        }
        return null;
    }

    public void changePassword(String username,String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userService.changePassword(username,encoder.encode(password));
        userService.changeUpddate(username,new Date());
    }
}

