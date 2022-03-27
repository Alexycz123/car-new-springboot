package com.ycz.controller;/*
 @author ycz
 @date 2021-07-10-22:47
*/

import com.ycz.form.BusRentForm;
import com.ycz.query.BusRentQuery;
import com.ycz.service.BusRentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequestMapping("rent")
@RestController
@Api(tags = "出租管理")
public class RentController {

    @Autowired
    private BusRentService busRentService;

    /**
     * 车辆出租信息插入
     * @param busRentForm
     * @return
     */
    @PostMapping("add.do")
    @ApiOperation(value = "添加出租记录")
    public Object add(BusRentForm busRentForm){
        System.out.println(busRentForm);
        return busRentService.addRent(busRentForm);
    }

    @RequestMapping("page.do")
    @ApiOperation(value = "请求出租的页面数据")
    public Object page(BusRentQuery query){

        System.out.println("进来了rent/page。do");
        return busRentService.selectRentList(query);
    }

    @PostMapping("edit.do")
    @ApiOperation(value = "编辑出租记录")
    public Object edit(BusRentForm form) throws ParseException {

        System.out.println(form);
        return busRentService.edit(form);

    }





}
