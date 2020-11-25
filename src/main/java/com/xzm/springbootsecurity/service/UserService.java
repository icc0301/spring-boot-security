package com.xzm.springbootsecurity.service;

import com.xzm.springbootsecurity.entity.UserEntity;

public interface UserService {

    UserEntity getOne(String username);
}
