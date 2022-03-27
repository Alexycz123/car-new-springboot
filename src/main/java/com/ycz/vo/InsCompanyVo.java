package com.ycz.vo;/*
 @author ycz
 @date 2021-10-21-15:44
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycz.domain.InsCompany;

import java.util.Date;

public class InsCompanyVo extends InsCompany {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
}
