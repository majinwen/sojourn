/**
 * @FileName: EvaluateAuthUtils.java
 * @Package com.ziroom.minsu.troy.auth
 * 
 * @author yd
 * @created 2016年10月31日 上午11:22:00
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.troy.auth.menu;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;

/**
 * <p>评价权限菜单 公用方法</p>
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
public class EvaluateAuthUtils {

	
	/**
	 * 
	 * 评价权限处理 权限说明：
	 * 1. 普通权限 正常走
	 * 2. 区域权限  查询角色区域下房源fid,查询评价
	 * 3. 数据角色 根据区域管家关系查询当前当前管家管的房源fid集合，查询评价
	 * 4. 数据区域权限  2和3取交集
	 * 5. 权限异常 直接返回页面null
	 * 6. 返回房源fid集合为null，代表无任何权限，也返回null
	 *
	 * @author yd
	 * @created 2016年10月31日 上午11:31:12
	 *
	 * @param authMenu
	 * @return
	 */
	public static DataTransferObject getAuthHouseFids(AuthMenuEntity authMenu,TroyHouseMgtService troyHouseMgtService){
		DataTransferObject dto = null;
		if(Check.NuNObj(troyHouseMgtService)){
			dto = new DataTransferObject();
			dto.setErrCode(DataTransferObject.ERROR);
			dto.setMsg("权限异常");
		}
		dto = JsonEntityTransform.json2DataTransferObject(troyHouseMgtService.findHouseFidByAuth(JsonEntityTransform.Object2Json(authMenu)));
		return dto;
	}
}
