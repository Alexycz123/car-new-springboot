package com.ycz.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.BusReturn;
import com.ycz.domain.EmpReturn;
import com.ycz.mapper.BusReturnMapper;
import com.ycz.mapper.EmpReturnMapper;
import com.ycz.query.EmpReturnQuery;
import com.ycz.service.EmpReturnService;
import com.ycz.vo.EmpReturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * (EmpReturn)表服务实现类
 *
 * @author makejava
 * @since 2021-12-22 18:42:45
 */
@Service("empReturnService")
public class EmpReturnServiceImpl implements EmpReturnService {

    @Autowired
    private EmpReturnMapper empReturnMapper;

    @Autowired
    private BusReturnMapper returnMapper;


    @Override
    @UpdateAnnotation(name = Constant.REDIS_EMPRETURN)
    public Object insertEmpReturn(Integer empId, Integer returnId, String maintainTime) throws ParseException {

        String[] maintainArr = maintainTime.split("~");
        EmpReturnVo empReturnVo=new EmpReturnVo();

        empReturnVo.setEmpId(empId).setReturnId(returnId).
                setBeginTime(new SimpleDateFormat("yyyy-MM-dd").parse(maintainArr[0]))
        .setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse(maintainArr[1]))
        ;
        System.out.println(empReturnVo);

        empReturnMapper.insertSelective(empReturnVo);
        return new Result();
    }

    @Override
    @RedisAnnotation(name = Constant.REDIS_EMPRETURN)
    public Object queryPage(EmpReturnQuery query) {
        Page<EmpReturnVo> returnVoPage = PageHelper.startPage(query.getPage(), query.getLimit());
        empReturnMapper.selectList(query);
        System.out.println(returnVoPage.toPageInfo());
        return returnVoPage.toPageInfo();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_EMPRETURN)
    @Transactional(rollbackFor = Exception.class)
    public Result update(EmpReturnVo empReturnVo) {
        empReturnMapper.updateByPrimaryKeySelective(empReturnVo);
        BusReturn busReturn=new BusReturn();
        busReturn.setId(empReturnVo.getReturnId());
        busReturn.setProblem(empReturnVo.getProblem());
        System.out.println(busReturn);
        returnMapper.updateByPrimaryKeySelective(busReturn);
        return new Result();
    }
}
