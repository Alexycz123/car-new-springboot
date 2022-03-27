package com.ycz.mapper;/*
 @author ycz
 @date 2021-11-01-15:14
*/

import com.ycz.domain.SysUserMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMsgMapper {

    Integer insertMsg(SysUserMsg sysUserMsg);

    List<SysUserMsg> selectMsg(@Param("sendUserId")Integer sendUserId,@Param("receiveUserId")Integer receiveUserId
    ,             @Param("page") Integer page, @Param("limit")Integer limit
    );

    Integer selectMsgCount(@Param("sendUserId")Integer sendUserId,@Param("receiveUserId")Integer receiveUserId);

}
