package com.xxb.springboot.dubbo.demo.consumer.controller;

import com.xxb.springboot.dubbo.demo.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @DubboReference
    private HelloService helloService;

    @GetMapping("/sayHello")
    public String sayHello(String name){
        return helloService.sayHello(name);
    }
}
