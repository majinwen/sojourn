package com.ziroom.zrp.service.trading.utils;

import com.zra.common.exception.ValidationParamException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.google.common.collect.Iterables.getFirst;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年01月31日 17:38
 * @Version 1.0
 * @Since 1.0
 */
public final class BeanValidator {

    // 默认校验器
    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 验证单个bean的参数
     *
     * @param t 被校验的参数
     * @throws ValidationParamException 参数校验不成功则抛出此异常
     */
    public static <T> void validate(T t) {

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        ConstraintViolation<T> constraintViolation = getFirst(constraintViolations, null);

        if (constraintViolation != null) {
            throw new ValidationParamException(constraintViolation);
        }
    }
}
