package com.ycz.controller;/*
 @author ycz
 @date 2021-12-23-16:49
*/

import com.ycz.common.Result;
import com.ycz.domain.BusReturn;
import com.ycz.domain.EmpMaintainer;
import com.ycz.domain.EmpReturn;
import com.ycz.query.EmpReturnQuery;
import com.ycz.service.EmpReturnService;
import com.ycz.vo.EmpReturnVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

///empReturn/postEmpReturn
@RequestMapping("/empReturn")
@RestController
public class EmpReturnController {

    @Autowired
    private EmpReturnService empReturnService;

    @PostMapping("/postEmpReturn")
    public Object postEmpReturn(@Param("empId")Integer empId,
                                @Param("maintainTime")String maintainTime,
                                @Param("returnId")Integer returnId) throws ParseException {
        System.out.println("维修工id"+empId);
        System.out.println("维修工时间"+maintainTime);
        System.out.println("还车Id"+returnId);
        return empReturnService.insertEmpReturn(empId,returnId,maintainTime);
    }

    @RequestMapping("/page.do")
    public Object page(EmpReturnQuery query, String name, String num
    ){
        EmpMaintainer emp=new EmpMaintainer();
        emp.setName(name);
        BusReturn busReturn=new BusReturn();
        busReturn.setNum(num);
        query.setBusReturn(busReturn);
        query.setEmpMaintainer(emp);
        System.out.println(query);
        return new Result(empReturnService.queryPage(query));
    }

    @RequestMapping("/edit.do")
    public Result edit(EmpReturnVo empReturnVo){
         return  empReturnService.update(empReturnVo);
    }

}
