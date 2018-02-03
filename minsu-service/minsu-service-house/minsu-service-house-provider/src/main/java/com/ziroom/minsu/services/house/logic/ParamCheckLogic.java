/**
 * @FileName: ParamCheckLogic.java
 * @Package: com.ziroom.sms.services.cleaning.logic
 * @author sence
 * @created 7/9/2015 7:59 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.services.house.logic;

import com.alibaba.fastjson.JSON;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MessageSourceUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>参数基本验证</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
@Component("house.paramCheckLogic")
public class ParamCheckLogic {

    @Resource(name = "house.messageSource")
    private MessageSource messageSource;

    @Resource(name = "house.validator")
    private Validator validator;

    /**
     * 校验参数，并依据validator校验
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public <T> ValidateResult<T> checkParamValidate(String jsonStr, Class<T> clazz) {
        DataTransferObject dto = null;
        //参数是否为空
        if (Check.NuNStrStrict(jsonStr)) {
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return new ValidateResult<T>(dto);
        }
        //valiator校验
        T t = JSON.parseObject(jsonStr, clazz);
        dto = checkObjParamValidate(t);
        if (!Check.NuNObj(dto)) {
            return new ValidateResult<T>(dto);
        }
        return new ValidateResult<T>(t);
    }

    /**
     * 对象校验，依据validator
     * use case
     * 派单前做的工单校验
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> DataTransferObject checkObjParamValidate(T t) {
        DataTransferObject dto = null;
        //参数是否为空
        if (Check.NuNObj(t)) {
            dto = new DataTransferObject();
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.NOT_FOUND));
            return dto;
        }
        //是否符合validator校验
        StringBuilder errorMsg = new StringBuilder();
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(t);
        if (!Check.NuNCollection(constraintViolationSet)) {
            dto = new DataTransferObject();
            Iterator<ConstraintViolation<T>> iterator = constraintViolationSet.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> constraint = iterator.next();
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.putValue(constraint.getPropertyPath().toString(), constraint.getMessage());
                errorMsg.append(constraint.getMessage()).append(",");
            }
            //设置错误信息
            if (errorMsg.length() != 0) {
                errorMsg.deleteCharAt(errorMsg.length() - 1);
                dto.setMsg(errorMsg.toString());
            }
            return dto;
        }
        return dto;
    }
}
