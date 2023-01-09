package com.rachel.logindemo.controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @GetMapping("/admin/hello")
    public String admin() {
        System.out.println("执行admin方法。。。");
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        System.out.println("执行user方法。。。");
        return "hello user";
    }

    @GetMapping("/db/hello")
    public String db() {
        System.out.println("执行db方法。。。");
        return "hello db";
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("执行hello方法。。。");
        return "hello";
    }

}
