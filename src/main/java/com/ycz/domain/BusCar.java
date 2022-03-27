package com.ycz.domain;/*
 @author ycz
 @date 2021-09-16-14:46
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCar {
    /**
     * 车辆id
     */
    private Integer id;
    /**
     * 车牌号
     */
    private String num;
    /**
     * 车辆类型
     */
    private Integer type;
    /**
     * 车辆颜色
     */
    private String color;
    /**
     * 车辆价格
     */
    private Integer price;
    /**
     * 车辆租金
     */
    private Integer rentPrice;
    /**
     * 车辆押金
     */
    private Integer desposit;
    /**
     * 是否已经租出去了
     */
    private Integer isRent;
    /**
     * 车辆描述
     */
    private String descp;
    /**
     * 车辆照片的地址
     */
    private String img;
    /**
     * 乐观锁
     */
    private String version;
    /**
     * 创建时间
     */
    private Date createTime;
}
