
package com.ziroom.minsu.services.message.proxy;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.message.dto.LandlordComplainRequest;
import com.ziroom.minsu.services.message.entity.SysComplainVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.SysComplainEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.SysComplainService;
import com.ziroom.minsu.services.message.service.SysComplainServiceImpl;

/**
 * <p>投诉建议的接口实现</p>
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
@Component("message.sysComplainServiceProxy")
public class SysComplainServiceProxy implements SysComplainService{
	
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SysComplainServiceProxy.class);
	
	@Resource(name = "message.messageSource")
	private MessageSource messageSource;
	
	@Resource(name = "message.sysComplainServiceImpl")
	private SysComplainServiceImpl sysComplainServiceImpl;

	/**
	 * 
	 * 保存投诉建议实体
	 *
	 * @author yd
	 * @created 2016年5月4日 上午11:53:29
	 *
	 * @param sysComplainEntity
	 * @return
	 */
	@Override
	public String save(String sysComplainEntity) {
		
		
		DataTransferObject dto = new DataTransferObject();
		SysComplainEntity sysEntity = JsonEntityTransform.json2Object(sysComplainEntity, SysComplainEntity.class);
		
		if(Check.NuNObj(sysEntity)||Check.NuNStr(sysEntity.getComplainUid())){
			
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("对象属性值错误");
			return dto.toJsonString();
		}
		LogUtil.info(logger, "保存实体sysEntity", sysEntity.toJsonStr());
		try {
			dto.putValue("result", this.sysComplainServiceImpl.save(sysEntity));
		} catch (Exception e) {
			LogUtil.error(logger, "保存实体异常e={}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("对象属性值错误");
		}
		
		return dto.toJsonString();
	}

	/**
	 *	根据条件查询
	 * @author wangwentao 2017/4/24 18:48
	 */
	@Override
	public String queryByCondition(String paramJson) {
		LogUtil.info(logger, "参数:{}", paramJson);
		DataTransferObject dto = new DataTransferObject();
		try{
			LandlordComplainRequest request = JsonEntityTransform.json2Object(paramJson, LandlordComplainRequest.class);
			PagingResult<SysComplainVo> pagingResult = sysComplainServiceImpl.queryByCondition(request);
			dto.putValue("total", pagingResult.getTotal());
			dto.putValue("list", pagingResult.getRows());
		}catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(logger, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

	/**
	 *
	 * @author wangwentao 2017/4/25 14:18
	 */
	public String selectByPrimaryKey(String id) {
		LogUtil.info(logger, "参数:{}", id);
		DataTransferObject dto = new DataTransferObject();

		if(Check.NuNStr(id)){
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.PARAM_NULL));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
			return dto.toJsonString();
		}

		try {
			Integer idInt = Integer.valueOf(id);
			SysComplainEntity sysComplainEntity = sysComplainServiceImpl.selectByPrimaryKey(idInt);
			if (!Check.NuNObj(sysComplainEntity)) {
				dto.putValue("result", sysComplainEntity);
			} else {
				dto.putValue("result", new SysComplainEntity());
			}

		} catch(Exception e){
			LogUtil.error(logger, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		LogUtil.info(logger, "结果:{}", dto.toJsonString());
		return dto.toJsonString();
	}

}
