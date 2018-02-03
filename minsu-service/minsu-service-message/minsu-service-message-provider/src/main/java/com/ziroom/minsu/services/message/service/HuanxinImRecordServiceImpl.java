/**
 * @FileName: HuanxinImRecordServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年9月10日 下午2:20:49
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.HuanxinImOfflineEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordImgEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImOfflineDao;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordDao;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordImgDao;
import com.ziroom.minsu.valenum.base.AuthIdentifyEnum;
import com.ziroom.minsu.valenum.msg.ImTypeEnum;

/**
 * <p>环信同步记录实现</p>
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
@Service("message.huanxinImRecordServiceImpl")
public class HuanxinImRecordServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(HuanxinImRecordServiceImpl.class);
	
	@Resource(name = "message.huanxinImRecordDao")
	private HuanxinImRecordDao huanxinImRecordDao;
	
	@Resource(name = "message.huanxinImOfflineDao")
	private HuanxinImOfflineDao huanxinImOfflineDao;
	
	@Resource(name = "message.huanxinImRecordImgDao")
	private HuanxinImRecordImgDao huanxinImRecordImgDao;
	
	/**
	 * 
	 * 保存记录
	 *
	 * @author lousshuai
	 * @created 2017年9月5日 下午2:23:55
	 *
	 * @param huanxinIm
	 * @return
	 */
	public int saveHuanxinImRecordAndOffline(HuanxinImRecordEntity imRecord){
		int save = this.huanxinImRecordDao.saveHuanxinImRecord(imRecord);
		if(save>0 && ImTypeEnum.IMG_MSG.getType().equals(imRecord.getType()) && AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType().equals(imRecord.getZiroomFlag())){
			HuanxinImRecordImgEntity huanxinImRecordImg = new HuanxinImRecordImgEntity();
			huanxinImRecordImg.setHuanxinFid(imRecord.getFid());
			huanxinImRecordImg.setZiroomFlag(AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType());
			huanxinImRecordImg.setUrl(imRecord.getUrl());
			huanxinImRecordImg.setFilename(imRecord.getFilename());
			save = save + huanxinImRecordImgDao.saveHuanxinImRecordImg(huanxinImRecordImg);
		}
		return save;
	}
	
	/**
	 * 
	 * 保存记录
	 *
	 * @author yd
	 * @created 2016年9月10日 下午2:23:55
	 *
	 * @param huanxinIm
	 * @return
	 */
	public int saveHuanxinImRecord(HuanxinImRecordEntity imRecord){
		return this.huanxinImRecordDao.saveHuanxinImRecord(imRecord);
	}
	
	/**
	 * 
	 * 批量保存环信记录，做异步批量保存
	 *
	 * @author yd
	 * @created 2016年9月10日 下午2:27:15
	 *
	 * @param listHuanxinImRecords
	 * @return
	 */
	public int bachSaveHuanxinImRecord(List<HuanxinImRecordEntity> listHuanxinImRecords){
		
		int upNum = 0;
		if(!Check.NuNCollection(listHuanxinImRecords)){
			LogUtil.info(LOGGER, "当前集合大小size={}", listHuanxinImRecords.size());
			for (HuanxinImRecordEntity huanxinImRecordEntity : listHuanxinImRecords) {
				upNum +=saveHuanxinImRecord(huanxinImRecordEntity);
			}
		}
		LogUtil.info(LOGGER, "成功更新数量upNum={}", upNum);
		return upNum;
	}

	/**
	 * TODO
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午3:10:00
	 *
	 * @param huanxinImRecord
	 * @return
	 */
	public long getCountMsgEach(Map<String, Object> params) {
		return this.huanxinImRecordDao.getCountMsgEach(params);
	}

	/**
	 * 
	 *
	 * @author loushuai
	 * @created 2017年9月19日 上午11:42:59
	 *
	 * @param json2Entity
	 */
	public int saveHuanxinOffline(HuanxinImOfflineEntity json2Entity) {
		return huanxinImOfflineDao.insertSelective(json2Entity);
	}
}
