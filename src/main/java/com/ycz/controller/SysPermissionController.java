package com.ycz.controller;


import com.ycz.common.validator.ValidatorUtil;
import com.ycz.domain.SysPermission;
import com.ycz.query.SysPermissionQuery;
import com.ycz.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 权限控制类
 * @Author: Todd Ding
 * @Date 2020-12-04 20:08
 */
@RestController
@RequestMapping("permission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 获取当前用户的左侧菜单
     *
     * @return
     */
    @RequestMapping("leftMenu.do")
    public Object getUserMenu() {
        return sysPermissionService.currentLeftMenu();
    }

    /**
     * 获取所有的权限数据
     *
     * @return
     */
    @RequestMapping("all.do")
    public Object all() {
        return sysPermissionService.queryAll();
    }

    /**
     * 权限列表的分页查询
     *
     * @param query
     * @return
     */
    @RequestMapping("page.do")
    public Object page(SysPermissionQuery query) {
        return sysPermissionService.queryPage(query);
    }

    /**
     * 新增权限
     *
     * @param sysPermission
     * @return
     */
    @RequestMapping("add.do")
    public Object add(SysPermission sysPermission) {
        ValidatorUtil.validator(sysPermission);
        return sysPermissionService.add(sysPermission);
    }

    /**
     * 修改权限
     *
     * @param sysPermission
     * @return
     */
    @RequestMapping("update.do")
    public Object update(SysPermission sysPermission) {
        ValidatorUtil.validator(sysPermission);
        return sysPermissionService.update(sysPermission);
    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
    @RequestMapping("delete.do")
    public Object delete(Integer id) {
        return sysPermissionService.delete(id);
    }

}
