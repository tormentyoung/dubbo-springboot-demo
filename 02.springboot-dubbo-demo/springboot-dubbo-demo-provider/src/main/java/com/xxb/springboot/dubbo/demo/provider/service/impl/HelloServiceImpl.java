package com.xxb.springboot.dubbo.demo.provider.service.impl;

import com.xxb.springboot.dubbo.demo.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

@Component
@DubboService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
