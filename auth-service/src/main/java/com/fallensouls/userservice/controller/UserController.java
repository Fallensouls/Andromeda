package com.fallensouls.userservice.controller;

import com.fallensouls.userservice.domain.User;
import com.fallensouls.userservice.exception.SaveUserDetailsFailureException;
import com.fallensouls.userservice.exception.UserNotFoundException;
import com.fallensouls.userservice.repository.UserRepository;
import com.fallensouls.userservice.vo.UserPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(value = "/user")
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public UserPageVO findUserByPage(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
                                     @RequestParam(value = "order", required = false, defaultValue = "true") boolean desc,
                                     @RequestParam(value = "orderby", required = false, defaultValue = "username") String orderby){
        Sort.Direction direction = desc? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, offset, direction, orderby);
        log.info("正在查询所要求的用户信息");
        Page<User> userPage = userRepository.findAll(pageable);
        return new UserPageVO(userPage.getTotalElements(), userPage.getTotalPages(), userPage.getContent());
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

//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable String id){
//        log.info("删除id为:{}的用户", id);
//        userRepository.deleteById(UUID.fromString(id));
//    }

    @DeleteMapping("/{id}")
    public void lockUser(@PathVariable String id){
        log.info("锁定id为:{}的用户", id);
        userRepository.lockUserById(UUID.fromString(id));
    }
}
