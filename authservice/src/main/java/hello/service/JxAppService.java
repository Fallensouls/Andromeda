//package hello.service;
//
//import hello.init.JxCustomConfigure;
//import hello.service.auth.AuthService;
//import hello.service.security.JwtTokenUtil;
//import hello.service.security.JwtUserDetailsService;
//import hello.service.start.StartService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.security.authentication.AuthenticationManager;
//
//import org.springframework.stereotype.Service;
//import javax.sql.DataSource;
//
//import hello.service.user.UserService;
//
//
//@Service("svc")
//@Scope("singleton")
//// 在此处注册所有的服务，以便被controller调用
//public class JxAppService {
//
//    private static final Logger log = LoggerFactory.getLogger(JxAppService.class);
//    private final JxCustomConfigure properties;
//    private final DataSource dataSource;
//    private final AuthenticationManager authenticationManager;
//
//    private UserService userService;
//    private AuthService authService;
//    private StartService startService;
//    private JwtUserDetailsService jwtUserDetailsService;
//
//    @Autowired
//    public JxAppService( JxCustomConfigure properties,DataSource dataSource,
//                         AuthenticationManager authenticationManager) {
//        this.dataSource = dataSource;
//        this.properties=properties;
//        this.authenticationManager = authenticationManager;
//    }
//
//    public JxCustomConfigure getProperties() {
//        return properties;
//    }
//
//
//    private UserService newUserService(){
//        UserService userService;
//        try{
//            userService = new UserService(dataSource);
//        }
//        catch (Exception e){
//            userService = null;
//        }
//        return userService;
//    }
//
//    public UserService getUserService(){
//        if (this.userService == null) {
//            this.userService = newUserService();
//        }
//        return this.userService;
//    }
//
//    private AuthService newAuthService(){
//        AuthService authService;
//        try{
//            authService = new AuthService(dataSource);
//        }
//        catch (Exception e){
//            authService=null;
//        }
//        return authService;
//    }
//
//    public AuthService getAuthService(){
//        if (this.authService == null) {
//            this.authService = newAuthService();
//        }
//        return this.authService;
//    }
//
//    private StartService newStartService(){
//        UserService userService = getUserService();
//        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
//        JwtUserDetailsService jwtUserDetailsService = getJwtUserDetailsService();
//        StartService startService;
//        try{
//            startService = new StartService(jwtUserDetailsService,authenticationManager,jwtTokenUtil,userService);
//        }catch (Exception e){
//            return null;
//        }
//        return startService;
//    }
//
//    public StartService getStartService() {
//        if(this.startService == null){
//            this.startService = newStartService();
//        }
//        return this.startService;
//    }
//
//    private JwtUserDetailsService newJwtUserDetailsService(){
//        JwtUserDetailsService jwtUserDetailsService;
//        try {
//            jwtUserDetailsService = new JwtUserDetailsService();
//        }catch (Exception e){
//            jwtUserDetailsService = null;
//        }
//        return jwtUserDetailsService;
//    }
//
//    public JwtUserDetailsService getJwtUserDetailsService(){
//        if(this.jwtUserDetailsService == null){
//            this.jwtUserDetailsService = newJwtUserDetailsService();
//        }
//        return this.jwtUserDetailsService;
//    }
//}