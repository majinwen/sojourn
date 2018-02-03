package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.UserActEmptyEntity;
import com.ziroom.minsu.services.cms.api.inner.UserActEmptyService;
import com.ziroom.minsu.services.cms.dao.CouponMobileLogDao;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.service.ActCouponServiceImpl;
import com.ziroom.minsu.services.cms.service.UserActEmptyServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>轮空用户参与活动</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/10.
 * @version 1.0
 * @since 1.0
 */
@Service("cms.userActEmptyProxy")
public class UserActEmptyProxy implements UserActEmptyService{

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserActEmptyProxy.class);

    @Resource(name = "cms.userActEmptyServiceImpl")
    private UserActEmptyServiceImpl userActEmptyService;

    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;
    
	@Resource(name = "cms.actCouponServiceImpl")
	private ActCouponServiceImpl actCouponServiceImpl;

    /**
     * 保存用户的轮空
     * @param paramJson
     * @return
     */
    @Override
    public String saveUserEmpty(String paramJson) {
        LogUtil.info(LOGGER, "保存用户轮空参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            // 校验参数
            UserActEmptyEntity userActEmptyEntity = JsonEntityTransform.json2Object(paramJson, UserActEmptyEntity.class);
            if (Check.NuNObj(userActEmptyEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数为空");
                return dto.toJsonString();
            }
            if(Check.NuNStr(userActEmptyEntity.getCustomerMobile())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("手机号为空");
                return dto.toJsonString();
            }
            if(Check.NuNStr(userActEmptyEntity.getActSn()) && Check.NuNStr(userActEmptyEntity.getGroupSn())){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("活动或组号为空");
                return dto.toJsonString();
            }
            //保存当前的活动信息
            userActEmptyService.saveUserEmpty(userActEmptyEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    /**
     * 校验当前活动是否参加过
     * @param mobile
     * @param actSn
     * @return
     */
    @Override
    public String countEmptyEmptyByMobileAndActSn(String mobile,String actSn) {
        LogUtil.info(LOGGER, "校验是否参加 mobile:{}  actSn:{}", mobile,actSn);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStr(mobile)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("手机号为空");
                return dto.toJsonString();
            }
            if(Check.NuNStr(actSn)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("活动号为空");
                return dto.toJsonString();
            }
            //当前电话参数的未中签数量
            Long count = userActEmptyService.countByMobileAndActSn(mobile,actSn);
            dto.putValue("num", count);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }



    /**
     * 校验当前活动是否参加过
     * @param mobile
     * @param groupSn
     * @return
     */
    @Override
    public String countEmptyByMobileAndGroupSn(String mobile,String groupSn) {
        LogUtil.info(LOGGER, "校验是否参加 mobile:{}  groupSn:{}", mobile,groupSn);
        DataTransferObject dto = new DataTransferObject();
        try {
            if(Check.NuNStr(mobile)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("手机号为空");
                return dto.toJsonString();
            }
            if(Check.NuNStr(groupSn)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("组号为空");
                return dto.toJsonString();
            }
            //当前电话参数的未中签数量
            Long count = userActEmptyService.countByMobileAndGroupSn(mobile,groupSn);
            
            MobileCouponRequest request = new MobileCouponRequest();
            request.setMobile(mobile);
            request.setGroupSn(groupSn);
            Long acnCount = actCouponServiceImpl.getCountMobileGroupCouNum(request);
            dto.putValue("num", count+acnCount);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }


}
