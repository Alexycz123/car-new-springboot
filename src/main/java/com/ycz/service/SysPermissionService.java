package com.ycz.service;




import com.ycz.common.Result;
import com.ycz.domain.SysPermission;
import com.ycz.query.SysPermissionQuery;

import java.util.List;

/**
 * @Description: 权限业务接口
 * @Author: Todd Ding
 * @Date 2020-12-04 20:13
 */
public interface SysPermissionService {
    /**
     * 获取当前用户的左侧菜单
     *
     * @return
     */
    Result currentLeftMenu();

    /**
     * 根据 ID 查询用户所有的权限标识
     *
     * @param id
     * @return
     */
    List<String> queryUserPermissionTags(Integer id);

    /**
     * 获取所有的权限数据
     *
     * @return
     */
    Result queryAll();

    /**
     * 权限列表的分页查询
     *
     * @param query
     * @return
     */
    Result queryPage(SysPermissionQuery query);

    /**
     * 新增权限
     *
     * @param sysPermission
     * @return
     */
    Result add(SysPermission sysPermission);

    /**
     * 修改权限
     *
     * @param sysPermission
     * @return
     */
    Result update(SysPermission sysPermission);

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    Result delete(Integer id);
}
