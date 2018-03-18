package com.ziroom.minsu.report.house.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.NationCodeEntity;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.report.basedata.dao.ConfCityDao;
import com.ziroom.minsu.report.basedata.entity.ConfCityEntity;
import com.ziroom.minsu.report.basedata.valenum.HouseOrderTypeEnum;
import com.ziroom.minsu.report.basedata.valenum.HouseStatusEnum;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.report.house.dao.HouseInfoReportDao;
import com.ziroom.minsu.report.house.dto.HouseInfoReportDto;
import com.ziroom.minsu.report.house.vo.HouseInfoReportVo;

/**
 * 
 * <p>房源信息报表</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author ls
 * @since 1.0
 * @version 1.0
 */
@Service("report.houseInfoReportService")
public class HouseInfoReportService implements ReportService <HouseInfoReportVo,HouseInfoReportDto>{
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseInfoReportService.class);

	@Resource(name = "report.houseInfoReportDao")
	HouseInfoReportDao houseInfoReportDao;

	 @Resource(name="report.confCityDao")
	 private ConfCityDao confCityDao;

	/**
	 * 房源信息报表查询
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
	public PagingResult<HouseInfoReportVo> getHouseInfoReportVoList(HouseInfoReportDto paramDto) {
		LogUtil.info(LOGGER, "houseInfoReportService,  getHouseInfoReportVoList, parma={}", JsonEntityTransform.Object2Json(paramDto));
		if(checkAllParamNull(paramDto)){
			paramDto.setRentWay(0);
		}
		PagingResult<HouseInfoReportVo>  houseInfoRVoList = this.getHfidListByParam(paramDto);//经过四个表中的查询条件筛选，获取"房源基础信息表fid"的集合
		if(houseInfoRVoList.getTotal() == 0){//集合总数是0，直接将分页集合返回
			return houseInfoRVoList;
		}
		List<HouseInfoReportVo> transferList = houseInfoRVoList.getRows();
			getAllVoByConfirmHfid(transferList, paramDto);//遍历Hfid或者Rfid集合，根据fid将VO中数据填充
			houseInfoRVoList.setRows(transferList);//将填充过的transferList重新设置回分页对象中
        return houseInfoRVoList;
	}
	
	/**
	 * 获取所有符合条件的Hfid和Rfid的集合
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
	public PagingResult<HouseInfoReportVo> getHfidListByParam(HouseInfoReportDto paramDto){
		LogUtil.info(LOGGER, "houseInfoReportService,  getHfidListByParam, parma={}", JsonEntityTransform.Object2Json(paramDto));
		if(!Check.NuNStr(paramDto.getRegionCode())){
			List<String> pCodeListByRCode = getPCodeListByRCode(paramDto.getRegionCode());
			if(Check.NuNCollection(pCodeListByRCode)){
				return new PagingResult<HouseInfoReportVo>();
			}
			paramDto.setProvinceCodeList(pCodeListByRCode);
		}
		if(!Check.NuNObj(paramDto.getLeaseMinPrice())){
			paramDto.setLeaseMinPrice(paramDto.getLeaseMinPrice()*100);
		}
		if(!Check.NuNObj(paramDto.getLeaseMaxPrice())){
			paramDto.setLeaseMaxPrice(paramDto.getLeaseMaxPrice()*100);
		}
		if(paramDto.getRentWay().equals(0)){//整租，查询出所有房源Hfid集合
			PagingResult<HouseInfoReportVo> allHfidList = houseInfoReportDao.getEntireRentList(paramDto);
			return allHfidList;
		}
		//分租，查询出所有房间roomFid集合
		PagingResult<HouseInfoReportVo> allHfidList = houseInfoReportDao.getJoinRentList(paramDto);
		return allHfidList;
	}
	
	/**
	 * 根据regionCode获取所有ProvinceCode集合
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
	public List<String> getPCodeListByRCode(String regionCode){
	    List<String> pCodeList = houseInfoReportDao.getPCodeListByRCode(regionCode);
	    return pCodeList;
	}
	
	/**
	 * 
	 * 根据所有确定的Hfid或者Rfid 将Vo中需要展示的字段全部填充
	 *
	 * @author ls
	 * @created 2017年4月24日 上午10:45:36
	 *
	 * @param paramDto
	 * @return
	 */
	public void getAllVoByConfirmHfid(List<HouseInfoReportVo> transferList, HouseInfoReportDto paramDto){
        for (HouseInfoReportVo tempVo : transferList) {
        	
        	//国家code==》国家名称 
        	if(!Check.NuNStr(tempVo.getNationCode())){
        		NationCodeEntity nationCodeEntity = confCityDao.getNationName(tempVo.getNationCode());
        		if(!Check.NuNObj(nationCodeEntity) && !Check.NuNObj(nationCodeEntity.getNationName())){
        			tempVo.setNationName(nationCodeEntity.getNationName());
        		}
        	}
        	
        	//城市code==》城市名称
        	ConfCityEntity cityByCode = confCityDao.getCityByCode(tempVo.getCityCode());
        	 if(!Check.NuNObj(cityByCode) && !Check.NuNStr(cityByCode.getShowName())){
        		 tempVo.setCityName(cityByCode.getShowName());
        	 }
        
        	//获取大区名称
        	 if(!Check.NuNStr(tempVo.getProvinceCode())){
        		CityRegionEntity cityRegionEntity = confCityDao.getRegionByProvinceCode(tempVo.getProvinceCode());
        		 if(!Check.NuNObj(cityRegionEntity) && !Check.NuNStr(cityRegionEntity.getRegionName())){
        			 tempVo.setRegionName(cityRegionEntity.getRegionName());
        		 }
        	 }
        	
        	 //日租金换成以元为单位
        	 if(!Check.NuNObj(tempVo.getLeasePrice()) && 0!=tempVo.getLeasePrice().intValue()){
        		 tempVo.setLeasePrice(tempVo.getLeasePrice()/100);
        	 }
        	//房源状态==》房源状态名称      房源出租类型==》出租类型名称
        	if(!Check.NuNObj(tempVo.getHouseStatus())){
        		tempVo.setHouseStatusName(HouseStatusEnum.getHouseStatusByCode(tempVo.getHouseStatus()).getName());
        	}
        	//tempVo.setRentWayName(HouseRentWayEnum.getEnumByRentWayCode(tempVo.getRentWay()).getName());
        	if(paramDto.getRentWay()==0){
        		tempVo.setRentWayName("整租");
        	}else{
        		tempVo.setRentWayName("分租");
        	}
        	
        	//预定类型==》预定类型名称    房源级别==》房源级别名称 
        	if(!Check.NuNObj(tempVo.getOrderType())){
        		if(!Check.NuNObj(HouseOrderTypeEnum.getEnumByOrderTypeCode(tempVo.getOrderType()))){
        			tempVo.setOrderTypeName(HouseOrderTypeEnum.getEnumByOrderTypeCode(tempVo.getOrderType()).getName());
        			 tempVo.setHouseQualityGrade(tempVo.getHouseQualityGrade());
        		}
        	}
        }
	}

	/**
	 * 
	 * 校验所有参数是否为空   1：为空返true 2:不为空返false
	 *
	 * @author ls
	 * @created 2017年5月3日 上午10:18:15
	 *
	 * @param paramDto
	 * @return
	 */
	public boolean checkAllParamNull(HouseInfoReportDto paramDto){
		
		if(Check.NuNObj(paramDto) 
				|| Check.NuNObj(paramDto.getOrderType())
				&& Check.NuNObj(paramDto.getHouseQualityGrade()) 
				&& Check.NuNStr(paramDto.getNationCode())
				&& Check.NuNStr(paramDto.getRegionCode())
				&& Check.NuNStr(paramDto.getCityCode()) 
				&& Check.NuNStr(paramDto.getFirstDeployBeginDate()) 
				&& Check.NuNStr(paramDto.getFirstDeployEndDate())
				&& Check.NuNObj(paramDto.getHouseStatus())
			    && Check.NuNObj(paramDto.getRentWay())
				&& Check.NuNObj(paramDto.getFirstDeployBeginDate())
				&& Check.NuNObj(paramDto.getFirstDeployEndDate())){
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#getPageInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public PagingResult<HouseInfoReportVo> getPageInfo(HouseInfoReportDto par) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ziroom.minsu.report.common.service.ReportService#countDataInfo(com.ziroom.minsu.services.common.dto.PageRequest)
	 */
	@Override
	public Long countDataInfo(HouseInfoReportDto par) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
