package com.ziroom.minsu.services.message.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgFirstAdvisoryEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.message.api.inner.MsgFirstAdvisoryService;
import com.ziroom.minsu.services.message.dao.MsgBaseDao;
import com.ziroom.minsu.services.message.dto.MsgAdvisoryFollowRequest;
import com.ziroom.minsu.services.message.entity.MsgAdvisoryFollowVo;
import com.ziroom.minsu.services.message.service.MsgBaseServiceImpl;
import com.ziroom.minsu.services.message.service.MsgFirstAdvisoryServiceImpl;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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
 * @author wangwentao 2017/5/25
 * @version 1.0
 * @since 1.0
 */
@Component("message.msgFirstAdvisoryServiceProxy")
public class MsgFirstAdvisoryServiceProxy implements MsgFirstAdvisoryService{

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(MsgFirstAdvisoryServiceProxy.class);

    @Resource(name = "message.msgFirstAdvisoryServiceImpl")
    private MsgFirstAdvisoryServiceImpl msgFirstAdvisoryServiceImpl;

    @Resource(name = "message.messageSource")
    private MessageSource messageSource;
    
    @Value("#{'${FIRST_ADVISORY_FOLLOW_START_TIME}'.trim()}")
    private String FIRST_ADVISORY_FOLLOW_START_TIME;
    
    @Resource(name = "message.msgBaseServiceImpl")
    private MsgBaseServiceImpl msgBaseServiceImpl;

