/**
 * @FileName: UploadOrderController.java
 * @Package com.asura.amp.common.pagetools
 * 
 * @author lijie
 * @created 2013-6-25 上午9:55:48
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.pagetools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.asura.framework.base.paging.SearchCondition;
import com.asura.framework.base.paging.SearchModel;

/**
 * 分页工具类
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lijie
 * @since 1.0
 * @version 1.0
 */
public class PageTools {

	/**
	 * 
	 * 分页查询上传出库单信息
	 * 
	 * @author lijie
	 * @created 2013-6-25 上午10:59:56
	 * 
	 * @param searchModel
	 * @param model
	 */
	public static Map<String, Object> headerList(SearchModel searchModel,
			Model model, int total) {
		// 创建查询条件map
		Map<String, Object> conditions = new HashMap<String, Object>();
		// 把SearchModel中条件取出，放入conditions中
		if (searchModel.getSearchConditionList() != null
				&& searchModel.getSearchConditionList().size() > 0) {
			List<SearchCondition> searchList = searchModel
					.getSearchConditionList();
			for (SearchCondition con : searchList) {
				conditions.put(con.getName(), con.getValue());
			}

		}
		// 添加查询sql
		// String
		// sql="com.asura.management.lscm.uploadorder.dao.selectOrderHeaderCount";
		// 获取数据总数目，方便分页查询
		// 组装分页条件
		SearchModel newModel = deptMethod(searchModel, conditions, total);
		// 添加分页起始和查询数量
		conditions.put("startNo", newModel.getFirstRowNum());
		conditions.put("limit", newModel.getPageSize());
		// 调用查询方法，同时传入查询条件
		return conditions;

	}

	/**
	 * 
	 * 组装查询条件
	 * 
	 * @author lijie
	 * @created 2013-6-28 上午11:13:19
	 * 
	 * @return
	 */
	private static SearchModel deptMethod(SearchModel searchModel,
			Map<String, Object> param, int total) {
		// 每页显示件数
		int pageSize = searchModel.getPageSize();
		// 总共页数
		int pageCount = total > 1 ? (total - 1) / pageSize + 1 : 1;

		// 当前页的范围控制，防止过界（1 - 总页数）
		if (searchModel.getPage() > pageCount && pageCount > 0) {
			searchModel.setPage(pageCount);
		} else if (searchModel.getPage() < 1) {
			searchModel.setPage(1);
		}

		if (pageCount == 0) {
			searchModel.setPage(1);
		}

		return searchModel;
	}

}
