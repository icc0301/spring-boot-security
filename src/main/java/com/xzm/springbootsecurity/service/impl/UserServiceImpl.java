package com.xzm.springbootsecurity.service.impl;

import com.xzm.springbootsecurity.dao.UserDao;
import com.xzm.springbootsecurity.entity.UserEntity;
import com.xzm.springbootsecurity.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity getOne(String username) {

        return userDao.queryByUsername(username);
    }
}
