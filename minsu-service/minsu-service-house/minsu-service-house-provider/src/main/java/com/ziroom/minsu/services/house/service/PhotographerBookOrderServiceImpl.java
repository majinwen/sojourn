/**
 * @FileName: PhotographerBookOrderServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author yd
 * @created 2016年11月8日 上午10:42:05
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookChangeLogEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookLogEntity;
import com.ziroom.minsu.entity.photographer.PhotographerBookOrderEntity;
import com.ziroom.minsu.services.common.utils.SnUtil;
import com.ziroom.minsu.services.house.dao.HouseBaseExtDao;
import com.ziroom.minsu.services.house.dao.PhotographerBookChangeLogDao;
import com.ziroom.minsu.services.house.dao.PhotographerBookLogDao;
import com.ziroom.minsu.services.house.dao.PhotographerBookOrderDao;
import com.ziroom.minsu.services.house.dto.BookHousePhotogDto;
import com.ziroom.minsu.services.house.photog.dto.PhotogOrderUpdateDto;
import com.ziroom.minsu.services.house.photog.vo.PhotographerBookOrderVo;
import com.ziroom.minsu.valenum.house.IsPhotographyEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderSourceEnum;
import com.ziroom.minsu.valenum.photographer.BookOrderStatuEnum;
import com.ziroom.minsu.valenum.photographer.BusinessTypeEnum;
import com.ziroom.minsu.valenum.photographer.UpdateTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>预约摄影订单的实现</p>
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
@Service("photographer.photographerBookOrderServiceImpl")
public class PhotographerBookOrderServiceImpl {

	
	@Resource(name = "house.houseBaseExtDao")
	private HouseBaseExtDao houseBaseExtDao;
	
	@Resource(name = "photographer.photographerBookLogDao")
	private PhotographerBookLogDao photographerBookLogDao;

	@Resource(name = "photographer.photographerBookChangeLogDao")
	private PhotographerBookChangeLogDao photographerBookChangeLogDao;
	
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(PhotographerBookOrderServiceImpl.class);
	
	@Resource(name = "photographer.photographerBookOrderDao")
	private PhotographerBookOrderDao photographerBookOrderDao;
	
	
	/**
	 * 
	 * 预约摄影第一步（BookHousePhotogDto 已经在上层校验）
	 *
	 * @author yd
	 * @created 2016年11月8日 上午10:45:01
	 *
	 * @param bookHousePhotogDto
	 * @return
	 */
	public int  saveBookHousePhotographer(BookHousePhotogDto bookHousePhotogDto){

		PhotographerBookOrderEntity photographerBookOrder = new  PhotographerBookOrderEntity();
		
		BeanUtils.copyProperties(bookHousePhotogDto, photographerBookOrder);
		photographerBookOrder.setBookOrderSn(SnUtil.getBookOrderSn());
		photographerBookOrder.setFid(UUIDGenerator.hexUUID());
		photographerBookOrder.setBookOrderStatu(BookOrderStatuEnum.ORDER_BOOKING.getCode());
		photographerBookOrder.setBookOrderSource(BookOrderSourceEnum.MINSU_LANDLORD.getCode());
		photographerBookOrder.setBusinessType(BusinessTypeEnum.BUSINESS_MINSU.getCode());
		
		LogUtil.info(logger, "bookHousePhotographer->预约摄影下单：参数bookHousePhotogDto={},订单信息photographerBookOrder={}", JsonEntityTransform.Object2Json(bookHousePhotogDto),photographerBookOrder.toJsonStr());
	
		int i = this.photographerBookOrderDao.savePhotographerBookOrder(photographerBookOrder);
		
		if(i>0){
			HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
			houseBaseExt.setHouseBaseFid(bookHousePhotogDto.getHouseFid());
			//第一步更新为预约中
			houseBaseExt.setIsPhotography(IsPhotographyEnum.IS_PHOTOGRAPHY_ING.getCode());
			this.houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);
		}
		return i;
	}
	
	
	/**
	 * 
	 * 查询摄影师预约订单
	 *
	 * @author yd
	 * @created 2016年11月8日 下午8:07:35
	 *
	 * @param houseFid
	 * @return
	 */
	public List<PhotographerBookOrderEntity> queryPhotographerBookOrderByHouseFid(String houseFid){
		return photographerBookOrderDao.queryPhotographerBookOrderByHouseFid(houseFid);
	}
	
	/**
	 * 查询摄影师预约订单
	 * @author zl
	 * @param bookOrderDto
	 * @return
	 */
	public PagingResult<PhotographerBookOrderVo> findPhotographerBookOrder(BookHousePhotogDto bookOrderDto){
		PagingResult<PhotographerBookOrderVo> list =photographerBookOrderDao.listPhotographerBookOrderByCondition(bookOrderDto);
		return list;
	}
	
	/**
	 * 
	 * 根据摄影师订单编号 修改信息  proxy 需要校验  a.订单是否存在 b. 修改状态是否正确 c.订单编号必须存在
	 * 1. 指定摄影师  修改摄影师  当前订单状态 由预约中 变成 预约成功
	 *    A. 修改当前订单状态  （填充当前摄影师）
	 *    B. 保存修改日志
	 * 2. 摄影师 摄影完成 点击完成  当前订单状态 由预约成功 变成 完成
	 *    A. 修改当前订单状态 
	 *    B. 保存修改日志
	 *
	 * @author yd
	 * @created 2016年11月4日 下午3:57:28
	 *
	 * @param photographerBookOrder
	 * @return
	 */
	public int updatePhotographerBookOrderBySn(PhotographerBookOrderEntity photographerBookOrder,PhotogOrderUpdateDto photogOrderUpdateDto){
		
		//修改订单
		int i = photographerBookOrderDao.updatePhotographerBookOrderBySn(photographerBookOrder);

		if(i>0){
			PhotographerBookLogEntity photographerBookLo = new PhotographerBookLogEntity();
			
			photographerBookLo.setBookOrderSn(photographerBookOrder.getBookOrderSn());
			photographerBookLo.setCreaterFid(photogOrderUpdateDto.getCreaterFid());
			photographerBookLo.setCreaterType(photogOrderUpdateDto.getCreaterType());
			photographerBookLo.setFromStatu(photogOrderUpdateDto.getOldStatu());
			photographerBookLo.setFid(UUIDGenerator.hexUUID());
			photographerBookLo.setIsDel(IsDelEnum.NOT_DEL.getCode());
			photographerBookLo.setToStatu(photographerBookOrder.getBookOrderStatu());
			this.photographerBookLogDao.savePhotographerBookLog(photographerBookLo);
			/** 重新指定摄影师需要更新记录到新表t_photographer_book_change_log*/
			if(photogOrderUpdateDto.getUpdateType() == UpdateTypeEnum.UPDATE_APPOINTED_PHOTOG_MODIFY.getCode()){
				PhotographerBookChangeLogEntity bookChangeLogEntity = new PhotographerBookChangeLogEntity();
				bookChangeLogEntity.setBookOrderSn(photographerBookOrder.getBookOrderSn());
				bookChangeLogEntity.setCreaterFid(photogOrderUpdateDto.getCreaterFid());
				bookChangeLogEntity.setCreaterType(photogOrderUpdateDto.getCreaterType());
				bookChangeLogEntity.setFromPhotographerUid(photogOrderUpdateDto.getPreGrapherUid());
				bookChangeLogEntity.setToPhotographerUid(photographerBookOrder.getPhotographerUid());
				photographerBookChangeLogDao.saveBookChangeLog(bookChangeLogEntity);
			}
			/** 如果选择了作废可再预约，修改base_ext表的状态*/
			PhotographerBookOrderEntity bookOrder = photographerBookOrderDao.queryPhotographerBookOrderBySn(photographerBookOrder.getBookOrderSn());
			if(photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.DOOR_NOT_PHOTO.getCode() || photogOrderUpdateDto.getUpdateType().intValue() == BookOrderStatuEnum.NOT_DOORANDPHOTO.getCode()){
				HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
				houseBaseExt.setHouseBaseFid(bookOrder.getHouseFid());
				//更新为未预约状态
				houseBaseExt.setIsPhotography(IsPhotographyEnum.IS_NOT_PHOTOGRAPHY.getCode());
				this.houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);
			}
			/** 如果操作完成状态，更新表的状态为已预约摄影*/
			if(photogOrderUpdateDto.getUpdateType().intValue() == UpdateTypeEnum.UPDATE_PHOTO_FINISHED.getCode()){
				HouseBaseExtEntity houseBaseExt = new HouseBaseExtEntity();
				houseBaseExt.setHouseBaseFid(bookOrder.getHouseFid());
				//更新为未预约状态
				houseBaseExt.setIsPhotography(IsPhotographyEnum.IS_PHOTOGRAPHY.getCode());
				this.houseBaseExtDao.updateHouseBaseExtByHouseBaseFid(houseBaseExt);
			}
		}


		return i;
	}
	
	/**
	 * 
	 * 查询摄影师预约订单
	 *
	 * @author yd
	 * @created 2016年11月8日 下午8:07:35
	 *
	 * @param bookOrderSn
	 * @return
	 */
	public PhotographerBookOrderEntity queryPhotographerBookOrderBySn(String bookOrderSn){
		return photographerBookOrderDao.queryPhotographerBookOrderBySn(bookOrderSn);
	}

	/**
	 * 查询摄影师预约订单
	 * @author lunan
	 * @created 2017年02月27日
	 *
	 * @param houseFid
	 * @return
	 */
	public PhotographerBookOrderEntity findBookOrderByHouseFid(String houseFid){
		return photographerBookOrderDao.findBookOrderByHouseFid(houseFid);
	}

	/**
	 * 查询预约摄影单的日志记录
	 * @param bookOrderSn
	 * @return
	 */
	public List<PhotographerBookLogEntity> findLogs(String bookOrderSn){
		return photographerBookLogDao.findLogs(bookOrderSn);
	}
	
}
