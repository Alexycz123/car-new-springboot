package com.ycz.vo;/*
 @author ycz
 @date 2021-07-11-21:05
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.BusRent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRentVo extends BusRent {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
}
