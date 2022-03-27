package com.ycz.mapper;


import com.ycz.domain.SysPermission;
import com.ycz.query.SysPermissionQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 权限数据操作接口
 * @Author: Todd Ding
 * @Date 2020-12-04 20:14
 */
@Mapper
public interface SysPermissionMapper {
    /**
     * 根据用户 ID 和 权限类型查询权限
     *
     * @param userId
     * @param type
     * @return
     */
    List<SysPermission> selectUserPermission(@Param("userId") Integer userId, @Param("type") Integer type);

    /**
     * 根据条件查询权限
     *
     * @param sysPermissionQuery
     * @return
     */
    List<SysPermission> selectList(SysPermissionQuery sysPermissionQuery);

    /**
     * 新增权限
     *
     * @param sysPermission
     */
    void insert(SysPermission sysPermission);

    /**
     * 修改权限
     *
     * @param sysPermission
     */
    void update(SysPermission sysPermission);

    /**
     * 根据父权限 ID 查询所有子权限 ID
     *
     * @param ids
     * @return
     */
    List<Integer> selectAllChildId(@Param("ids") List<Integer> ids);

    /**
     * 批量删除权限
     *
     * @param ids
     */
    void batchDelete(@Param("ids") List<Integer> ids);

    /**
     * 批量删除角色权限关系
     *
     * @param ids
     */
    void batchDeleteRel(@Param("ids") List<Integer> ids);
}
