package com.ycz.service;/*
 @author ycz
 @date 2021-10-21-16:11
*/

import com.ycz.common.Result;
import com.ycz.form.InsCompanyForm;
import com.ycz.query.InsCompanyQuery;

public interface InsCompanyService {

    Result selectList(InsCompanyQuery insCompanyQuery);

    Result addIns(InsCompanyForm form);

    Result updateIns(InsCompanyForm form);
}
