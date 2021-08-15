package com.demo.dubbo.provider.service.impl;

import com.demo.dubbo.api.service.HelloService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class HelloServiceImpl implements HelloService {

    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
