package com.ycz.mapper;/*
 @author ycz
 @date 2021-10-21-15:41
*/

import com.ycz.domain.InsCompany;
import com.ycz.form.InsCompanyForm;
import com.ycz.query.InsCompanyQuery;
import com.ycz.vo.InsCompanyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InsCompanyMapper {

    List<InsCompany> selectList(InsCompanyQuery insCompanyQuery);

    InsCompany selectByName_Phone_ComplaintsPhone(InsCompanyQuery query);

    InsCompany selectByName_Phone_ComplaintsPhoneNoIncludeId(InsCompanyQuery query);

    void insert(InsCompanyForm form);

    InsCompanyVo selectInsById(@Param("id") Integer id);

    void updateCompanyById(InsCompanyForm form);
}
