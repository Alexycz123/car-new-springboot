package com.ycz.mapper;/*
 @author ycz
 @date 2021-11-12-18:49
*/

import com.ycz.domain.EmpMaintainer;
import com.ycz.query.EmpMaintainerQuery;
import com.ycz.vo.EmpMaintainerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Mapper
public interface EmpMaintainerMapper extends tk.mybatis.mapper.common.Mapper<EmpMaintainer>, MySqlMapper<EmpMaintainer> {

    List<EmpMaintainerVo> selectList(EmpMaintainerQuery query);

    Integer selectCount(EmpMaintainerQuery query);

    void deleteBatchByIds(@Param("ids") List<Integer> ids);
}
