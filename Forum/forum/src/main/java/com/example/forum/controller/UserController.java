package com.example.forum.controller;

import com.example.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.forum.entity.User;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login/{id}/{password}")
    @ResponseBody
    public User login(@PathVariable("id")Integer id,@PathVariable("password")String password){
        System.out.println(id);
        System.out.println(password);
        return userService.login(id,password);
    }

    @RequestMapping("/loginByEmail/{email}/{password}")
    @ResponseBody
    public User loginByEmail(@PathVariable("email")String email,@PathVariable("password")String password){
        return userService.loginByEmail(email,password);
    }

    @RequestMapping("/register")
    public String register(@RequestBody User user){
        return userService.register(user);
    }

    @RequestMapping("/getMessage/{id}")
    @ResponseBody
    public User getMessage(@PathVariable("id")Integer id){
        return userService.getMessage(id);
    }

    @RequestMapping("/updateMessage")
    public String updateMessage(@RequestBody User user){
        return userService.updateMessage(user);
    }

    @RequestMapping("/checkEmail/{email}")
    public String checkEmail(@PathVariable("email") String email){
        return userService.checkEmail(email);
    }
}
