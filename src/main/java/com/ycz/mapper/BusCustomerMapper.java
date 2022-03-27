package com.ycz.mapper;/*
 @author ycz
 @date 2021-06-26-15:34
*/


import com.ycz.domain.BusCustomer;
import com.ycz.form.BusCustomerForm;
import com.ycz.query.BusCustomerQuery;
import com.ycz.vo.BusCustomerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Mapper
public interface BusCustomerMapper extends tk.mybatis.mapper.common.Mapper<BusCustomer>, MySqlMapper<BusCustomer> {
    List<BusCustomerVo> selectCustomList(BusCustomerQuery query);

    BusCustomerVo selectCustomer_idCard_phone(BusCustomerQuery query);

    int insertBusCustomer(BusCustomerForm busCustomerForm);

    int deleteCustomer(@Param("id") Integer id);

    int updateCustomer(BusCustomerForm busCustomerForm);

    BusCustomerVo selectCustomer(BusCustomerForm busCustomerForm);

    int batchInsert(@Param("customers") List<BusCustomerVo> busCustomerVoList);

    BusCustomerVo selectCustomer_idCard(@Param("idCard")String idCard);
}
