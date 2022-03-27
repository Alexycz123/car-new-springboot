package com.ycz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "emp_return")
@Accessors(chain = true)
public class EmpReturn{
    @Id
    private Integer id;

    private Integer empId;

    private Integer returnId;

    private Integer version;

    private Integer deleted;

    private Date beginTime;

    private Date endTime;
    @Transient
    private String problem;
    @Transient
    private EmpMaintainer empMaintainer;
    @Transient
    private BusReturn busReturn;

}

