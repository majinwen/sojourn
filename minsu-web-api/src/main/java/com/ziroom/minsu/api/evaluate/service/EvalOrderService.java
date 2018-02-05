package com.ziroom.minsu.api.evaluate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ziroom.minsu.api.common.util.ApiDateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.evaluate.entity.EvaShowVo;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import com.ziroom.minsu.services.order.entity.OrderInfoVo;
import com.ziroom.minsu.valenum.common.RequestTypeEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateClientBtnStatuEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateRulesEnum;
import com.ziroom.minsu.valenum.evaluate.EvaluateStatuEnum;
import com.ziroom.minsu.valenum.evaluate.OrderEvalTypeEnum;
import com.ziroom.minsu.valenum.order.OrderEvaStatusEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/14.
 * @version 1.0
 * @since 1.0
 */
@Service("api.evalOrderService")
public class EvalOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvalOrderService.class);


    @Value("#{'${pic_base_addr_mona}'.trim()}")
    private String picBaseAddrMona;


    @Value("#{'${list_small_pic}'.trim()}")
    private String list_small_pic;

    @Resource(name = "basedata.cityTemplateService")
    private CityTemplateService cityTemplateService;


    /**
     * 将当前的订单转化成评价列表
     * @author afi
     * @param orderList
     * @return
     */
    public List<EvaShowVo> tranOrder2Eval(List<OrderInfoVo> orderList, OrderEvalRequest orderEvalRequest){
        List<EvaShowVo> list = new ArrayList<>();
        if (Check.NuNObj(orderEvalRequest)){
            return list;
        }
        if (Check.NuNObjs(orderEvalRequest.getOrderEvalType(),orderEvalRequest.getRequestType())){
            return list;
        }
        if (Check.NuNCollection(orderList)){
            return list;
        }
        for (OrderInfoVo orderInfoVo : orderList) {
            EvaShowVo evalVo = transOrderObj2EvalObj(orderInfoVo,orderEvalRequest);       
            list.add(evalVo);
        }
        return list;
    }
    
  /**
   * 
   * TODO 
   * 
   *
   * @author zl
   * @created 2017年2月3日 下午2:18:24
   *
   * @param orderInfoVo
   * @param orderEvalRequest
   * @return
   */
    public EvaShowVo transOrderObj2EvalObj(OrderInfoVo orderInfoVo, OrderEvalRequest orderEvalRequest){
    	
    	LogUtil.info(LOGGER, "订单转化参数：orderInfoVo={},orderEvalRequest={}", JsonEntityTransform.Object2Json(orderInfoVo),JsonEntityTransform.Object2Json(orderEvalRequest));
    	
    	if(Check.NuNObj(orderInfoVo) || Check.NuNObj(orderEvalRequest) || Check.NuNObj(orderEvalRequest.getRequestType())){
    		return null;
    	}
    	 
    	EvaShowVo evalVo = new EvaShowVo();
    	BeanUtils.copyProperties(orderInfoVo, evalVo);
        if(Check.NuNObj(evalVo.getHouseName())){
            evalVo.setHouseName(evalVo.getRoomName());
        }
        evalVo.setStartTimeStr(DateUtil.dateFormat(orderInfoVo.getStartTime(), "yyyy-MM-dd"));
        evalVo.setEndTimeStr(DateUtil.dateFormat(orderInfoVo.getEndTime(), "yyyy-MM-dd"));
        evalVo.setPicUrl(PicUtil.getSpecialPic(picBaseAddrMona, orderInfoVo.getPicUrl(), list_small_pic));
        evalVo.setFid(evalVo.returnFid(orderInfoVo.getHouseFid(), orderInfoVo.getRoomFid(), orderInfoVo.getBedFid()));
        int housingDay = DateSplitUtil.countDateSplit(orderInfoVo.getStartTime(), orderInfoVo.getEndTime());
        evalVo.setHousingDay(housingDay);
        evalVo.setPeopleNum(orderInfoVo.getPeopleNum());
        
        evalVo.setUserPicUrl(getUserPic( orderInfoVo,orderEvalRequest.getRequestType()));

        if(orderEvalRequest.getRequestType() == RequestTypeEnum.TENANT.getRequestType()){
        	
        	if(orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.UESR_HVA_EVA.getCode() || orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.ALL_EVA.getCode()){
	             evalVo.setEvaTips("已评价");
	         }else if(!Check.NuNObj(orderInfoVo.getRealEndTime()) && ApiDateUtil.isEvaExpire(orderInfoVo.getRealEndTime(),getEvalTimeDay())){
	             evalVo.setEvaTips("未评价，已过期");
	         }else {
	             evalVo.setEvaTips("未评价");
	         }	     	 
	     }else if(orderEvalRequest.getRequestType() == RequestTypeEnum.LANDLORD.getRequestType()){ 
	    	 if(orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.LANLORD_EVA.getCode() || orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.ALL_EVA.getCode()){
	             evalVo.setEvaTips("已评价");
	         }else if(!Check.NuNObj(orderInfoVo.getRealEndTime()) && ApiDateUtil.isEvaExpire(orderInfoVo.getRealEndTime(),getEvalTimeDay())){
	             evalVo.setEvaTips("未评价，已过期");
	         }else {
	             evalVo.setEvaTips("未评价");
	         }
	    	 //房东待评价状态订单，开始时间和结束时间格式
	    	 if(orderEvalRequest.getOrderEvalType()==OrderEvalTypeEnum.WAITING.getCode()){
	    		 evalVo.setStartTimeStr(DateUtil.dateFormat(orderInfoVo.getStartTime(), "yyyy/MM/dd"));
	    	     evalVo.setEndTimeStr(DateUtil.dateFormat(orderInfoVo.getEndTime(), "yyyy/MM/dd"));
	    	 }
	     }

        EvaluateClientBtnStatuEnum pjStatusEnum = getPjStatusEnum(orderInfoVo,orderEvalRequest.getRequestType());
        //2017-11。27号版本，房东首页改版, 添加以下规则
        //  100待评价, 既展示“邀请Ta评价”按钮，又展示“写评价”按钮  
        //  101用户已评价, 展示“写评价”按钮 , 不展示“邀请Ta评价”按钮
        //  110房东已评价，  展示“邀请Ta评价”按钮， 不 展示“写评价”按钮  
        if(orderEvalRequest.getRequestType() == RequestTypeEnum.LANDLORD.getRequestType()){
        	int limitDay =  getEvalTimeDay();
            if((orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.WAITINT_EVA.getCode() || orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.UESR_HVA_EVA.getCode()
         			|| orderInfoVo.getEvaStatus()==OrderEvaStatusEnum.LANLORD_EVA.getCode())
                     && !Check.NuNObj(orderInfoVo.getRealEndTime()) && !ApiDateUtil.isEvaExpire(orderInfoVo.getRealEndTime(),limitDay)){
         		pjStatusEnum =EvaluateClientBtnStatuEnum.C_EVAL;
         		//100待评价，或者110房东已评价，展示“邀请Ta评价”按钮，不 展示“写评价”按钮
         		if(orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.WAITINT_EVA.getCode() || orderInfoVo.getEvaStatus()==OrderEvaStatusEnum.LANLORD_EVA.getCode()){
         			if(orderInfoVo.getEvaStatus()==OrderEvaStatusEnum.LANLORD_EVA.getCode()){
         				pjStatusEnum =null;
         			}
         			evalVo.setInvitEvalButton(EvaluateClientBtnStatuEnum.INVITE_EVAL.getEvaStatuName());
         			evalVo.setLandlordUid(orderInfoVo.getLandlordUid());
         			evalVo.setUserUid(orderInfoVo.getUserUid());
         			evalVo.setRentWay(orderInfoVo.getRentWay());
         		}
         	}
            
            
        }
     		
        if(!Check.NuNObj(pjStatusEnum)){        	
        	evalVo.setPjStatus(pjStatusEnum.getEvaStatuCode());
        	evalVo.setPjButton(pjStatusEnum.getEvaStatuName());
        }

        //房东取消
        if (orderInfoVo.getOrderStatus() == OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus()){
            evalVo.setPjStatus(pjStatusEnum.getEvaStatuCode());
            evalVo.setPjButton(pjStatusEnum.getEvaStatuName());
            evalVo.setEvaTips("");
        }
        
    	return evalVo;
    }
    
    /**
     * 获取评价的图片地址
     * 房东的列表返回的是房客的头像，房客的列表返回的是 
     * TODO
     *
     * @author zl
     * @created 2017年2月8日 下午2:06:21
     *
     * @return
     */
    public String getUserPic(OrderInfoVo orderInfoVo, int requestType){
    	if(Check.NuNObj(orderInfoVo)){
   		 	return null;
   	 	}
    	
    	if(requestType == RequestTypeEnum.LANDLORD.getRequestType()){
    		 
    		return orderInfoVo.getTenantPicUrl();
    	}else if(requestType == RequestTypeEnum.TENANT.getRequestType()){ 
    		 
    		return orderInfoVo.getLandPicUrl();
    	}
    	
    	return null;
    }
    
    
    /**
     * 
     * TODO
     * 获取评价客户端按钮状态
     * @author zl
     * @created 2017年2月3日 下午2:27:54
     *
     * @param orderInfoVo
     * @param requestType
     * @return
     */
    public EvaluateClientBtnStatuEnum getPjStatusEnum(OrderInfoVo orderInfoVo, int requestType){
    	
    	 if(Check.NuNObj(orderInfoVo)){
    		 return null;
    	 }
        int limitDay =  getEvalTimeDay();
    	 EvaluateClientBtnStatuEnum pjStatusEnum =null;
    	 
		 if(requestType == RequestTypeEnum.LANDLORD.getRequestType()){
	     	
	     	if((orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.WAITINT_EVA.getCode() || orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.UESR_HVA_EVA.getCode())
	                 && !Check.NuNObj(orderInfoVo.getRealEndTime()) && !ApiDateUtil.isEvaExpire(orderInfoVo.getRealEndTime(),limitDay)){
	     		
	     		pjStatusEnum =EvaluateClientBtnStatuEnum.C_EVAL;

	     	}else if(orderInfoVo.getEvaStatus() != OrderEvaStatusEnum.NO_EVA.getCode() && Check.NuNObj(orderInfoVo.getLandReplyTime())
	                 && !Check.NuNObj(orderInfoVo.getRealEndTime()) && this.isLandCanRepply(orderInfoVo)){
	             
	     		pjStatusEnum =EvaluateClientBtnStatuEnum.C_REPLAY;
	     		
	         }else{
	        	 
	        	 pjStatusEnum =EvaluateClientBtnStatuEnum.N_EVAL;
	         }
	     	
	     }else if(requestType == RequestTypeEnum.TENANT.getRequestType()){ 
	     	
	     	if((orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.WAITINT_EVA.getCode() || orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.LANLORD_EVA.getCode())
	                 && ((orderInfoVo.getOrderStatus()==OrderStatusEnum.CHECKED_IN.getOrderStatus() || orderInfoVo.getOrderStatus()==OrderStatusEnum.CHECKED_IN_BILL.getOrderStatus()) 
	                		|| (!Check.NuNObj(orderInfoVo.getRealEndTime()) && !ApiDateUtil.isEvaExpire(orderInfoVo.getRealEndTime(),limitDay))) 
	                	){
	             
	     		pjStatusEnum =EvaluateClientBtnStatuEnum.C_EVAL;  
	         
	     	}else if((orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.ALL_EVA.getCode() || orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.UESR_HVA_EVA.getCode())){
	             
	     		pjStatusEnum =EvaluateClientBtnStatuEnum.S_EVAL;  
	         
	     	}else{
	     		pjStatusEnum =EvaluateClientBtnStatuEnum.N_EVAL;
	        }
	     	
	     }
		 
		 return pjStatusEnum;
    }
    


    /**
     * 获取评价配置天数
     * @author lishaochuan
     * @create 2017/1/20 17:45
     * @param 
     * @return 
     */
    private int getEvalTimeDay(){
        /** 获取评价时间*/
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum003.getValue()));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
            LogUtil.error(LOGGER, "获取评价时间,timeStrJson:{}", timeStrJson);
            throw new BusinessException("获取评价时间");
        }
        int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        return limitDay;
    }
    
    
    
    /**
     * 判断评价是否可以展示
     * @author zl
     * @create 2017/2/3 10:13
     * @param 
     * @return 
     */
    public boolean isEvaCanShow(OrderInfoVo orderInfoVo){
        if(Check.NuNObj(orderInfoVo)){
            return false;
        }
        
        Date evaShowTime = getEvaShowTime(orderInfoVo);
        if(Check.NuNObj(evaShowTime)){
        	return false;
        }
        
        return evaShowTime.before(new Date())?true:false;            
    }
    
    
    /**
     * 获取评价展示的时间点
     * @author zl
     * @create 2017/2/3 10:43
     * @param 
     * @return 
     */
    public Date getEvaShowTime(OrderInfoVo orderInfoVo){
        
    	Date date = null;
    	
    	if(Check.NuNObj(orderInfoVo)){
            return null;
        }
        
    	if(orderInfoVo.getEvaStatus() == OrderEvaStatusEnum.ALL_EVA.getCode() 
    			&& !Check.NuNObj(orderInfoVo.getLandEvaStatu())&& orderInfoVo.getLandEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode() 
    			&& !Check.NuNObj(orderInfoVo.getTenantEvaStatu()) && orderInfoVo.getTenantEvaStatu()==EvaluateStatuEnum.ONLINE.getEvaStatuCode()){//都评价且审核返回最后一个审核时间
    	   
    		Date landEvalTime = orderInfoVo.getLandEvalTime();    	    
    	    Date tenantEvalTime = orderInfoVo.getTenantEvalTime(); 
    	    if (!Check.NuNObj(landEvalTime) && !Check.NuNObj(tenantEvalTime)) {
    	    	date = landEvalTime.before(tenantEvalTime)?tenantEvalTime:landEvalTime;
			}
    	    
        }else{//返回过期展示的时间点
        	int limitDay = getEvalShowLimitDay();        	
        	Date realEndTime = orderInfoVo.getRealEndTime();
        	
        	date = DateUtil.getTime(realEndTime, limitDay);
        }
    	
    	return date;        
    }
    
    
    /**
     * 判断房东是否可以填写公开回复
     * @author zl
     * @create 2017/2/3 10:20
     * @param 
     * @return 
     */
    public boolean isLandCanRepply(OrderInfoVo orderInfoVo){
        if(Check.NuNObj(orderInfoVo)){
            return false;
        }
        
        if( Check.NuNObj(orderInfoVo.getTenantEvaStatu())|| orderInfoVo.getTenantEvaStatu()!=EvaluateStatuEnum.ONLINE.getEvaStatuCode()){//房客评价未通过审核
        	return false;
        }
        
        Date evaShowTime = getEvaShowTime(orderInfoVo);

        if(Check.NuNObj(evaShowTime)){
        	return false;
        }
        int landCanRepplyDay =  getLandCanRepplyDay();
        if( isEvaCanShow(orderInfoVo) && (new Date().getTime()-evaShowTime.getTime()) < landCanRepplyDay * 24 * 3600 * 1000){
        	return true;
        } 
        
        return false;
        
    }
    
    /**
     * 获取订单结束后展示评价天数
     * @author zl
     * @create 2017/2/3 10:22
     * @param 
     * @return 
     */
    public int getEvalShowLimitDay(){
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum005.getValue()));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
            LogUtil.error(LOGGER, "获取订单结束后展示评价天数,timeStrJson:{}", timeStrJson);
            throw new BusinessException("获取订单结束后展示评价天数失败！");
        }
        int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        return limitDay;
    }
    
    
    /**
     * 获取房东公开回复有效配置天数
     * @author zl
     * @create 2017/2/3 10:22
     * @param 
     * @return 
     */
    public int getLandCanRepplyDay(){
        String timeStrJson = cityTemplateService.getTextValue(null, String.valueOf(EvaluateRulesEnum.EvaluateRulesEnum004.getValue()));
        DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(timeStrJson);
        if(resultDto.getCode() != DataTransferObject.SUCCESS){
            LogUtil.error(LOGGER, "获取房东公开回复有效配置天数,timeStrJson:{}", timeStrJson);
            throw new BusinessException("获取房东公开回复有效配置天数失败！");
        }
        int limitDay = ValueUtil.getintValue(resultDto.getData().get("textValue"));
        return limitDay;
    }

}
