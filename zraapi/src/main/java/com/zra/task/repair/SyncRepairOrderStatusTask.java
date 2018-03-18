/**
 * @FileName: SyncRepairOrderStatus.java
 * @Package com.zra.task.repair
 * 
 * @author bushujie
 * @created 2017年10月12日 上午11:29:28
 * 
 * Copyright 2011-2015 asura
 */
package com.zra.task.repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.github.pagehelper.PageInfo;
import com.zra.common.utils.HttpClientUtils;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.SecurityUtil;
import com.zra.repair.entity.ZryRepairOrder;
import com.zra.repair.entity.dto.ZryRepairOrderPageDto;
import com.zra.repair.service.ZryRepairOrderService;

/**
 * <p>同步维修单状态</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Component
public class SyncRepairOrderStatusTask {
	
	
	@Autowired
	private ZryRepairOrderService zryRepairOrderService;
	
	
	private static Logger LOGGER = LoggerFactoryProxy.getLogger(SyncRepairOrderStatusTask.class);
	
	
	/**
	 * 
	 * 同步维修单状态 两个小时执行一次
	 *
	 * @author bushujie
	 * @created 2017年10月12日 下午2:59:50
	 *
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void syncRepairOrderStatus(){
		LOGGER.info("同步维修单状态定时任务开始执行！");		
		ZryRepairOrderPageDto repairOrderPage=new ZryRepairOrderPageDto();
		repairOrderPage.setIsSync(1);
		repairOrderPage.setPageSize(50);
		repairOrderPage.setPageNum(1);
		PageInfo<ZryRepairOrder> pageInfo=zryRepairOrderService.findByPaging(repairOrderPage);
		for(int i=1;i<=pageInfo.getPages();i++){
			repairOrderPage.setPageNum(i);
			pageInfo=zryRepairOrderService.findByPaging(repairOrderPage);
			List<ZryRepairOrder> list=pageInfo.getList();
			LOGGER.info("分页查询结果数量："+list.size());	
			List<String> billNumList=new ArrayList<String>();
			for(ZryRepairOrder order:list){
				LOGGER.info("循环订单号："+order.getOrderSn());	
				billNumList.add(order.getOrderSn());
			}
	    	String billNum=StringUtils.join(billNumList.toArray(),",");
	    	Map<String, String> param=new HashMap<String, String>();
	    	param.put("billNums", billNum);
	    	LOGGER.info("调用维修参数："+param.toString());	
	    	param.put("sign",SecurityUtil.encryptParam(param, PropUtils.getString("REPAIR_ENCRYPT_KEY")));
	    	String resultJson=HttpClientUtils.doGet(PropUtils.getString("REPAIR_SEARCH_ORDER_URL"), param);
	    	LOGGER.info("调用维修结果："+resultJson);	
	    	JSONObject resultObj=JSONObject.parseObject(resultJson);
	    	JSONObject data=resultObj.getJSONObject("data");
	    	JSONArray resultList=data.getJSONArray("list");
	    	for(int j=0;j<resultList.size();j++){
	    		JSONObject object=resultList.getJSONObject(j);
	    		ZryRepairOrder order=new ZryRepairOrder();
	    		order.setOrderSn(object.getString("billNum"));
	    		order.setOrderStatus(object.getInteger("statusCode"));
	    		LOGGER.info("更新参数："+object.toJSONString());
	    		boolean flag=zryRepairOrderService.updateStatusByOrderSn(order);
	    		LOGGER.info("更新结果："+flag);
	    	}
		}
		LOGGER.info("同步维修单状态定时任务结束！");	
	}
}
