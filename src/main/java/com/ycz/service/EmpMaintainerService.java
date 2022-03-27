package com.ycz.service;/*
 @author ycz
 @date 2021-11-12-18:55
*/

import com.ycz.common.Result;
import com.ycz.domain.EmpMaintainer;
import com.ycz.query.EmpMaintainerQuery;

import java.util.List;

public interface EmpMaintainerService {
    Result selectList(EmpMaintainerQuery query);

    Object insert(EmpMaintainer empMaintainer);

    Object update(EmpMaintainer empMaintainer);

    Result deleteBatch(Integer id);
}
