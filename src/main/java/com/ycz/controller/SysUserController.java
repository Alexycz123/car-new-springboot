package com.ycz.controller;/*
 @author ycz
 @date 2021-06-25-16:19
*/

import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.common.validator.ValidatorUtil;
import com.ycz.domain.SysIp;
import com.ycz.domain.SysUser;
import com.ycz.form.SysUserForm;
import com.ycz.form.SysUserResetPwdForm;
import com.ycz.query.SysUserQuery;
import com.ycz.service.SysIpService;
import com.ycz.service.SysUserService;
import com.ycz.utils.DateUtil;
import com.ycz.utils.IpToAddressUtil;
import com.ycz.utils.IpUtil;
import com.ycz.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/sysuser")
@Api(tags = "用户管理")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysIpService ipService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录操作
     * @param loginName 用户名
     * @param loginPassword 密码
     * @param session
     * @param request
     * @param verifycode 验证码
     * @return
     */
    @PostMapping (value = "/login.do")
    @ApiOperation(value = "登录操作")
    public Object login(String loginName, String loginPassword, HttpSession session,
                        HttpServletRequest request,String verifycode
    ){
        System.out.println("进来了login页面");

        System.out.println("获取的验证码"+verifycode);
        System.out.println("重新获取的"+request.getSession().getAttribute("code"));
        if (!verifycode.equalsIgnoreCase(request.getSession().getAttribute("code").toString())){
            System.out.println("验证码错误");
            return new Result(CodeMsg.USER_CODE_ERROR);
        }

        String username=loginName;
        Md5Hash md5HashPassword = new Md5Hash(loginPassword, Constant.MD5_SALT, 2);//二次加密
        System.out.println(md5HashPassword.toString());
        String password=md5HashPassword.toString();


        Subject subject= SecurityUtils.getSubject();
        //将用户名与密码放进去
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        /**
         * 这是redis的loginName和password
         * 暂时有点问题想不通
         */
//        Object redisPassword = redisUtil.get("loginName:" + loginName);
//        if (redisPassword!=null && redisPassword.toString().equals(password)){
//            log.info("从redis获取登录的 loginName");
//            setIp(session,request,username,loginName);
//            subject
//            return new Result();
//        }


        try {
            subject.login(token);
//            Object principal = token.getPrincipal();//获得admin
//            System.out.println("principal"+principal);

            //下面就是登录成功后做的
            //登录成功后设置redis
//            redisUtil.set("loginName:"+loginName,password,600);
            //TODO
            setIp(session,request,username,loginName);

            return new Result();
        }catch (UnknownAccountException | IncorrectCredentialsException e){//用户名错误
            return new Result(CodeMsg.USER_USER_PASSWORD_ERROR);
        }//密码不存在
    }


    public void setIp(HttpSession session,HttpServletRequest request,String username,String loginName){
        session.setAttribute("loginName",loginName);

        SysUser loginUser = (SysUser) session.getAttribute("loginUser");
        session.setAttribute("loginId",loginUser.getId());
        IpUtil ipUtil=new IpUtil();
        System.out.println(ipUtil.getIpAddr(request));
        System.out.println("---------------------");
        String cityInfo = IpToAddressUtil.getCityInfo(ipUtil.getIpAddr(request));
        System.out.println("地址为"+cityInfo);
        SysIp sysIp=new SysIp();
        sysIp.setIp(ipUtil.getIpAddr(request));

        sysIp.setUserLoginName(username);
        System.out.println(loginUser.getId());
        sysIp.setUserId(loginUser.getId());
        if (cityInfo==null){
            sysIp.setLoginAddress("无法定位");
        }else {
            sysIp.setLoginAddress(cityInfo);
        }

        sysIp.setLastLoginTime(DateUtil.getDate());
        ipService.insert(sysIp);
    }

    @RequestMapping("/noauth")
    public String noauth(Model model){
        return "未经授权不能进入";
    }

    @RequestMapping("/logout.do")
    @ApiOperation("退出登录")
    public Object logout(){
        Subject subject =SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {//已经认证
            subject.logout();//退出
            WebSocketController.subOnlineCount();
        }
        //跳转到登录页面
        return new Result();
    }
    @RequestMapping("/page.do")
    public Object page(SysUserQuery query){
        return sysUserService.queryPage(query);
    }
    /*
    新增插入
     */
    @ApiOperation("添加用户")
    @PostMapping("/add.do")
    public Object add(SysUserForm sysUserForm){
        ValidatorUtil.validator(sysUserForm);
        return sysUserService.add(sysUserForm);
    }

    @RequestMapping("/reset.do")
    @ApiOperation("重新设置密码")
    public Object reset(Integer id){
        return sysUserService.resetPaaword(id);
    }

    @PostMapping("/delete.do")
    @ApiOperation("批量删除用户")
    public Object delete(String array){
        System.out.println(array);
        String sub=array.replace("[","");
        String sub1=sub.replace("]","");
        String[] arr=sub1.split(",",'1');
        System.out.println("切割后的长度"+arr.length);
        List<Integer> ids=new ArrayList<>();
        for (String s : arr) {
            System.out.println(s);
            Integer id=Integer.parseInt(s);
            ids.add(id);
        }

        sysUserService.deleteBatchUserByIds(ids);
        return new Result();
    }

    /*
    修改密码
     */

    @PostMapping("/resetPassword.do")
    @ApiOperation("修改密码")
    public Object updatePassword(SysUserResetPwdForm sysUserResetPwdForm){
        ValidatorUtil.validator(sysUserResetPwdForm);
        System.out.println(sysUserResetPwdForm);
        return sysUserService.updatePassword(sysUserResetPwdForm);
    }

    @GetMapping("/checkCodeServlet.do")
    @ApiOperation("获取验证码")
    public void checkCodeServlet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int width = 120;
        int height = 70;
        //1.创建一对象，在内存中图片(验证码图片对象)
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //2.美化图片
        //2.1 填充背景色
        Graphics g = image.getGraphics();//画笔对象
        g.setColor(Color.white);//背景颜色
        g.fillRect(0,0,width,height);

        g.setColor(Color.BLUE);    //2.2画边框
        g.drawRect(0,0,width - 1,height - 1);
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789";
        StringBuilder builder=new StringBuilder();
        //生成随机角标
        Random ran = new Random();
        for (int i = 1; i <= 4; i++) {
            int index = ran.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);//随机字符
            //2.3写验证码
            g.drawString(ch+"",width/5*i,height/2);
            builder.append(ch);
        }
        System.out.println(builder);
        HttpSession session = request.getSession();
        session.setAttribute("code",builder);
        //2.4画干扰线
        g.setColor(Color.white);
        //随机生成坐标点
        for (int i = 0; i < 10; i++) {
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);

            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }
        //3.将图片输出到页面展示
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

}
