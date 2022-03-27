package com.ycz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 用户实体类
 * @author: Todd Ding
 * @date 2020-11-30 14:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 登录名
     */
//    @NotEmpty(message = "loginName:登录名不能为空")
//    @Length(min = 6,max = 15,message = "loginName:登录名6~15位字符")
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 手机号
     //     */
//    @NotEmpty(message = "phone:手机号不能为空")
//    @Length(min = 11,max = 11,message = "phone:手机号11位字符")
    private String phone;

    /**
     * 真实姓名
     */
//    @NotEmpty(message = "realname:姓名不能为空")
//    @Length(max = 20,message = "realname:姓名最多20位字符")
    private String realname;

    /**
     * 身份证号
     //     */
//    @NotEmpty(message = "idCard:身份证号不能为空")
//    @Length(min=18,max = 18,message = "idCard:身份证号18位字符")
    private String idCard;

    /**
     * 性别  1 男  2 女
     */
//    @NotNull(message = "sex:性别不能为空")
//    @Range(min = 1,max = 2,message = "sex:性别只能是男(1)或者女(2)")
    private Integer sex;

    /**
     * 地址
     */
//    @NotEmpty(message = "address:地址不能为空")
//    @Length(max = 100,message = "address:地址最多100个字符")
    private String address;

    /**
     * 图像
     */
    private String img;

    /**
     * 创建时间
     */
//    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}