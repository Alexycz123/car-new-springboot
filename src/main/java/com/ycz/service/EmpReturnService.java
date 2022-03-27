package com.ycz.service;

import com.ycz.common.Result;
import com.ycz.domain.EmpReturn;
import com.ycz.query.EmpReturnQuery;
import com.ycz.vo.EmpReturnVo;

import java.text.ParseException;


/**
 * (EmpReturn)表服务接口
 *
 * @author makejava
 * @since 2021-12-22 18:42:45
 */
public interface EmpReturnService {


    Object insertEmpReturn(Integer empId, Integer returnId, String maintainTime) throws ParseException;

    Object queryPage(EmpReturnQuery query);

    Result update(EmpReturnVo empReturnVo);
}
