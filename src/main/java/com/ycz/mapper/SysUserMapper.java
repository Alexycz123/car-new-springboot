package com.ycz.mapper;

import com.ycz.domain.SysUser;
import com.ycz.form.SysUserForm;
import com.ycz.query.SysUserQuery;
import com.ycz.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 用户数据操作接口
 * @author: Todd Ding
 * @date 2020-11-30 14:50
 */
@Mapper
public interface SysUserMapper {
    /**
     * 根据用户名或者密码查询用户
     *
     * @param username
     * @param
     * @return
     */

    //   select
    //        <include refid="Base_Column_List"/>
    //        from sys_user
    //        where  login_name=#{username}
    SysUser queryUserByName(@Param("username") String username);
    SysUser selectUserByNameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据参数查询用户列表
     *
     * @param query
     * @return
     */
    List<SysUserVo> selectList(SysUserQuery query);


    SysUserVo selectUserByPhone_loginName_idCard(SysUserQuery query);

    int insertUser(SysUserForm sysUserForm);

    int resetPassword(@Param("id") Integer id, @Param("password") String password);

    int deleteUser(@Param("id") Integer id);

    String selectPasswordByID(@Param("id")int id);

    void deleteBatchUserByIds(List<Integer> ids);
    int updatePassword(@Param("id")Integer id,@Param("password")String password);
//    /**
//     * 校验用户关键字是否重复
//     *
//     * @param query 包含 登录名/手机号/身份证号
//     * @return
//     */
//    SysUser selectUserByKeywords(SysUserQuery query);
//
//    /**
//     * 新增用户
//     *
//     * @param sysUser
//     */
//    void insert(SysUser sysUser);
//
//    /**
//     * 修改用户密码
//     *
//     * @param id
//     * @param password
//     */
//    void updatePassword(@Param("id") Integer id, @Param("password") String password);
}
