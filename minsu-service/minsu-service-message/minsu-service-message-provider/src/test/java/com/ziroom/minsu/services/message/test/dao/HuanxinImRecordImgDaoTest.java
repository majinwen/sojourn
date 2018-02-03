/**
 * @FileName: HuanxinImRecordImgDaoTest.java
 * @Package com.ziroom.minsu.services.message.test.dao
 * 
 * @author loushuai
 * @created 2017年9月8日 下午3:39:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.test.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.entity.message.HuanxinImRecordImgEntity;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordDao;
import com.ziroom.minsu.services.message.dao.HuanxinImRecordImgDao;
import com.ziroom.minsu.services.message.dto.DealImYellowPicRequest;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import com.ziroom.minsu.valenum.base.AuthIdentifyEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */
public class HuanxinImRecordImgDaoTest extends BaseTest{

	

	@Resource(name = "message.huanxinImRecordImgDao")
	private HuanxinImRecordImgDao huanxinImRecordImgDao;
	
	@Resource(name = "message.huanxinImRecordDao")
	private HuanxinImRecordDao huanxinImRecordDao;
	
	@Test
	public void saveHuanxinImRecordTest() {
		for (int i = 0; i <10; i++) {
			HuanxinImRecordImgEntity imRecord = new HuanxinImRecordImgEntity();
			imRecord.setHuanxinFid(UUIDGenerator.hexUUID());
			imRecord.setZiroomFlag("ZIROOM_CHANGZU_IM");
			imRecord.setUrl("http://my.csdn.net/zs75622126");
			this.huanxinImRecordImgDao.saveHuanxinImRecordImg(imRecord);
		}
	}
	
	@Test
	public void updateByParamTest() {
		
		HuanxinImRecordImgEntity imRecord = new HuanxinImRecordImgEntity();
		imRecord.setHuanxinFid("8a9e9aa35e560056015e560056890000");
		imRecord.setZiroomFlag("ZIROOM_CHANGZU_IM");
		imRecord.setUrl("http://pic.sogou.com/pics/recompic/detail.jsp?category=%E5%A3%81%E7%BA%B8&tag=%E9%A3%8E%E6%99%AF#0%26799954%260");
		imRecord.setIsCertified(1);
		imRecord.setIsCompliance(1);
		this.huanxinImRecordImgDao.updateByParam(imRecord);
	}
	
	@Test
	public void queryAllNeedDealImPicTest() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ziroomFlag", AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType());
		paramMap.put("isCertified", YesOrNoEnum.NO.getCode());
		List<HuanxinImRecordImgEntity> list = huanxinImRecordImgDao.queryAllNeedDealImPic(paramMap);
		for (HuanxinImRecordImgEntity huanxinImRecordImg : list) {
			String url = huanxinImRecordImg.getUrl();
			//调用鉴黄接口
			
			HuanxinImRecordImgEntity newHuanxinImRecordImg = new HuanxinImRecordImgEntity();
			int i=1;
			if(i<3){//是黄色图片
				i++;
				newHuanxinImRecordImg.setHuanxinFid(huanxinImRecordImg.getHuanxinFid());
				newHuanxinImRecordImg.setIsCertified(YesOrNoEnum.YES.getCode());
				newHuanxinImRecordImg.setIsCompliance(YesOrNoEnum.YES.getCode());
				int updateByParam = huanxinImRecordImgDao.updateByParam(newHuanxinImRecordImg);
				if(updateByParam>0){
					HuanxinImRecordEntity huanxinImRecord = new HuanxinImRecordEntity();
					huanxinImRecord.setFid(huanxinImRecordImg.getHuanxinFid());
					huanxinImRecord.setUrl("http://pic.sogou.com/pics/recompic/detail.jsp?category=%E5%A3%81%E7%BA%B8&tag=%E9%A3%8E%E6%99%AF#1%261126567%260");//TODO 
					huanxinImRecordDao.updateByParam(huanxinImRecord);
				}
			}else{
				newHuanxinImRecordImg.setHuanxinFid(huanxinImRecordImg.getHuanxinFid());
				newHuanxinImRecordImg.setIsCertified(YesOrNoEnum.YES.getCode());
				int updateByParam = huanxinImRecordImgDao.updateByParam(newHuanxinImRecordImg);
			}
	   }
	}
	
	@Test
	public void testgetAllAbnormalPic(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ziroomFlag", AuthIdentifyEnum.ZIROOM_CHANGZU_IM.getType());
		map.put("isCertified", YesOrNoEnum.YES.getCode());
		map.put("isCompliance",  YesOrNoEnum.YES.getCode());
		List<String> allAbnormalPic = huanxinImRecordImgDao.getAllAbnormalPic(map);
		System.out.println(allAbnormalPic);
	}
}
