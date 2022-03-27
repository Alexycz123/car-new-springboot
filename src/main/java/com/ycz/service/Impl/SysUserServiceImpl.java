package com.ycz.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.SysUser;
import com.ycz.form.SysUserForm;
import com.ycz.form.SysUserResetPwdForm;
import com.ycz.mapper.SysUserMapper;
import com.ycz.query.SysUserQuery;
import com.ycz.service.SysUserService;
import com.ycz.vo.SysUserVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户业务接口实现类
 * @author: Todd Ding
 * @date 2020-11-30 14:50
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_USER)
    public Result queryUser(String username, String password) {
        SysUser sysUser = sysUserMapper.selectUserByNameAndPassword(username, password);
        if (sysUser == null) {
            return new Result(CodeMsg.USER_USER_PASSWORD_ERROR);
        }
        return new Result(sysUser);
    }

    @Override
    @RedisAnnotation(name = Constant.REDIS_USER)
    public Result queryPage(SysUserQuery query) {
        //开启分页查询
        Page<SysUserVo> sysUserVOPage = PageHelper.startPage(query.getPage(), query.getLimit());
        //根据参数查询用户列表
        sysUserMapper.selectList(query);
        return new Result(sysUserVOPage.toPageInfo());
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_USER)
    public Result add(SysUserForm sysUserForm) {
        SysUserQuery query=new SysUserQuery();

        query.setLoginName(sysUserForm.getLoginName());

        SysUserVo sysUserVo= sysUserMapper.selectUserByPhone_loginName_idCard(query);
        if (sysUserVo!=null){
            return new Result(CodeMsg.USER_LOGIN_NAME_EXIST_ERROR);
        }

        query=new SysUserQuery();
        query.setPhone(sysUserForm.getPhone());
        sysUserVo=sysUserMapper.selectUserByPhone_loginName_idCard(query);
        if (sysUserVo!=null){
            return new Result(CodeMsg.CUSTOMER_PHONE_EXIST_ERROR);
        }

        query=new SysUserQuery();
        query.setIdCard(sysUserForm.getIdCard());
        sysUserVo=sysUserMapper.selectUserByPhone_loginName_idCard(query);
        if (sysUserVo!=null){
            return new Result(CodeMsg.USER_ID_CARD_EXIST_ERROR);
        }

        Md5Hash md5Hash = new Md5Hash(Constant.DEFAULT_PASSWORD, Constant.MD5_SALT, 2);
        sysUserForm.setLoginPassword(md5Hash.toString());

        sysUserMapper.insertUser(sysUserForm);

        return new Result();

    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_USER)
    public Result resetPaaword(Integer id) {
        Md5Hash md5Hash = new Md5Hash(Constant.DEFAULT_PASSWORD, Constant.MD5_SALT, 2);
        sysUserMapper.resetPassword(id,md5Hash.toString());
        return new Result();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_USER)
    public Result deleteUser(Integer id) {
        sysUserMapper.deleteUser(id);
        return new Result();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_USER)
    public Result updatePassword(SysUserResetPwdForm sysUserResetPwdForm) {
        System.out.println("进来了IMPL 的修改密码");
        /*
        判断是否新密码与旧密码相同
         */
        if (sysUserResetPwdForm.getPassword().equals(sysUserResetPwdForm.getUsedPassword())){//新密码与旧密码
            return new Result(CodeMsg.USEDPASSWORD_equal_NEWPASSWORD);
        }
        /*
        判断新密码之间是否相同
         */
        if (!sysUserResetPwdForm.getPassword().equals(sysUserResetPwdForm.getNewPassword())){
            return new Result(CodeMsg.PASSWORD_NO_EQUAL_NEWPASSWORD);
        }else {
             /*
        获取原来的密码
         */
            String usedPassword= sysUserMapper.selectPasswordByID(sysUserResetPwdForm.getId());

            System.out.println("数据库原来的密码是"+usedPassword);
        /*
        将sysUserResetPwdFrom原来的密码加密看看是否一样
         */
            Md5Hash md5Hash = new Md5Hash(sysUserResetPwdForm.getUsedPassword(), Constant.MD5_SALT, 2);

            System.out.println("用户输入的原来密码为"+md5Hash.toString());

            if (usedPassword.equals(md5Hash.toString())){
                System.out.println("两个旧密码之间相同，然后插入新密码即可");
            /*
            将新密码加密
             */
                Md5Hash md5Hash2 = new Md5Hash(sysUserResetPwdForm.getPassword(), Constant.MD5_SALT, 2);
                System.out.println("修改后的密码加密后"+md5Hash2.toString());
                /*
                更新新密码
                 */
                sysUserMapper.updatePassword(sysUserResetPwdForm.getId(),md5Hash2.toString());

            }
        }


        return new Result();
    }

    @Override
//    @RedisAnnotation(name = Constant.REDIS_USER)
    public SysUser queryUserByName(String username) {
        return sysUserMapper.queryUserByName(username);
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_USER)
    public void deleteBatchUserByIds(List<Integer> ids) {
        sysUserMapper.deleteBatchUserByIds(ids);
    }


    /**
     * 用户的分页查询
     *
     * @param query
     * @return
     */

//
//    /**
//     * 新增用户
//     *
//     * @param sysUser
//     * @return
//     */
//    @Override
//    public Result add(SysUser sysUser) {
//        // 进行业务数据校验
//        SysUserQuery query = new SysUserQuery();
//        // 校验用户名是否重复
//        query.setLoginName(sysUser.getLoginName());
//        // 根据登录名查询用户
//        SysUser user = sysUserMapper.selectUserByKeywords(query);
//        if (user != null) {
//            return new Result(CodeMsg.USER_LOGIN_NAME_EXIST_ERROR);
//        }
//
//        // 校验手机号是否重复
//        query = new SysUserQuery();
//        query.setPhone(sysUser.getPhone());
//        // 根据手机号查询用户
//        user = sysUserMapper.selectUserByKeywords(query);
//        if (user != null) {
//            return new Result(CodeMsg.USER_PHONE_EXIST_ERROR);
//        }
//
//        // 校验身份证号是否重复
//        query = new SysUserQuery();
//        query.setIdCard(sysUser.getIdCard());
//        // 根据身份证号查询用户
//        user = sysUserMapper.selectUserByKeywords(query);
//        if (user != null) {
//            return new Result(CodeMsg.USER_ID_CARD_EXIST_ERROR);
//        }
//
//        // 新增用户
//        // 获取加密后的默认密码
//        Md5Hash md5Hash = new Md5Hash(Constant.DEFAULT_PASSWORD, Constant.MD5_SALT, 2);
//        sysUser.setLoginPassword(md5Hash.toString());
//        sysUserMapper.insert(sysUser);
//        return new Result();
//    }
//
//    /**
//     * 重置用户密码
//     *
//     * @param id
//     * @return
//     */
//    @Override
//    public Result resetPassword(Integer id) {
//        Md5Hash md5Hash = new Md5Hash(Constant.DEFAULT_PASSWORD, Constant.MD5_SALT, 2);
//        sysUserMapper.updatePassword(id, md5Hash.toString());
//        return new Result();
//    }
//
//    /**
//     * 修改密码
//     *
//     * @param id
//     * @param newPassword
//     * @return
//     */
//    @Override
//    public Result updatePassword(Integer id, String newPassword) {
//        Md5Hash md5Hash = new Md5Hash(newPassword, Constant.MD5_SALT, 2);
//        sysUserMapper.updatePassword(id, md5Hash.toString());
//        return new Result();
//    }
}
