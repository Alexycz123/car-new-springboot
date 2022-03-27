package com.ycz.controller;/*
 @author ycz
 @date 2021-07-02-19:23
*/


import com.ycz.common.validator.ValidatorUtil;
import com.ycz.form.BusCarForm;
import com.ycz.query.BusCarQuery;
import com.ycz.service.BusCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("car")
@Api(tags = "车辆管理")
public class CarController {

    @Autowired
     private BusCarService busCarService;

    /**
     * 车辆页面List
     * @param busCarQuery 查询内容
     * @return
     */

    @RequestMapping("page.do")
    @ApiOperation(value = "请求车辆的页面信息")
    public Object CarList(BusCarQuery busCarQuery, Integer page,Integer limit){
        System.out.println("进来了Car/page.do");
        return busCarService.selectCarList(busCarQuery,page,limit);
    }

    @RequestMapping("page2.do")
    @ApiOperation(value = "请求车辆的页面信息")
    public Object CarList2(){
        System.out.println("进来了Car/page.do");
        return busCarService.selectCarList2();
    }

    /**
     * 添加车辆
     * @param busCarForm
     * @return
     */
    @PostMapping("add.do")
    @ApiOperation(value = "添加车辆")
    public Object addCar(BusCarForm busCarForm){

        System.out.println(busCarForm);

        ValidatorUtil.validator(busCarForm);

        return busCarService.addCar(busCarForm);
    }

    /**
     * 编辑车辆
     * @param busCarForm 传过来的Form类型
     * @return
     */
    @PostMapping("edit.do")
    @ApiOperation(value = "编辑车辆")
    public Object editCar(BusCarForm busCarForm){
        System.out.println(busCarForm);
        ValidatorUtil.validator(busCarForm);
        return busCarService.updateCar(busCarForm);

    }

    /**
     * 删除车辆
     * @param array 传过来的数组String类型
     * @return
     */
    @PostMapping("delete.do")
    @ApiOperation(value = "删除车辆")
    public Object deleteCar(String array){
        System.out.println("进入了car delete。do");
        System.out.println(array);
        String sub=array.replace("[","");
        String sub1=sub.replace("]","");
        String[] arr=sub1.split(",",'1');
        System.out.println("切割后的长度"+arr.length);
        List<Integer> deleteIds=new ArrayList<>();
        for (String s : arr) {
            Integer i=Integer.parseInt(s);
            deleteIds.add(i);
        }


        return  busCarService.batchDeleteCar(deleteIds);
    }

}
