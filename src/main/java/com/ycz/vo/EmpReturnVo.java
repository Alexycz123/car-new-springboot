package com.ycz.vo;/*
 @author ycz
 @date 2021-12-22-18:44
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.EmpReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emp_return")
@Accessors(chain = true)
public class EmpReturnVo extends EmpReturn {

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date beginTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;
}
