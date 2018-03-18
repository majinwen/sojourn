/**
 * @FileName: RentCheckinPersonVo.java
 * @Package com.ziroom.zrp.service.trading.entity
 * 
 * @author bushujie
 * @created 2017年12月5日 下午2:14:56
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.zrp.service.trading.entity;

import java.io.Serializable;
import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;


/**
 * <p>入住人列表实体类</p>
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
@Data
public class RentCheckinPersonVo extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4754464774881700266L;
	/**
	 * 入住人uid
	 */
	private String personUid;
	/**
	 * 入住人姓名
	 */
	private String personName;
	/**
	 * 合同编号
	 */
	private String rentCode;
	/**
	 * 出租方式
	 */
	private Integer rentType;
	/**
	 * 房间编号
	 */
	private String roomCode;
	/**
	 * 房型名称
	 */
	private String houseTypeName;
	/**
	 * 朝向
	 */
	private String direction;
	/**
	 * 签约时间
	 */
	private Date conSignDate;
	/**
	 * 合同状态
	 */
	private String conStatus;
	/**
	 * 录入状态
	 */
	private Integer personDataStatus; 
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 合同id
	 */
	private String contractId;
	
	/**
	 * 项目id
	 */
	private String projectId;
	
	/**
	 * 客户类型：1 普通个人客户 2 企悦会员工 3 企业客户
	 */
	private Integer customerType;

    /**
     * 合同子表id
     */
	private String rentedetailId;
	
}
