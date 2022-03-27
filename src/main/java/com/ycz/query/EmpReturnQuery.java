package com.ycz.query;

import com.ycz.domain.BusReturn;
import com.ycz.domain.EmpMaintainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (EmpReturn)实体类
 *
 * @author makejava
 * @since 2021-12-22 18:42:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmpReturnQuery extends Query {

    private Integer id;

    private Integer empId;

    private Integer returnId;

    private Integer version;

    private Integer deleted;


    private Date beginTime;
    private Date endTime;

    private EmpMaintainer empMaintainer;

    private BusReturn busReturn;


}

