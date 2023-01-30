package com.rachel.logindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThyeleafController {

    @RequestMapping("/getDemoHtml")
    public String getDemoHtml() {
        //此处是需要展示的html在templates下的具体路径
        return "index";
    }

    @GetMapping("/login_page")
    public String getLoginPage() {
        return "login_page";
    }

}
