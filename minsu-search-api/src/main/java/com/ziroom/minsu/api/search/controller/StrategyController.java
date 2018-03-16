package com.ziroom.minsu.api.search.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.search.common.jsonp.JsonpVo;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.cms.api.inner.CityFileService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.search.api.inner.CmsSearchService;
import com.ziroom.minsu.services.search.dto.CmsArticleDetailRequest;
import com.ziroom.minsu.services.search.dto.CmsArticleRequest;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.dto.HouseListRequset;
import com.ziroom.minsu.services.search.entity.CmsArticleDetailEntity;
import com.ziroom.minsu.services.search.entity.CmsArticleEntity;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.vo.HouseListSimpleVo;
import com.ziroom.minsu.services.search.vo.Top50HouseListVo;
import com.ziroom.minsu.valenum.search.SortTypeEnum;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.ziroom.minsu.api.search.common.log.user.QueryLog;
import com.ziroom.minsu.api.search.controller.abs.AbstractController;
import com.ziroom.minsu.api.search.service.ElasticSearchService;
import com.ziroom.minsu.services.customer.api.inner.CustomerLocationService;
import com.ziroom.minsu.services.search.api.inner.SearchService;

@Controller
@RequestMapping("/strategy")
public class StrategyController extends AbstractController{

    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyController.class);
    
    /**
     * 用于记录搜索的记录
     */
    private static final Logger record = LoggerFactory.getLogger(QueryLog.class);

	@Resource(name = "search.cmsSearchService")
	private CmsSearchService cmsSearchService;

	@Resource(name = "search.searchServiceApi")
	private SearchService searchService;

	@Value("#{'${default_pic_size}'.trim()}")
	private String defaultPicSize;

	@Value("#{'${default_icon_size}'.trim()}")
	private String defaultIconSize;

	@Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;



	/**
	 * 攻略查询接口
	 * @author zl
	 * @created 2017/8/2 10:14
	 * @param
	 * @return 
	 */
    @RequestMapping(value ="/query")
    public @ResponseBody DataTransferObject query(HttpServletRequest request,HttpServletResponse response) {

		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout

		DataTransferObject dto = new DataTransferObject();

		try{

			HouseInfoRequest houseInfo = getEntity(request,HouseInfoRequest.class);
			if(Check.NuNObj(houseInfo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数为空");
				return dto;
			}

			CmsArticleRequest paramsRequest = new CmsArticleRequest();
			BeanUtils.copyProperties(paramsRequest,houseInfo);
			LogUtil.info(LOGGER,"查询攻略列表参数={}",JsonEntityTransform.Object2Json(paramsRequest));
			String detailJson = cmsSearchService.getListByConditionAndRecommend(JsonEntityTransform.Object2Json(paramsRequest));
			dto = JsonEntityTransform.json2DataTransferObject(detailJson);

		}catch (Exception e){
			LogUtil.error(LOGGER,"查询攻略列表异常：e={}",e);
		}
		
		return dto;
		
    }

    /**
     * jsonp查询攻略列表
     * @author zl
     * @created 2017/8/9 10:20
     * @param
     * @return 
     */
	@RequestMapping(value ="/jsonp/query")
	@ResponseBody
	public JsonpVo jsonpQuery(HttpServletRequest request, HttpServletResponse response ,String par){
		long t1 = System.currentTimeMillis();
		JsonpVo jsonpVo = new JsonpVo();
		String jsonpcallback=request.getParameter("callback");
		jsonpVo.setCallBack(jsonpcallback);
		DataTransferObject dto = new DataTransferObject();

		if (Check.NuNStr(par)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			jsonpVo.setDto(dto);
			return jsonpVo;
		}

		LogUtil.info(LOGGER,"jsonp查询攻略列表参数={}",par);
		CmsArticleRequest paramsRequest =null;
		try{
			HouseInfoRequest houseInfo = JsonEntityTransform.json2Entity(par,HouseInfoRequest.class);
			if(Check.NuNObj(houseInfo)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				jsonpVo.setDto(dto);
				return jsonpVo;
			}
			paramsRequest = new CmsArticleRequest();
			BeanUtils.copyProperties(paramsRequest,houseInfo);
		}catch (Exception e){
			LogUtil.error(LOGGER,"jsonp查询攻略列表参数转化异常：e={}",e);
		}

		if (Check.NuNObj(paramsRequest)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			jsonpVo.setDto(dto);
			return jsonpVo;
		}

		try{
			String detailJson = cmsSearchService.getListByConditionAndRecommend(JsonEntityTransform.Object2Json(paramsRequest));
			dto = JsonEntityTransform.json2DataTransferObject(detailJson);

		}catch (Exception e){
			LogUtil.error(LOGGER,"jsonp查询攻略列表异常：e={}",e);
		} 
		jsonpVo.setDto(dto);

		LogUtil.info(LOGGER,"jsonp查询攻略列表返回结果={}",JsonEntityTransform.Object2Json(jsonpVo));
		return jsonpVo;
	}



    /**
     * 攻略详情接口
     * @author zl
     * @created 2017/8/2 15:52
     * @param
     * @return 
     */
	@RequestMapping(value ="/detail")
	@ResponseBody
	public  DataTransferObject detail(HttpServletRequest request,HttpServletResponse response) {
		DataTransferObject dto = new DataTransferObject();
		response.addHeader( "Access-Control-Allow-Origin", "*" ); // open your api to any client
		// response.addHeader( "Access-Control-Allow-Methods", "POST" ); // a allow post
		response.addHeader( "Access-Control-Max-Age", "3000" ); // time from request to response before timeout
		String uid = getUserId(request);
 		try {

			CmsArticleDetailRequest paramsRequest = getEntity(request,CmsArticleDetailRequest.class);
			LogUtil.info(LOGGER,"查询攻略详情参数={}",JsonEntityTransform.Object2Json(paramsRequest));
			if(Check.NuNObj(paramsRequest)){
				dto = new DataTransferObject();
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("参数错误");
				return dto;
			}
			String detailJson = cmsSearchService.getArticleDetail(JsonEntityTransform.Object2Json(paramsRequest));
			dto = JsonEntityTransform.json2DataTransferObject(detailJson);
			CmsArticleDetailEntity detail = SOAResParseUtil.getValueFromDataByKey(detailJson, "articleDetail", CmsArticleDetailEntity.class);
			if (Check.NuNObj(detail)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("不存在");
				return dto;
			}

			String hotReginScenic=null;
			if(!Check.NuNCollection(detail.getBusinessAreas())){
				hotReginScenic=detail.getBusinessAreas().get(0);
			}

			List<HouseListSimpleVo> houseResultList = getHouseList(uid,hotReginScenic,detail.getCityCode());
			dto.putValue("houseList",houseResultList);

			List<CmsArticleEntity> cmsList = getCmsList(detail.getId(),detail.getCityCode(),detail.getCategory());
			dto.putValue("suggestCmsList",cmsList);

		}catch(Exception e){
			LogUtil.error(LOGGER,"查询攻略详情异常：e={}",e);
		}
		return dto;
    }



	/**
	 * jsonp查询攻略详情
	 * @author zl
	 * @created 2017/8/9 10:20
	 * @param
	 * @return
	 */
	@RequestMapping(value ="/jsonp/detail")
	@ResponseBody
	public JsonpVo jsonpDetail(HttpServletRequest request, HttpServletResponse response ,String par){
		long t1 = System.currentTimeMillis();
		JsonpVo jsonpVo = new JsonpVo();
		String jsonpcallback=request.getParameter("callback");
		jsonpVo.setCallBack(jsonpcallback);
		DataTransferObject dto = new DataTransferObject();
		String uid = getUserId(request);

		if (Check.NuNStr(par)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数为空");
			jsonpVo.setDto(dto);
			return jsonpVo;
		}

		LogUtil.info(LOGGER,"jsonp查询攻略详情参数={}",par);
		CmsArticleDetailRequest paramsRequest =null;
		try{
			paramsRequest = JsonEntityTransform.json2Entity(par,CmsArticleDetailRequest.class);
		}catch (Exception e){
			LogUtil.error(LOGGER,"jsonp查询攻略详情参数转化异常：e={}",e);
		}

		if (Check.NuNObj(paramsRequest)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("参数错误");
			jsonpVo.setDto(dto);
			return jsonpVo;
		}

		try{
			String detailJson = cmsSearchService.getArticleDetail(JsonEntityTransform.Object2Json(paramsRequest));
			dto = JsonEntityTransform.json2DataTransferObject(detailJson);
			CmsArticleDetailEntity detail = SOAResParseUtil.getValueFromDataByKey(detailJson, "articleDetail", CmsArticleDetailEntity.class);
			if (Check.NuNObj(detail)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("不存在");
				jsonpVo.setDto(dto);
				return jsonpVo;
			}

			String hotReginScenic=null;
			if(!Check.NuNCollection(detail.getBusinessAreas())){
				hotReginScenic=detail.getBusinessAreas().get(0);
			}

			List<HouseListSimpleVo> houseResultList = getHouseList(uid,hotReginScenic,detail.getCityCode());
			dto.putValue("houseList",houseResultList);

			List<CmsArticleEntity> cmsList = getCmsList(detail.getId(),detail.getCityCode(),detail.getCategory());
			dto.putValue("suggestCmsList",cmsList);

		}catch (Exception e){
			LogUtil.error(LOGGER,"jsonp查询攻略详情异常：e={}",e);
		}
		jsonpVo.setDto(dto);

		LogUtil.info(LOGGER,"jsonp查询攻略详情返回结果={}",JsonEntityTransform.Object2Json(jsonpVo));
		return jsonpVo;
	}

	/**
	 * 推荐房源
	 * @author zl
	 * @created 2017/8/9 17:01
	 * @param
	 * @return
	 */
	private List<HouseListSimpleVo> getHouseList(String uid,String hotReginScenic,String cityCode){
		List<HouseListSimpleVo> houseResultList = new ArrayList<>();
		try{
			int limit =4;
			Set<String> ids = new HashSet<>();
			HouseInfoRequest houseInfo =  new HouseInfoRequest();
			houseInfo.setQ("*:*");
			houseInfo.setLimit(limit);
			houseInfo.setCityCode(cityCode);
			houseInfo.setHotReginBusiness(hotReginScenic);
			houseInfo.setHotReginScenic(hotReginScenic);
			houseInfo.setSortType(SortTypeEnum.MARK.getCode());
			String houseJsonRst =searchService.getHouseListInfoAndSuggest(defaultPicSize, JsonEntityTransform.Object2Json(houseInfo),uid);
			DataTransferObject houseDto = JsonEntityTransform.json2DataTransferObject(houseJsonRst);
			if (houseDto!=null && DataTransferObject.SUCCESS==houseDto.getCode()) {
				List<HouseInfoEntity> list = SOAResParseUtil.getListValueFromDataByKey(houseJsonRst, "list", HouseInfoEntity.class);
				if(!Check.NuNCollection(list)){
					for (HouseInfoEntity houseInfoEntity : list) {
						HouseListSimpleVo vo = new HouseListSimpleVo();
						BeanUtils.copyProperties(vo, houseInfoEntity);
						if (!Check.NuNStr(houseInfoEntity.getTop50Title())) {
							vo.setHouseName(houseInfoEntity.getTop50Title());
						}
						vo.setPrice(getYuan(vo.getPrice()));
						houseResultList.add(vo);
						String id = vo.getFid()+vo.getRentWay();
						ids.add(id);
					}
				}
			}

			if(houseResultList.size()<limit){
				houseInfo.setHotReginScenic(null);
				houseInfo.setHotReginBusiness(null);
				houseInfo.setLimit(10);
				houseJsonRst =searchService.getHouseListInfoAndSuggest(defaultPicSize, JsonEntityTransform.Object2Json(houseInfo),uid);
				houseDto = JsonEntityTransform.json2DataTransferObject(houseJsonRst);
				if (houseDto!=null && DataTransferObject.SUCCESS==houseDto.getCode()) {
					List<HouseInfoEntity> list = SOAResParseUtil.getListValueFromDataByKey(houseJsonRst, "list", HouseInfoEntity.class);
					if(!Check.NuNCollection(list)){
						for (HouseInfoEntity houseInfoEntity : list) {
							if(houseResultList.size()>=limit) break;
							String id = houseInfoEntity.getFid()+houseInfoEntity.getRentWay();
							if(ids.contains(id)) continue;

							HouseListSimpleVo vo = new HouseListSimpleVo();
							BeanUtils.copyProperties(vo, houseInfoEntity);
							if (!Check.NuNStr(houseInfoEntity.getTop50Title())) {
								vo.setHouseName(houseInfoEntity.getTop50Title());
							}
							vo.setPrice(getYuan(vo.getPrice()));
							houseResultList.add(vo);
						}
					}
				}

			}

		}catch (Exception e){
			LogUtil.error(LOGGER,"查询推荐房源异常：e={}",e);
		}

		return houseResultList;
	}

	/**
	 * 转成元
	 * @author zl
	 * @created 2017/8/11 15:26
	 * @param
	 * @return
	 */
	private Integer getYuan(Integer fen){
		if(Check.NuNObj(fen)) return null;
		return fen/100;
	}


	/**
	 * 相似攻略
	 * @author zl
	 * @created 2017/8/9 17:06
	 * @param
	 * @return 
	 */
	private List<CmsArticleEntity> getCmsList(String id,String cityCode,String category){
		List<CmsArticleEntity> resultList = new ArrayList<>();
		try{

			Set<String> notIds = new HashSet<>();
			notIds.add(id);

			CmsArticleRequest paramsRequest = new CmsArticleRequest();
			paramsRequest.setLimit(2);
			paramsRequest.setCityCode(cityCode);
			paramsRequest.setNotIds(notIds);
			String detailJson = cmsSearchService.getListByConditionAndRecommend(JsonEntityTransform.Object2Json(paramsRequest));
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(detailJson);
			if (dto!=null && DataTransferObject.SUCCESS==dto.getCode()) {
				List<CmsArticleEntity> list = SOAResParseUtil.getListValueFromDataByKey(detailJson, "list", CmsArticleEntity.class);
				if(!Check.NuNCollection(list)){
					for (CmsArticleEntity cms:list) {
						notIds.add(cms.getId());
						resultList.add(cms);
					}
				}
			}

			paramsRequest.setCityCode(null);
			paramsRequest.setCategory(category);
			paramsRequest.setNotIds(notIds);
			detailJson = cmsSearchService.getListByConditionAndRecommend(JsonEntityTransform.Object2Json(paramsRequest));
			dto = JsonEntityTransform.json2DataTransferObject(detailJson);
			if (dto!=null && DataTransferObject.SUCCESS==dto.getCode()) {
				List<CmsArticleEntity> list = SOAResParseUtil.getListValueFromDataByKey(detailJson, "list", CmsArticleEntity.class);
				if(!Check.NuNCollection(list)){
					for (CmsArticleEntity cms:list) {
						notIds.add(cms.getId());
						resultList.add(cms);
					}
				}
			}
			int limit =4;
			if (resultList.size()<limit){
				paramsRequest.setCityCode(null);
				paramsRequest.setCategory(null);
				paramsRequest.setNotIds(notIds);
				paramsRequest.setLimit(limit-resultList.size());
				detailJson = cmsSearchService.getListByConditionAndRecommend(JsonEntityTransform.Object2Json(paramsRequest));
				dto = JsonEntityTransform.json2DataTransferObject(detailJson);
				if (dto!=null && DataTransferObject.SUCCESS==dto.getCode()) {
					List<CmsArticleEntity> list = SOAResParseUtil.getListValueFromDataByKey(detailJson, "list", CmsArticleEntity.class);
					if(!Check.NuNCollection(list)){
						resultList.addAll(list);
					}
				}


			}

		}catch (Exception e){
			LogUtil.error(LOGGER,"查询推荐攻略列表异常：e={}",e);
		}
		return resultList;
	}






}
