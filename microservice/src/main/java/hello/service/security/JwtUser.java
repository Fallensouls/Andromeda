package hello.service.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class JwtUser implements UserDetails{
    private UUID id;
    private String username;
    private String password;
    private String telphone;
    private String email;
    private short rowstate;  //定义默认值为0
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date crtdate; //创建日期
    private Date upddate; //修改日期
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            UUID id,
            String username,
            String password,
            String telphone,
            String email,
            short rowstate,
            Date crtdate,
            Date upddate,
            Collection<? extends GrantedAuthority> authorities
            ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telphone = telphone;
        this.rowstate = rowstate;
        this.crtdate = crtdate;
        this.upddate = upddate;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getTelphone() {
        return telphone;
    }

    public String getEmail() {
        return email;
    }

    public short getRowstate() {
        return rowstate;
    }

    public Date getCrtdate() {
        return crtdate;
    }

    public Date getUpddate() {
        return upddate;
    }
}
