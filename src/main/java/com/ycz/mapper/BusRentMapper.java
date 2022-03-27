package com.ycz.mapper;/*
 @author ycz
 @date 2021-07-10-22:58
*/


import com.ycz.form.BusRentForm;
import com.ycz.query.BusRentQuery;
import com.ycz.vo.BusRentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusRentMapper {
    void insertRent(BusRentForm busRentForm);

    List<BusRentVo> selectList(BusRentQuery query);

    BusRentVo selectRentByNum(BusRentQuery busRentQuery);

    int updateRentFlag(@Param("id") Integer rentId, @Param("oldFlag") Integer oldFlag, @Param("flag") Integer flag);

    BusRentVo selectRentByID(@Param("id") Integer id);

    void updateRent(BusRentForm form);
}
