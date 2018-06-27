package hello.service.user;

import hello.foundation.BasicService;

import javax.sql.DataSource;
import java.util.*;

public class UserService extends BasicService<User>{

    public void init(DataSource dataSource){
        this.setDataSource(dataSource);
        this.setTableName("role");
    }

    public HashMap<String,Object> getProperties(User user) {
        // 将user对象的值填充到key-value形式的HashMap内
        HashMap<String,Object> params=new HashMap<>();
        UUID id = UUID.randomUUID();
        user.setId(id);
        params.put("id", user.getId().toString());
        params.put("username",user.getUsername());
        params.put("password",user.getPassword());
        params.put("telphone", user.getTelphone());
        params.put("email", user.getEmail());
        params.put("rowstate", String.valueOf(user.getRowstate()));
        params.put("crtdate",  user.getCrtdate().toString());
        return params;
    }
}
