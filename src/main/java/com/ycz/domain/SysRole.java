package com.ycz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 角色实体类
 * @Author: Todd Ding
 * @Date 2020-12-04 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {
    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    @Length(max = 20, message = "角色名称最多20个字符")
    private String name;

    /**
     * 角色标识
     */
    @NotEmpty(message = "角色标识不能为空")
    @Length(max = 20, message = "角色标识最多20个字符")
    private String tag;

    /**
     * 角色描述
     */
    @Length(max = 100, message = "角色描述最多20个字符")
    private String descp;
}
