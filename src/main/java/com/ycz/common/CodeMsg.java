package com.ycz.common;

/**
 * @Description: 业务码 业务消息枚举
 * @author: Todd Ding
 * @date 2020-11-30 11:51
 */

//业务消息枚举
public enum CodeMsg {

    SUCCESS(200, "操作成功"),
    ERROR(110, "程序员送外卖了!"),

    USER_USER_PASSWORD_ERROR(4001001, "用户名或者密码错误!"),

    USER_LOGIN_NAME_EXIST_ERROR(4001002, "用户登录名已被使用!"),
    USER_PHONE_EXIST_ERROR(4001003, "用户手机号已被使用!"),
    USER_ID_CARD_EXIST_ERROR(4001004, "用户身份证号已被使用!"),
    USER_NOT_HAVE_PERMISSION_ERROR(4001005, "用户权限不足!"),
    USER_CODE_ERROR(4001009,"验证码错误"),
    USER_UPDATE_PASSWORD_ERROR(4001006, "修改失败,原密码不正确"),
    USEDPASSWORD_equal_NEWPASSWORD(4001007,"原密码与新密码相同"),
    PASSWORD_NO_EQUAL_NEWPASSWORD(4001008,"新密码与新密码之间不相同"),


    CUSTOMER_ID_CARD_EXIST_ERROR(4002001, "客户身份证号已被使用!"),
    CUSTOMER_PHONE_EXIST_ERROR(4002002, "客户手机号已被使用!"),
    CUSTOMER_NAME_NULL(4402003,"客户的名字不存在，所以不需要添加"),

    CAR_UPLOAD_IMG_ERROR(4003001, "汽车图片上传失败!"),
    CAR_NUM_EXIST_ERROR(4003002, "汽车车牌号已被使用!"),
    CAR_NOT_RETURN(4003003,"汽车未还，无法删除"),

    RENT_CUSTOMER_ID_CARD_ERROR(4004001, "客户身份证号不存在!"),
    RENT_CAR_RENTED_ERROR(4004002, "车辆已经出租!"),
    RENT_FAILED_ERROR(4004003, "车辆出租失败,车辆信息发生了变化!"),


    RETURN_CAR_ERROR(4005001, "车辆已经归还,请不要重复还车!"),
    RETURN_FAILED_RENT_CHANGED_ERROR(4005002, "还车失败,出租记录发生变化!"),
    RETURN_FAILED_CAR_CHANG_ERROR(4005003, "还车失败,车辆状态修改失败!"),
    MESSAGE_EQUALS_NEWMESSAGE(4006007,"要修改的信息与原信息相同，不用修改"),
    CAR_NUM_NOTEXEXIT(4006008,"车辆的车牌号不存在于库中"),
    BEGINTIME_AND_ENDTIME_ERROR(4006009,"开始时间不能大于结束时间"),
    ROLE_EXIST(4006010,"用户已经设置角色"),

    INS_COMPANY_NAME_EXIST(4000501,"保险公司名字已经存在"),
    INS_COMPANY_PHONE_EXIST(4000501,"保险公司手机号码已经存在"),
    INS_COMPANY_COMPLAINTS_PHONE_EXIST(4000501,"保险公司投诉号码已经存在")
    ;
    public Integer code; // 业务码
    public String msg; // 业务消息

    CodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
