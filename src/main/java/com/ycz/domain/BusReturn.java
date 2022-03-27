package com.ycz.domain;/*
 @author ycz
 @date 2021-07-15-21:47
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_return")
public class BusReturn implements Serializable {
    @Id
    private Integer id;

    /*
    车牌号
     */
    @NotEmpty(message = "车牌号不能为空")
    @Length(min = 7,max = 8,message = "车牌号为7-8位")
    private String num;
    /*
    出租ID
     */
    private Integer rentId;
    /*
    返还时间
     */
    private Date returnTime;
    /*
    资金价格
     */
    private Integer rentPrice;
    /*
    支付价格
     */
    @NotNull(message = "赔付金额不能为空")
    @Min(value = 0,message = "赔付金额不能为负数")
    private Integer payMoney;
    /*
    问题
     */
    private String problem;
    /*
    总价格
     */
    private Integer totalMoney;
    /*
    id
     */
    private Integer userId;
    /*
    创建时间
     */
    private Date createTime;
}
