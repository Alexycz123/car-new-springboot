package com.ycz.vo;/*
 @author ycz
 @date 2021-06-26-12:58  
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.SysUser;
import lombok.Data;

import java.util.Date;


@Data
public class SysUserVo extends SysUser {


    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}
