package com.ycz.query;/*
 @author ycz
 @date 2021-07-17-16:31  
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusReturnQuery extends Query{
    private Integer id;
    private String num;
    private Integer minRentPrice;
    private Integer maxRentPrice;
}
