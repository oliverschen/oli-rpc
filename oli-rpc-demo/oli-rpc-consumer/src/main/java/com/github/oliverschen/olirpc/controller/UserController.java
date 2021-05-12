package com.github.oliverschen.olirpc.controller;

import com.github.oliverschen.olirpc.api.UserService;
import com.github.oliverschen.olirpc.entity.User;
import com.github.oliverschen.olirpc.refer.OliRefer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ck
 */
@RestController
public class UserController {

    @Autowired
    private OliRefer oliRefer;

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") Integer id) {
        UserService userService = oliRefer.create(UserService.class, User.class);
        return userService.findById(id);
    }
}
