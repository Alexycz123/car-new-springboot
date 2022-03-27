package com.ycz.query;/*
 @author ycz
 @date 2021-07-10-22:58  
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRentQuery extends Query{
    /**
     * 根据身份证
     *
     */
    private String idCard;
    /*
    类型
     */
    private Integer type;
    /*
    车牌号
     */
    private String num;
    /*
    最大租金价格
     */
    private Integer maxRentPrice;
    /*
      最小租金价格
         */
    private Integer minRentPrice;

    private Integer flag;

    private Integer id;

    private Integer minDesposit;

    private Integer maxDesposit;

    private String beginTime;

    private String minBeginTime;

    private String maxBeginTime;
}
