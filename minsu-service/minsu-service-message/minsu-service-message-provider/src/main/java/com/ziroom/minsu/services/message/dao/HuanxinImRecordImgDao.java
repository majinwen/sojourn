package com.ziroom.minsu.services.message.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.message.HuanxinImRecordImgEntity;
import com.ziroom.minsu.services.message.dto.DealImYellowPicRequest;

@Repository("message.huanxinImRecordImgDao")
public class HuanxinImRecordImgDao {
	
private String SQLID = "message.huanxinImRecordImgDao.";
	
	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
    
	/**
	 * 
	 * 插入实体
	 *
	 * @author loushuai
	 * @created 2017年9月8日 下午3:21:01
	 *
	 * @param imRecord
	 * @return
	 */
	public int saveHuanxinImRecordImg(HuanxinImRecordImgEntity imRecord){
		return mybatisDaoContext.save(SQLID+"insertSelective", imRecord);
	}
	
	/**
	 * 
	 * 修改实体
	 *
	 * @author loushuai
	 * @created 2017年9月8日 下午3:24:00
	 *
	 * @param imRecord
	 * @return
	 */
	public int updateByParam(HuanxinImRecordImgEntity imRecord){
		return mybatisDaoContext.update(SQLID+"updateByParam", imRecord);
	}

	/**
	 * 获取所有尚未鉴别的照片
	 *
	 * @author loushuai
	 * @created 2017年9月8日 下午3:57:12
	 *
	 * @return
	 */
	public List<HuanxinImRecordImgEntity> queryAllNeedDealImPic(Map<String, Object> paramMap) {
		return mybatisDaoContext.findAll(SQLID+"queryAllNeedDealImPic", HuanxinImRecordImgEntity.class, paramMap);
	}

	/**
	 * 从t_huanxin_im_record_img获取所有is_certified=1和is_compliance=1的huanxin_fid集合
	 *
	 * @author loushuai
	 * @created 2017年9月13日 上午10:18:49
	 *
	 * @param map
	 * @return
	 */
	public List<String> getAllAbnormalPic(Map<String, Object> map) {
		return mybatisDaoContext.findAll(SQLID+"getAllAbnormalPic", String.class, map);
	}
}