package com.ycz.domain;/*
 @author ycz
 @date 2021-06-26-15:31
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCustomer {
    /**
     * 客户id
     */
    @Id
    private Integer id;
    /**
     *客户姓名
     */
    private String name;
    /**
     * 客户手机号码
     */
    private String phone;
    /**
     * 客户地址
     */
    private String address;
    /**
     * 客户身份证
     */
    private String idCard;
    /**
     * 客户性别
     */
    private Integer sex;
    /**
     * 客户创建时间
     */
    private Date createTime;
    /**
     * 客户更新时间
     */
    private Date updateTime;
}
