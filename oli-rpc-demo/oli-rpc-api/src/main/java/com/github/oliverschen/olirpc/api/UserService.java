package com.github.oliverschen.olirpc.api;

import com.github.oliverschen.olirpc.entity.User;

/**
 * @author ck
 */
public interface UserService {

    User findById(Integer id);

}
