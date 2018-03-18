/**
 * @FileName: BaseDataService.java
 * @Package com.ziroom.minsu.report.board.service
 * 
 * @author bushujie
 * @created 2017年1月12日 下午5:21:41
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.report.board.service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.report.board.dao.BaseDataDao;
import com.ziroom.minsu.report.board.dto.EmpTargetItemRequest;
import com.ziroom.minsu.report.board.vo.EmpTargetItem;
import com.ziroom.minsu.report.board.vo.EmpTargetItemVo;
import com.ziroom.minsu.report.common.service.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
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
@Service("report.BaseDataServiceFoexcel")
public class BaseDataServiceForExcel implements ReportService<EmpTargetItemVo,EmpTargetItemRequest> {
	
	@Resource(name="report.baseDataDao")
	private BaseDataDao baseDataDao;
	
	/**
	 * 
	 * 分页查询员工目标列表
	 *
	 * @author bushujie
	 * @created 2017年1月12日 下午5:40:03
	 *
	 * @param guardAreaR
	 * @return
	 */
	public PagingResult<EmpTargetItem> findGaurdAreaByPage(EmpTargetItemRequest empTargetItemRequest){
		return baseDataDao.findGaurdAreaByPage(empTargetItemRequest);
	}

	/**
	 * 分页查询员工目标列表(分页专用)
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2017/1/13 18:12
	 */
	public PagingResult<EmpTargetItemVo> findGaurdAreaForExcel(EmpTargetItemRequest empTargetItemRequest){
		return baseDataDao.findGaurdAreaForExcel(empTargetItemRequest);
	}

	@Override
	public PagingResult<EmpTargetItemVo> getPageInfo(EmpTargetItemRequest par) {
		return null;
	}

	@Override
	public Long countDataInfo(EmpTargetItemRequest par) {
		return null;
	}
}
