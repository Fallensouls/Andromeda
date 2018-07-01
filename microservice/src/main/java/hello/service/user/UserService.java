package hello.service.user;

import hello.foundation.BasicService;
import hello.service.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends BasicService<User>{

    @Autowired
    public UserService(DataSource dataSource){
        this.setDataSource(dataSource);
        this.setTableName("role");
    }

    public HashMap<String,Object> getProperties(User user) {
        // 将user对象的值填充到key-value形式的HashMap内
        HashMap<String,Object> params=new HashMap<>();
        UUID id = UUID.randomUUID();
        user.setId(id);
        user.setUpddate(new Date());
        user.setCrtdate(new Date());
        params.put("id", user.getId().toString());
        params.put("username",user.getUsername());
        params.put("password",user.getPassword());
        params.put("telphone", user.getTelphone());
        params.put("email", user.getEmail());
        params.put("rowstate", String.valueOf(user.getRowstate()));
        params.put("crtdate",  user.getCrtdate().toString());
        params.put("upddate",  user.getUpddate().toString());
        return params;
    }

    public User findUserByUsername(String username){
        User user = null;
        try {
            user = this.getJdbcTemplate().queryForObject("SELECT * FROM role WHERE username=?",
                    new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        }
        catch (Exception e){
            System.out.println("未找到该用户！");
        }
        return user;
    }

    public List<String> getAuthsByUser(UUID userid) {
        List<Role> authlist = this.getJdbcTemplate().query("SELECT auth.id,auth.auth" +
                        " FROM auth, role_auth_relation " +
                        "WHERE auth.id=role_auth_relation.authid AND role_auth_relation.userid=?",
                new Object[]{userid}, new BeanPropertyRowMapper<>(Role.class));

        return authlist.stream().map(Role::getAuth).collect(Collectors.toList());
    }

    public void addAuthForUser(String userid, int authid){
        this.getJdbcTemplate().update("INSERT INTO role_auth_relation VALUES(?, ?)",
                UUID.fromString(userid), authid);
    }

    public void deleteAuthForUser(String userid, int authid){
        this.getJdbcTemplate().update("DELETE FROM role_auth_relation WHERE userid=? and authid=?",
                UUID.fromString(userid), authid);
    }
}
