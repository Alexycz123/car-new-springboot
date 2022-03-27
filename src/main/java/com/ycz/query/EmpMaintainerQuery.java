package com.ycz.query;/*
 @author ycz
 @date 2021-11-12-18:48
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpMaintainerQuery extends Query{
    private Integer id;
    private String name;
    private String address;

}
