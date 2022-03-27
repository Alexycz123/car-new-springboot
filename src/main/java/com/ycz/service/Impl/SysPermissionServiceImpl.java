package com.ycz.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.ActiveUser;
import com.ycz.domain.SysPermission;
import com.ycz.mapper.SysPermissionMapper;
import com.ycz.query.SysPermissionQuery;
import com.ycz.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description: 权限业务接口实现
 * @Author: Todd Ding
 * @Date 2020-12-04 20:13
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 获取当前用户的左侧菜单
     *
     * @return
     */
    @Override
    @RedisAnnotation(name=Constant.REDIS_PERMISSION)
    public Result currentLeftMenu() {
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        Integer userId = activeUser.getSysUser().getId();
        //查询出用户所有的菜单
        List<SysPermission> sysPermissions = sysPermissionMapper.selectUserPermission(userId, Constant.PERMISSION_TYPE_MENU);
        // 将数组 转化 父子关系
        Map<Integer, SysPermission> menu = new HashMap<>();

        long start = System.currentTimeMillis();
        for (SysPermission sysPermission : sysPermissions) {
            //获取父菜单ID 如果这个ID 值为 0 说明是一级菜单
            Integer parentId = sysPermission.getParentId();
            if (parentId.equals(Constant.MENU_LV1)) {
                //初始化一级菜单及其子菜单容器
                sysPermission.setChildren(new ArrayList<SysPermission>());
                menu.put(sysPermission.getId(), sysPermission);
            }
        }

        //遍历所有菜单 为1级菜单设置2级菜单
        for (SysPermission sysPermission : sysPermissions) {
            //获取菜单的父菜单
            Integer parentId = sysPermission.getParentId();
            //判断一级菜单容器中是否有当前菜单的父菜单
            if (menu.containsKey(parentId)) {
                //获取对应的父菜单
                SysPermission sysPermissionLv1 = menu.get(parentId);
                //将当前菜单放入到对应的父菜单的子容器中
                sysPermissionLv1.getChildren().add(sysPermission);

            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "毫秒");
        //获取map中所有的一级菜单的集合
        Collection<SysPermission> values = menu.values();
        //返回数据
        return new Result(values);
    }

    /**
     * 根据 ID 查询用户所有的权限标识
     *
     * @param id
     * @return
     */
    @Override
    @RedisAnnotation(name=Constant.REDIS_PERMISSION)
    public List<String> queryUserPermissionTags(Integer id) {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectUserPermission(id, null);
        List<String> permissionTags = new ArrayList<>();
        sysPermissions.forEach(sysPermission -> {
            permissionTags.add(sysPermission.getTag());
        });
        return permissionTags;
    }

    /**
     * 获取所有的权限数据
     *
     * @return
     */
    @Override
    @RedisAnnotation(name=Constant.REDIS_PERMISSION)
    public Result queryAll() {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectList(null);
        return new Result(sysPermissions);
    }

    /**
     * 权限列表的分页查询
     *
     * @param query
     * @return
     */
    @Override
    @RedisAnnotation(name=Constant.REDIS_PERMISSION)
    public Result queryPage(SysPermissionQuery query) {
        Page<SysPermission> sysPermissionPage = PageHelper.startPage(query.getPage(), query.getLimit());
        sysPermissionMapper.selectList(query);
        return new Result(sysPermissionPage.toPageInfo());
    }

    /**
     * 新增权限
     *
     * @param sysPermission
     * @return
     */
    @Override
    @UpdateAnnotation(name=Constant.REDIS_PERMISSION)
    public Result add(SysPermission sysPermission) {
        sysPermissionMapper.insert(sysPermission);
        return new Result();
    }

    /**
     * 修改权限
     *
     * @param sysPermission
     * @return
     */
    @Override
    @UpdateAnnotation(name=Constant.REDIS_PERMISSION)
    public Result update(SysPermission sysPermission) {
        sysPermissionMapper.update(sysPermission);
        return new Result();
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name=Constant.REDIS_PERMISSION)
    public Result delete(Integer id) {
        /**
         *  1. 将所有子权限查询出来
         *  2. 根据当前权限 ID 和 子权限 ID 统一删除
         *  3. 根据当前权限 ID 和 子权限 ID 将相应的角色权限关系表数据删除
         */
        // 待删除的当前权限 ID 和 子权限 ID
        List<Integer> ids = new ArrayList<>();
        // 存放当前权限 ID
        ids.add(id);  // 一级权限
        // 根据当前权限 ID 查询子权限 ID
        // 二级权限
        List<Integer> childIdLv2 = sysPermissionMapper.selectAllChildId(ids);

        // 三级权限
        List<Integer> childIdLv3 = new ArrayList<>();
        if (!childIdLv2.isEmpty()) {
            childIdLv3 = sysPermissionMapper.selectAllChildId(childIdLv2);
        }
        // 将二级菜单数据合并
        ids.addAll(childIdLv2);
        // 将三级菜单数据合并
        ids.addAll(childIdLv3);
        // 批量删除权限
        sysPermissionMapper.batchDelete(ids);
        // 批量删除角色权限关系
        sysPermissionMapper.batchDeleteRel(ids);
        return new Result();
    }
}
