package com.fallensouls.userservice.controller;

import com.fallensouls.userservice.domain.User;
import com.fallensouls.userservice.exception.SaveUserDetailsFailureException;
import com.fallensouls.userservice.exception.UserNotFoundException;
import com.fallensouls.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(value = "/user")
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> findAllUser(){
        log.info("正在查询所有用户信息");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable String id) throws Exception{
        log.info("正在查询id为:{}的用户", id);
        Optional<User> user = userRepository.findById(UUID.fromString(id));
        if(user.isPresent()){
            return user.get();
        }else {
            log.error("id为{}的用户不存在", id);
            throw new UserNotFoundException("该用户不存在");
        }
    }

    @PostMapping
    public void addUser(@Valid @RequestBody User user, BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            log.error("添加新用户失败，失败原因为: {}", bindingResult.getFieldError().getDefaultMessage());
            throw new SaveUserDetailsFailureException("添加新用户失败");
        }
        log.info("正在添加新用户的信息");
        userRepository.save(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @Valid @RequestBody User user, BindingResult bindingResult) throws Exception{
        if(!userRepository.existsById(UUID.fromString(id))){
            log.error("要修改信息的用户不存在");
            throw new UserNotFoundException("该用户不存在");
        }
        if(bindingResult.hasErrors()){
            log.error("更新用户信息失败，失败原因为: {}", bindingResult.getFieldError().getDefaultMessage());
            throw new SaveUserDetailsFailureException("更新用户信息失败");
        }
        log.info("正在更新用户信息");
        userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        log.info("删除id为:{}的用户", id);
        userRepository.deleteById(UUID.fromString(id));
    }
}
