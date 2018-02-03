package com.ziroom.minsu.services.message.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.MsgAdvisoryFollowupService;
import com.ziroom.minsu.services.message.service.MsgAdvisoryFollowupServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/25
 * @version 1.0
 * @since 1.0
 */
@Component("message.msgAdvisoryFollowupServiceProxy")
public class MsgAdvisoryFollowupServiceProxy implements MsgAdvisoryFollowupService{

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(MsgAdvisoryFollowupServiceProxy.class);

    @Resource(name = "message.msgAdvisoryFollowupServiceImpl")
    private MsgAdvisoryFollowupServiceImpl msgAdvisoryFollowupServiceImpl;

    @Resource(name = "message.messageSource")
    private MessageSource messageSource;


    @Override
    public String save(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        MsgAdvisoryFollowupEntity msgAdvisoryFollowupEntity = JsonEntityTransform.json2Entity(paramJson, MsgAdvisoryFollowupEntity.class);
        if(Check.NuNObj(msgAdvisoryFollowupEntity)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("msgAdvisoryFollowupEntity is null");
            return dto.toJsonString();
        }
        if (Check.NuNStr(msgAdvisoryFollowupEntity.getMsgFirstAdvisoryFid())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("msgFirstAdvisoryFid is null");
            return dto.toJsonString();
        }
        if (Check.NuNStr(msgAdvisoryFollowupEntity.getMsgFirstAdvisoryFid())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("msgFirstAdvisoryFid is null");
            return dto.toJsonString();
        }
        if (Check.NuNObj(msgAdvisoryFollowupEntity.getBeforeStatus())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("beforeStatus is null");
            return dto.toJsonString();
        }
        if (Check.NuNObj(msgAdvisoryFollowupEntity.getAfterStatus())) {
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("afterStatus is null");
            return dto.toJsonString();
        }
        dto.putValue("result", this.msgAdvisoryFollowupServiceImpl.save(msgAdvisoryFollowupEntity));
        return dto.toJsonString();
    }

    @Override
    public String getByFid(String fid) {
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(fid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("fid is null");
            return dto.toJsonString();
        }
        MsgAdvisoryFollowupEntity msgAdvisoryFollowupEntity = this.msgAdvisoryFollowupServiceImpl.getByFid(fid);
        dto.putValue("result", msgAdvisoryFollowupEntity);
        return dto.toJsonString();
    }

    @Override
    public String getAllByFisrtAdvisoryFid(String msgAdvisoryfid) {
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(msgAdvisoryfid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg("msgAdvisoryfid is null");
            return dto.toJsonString();
        }
        List<MsgAdvisoryFollowupEntity> list = this.msgAdvisoryFollowupServiceImpl.getAllByFisrtAdvisoryFid(msgAdvisoryfid);
        dto.putValue("list", list);
        return dto.toJsonString();
    }
}
