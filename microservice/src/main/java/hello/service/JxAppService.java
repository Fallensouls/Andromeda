package hello.service;

import hello.init.JxCustomConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

import hello.service.user.UserService;


@Service("svc")
@Scope("singleton")
// 在此处注册所有的服务，以便被controller调用
public class JxAppService {

    private static final Logger log = LoggerFactory.getLogger(JxAppService.class);
    private final JxCustomConfigure properties;
    private final DataSource dataSource;

//    private JxNodeService node;
//    private JxMobileIoServiceNew mio;
//    private TagService tagService;
    private UserService userService;


    @Autowired
    public JxAppService( JxCustomConfigure properties,DataSource dataSource) {
        this.dataSource = dataSource;
        this.properties=properties;
    }

    public JxCustomConfigure getProperties() {
        return properties;
    }

    private UserService newUserService(){
        UserService userService=new UserService();
        try{
            userService.init(dataSource);
        }
        catch (Exception e){
            userService=null;
        }
        return userService;
    }

    public UserService getUserService(){
        if (this.userService == null) {
            this.userService = newUserService();
        }
        return this.userService;
    }

}