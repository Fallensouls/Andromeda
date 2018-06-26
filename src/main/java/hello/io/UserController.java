package hello.io;

import hello.service.JxAppService;
import hello.service.user.User;
import hello.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(JxAppService svc) {
        this.userService = svc.getUserService();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.GET)
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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public long getCount(@RequestParam(value = "name" ,required = false) String name,
                         @RequestParam(value = "objectstate" ,required = false) String objectstate,
                         @RequestParam(value = "crtbeg" ,required = false) String crtbeg,
                         @RequestParam(value = "crtend" ,required = false) String crtend){
        HashMap<String,Object> params=new HashMap<>();
        params.put("name",name);params.put("objectstate",objectstate);
        params.put("crtbeg",crtbeg);params.put("crtend",crtend);
        return userService.getCount(params);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.POST)
    public User addUser(@RequestBody User user){
        HashMap<String, Object> params = userService.getProperties(user);
        return userService.addRow(User.class,params);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = "id")String id){
        return userService.getRowById(id,User.class);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "id")String id){
        userService.removeRow(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public void updateUser(@PathVariable(value = "id")String id,
                              @RequestBody User user){
        HashMap<String, Object> params = userService.getProperties(user);
        userService.updateRow(id,params);
    }
}
