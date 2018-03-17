/**
 * @FileName: HouseTopController.java
 * @Package com.ziroom.minsu.mapp.house.top
 * 
 * @author yd
 * @created 2017年3月20日 上午9:43:33
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.mapp.house.top.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.mapp.common.entity.ResponseDto;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.house.entity.HouseDetailTopVo;
import com.ziroom.minsu.services.house.entity.HouseTopColumnVo;
import com.ziroom.minsu.valenum.house.ColumnStyleEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;

/**
 * <p>top50 房源控制层</p>
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
@Controller
@RequestMapping("houseTop")
public class HouseTopController {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HouseTopController.class);

	@Value("#{'${TOP50_HOUSEDETAIL_URL}'.trim()}")
	private String TOP50_HOUSEDETAIL_URL;

	@Value("#{'${TOP50_LIST_INIT_URL}'.trim()}")
	private String TOP50_LIST_INIT_URL;

	@Value("#{'${TOP50_HOUSE_LIST_URL}'.trim()}")
	private String TOP50_HOUSE_LIST_URL;
	
	@Value("#{'${TOP50_HOUSE_SIMILAR_URL}'.trim()}")
	private String TOP50_HOUSE_SIMILAR_URL;

	@Value("#{'${TOP50_HOUSE_BANGDAN_URL}'.trim()}")
	private String TOP50_HOUSE_BANGDAN_URL;
	/**
	 * 
	 * top50的房源详情页
	 *
	 * @author yd
	 * @created 2017年3月20日 上午9:49:11
	 *
	 * @param request
	 * @param houseDetailDto
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/houseDetail")
	public String houseTopDetail(HttpServletRequest request){

		String fid = request.getParameter("fid");
		String rentWayStr = request.getParameter("rentWay");

		if(Check.NuNStr(fid)||Check.NuNStr(rentWayStr)){
			LogUtil.error(LOGGER, "【houseTopDetail】参数错误:fid={},rentWay={}",fid, rentWayStr);
			return "error/error";
		}

		try {
			int rentWay = Integer.valueOf(rentWayStr);
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);

			if(Check.NuNObj(rentWayEnum)){
				LogUtil.error(LOGGER, "【houseTopDetail】出租方式错误错误:fid={},rentWay={}",fid, rentWay);
				return "error/error";
			}
			Map<String, String> param = new HashMap<String, String>();

			param.put("fid", fid);
			param.put("rentWay",rentWayStr);

			String houseDetailJson = CloseableHttpsUtil.sendFormPost(TOP50_HOUSEDETAIL_URL, param);

			if(Check.NuNStr(houseDetailJson)){
				LogUtil.error(LOGGER, "【houseTopDetail】接口请求异常:TOP50_HOUSEDETAIL_URL={},param={}",TOP50_HOUSEDETAIL_URL, JsonEntityTransform.Object2Json(param));
				return "error/error";
			}

			ResponseDto dto = JsonEntityTransform.json2Object(houseDetailJson, ResponseDto.class);
			if(!Check.NuNStr(dto.getStatus())&&dto.getStatus().equals(DataTransferObject.ERROR)
					||Check.NuNObj(dto.getData())){
				LogUtil.error(LOGGER, "【houseTopDetail】接口请求错误:TOP50_HOUSEDETAIL_URL={},param={},msg={},data={}",TOP50_HOUSEDETAIL_URL, JsonEntityTransform.Object2Json(param),dto.getMessage(),dto.getData());
				return "error/error";
			}

			HouseDetailTopVo houseDetailTopVo  = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(dto.getData()), HouseDetailTopVo.class);
			setHouseTopColumnVo(request, houseDetailTopVo);
			request.setAttribute("houseDetailVo", houseDetailTopVo); 
			request.setAttribute("TOP50_HOUSE_SIMILAR_URL", TOP50_HOUSE_SIMILAR_URL);
			request.setAttribute("rentWay", rentWayStr);
			request.setAttribute("fid", fid);
			request.setAttribute("TOP50_HOUSE_BANGDAN_URL", TOP50_HOUSE_BANGDAN_URL); 

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【houseTopDetail】接口请求异常:TOP50_HOUSEDETAIL_URL={},e={}",TOP50_HOUSEDETAIL_URL,e);
			return "error/error";
		}

		return "/house/houseTop/houseTopDetail";
	}


	/**
	 *  
	 * top50榜单
	 *
	 * @author zl
	 * @created 2017年3月20日 下午8:12:01
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/top50List")
	public String top50List(HttpServletRequest request){


		try {

			Map<String, String> param = new HashMap<String, String>();		
			
			String listInitJson = CloseableHttpUtil.sendFormPost(TOP50_LIST_INIT_URL, param);
			
			if(Check.NuNStr(listInitJson)){
				LogUtil.error(LOGGER, "top50榜单页初始化数据请求异常:URL={}",TOP50_LIST_INIT_URL);
				return "error/error";
			}

			ResponseDto rsp = JsonEntityTransform.json2Object(listInitJson, ResponseDto.class);
			if (!Check.NuNObj(rsp) && rsp.getStatus().equals(ResponseDto.SUCCESS) ) { 

				JSONObject data = SOAResParseUtil.getJsonObj(JsonEntityTransform.Object2Json(rsp.getData()));
				
				request.setAttribute("top50ListTitleBackground", data.get("top50ListTitleBackground")); 
				request.setAttribute("top50ListShareUrl", data.get("top50ListShareUrl")); 
				request.setAttribute("top50ListShareTitle", data.get("top50ListShareTitle")); 
				request.setAttribute("top50ListShareContent", data.get("top50ListShareContent")); 
				request.setAttribute("top50ListShareImgSrc", data.get("top50ListShareImgSrc")); 

			}else{
				LogUtil.error(LOGGER, "top50榜单页初始化数据失败:URL={},result={}",TOP50_LIST_INIT_URL,listInitJson);
			} 	
		} catch (Exception e) {
			LogUtil.error(LOGGER, "top50榜单初始化异常，e={}",e);
		}

		request.setAttribute("TOP50_HOUSE_LIST_URL", TOP50_HOUSE_LIST_URL); 

		return "/house/houseTop/top50list";
	}


	/**
	 *  
	 * top50榜单
	 *
	 * @author zl
	 * @created 2017年3月20日 下午8:42:35
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/top50SimpleDetail")
	public String top50SimpleDetail(HttpServletRequest request){



		String fid = request.getParameter("fid");
		String rentWayStr = request.getParameter("rentWay");

		if(Check.NuNStr(fid)||Check.NuNStr(rentWayStr)){
			LogUtil.error(LOGGER, "【top50SimpleDetail】参数错误:fid={},rentWay={}",fid, rentWayStr);
			return "error/error";
		}

		try {
			int rentWay = Integer.valueOf(rentWayStr);
			RentWayEnum rentWayEnum = RentWayEnum.getRentWayByCode(rentWay);

			if(Check.NuNObj(rentWayEnum)){
				LogUtil.error(LOGGER, "【top50SimpleDetail】出租方式错误错误:fid={},rentWay={}",fid, rentWay);
				return "error/error";
			}
			Map<String, String> param = new HashMap<String, String>();

			param.put("fid", fid);
			param.put("rentWay",rentWayStr);

			String houseDetailJson = CloseableHttpUtil.sendFormPost(TOP50_HOUSEDETAIL_URL, param);

			if(Check.NuNStr(houseDetailJson)){
				LogUtil.error(LOGGER, "【top50SimpleDetail】接口请求异常:TOP50_HOUSEDETAIL_URL={},param={}",TOP50_HOUSEDETAIL_URL, JsonEntityTransform.Object2Json(param));
				return "error/error";
			}

			ResponseDto dto = JsonEntityTransform.json2Object(houseDetailJson, ResponseDto.class);
			if(!Check.NuNStr(dto.getStatus())&&dto.getStatus().equals(DataTransferObject.ERROR)
					||Check.NuNObj(dto.getData())){
				LogUtil.error(LOGGER, "【top50SimpleDetail】接口请求错误:TOP50_HOUSEDETAIL_URL={},param={},msg={},data={}",TOP50_HOUSEDETAIL_URL, JsonEntityTransform.Object2Json(param),dto.getMessage(),dto.getData());
				return "error/error";
			}

			HouseDetailTopVo houseDetailTopVo  = JsonEntityTransform.json2Object(JsonEntityTransform.Object2Json(dto.getData()), HouseDetailTopVo.class);
			
			request.setAttribute("houseDetailVo", houseDetailTopVo); 

		} catch (Exception e) {
			LogUtil.error(LOGGER, "【top50SimpleDetail】接口请求异常:TOP50_HOUSEDETAIL_URL={},e={}",TOP50_HOUSEDETAIL_URL,e);
			return "error/error";
		}


		return "/house/houseTop/top50SimpleDetail";
	}

	/**
	 * 
	 * 设置top50 房源特殊属性
	 *
	 * @author yd
	 * @created 2017年3月21日 下午3:03:03
	 *
	 * @param request
	 * @param houseDetailTopVo
	 */
	private void setHouseTopColumnVo(HttpServletRequest request,HouseDetailTopVo houseDetailTopVo){

		if(!Check.NuNObjs(houseDetailTopVo,houseDetailTopVo.getHouseTopInfoVo())){
			List<HouseTopColumnVo> houseTopColumnVoList = houseDetailTopVo.getHouseTopInfoVo().getHouseTopColumnVoList();
			if(!Check.NuNCollection(houseTopColumnVoList)){
				StringBuffer houseTopColumnVoStr = new  StringBuffer();
				for (HouseTopColumnVo houseTopColumnVo : houseTopColumnVoList) {
					int  columnType   = houseTopColumnVo.getColumnType();
					int columnStyle = houseTopColumnVo.getColumnStyle();
					String css = "txtLeft";
					if(columnStyle == ColumnStyleEnum.Column_Style_101.getValue()){
						 css = "txtLeft";
					}
					if(columnStyle == ColumnStyleEnum.Column_Style_103.getValue()){
						 css = "txtCenter";
					}
					if(columnStyle == ColumnStyleEnum.Column_Style_102.getValue()){
						 css = "txtRight";
					}
					switch (columnType) {
					case 101:
						houseTopColumnVoStr.append("<div class='title con "+css+"'>"+houseTopColumnVo.getColumnContent()+"</div>");
						break;
					case 102:
						houseTopColumnVoStr.append("<div class='title con "+css+"'>"+houseTopColumnVo.getColumnContent()+"</div>");
						break;
					case  103:
						houseTopColumnVoStr.append("<div class='con "+css+"'><p class='bold mb18 fsz18'>"+houseTopColumnVo.getColumnContent()+"</p></div>");
						break;
					case 104:
						houseTopColumnVoStr.append("<div class='con smallTitle fsz16 "+css+"'>"+houseTopColumnVo.getColumnContent()+"</div>");
						break;
					case 105:
						houseTopColumnVoStr.append("<div class='con smallTitle fsz16 "+css+"'>"+houseTopColumnVo.getColumnContent()+"</div>");
						break;
					case 201:
						houseTopColumnVoStr.append("<div class='con line "+css+"'> ");
						if (!Check.NuNStr(houseTopColumnVo.getColumnContent())) {
							houseTopColumnVo.setColumnContent(houseTopColumnVo.getColumnContent().replaceAll(StringUtils.specialKey+StringUtils.specialKey, StringUtils.specialKey));
							String[] strings = houseTopColumnVo.getColumnContent().split(StringUtils.specialKey);  
							for (String string : strings) {
								houseTopColumnVoStr.append("<p class='point'>").append(string).append("</p>");
							}
						}
						houseTopColumnVoStr.append("</div>");							
						break;
					case 202:
						houseTopColumnVoStr.append("<div class='con "+css+"'><p class='info'>"+houseTopColumnVo.getColumnContent()+"</p></div>");
						break;
					case 203:
						houseTopColumnVoStr.append("<div class='con "+css+"'><p class='picFrom'>"+houseTopColumnVo.getColumnContent()+"</p></div>");
						break;
					case 204:
						houseTopColumnVoStr.append("<div class='con "+css+"'><p class='picFrom'>"+houseTopColumnVo.getColumnContent()+"</p></div>");
						break;
						
					case 301:
						houseTopColumnVoStr.append("<div class='con "+css+"'><img src='"+houseTopColumnVo.getPicUrl()+"' alt=''></div>");
						break;
					case 302:
						houseTopColumnVoStr.append("");
						break;
					case 401:
						//houseTopColumnVoStr.append("  <div class='S_video con'><iframe id='J_iframe' style='z-index:1;' src='https://v.qq.com/iframe/player.html?vid=e0385ftha4y' allowfullscreen='' frameborder='0'></iframe></div>");
						houseTopColumnVoStr.append("  <div class='S_video con'><iframe id='J_iframe' style='z-index:1;' src='"+houseTopColumnVo.getVideoUrl()+"' allowfullscreen='' frameborder='0'></iframe></div>");
						break;
					case 501:
						houseTopColumnVoStr.append("");
						break;

					default:
						break;
					}
				}
				request.setAttribute("houseTopColumnVoStr", houseTopColumnVoStr.toString());
			}
		}

	}

	/**
	 * 
	 * 播放视频页面 
	 * 例子地址:https://v.qq.com/iframe/player.html?vid=e0385ftha4y
	 *
	 * @author yd
	 * @created 2017年3月22日 上午10:38:39
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("${NO_LOGIN_AUTH}/playVideo")
	public String playVideo(HttpServletRequest request){
		
		String videoUrl = request.getParameter("videoUrl");
		if(Check.NuNStr(videoUrl)){
			LogUtil.error(LOGGER, "【视频地址错误】videoUrl={}", videoUrl);
		}
		
		request.setAttribute("videoUrl", videoUrl);
		return "/house/houseTop/video";
	}
	
}
