package com.ycz.service;/*
 @author ycz
 @date 2021-10-18-14:40
*/

import com.ycz.common.Result;
import com.ycz.domain.SysIp;
import com.ycz.query.SysIpQuery;

public interface SysIpService {

    Result insert(SysIp sysIp);

    Object queryPage(SysIpQuery query);
}
