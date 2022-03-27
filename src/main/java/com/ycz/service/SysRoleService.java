package com.ycz.service;



import com.ycz.common.Result;
import com.ycz.domain.SysRole;
import com.ycz.query.SysRoleQuery;

import java.util.List;

/**
 * @Description: 角色业务接口
 * @Author: Todd Ding
 * @Date 2020-12-04 16:26
 */
public interface SysRoleService {
    /**
     * 角色列表的分页查询
     *
     * @param query
     * @return
     */
    Result queryPage(SysRoleQuery query);

    /**
     * 新增角色信息
     *
     * @param sysRole
     * @return
     */
    Result add(SysRole sysRole);

    /**
     * 修改角色信息
     *
     * @param sysRole
     * @return
     */
    Result update(SysRole sysRole);

    /**
     * 获取所有的角色
     *
     * @return
     */
    Result queryAll();

    /**
     * 根据用户 ID 查询用户的所有角色
     *
     * @param userId
     * @return
     */
    Result queryUserRoles(Integer userId);

    /**
     * 批量设置用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    Result insertUserRoles(Integer userId, List<Integer> roleId);

    /**
     * 根据 ID 查询用户所有角色标识
     *
     * @param id
     * @return
     */
    List<String> queryUserRolesTag(Integer id);

    /**
     * 根据角色 ID 查询所有的权限 ID
     *
     * @param id
     * @return
     */
    Result queryRolePermissionIds(Integer id);

    /**
     * 批量设置角色权限ID
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    Result addRolePermission(Integer roleId, List<Integer> permissionId);
}
