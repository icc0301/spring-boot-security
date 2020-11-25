package com.xzm.springbootsecurity.dao;

import com.xzm.springbootsecurity.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RolePermissionDao {

    List<RolePermissionEntity> queryByRoleId(Integer role_id);
}
