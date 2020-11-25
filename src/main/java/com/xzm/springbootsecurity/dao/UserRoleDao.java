package com.xzm.springbootsecurity.dao;

import com.xzm.springbootsecurity.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRoleDao {

    List<UserRoleEntity> quetyByUid(Integer uid);
}
