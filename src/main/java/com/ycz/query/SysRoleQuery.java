package com.ycz.query;

import lombok.Data;

/**
 * @Description: 角色列表查询参数的封装类
 * @Author: Todd Ding
 * @Date 2020-12-04 16:35
 */
@Data
public class SysRoleQuery extends Query {
    /**
     * 角色名称
     */
    private String name;
}
