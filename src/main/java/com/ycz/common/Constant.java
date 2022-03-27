package com.ycz.common;

/**
 * @Description: 常量接口
 * @author: Todd Ding
 * @date 2020-11-30 11:54
 */

//全异常接口
public interface Constant {
    /**
     * 系统异常码  : 数据校验不通过
     */

    Integer PARAM_CHECKED_ERROR = 4000001;
    /**
     * MD5加密的盐
     */
    String MD5_SALT = "day day up";
    /**
     * 默认的登录密码
     */


    String DEFAULT_PASSWORD = "123456";

    /**
     * 保存上传文件的文件夹
     */
    String UPLOAD_FOLDER = "upload";

    /**
     * 未出租
     */
    Integer CAR_RENT_NOT = 1;

    /**
     * 已经出租
     */
    Integer CAR_RENT_ED = 2;

    /**
     * 未还车
     */
    Integer CAR_RETURN_NOT = 1;

    /**
     * 已还车
     */
    Integer CAR_RETURN_ED = 2;

    /**
     * 权限类型 菜单  1
     */
    Integer PERMISSION_TYPE_MENU = 1;

    /**
     * 权限类型 按钮  2
     */
    Integer PERMISSION_TYPE_BTN = 2;
    /**
     * 一级菜单
     */
    Integer MENU_LV1 = 0;

    /**
     * Redis的缓存的CarList
     */
    String REDIS_CARLIST="CarList";
    String REDIS_CUSTOMER="CustomerList";
    String REDIS_RENT="RentList";
    String REDIS_RETURN="ReturnList";
    String REDIS_EMPMAINTAINER="EMPMAINList";
    String REDIS_INS="INSList";
    String REDIS_SYSIP="SYSIPList";
    String REDIS_PERMISSION="PERMISSIONList";
    String REDIS_ROLE="ROLENList";
    String REDIS_USERMSG="USERMSGList";
    String REDIS_USER="USERList";
    String REDIS_EMPRETURN = "EMPRETURN";
}
