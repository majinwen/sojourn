package com.ziroom.minsu.services.message.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.message.HuanxinImRecordEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.message.dto.DealImYellowPicRequest;
import com.ziroom.minsu.services.message.dto.MsgSyncRequest;
import com.ziroom.minsu.services.message.dto.PeriodHuanxinRecordRequest;

/**
 * 
 * <p>环信IM聊天记录持久层</p>
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
@Repository("message.huanxinImRecordDao")
public class HuanxinImRecordDao {

	private String SQLID = "message.huanxinImRecordDao.";
	
	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	/**
	 * 
	 * 保存环信IM聊天记录
	 *
	 * @author yd
	 * @created 2016年9月9日 下午3:20:18
	 *
	 * @param imRecord
	 * @return
	 * 
	 */
	public int saveHuanxinImRecord(HuanxinImRecordEntity imRecord){
		return mybatisDaoContext.save(SQLID+"insertSelective", imRecord);
	}
	
	/**
	 * 
	 * 分页查询 聊天信息
	 *
	 * @author yd
	 * @created 2017年8月2日 下午2:13:30
	 *
	 * @param msgSyncRequest
	 * @return
	 */
	public PagingResult<HuanxinImRecordEntity> queryHuanxinImRecordByPage(MsgSyncRequest msgSyncRequest){
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgSyncRequest.getLimit());
		pageBounds.setPage(msgSyncRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"queryHuanxinImRecordByPage", HuanxinImRecordEntity.class, msgSyncRequest,pageBounds);
	}

	/**
	 * 获取用户24小时内聊天记录（t_huanxin_im_record表）
	 *
	 * @author loushuai
	 * @created 2017年9月5日 下午2:20:46
	 *
	 * @param object2Json
	 * @return
	 */
	public PagingResult<HuanxinImRecordEntity> queryUserChatInTwentyFour(PeriodHuanxinRecordRequest msgSyncRequest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setLimit(msgSyncRequest.getLimit());
		pageBounds.setPage(msgSyncRequest.getPage());
		return this.mybatisDaoContext.findForPage(SQLID+"queryUserChatInTwentyFour", HuanxinImRecordEntity.class, msgSyncRequest,pageBounds);
	}

	/**
	 * TODO
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午3:10:18
	 *
	 * @param huanxinImRecord
	 * @return
	 */
	public long getCountMsgEach(Map<String, Object> params) {
		return this.mybatisDaoContext.count(SQLID+"getCountMsgEach", params);
	}

	/**
	 * 处理黄色图片
	 *
	 * @author loushuai
	 * @created 2017年9月7日 下午8:56:17
	 *
	 * @param pageRquest
	 * @return
	 */
	public PagingResult<HuanxinImRecordEntity> queryAllNeedDealImPic(DealImYellowPicRequest rquest) {
		PageBounds pageBounds = new PageBounds();
		pageBounds.setPage(rquest.getPage());
		pageBounds.setLimit(rquest.getLimit());	
		return mybatisDaoContext.findForPage(SQLID+"queryAllNeedDealImPic", HuanxinImRecordEntity.class, rquest, pageBounds);
		
	}

	/**
	 * 根据条件修改
	 *
	 * @author loushuai
	 * @created 2017年9月8日 下午4:41:29
	 *
	 * @param huanxinImRecord
	 * @return
	 */
	public int updateByParam(HuanxinImRecordEntity huanxinImRecord) {
		return mybatisDaoContext.update(SQLID+"updateByParam", huanxinImRecord);
	}
	
	public HuanxinImRecordEntity getByHuanxinFid(String huanxinFid){
		return mybatisDaoContext.findOne(SQLID + "getByHuanxinFid", HuanxinImRecordEntity.class, huanxinFid);
	} 
}
