package com.ycz.mapper;

import com.ycz.query.EmpReturnQuery;
import com.ycz.vo.BusReturnVo;
import com.ycz.vo.EmpReturnVo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * (EmpReturn)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-22 18:42:44
 */

@Mapper
public interface EmpReturnMapper extends tk.mybatis.mapper.common.Mapper<EmpReturnVo>, MySqlMapper<EmpReturnVo> {


    List<BusReturnVo> selectList(EmpReturnQuery empReturnQuery);
}

