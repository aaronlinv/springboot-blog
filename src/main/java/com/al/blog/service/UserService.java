package com.al.blog.service;

import com.al.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
