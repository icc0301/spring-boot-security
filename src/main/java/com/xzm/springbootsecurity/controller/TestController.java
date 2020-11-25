package com.xzm.springbootsecurity.controller;

import com.xzm.springbootsecurity.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(){

        System.out.println(userService.getOne("admin"));

        return "欢迎来到小猪迷Security";
    }

    @GetMapping("/admin")
    public String admin(){
        return "我是admin";
    }
}
