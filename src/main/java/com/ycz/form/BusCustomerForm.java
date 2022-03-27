package com.ycz.form;/*
 @author ycz
 @date 2021-06-29-13:26  
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCustomerForm {

    private Integer id;

    @NotEmpty(message = "姓名必须填写")
    @Length(min = 2,max = 20,message = "姓名为2-20个字符")
    private String name;

    @NotEmpty(message = "phone:手机号不能为空")
    @Length(min = 11,max = 11,message = "phone:手机号11位字符")
    private String phone;

    @NotEmpty(message = "idCard:身份证号不能为空")
    @Length(min=18,max = 18,message = "idCard:身份证号18位字符")
    private String idCard;

    @NotNull(message = "sex:性别不能为空")
    @Range(min = 1,max = 2,message = "sex:性别只能是男(1)或者女(2)")
    private Integer sex;

    @NotEmpty(message = "address:地址不能为空")
    @Length(max = 100,message = "address:地址最多100个字符")
    private String address;

    private Date createTime;

    private Date updateTime;
}
