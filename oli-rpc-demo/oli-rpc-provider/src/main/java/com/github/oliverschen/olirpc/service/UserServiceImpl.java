package com.github.oliverschen.olirpc.service;

import com.github.oliverschen.olirpc.annotaion.OliService;
import com.github.oliverschen.olirpc.api.UserService;
import com.github.oliverschen.olirpc.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author ck
 */
@OliService(name = "userService")
public class UserServiceImpl implements UserService {

    @Override
    public User findById(Integer id) {
        User u = new User();
        u.setId(id);
        u.setUsername("HHH");
        u.setAge(12);
        return u;
    }
}