    @Override
    public String queryByMsgBaseFid(String msgBaseFid) {
        LogUtil.info(logger, "请求参数:{}", msgBaseFid);
        DataTransferObject dto = new DataTransferObject();
        if(Check.NuNStr(msgBaseFid)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto.toJsonString();
        }
        MsgFirstAdvisoryEntity msgFirstAdvisoryEntity = msgFirstAdvisoryServiceImpl.queryByMsgBaseFid(msgBaseFid);
        dto.putValue("data",msgFirstAdvisoryEntity);
        LogUtil.info(logger, "返回：{}", dto.toJsonString());
        return dto.toJsonString();
    }

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.services.message.api.inner.MsgFirstAdvisoryService#queryAllNeedFollowList(java.lang.String)
	 */
	@Override
	public String queryAllNeedFollowList(String object2Json) {
		long start = System.currentTimeMillis();
		LogUtil.info(logger, "请求参数：{}", object2Json); 
		DataTransferObject dto = new DataTransferObject();
		MsgAdvisoryFollowRequest paramRequet = JsonEntityTransform.json2Entity(object2Json, MsgAdvisoryFollowRequest.class);
		if(Check.NuNObj(paramRequet)){
			 dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	         dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
	         return dto.toJsonString();
		}
		if(atLeastOneParmaNotNull(paramRequet)){//至少有一个页面参数不为空
			if(Check.NuNCollection(paramRequet.getTenantUids()) && Check.NuNCollection(paramRequet.getLandlordUids()) && Check.NuNObj(paramRequet.getFollowStatus()) && Check.NuNCollection(paramRequet.getHouseFidList())){
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
		         dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
		         return dto.toJsonString();
			}
		}
		//houseFidList和roomFidList，当有一个为空的时候  不能使用union（都不为的时候走queryAllNeedFollowPage方法，使用整租分租union）
		PagingResult<MsgAdvisoryFollowVo> imFollowVoByPage = new PagingResult<MsgAdvisoryFollowVo>();
	/*	if(!Check.NuNCollection(paramRequet.getHouseFidList()) && !Check.NuNCollection(paramRequet.getRoomFidList())){
			imFollowVoByPage = msgFirstAdvisoryServiceImpl.queryAllNeedFollowPage(paramRequet);
		}else{
		   
		}*/
		if(!Check.NuNStr(FIRST_ADVISORY_FOLLOW_START_TIME)){
			paramRequet.setMsgSendStartTime(FIRST_ADVISORY_FOLLOW_START_TIME);
		}
		imFollowVoByPage = msgFirstAdvisoryServiceImpl.queryAllNeedFollowPageNoUnion(paramRequet);
		if(imFollowVoByPage.getTotal()<=0){
            dto.putValue("imFollowVoList", new ArrayList<MsgAdvisoryFollowVo>());
            dto.putValue("size", 0);
            return dto.toJsonString();
        }
		List<MsgAdvisoryFollowVo> rows = imFollowVoByPage.getRows();
        List<String> tenantUidList = new ArrayList<>();
        for (MsgAdvisoryFollowVo row : rows) {
        	tenantUidList.add(row.getTenantUid());
        }
		 paramRequet.setTenantUidList(tenantUidList);
		List<MsgAdvisoryFollowVo> imFollowVoList = msgFirstAdvisoryServiceImpl.queryAllNeedFollowList(paramRequet);
		List<MsgAdvisoryFollowVo> resultFollowVoList = new ArrayList<MsgAdvisoryFollowVo>();
				
		if(!Check.NuNCollection(imFollowVoList)){
			for (MsgAdvisoryFollowVo msgAdvisoryFollowVo : imFollowVoList) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("msgSenderType", UserTypeEnum.LANDLORD_HUAXIN.getUserType());
				param.put("isSystemSend",YesOrNoEnum.NO.getCode());
				param.put("msgHouseFid", msgAdvisoryFollowVo.getMsgHouseFid());
				List<MsgBaseEntity> msgBaseList =  msgBaseServiceImpl.findAllImByParam(param);
				msgAdvisoryFollowVo.setIsReplay(0);
				if(!Check.NuNCollection(msgBaseList)){
					msgAdvisoryFollowVo.setIsReplay(YesOrNoEnum.YES.getCode());
				}
				//房东未回复
				if(!Check.NuNObjs(paramRequet.getIsReplay()) && paramRequet.getIsReplay()==YesOrNoEnum.NO.getCode()){
					if(Check.NuNCollection(msgBaseList)){
						resultFollowVoList.add(msgAdvisoryFollowVo);
						continue;
					}
				}
				//房东已回复
				if(!Check.NuNObjs(paramRequet.getIsReplay()) && paramRequet.getIsReplay()==YesOrNoEnum.YES.getCode()){
					if(!Check.NuNCollection(msgBaseList)){
						resultFollowVoList.add(msgAdvisoryFollowVo);
						continue;
					}
				}
				if(Check.NuNObjs(paramRequet.getIsReplay())){
					resultFollowVoList.add(msgAdvisoryFollowVo);
				}
			}
		}	
		
		dto.putValue("imFollowVoList", resultFollowVoList);
		dto.putValue("size", imFollowVoByPage.getTotal());
		long end = System.currentTimeMillis();
		LogUtil.info(logger, "queryAllNeedFollowList  结果：{} 用时={}",resultFollowVoList,(end-start)); 
		return dto.toJsonString();
	}
	
	/**
	 * 
	 * IM跟进处理：是否页面的参数至少有一个不为空 ，
	 *
	 * @author loushuai
	 * @created 2017年6月3日 下午15:26:45
	 *
	 * @param paramRequet
	 * @return
	 */
	public boolean atLeastOneParmaNotNull(MsgAdvisoryFollowRequest paramRequet){
		if(!Check.NuNStr(paramRequet.getTenantName()) ||  !Check.NuNStr(paramRequet.getTenantTel())
				||  !Check.NuNStr(paramRequet.getLandlordName()) ||  !Check.NuNStr(paramRequet.getLandlordTel())
				||  !Check.NuNStr(paramRequet.getHouseName()) ||  !Check.NuNStr(paramRequet.getNationCode())  ||  !Check.NuNStr(paramRequet.getProvinceCode()) ||  !Check.NuNStr(paramRequet.getCityCode())
				||  !Check.NuNObj(paramRequet.getFollowStatus())){
			return true;
		}
		return false;
	}
}
