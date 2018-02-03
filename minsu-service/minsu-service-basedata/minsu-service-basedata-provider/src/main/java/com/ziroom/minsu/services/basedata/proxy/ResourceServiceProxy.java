/**
 * @FileName: MenuOperServiceProxy.java
 * @Package com.ziroom.minsu.services.basedata.proxy
 * 
 * @author liyingjie
 * @created 2016年3月9日 上午11:28:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.basedata.proxy;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.sys.ResourceEntity;
import com.ziroom.minsu.services.basedata.api.inner.ResourceService;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.basedata.dto.ResourceRequest;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.logic.ParamCheckLogic;
import com.ziroom.minsu.services.basedata.service.ResourceServiceImpl;

/**
 * <p>
 * 后台菜单业务代理层
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liyingjie
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.resourceServiceProxy")
public class ResourceServiceProxy implements ResourceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServiceProxy.class);

	@Resource(name = "basedata.resourceServiceImpl")
	private ResourceServiceImpl resourceServiceImpl;

	@Resource(name = "basedata.messageSource")
	private MessageSource messageSource;

	@Resource(name = "basedata.paramCheckLogic")
	private ParamCheckLogic paramCheckLogic;

	/**
	 * 更新菜单信息
	 * 
	 * @param paramJson
	 * @return
	 */
	public String updateMenuByFid(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ResourceEntity menu = JsonEntityTransform.json2Object(paramJson, ResourceEntity.class);
			// 直接更新菜单信息
			resourceServiceImpl.updateMenuByFid(menu);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.MenuOperService#
	 * searchMenuResList(java.lang.String)
	 */
	@Override
	public String searchMenuResList(String paramJson) {
		DataTransferObject dto = new DataTransferObject();
		try {
			ResourceRequest menuOperRequest = JsonEntityTransform.json2Object(paramJson, ResourceRequest.class);
			// 条件查询后台用户
			PagingResult<ResourceEntity> pr = resourceServiceImpl.findMenuResList(menuOperRequest);
			dto.putValue("list", pr.getRows());
			dto.putValue("total", pr.getTotal());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));

		}
		return dto.toJsonString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.MenuOperService#
	 * insertMenuResource(java.lang.String)
	 */
	@Override
	public void insertMenuResource(String paramJson) {
		try {
			ResourceEntity menuResourceEntity = JsonEntityTransform.json2Object(paramJson, ResourceEntity.class);
			resourceServiceImpl.saveMenuResouce(menuResourceEntity);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ziroom.minsu.services.basedata.api.inner.MenuOperService#
	 * searchAllMenuChildResList()
	 */
	@Override
	public String searchAllMenuChildResList() {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<ResourceEntity> resList = resourceServiceImpl.findAllMenuChildList();
			dto.putValue("list", resList);
			dto.putValue("total", resList.size());
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ziroom.minsu.services.basedata.api.inner.MenuOperService#menuTreeVo()
	 */
	@Override
	public String menuTreeVo() {
		DataTransferObject dto = new DataTransferObject();
		try {
			List<TreeNodeVo> menuTreeNodeVos = resourceServiceImpl.findMenuTreeNodeVos();
			dto.putValue("list", menuTreeNodeVos);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}
	
}
