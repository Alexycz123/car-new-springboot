package com.ycz.controller;/*
 @author ycz
 @date 2022-01-15-21:12
*/

import com.ycz.query.SysIpQuery;
import com.ycz.service.SysIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ip")
public class SysIpController {

    @Autowired
    private SysIpService ipService;

    @GetMapping("/page.do")
    public Object list(SysIpQuery query){
        return ipService.queryPage(query);
    }

}
