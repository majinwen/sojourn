/**
 * @FileName: FillFirstPageServiceController.java
 * @Package com.ziroom.minsu.api.common.controller
 *
 * @author yd
 * @created 2017年5月25日 上午10:42:20
 *
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.api.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ziroom.minsu.api.search.common.header.Header;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.valenum.version.VersionCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.valenum.FirstPageInfoUnLoginEnum;
import com.ziroom.minsu.api.search.controller.abs.CallableCommonImpl;
import com.ziroom.minsu.api.search.service.QueryService;
import com.ziroom.minsu.entity.search.LabelTipsEntity;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.search.api.inner.SearchService;
import com.ziroom.minsu.services.search.dto.HouseSearchRequest;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.valenum.search.LabelTipsEnum;
import com.ziroom.minsu.valenum.search.LabelTipsStyleEnum;

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
@RequestMapping("/firstPageQuery")
@Controller
public class FillFirstPageServiceController extends AbstractController{


	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FillFirstPageServiceController.class);

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	@Value("#{'${MINSU_INVENTORY_URL}'.trim()}")
	private String MINSU_INVENTORY_URL;

	@Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;

	@Resource(name = "query.queryService")
	private QueryService queryService;

	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;



	/**
	 *
	 * 首页接口整合 未登陆接口
	 *
	 * @author yd
	 * @created 2017年5月25日 下午3:42:28
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/fillFirstPageInfo")
	@ResponseBody
	public DataTransferObject  fillFirstPageInfo(HttpServletRequest request, HttpServletResponse response){


		DataTransferObject dto = new DataTransferObject();

		Future<String> top50Future =  null;
		Future<String> todayDisFuture  =  null;
		Future<String> lastHouseFuture = null;
		Future<String> minsuInventoryFuture = null;
		try {
			// TOP50列表数据
			top50Future = executeHttpRequest(FirstPageInfoUnLoginEnum.TOP50.getKey(), request, response);
			// 今日特惠数据
			todayDisFuture = executeHttpRequest(FirstPageInfoUnLoginEnum.TODAYDISCOUNT.getKey(), request, response);
			// 新上房源数据
			lastHouseFuture = executeHttpRequest(FirstPageInfoUnLoginEnum.LASTHOUSE.getKey(), request, response);
			// 民宿清单数据
			minsuInventoryFuture = executeHttpRequestByGet(MINSU_INVENTORY_URL, null);
			int flag = 0;
			while (true){
				if(flag>0){

					setTop50Data(dto, top50Future);

					setTodayDisData(dto, todayDisFuture);

					Header header = getHeader(request);

					// 在V_20170822(100020,79, "5.3.3")之后不返回新上房源数据
					if(!Check.NuNStr(header.getVersionCode()) && !VersionCodeEnum.checkCompatibleVersion(Integer.valueOf(header.getVersionCode()), VersionCodeEnum.V_20170822.getCode())){
					    setLastHouseData(dto, lastHouseFuture);
					}

					setMinsuInventoryData(dto, minsuInventoryFuture);

					break;
				}
				if (top50Future.isDone() && todayDisFuture.isDone() && lastHouseFuture.isDone() && minsuInventoryFuture.isDone()) {
					flag++;
					continue;
				}
			}
		} catch (Exception e) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("请求异常,请重试");
			LogUtil.error(LOGGER, "【首页接口数据：新上房源数据】接口异常：e={}", e);
		}finally{
			if(!Check.NuNObj(top50Future)){
				top50Future.isCancelled();
			}
			if(!Check.NuNObj(todayDisFuture)){
				todayDisFuture.isCancelled();
			}
			if(!Check.NuNObj(lastHouseFuture)){
				lastHouseFuture.isCancelled();
			}
			if (!Check.NuNObj(minsuInventoryFuture)) {
				minsuInventoryFuture.isCancelled();
			}
		}
		return dto;
	}

	/**
	 * 获取执行 线程服务
	 *
	 * @author zhangyl
	 * @created 2017/8/18 18:16
	 * @param
	 * @return
	 */
	public static ExecutorService getExecutorService(){
		return executorService;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.api.common.abs.CallableCommonImpl#executeHttpRequest(java.lang.String)
	 */
	public Future<String> executeHttpRequest(final String key,final HttpServletRequest request, final HttpServletResponse response) {
		return CallableCommonImpl.getExecutorService().submit(new Callable<String>() {
			public String call() throws Exception {
				DataTransferObject dto = new DataTransferObject();
				if(Check.NuNStr(key)){
					LogUtil.error(LOGGER, "【首页接口整合,获取数据异常】key={}", key);
					dto.setErrCode(DataTransferObject.ERROR);
					dto.setMsg("获取数据异常");
					return dto.toJsonString();
				}
				if(FirstPageInfoUnLoginEnum.TOP50.getKey().equals(key)){
					dto = queryService.top50HouseListArticles(request, response);
				}
				if(FirstPageInfoUnLoginEnum.TODAYDISCOUNT.getKey().equals(key)){
					dto = queryService.todayDiscountArticles(request, response);
				}
				if(FirstPageInfoUnLoginEnum.LASTHOUSE.getKey().equals(key)){
					dto = queryService.lasthouse(request, response);
				}
				return dto.toJsonString();
			}
		});
	}

	private Future<String> executeHttpRequestByGet(String url, final Map<String, String> headerMap) {
		return getExecutorService().submit(new Callable<String>() {
			public String call() throws Exception {
				return CloseableHttpsUtil.sendGet(url, headerMap);
			}
		});
	}

	/**
	 *
	 * TOP50列表数据
	 *
	 * @author zhangyl
	 * @created 2017年8月3日 上午11:31:26
	 *
	 * @param dto
	 * @param top50Future
	 */
	private void setTop50Data(DataTransferObject dto, Future<String> top50Future) {
		try {
			DataTransferObject top50Dto = JsonEntityTransform.json2DataTransferObject(top50Future.get());
			Map<String, Object> top50Map = new HashMap<String, Object>();
			if(top50Dto.getCode() == DataTransferObject.SUCCESS){
				top50Map = top50Dto.getData();
			}else{
				LogUtil.info(LOGGER, "【首页接口数据：TOP50列表数据】异常：code={},message={}", top50Dto.getCode(),top50Dto.getMsg());
			}
			dto.putValue(FirstPageInfoUnLoginEnum.TOP50.getKey(),top50Map);
		} catch (InterruptedException e) {
			LogUtil.error(LOGGER, "【首页接口数据：TOP50列表数据】中断异常：e={}", e);
		} catch (ExecutionException e) {
			LogUtil.error(LOGGER, "【首页接口数据：TOP50列表数据】线程异常：e={}", e);
		}
	}

	/**
	 *
	 * 今日特惠数据
	 *
	 * @author zhangyl
	 * @created 2017年8月3日 上午11:31:55
	 *
	 * @param dto
	 * @param todayDisFuture
	 */
	private void setTodayDisData(DataTransferObject dto, Future<String> todayDisFuture) {
		try {

			DataTransferObject todayDisDto = JsonEntityTransform.json2DataTransferObject(todayDisFuture.get());
			Map<String, Object> todayDisMap = new HashMap<String, Object>();
			if(todayDisDto.getCode() == DataTransferObject.SUCCESS){
				todayDisMap = todayDisDto.getData();
			}else{
				LogUtil.info(LOGGER, "【首页接口数据：今日特惠数据】异常：code={},message={}", todayDisDto.getCode(),todayDisDto.getMsg());
			}
			dto.putValue(FirstPageInfoUnLoginEnum.TODAYDISCOUNT.getKey(), todayDisMap);
		} catch (InterruptedException e) {
			LogUtil.error(LOGGER, "【首页接口数据：今日特惠数据】中断异常：e={}", e);

		} catch (ExecutionException e) {
			LogUtil.error(LOGGER, "【首页接口数据：今日特惠数据】线程异常：e={}", e);
		}
	}

	/**
	 *
	 * 新上房源数据
	 *
	 * @author zhangyl
	 * @created 2017年8月3日 上午11:32:04
	 *
	 * @param dto
	 * @param lastHouseFuture
	 */
	private void setLastHouseData(DataTransferObject dto, Future<String> lastHouseFuture) {
		try {

			DataTransferObject lastHouseDto = JsonEntityTransform.json2DataTransferObject(lastHouseFuture.get());
			Map<String, Object> lastHouseMap = new HashMap<String, Object>();
			if(lastHouseDto.getCode() == DataTransferObject.SUCCESS){
				lastHouseMap = lastHouseDto.getData();
			}else{
				LogUtil.info(LOGGER, "【首页接口数据：新上房源数据】异常：code={},message={}", lastHouseDto.getCode(),lastHouseDto.getMsg());
			}
			dto.putValue(FirstPageInfoUnLoginEnum.LASTHOUSE.getKey(), lastHouseMap);
		} catch (InterruptedException e) {
			LogUtil.error(LOGGER, "【首页接口数据：新上房源数据】中断异常：e={}", e);
		} catch (ExecutionException e) {
			LogUtil.error(LOGGER, "【首页接口数据：新上房源数据】线程异常：e={}", e);
		}
	}

	/**
	 *
	 * 民宿清单数据
	 *
	 * @author zhangyl
	 * @created 2017年8月3日 上午11:32:23
	 *
	 * @param dto
	 * @param minsuInventoryFuture
	 */
	private void setMinsuInventoryData(DataTransferObject dto, Future<String> minsuInventoryFuture) {
		try {
			String json = minsuInventoryFuture.get();
			LogUtil.info(LOGGER, "【首页接口数据：民宿清单数据】CMS接口数据：json={}", json);

			JSONObject minsuInventoryJSON = JSONObject.parseObject(json);
			if (minsuInventoryJSON.getInteger("error_code") == 0) {

				JSONObject minsuInventoryData = minsuInventoryJSON.getJSONObject("data");

				JSONArray houseArray = minsuInventoryData.getJSONArray("house");

				// CMS新增房源描述字段 20170904
				Map<String, String> descMap = new HashMap<>();

				// 解析配置的房源参数并查询房源信息
				List<HouseSearchRequest> houseSearchRequestList = new ArrayList<>();
				for (int i = 0; i < houseArray.size(); i++) {
					JSONObject house = houseArray.getJSONObject(i);
					String[] targetArray = house.getString("target").split("&");
					String fid = targetArray[0].split("=")[1];
					Integer rentWay = Integer.parseInt(targetArray[1].split("=")[1]);

					HouseSearchRequest houseSearchRequest = new HouseSearchRequest();
					houseSearchRequest.setFid(fid);
					houseSearchRequest.setRentWay(rentWay);
					houseSearchRequestList.add(houseSearchRequest);

					descMap.put(fid + "_" + rentWay, house.getString("description"));
				}
				LogUtil.info(LOGGER, "【首页接口数据：民宿清单数据】查询房源信息参数={}", houseSearchRequestList);
				String houseListRst = searchService.getHouseList(defaultPicSize, null, JsonEntityTransform.Object2Json(houseSearchRequestList));
				LogUtil.info(LOGGER, "【首页接口数据：民宿清单数据】查询房源信息返回={}", houseListRst);

				// 填充房源信息
				JSONArray houseList = new JSONArray();
				if (SOAResParseUtil.checkSOAReturnExpect(houseListRst, DataTransferObject.SUCCESS)) {
					List<HouseInfoEntity> houseInfoEntityList = SOAResParseUtil.getListValueFromDataByKey(houseListRst, "list", HouseInfoEntity.class);

					for (HouseInfoEntity houseInfoEntity : houseInfoEntityList) {

						// 标签
						Set<String> labelSet = new HashSet<>();
						for (LabelTipsEntity labelTipsEntity : houseInfoEntity.getLabelTipsList()) {
							labelSet.add(labelTipsEntity.getName());
						}

						// 今夜特价、TOP50、新上 只展示一个
						List<LabelTipsEntity> labelTipsEntityList = new ArrayList<>();
						String labelName = "";
						if (labelSet.contains(LabelTipsEnum.IS_TODAY_DISCOUNT.getName())) {
							labelName = LabelTipsEnum.IS_TODAY_DISCOUNT.getName();
						} else if (labelSet.contains(LabelTipsEnum.IS_TOP50.getName())) {
							labelName = LabelTipsEnum.IS_TOP50.getName();
						} else if (labelSet.contains(LabelTipsEnum.IS_NEW.getName())) {
							labelName = LabelTipsEnum.IS_NEW.getName();
						}
						if (!Check.NuNStr(labelName)) {
							LabelTipsEntity labelEntity = new LabelTipsEntity();
							labelEntity.setIndex(1);
							labelEntity.setTipsType(LabelTipsStyleEnum.WORDS_WITH_APP_BUTTON.getCode());
							labelEntity.setName(labelName);
							labelTipsEntityList.add(labelEntity);
						}

						JSONObject house = new JSONObject();
						String fid = houseInfoEntity.getFid();
						Integer rentWay = houseInfoEntity.getRentWay();
						house.put("fid", fid);
						house.put("rentWay", rentWay);
						house.put("price", houseInfoEntity.getPrice() / 100);
						house.put("labelTipsList", labelTipsEntityList);
						house.put("picUrl", houseInfoEntity.getPicUrl());
						house.put("isTop50Online", houseInfoEntity.getIsTop50Online());
						house.put("cityName", houseInfoEntity.getCityName());
						house.put("description", descMap.get(fid + "_" + rentWay));
						houseList.add(house);
					}
				} else {
					LogUtil.error(LOGGER, "【首页接口数据：民宿清单数据】调用dubbo查询房源服务状态异常：jsonRst={}", houseListRst);
				}

				minsuInventoryData.put("house", houseList);

				dto.putValue(FirstPageInfoUnLoginEnum.MINSUINVENTORY.getKey(), minsuInventoryData);
			} else {
				LogUtil.error(LOGGER, "【首页接口数据：民宿清单数据】CMS接口数据异常：json={}", json);
			}

		} catch (InterruptedException e) {
			LogUtil.error(LOGGER, "【首页接口数据：民宿清单数据】中断异常：e={}", e);
		} catch (ExecutionException e) {
			LogUtil.error(LOGGER, "【首页接口数据：民宿清单数据】线程异常：e={}", e);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【首页接口数据：民宿清单数据】运行异常：e={}", e);
		}
	}


}
