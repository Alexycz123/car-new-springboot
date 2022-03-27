package com.ycz.controller;/*
 @author ycz
 @date 2021-10-29-11:16
*/

import com.ycz.common.Result;
import com.ycz.service.SysUserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sysusermsg")
public class SysUserMsgController {


    @Autowired
    @Qualifier("sysUserMsgServiceImpl2")
    private SysUserMsgService sysUserMsgService;

    @GetMapping("/getMsg/{sendId}/{receiveId}/{page}/{limit}")
    public Result getMsg(@PathVariable("sendId") Integer sendId, @PathVariable("receiveId") Integer receiveId,
                         @PathVariable("page") Integer currentPage, Model model, @PathVariable Integer limit){
        Map<String,Object> maps=new HashMap<>();
        System.out.println("获取的当前页"+currentPage);
        System.out.println("获取的每页条数"+limit);

        Integer page=(currentPage-1) * limit;


        maps.put("list",sysUserMsgService.selectMsg(sendId,receiveId,page, limit));
        //还需包装两个参数
        Integer total=sysUserMsgService.selectMsgCount(sendId,receiveId);
        maps.put("page",currentPage);
        maps.put("limit",limit);
        //信息的条数
        maps.put("total",total);
        //总共有多少页
        Integer pageTotal=total% limit ==0?total/ limit :total/ limit +1;
        maps.put("pageTotal",pageTotal);
        return new Result(maps);
    }

}
