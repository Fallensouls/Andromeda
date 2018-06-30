package hello.service.auth;

import hello.foundation.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;

@Service
public class AuthService extends BasicService<Auth> {
    @Autowired
    public AuthService(DataSource dataSource){
        this.setDataSource(dataSource);
        this.setTableName("auth");
    }

    public HashMap<String,Object> getProperties(Auth auth) {
        HashMap<String,Object> params=new HashMap<>();
        params.put("id", String.valueOf(auth.getId()));
        params.put("auth",auth.getAuth());
        return params;
    }
}
