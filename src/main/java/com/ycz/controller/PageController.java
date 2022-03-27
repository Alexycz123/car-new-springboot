package com.ycz.controller;/*
 @author ycz
 @date 2021-09-16-15:49
*/

import com.ycz.query.BusCarQuery;
import com.ycz.service.BusCarService;
import com.ycz.vo.BusCarVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Api(tags = "跳转页面")
public class PageController {

    @Autowired
    private BusCarService carService;

    @RequestMapping({"/","/index"})
    @ApiOperation(value = "跳转到主页面")
    public String toIndex(){
        return "index";
    }

    @RequestMapping({"/user","/user/index"})
    @ApiOperation(value = "跳转到用户主页面")
    public String toUserIndex(Model model){
        BusCarQuery query=new BusCarQuery();
        List<BusCarVo> busCarVoList= carService.userSelectCarList(query);
        model.addAttribute("lists",busCarVoList);
        System.out.println(busCarVoList);
        return "user/index";
    }

    @RequestMapping("/ins/list.do")
    public String toInsList(Model model){
        return "view/insCompany/list";
    }


    @RequestMapping({"/user/products","/user/index"})
    @ApiOperation(value = "跳转到用户一个产品的页面")
    public String toUserOneProducts(Model model){
        return "user/one-products";
    }

    @RequestMapping("/main.do")
    @ApiOperation(value = "跳转到后端主页面")

    public String toMain(Model model, HttpSession session) throws IllegalAccessException, InstantiationException {
        model.addAttribute("loginName",session.getAttribute("loginName"));
        model.addAttribute("loginId",session.getAttribute("loginId"));
        model.addAttribute("page",1);
        model.addAttribute("limit",5);
        return "main";
    }

    @RequestMapping("/user/list.do")
    @ApiOperation(value = "跳转到用户的list页面")
    public String toUserList(){
        return "view/user/list";
    }

    @RequestMapping("/car/list.do")
    @ApiOperation(value = "请求车辆的list")
    public String toCarList(){
        return "view/car/list";
    }

    @RequestMapping("/customer/list.do")
    @ApiOperation(value = "请求客户的list")
    public String toCustomerList(){
        return "view/customer/list";
    }


    @RequestMapping("/rent/list.do")
    @ApiOperation(value = "请求出租的list")
    public String toRentList(){
        return "view/rent/list";
    }

    @RequestMapping("/return/list.do")
    @ApiOperation(value = "请求还车的list")
    public String toReturnList(){
        return "view/return/list";
    }

    @RequestMapping("/role/list.do")
    public String toRoleList(){
        return "view/role/list";
    }

    @RequestMapping("/emp/list.do")
    public String toEmpList(){
        return "view/emp/list";
    }
    @RequestMapping("/empReturn/list.do")
    public String toEmpReturnList(){
        return "view/empReturn/list";
    }

    @RequestMapping("/permission/list.do")
    public String toPermission(){
        return "view/permission/list";
    }
    @RequestMapping("/ip/list.do")
    public String toIp(){
        return "view/ip/list";
    }
///empReturn/list.do

}
