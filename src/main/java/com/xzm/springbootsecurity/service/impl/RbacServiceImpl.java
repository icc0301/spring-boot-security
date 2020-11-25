package com.xzm.springbootsecurity.service.impl;

import com.xzm.springbootsecurity.dao.PermissionDao;
import com.xzm.springbootsecurity.dao.RolePermissionDao;
import com.xzm.springbootsecurity.dao.UserDao;
import com.xzm.springbootsecurity.dao.UserRoleDao;
import com.xzm.springbootsecurity.entity.PermissionEntity;
import com.xzm.springbootsecurity.entity.RolePermissionEntity;
import com.xzm.springbootsecurity.entity.UserEntity;
import com.xzm.springbootsecurity.entity.UserRoleEntity;
import com.xzm.springbootsecurity.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private final UserDao userDao;
    private final UserRoleDao userRoleDao;
    private final RolePermissionDao rolePermissionDao;
    private final PermissionDao permissionDao;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public RbacServiceImpl(UserDao userDao, UserRoleDao userRoleDao, RolePermissionDao rolePermissionDao, PermissionDao permissionDao) {
        this.userDao = userDao;
        this.userRoleDao = userRoleDao;
        this.rolePermissionDao = rolePermissionDao;
        this.permissionDao = permissionDao;
    }

    @Override
    public Boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 获取当前用户的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        AtomicReference<Boolean> isEnd = new AtomicReference<>(false);
        if(username!=null && !username.equals("anonymousUser")){
            // 获取角色List
            UserEntity userEntity = userDao.queryByUsername(username);
            List<UserRoleEntity> userRoleEntityList = userRoleDao.quetyByUid(userEntity.getId());
            System.out.println(userRoleEntityList);
            if(userRoleEntityList.size() > 0){
                userRoleEntityList.forEach(val->{
                    // 获取角色权限关联表
                    System.out.println("角色ID为：" + val.getRole_id());
                    List<RolePermissionEntity> rolePermissionEntity = rolePermissionDao.queryByRoleId(val.getRole_id());
                    if(rolePermissionEntity.size() > 0){
                        rolePermissionEntity.forEach(info->{
                            PermissionEntity permissionEntity = permissionDao.queryById(info.getPermission_id());
                            System.out.println("permissionEntity.getPath()");
                            System.out.println(permissionEntity.getPath());
                            System.out.println(request.getRequestURI());
                            if (antPathMatcher.match(permissionEntity.getPath(), request.getRequestURI())) {
                                isEnd.set(true);
                            }
                        });
                    }
                });
            }
        }
        return isEnd.get();
    }
}
