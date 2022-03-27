package com.ycz.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.SysRole;
import com.ycz.mapper.SysRoleMapper;
import com.ycz.query.SysRoleQuery;
import com.ycz.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 角色业务接口实现
 * @Author: Todd Ding
 * @Date 2020-12-04 16:26
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 角色列表的分页查询
     *
     * @param query
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_ROLE)
    public Result queryPage(SysRoleQuery query) {
        Page<SysRole> sysRolePage = PageHelper.startPage(query.getPage(), query.getLimit());
        sysRoleMapper.selectList(query);
        return new Result(sysRolePage.toPageInfo());
    }

    /**
     * 新增角色信息
     *
     * @param sysRole
     * @return
     */
    @Override
    @UpdateAnnotation(name= Constant.REDIS_ROLE)
    public Result add(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
        return new Result();
    }

    /**
     * 修改角色信息
     *
     * @param sysRole
     * @return
     */
    @Override
    @UpdateAnnotation(name= Constant.REDIS_ROLE)
    public Result update(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
        return new Result();
    }

    /**
     * 获取所有的角色
     *
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_ROLE)
    public Result queryAll() {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        return new Result(sysRoles);
    }

    /**
     * 根据用户 ID 查询用户的所有角色
     *
     * @param userId
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_ROLE)
    public Result queryUserRoles(Integer userId) {
        List<SysRole> roles = sysRoleMapper.selectListByUserId(userId);
        return new Result(roles);
    }

    /**
     * 批量设置用户角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name= Constant.REDIS_ROLE)
    public Result insertUserRoles(Integer userId, List<Integer> roleId) {
        // 删除用户的所有角色关系
        sysRoleMapper.deleteUserRole(userId);
        // 新增用户角色关系
        sysRoleMapper.batchInsertUserRoles(userId, roleId);
        return new Result();
    }

    /**
     * 根据 ID 查询用户所有角色标识
     *
     * @param id
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_ROLE)
    public List<String> queryUserRolesTag(Integer id) {
        List<SysRole> sysRoles = sysRoleMapper.selectListByUserId(id);
        List<String> roleTags = new ArrayList<>();
        sysRoles.forEach(role -> {
            roleTags.add(role.getTag());
        });
        return roleTags;
    }

    /**
     * 根据角色 ID 查询所有的权限 ID
     *
     * @param id
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_ROLE)
    public Result queryRolePermissionIds(Integer id) {
        List<Integer> pIds = sysRoleMapper.selectPermissionIds(id);
        return new Result(pIds);
    }

    /**
     * 批量设置角色权限ID
     *
     * @param roleId
     * @param permissionId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name= Constant.REDIS_ROLE)
    public Result addRolePermission(Integer roleId, List<Integer> permissionId) {
        // 根据角色ID 删除当前角色权限关系
        sysRoleMapper.deleteRolePermRel(roleId);
        // 新增角色权限关系
        sysRoleMapper.batchInsertRolePermRel(roleId, permissionId);
        return new Result();
    }
}
