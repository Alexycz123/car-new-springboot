package com.ycz.controller;/*
 @author ycz
 @date 2021-10-21-16:14
*/

import com.ycz.common.Result;
import com.ycz.form.InsCompanyForm;
import com.ycz.query.InsCompanyQuery;
import com.ycz.service.InsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ins")
public class InsCompanyController {

    @Autowired
    private InsCompanyService insCompanyService;

    @GetMapping("/page.do")
    public Result getPage(InsCompanyQuery query){
        System.out.println(query);
        return insCompanyService.selectList(query);
    }

    @PostMapping("/add.do")
    public Result add(InsCompanyForm form){

        System.out.println(form);

        return  insCompanyService.addIns(form);
    }

    @PostMapping("/edit.do")
    public Result edit(InsCompanyForm form){
        System.out.println(form);
        return insCompanyService.updateIns(form);
    }


}
