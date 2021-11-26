package com.zzl.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 进入 主页
 *
 * @author makejava
 * @since 2021-01-22 11:28:06
 */
@Controller
public class CarController {

    @GetMapping("/car")
    public String sayHello(Model model) {
        //相当于访问 /templates/hello.html
        return "index";
    }

    @GetMapping("/chat")
    public String chat(Model model) {
        //相当于访问 /templates/hello.html
        return "chat";
    }
}