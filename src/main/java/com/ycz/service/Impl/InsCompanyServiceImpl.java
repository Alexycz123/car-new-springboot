package com.ycz.service.Impl;/*
 @author ycz
 @date 2021-10-21-16:12
*/

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import com.ycz.domain.InsCompany;
import com.ycz.form.InsCompanyForm;
import com.ycz.mapper.InsCompanyMapper;
import com.ycz.query.InsCompanyQuery;
import com.ycz.service.InsCompanyService;
import com.ycz.utils.DateUtil;
import com.ycz.vo.InsCompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsCompanyServiceImpl implements InsCompanyService {
    @Autowired
    private InsCompanyMapper insCompanyMapper;

    @Override
    @RedisAnnotation(name = Constant.REDIS_INS)
    public Result selectList(InsCompanyQuery insCompanyQuery) {

        Page<InsCompany> insCompanyPage= PageHelper.startPage(insCompanyQuery.getPage(),insCompanyQuery.getLimit());

        insCompanyMapper.selectList(insCompanyQuery);

        System.out.println(insCompanyPage.toPageInfo());

        return new Result(insCompanyPage.toPageInfo());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name = Constant.REDIS_INS)
    public Result addIns(InsCompanyForm form) {
        //判断名字与电话号码与投诉号码有没有重复
        InsCompanyQuery query=new InsCompanyQuery();
        query.setName(form.getName());
        InsCompany insCompany = insCompanyMapper.selectByName_Phone_ComplaintsPhone(query);
        if (insCompany!=null){
            return new Result(CodeMsg.INS_COMPANY_NAME_EXIST);
        }

        query=new InsCompanyQuery();
        query.setPhone(form.getPhone());
        insCompany= insCompanyMapper.selectByName_Phone_ComplaintsPhone(query);
        if (insCompany!=null){
            return new Result(CodeMsg.INS_COMPANY_PHONE_EXIST);
        }

        query=new InsCompanyQuery();
        query.setPhone(form.getComplaintsPhone());
        insCompany= insCompanyMapper.selectByName_Phone_ComplaintsPhone(query);
        if (insCompany!=null){
            return new Result(CodeMsg.INS_COMPANY_COMPLAINTS_PHONE_EXIST);
        }

        form.setCreateTime(DateUtil.getDate());
        insCompanyMapper.insert(form);
        return new Result();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @UpdateAnnotation(name = Constant.REDIS_INS)
    public Result updateIns(InsCompanyForm form) {
        //判断名字与电话号码与投诉号码有没有重复
        InsCompanyQuery query=new InsCompanyQuery();
        query.setName(form.getName()).setId(form.getId());
        InsCompany insCompany = insCompanyMapper.selectByName_Phone_ComplaintsPhoneNoIncludeId(query);
        if (insCompany!=null){
            return new Result(CodeMsg.INS_COMPANY_NAME_EXIST);
        }

        query=new InsCompanyQuery();
        query.setPhone(form.getPhone()).setId(form.getId());
        insCompany= insCompanyMapper.selectByName_Phone_ComplaintsPhoneNoIncludeId(query);
        if (insCompany!=null){
            System.out.println("电话号码重复的："+insCompany);
            return new Result(CodeMsg.INS_COMPANY_PHONE_EXIST);
        }

        query=new InsCompanyQuery();
        query.setPhone(form.getComplaintsPhone()).setId(form.getId());
        insCompany= insCompanyMapper.selectByName_Phone_ComplaintsPhoneNoIncludeId(query);
        if (insCompany!=null){
            return new Result(CodeMsg.INS_COMPANY_COMPLAINTS_PHONE_EXIST);
        }

        //根据form id查询出来当前的version
        //之后填写的时候判断version是否相同
        //如果相同的话，version 也会同时 + 1

        InsCompanyVo insCompanyVo= insCompanyMapper.selectInsById(form.getId());

        System.out.println("要修改的保险公司"+insCompanyVo);

        form.setCreateTime(DateUtil.getDate());

        form.setVersion(insCompanyVo.getVersion());
        insCompanyMapper.updateCompanyById(form);

        return new Result();
    }
}
