package com.ycz.query;/*
 @author ycz
 @date 2021-07-02-19:58
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCarQuery extends Query{
    private Integer id;
    private String num;
    private String color;

    private Integer type;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minRentPrice;
    private Integer maxRentPrice;
    private String descp;
    private Integer isRent;
}
