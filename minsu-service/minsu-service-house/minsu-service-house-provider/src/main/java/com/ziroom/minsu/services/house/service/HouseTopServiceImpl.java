/**
 * @FileName: HouseTopServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author bushujie
 * @created 2017年3月17日 下午4:15:31
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ziroom.minsu.services.house.entity.HouseTopSortVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseTagEntity;
import com.ziroom.minsu.entity.house.HouseTopColumnEntity;
import com.ziroom.minsu.entity.house.HouseTopEntity;
import com.ziroom.minsu.entity.house.HouseTopLogEntity;
import com.ziroom.minsu.services.house.dao.HouseTagDao;
import com.ziroom.minsu.services.house.dao.HouseTopColumnDao;
import com.ziroom.minsu.services.house.dao.HouseTopDao;
import com.ziroom.minsu.services.house.dao.HouseTopLogDao;
import com.ziroom.minsu.services.house.dto.HouseTopDto;
import com.ziroom.minsu.services.house.dto.HouseTopSaveDto;
import com.ziroom.minsu.services.house.entity.HouseTopDetail;
import com.ziroom.minsu.services.house.entity.HouseTopListVo;
import com.ziroom.minsu.services.house.entity.HouseTopVo;
import com.ziroom.minsu.valenum.house.ColumnStyleEnum;
import com.ziroom.minsu.valenum.house.ColumnTypeEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0022Enum;

/**
 * <p>top房源相关业务方法</p>
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
@Service("house.houseTopServiceImpl")
public class HouseTopServiceImpl {

	private static  Logger logger = LoggerFactory.getLogger(HouseTopServiceImpl.class);

	@Resource(name="house.houseTopDao")
	private HouseTopDao houseTopDao;

	@Resource(name="house.houseTagDao")
	private HouseTagDao houseTagDao;
	
	@Resource(name="house.houseTopLogDao")
	private HouseTopLogDao houseTopLogDao;
	
	@Resource(name="house.houseTopColumnDao")
	private HouseTopColumnDao houseTopColumnDao;


	/**
	 * 
	 * 分页查询塔尖房源列表
	 *
	 * @author bushujie
	 * @created 2017年3月17日 下午4:19:25
	 *
	 * @param houseTopDto
	 * @return
	 */
	public PagingResult<HouseTopListVo> findTopHouseListPage(HouseTopDto houseTopDto){
		return houseTopDao.findTopHouseListPage(houseTopDto);
	}

	/**
	 * 
	 * 查询top50 房源特有属性：  1. 条目   2. 标签
	 *
	 * @author yd
	 * @created 2017年3月17日 下午8:09:33
	 *
	 * @param params
	 * @return
	 */
	public HouseTopVo findHouseTopVoByHouse(Map<String, Object> params){
		HouseTopVo houseTopVo= null;
		if(!Check.NuNMap(params)){
			houseTopVo = this.houseTopDao.findHouseTopVoByHouse(params);
		}
		return houseTopVo;
	}
	/**
	 * 
	 * 插入top房源数据
	 *
	 * @author bushujie
	 * @created 2017年3月21日 下午3:32:31
	 *
	 * @param houseTopSaveDto
	 */
	public void insertHouseTop(HouseTopSaveDto houseTopSaveDto){
		//保存top房源主表
		HouseTopEntity houseTopEntity=new HouseTopEntity();
		BeanUtils.copyProperties(houseTopSaveDto, houseTopEntity);
		houseTopEntity.setFid(MD5Util.MD5Encode(houseTopEntity.getHouseBaseFid()+houseTopEntity.getRoomFid()));
		houseTopDao.insertHouseTop(houseTopEntity);
		//保存亮点标题
		HouseTopColumnEntity sternTitle=new HouseTopColumnEntity();
		sternTitle.setColumnType(ColumnTypeEnum.Column_Type_101.getValue());
		sternTitle.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
		sternTitle.setHouseTopFid(houseTopEntity.getFid());
		sternTitle.setFid(UUIDGenerator.hexUUID());
		sternTitle.setColumnContent(houseTopSaveDto.getSternTitle());
		sternTitle.setColumnSort(1);
		houseTopColumnDao.insertHouseTopColumnEntity(sternTitle);
		//保存亮点文本
		HouseTopColumnEntity sternContent=new HouseTopColumnEntity();
		sternContent.setColumnType(ColumnTypeEnum.Column_Type_201.getValue());
		sternContent.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
		sternContent.setHouseTopFid(houseTopEntity.getFid());
		sternContent.setFid(UUIDGenerator.hexUUID());
		sternContent.setColumnContent(houseTopSaveDto.getSternContent());
		sternContent.setColumnSort(2);
		houseTopColumnDao.insertHouseTopColumnEntity(sternContent);
		//保存封面图片
		HouseTopColumnEntity coverPicParam=new HouseTopColumnEntity();
		coverPicParam.setColumnType(ColumnTypeEnum.Column_Type_302.getValue());
		coverPicParam.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
		coverPicParam.setHouseTopFid(houseTopEntity.getFid());
		coverPicParam.setFid(UUIDGenerator.hexUUID());
		String[] pns=houseTopSaveDto.getCoverPicParam().split("\\|");
		coverPicParam.setPicServerUuid(pns[0]);
		coverPicParam.setPicBaseUrl(pns[1]);
		coverPicParam.setPicSuffix(pns[2]);
		coverPicParam.setWidth(Integer.valueOf(pns[3]));
		coverPicParam.setHight(Integer.valueOf(pns[4]));
		coverPicParam.setColumnSort(3);
		houseTopColumnDao.insertHouseTopColumnEntity(coverPicParam);
		//保存top房源条目
		if(!Check.NuNCollection(houseTopSaveDto.getColumnS())){
			for(int i=0;i<houseTopSaveDto.getColumnS().size();i++){
				String[] cns=houseTopSaveDto.getColumnS().get(i).split("\\|");
				HouseTopColumnEntity houseTopColumnEntity=new HouseTopColumnEntity();
				houseTopColumnEntity.setColumnType(Integer.valueOf(cns[0]));
				houseTopColumnEntity.setHouseTopFid(houseTopEntity.getFid());
				houseTopColumnEntity.setColumnStyle(Integer.valueOf(cns[1]));
				houseTopColumnEntity.setFid(UUIDGenerator.hexUUID());
				//标题类处理
				if(cns[0].startsWith("1")||cns[0].startsWith("2")){
					houseTopColumnEntity.setColumnContent(houseTopSaveDto.getColumnContent().get(i));
				}else if(cns[0].startsWith("3")){
					houseTopColumnEntity.setPicServerUuid(cns[2]);
					houseTopColumnEntity.setPicBaseUrl(cns[3]);
					houseTopColumnEntity.setPicSuffix(cns[4]);
					houseTopColumnEntity.setWidth(Integer.valueOf(cns[5]));
					houseTopColumnEntity.setHight(Integer.valueOf(cns[6]));
				}else if(cns[0].startsWith("4")||cns[0].startsWith("5")){
					houseTopColumnEntity.setColumnContent(houseTopSaveDto.getColumnContent().get(i));
					houseTopColumnEntity.setPicServerUuid(cns[2]);
					houseTopColumnEntity.setPicBaseUrl(cns[3]);
					houseTopColumnEntity.setPicSuffix(cns[4]);
					houseTopColumnEntity.setWidth(Integer.valueOf(cns[5]));
					houseTopColumnEntity.setHight(Integer.valueOf(cns[6]));
				}
				houseTopColumnEntity.setColumnSort(i+4);
				houseTopColumnDao.insertHouseTopColumnEntity(houseTopColumnEntity);
			}
		}
		//保存top房源标签
		if(!Check.NuNCollection(houseTopSaveDto.getTagFids())){
			for(String tag:houseTopSaveDto.getTagFids()){
				HouseTagEntity houseTagEntity=new HouseTagEntity();
				houseTagEntity.setHouseBaseFid(houseTopEntity.getHouseBaseFid());
				houseTagEntity.setRoomFid(houseTopEntity.getRoomFid());
				houseTagEntity.setRentWay(houseTopEntity.getRentWay());
				houseTagEntity.setTagFid(tag.split("\\|")[0]);
				houseTagEntity.setTagType(Integer.valueOf(tag.split("\\|")[1]));
				houseTagEntity.setFid(UUIDGenerator.hexUUID());
				houseTagDao.insertHouseTag(houseTagEntity);
			}
		}
		//保存添加日志
		HouseTopLogEntity houseTopLogEntity=new HouseTopLogEntity();
		houseTopLogEntity.setFid(UUIDGenerator.hexUUID());
		houseTopLogEntity.setCreateFid(houseTopSaveDto.getCreateFid());
		houseTopLogEntity.setEmpCode(houseTopSaveDto.getEmpCode());
		houseTopLogEntity.setEmpName(houseTopSaveDto.getEmpName());
		houseTopLogEntity.setOperateType(0);
		houseTopLogEntity.setFromState(0);
		houseTopLogEntity.setToState(0);
		houseTopLogEntity.setHouseTopFid(houseTopEntity.getFid());
		houseTopLogDao.insertHouseTopLog(houseTopLogEntity);
	}
	
	/**
	 * 
	 * 查询top房源详情
	 *
	 * @author bushujie
	 * @created 2017年3月22日 上午10:30:31
	 *
	 * @param fid
	 * @return
	 */
	public HouseTopDetail findHouseTopDetail(String fid){
		HouseTopDetail houseTopDetail=houseTopDao.findHouseTopDetail(fid);
		Map<String, Object> columnMap=new HashMap<>();
		columnMap.put("houseTopFid", houseTopDetail.getFid());
		columnMap.put("columnType", ColumnTypeEnum.Column_Type_101.getValue());
		houseTopDetail.setSternTitle(houseTopColumnDao.findHouseTopColumnByType(columnMap));
		columnMap.put("columnType", ColumnTypeEnum.Column_Type_201.getValue());
		houseTopDetail.setSternContent(houseTopColumnDao.findHouseTopColumnByType(columnMap));
		columnMap.put("columnType", ColumnTypeEnum.Column_Type_302.getValue());
		houseTopDetail.setCoverPicParam(houseTopColumnDao.findHouseTopColumnByType(columnMap));
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("houseBaseFid", houseTopDetail.getHouseBaseFid());
		paramMap.put("roomFid", houseTopDetail.getRoomFid());
		paramMap.put("tagType", ProductRulesEnum0022Enum.TAG_TOP50_HOUSE.getValue());
		paramMap.put("rentWay", houseTopDetail.getRentWay());
		houseTopDetail.setTagFidList(houseTagDao.findHouseTagByParams(paramMap));
		return houseTopDetail;
	}
	/**
	 *
	 * 顺序靠前换到顺序靠后的前面 (上浮操作)
	 * @author lunan
	 * @created 2017年3月22日
	 *
	 * @param
	 */
	 public int updateHouseTopSortFloat(HouseTopSortVo houseTopSortVo){
		Integer result = 0;
		if(!Check.NuNObjs(houseTopSortVo,houseTopSortVo.getNewTopSort(),houseTopSortVo.getFid())){
			//根据fid去查询houseTop
			HouseTopEntity oldHouseTop = this.houseTopDao.findHouseTopEntityByfid(houseTopSortVo.getFid());
			if(Check.NuNObj(oldHouseTop)){
				return result;
			}
			//执行上浮操作
			result = this.houseTopDao.updateHouseTopSortFloat(houseTopSortVo.getNewTopSort(),oldHouseTop.getTopSort());
			if(result>0){
				//根据fid去更新 最新的排序号
				HouseTopEntity newHouseTop = new HouseTopEntity();
				newHouseTop.setFid(houseTopSortVo.getFid());
				newHouseTop.setTopSort(houseTopSortVo.getNewTopSort());
				result = this.houseTopDao.updateHouseTopByfid(newHouseTop);
			}
			return result;
		}else{
			return result;
		}
	 }

	/**
	 *
	 * 顺序靠后换到顺序靠前的前面 (下沉操作)
	 * @author lunan
	 * @created 2017年3月22日
	 *
	 * @param
	 */
	public int updateHouseTopSortSink(HouseTopSortVo houseTopSortVo){
		Integer result = 0;
		if(!Check.NuNObjs(houseTopSortVo,houseTopSortVo.getNewTopSort(),houseTopSortVo.getFid())){
			//根据fid去查询houseTop
			HouseTopEntity oldHouseTop = this.houseTopDao.findHouseTopEntityByfid(houseTopSortVo.getFid());
			if(Check.NuNObj(oldHouseTop)){
				return result;
			}
			//执行下沉操作,大序号换小序号
			result = this.houseTopDao.updateHouseTopSortSink(houseTopSortVo.getNewTopSort(),oldHouseTop.getTopSort());
			if(result>0){
				//根据fid去更新 最新的排序号
				HouseTopEntity newHouseTop = new HouseTopEntity();
				newHouseTop.setFid(houseTopSortVo.getFid());
				newHouseTop.setTopSort(houseTopSortVo.getNewTopSort());
				result = this.houseTopDao.updateHouseTopByfid(newHouseTop);
			}
			return result;
		}else{
			return result;
		}
	}

	/**
	 *
	 * 交换序号顺序
	 * @author lunan
	 * @created 2017年3月22日
	 *
	 * @param
	 */
	public int updateHouseTopSortExchange(HouseTopSortVo houseTopSortVo){
		Integer result = 0;
		if(!Check.NuNObjs(houseTopSortVo,houseTopSortVo.getNewTopSort(),houseTopSortVo.getFid())){
			//根据fid去查询houseTop
			HouseTopEntity oldHouseTop = this.houseTopDao.findHouseTopEntityByfid(houseTopSortVo.getFid());
			if(Check.NuNObj(oldHouseTop)){
				return result;
			}
			//要交换的新的序号，通过旧的序号去更新，然后根据旧的fid去跟新被交换的序号 2想换成4 4      2
			result = this.houseTopDao.updateHouseTopByTopSort(houseTopSortVo.getNewTopSort(),oldHouseTop.getTopSort());
			if(result>0){
				//根据fid去更新 最新的排序号
				HouseTopEntity newHouseTop = new HouseTopEntity();
				newHouseTop.setFid(houseTopSortVo.getFid());
				newHouseTop.setTopSort(houseTopSortVo.getNewTopSort());
				result = this.houseTopDao.updateHouseTopByfid(newHouseTop);
			}
			return result;
		}else{
			return result;
		}
	}

	/**
	 * 根据fid查询一条HouseTopEntity
	 * @param fid
	 * @return
     */
	public HouseTopEntity findHouseTopByFid(String fid){
		return this.houseTopDao.findHouseTopEntityByfid(fid);
	}

	/**
	 * 根据fid更新一条HouseTopEntity
	 * @param houseTopEntity
	 * @return
	 */
	public int updateHouseTop(HouseTopEntity houseTopEntity,HouseTopSaveDto houseTopSaveDto){
		//添加操作日志
		HouseTopLogEntity houseTopLogEntity=new HouseTopLogEntity();
		houseTopLogEntity.setFid(UUIDGenerator.hexUUID());
		houseTopLogEntity.setCreateFid(houseTopSaveDto.getCreateFid());
		houseTopLogEntity.setEmpCode(houseTopSaveDto.getEmpCode());
		houseTopLogEntity.setEmpName(houseTopSaveDto.getEmpName());
		houseTopLogEntity.setOperateType(0);
		houseTopLogEntity.setFromState(houseTopSaveDto.getTopStatus());
		houseTopLogEntity.setToState(houseTopEntity.getTopStatus());
		houseTopLogEntity.setHouseTopFid(houseTopEntity.getFid());
		houseTopLogDao.insertHouseTopLog(houseTopLogEntity);
		return this.houseTopDao.updateHouseTopByfid(houseTopEntity);
	}
	
	/**
	 * 
	 * 更新top房源内容
	 *
	 * @author bushujie
	 * @created 2017年3月22日 下午6:11:38
	 *
	 * @param houseTopSaveDto
	 */
	public void updateHouseTop(HouseTopSaveDto houseTopSaveDto){
		HouseTopEntity houseTopEntity=new HouseTopEntity();
		BeanUtils.copyProperties(houseTopSaveDto, houseTopEntity);
		houseTopDao.updateHouseTopByfid(houseTopEntity);
		//删除老标签
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("rentWay", houseTopSaveDto.getRentWay());
		paramMap.put("houseBaseFid", houseTopSaveDto.getHouseBaseFid());
		paramMap.put("roomFid", houseTopSaveDto.getRoomFid());
		paramMap.put("tagType", ProductRulesEnum0022Enum.TAG_TOP50_HOUSE.getValue());
		houseTagDao.delHouseTagByParams(paramMap);
		//添加新标签
		if(!Check.NuNCollection(houseTopSaveDto.getTagFids())){
			for(String tag:houseTopSaveDto.getTagFids()){
				HouseTagEntity houseTagEntity=new HouseTagEntity();
				houseTagEntity.setHouseBaseFid(houseTopEntity.getHouseBaseFid());
				houseTagEntity.setRoomFid(houseTopEntity.getRoomFid());
				houseTagEntity.setRentWay(houseTopEntity.getRentWay());
				houseTagEntity.setTagFid(tag.split("\\|")[0]);
				houseTagEntity.setTagType(Integer.valueOf(tag.split("\\|")[1]));
				houseTagEntity.setFid(UUIDGenerator.hexUUID());
				houseTagDao.insertHouseTag(houseTagEntity);
			}
		}
		//更新或添加亮点标题
		HouseTopColumnEntity sternTitle=new HouseTopColumnEntity();
		if(Check.NuNStr(houseTopSaveDto.getSternTitleFid())){
			sternTitle.setColumnType(ColumnTypeEnum.Column_Type_101.getValue());
			sternTitle.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			sternTitle.setHouseTopFid(houseTopEntity.getFid());
			sternTitle.setFid(UUIDGenerator.hexUUID());
			sternTitle.setColumnContent(houseTopSaveDto.getSternTitle());
			sternTitle.setColumnSort(1);
			houseTopColumnDao.insertHouseTopColumnEntity(sternTitle);
		} else {
			sternTitle.setFid(houseTopSaveDto.getSternTitleFid());
			sternTitle.setColumnContent(houseTopSaveDto.getSternTitle());
			houseTopColumnDao.updateHouseTopColumn(sternTitle);
		}
		//更新或添加亮点内容
		HouseTopColumnEntity sternContent=new HouseTopColumnEntity();
		if(Check.NuNStr(houseTopSaveDto.getSternContentFid())){
			sternContent.setColumnType(ColumnTypeEnum.Column_Type_201.getValue());
			sternContent.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			sternContent.setHouseTopFid(houseTopEntity.getFid());
			sternContent.setFid(UUIDGenerator.hexUUID());
			sternContent.setColumnContent(houseTopSaveDto.getSternContent());
			sternContent.setColumnSort(2);
			houseTopColumnDao.insertHouseTopColumnEntity(sternContent);
		}else {
			sternContent.setFid(houseTopSaveDto.getSternContentFid());
			sternContent.setColumnContent(houseTopSaveDto.getSternContent());
			houseTopColumnDao.updateHouseTopColumn(sternContent);
		}
		//更新封面图片
		HouseTopColumnEntity coverPicParam=new HouseTopColumnEntity();
		if(Check.NuNStr(houseTopSaveDto.getCoverPicParamFid())){
			coverPicParam.setColumnType(ColumnTypeEnum.Column_Type_302.getValue());
			coverPicParam.setColumnStyle(ColumnStyleEnum.Column_Style_101.getValue());
			coverPicParam.setHouseTopFid(houseTopEntity.getFid());
			coverPicParam.setFid(UUIDGenerator.hexUUID());
			String[] pns=houseTopSaveDto.getCoverPicParam().split("\\|");
			coverPicParam.setPicServerUuid(pns[0]);
			coverPicParam.setPicBaseUrl(pns[1]);
			coverPicParam.setPicSuffix(pns[2]);
			coverPicParam.setWidth(Integer.valueOf(pns[3]));
			coverPicParam.setHight(Integer.valueOf(pns[4]));
			coverPicParam.setColumnSort(3);
			houseTopColumnDao.insertHouseTopColumnEntity(coverPicParam);
		} else {
			String[] picS=houseTopSaveDto.getCoverPicParam().split("\\|");
			coverPicParam.setFid(houseTopSaveDto.getCoverPicParamFid());
			coverPicParam.setPicServerUuid(picS[0]);
			coverPicParam.setPicBaseUrl(picS[1]);
			coverPicParam.setPicSuffix(picS[2]);
			coverPicParam.setWidth(Integer.valueOf(picS[3]));
			coverPicParam.setHight(Integer.valueOf(picS[4]));
			houseTopColumnDao.updateHouseTopColumn(coverPicParam);
		}
		//更新保存条目
		if(!Check.NuNCollection(houseTopSaveDto.getMarkUp())){
			int updateInt=0;
			int insertInt=0;
			for(int i=0;i<houseTopSaveDto.getMarkUp().size();i++){
				if(houseTopSaveDto.getMarkUp().get(i)==0){
					String[] cns=houseTopSaveDto.getColumnS().get(insertInt).split("\\|");
					HouseTopColumnEntity houseTopColumnEntity=new HouseTopColumnEntity();
					houseTopColumnEntity.setColumnType(Integer.valueOf(cns[0]));
					houseTopColumnEntity.setHouseTopFid(houseTopEntity.getFid());
					houseTopColumnEntity.setColumnStyle(Integer.valueOf(cns[1]));
					houseTopColumnEntity.setFid(UUIDGenerator.hexUUID());
					//标题类处理
					if(cns[0].startsWith("1")||cns[0].startsWith("2")){
						houseTopColumnEntity.setColumnContent(houseTopSaveDto.getColumnContent().get(insertInt));
					}else if(cns[0].startsWith("3")){
						houseTopColumnEntity.setPicServerUuid(cns[2]);
						houseTopColumnEntity.setPicBaseUrl(cns[3]);
						houseTopColumnEntity.setPicSuffix(cns[4]);
						houseTopColumnEntity.setWidth(Integer.valueOf(cns[5]));
						houseTopColumnEntity.setHight(Integer.valueOf(cns[6]));
					}else if(cns[0].startsWith("4")||cns[0].startsWith("5")){
						houseTopColumnEntity.setColumnContent(houseTopSaveDto.getColumnContent().get(insertInt));
						houseTopColumnEntity.setPicServerUuid(cns[2]);
						houseTopColumnEntity.setPicBaseUrl(cns[3]);
						houseTopColumnEntity.setPicSuffix(cns[4]);
						houseTopColumnEntity.setWidth(Integer.valueOf(cns[5]));
						houseTopColumnEntity.setHight(Integer.valueOf(cns[6]));
					}
					houseTopColumnEntity.setColumnSort(i+4);
					houseTopColumnDao.insertHouseTopColumnEntity(houseTopColumnEntity);
					insertInt++;
				} else if(houseTopSaveDto.getMarkUp().get(i)==1) {
					HouseTopColumnEntity houseTopColumnEntity=new HouseTopColumnEntity();
					houseTopColumnEntity.setFid(houseTopSaveDto.getHouseTopColumnFidS().get(updateInt));
					if(!houseTopSaveDto.getColumnContentU().get(updateInt).equals("pic001")){
						houseTopColumnEntity.setColumnContent(houseTopSaveDto.getColumnContentU().get(updateInt));
					}
					houseTopColumnEntity.setColumnSort(i+4);
					houseTopColumnDao.updateHouseTopColumn(houseTopColumnEntity);
					updateInt++;
				}
			}
		}
		//添加操作日志
		HouseTopLogEntity houseTopLogEntity=new HouseTopLogEntity();
		houseTopLogEntity.setFid(UUIDGenerator.hexUUID());
		houseTopLogEntity.setCreateFid(houseTopSaveDto.getCreateFid());
		houseTopLogEntity.setEmpCode(houseTopSaveDto.getEmpCode());
		houseTopLogEntity.setEmpName(houseTopSaveDto.getEmpName());
		houseTopLogEntity.setOperateType(2);
		houseTopLogEntity.setFromState(0);
		houseTopLogEntity.setToState(0);
		houseTopLogEntity.setHouseTopFid(houseTopEntity.getFid());
		houseTopLogDao.insertHouseTopLog(houseTopLogEntity);
	}
	
	/**
	 * 
	 * 更新top房源条目
	 *
	 * @author bushujie
	 * @created 2017年3月24日 上午10:46:37
	 *
	 * @param houseTopColumnEntity
	 */
	public void updateHouseTopColumn(HouseTopColumnEntity houseTopColumnEntity){
		houseTopColumnDao.updateHouseTopColumn(houseTopColumnEntity);
	}
}
