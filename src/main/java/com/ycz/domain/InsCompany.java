package com.ycz.domain;/*
 @author ycz
 @date 2021-10-21-15:35
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

/**
 * 保险公司表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsCompany {
    /*
    保险公司id
     */
    @Id
    private Integer id;

    private String name;

    private String address;
    private String phone;

    private String complaintsPhone;
    private Integer version;
    private Integer deleted;

    private Date createTime;


}
