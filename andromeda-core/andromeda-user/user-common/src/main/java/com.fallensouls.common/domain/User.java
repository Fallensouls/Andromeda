package com.fallensouls.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@GenericGenerator(name = "jpa-uuid", strategy = "uuid2")
public class User {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private UUID id;
    @Pattern(regexp = "^[a-zA-Z0-9_]+$" , message = "用户名只能包含字母、数字以及下划线")
    private String username;
    @Pattern(regexp = "^[\\x21-\\x7e]{5,16}$", message = "密码长度应在5-16位之间")
    private String password;
    @Pattern(regexp = "^[0-9]{11}$", message = "电话号码应为仅包含0-9在内的11位数字")
    private String telphone;
    @Email(message = "邮件地址必须符合格式")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @CreatedDate
    private Date crtdate; //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @LastModifiedDate
    private Date upddate; //修改日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastlogin; //最后登录时间

    private boolean islocked; // 账号是否被锁定

    private String role;  // 用户的角色


}
