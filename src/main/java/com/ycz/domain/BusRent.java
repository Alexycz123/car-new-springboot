package com.ycz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 汽车出租
 * @author: Mr.T
 * @date 2020-12-02 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRent {
    /**
     * 出租记录ID
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String num;

    /**
     * 车型  1 轿车 2 SUV  3跑车
     */
    private Integer type;

    /**
     * 租金
     */
    private Integer rentPrice;

    /**
     * 押金
     */
    private Integer desposit;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 客户身份证号
     */
    private String idCard;

    /**
     * 计划租车开始时间
     */
    private String beginTime;

    /**
     * 计划租车的结束时间
     */
    private String endTime;

    /**
     * 状态  1 未还车  2 已还车
     */
    private Integer flag;

    /**
     * 业务员ID
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
