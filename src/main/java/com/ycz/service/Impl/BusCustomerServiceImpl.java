package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-06-26-15:45
*/

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.BusCustomer;
import com.ycz.form.BusCustomerForm;
import com.ycz.mapper.BusCustomerMapper;
import com.ycz.query.BusCustomerQuery;
import com.ycz.service.BusCustomerService;
import com.ycz.utils.DateUtil;
import com.ycz.vo.BusCustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BusCustomerServiceImpl implements BusCustomerService {

    @Autowired
    private BusCustomerMapper busCustomerMapper;
    //传回去是Result

    @RedisAnnotation(name = Constant.REDIS_CUSTOMER)
    public Result selectCustomList(BusCustomerQuery query) {

        Page<BusCustomerVo> busCustomerVoPage= PageHelper.startPage(query.getPage(),query.getLimit());

        busCustomerMapper.selectCustomList(query);

        System.out.println(busCustomerVoPage.toPageInfo());

        return new Result(busCustomerVoPage.toPageInfo());
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_CUSTOMER)
    public Result updateCustomer(BusCustomerForm busCustomerForm) {
        BusCustomerVo busCustomerVo= busCustomerMapper.selectCustomer(busCustomerForm);
        if (busCustomerVo!=null){
            return new Result(CodeMsg.MESSAGE_EQUALS_NEWMESSAGE);
        }
        /*
        根据身份证号查询
         */
        BusCustomerQuery query=new BusCustomerQuery();
        query.setIdCard(busCustomerForm.getIdCard());
        busCustomerVo=busCustomerMapper.selectCustomer_idCard_phone(query);
        if (busCustomerVo!=null && !busCustomerForm.getId().equals(busCustomerVo.getId())){
            return new Result(CodeMsg.CUSTOMER_ID_CARD_EXIST_ERROR);
        }
        /*
        根据手机号查询是否重复
         */
        query=new BusCustomerQuery();
        query.setPhone(busCustomerForm.getPhone());
        busCustomerVo=busCustomerMapper.selectCustomer_idCard_phone(query);
        if (busCustomerVo!=null && !busCustomerForm.getId().equals(busCustomerVo.getId())){
            return new Result(CodeMsg.CUSTOMER_PHONE_EXIST_ERROR);
        }

        busCustomerForm.setUpdateTime(DateUtil.getDate());
        busCustomerMapper.updateCustomer(busCustomerForm);
        return new Result();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_CUSTOMER)
    @Transactional(rollbackFor = Exception.class)
    public Result addCustomer(BusCustomerForm busCustomerForm) {

        /*
        要搜索的东西
         */
        BusCustomerQuery query=new BusCustomerQuery();
        BusCustomerVo busCustomerVo=new BusCustomerVo();
        query.setPhone(busCustomerForm.getPhone());
        busCustomerVo=busCustomerMapper.selectCustomer_idCard_phone(query);
        if (busCustomerVo!=null){
            return new Result(CodeMsg.CUSTOMER_PHONE_EXIST_ERROR);
        }
        query=new BusCustomerQuery();
        query.setIdCard(busCustomerForm.getIdCard());
        busCustomerVo=busCustomerMapper.selectCustomer_idCard_phone(query);
        if (busCustomerVo!=null){
            return new Result(CodeMsg.CUSTOMER_ID_CARD_EXIST_ERROR);
        }
        busCustomerForm.setCreateTime(DateUtil.getDate());
        busCustomerForm.setUpdateTime(DateUtil.getDate());
        busCustomerMapper.insertBusCustomer(busCustomerForm);
        return new Result();
    }

    @Override
    @UpdateAnnotation(name = Constant.REDIS_CUSTOMER)
    public Result deleteCustomer(Integer id) {
        busCustomerMapper.deleteCustomer(id);
        return new Result();
    }

    @Override
    public void exportCustomer(BusCustomerQuery query, HttpServletResponse response) throws IOException {
        //与客户端相关的输出流
        ServletOutputStream os=response.getOutputStream();
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        String fileName= URLEncoder.encode("客户信息.xls","utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        //符合条件的客户
       List<BusCustomerVo> busCustomerVoList= busCustomerMapper.selectCustomList(query);
        //将客户转化为excel数据流
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        //取别名
        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("phone","手机号码");
        writer.addHeaderAlias("idCard","身份证");
        writer.addHeaderAlias("sex","性别");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("updateTime","更新时间");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(busCustomerVoList,true);
        //将Excel输出给浏览器
        writer.flush(os,true);


    }
    /*
    处理上传的文件Excel中客户文件
    将Excel信息中客户插入到数据库中
     */
    @Override
    @UpdateAnnotation(name = Constant.REDIS_CUSTOMER)
    @Transactional(rollbackFor = Exception.class)
    public Result batchAddCustomer(MultipartFile file) throws IOException {
        //解析excel文件
       ExcelReader render= ExcelUtil.getReader(file.getInputStream());
        List<Map<String, Object>> mapList = render.readAll();

        List<BusCustomerVo> busCustomerVoList=new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            //TODO 先Object 判断
            Result result = transitionBusCustomer(map);
            if (result.getCode()==4002001 || result.getCode()==4002002){
                if (map.get("name")!=null){
                    stringList.add((String) map.get("name"));
                }else if (map.get("姓名")!=null){
                    stringList.add((String) map.get("姓名"));
                }
                continue; // 跳过了此次循环
            }
            System.out.println("不执行添加用户");
            if (result.getData()!=null){
                BusCustomerVo busCustomerVo = (BusCustomerVo) result.getData();
                busCustomerVoList.add(busCustomerVo);
            }
        }
        //将数据转化为对象
        System.out.println(busCustomerVoList);
        // 不对 ，都有会错误的
        if (!busCustomerVoList.isEmpty()){
            System.out.println("=====批量导入客户不为空=====");
            busCustomerMapper.batchInsert(busCustomerVoList);
        }

        //则需要返回
        if (!stringList.isEmpty()){
            System.out.println("不添加客户的姓名为===》"+stringList);
            Result result = new Result();
            result.setCode(200);
            StringBuilder msg= new StringBuilder("客户中有信息错误的是(身份证或手机号相同):" + stringList + "----添加成功的有:");
            for (BusCustomerVo busCustomerVo : busCustomerVoList) {
                msg.append(busCustomerVo.getName());
            }
            result.setMsg(msg.toString());
            return result;
        }else {
            //无添加失败的
            return new Result();
        }


    }

    @Override
    public BusCustomer selectCustomerById(Integer id) {
        return busCustomerMapper.selectByPrimaryKey(id);
    }

    private Result transitionBusCustomer(Map<String, Object> map) {
        BusCustomerVo busCustomerVo= new BusCustomerVo();
        if (map.get("name")!=null){
            busCustomerVo.setName((String) map.get("name"));
        }else if (map.get("姓名")!=null){
            busCustomerVo.setName((String) map.get("姓名"));
        }else {
            //连姓名都没有，没必要继续下面的操作
            return new Result(CodeMsg.CUSTOMER_NAME_NULL);
        }
        // 接下来验证手机号码 与 身份证是否相同
        if (map.get("phone")!=null){
            busCustomerVo.setPhone( map.get("phone").toString());
        }else if (map.get("手机号码")!=null){
            busCustomerVo.setPhone(map.get("手机号码").toString());
        }
        BusCustomerQuery query=new BusCustomerQuery();
        query.setPhone(busCustomerVo.getPhone());
         BusCustomerVo busCustomerVo2=busCustomerMapper.selectCustomer_idCard_phone(query);
        if (busCustomerVo2!=null){
            return new Result(CodeMsg.CUSTOMER_PHONE_EXIST_ERROR);
        }

        if (map.get("idCard")!=null){
            busCustomerVo.setIdCard(map.get("idCard").toString());
        }else if (map.get("身份证")!=null){
            busCustomerVo.setIdCard(map.get("身份证").toString());
        }

        query = new BusCustomerQuery();
        query.setIdCard(busCustomerVo.getIdCard());
        busCustomerVo2=busCustomerMapper.selectCustomer_idCard_phone(query);
        if (busCustomerVo2!=null){
            return new Result(CodeMsg.CUSTOMER_ID_CARD_EXIST_ERROR);
        }

        if (map.get("sex")!=null){
            busCustomerVo.setSex(Math.toIntExact((Long) map.get("sex")));
        }else if (map.get("性别")!=null){
            busCustomerVo.setSex(Math.toIntExact((Long) map.get("性别")));
        }

        if (map.get("address")!=null){
            busCustomerVo.setAddress((String) map.get("address"));
        }else if (map.get("地址")!=null){
            busCustomerVo.setAddress((String) map.get("地址"));
        }

        if (map.get("createTime")!=null){
            busCustomerVo.setCreateTime((Date) map.get("createTime"));
        }else if (map.get("创建时间")!=null){
            busCustomerVo.setCreateTime((Date) map.get("创建时间"));
        }

        if (map.get("updateTime")!=null){
            busCustomerVo.setUpdateTime((Date) map.get("updateTime"));
        }else if (map.get("更新时间")!=null){
            busCustomerVo.setUpdateTime((Date) map.get("更新时间"));
        }
        return new Result(busCustomerVo);
        //不对啊 ， id 不应该出现在 批量添加中啊 傻逼了
    }
}
