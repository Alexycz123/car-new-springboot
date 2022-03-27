package com.ycz.vo;/*
 @author ycz
 @date 2021-07-15-21:09
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.BusReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusReturnVo extends BusReturn {

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date returnTime;
}
