package hello.io;

import hello.service.auth.AuthService;
import hello.service.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public Auth addAuth(@RequestBody Auth auth){
        HashMap<String, Object> params = authService.getProperties(auth);
        return authService.addRowByID(Auth.class,params);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAuth(@PathVariable(value = "id")String id){
        authService.removeRowByID(id);
    }

}
