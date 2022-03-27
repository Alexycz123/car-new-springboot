package com.ycz.domain;/*
 @author ycz
 @date 2021-06-25-14:38
 */
//当前用户的包装类

import lombok.Data;

import java.util.List;

@Data
public class ActiveUser {

    //当前认证的用户
    private SysUser sysUser;
    private String realname;
//    private String phone;
    private List<String> roles;
    //用户所有的权限
    private List<String> permissions;


}
