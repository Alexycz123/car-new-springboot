package com.ycz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description: 权限实体类
 * @Author: Todd Ding
 * @Date 2020-12-04 20:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission {
    /**
     * 权限ID
     */
    private Integer id;

    /**
     * 权限名称
     */
    @NotEmpty(message = "权限名称不能为空")
    @Length(max = 10, message = "权限名称最多10个字符")
    private String title;

    /**
     * 权限图标
     */
    @Length(max = 20, message = "权限图标最多20个字符")
    private String icon;

    /**
     * 权限连接  菜单连接
     */
    @Length(max = 100, message = "权限链接最多100个字符")
    private String href;

    /**
     * 是否展开   1 展开   0  不展开
     */
    @Range(min = 0, max = 1, message = "是否展开只能是0~1")
    private Integer spread;

    /**
     * 权限类型  1 菜单  2 按钮
     */
    @NotNull(message = "权限类型不能为空")
    @Range(min = 1, max = 2, message = "权限类型只能是1~2")
    private Integer type;

    /**
     * 权限自定义标识
     */
    @NotEmpty(message = "权限标识不能为空")
    @Length(max = 20, message = "权限标识最多20个字符")
    private String tag;

    /**
     * 排序 越大越靠前
     */
    private Integer sort;

    /**
     * 父权限ID 默认 0 表示一级菜单
     */
    @NotNull(message = "父权限ID不能为空")
    private Integer parentId;

    /**
     * 子菜单
     */
    private List<SysPermission> children;

    /**
     * DTREE 复选框标记
     */
    private String checkArr = "0";
}
