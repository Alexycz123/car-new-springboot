package com.ycz.mapper;/*
 @author ycz
 @date 2021-10-18-14:41
*/

import com.ycz.domain.SysIp;
import com.ycz.query.SysIpQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysIpMapper {

    void insert(SysIp sysIp);

    List<SysIp> selectList(SysIpQuery query);
}
