package com.xzm.springbootsecurity.service.impl;

import com.xzm.springbootsecurity.entity.UserEntity;
import com.xzm.springbootsecurity.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public MyUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("------------------------------------------");
        System.out.println(s);
        UserEntity userEntity = userService.getOne(s);
        System.out.println(userEntity);
        if(userEntity==null)throw new UsernameNotFoundException(s);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 测试需要，这边手动写一个，角色这个是权限认证的，后面说权限控制的时候会说
        authorities.add(new SimpleGrantedAuthority("admin"));
        // 这边密码要处理一下，默认Security是加密后的
        String passwordAfterEncoder = new BCryptPasswordEncoder().encode(userEntity.getPassword());
        System.out.println(userEntity.getPassword());
        System.out.println(passwordAfterEncoder);
        // new User是Security下的一个类，authorities为角色
        return new User(userEntity.getUsername(),passwordAfterEncoder,authorities);
    }
}
