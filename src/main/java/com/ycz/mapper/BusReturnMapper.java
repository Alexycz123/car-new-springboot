package com.ycz.mapper;/*
 @author ycz
 @date 2021-07-15-21:08
*/


import com.ycz.domain.BusReturn;
import com.ycz.form.BusReturnForm;
import com.ycz.query.BusReturnQuery;
import com.ycz.vo.BusReturnVo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Mapper
public interface BusReturnMapper extends tk.mybatis.mapper.common.Mapper<BusReturn>, MySqlMapper<BusReturn> {

    void insertReturn(BusReturnForm form);

    List<BusReturnVo> selectList(BusReturnQuery query);
}
