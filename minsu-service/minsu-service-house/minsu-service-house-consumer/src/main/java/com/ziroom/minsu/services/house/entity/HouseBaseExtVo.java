package com.ziroom.minsu.services.house.entity;

import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.entity.house.HouseBaseExtEntity;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;

/**
 * 
 * <p>房源基本信息扩展vo</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class HouseBaseExtVo extends HouseBaseExtEntity {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6809162793638115404L;
	
	// 操作步骤
	private Integer operateSeq;
	
	// 房源配置集合
	private List<HouseConfMsgEntity> houseConfList = new ArrayList<HouseConfMsgEntity>();


	public Integer getOperateSeq() {
		return operateSeq;
	}
	
	public void setOperateSeq(Integer operateSeq) {
		this.operateSeq = operateSeq;
	}
	
	public List<HouseConfMsgEntity> getHouseConfList() {
		return houseConfList;
	}

	public void setHouseConfList(List<HouseConfMsgEntity> houseConfList) {
		this.houseConfList = houseConfList;
	}

}
