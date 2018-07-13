package hello.service.security;

import org.springframework.security.core.GrantedAuthority;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

public class JwtResponse {
    private String username;
    private String telphone;
    private String email;
    private String crtdate;
    private String upddate;
    private String lastlogin;
    private String token;
    private String refreshtoken;
    private List<GrantedAuthority> authorities;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public JwtResponse(String username,String telphone, String email, Date crtdate, Date upddate, Date lastlogin,List<GrantedAuthority> authorities,String token, String refreshtoken){
        this.username = username;
        this.telphone = telphone;
        this.email = email;
        String crttime = formatter.format(crtdate);
        String updtime = formatter.format(upddate);
        String lastlogintime = formatter.format(lastlogin);
        this.crtdate = crttime;
        this.upddate = updtime;
        this.lastlogin = lastlogintime;
        this.authorities = authorities;
        this.token = token;
        this.refreshtoken = refreshtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getTelphone() {
        return telphone;
    }

    public String getEmail() {
        return email;
    }

    public String getCrtdate() {
        return crtdate;
    }

    public String getUpddate() {
        return upddate;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
