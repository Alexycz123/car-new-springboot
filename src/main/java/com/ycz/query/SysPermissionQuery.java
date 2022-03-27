package com.ycz.query;

import lombok.Data;

/**
 * @Description: 权限列表的查询参数封装类
 * @Author: Todd Ding
 * @Date 2020-12-05 17:43
 */
@Data
public class SysPermissionQuery extends Query {
    /**
     * 权限名称
     */
    private String title;
}
