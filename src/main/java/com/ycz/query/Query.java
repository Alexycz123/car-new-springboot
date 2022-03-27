package com.ycz.query;

import lombok.Data;

/**
 * @Description: 查询基类
 * @Author: Todd Ding
 * @Date 2020-12-01 17:49
 */
@Data
public abstract class Query {
    /**
     * 页码
     */
    private Integer page = 1;
    /**
     * 每页数据条数
     */
    private Integer limit = 10;
}
