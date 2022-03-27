package com.ycz.query;/*
 @author ycz
 @date 2022-01-15-21:14
*/

import lombok.Data;

@Data
public class SysIpQuery extends Query{
    private String loginAddress;
    private String ip;
}
