package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.HouseTypeEntity;
import com.ziroom.zrp.service.houses.api.HouseTypeService;
import com.ziroom.zrp.service.houses.dto.AddHouseGroupDto;
import com.ziroom.zrp.service.houses.entity.AddHouseGroupVo;
import com.ziroom.zrp.service.houses.service.HouseTypeServiceImpl;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * <p>房型代理类</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月17日
 * @since 1.0
 */
@Component("houses.houseTypeServiceProxy")
public class HouseTypeServiceProxy implements HouseTypeService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseTypeServiceProxy.class);
	
	@Resource(name="houses.houseTypeServiceImpl")
	private HouseTypeServiceImpl houseTypeServiceImpl;
	
	@Override
	public String saveHouseType(HouseTypeEntity houseTypeEntity) {
		LogUtil.info(LOGGER,"【saveHouseType】入参：{}",JSONObject.toJSONString(houseTypeEntity));
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(houseTypeEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间类型为空！");
			return dto.toJsonString();
		}
		try{
			int isSuccess = houseTypeServiceImpl.saveHouseType(houseTypeEntity);
			if(isSuccess == 1){
				return dto.toJsonString();
			}else{
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("保存房间类型失败！");
				return dto.toJsonString();
			}
		}catch(Exception e){
			LogUtil.info(LOGGER,"【saveHouseType】出错：{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间类型为空！");
			return dto.toJsonString();
		}
	}

	@Override
	public String findHouseTypeById(String fid) {
		LogUtil.info(LOGGER,"【findHouseTypeById】入参:{}",fid);
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNStr(fid)){
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间类型ID为空！");
			return dto.toJsonString();
		}
		try{
			HouseTypeEntity houseTypeEntity = houseTypeServiceImpl.findHouseTypeById(fid);
			LogUtil.info(LOGGER, "【findHouseTypeById】查询房型信息返回：{}", JSONObject.toJSON(houseTypeEntity));
			if(Check.NuNObj(houseTypeEntity)){
				dto.setErrCode(DataTransferObject.ERROR);
				dto.setMsg("未查询到房间类型");
				return dto.toJsonString();
			}
			dto.putValue("houseType", houseTypeEntity);
			return dto.toJsonString();
		}catch(Exception e){
			LogUtil.info(LOGGER,"【findHouseTypeById】出错:{}",e);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("房间类型ID为空！");
			return dto.toJsonString();
		}
	}

	@Override
	public String findLayoutListForPage(String paramJson) {
		LogUtil.info(LOGGER,"【findLayoutListForPage】paramJson={}",paramJson);
		DataTransferObject dto = new DataTransferObject();
		try {
			AddHouseGroupDto addHouseGroupDto = JsonEntityTransform.json2Object(paramJson,AddHouseGroupDto.class);
			PagingResult<AddHouseGroupVo> pagingResult = houseTypeServiceImpl.findLayoutListForPage(addHouseGroupDto);
			dto.putValue("list",pagingResult.getRows());
			dto.putValue("total",pagingResult.getTotal());
		}catch (Exception e){
			LogUtil.error(LOGGER, "【findLayoutListForPage】 error:{},param={}", e, paramJson);
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("系统错误");
		}
		return dto.toJsonString();
	}

	@Override
	public String findHouseTypeListByIds(String houseTypeIds) {
		LogUtil.info(LOGGER,"[findHouseTypesByIds]{}",houseTypeIds);
		DataTransferObject dto = new DataTransferObject();
		if (Check.NuNStrStrict(houseTypeIds)) {
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("findHouseTypesByIds 参加为空");
			return dto.toJsonString();
		}
		List<String>  houseTypeIdList = Arrays.asList(houseTypeIds.split(","));
		List<HouseTypeEntity> houseTypeEntityList =  houseTypeServiceImpl.findHouseTypeListByIds(houseTypeIdList);
		dto.putValue("data", houseTypeEntityList);
		return  dto.toJsonString();
	}

	/**
	 * 查询房屋类型By项目Ids
	 *
	 * @param projectIds
	 * @return
	 */
	@Override
	public String findHouseTypeByProjectIds(@NonNull Collection<String> projectIds) {

		LogUtil.info(LOGGER,"[findHouseTypeByProjectIds] parms: {}", projectIds);

		DataTransferObject dto = new DataTransferObject();
		List<HouseTypeEntity> houseTypeEntities = null;
		String message = "Success"; Integer code = DataTransferObject.SUCCESS;

		try {
			houseTypeEntities = houseTypeServiceImpl.findHouseTypeByProjectIds(projectIds);
		} catch (Exception e) {
			LogUtil.error(LOGGER,"[findHouseTypeByProjectIds] parms: {}", projectIds);
			message = "获取户型异常";
			code = DataTransferObject.ERROR;
		}
		dto.setErrCode(code);
		dto.setMsg(message);
		dto.putValue("data", houseTypeEntities);
		return dto.toJsonString();
	}
}
