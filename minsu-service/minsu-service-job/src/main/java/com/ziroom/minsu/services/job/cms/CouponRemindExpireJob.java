package com.ziroom.minsu.services.job.cms;

import com.asura.framework.base.context.ApplicationContext;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.quartz.job.AsuraJob;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.basedata.api.inner.SmsTemplateService;
import com.ziroom.minsu.services.basedata.dto.SmsRequest;
import com.ziroom.minsu.services.cms.api.inner.ActCouponService;
import com.ziroom.minsu.services.cms.api.inner.JobActService;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.entity.CouponUserUidVo;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.valenum.msg.MessageTemplateCodeEnum;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>房源详情领券 到期前一个月发送短信,提醒到期</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author jixd on 2017年06月16日 16:24:59
 * @since 1.0
 * @version 1.0
 */
public class CouponRemindExpireJob extends AsuraJob {
	
	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CouponRemindExpireJob.class);

	private static final String GROUP_SN = "XQYLQ1701";

	/**
	 * 优惠券活动结束job
	 * 每天一次  下午5点
	 * 0 0 17 * * ?
	 */
	@Override
	public void run(JobExecutionContext jobExecutionContext) {
        LogUtil.info(LOGGER, "CouponRemindExpireJob 开始执行.....");
        long startTime = System.currentTimeMillis();
		try {
			ActCouponService actCouponService =  (ActCouponService)ApplicationContext.getContext().getBean("job.actCouponService");
			CustomerInfoService customerInfoService =  (CustomerInfoService)ApplicationContext.getContext().getBean("job.customerInfoService");
			SmsTemplateService smsTemplateService=(SmsTemplateService) ApplicationContext.getContext().getBean("basedata.smsTemplateService");
			ActCouponRequest actCouponRequest = new ActCouponRequest();
			actCouponRequest.setGroupSn(GROUP_SN);
			int page = 1;
			while (true){
				String jsonUid = actCouponService.listOneMonthExpireUidByGroupSnPage(JsonEntityTransform.Object2Json(actCouponRequest));
				LogUtil.info(LOGGER,"page={},需要发送短信uid列表list={}",page,jsonUid);
				page ++;
				actCouponRequest.setPage(page);
				DataTransferObject dto =  JsonEntityTransform.json2DataTransferObject(jsonUid);
				if (dto.getCode() == DataTransferObject.ERROR){
					break;
				}
				List<CouponUserUidVo> list = dto.parseData("list", new TypeReference<List<CouponUserUidVo>>() {});
				if (Check.NuNCollection(list)){
					break;
				}
				for (int i = 0;i<list.size();i++){
					CouponUserUidVo couponUserUidVo = list.get(i);
					String uid = couponUserUidVo.getUid();
					DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerInfoByUid(uid));
					if (customerDto.getCode() == DataTransferObject.ERROR){
						continue;
					}
					CustomerBaseMsgEntity customerBase = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
					if (Check.NuNObj(customerBase)){
						continue;
					}
					if (Check.NuNStr(customerBase.getCustomerMobile())){
						continue;
					}
					String msgCode = ValueUtil.getStrValue(MessageTemplateCodeEnum.CMS_HOUSE_DETAIL_COUPON_EXPIRE_MSG.getCode());
					SmsRequest smsRequest  = new SmsRequest();
					smsRequest.setMobile(customerBase.getCustomerMobile());
					smsRequest.setSmsCode(msgCode);
					LogUtil.info(LOGGER, "发短信参数{}",JsonEntityTransform.Object2Json(smsRequest));
					smsTemplateService.sendSmsByCode(JsonEntityTransform.Object2Json(smsRequest));
				}
			}

			LogUtil.info(LOGGER, "CouponExpireJob 执行结束,花费时间time={}秒",(System.currentTimeMillis()-startTime)/1000);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "e:{}", e);
		}

	}

}
