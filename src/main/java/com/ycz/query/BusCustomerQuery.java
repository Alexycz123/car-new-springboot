package com.ycz.query;/*
 @author ycz
 @date 2021-06-26-15:47  
*/

import lombok.Data;

@Data
public class BusCustomerQuery extends Query{
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户地址
     */
    private String address;
    /*
    用户性别
     */
    private Integer sex;

    private String idCard;


}
