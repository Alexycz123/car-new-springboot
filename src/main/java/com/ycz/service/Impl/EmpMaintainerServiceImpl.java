package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-11-12-18:55
*/

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.EmpMaintainer;
import com.ycz.mapper.EmpMaintainerMapper;
import com.ycz.query.EmpMaintainerQuery;
import com.ycz.service.EmpMaintainerService;
import com.ycz.vo.EmpMaintainerVo;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EmpMaintainerServiceImpl implements EmpMaintainerService {
    @Autowired
    private EmpMaintainerMapper empMaintainerMapper;

    @Override
    @RedisAnnotation(name = Constant.REDIS_EMPMAINTAINER)
    public Result selectList(EmpMaintainerQuery query) {

        Page<EmpMaintainerVo> empMaintainerVos= PageHelper.startPage(query.getPage(),query.getLimit());
        empMaintainerMapper.selectList(query);

        return new Result(empMaintainerVos.toPageInfo());
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_EMPMAINTAINER)
    public Result insert(EmpMaintainer empMaintainer) {
        empMaintainer.setCreateTime(new Date());
        int i = empMaintainerMapper.insertSelective(empMaintainer);
        return new Result();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_EMPMAINTAINER)
    public Result update(EmpMaintainer empMaintainer) {
        empMaintainerMapper.updateByPrimaryKeySelective(empMaintainer);
        return new Result();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_EMPMAINTAINER)
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBatch(Integer id) {
        empMaintainerMapper.deleteByPrimaryKey(id);
        return new Result();
    }
}
