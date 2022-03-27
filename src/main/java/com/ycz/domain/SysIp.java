package com.ycz.domain;/*
 @author ycz
 @date 2021-10-18-14:37
*/


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysIp {

    private Integer id;

    private String ip;

    private String loginAddress;

    private Integer userId;

    private String userLoginName;

    private Date lastLoginTime;

}
