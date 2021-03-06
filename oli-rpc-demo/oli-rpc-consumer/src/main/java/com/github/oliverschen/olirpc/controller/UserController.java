package com.github.oliverschen.olirpc.controller;

import com.github.oliverschen.olirpc.annotaion.OliRefer;
import com.github.oliverschen.olirpc.api.UserService;
import com.github.oliverschen.olirpc.entity.User;
import com.github.oliverschen.olirpc.remote.refer.OliBus;
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
    private OliBus oliBus;
    @OliRefer
    private UserService userService;

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") Integer id) {
        UserService service = oliBus.create(UserService.class);
        return service.findById(id);
    }

    @GetMapping("/user")
    public User user() {
        return userService.findById(1);
    }
}
