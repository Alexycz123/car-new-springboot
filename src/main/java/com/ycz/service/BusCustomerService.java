package com.ycz.service;/*
 @author ycz
 @date 2021-06-26-15:44
*/


import com.ycz.common.Result;
import com.ycz.domain.BusCustomer;
import com.ycz.form.BusCustomerForm;
import com.ycz.query.BusCustomerQuery;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface BusCustomerService {
    Result selectCustomList(BusCustomerQuery query);

    Result updateCustomer(BusCustomerForm busCustomerForm);

    Result addCustomer(BusCustomerForm busCustomerForm);

    Result deleteCustomer(Integer id);

    /*
    导出客户
     */
    void exportCustomer(BusCustomerQuery query, HttpServletResponse response) throws IOException;

    Result batchAddCustomer(MultipartFile file) throws IOException;

    BusCustomer selectCustomerById(Integer id);
}
