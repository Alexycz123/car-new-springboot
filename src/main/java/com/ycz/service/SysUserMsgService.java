package com.ycz.service;/*
 @author ycz
 @date 2021-11-01-15:20
*/

import com.ycz.domain.SysUserMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMsgService {

     Object insert(SysUserMsg sysUserMsg);

     List<SysUserMsg> selectMsg(@Param("sendUserId")Integer sendUserId, @Param("receiveUserId")Integer receiveUserId,
                                @Param("page") Integer page, @Param("limit")Integer limit
                                );

     Integer selectMsgCount(Integer sendId, Integer receiveId);
}
