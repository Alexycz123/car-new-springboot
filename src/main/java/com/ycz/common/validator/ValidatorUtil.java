package com.ycz.common.validator;

import com.ycz.common.Constant;
import com.ycz.common.exception.BussiException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Description: 数据校验器
 * 前端传输数据给后端
 * 后端 : 数据格式校验和数据业务校验
 * 数据格式校验: 例如 手机号  11位  如果传输是12位: 数据库由于11位 DATA tolong 导致数据无法插入到数据库 报SQL相关的异常
 * 因为没有对数据进行格式的校验
 *
 * 查询数据表中是否已经存在该手机号 这是数据业务校验
 * 虽然前端可以进行数据格式的校验.但是那是前端做的.一般在开发中,会在控制层进行数据的格式校验,
 * 在service层进行业务校验.
 * 只有格式校验和业务校验都通过之后才会进行数据的数据操作.
 * @author: Todd Ding
 * @date 2020-11-30 14:18
 */

//数据校验器
public class ValidatorUtil {
    /**
     * 数据校验器对象
     */
    public static final Validator validator;

    /**
     * 实例化数据校验器对象
     */
    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    /**
     * 进行数据校验码
     *
     * @param object
     */
    public static void validator(Object object) {
        /**
         * 进行具体的数据校验
         */
        Set<ConstraintViolation<Object>> validateSet = validator.validate(object);
        // 如果数据校验器校验的结果不为空 说明就存在数据校验不通过
        if (validateSet != null && !validateSet.isEmpty()) {
            // 遍历校验不通过的信息
            for (ConstraintViolation<Object> objectConstraintViolation : validateSet) {
                // 校验不通过的原因
                String message = objectConstraintViolation.getMessage();
                Integer code = Constant.PARAM_CHECKED_ERROR;
                throw new BussiException(code, message);
            }
        }
    }
}
