package com.fallensouls.client;

import com.fallensouls.common.domain.User;
import com.fallensouls.common.vo.UserPageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient("userservice")
public interface UserFeignClient {

    @GetMapping("/user")
    UserPageVO findUserByPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                             @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
                             @RequestParam(value = "order", required = false, defaultValue = "true") boolean desc,
                             @RequestParam(value = "orderby", required = false, defaultValue = "username") String orderby);

    @GetMapping("/user/{id}")
    User findUserById(@PathVariable String id);

    @PostMapping("/user")
    void addUser(@Valid @RequestBody User user, BindingResult bindingResult);

    @PutMapping("/{id}")
    void updateUser(@PathVariable String id, @Valid @RequestBody User user, BindingResult bindingResult);

    @DeleteMapping("/{id}")
    void lockUser(@PathVariable String id);
}
