package com.clipboarder.clipboarder.controller;

import com.clipboarder.clipboarder.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @GetMapping
    public String test(){
        return "TEST";
    }
}
