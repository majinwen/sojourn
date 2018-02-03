package com.ziroom.minsu.services.cms.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.cms.ActivityChristmasApplyEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityChristmasApplyService;
import com.ziroom.minsu.services.cms.dto.ChristmasApplyRequest;
import com.ziroom.minsu.services.cms.service.ActivityChristmasApplyImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/9 11:40
 * @version 1.0
 * @since 1.0
 */
@Service("cms.activityChristmasApplyProxy")
public class ActivityChristmasApplyProxy implements ActivityChristmasApplyService {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityChristmasApplyProxy.class);

    @Resource(name = "cms.messageSource")
    private MessageSource messageSource;

    @Resource(name = "cms.activityChristmasApplyImpl")
    private ActivityChristmasApplyImpl activityChristmasApplyImpl;

    /**
     * 保存申请预定信息
     *
     * @param paramJson
     * @return
     * @author lishaochuan
     * @create 2016/12/9 11:41
     */
    @Override
    public String apply(String paramJson) {
        LogUtil.info(LOGGER, "参数:{}", paramJson);
        DataTransferObject dto = new DataTransferObject();
        try {
            ChristmasApplyRequest request = JsonEntityTransform.json2Object(paramJson, ChristmasApplyRequest.class);

            ActivityChristmasApplyEntity christmasApply = new ActivityChristmasApplyEntity();
            christmasApply.setMoblie(request.getMobile());
            christmasApply.setActivityDate(DateUtil.parseDate(request.getActivityDate(), "yyyy-MM-dd"));
            ActivityChristmasApplyEntity activityChristmasApplyEntity = activityChristmasApplyImpl.selectByCondition(christmasApply);
            if(!Check.NuNObj(activityChristmasApplyEntity)){
    			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
    			dto.setMsg("我们已经收到你的申请了哦，下拉页面领取民宿优惠券，或是换一个奇妙夜申请吧！");
    			return dto.toJsonString();
            }

            christmasApply.setHouseSn(request.getHouseSn());
            christmasApply.setHouseName(request.getHouseName());
            christmasApply.setApplyReason(request.getApplyReason());
            activityChristmasApplyImpl.save(christmasApply);


        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "结果:{}", dto.toJsonString());
        return dto.toJsonString();
    }
}
