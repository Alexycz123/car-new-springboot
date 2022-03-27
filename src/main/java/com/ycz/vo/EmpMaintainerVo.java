package com.ycz.vo;/*
 @author ycz
 @date 2021-11-12-18:44
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.EmpMaintainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpMaintainerVo extends EmpMaintainer {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
}
