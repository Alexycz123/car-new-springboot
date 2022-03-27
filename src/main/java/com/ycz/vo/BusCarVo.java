package com.ycz.vo;/*
 @author ycz
 @date 2021-07-02-19:50
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.BusCar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_car")
public class BusCarVo extends BusCar {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}
