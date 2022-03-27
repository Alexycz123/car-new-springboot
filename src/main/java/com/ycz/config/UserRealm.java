package com.ycz.config;/*
 @author ycz
 @date 2021-07-30-23:42
*/


import com.ycz.domain.ActiveUser;
import com.ycz.domain.SysUser;
import com.ycz.service.SysPermissionService;
import com.ycz.service.SysRoleService;
import com.ycz.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//自定义的UserRealm    extends AuthorizingRealm
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证功能");

        UsernamePasswordToken userToken=(UsernamePasswordToken) authenticationToken;
        SysUser sysUser=sysUserService.queryUserByName(userToken.getUsername());
        System.out.println(sysUser);
        if (sysUser==null){
            return null; //抛出异常 ，UnknownAccountException
        }

        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("loginUser",sysUser);


        List<String> roleTags = sysRoleService.queryUserRolesTag(sysUser.getId());
        // 根据用户ID 查询用户所有的权限标识
        List<String> permissionTags = sysPermissionService.queryUserPermissionTags(sysUser.getId());

        ActiveUser activeUser = new ActiveUser();
        activeUser.setSysUser(sysUser);
        activeUser.setRealname(sysUser.getRealname());
        activeUser.setRoles(roleTags);
        activeUser.setPermissions(permissionTags);

        log.info(String.valueOf(activeUser));
        //传值user到上面
        return new SimpleAuthenticationInfo(activeUser,sysUser.getLoginPassword(),sysUser.getRealname());
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("开始执行授权功能");

        //授权
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        List<String> roles = activeUser.getRoles();
        List<String> permissions = activeUser.getPermissions();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        log.info("此角色有啥角色"+simpleAuthorizationInfo.getRoles());
        log.info("授权完成");
        //增加user:add的授权功能
        // info.addStringPermission("user:add");
        // info.addStringPermission(currentUser.getPerms());
        //       return info;
        //        return info;
        return simpleAuthorizationInfo;
    }

}
