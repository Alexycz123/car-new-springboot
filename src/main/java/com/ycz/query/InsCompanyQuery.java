package com.ycz.query;/*
 @author ycz
 @date 2021-10-21-15:40
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InsCompanyQuery extends Query{
    private Integer id;
    private String name;//保险公司姓名查找
    private String address;
    private String phone;
    private String complaintsPhone;
}
