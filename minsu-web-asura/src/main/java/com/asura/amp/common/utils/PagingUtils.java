/**
 * @FileName: PagingUtils.java
 * @Package com.asura.management.common.utils
 * 
 * @author zhangshaobin
 * @created 2012-12-25 下午4:32:06
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.utils;

import java.util.List;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchModel;

/**
 * <p>分页处理类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class PagingUtils {
	
	/**
	 * 按照查询条件进行全数据分页处理
	 *
	 * @author zhangshaobin
	 * @created 2012-12-25 下午4:32:48
	 *
	 * @param searchModel
	 * @param fullResultList
	 * @return
	 */
	public static <T extends BaseEntity> PagingResult<T> dealFullResult(SearchModel searchModel, List<T> fullResultList) {
		// 每页显示件数
		int pageSize = searchModel.getPageSize();
		// 总共页数
		int pageCount = fullResultList.size() > 1 ? (fullResultList.size() - 1) / pageSize + 1 : 1;
		
		// 当前页的范围控制，防止过界（1 - 总页数）
		if (searchModel.getPage() > pageCount) {
			searchModel.setPage(pageCount);
		} else if (searchModel.getPage() < 1) {
			searchModel.setPage(1);
		}
		
		// 当前页号
		int page = searchModel.getPage();
		
		// 全数据取得当前页数据
		int fromIndex = (page - 1) * pageSize;
		int toIndex = fromIndex + pageSize > fullResultList.size() ? fullResultList.size() : fromIndex + pageSize;
		
		PagingResult<T> pagingResult = new PagingResult<T>(fullResultList.size(), fullResultList.subList(fromIndex, toIndex));
		
		return pagingResult;
	}
}
