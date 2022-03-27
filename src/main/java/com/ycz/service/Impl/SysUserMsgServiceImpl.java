package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-11-01-15:20
*/


import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.SysUserMsg;
import com.ycz.mapper.SysUserMsgMapper;
import com.ycz.service.SysUserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserMsgServiceImpl implements SysUserMsgService {

    @Autowired
    private SysUserMsgMapper sysUserMsgMapper;


    @Override
    public Result insert(SysUserMsg sysUserMsg) {
        sysUserMsgMapper.insertMsg(sysUserMsg);
        return new Result();
    }

    @Override
    public List<SysUserMsg> selectMsg(Integer sendUserId, Integer receiveUserId, Integer page,Integer limit) {
        return sysUserMsgMapper.selectMsg(sendUserId,receiveUserId,page,limit);
    }

    @Override
    public Integer selectMsgCount(Integer sendId, Integer receiveId) {
        return sysUserMsgMapper.selectMsgCount(sendId,receiveId);
    }


}
