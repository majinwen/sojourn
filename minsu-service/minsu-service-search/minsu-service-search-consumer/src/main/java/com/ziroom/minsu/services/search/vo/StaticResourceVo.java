package com.ziroom.minsu.services.search.vo;

import java.util.List;

import com.ziroom.minsu.entity.base.StaticResourceEntity;
import com.ziroom.minsu.entity.base.StaticResourcePicEntity;
/**
 * 
 * <p>静态资源</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zl
 * @since 1.0
 * @version 1.0
 */
public class StaticResourceVo extends StaticResourceEntity {
     
	/**
	 * 
	 */
	private static final long serialVersionUID = -4908325768432326926L;
	
	/**
	 * 静态资源图片列表
	 */
	private List<StaticResourcePicEntity> staticResourcePicList;

	public List<StaticResourcePicEntity> getStaticResourcePicList() {
		return staticResourcePicList;
	}

	public void setStaticResourcePicList(List<StaticResourcePicEntity> staticResourcePicList) {
		this.staticResourcePicList = staticResourcePicList;
	} 

}