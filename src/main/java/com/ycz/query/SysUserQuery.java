package com.ycz.query;

import lombok.Data;

/**
 * @Description: 用户列表查询参数的封装类
 * @Author: Todd Ding
 * @Date 2020-12-01 17:50
 */

@Data
public class SysUserQuery extends Query {//搜索的Form
    /**
     * 用户姓名
     */
    private String realname;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户地址
     */
    private String address;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 性别
     */
    private Integer sex;
}