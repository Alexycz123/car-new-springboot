package com.ycz.service;/*
 @author ycz
 @date 2021-07-10-22:59
*/



import com.ycz.common.Result;
import com.ycz.domain.BusRent;
import com.ycz.form.BusRentForm;
import com.ycz.query.BusRentQuery;

import java.text.ParseException;

public interface BusRentService {
    Result addRent(BusRentForm busRentForm);

    Result selectRentList(BusRentQuery query);

    Result edit(BusRentForm form) throws ParseException;


}
