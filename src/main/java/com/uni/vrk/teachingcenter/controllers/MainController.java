package com.uni.vrk.teachingcenter.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class MainController {

    @GetMapping("/home")
    public String showUser() {
        return "application works";
    }
}
