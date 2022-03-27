package com.ycz.vo;/*
 @author ycz
 @date 2021-10-18-14:39
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.SysIp;

import java.util.Date;

public class SysIpVo extends SysIp {

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastLoginTime;
}
