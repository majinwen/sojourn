package com.ziroom.minsu.troy.common.send;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.common.constant.JpushConst;
import com.ziroom.minsu.services.common.jpush.JpushUtils;
import com.ziroom.minsu.services.common.jpush.base.JpushConfig;
import com.ziroom.minsu.services.common.jpush.base.MessageTypeEnum;
import com.ziroom.minsu.services.common.sms.SendService;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * <p>极光通知消息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author yd on 2016/5/12.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "troy.jpushSendService")
public class JpushSendService implements SendService<List<String> , Integer, Map<String, String>,Map<String, String>> {

    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JpushSendService.class);

    @Override
    public boolean send(List<String>  uids, Integer template, Map<String, String> par,Map<String, String> bizPar) {
        boolean flag = false;
        try {
            if(Check.NuNObjs(template)||Check.NuNCollection(uids)){
                LogUtil.error(LOGGER,"par is empty uid:{},template:{},par:{}",uids,template,par);
                return false;
            }
            //调用短信模板
            String templateJson = smsTemplateService.getTemplateByCode(ValueUtil.getStrValue(template));
            DataTransferObject templateJsonDto = JsonEntityTransform.json2DataTransferObject(templateJson);
            SmsTemplateEntity smsTemplateEntity = templateJsonDto.parseData("template", new TypeReference<SmsTemplateEntity>() {
            });
            if(Check.NuNObj(smsTemplateEntity)){
                LogUtil.info(LOGGER, "smsTemplateEntity is empty the template is:",template);
                return false;
            }
            //获取短信内容
            if(Check.NuNStr(smsTemplateEntity.getSmsComment())){
                LogUtil.info(LOGGER, "smsComment is empty the template is:",template);
                return false;
            }
            //直接发送短息
            JpushConfig jpushConfig = new JpushConfig();
            //if(!Check.NuNObj(bizPar)){
                //带参数的为 自定义消息类型，其他的为通知类型
                jpushConfig.setMessageType(MessageTypeEnum.MESSAGE.getCode());
                jpushConfig.setExtrasMap(bizPar);
                jpushConfig.setTitle(par.get("title"));
                if(!bizPar.containsKey(JpushConst.MSG_BODY_TYPE_KEY)){
                	bizPar.put(JpushConst.MSG_BODY_TYPE_KEY, JpushConst.MSG_BODY_TYPE_VALUE);
                    bizPar.put(JpushConst.MSG_PUSH_TIME,String.valueOf(System.currentTimeMillis()));
                }
            //}
            jpushConfig.setContent(smsTemplateEntity.getSmsComment());

            LogUtil.info(LOGGER, "开始发送推送 参数：uid:{},jpushConfig:{}，bizpar:{}", JsonEntityTransform.Object2Json(uids), JsonEntityTransform.Object2Json(jpushConfig), JsonEntityTransform.Object2Json(par));
            JpushUtils.sendPushMany(uids,jpushConfig,par);
            flag = true;
        }catch (Exception e){
            LogUtil.error(LOGGER,"error:{}",e);
        }
        return flag;
    }

}
