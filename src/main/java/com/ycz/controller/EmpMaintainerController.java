package com.ycz.controller;/*
 @author ycz
 @date 2021-11-12-18:57
*/


import com.ycz.common.Result;
import com.ycz.common.validator.ValidatorUtil;
import com.ycz.domain.EmpMaintainer;
import com.ycz.query.EmpMaintainerQuery;
import com.ycz.service.EmpMaintainerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/emp")
@RestController
@Api("维修工表")
public class EmpMaintainerController {

    @Autowired
    private EmpMaintainerService empMaintainerService;

    @RequestMapping("/page.do")
    @ApiOperation("维修工list")
    public Result list(EmpMaintainerQuery query){
        System.out.println(query);
        return empMaintainerService.selectList(query);
    }


    @PostMapping("/add.do")
    public Result add(EmpMaintainer empMaintainer){
        ValidatorUtil.validator(empMaintainer);
        empMaintainerService.insert(empMaintainer);
        return new Result();
    }

    @PostMapping("/edit.do")
    public Result edit(EmpMaintainer empMaintainer){
        ValidatorUtil.validator(empMaintainer);
        empMaintainer.setUpdateTime(new Date());
        empMaintainerService.update(empMaintainer);
        return new Result();
    }

    @PostMapping("/delete.do")
    public Result delete(@RequestParam("array") String array){
        String sub=array.replace("[","");
        String sub1=sub.replace("]","");
        String[] split = sub1.split(",");
        for (String s : split) {
            int id = Integer.parseInt(s);
            empMaintainerService.deleteBatch(id);
        }
        return new Result();
    }



}
