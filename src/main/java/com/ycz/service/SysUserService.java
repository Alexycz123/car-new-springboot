package com.ycz.service;


import com.ycz.common.Result;
import com.ycz.domain.SysUser;
import com.ycz.form.SysUserForm;
import com.ycz.form.SysUserResetPwdForm;
import com.ycz.query.SysUserQuery;

import java.util.List;

/**
 * @Description: 用户业务接口类
 * @author: Todd Ding
 * @date 2020-11-30 14:50
 */
public interface SysUserService {

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    Result queryUser(String username, String password);

    /**
     * 用户的分页查询
     *
     * @param query
     * @return
     */
    Result queryPage(SysUserQuery query);

    Result add(SysUserForm sysUserForm);

    /*
    重置密码

     */
    Result resetPaaword(Integer id);

    Result deleteUser(Integer id);

    Result updatePassword(SysUserResetPwdForm sysUserResetPwdForm);

    SysUser queryUserByName(String username);

    void deleteBatchUserByIds(List<Integer> ids);


}
