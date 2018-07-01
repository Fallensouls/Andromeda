package hello.service.role;

import hello.foundation.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;

@Service
public class RoleService extends BasicService<Role> {
    @Autowired
    public RoleService(DataSource dataSource){
        this.setDataSource(dataSource);
        this.setTableName("auth");
    }

    public HashMap<String,Object> getProperties(Role auth) {
        HashMap<String,Object> params=new HashMap<>();
        params.put("id", String.valueOf(auth.getId()));
        params.put("auth",auth.getAuth());
        return params;
    }
}
