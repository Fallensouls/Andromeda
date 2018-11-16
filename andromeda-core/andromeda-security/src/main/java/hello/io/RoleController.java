package hello.io;

import hello.service.role.RoleService;
import hello.service.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public void addRole(@RequestBody Role auth){
        HashMap<String, Object> params = roleService.getProperties(auth);
        roleService.addRowByID(Role.class,params);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(@PathVariable(value = "id")String id){
        roleService.removeRowByID(id);
    }

}
