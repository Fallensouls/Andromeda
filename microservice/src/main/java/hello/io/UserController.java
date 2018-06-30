package hello.io;

import hello.service.user.User;
import hello.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;


//    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUserList(@RequestParam(value = "name" ,required = false) String name,
                                     @RequestParam(value = "objectstate" ,required = false) String objectstate,
                                     @RequestParam(value = "crtbeg" ,required = false) String crtbeg,
                                     @RequestParam(value = "crtend" ,required = false) String crtend,
                                     @RequestParam(value = "offset",required = false,defaultValue = "0")int offset,
                                     @RequestParam(value = "limit",required = false,defaultValue = "50")int limit,
                                     @RequestParam(value = "order",required = false,defaultValue = "crtdate")String order,
                                     @RequestParam(value = "desc",required = false,defaultValue = "true")boolean desc){
        HashMap<String,Object> params=new HashMap<>();
        params.put("name",name);params.put("objectstate",objectstate);
        params.put("crtbeg",crtbeg);params.put("crtend",crtend);
        params.put("offset",offset);params.put("limit",limit);params.put("order",order);params.put("desc",desc);
        return  userService.findList(params,User.class);
    }

//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public long getCount(@RequestParam(value = "name" ,required = false) String name,
                         @RequestParam(value = "objectstate" ,required = false) String objectstate,
                         @RequestParam(value = "crtbeg" ,required = false) String crtbeg,
                         @RequestParam(value = "crtend" ,required = false) String crtend){
        HashMap<String,Object> params=new HashMap<>();
        params.put("name",name);params.put("objectstate",objectstate);
        params.put("crtbeg",crtbeg);params.put("crtend",crtend);
        return userService.getCount(params);
    }

//    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public User addUser(@RequestBody User user){
        HashMap<String, Object> params = userService.getProperties(user);
        String userid = (String)params.get("id");
        userService.addAuthForUser(userid,1); //新注册的用户设为普通用户
        return userService.addRowByUUID(User.class,params);
    }

//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @PostAuthorize("returnObject.username == principal.username or hasRole('ROLE_ADMIN')")
    public User getUserById(@PathVariable(value = "id")String id){
        return userService.getRowByUUID(id,User.class);
    }

//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable(value = "id")String id){
        userService.removeRowByUUID(id);
    }

//    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(@PathVariable(value = "id")String id,
                              @RequestBody User user){
        HashMap<String, Object> params = userService.getProperties(user);
        userService.updateRowByID(id,params);
    }
}
