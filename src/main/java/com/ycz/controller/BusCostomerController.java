package com.ycz.controller;/*
 @author ycz
 @date 2021-06-26-15:53
*/


import com.ycz.common.Result;
import com.ycz.common.validator.ValidatorUtil;
import com.ycz.domain.BusCustomer;
import com.ycz.domain.BusRent;
import com.ycz.form.BusCustomerForm;
import com.ycz.query.BusCustomerQuery;
import com.ycz.query.BusRentQuery;
import com.ycz.service.BusCustomerService;
import com.ycz.service.BusRentService;
import com.ycz.vo.BusRentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("buscustomer")
@Api(tags = "客户管理")
public class BusCostomerController {

    @Autowired
    private BusCustomerService busCustomerService;

    @Autowired
    private BusRentService busRentService;

    @RequestMapping("page.do")
    @ApiOperation(value = "请求当前页面的数据")
    public Object getCustomerList(BusCustomerQuery query){
        return busCustomerService.selectCustomList(query);
    }

    @PostMapping("add.do")
    @ApiOperation(value = "添加客户")
    public Object addCustomer(BusCustomerForm busCustomerForm){
        System.out.println("进来了BusCustomer add .do");
        ValidatorUtil.validator(busCustomerForm);
        System.out.println(busCustomerForm);
        return busCustomerService.addCustomer(busCustomerForm);
    }

    @PostMapping ("delete.do")
    @ApiOperation(value = "删除客户")
    public Object deleteCustomer(String array){
        System.out.println("进入了Customer delete。do");
        System.out.println(array);
        String sub=array.replace("[","");
        String sub1=sub.replace("]","");
        String[] arr=sub1.split(",",'1');
        System.out.println("切割后的长度"+arr.length);
        StringBuilder resultString=new StringBuilder();
        resultString.append("id为==》");
        for (String s : arr) {
            System.out.println(s);
            Integer id=Integer.parseInt(s);
            // 验证此客户是否出租过车
            BusCustomer busCustomer=busCustomerService.selectCustomerById(id);
            BusRentQuery query=new BusRentQuery();
            query.setIdCard(busCustomer.getIdCard());
            Result result = busRentService.selectRentList(query);
            if (result.getData() ==null){
                busCustomerService.deleteCustomer(id);
            }else {
                resultString.append(id).append(",");
            }
        }
        resultString.append("删除失败，因为出租记录有此客户");
        return new Result(resultString);

    }




    @PostMapping("edit.do")
    @ApiOperation(value = "编辑客户")
    public Object editCustomer(BusCustomerForm busCustomerForm){
        System.out.println("进来了BusCustomer edit");
        System.out.println(busCustomerForm);
        ValidatorUtil.validator(busCustomerForm);
        return busCustomerService.updateCustomer(busCustomerForm);
    }

    /*
    导出符合条件的客户
     */

    @RequestMapping("export.do")
    @ApiOperation(value = "导出客户")
    public void export(BusCustomerQuery query, HttpServletResponse response) throws IOException {
            busCustomerService.exportCustomer(query,response);
    }
    /*
    处理文件上传
     */
    @RequestMapping("import.do")
    @ApiOperation(value = "导入客户")
    public Object CustomerImport(@RequestParam("customers") MultipartFile file) throws IOException {
        return busCustomerService.batchAddCustomer(file);
    }

}
