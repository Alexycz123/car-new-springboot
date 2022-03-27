package com.ycz.mapper;


import com.ycz.domain.SysRole;
import com.ycz.query.SysRoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 角色数据操作接口
 * @Author: Todd Ding
 * @Date 2020-12-04 16:27
 */
@Mapper
public interface SysRoleMapper {
    /**
     * 根据参数查询角色列表
     *
     * @param query
     * @return
     */
    List<SysRole> selectList(SysRoleQuery query);

    /**
     * 新增角色信息
     *
     * @param sysRole
     */
    void insert(SysRole sysRole);

    /**
     * 修改角色信息
     *
     * @param sysRole
     */
    void update(SysRole sysRole);

    /**
     * 根据用户 ID 查询用户的所有角色
     *
     * @param userId
     * @return
     */
    List<SysRole> selectListByUserId(@Param("userId") Integer userId);

    /**
     * 删除用户角色关系
     *
     * @param userId
     */
    void deleteUserRole(@Param("userId") Integer userId);

    /**
     * 新增用户角色关系
     *
     * @param userId
     * @param roleId
     */
    void batchInsertUserRoles(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleId);

    /**
     * 根据角色 ID 查询所有的权限 ID
     *
     * @param id
     * @return
     */
    List<Integer> selectPermissionIds(@Param("roleId") Integer id);

    /**
     * 根据角色ID 删除当前角色权限关系
     *
     * @param roleId
     */
    void deleteRolePermRel(@Param("roleId") Integer roleId);

    /**
     * 新增角色权限关系
     *
     * @param roleId
     * @param permissionId
     */
    void batchInsertRolePermRel(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionId);

}
