package com.ziroom.minsu.services.house.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HousePhyMsgEntity;
import com.ziroom.minsu.entity.house.HouseSurveyLogEntity;
import com.ziroom.minsu.entity.house.HouseSurveyMsgEntity;
import com.ziroom.minsu.entity.house.HouseSurveyPicMsgEntity;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.dao.HousePhyMsgDao;
import com.ziroom.minsu.services.house.dao.HouseSurveyLogDao;
import com.ziroom.minsu.services.house.dao.HouseSurveyMsgDao;
import com.ziroom.minsu.services.house.dao.HouseSurveyPicMsgDao;
import com.ziroom.minsu.services.house.survey.dto.SurveyPicDto;
import com.ziroom.minsu.services.house.survey.dto.SurveyRequestDto;
import com.ziroom.minsu.services.house.survey.entity.HouseSurveyVo;
import com.ziroom.minsu.valenum.house.SurveyOperateTypeEnum;

/**
 * 
 * <p>
 * 房源实勘接口实现
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Service("house.houseSurveyServiceImpl")
public class HouseSurveyServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(HouseSurveyServiceImpl.class);

	private static final String DEFAULT_CODE_DIGIT = "00000000";

	private static final String SURVEYSN_SUFFIX = "SK";

	private static final int PRE_NUM = 4;

	@Resource(name = "house.houseSurveyMsgDao")
	private HouseSurveyMsgDao houseSurveyMsgDao;

	@Resource(name = "house.houseSurveyPicMsgDao")
	private HouseSurveyPicMsgDao houseSurveyPicMsgDao;

	@Resource(name = "house.houseSurveyLogDao")
	private HouseSurveyLogDao houseSurveyLogDao;

	@Resource(name = "house.housePhyMsgDao")
	private HousePhyMsgDao housePhyMsgDao;

	@Resource(name = "house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;

	/**
	 * 根据房源fid查询需要实勘房源信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param requestDto
	 * @return
	 */
	public PagingResult<HouseSurveyVo> findSurveyHouseListByPage(SurveyRequestDto requestDto) {
		return houseSurveyMsgDao.findSurveyHouseListByPage(requestDto);
	}

	/**
	 * 查询需要实勘房源列表
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseSurveyMsgEntity findHouseSurveyMsgByHouseFid(String houseBaseFid) {
		return houseSurveyMsgDao.findHouseSurveyMsgByHouseFid(houseBaseFid);
	}

	/**
	 * 根据房源实勘图片fid查询房源实勘图片信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param surveyPicFid
	 * @return
	 */
	public HouseSurveyPicMsgEntity findHouseSurveyPicMsgByFid(String surveyPicFid) {
		return houseSurveyPicMsgDao.findHouseSurveyPicMsgByFid(surveyPicFid);
	}

	/**
	 * 新增房源实勘信息 1.先保存房源实勘信息获取自增id 2.拼装房源实勘编号并更新
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param surveyMsgEntity
	 * @return
	 */
	public int insertHouseSurveyMsg(HouseSurveyMsgEntity surveyMsgEntity) {
		int upNum = 0;
		upNum += houseSurveyMsgDao.insertHouseSurveyMsg(surveyMsgEntity);
		String surveySn = this.getSurveySn(surveyMsgEntity);
		surveyMsgEntity.setSurveySn(surveySn);
		upNum += houseSurveyMsgDao.updateHouseSurveyMsg(surveyMsgEntity);
		return upNum;
	}

	/**
	 * 获取房源实勘编号
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param surveyMsgEntity
	 * @return
	 */
	private String getSurveySn(HouseSurveyMsgEntity surveyMsgEntity) {
		Assert.notNull(surveyMsgEntity.getId(), "[房源实勘]主键不能为空");
		String preCityCode = this.getPreCityCodeByHouseFid(surveyMsgEntity.getHouseBaseFid());
		String pkCode = this.getPrimaryKeyCodeById(surveyMsgEntity.getId());
		StringBuilder sb = new StringBuilder();
		sb.append(preCityCode).append(pkCode).append(SURVEYSN_SUFFIX);
		return sb.toString();
	}

	/**
	 * 根据房源fid获取城市code前4位
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	private String getPreCityCodeByHouseFid(String houseBaseFid) {
		HousePhyMsgEntity phyMsgEntity = housePhyMsgDao.findHousePhyMsgByHouseBaseFid(houseBaseFid);
		Assert.notNull(phyMsgEntity, "[房源实勘]城市编码不能为空");
		String cityCode = phyMsgEntity.getCityCode();
		LogUtil.info(LOGGER, "cityCode={}", cityCode);
		if (Math.max(cityCode.length(), PRE_NUM) > PRE_NUM) {
			return cityCode.substring(0, PRE_NUM);
		} else {
			return cityCode;
		}
	}

	/**
	 * 根据自增id生成8位编号
	 *
	 * @author liujun
	 * @created 2016年10月27日
	 *
	 * @param id
	 * @return
	 */
	private String getPrimaryKeyCodeById(Integer id) {
		if (String.valueOf(id).length() < DEFAULT_CODE_DIGIT.length()) {
			String covering = DEFAULT_CODE_DIGIT.substring(String.valueOf(id).length());
			StringBuilder sb = new StringBuilder(DEFAULT_CODE_DIGIT.length());
			sb.append(covering).append(id);
			return sb.toString();
		} else {
			return String.valueOf(id);
		}
	}

	/**
	 * 根据房源实勘fid查询房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param fid
	 * @return
	 */
	public HouseSurveyMsgEntity findHouseSurveyMsgByFid(String fid) {
		return houseSurveyMsgDao.findHouseSurveyMsgByFid(fid);
	}

	/**
	 * 更新房源实勘信息 1.更新房源实勘信息 2.更新房源扩展信息 3.记录操作日志
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param newEntity
	 * @param oldEntity
	 * @return
	 */
	public int updateHouseSurveyMsg(HouseSurveyMsgEntity newEntity, HouseSurveyMsgEntity oldEntity) {
		int upNum = 0;
		// 1.更新房源实勘信息
		upNum += houseSurveyMsgDao.updateHouseSurveyMsg(newEntity);

		// 2.更新房源扩展信息 HouseBaseExtEntity#surveyResult
		upNum += this.updateHouseBaseExt(newEntity);

		if (!Check.NuNObj(newEntity.getOperateType())) {
			// 3.记录操作日志
			upNum += this.insertHouseSurveyLog(newEntity, oldEntity);
		}
		return upNum;
	}

	/**
	 * 更新房源扩展信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param surveyMsgEntity
	 * @return
	 */
	private int updateHouseBaseExt(HouseSurveyMsgEntity surveyMsgEntity) {
		HouseBaseExtEntity houseBaseExt = houseBaseExtDao.getHouseBaseExtByHouseBaseFid(surveyMsgEntity.getHouseBaseFid());
		Assert.notNull(houseBaseExt, "[房源实勘]房源扩展信息不能为空");
		houseBaseExt.setSurveyResult(surveyMsgEntity.getSurveyResult());
		return houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);
	}

	/**
	 * 记录房源实勘操作日志
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param newEntity
	 * @param oldEntity
	 * @return
	 */
	private int insertHouseSurveyLog(HouseSurveyMsgEntity newEntity, HouseSurveyMsgEntity oldEntity) {
		HouseSurveyLogEntity houseSurveyLogEntity = new HouseSurveyLogEntity();
		if (newEntity.getOperateType().intValue() == SurveyOperateTypeEnum.UPDATE.getCode()) {
			houseSurveyLogEntity.setPreSurveyDate(oldEntity.getSurveyDate());
			houseSurveyLogEntity.setCurSurveyDate(newEntity.getSurveyDate());
			houseSurveyLogEntity.setPreSurveyResult(oldEntity.getSurveyResult());
			houseSurveyLogEntity.setCurSurveyResult(newEntity.getSurveyResult());
		}
		houseSurveyLogEntity.setFid(UUIDGenerator.hexUUID());
		houseSurveyLogEntity.setSurveyFid(newEntity.getFid());
		houseSurveyLogEntity.setOperateFid(newEntity.getOperateFid());
		houseSurveyLogEntity.setOperateType(newEntity.getOperateType());
		return houseSurveyLogDao.insertHouseSurveyLog(houseSurveyLogEntity);
	}

	/**
	 * 新增房源实勘图片信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param surveyPicMsgEntity
	 * @return
	 */
	public int insertHouseSurveyPicMsg(HouseSurveyPicMsgEntity surveyPicMsgEntity) {
		return houseSurveyPicMsgDao.insertHouseSurveyPicMsg(surveyPicMsgEntity);
	}

	/**
	 * 更新房源实勘图片信息
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param surveyPicMsgEntity
	 * @return
	 */
	public int updateHouseSurveyPicMsg(HouseSurveyPicMsgEntity surveyPicMsgEntity) {
		return houseSurveyPicMsgDao.updateHouseSurveyPicMsg(surveyPicMsgEntity);
	}

	/**
	 * 根据图片类型查询图片数量
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param surveyPicDto
	 * @return
	 */
	public long findPicCountByType(SurveyPicDto surveyPicDto) {
		return houseSurveyPicMsgDao.findPicCountByType(surveyPicDto);
	}

	/**
	 * 根据房源实勘fid与图片类型查询房源实勘图片列表
	 *
	 * @author liujun
	 * @created 2016年11月21日
	 *
	 * @param picDto
	 * @return
	 */
	public List<HouseSurveyPicMsgEntity> findSurveyPicListByType(SurveyPicDto picDto) {
		return houseSurveyPicMsgDao.findSurveyPicListByType(picDto);
	}
}
