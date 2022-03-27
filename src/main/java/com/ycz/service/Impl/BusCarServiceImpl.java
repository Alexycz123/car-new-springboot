package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-07-02-19:24
*/

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.BusCar;
import com.ycz.form.BusCarForm;
import com.ycz.mapper.BusCarMapper;
import com.ycz.query.BusCarQuery;
import com.ycz.service.BusCarService;
import com.ycz.utils.DateUtil;
import com.ycz.vo.BusCarVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//@Service
@Component
@Slf4j
public class BusCarServiceImpl implements BusCarService {
    @Autowired
    private BusCarMapper busCarMapper;

    @Override
    @RedisAnnotation(name = Constant.REDIS_CARLIST)
    public Result selectCarList(BusCarQuery busCarQuery,Integer page,Integer limit) {
        /*
        layui 传的是page limit
        page 要进行处理
        page = （page-1）* limit
        要传 page Limit 进行分页
        Object cacheValue=redisUtil.get("carList" + page + Limit);
        就可以Redis 缓  存  处  理  了
         */
//        Set<String> keys = redisUtil.getKeys(Constant.REDIS_CARLIST+"*");
//        System.out.println("模糊查找的："+keys);
        Page<BusCarVo> busCarVoPage= PageHelper.startPage(busCarQuery.getPage(),busCarQuery.getLimit());
        busCarMapper.selectCarList(busCarQuery);
//        System.out.println(busCarVoPage.toPageInfo());
//        System.out.println("impl +"+page+limit);
//
//
//      //  Object cacheValue=redisUtil.get(Constant.REDIS_CARLIST+busCarQuery);
//        Object cacheValue=redisUtil.get(Constant.REDIS_CARLIST+"&page="+busCarQuery.getPage()+"&limit="+busCarQuery.getLimit()+"&total"+busCarVoPage.getTotal() +busCarQuery);
//
//        if (cacheValue==null){
//            redisUtil.set(Constant.REDIS_CARLIST+"&page="+busCarQuery.getPage()+"&limit="+busCarQuery.getLimit()+"&total"+busCarVoPage.getTotal() +busCarQuery,
//
//                    busCarVoPage.toPageInfo());
//            redisUtil.expire(Constant.REDIS_CARLIST+"&page="+busCarQuery.getPage()+"&limit="+busCarQuery.getLimit()+"&total"+busCarVoPage.getTotal() +busCarQuery, 60 * 5);
//
//            return new Result(busCarVoPage.toPageInfo());
//        }else {
//            System.out.println("redis从缓存中取");
//            return  new Result(cacheValue);
//        }

        return new Result(busCarVoPage.toPageInfo());

    }

    @Override
    public Result selectCarList2() {

        Result result = new Result();
        result.setData(busCarMapper.selectCarList2());


        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name = Constant.REDIS_CARLIST)
    public Result addCar(BusCarForm busCarForm) {

        BusCarQuery query=new BusCarQuery();
        //查询车牌号是否重复
        query.setNum(busCarForm.getNum());
        BusCar busCar=busCarMapper.selectCarByNum(query);
        if (busCar!=null){//车辆已经存在
            return new Result(CodeMsg.CAR_NUM_EXIST_ERROR);
        }else {
            //插入车辆
            //设置当前时间
            busCarForm.setCreateTime(DateUtil.getDate());
            //设置是否出租
            busCarForm.setIsRent(1);
            //设置版本 为1
            busCarForm.setVersion("1");
            //插入车辆
            busCarMapper.insertCar(busCarForm);

            //加入到数据库后删除缓存
            //模糊查询CarList中的Redis


            return new Result();
        }

    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_CARLIST)
    public Result updateCar(BusCarForm busCarForm) {
        //验证是否有修改
        BusCarVo busCarVo=busCarMapper.selectCarAll(busCarForm);
        if (busCarVo!=null){
            return new Result(CodeMsg.MESSAGE_EQUALS_NEWMESSAGE);
        }else {//修改东西

            busCarForm.setCreateTime(DateUtil.getDate());
            busCarMapper.updateCar(busCarForm);
            return new Result();
        }
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_CARLIST)
    public Result deleteCar(Integer id) {

        //需要判断车辆的状态，如果车辆显示已租出，则不允许删除，返回状态
        BusCarVo carVo=new BusCarVo();
        carVo.setId(id);
        /**
         * 已经出租
         */
        carVo.setIsRent(Constant.CAR_RENT_ED);//已经出租
        BusCarVo carVoNew = busCarMapper.selectOne(carVo);
        if (carVoNew!=null){ //
            log.error("删除不了，因为车辆还未还");
            return new Result(CodeMsg.CAR_NOT_RETURN);
        }else {
           // busCarMapper.deleteCar(id);
            return new Result();
        }


    }

    @Override
    public List<BusCarVo> userSelectCarList(BusCarQuery query) {
        return busCarMapper.selectCarList(query);
    }

    @Override
    public Object batchDeleteCar(List<Integer> deleteIds) {

        List<String> resultData=new ArrayList<>();
        //需要判断车辆的状态，如果车辆显示已租出，则不允许删除，返回状态
        for (Integer deleteId : deleteIds) {
            BusCarVo carVo=new BusCarVo();
            carVo.setId(deleteId);
            carVo.setIsRent(Constant.CAR_RENT_ED);//已经出租
            /**
             * 已经出租
             */
            BusCarVo carVoNew = busCarMapper.selectOne(carVo);
            /*
            显示删除不了的数据，返回车牌回去
             */

            if (carVoNew!=null){ //
                log.error("删除不了，因为车辆还未还");
                resultData.add(carVoNew.getNum());
            }else {
                 busCarMapper.deleteCar(deleteId);
            }
        }
        return new Result(resultData);

    }
}
