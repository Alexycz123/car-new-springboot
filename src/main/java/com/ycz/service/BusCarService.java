package com.ycz.service;/*
 @author ycz
 @date 2021-07-02-19:24
*/


import com.ycz.common.Result;
import com.ycz.form.BusCarForm;
import com.ycz.query.BusCarQuery;
import com.ycz.vo.BusCarVo;

import java.util.List;

public interface BusCarService {
    Result selectCarList(BusCarQuery busCarQuery,Integer page,Integer Limit);

    Result selectCarList2();

    Result addCar(BusCarForm busCarForm);

    Result updateCar(BusCarForm busCarForm);

    Result deleteCar(Integer id);

    List<BusCarVo> userSelectCarList(BusCarQuery query);

    Object batchDeleteCar(List<Integer> deleteIds);
}
