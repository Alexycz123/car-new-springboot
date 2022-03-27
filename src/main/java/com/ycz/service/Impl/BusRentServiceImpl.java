package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-07-10-22:59
*/

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.common.exception.BussiException;
import com.ycz.domain.ActiveUser;
import com.ycz.domain.BusRent;
import com.ycz.form.BusRentForm;
import com.ycz.mapper.BusCarMapper;
import com.ycz.mapper.BusCustomerMapper;
import com.ycz.mapper.BusRentMapper;
import com.ycz.query.BusCarQuery;
import com.ycz.query.BusRentQuery;
import com.ycz.service.BusRentService;
import com.ycz.utils.DateUtil;
import com.ycz.vo.BusCarVo;
import com.ycz.vo.BusCustomerVo;
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
public class BusRentServiceImpl implements BusRentService {
    @Autowired
    private BusRentMapper busRentMapper;

    @Autowired
    private BusCustomerMapper busCustomerMapper;

    @Autowired
    private BusCarMapper busCarMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name = {Constant.REDIS_RENT,Constant.REDIS_CARLIST})
    public Result addRent(BusRentForm form) {
        //查验客户身份证是否存在
        BusRentQuery query=new BusRentQuery();
        query.setIdCard(form.getIdCard());
        BusCustomerVo busCustomerVo= busCustomerMapper.selectCustomer_idCard(query.getIdCard());
        if (busCustomerVo==null){
            System.out.println("客户不存在");
            return new Result(CodeMsg.RENT_CUSTOMER_ID_CARD_ERROR);
        }
        /**
         * 查看车辆状态 若为2 已经出租
         */
        BusCarQuery busCarQuery=new BusCarQuery();
        busCarQuery.setNum(form.getNum());
        BusCarVo busCarVo= (BusCarVo) busCarMapper.selectCarByNum(busCarQuery);
        if (busCarVo.getIsRent().equals(2)){
            //车辆已经出租
            return new Result(CodeMsg.RENT_CAR_RENTED_ERROR);
        }
        /**
         * 若未出租，则需要修改状态与version
         * version 的目的是先查找出在数据中找到数据，更新的时候看version是不是当前的version
         * 如果version相同，这更新，version不同则不改变
         *
         *        update bus_car set
         *             is_rent = #{isRent} ,
         *             version = version + 1
         *             where id = #{id} and
         *             version = #{version} ->检查version是否与查找时相同
         */
        Integer rows=busCarMapper.
                updateCar_isRent_version_ByID(busCarVo.getId(),2,busCarVo.getVersion());
        if (rows != 1){
            throw new BussiException();
        }
        //为客户名字赋值
        form.setName(busCustomerVo.getName());

        String[] arr=form.getRentTime().split(" ~ ",'1');
        //起始时间
        form.setBeginTime(arr[0]);
        form.setEndTime(arr[1]);
        //为user_id赋值
        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        form.setUserId(activeUser.getSysUser().getId());
        System.out.println(form);
        form.setFlag(1);

        form.setUpdateTime(DateUtil.getDate());
        form.setCreateTime(DateUtil.getDate());
        busRentMapper.insertRent(form);
        return new Result();
    }

    /**
     * 查询车辆
     * @param query
     * @return
     */
    @Override
    @RedisAnnotation(name = Constant.REDIS_RENT)
    public Result selectRentList(BusRentQuery query) {
        Page<BusRentVo> busRentVoPage = PageHelper.startPage(query.getPage(), query.getLimit());
        //如果开始时间不为空
        if (StrUtil.isNotEmpty(query.getBeginTime())){
            String[] split = query.getBeginTime().split("~");
            query.setMinBeginTime(split[0].trim());
            query.setMaxBeginTime(split[1].trim());
        }
        busRentMapper.selectList(query);

        return new Result(busRentVoPage.toPageInfo());
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_RENT)
    public Result edit(BusRentForm form) {

        String beginTime = form.getBeginTime();
        String endTime = form.getEndTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(beginTime);
            Date date2 = format.parse(endTime);
            int compareTo = date1.compareTo(date2);
            System.out.println("比较结果"+compareTo);
            if (compareTo==1){
                return new Result(CodeMsg.BEGINTIME_AND_ENDTIME_ERROR);
            }


            form.setUpdateTime(DateUtil.getDate());
            busRentMapper.updateRent(form);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Result();
    }


}
