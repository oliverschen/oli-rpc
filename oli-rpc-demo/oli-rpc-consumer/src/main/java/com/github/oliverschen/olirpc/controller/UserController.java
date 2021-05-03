package com.github.oliverschen.olirpc.controller;

import com.github.oliverschen.olirpc.api.UserService;
import com.github.oliverschen.olirpc.client.OliRpc;
import com.github.oliverschen.olirpc.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ck
 */
@RestController
public class UserController {

    @Value("${oli.url}")
    private String url;

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") Integer id) {
        UserService userService = OliRpc.create(UserService.class, url,User.class);
        return userService.findById(id);
    }
}
