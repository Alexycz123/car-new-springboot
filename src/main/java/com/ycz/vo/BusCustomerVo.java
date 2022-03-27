package com.ycz.vo;/*
 @author ycz
 @date 2021-06-26-15:36  
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.BusCustomer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCustomerVo extends BusCustomer {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updateTime;
}
