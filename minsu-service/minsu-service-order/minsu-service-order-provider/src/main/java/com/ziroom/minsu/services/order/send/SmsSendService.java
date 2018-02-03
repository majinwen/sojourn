package com.ziroom.minsu.services.order.send;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.conf.SmsTemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.common.constant.SysConst;
import com.ziroom.minsu.services.common.sms.MessageUtils;
import com.ziroom.minsu.services.common.sms.SendService;
import com.ziroom.minsu.services.common.sms.base.SmsMessage;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.proxy.OrderUserServiceProxy;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>短信的模板发送</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/12.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "order.smsSendService")
public class SmsSendService implements SendService<String, Integer, Map<String, String>,Void> {

    @Resource(name="basedata.smsTemplateService")
    private SmsTemplateService smsTemplateService;

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSendService.class);

    @Override
    public boolean send(String phone, Integer template, Map<String, String> par,Void v) {
        boolean flag = false;
        try {
            if(Check.NuNObjs(phone,template)){
                LogUtil.error(LOGGER,"par is empty phone:{},template:{},par:{}",phone,template,par);
                return false;
            }
            //调用短信模板
            String templateJson = smsTemplateService.getTemplateByCode(ValueUtil.getStrValue(template));
            DataTransferObject templateJsonDto = JsonEntityTransform.json2DataTransferObject(templateJson);
            SmsTemplateEntity smsTemplateEntity = templateJsonDto.parseData("template", new TypeReference<SmsTemplateEntity>() {
            });
            if(Check.NuNObj(smsTemplateEntity)){
                LogUtil.info(LOGGER, "smsTemplateEntity is empty the template is",template);
                return false;
            }
            //获取短信内容
            if(Check.NuNStr(smsTemplateEntity.getSmsComment())){
                LogUtil.info(LOGGER, "smsComment is empty the template is",template);
                return false;
            }
            
            String  mobileNationCode =Check.NuNStr(par.get(SysConst.MOBILE_NATION_CODE_KEY))?SysConst.MOBILE_NATION_CODE:par.get(SysConst.MOBILE_NATION_CODE_KEY);
            //直接发送短息
            MessageUtils.sendSms(new SmsMessage(phone, smsTemplateEntity.getSmsComment(),mobileNationCode), par);
            flag = true;
        }catch (Exception e){
            LogUtil.error(LOGGER,"error:{}",e);
        }
        return flag;
    }
    
    public static void main(String[] args) {
    	Map<String, String> par = new HashMap<String, String>();
    	par.put(SysConst.MOBILE_NATION_CODE_KEY, "51");
        String  mobileNationCode =Check.NuNStr(par.get(SysConst.MOBILE_NATION_CODE_KEY))?SysConst.MOBILE_NATION_CODE:par.get(SysConst.MOBILE_NATION_CODE_KEY);
	    System.out.println(mobileNationCode);
    
    }

}
