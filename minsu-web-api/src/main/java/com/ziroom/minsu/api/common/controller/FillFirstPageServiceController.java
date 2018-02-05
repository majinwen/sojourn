/**
 * @FileName: FillFirstPageServiceController.java
 * @Package com.ziroom.minsu.api.common.controller
 *
 * @author yd
 * @created 2017年5月25日 上午10:42:20
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.CallableCommonImpl;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.valeenum.FirstPageInfoLoginEnum;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.order.api.inner.OrderCommonService;
import com.ziroom.minsu.services.order.dto.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <p>首页服务请求</p>
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
@RequestMapping("firstPageService")
@Controller
public class FillFirstPageServiceController extends CallableCommonImpl{

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FillFirstPageServiceController.class);

	@Resource(name="customer.customerInfoService")
	private CustomerInfoService customerInfoService;

	@Resource(name="order.orderCommonService")
	private OrderCommonService orderCommonService;


	/**
	 * @description: api首页接口整合 登陆接口
	 * @author: lusp
	 * @date: 2017/7/27 16:49
	 * @params: request
	 * @return:
	 */
	@RequestMapping("/${LOGIN_AUTH}/fillFirstPageInfo")
	@ResponseBody
	public ResponseEntity<ResponseSecurityDto>  fillFirstPageInfo(HttpServletRequest request){
		DataTransferObject dto = new DataTransferObject();
		Future<JSONObject> whetherLandlordFuture = null;
		try {
			String uid = (String) request.getAttribute("uid");
			if(Check.NuNStrStrict(uid)){
				LogUtil.error(LOGGER,"fillFirstPageInfo(),首页整合接口uid为空，paramJson:{}",request.toString());
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数异常");
				return new ResponseEntity<>(ResponseSecurityDto.responseUnEncrypt(dto), HttpStatus.OK);
			}

			//是否为房东，以及是否有订单
			whetherLandlordFuture = whetherLandlord(uid);

			int flag = 0;
			while (true){
				if(flag>0){
					if(whetherLandlordFuture.isDone()){
						try {
							JSONObject whetherLandlordJson = whetherLandlordFuture.get();
							dto.putValue(FirstPageInfoLoginEnum.WHETHERLANDLORD.getKey(),whetherLandlordJson);
						} catch (InterruptedException e) {
							LogUtil.error(LOGGER, "【首页接口数据：是否为房东】中断异常：e={}", e);
						} catch (ExecutionException e) {
							LogUtil.error(LOGGER, "【首页接口数据：是否为房东】线程异常：e={}", e);
						}
					}
					flag = 0;
					break;
				}
				if(whetherLandlordFuture.isDone()){
					flag++;
					continue;
				}
			}
		} catch (Exception e) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请求异常,请重试");
			LogUtil.error(LOGGER, "【首页接口数据】接口异常：e={}", e);
		}finally{
			if(!Check.NuNObj(whetherLandlordFuture)){
				whetherLandlordFuture.isCancelled();
			}
		}
		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(dto.getData()), HttpStatus.OK);
	}


	/* (non-Javadoc)
	 * @see com.ziroom.minsu.api.common.abs.CallableCommonImpl#executeHttpRequest(java.lang.String)
	 */
	@Override
	public Future<String> executeHttpRequest(final String url) {
		return CallableCommonImpl.getExecutorService().submit(new Callable<String>() {
			public String call() throws Exception {
				return CloseableHttpsUtil.sendPost(url);
			}
		});
	}

	/**
	 * @description: 根据uid判断是否是民宿房东，以及是否有订单
	 * @author: lusp
	 * @date: 2017/7/27 10:16
	 * @params: uid
	 * @return:
	 */
	public Future<JSONObject> whetherLandlord(String uid){
		return CallableCommonImpl.getExecutorService().submit(
				new Callable<JSONObject>() {
					@Override
					public JSONObject call() throws Exception {
						JSONObject resultJson = new JSONObject();
						resultJson.put("whetherLandlord",0);
						resultJson.put("hasOrder",0);
						try {
							if(Check.NuNStrStrict(uid)){
								LogUtil.error(LOGGER,"whetherLandlord(),用户uid为空，uid:{}",uid);
								return resultJson;
							}
							String customerInfoString = customerInfoService.getCustomerInfoByUid(uid);
							DataTransferObject customerInfoDto = JsonEntityTransform.json2DataTransferObject(customerInfoString);
							if(customerInfoDto.getCode() == DataTransferObject.ERROR){
								LogUtil.error(LOGGER,"whetherLandlord(),根据用户uid 获取用户基本信息失败，errorMsg:{}",customerInfoDto.getMsg());
								return resultJson;
							}
							CustomerBaseMsgEntity customerBaseMsgEntity = SOAResParseUtil.getValueFromDataByKey(customerInfoString,"customerBase",CustomerBaseMsgEntity.class);
							if(Check.NuNObj(customerBaseMsgEntity)){
								LogUtil.error(LOGGER,"whetherLandlord(),该用户不存在，uid:{}",uid);
								return resultJson;
							}
							Integer isLandlord = customerBaseMsgEntity.getIsLandlord();
							if(Check.NuNObj(isLandlord)){
								return resultJson;
							}
							if(isLandlord == 1){
								resultJson.put("whetherLandlord",1);
								OrderRequest orderRequest = new OrderRequest();
								orderRequest.setLandlordUid(uid);
								orderRequest.setRequestType(2);
								orderRequest.setPage(1);
								orderRequest.setLimit(1);
								String orderListString = orderCommonService.getOrderListByCondiction(JsonEntityTransform.Object2Json(orderRequest));
								DataTransferObject orderDto = JsonEntityTransform.json2DataTransferObject(orderListString);
								if(orderDto.getCode() == DataTransferObject.ERROR){
									LogUtil.error(LOGGER,"whetherLandlord(),根据用户uid获取房东订单集合失败，errorMsg:{}",orderDto.getMsg());
									return resultJson;
								}
								Long sizeL = SOAResParseUtil.getLongFromDataByKey(orderListString,"size");
								if(sizeL>0){
									resultJson.put("hasOrder",1);
								}
							}
						}catch (Exception e){
							LogUtil.error(LOGGER,"error:{}",e);
						}
						return resultJson;
					}
				}
		);
	}




}
