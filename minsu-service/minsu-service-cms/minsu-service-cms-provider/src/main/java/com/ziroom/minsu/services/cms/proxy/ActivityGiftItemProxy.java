/**
 * @FileName: ActivityGiftItemProxy.java
 * @Package com.ziroom.minsu.services.cms.proxy
 * 
 * @author yd
 * @created 2016年10月9日 下午5:12:21
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.proxy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.ziroom.minsu.entity.cms.ActivityGiftItemEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityGiftItemService;
import com.ziroom.minsu.services.cms.service.ActivityGiftItemServiceImpl;
import com.ziroom.minsu.services.common.constant.MessageConst;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("cms.activityGiftItemProxy")
public class ActivityGiftItemProxy implements ActivityGiftItemService {

	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityGiftItemProxy.class);
	
	@Resource(name = "cms.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "cms.activityGiftItemServiceImpl")
	private ActivityGiftItemServiceImpl activityGiftItemServiceImpl ;
	/**
	 * 
	 * 保存活动礼物
	 *
	 * @author yd
	 * @created 2016年10月9日 下午5:06:51
	 *
	 * @param activityGiftItem
	 * @return
	 */
	@Override
	public String saveGiftItem(String activityGiftItem) {

		DataTransferObject dto = new DataTransferObject();
		
		ActivityGiftItemEntity acItem = JsonEntityTransform.json2Object(activityGiftItem, ActivityGiftItemEntity.class);
		
		if(Check.NuNObj(acItem)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("实体不存在");
			return dto.toJsonString();
		}
		int result = this.activityGiftItemServiceImpl.saveGiftItem(acItem);
		
		dto.putValue("result", result);
		return dto.toJsonString();
	}

}
