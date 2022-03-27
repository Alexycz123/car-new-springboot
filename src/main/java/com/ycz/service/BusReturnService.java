package com.ycz.service;/*
 @author ycz
 @date 2021-07-15-21:07
*/


import com.ycz.common.Result;
import com.ycz.domain.BusReturn;
import com.ycz.form.BusReturnForm;
import com.ycz.query.BusReturnQuery;

import java.text.ParseException;

public interface BusReturnService {

    Result addReturn(BusReturnForm form) throws ParseException;

    Result selectList(BusReturnQuery query);

    Result updateReturn(BusReturn busReturn);
}
