package com.xxb.springboot.controller;

import com.xxb.springboot.dao.UserMapper;
import com.xxb.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/list")
    public List<User> sayHello(){
        List<User> userList = userMapper.selectList(null);
        return userList;
    }
}
