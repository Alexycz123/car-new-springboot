package com.ycz.form;/*
 @author ycz
 @date 2021-07-03-20:22  
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusCarForm {

    private Integer id;//汽车ID

    @NotEmpty(message = "车牌号不能为空")
    @Length(min = 7,max = 8,message = "车牌号7-8位")
    private String num;

    @NotNull(message = "车型不能为空")
    @Range(min = 1,max = 3,message = "车辆类型只能1-3")
    private Integer type;

    @NotEmpty(message = "汽车颜色不能为空")
    @Length(max = 10,message = "汽车颜色最多10位字符")
    private String color;

    @NotEmpty(message = "汽车图片不能为空")
    @Length(max=100,message = "汽车图片地址最多100位字符")
    private String img;

    @NotNull(message = "汽车金额不能为空")
    @Range(min = 1,max = 999999999,message = "汽车金额1-9999999999")
    private Integer price;

    @NotNull(message = "汽车租金不能为空")
    @Range(min = 1,max = 999999999,message = "汽车租金1-9999999999")
    private Integer rentPrice;

    @NotNull(message = "汽车押金不能为空")
    @Range(min = 1,max = 999999999,message = "汽车押金1-999999999")
    private String desposit;

    @Length(max = 100,message = "汽车描述最多100个字符")
    private String descp;

    private Integer isRent;

    private Date createTime;

    private String version;
}
