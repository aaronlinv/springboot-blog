package com.al.blog.service;

import com.al.blog.dao.UserRepository;
import com.al.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findByUsernameandAndPassword(username, password);
        return user;
    }
}