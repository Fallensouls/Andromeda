package hello.service.security;

import java.util.List;
import java.util.stream.Collectors;

import hello.service.user.User;
import hello.service.user.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user,UserService userService) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getTelphone(),
                user.getEmail(),
                user.getRowstate(),
                user.getCrtdate(),
                user.getUpddate(),
                user.getLastlogin(),
                mapToGrantedAuthorities(userService.getAuthsByUser(user.getId()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
