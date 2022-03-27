package com.ycz.form;/*
 @author ycz
 @date 2021-11-10-19:21
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsCompanyForm {

    private Integer id;

    @NotEmpty(message = "保险公司名字不能为空")
    @Length(min = 3,max = 20,message = "保险公司名字为2-20个字符")
    private String name;

    @NotEmpty(message = "公司地址不能为空")
    @Length(min = 2,max = 100,message = "公司地址为2-100个字符")
    private String address;
    @NotEmpty(message = "手机号码不能为空")
    @Length(min = 11,max = 11,message = "手机号码11位")
    private String phone;
    @NotEmpty(message = "投诉号码不能为空")
    @Length(min = 11,max = 11,message = "投诉号码为11位")
    private String complaintsPhone;
    private Integer version;
    private Integer deleted;

    private Date createTime;
}
