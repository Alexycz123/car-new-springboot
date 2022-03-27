package com.ycz.domain;/*
 @author ycz
 @date 2021-11-12-18:44
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpMaintainer {
    @Id
    private Integer id;
    @NotEmpty(message = "姓名不能为空")
    @Length(min = 2,max = 20,message = "姓名为2-20个字符")
    private String name;
    private Integer sex;
    @NotEmpty(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号为11位")
    private String phone;
    @NotEmpty(message = "idCard:身份证号不能为空")
    @Length(min=18,max = 18,message = "idCard:身份证号18位字符")
    private String idCard;
    @NotEmpty(message = "地址不能为空")
    @Length(max = 100,message = "地址长度为100个字符")
    private String address;
    private Integer version;
    private Integer deleted;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
}
