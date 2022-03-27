package com.ycz.controller;


import com.ycz.common.validator.ValidatorUtil;
import com.ycz.domain.SysRole;
import com.ycz.query.SysRoleQuery;
import com.ycz.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 角色控制类
 * @Author: Todd Ding
 * @Date 2020-12-04 16:25
 */
@RestController
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色列表的分页查询
     *
     * @param query
     * @return
     */
    @RequestMapping("page.do")
    public Object page(SysRoleQuery query) {
        return sysRoleService.queryPage(query);
    }

    /**
     * 新增角色信息
     *
     * @param sysRole
     * @return
     */
    @RequestMapping("add.do")
    public Object add(SysRole sysRole) {
        ValidatorUtil.validator(sysRole);
        return sysRoleService.add(sysRole);
    }

    /**
     * 修改角色信息
     *
     * @param sysRole
     * @return
     */
    @RequestMapping("update.do")
    public Object update(SysRole sysRole) {
        ValidatorUtil.validator(sysRole);
        return sysRoleService.update(sysRole);
    }

    /**
     * 获取所有的角色
     *
     * @return
     */
    @RequestMapping("all.do")
    public Object all() {
        return sysRoleService.queryAll();
    }

    /**
     * 根据用户 ID 查询用户的所有角色
     *
     * @param userId
     * @return
     */
    @RequestMapping("userRoles.do")
    public Object userRoles(@RequestParam("userId") Integer userId) {
        return sysRoleService.queryUserRoles(userId);
    }

    /**
     * 批量设置用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    @RequestMapping("setRole.do")
    public Object setRole(@RequestParam("id") Integer userId, @RequestParam("role") List<Integer> roleId) {
        return sysRoleService.insertUserRoles(userId, roleId);
    }

    /**
     * 根据角色 ID 查询所有的权限 ID
     *
     * @param id
     * @return
     */
    @RequestMapping("permissionIds.do")
    public Object permissionIds(@RequestParam("id") Integer id) {
        return sysRoleService.queryRolePermissionIds(id);
    }

    /**
     * 批量设置角色权限ID
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @RequestMapping("setRolePermission.do")
    public Object setRolePermission(@RequestParam("roleId") Integer roleId, @RequestParam("permissionId") List<Integer> permissionId) {
        return sysRoleService.addRolePermission(roleId, permissionId);
    }

}
