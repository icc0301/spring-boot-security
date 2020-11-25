package com.xzm.springbootsecurity.dao;

import com.xzm.springbootsecurity.entity.PermissionEntity;
import com.xzm.springbootsecurity.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PermissionDao {

    PermissionEntity queryById(Integer id);

}
