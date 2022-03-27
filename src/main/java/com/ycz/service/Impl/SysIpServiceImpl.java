package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-10-18-14:40
*/

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.SysIp;
import com.ycz.mapper.SysIpMapper;
import com.ycz.query.SysIpQuery;
import com.ycz.service.SysIpService;
import com.ycz.vo.SysIpVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysIpServiceImpl implements SysIpService {

    @Autowired
    private SysIpMapper sysIpMapper;

    /**
     * 插入数据，走缓存
     * @param sysIp
     * @return
     */
    @Override
    @UpdateAnnotation(name = Constant.REDIS_SYSIP)
    public Result insert(SysIp sysIp) {
        sysIpMapper.insert(sysIp);
        return new Result();
    }

    @Override
    public Object queryPage(SysIpQuery query) {

        Page<SysIpVo> sysIpVos= PageHelper.startPage(query.getPage(),query.getLimit());

        sysIpMapper.selectList(query);

        return new Result(sysIpVos.toPageInfo());
    }


}
