/**
 * @FileName: ColumnRegionServiceImpl.java
 * @Package com.ziroom.minsu.services.cms.service
 * 
 * @author bushujie
 * @created 2016年11月10日 下午7:05:20
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.cms.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.cms.ColumnRegionEntity;
import com.ziroom.minsu.entity.cms.ColumnRegionItemEntity;
import com.ziroom.minsu.entity.cms.ColumnRegionPicEntity;
import com.ziroom.minsu.services.cms.dao.ColumnRegionDao;
import com.ziroom.minsu.services.cms.dao.ColumnRegionItemDao;
import com.ziroom.minsu.services.cms.dao.ColumnRegionPicDao;
import com.ziroom.minsu.services.cms.dto.ColumnRegionAddRequest;
import com.ziroom.minsu.services.cms.dto.ColumnRegionRequest;
import com.ziroom.minsu.services.cms.dto.OrderSortUpRequest;
import com.ziroom.minsu.services.cms.entity.ColumnRegionUpVo;
import com.ziroom.minsu.services.cms.entity.ColumnRegionVo;

/**
 * <p>专栏商圈景点业务</p>
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
@Service("cms.columnRegionServiceImpl")
public class ColumnRegionServiceImpl {
	
	@Resource(name="cms.columnRegionDao")
	private ColumnRegionDao columnRegionDao;
	
	@Resource(name="cms.columnRegionPicDao")
	private ColumnRegionPicDao columnRegionPicDao;
	
	@Resource(name="cms.columnRegionItemDao")
	private ColumnRegionItemDao columnRegionItemDao;
	
	
	/**
	 * 
	 * 分页查询专栏商圈景点列表
	 *
	 * @author bushujie
	 * @created 2016年11月11日 上午9:55:26
	 *
	 * @param columnRegionRequest
	 * @return
	 */
	public PagingResult<ColumnRegionVo> findColumnRegionList(ColumnRegionRequest columnRegionRequest){
		return columnRegionDao.findColumnRegionList(columnRegionRequest);
	}
	
	/**
	 * 
	 * 插入专栏商圈景点
	 *
	 * @author bushujie
	 * @created 2016年11月14日 上午11:00:22
	 *
	 * @param columnRegionAddRequest
	 */
	public void insertColumnRegion(ColumnRegionAddRequest columnRegionAddRequest){
		//保存专栏商圈景点
		ColumnRegionEntity columnRegionEntity=new ColumnRegionEntity();
		BeanUtils.copyProperties(columnRegionAddRequest, columnRegionEntity);
		columnRegionEntity.setFid(UUIDGenerator.hexUUID());
		columnRegionEntity.setOrderSort(columnRegionDao.findNextOrderSortByColumnCityFid(columnRegionEntity.getColumnCityFid()));
		columnRegionDao.insertColumnRegion(columnRegionEntity);
		//保存主图
		if(!Check.NuNStr(columnRegionAddRequest.getMainPicParam())){
			ColumnRegionPicEntity columnRegionPicEntity=new ColumnRegionPicEntity();
			String[] picS=columnRegionAddRequest.getMainPicParam().split("\\|");
			columnRegionPicEntity.setFid(UUIDGenerator.hexUUID());
			columnRegionPicEntity.setColumnRegionFid(columnRegionEntity.getFid());
			columnRegionPicEntity.setPicBaseUrl(picS[1]);
			columnRegionPicEntity.setPicServerUuid(picS[0]);
			columnRegionPicEntity.setPicSuffix(picS[2]);
			columnRegionPicEntity.setPicType(0);
			columnRegionPicEntity.setCreateFid(columnRegionEntity.getCreateFid());
			columnRegionPicDao.insertColumnRegionPic(columnRegionPicEntity);
		}
		//保存轮播图
		if(!Check.NuNCollection(columnRegionAddRequest.getCarouselPicParam())){
			for(String pic:columnRegionAddRequest.getCarouselPicParam()){
				ColumnRegionPicEntity columnRegionPicEntity=new ColumnRegionPicEntity();
				String[] picS=pic.split("\\|");
				columnRegionPicEntity.setFid(UUIDGenerator.hexUUID());
				columnRegionPicEntity.setColumnRegionFid(columnRegionEntity.getFid());
				columnRegionPicEntity.setPicBaseUrl(picS[1]);
				columnRegionPicEntity.setPicServerUuid(picS[0]);
				columnRegionPicEntity.setPicSuffix(picS[2]);
				columnRegionPicEntity.setPicType(1);
				columnRegionPicEntity.setCreateFid(columnRegionEntity.getCreateFid());
				columnRegionPicDao.insertColumnRegionPic(columnRegionPicEntity);
			}
		}
		//保存推荐项目
		if(!Check.NuNCollection(columnRegionAddRequest.getItemPicParam())){
			for (String item:columnRegionAddRequest.getItemPicParam()) {
				ColumnRegionItemEntity columnRegionItemEntity=new ColumnRegionItemEntity();
				String[] itemS=item.split("\\|");
				columnRegionItemEntity.setColumnRegionFid(columnRegionEntity.getFid());
				columnRegionItemEntity.setRegionItemsFid(itemS[3]);
				columnRegionItemEntity.setIconBaseUrl(itemS[1]);
				columnRegionItemEntity.setIconServerUuid(itemS[0]);
				columnRegionItemEntity.setIconSuffix(itemS[2]);
				columnRegionItemEntity.setFid(UUIDGenerator.hexUUID());
				columnRegionItemEntity.setCreateFid(columnRegionEntity.getCreateFid());
				columnRegionItemEntity.setOrderSort(columnRegionItemDao.findNextOrderSortByColumnRegionFid(columnRegionEntity.getFid()));
				columnRegionItemDao.insertColumnRegionItem(columnRegionItemEntity);
			}
		}
	}
	
	/**
	 * 
	 * 初始化专栏景点商圈更新数据
	 *
	 * @author bushujie
	 * @created 2016年11月14日 下午5:26:23
	 *
	 * @param fid
	 * @return
	 */
	public ColumnRegionUpVo findColumnRegionUpVo(String fid){
		return columnRegionDao.findColumnRegionUpVo(fid);
	}
	
	/**
	 * 
	 * 更新专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午5:02:49
	 *
	 * @param columnRegionAddRequest
	 */
	public void updateColumnRegion(ColumnRegionAddRequest columnRegionAddRequest){
		//更新专栏景点商圈
		ColumnRegionEntity columnRegionEntity=new ColumnRegionEntity();
		BeanUtils.copyProperties(columnRegionAddRequest, columnRegionEntity);
		columnRegionDao.updateColumnRegion(columnRegionEntity);
		//删除的图片
		if(!Check.NuNStr(columnRegionAddRequest.getDelPicFids())){
			for(String picFid:columnRegionAddRequest.getDelPicFids().split("\\-")){
				ColumnRegionPicEntity columnRegionPicEntity=new ColumnRegionPicEntity();
				columnRegionPicEntity.setFid(picFid);
				columnRegionPicEntity.setIsDel(1);
				columnRegionPicDao.updateColumnRegionPic(columnRegionPicEntity);
			}
		}
		//删除的项目
		if(!Check.NuNStr(columnRegionAddRequest.getDelItemFids())){
			for(String itemFid:columnRegionAddRequest.getDelItemFids().split("\\-")){
				ColumnRegionItemEntity columnRegionItemEntity=new ColumnRegionItemEntity();
				columnRegionItemEntity.setFid(itemFid);
				columnRegionItemEntity.setIsDel(1);
				columnRegionItemDao.updateColumnRegionItem(columnRegionItemEntity);
			}
		}
		//增加的轮播图
		if(!Check.NuNCollection(columnRegionAddRequest.getCarouselPicParam())){
			for(String pic:columnRegionAddRequest.getCarouselPicParam()){
				ColumnRegionPicEntity columnRegionPicEntity=new ColumnRegionPicEntity();
				String[] picS=pic.split("\\|");
				columnRegionPicEntity.setFid(UUIDGenerator.hexUUID());
				columnRegionPicEntity.setColumnRegionFid(columnRegionEntity.getFid());
				columnRegionPicEntity.setPicBaseUrl(picS[1]);
				columnRegionPicEntity.setPicServerUuid(picS[0]);
				columnRegionPicEntity.setPicSuffix(picS[2]);
				columnRegionPicEntity.setPicType(1);
				columnRegionPicEntity.setCreateFid(columnRegionEntity.getCreateFid());
				columnRegionPicDao.insertColumnRegionPic(columnRegionPicEntity);
			}
		}
		//是否更新主图
		if(!Check.NuNStr(columnRegionAddRequest.getMainPicParam())){
			ColumnRegionPicEntity columnRegionPicEntity=new ColumnRegionPicEntity();
			String[] picS=columnRegionAddRequest.getMainPicParam().split("\\|");
			columnRegionPicEntity.setColumnRegionFid(columnRegionEntity.getFid());
			columnRegionPicEntity.setPicBaseUrl(picS[1]);
			columnRegionPicEntity.setPicServerUuid(picS[0]);
			columnRegionPicEntity.setPicSuffix(picS[2]);
			columnRegionPicEntity.setPicType(0);
			columnRegionPicEntity.setCreateFid(columnRegionEntity.getCreateFid());
			int upNum=columnRegionPicDao.updateColumnRegionPicByType(columnRegionPicEntity);
			if(upNum==0){
				columnRegionPicEntity.setFid(UUIDGenerator.hexUUID());
				columnRegionPicDao.insertColumnRegionPic(columnRegionPicEntity);
			}
		}
		//更新或保存推荐项目
		if(!Check.NuNCollection(columnRegionAddRequest.getItemPicParam())){
			for (String item:columnRegionAddRequest.getItemPicParam()) {
				ColumnRegionItemEntity columnRegionItemEntity=new ColumnRegionItemEntity();
				String[] itemS=item.split("\\|");
				columnRegionItemEntity.setColumnRegionFid(columnRegionEntity.getFid());
				columnRegionItemEntity.setRegionItemsFid(itemS[3]);
				columnRegionItemEntity.setIconBaseUrl(itemS[1]);
				columnRegionItemEntity.setIconServerUuid(itemS[0]);
				columnRegionItemEntity.setIconSuffix(itemS[2]);
				columnRegionItemEntity.setFid(UUIDGenerator.hexUUID());
				columnRegionItemEntity.setCreateFid(columnRegionEntity.getCreateFid());
				int upNum=columnRegionItemDao.updateColumnRegionItemByRegionItemsFid(columnRegionItemEntity);
				if(upNum==0){
					columnRegionItemDao.insertColumnRegionItem(columnRegionItemEntity);
				}
			}
		}
		//推荐项目排序调整
		Collections.sort(columnRegionAddRequest.getItemSortS());
		for(int i=0;i<columnRegionAddRequest.getItemFidS().size();i++){
			ColumnRegionItemEntity columnRegionItemEntity=new ColumnRegionItemEntity();
			columnRegionItemEntity.setFid(columnRegionAddRequest.getItemFidS().get(i));
			columnRegionItemEntity.setOrderSort(columnRegionAddRequest.getItemSortS().get(i));
			columnRegionItemDao.updateColumnRegionItem(columnRegionItemEntity);
		}
	}
	
	/**
	 * 
	 * fid删除专栏景点商圈
	 *
	 * @author bushujie
	 * @created 2016年11月15日 下午9:05:40
	 *
	 * @param fid
	 */
	public void delColumnRegion(String fid){
		ColumnRegionEntity columnRegionEntity=new ColumnRegionEntity();
		columnRegionEntity.setFid(fid);
		columnRegionEntity.setIsDel(1);
		columnRegionDao.updateColumnRegion(columnRegionEntity);
	}
	
	/**
	 * 
	 * 城市专栏查询包含景点商圈列表
	 *
	 * @author bushujie
	 * @created 2016年11月16日 下午6:08:13
	 *
	 * @param columnCityFid
	 * @return
	 */
	public List<ColumnRegionUpVo> findColumnRegionUpVoListByCityFid(String columnCityFid){
		return columnRegionDao.findColumnRegionUpVoListByCityFid(columnCityFid);
	}
	
	/**
	 * 
	 * 调整专栏景点顺序
	 *
	 * @author bushujie
	 * @created 2017年1月6日 下午2:10:22
	 *
	 * @param orderSortUpRequest
	 */
	public void upColumnRegionOrderSort(OrderSortUpRequest orderSortUpRequest){
		ColumnRegionEntity upSort=new ColumnRegionEntity();
		upSort.setFid(orderSortUpRequest.getUpFid());
		upSort.setOrderSort(orderSortUpRequest.getUpSort());
		ColumnRegionEntity downSort=new ColumnRegionEntity();
		downSort.setFid(orderSortUpRequest.getDownFid());
		downSort.setOrderSort(orderSortUpRequest.getDownSort());
		columnRegionDao.updateColumnRegion(upSort);
		columnRegionDao.updateColumnRegion(downSort);
	}
}
