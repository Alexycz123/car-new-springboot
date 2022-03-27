package com.ycz.controller;/*
 @author ycz
 @date 2021-07-15-21:44
*/

import com.ycz.common.validator.ValidatorUtil;
import com.ycz.domain.BusReturn;
import com.ycz.form.BusReturnForm;
import com.ycz.query.BusReturnQuery;
import com.ycz.service.BusReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("return")
@Api(tags = "还车管理")
public class ReturnController {
    @Autowired
    private BusReturnService busReturnService;

    @PostMapping("add.do")
    @ApiOperation(value = "添加还车记录")
    public Object add(BusReturnForm form) throws ParseException {

        ValidatorUtil.validator(form);
        return busReturnService.addReturn(form);
    }

    @RequestMapping("page.do")
    @ApiOperation(value = "请求还车记录")
    public Object page(BusReturnQuery query){
        return busReturnService.selectList(query);
    }

    @PostMapping("/edit.do")
    public Object edit(BusReturn busReturn){
        System.out.println(busReturn);
        ValidatorUtil.validator(busReturn);
        return busReturnService.updateReturn(busReturn);
    }
}
