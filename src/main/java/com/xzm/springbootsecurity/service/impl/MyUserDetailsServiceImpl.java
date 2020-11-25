package com.xzm.springbootsecurity.service.impl;

import com.xzm.springbootsecurity.dao.UserRoleDao;
import com.xzm.springbootsecurity.entity.UserEntity;
import com.xzm.springbootsecurity.entity.UserRoleEntity;
import com.xzm.springbootsecurity.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final UserRoleDao userRoleDao; //文档刚开始的时候没有这个，可以注释掉，后面在来

    public MyUserDetailsServiceImpl(UserService userService, UserRoleDao userRoleDao) {
        this.userService = userService;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("------------------------------------------");
        System.out.println(s);
        UserEntity userEntity = userService.getOne(s);
        System.out.println(userEntity);
        if(userEntity==null)throw new UsernameNotFoundException(s);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 获取用户角色(暂时没有创建表的时候用)
        // authorities.add这个是添加角色的，如果刚开始没有，可以使用注释部分暂时跳过，后面表之类的都创建好了在来,注释部分的xzm是随意写的，只要是非空就好
        // authorities.add(new SimpleGrantedAuthority("xzm"));
        // 获取用户角色(正式使用)
        List<UserRoleEntity> userRoleEntityList = userRoleDao.quetyByUid(userEntity.getId());
        if(userRoleEntityList.size() > 0){
            userRoleEntityList.forEach(info->{
                authorities.add(new SimpleGrantedAuthority(info.getRole_id().toString())); // 这里我用角色ID替代角色名
            });
        }else{
            throw new UsernameNotFoundException("用户没有分配权限");
        }
        // 这边密码要处理一下，默认Security是加密后的
        String passwordAfterEncoder = new BCryptPasswordEncoder().encode(userEntity.getPassword());
        System.out.println(userEntity.getPassword());
        System.out.println(passwordAfterEncoder);
        // new User是Security下的一个类，authorities为角色
        return new User(userEntity.getUsername(),passwordAfterEncoder,authorities);
    }
}
