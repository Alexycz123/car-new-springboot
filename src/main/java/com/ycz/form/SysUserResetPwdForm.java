package com.ycz.form;/*
 @author ycz
 @date 2021-06-28-14:45  
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserResetPwdForm {

    private Integer id;

    private String Password;

    @NotEmpty(message = "Password:密码不能为空")
    @Length(min = 6,max = 15,message = "Password:密码为6~15位字符")
    private String usedPassword;

    @NotEmpty(message = "newPassword:密码不能为空")
    @Length(min = 6,max = 15,message = "newPassword:新密码为6~15位字符")
    private String newPassword;

    @NotEmpty(message = "address:地址不能为空")
    @Length(max = 100,message = "address:地址最多100个字符")
    private String address;

}
