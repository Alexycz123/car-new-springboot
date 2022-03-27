package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-07-15-21:07
*/

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.common.exception.BussiException;
import com.ycz.domain.ActiveUser;
import com.ycz.domain.BusReturn;
import com.ycz.form.BusReturnForm;
import com.ycz.mapper.BusCarMapper;
import com.ycz.mapper.BusRentMapper;
import com.ycz.mapper.BusReturnMapper;
import com.ycz.query.BusCarQuery;
import com.ycz.query.BusReturnQuery;
import com.ycz.service.BusReturnService;
import com.ycz.vo.BusCarVo;
import com.ycz.vo.BusRentVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BusReturnServiceImpl implements BusReturnService {
    @Autowired
    private BusRentMapper busRentMapper;
    @Autowired
    private BusCarMapper busCarMapper;
    @Autowired
    private BusReturnMapper busReturnMapper;
    /**
     * 还车
     * @param form
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    // 既要删除出租列表的缓存，也要删除还车列表的缓存，既要删除车辆
    @UpdateAnnotation(name = {Constant.REDIS_RETURN,Constant.REDIS_RENT,Constant.REDIS_CARLIST})
    public Result addReturn(BusReturnForm form) throws ParseException {
       /*
       1、验证车牌号是否存在
       2、验证车辆是否租了出去 在car 与 rent
       3、根据天数计算租金
       4、计算总价
       5、插入时间
       6、将车辆改为未租CARmapper
       7、将车辆从出租RentMapper删除
       8、插入还车记录表中
        */
        BusCarQuery busCarQuery=new BusCarQuery();
        busCarQuery.setNum(form.getNum());
        BusCarVo busCarVo= (BusCarVo) busCarMapper.selectCarByNum(busCarQuery);
        BusRentVo busRentVo=busRentMapper.selectRentByID(form.getRentId());
        if (busCarVo==null){
            return new Result(CodeMsg.CAR_NUM_NOTEXEXIT);
        }
        if (busCarVo.getIsRent().equals(1)){ //如果车辆是未租，则返回车辆当前是未租状态下，不需要还
           return new Result(CodeMsg.RETURN_CAR_ERROR);
        }
        if (busRentVo.getFlag().equals(2)){//未还出去 1 未还    2还了
            return new Result(CodeMsg.RETURN_CAR_ERROR);
        }
        /**
         * 分析开始和结束时间
         * 结束时间是否比开始时间大
         */
        String begin2=busRentVo.getBeginTime();
        String endTime2 = form.getReturnTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = format.parse(begin2);
        Date date2 = format.parse(endTime2);
        int compareTo = date1.compareTo(date2);
        System.out.println("比较结果"+compareTo);
        if (compareTo==1){
            return new Result(CodeMsg.BEGINTIME_AND_ENDTIME_ERROR);
        }

        // 没有还车 则修改还车记录状态 修改为换了
        int rows = busRentMapper.updateRentFlag(form.getRentId(), busRentVo.getFlag(), Constant.CAR_RETURN_ED);
        if (rows != 1) {
            return new Result(CodeMsg.RETURN_FAILED_RENT_CHANGED_ERROR);
        }
        // 修改车辆状态 未出租
        rows = busCarMapper.updateCar_isRent_version_ByID(busCarVo.getId(), Constant.CAR_RENT_NOT, busCarVo.getVersion());
        if (rows != 1) {
            throw new BussiException(CodeMsg.RETURN_FAILED_CAR_CHANG_ERROR.code, CodeMsg.RETURN_FAILED_CAR_CHANG_ERROR.msg);
        }
        // 计算出租总金额
        String beginTime = busRentVo.getBeginTime() + " 00:00:00";
        String returnTime = form.getReturnTime() + " 23:59:59";
        int rentPrice = busRentVo.getRentPrice(); // 单日租金
        Date begin = DateUtil.parse(beginTime, "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.parse(returnTime, "yyyy-MM-dd HH:mm:ss");
        int days = (int) DateUtil.betweenDay(begin, end, true) + 1;
        if (DateUtil.isSameDay(begin, end)) {
            days = 1;
        }
        rentPrice = days * rentPrice; // 计算总租金
        int totalMoney = rentPrice + form.getPayMoney();
        form.setRentPrice(rentPrice);
        form.setTotalMoney(totalMoney);
        // 获取操作员ID
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        form.setUserId(activeUser.getSysUser().getId());

        form.setCreateTime(com.ycz.utils.DateUtil.getDate());
        System.out.println(form);
        busReturnMapper.insertReturn(form);
        return new Result();
    }

    /**
     * 还车车辆列表
     * @param query
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_RETURN)
    public Result selectList(BusReturnQuery query) {

        Page<Object> busReturnVoPage= PageHelper.startPage(query.getPage(),query.getLimit());

        busReturnMapper.selectList(query);

        return  new Result(busReturnVoPage.toPageInfo());

    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_RETURN)
    public Result updateReturn(BusReturn busReturn) {
        int i = busReturnMapper.updateByPrimaryKeySelective(busReturn);
        System.out.println(i>0?"更新还车信息修改成功":"更改还车信息修改失败");
        return new Result();
    }
}
