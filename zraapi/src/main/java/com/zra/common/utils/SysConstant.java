package com.zra.common.utils;

/**
 * @author wangws21
 * 系统常量
 */
public class SysConstant {

	/**
	 * 管理员角色唯一标识
	 */
	public static final String ADMIN_ROLE_ID = "E4DA27D2E77840F0E040007F0100728B";
	
	/**
	 * 管家角色名称
	 */
	public static final String ZO_ROLE_NAME = "ZO";
	
	/**
	 * 项目经理唯一标识
	 */
	public static final String PMID = "8a8ac425524a066901525882ef090104"; 
	
	/**
	 * 约看提醒短信发送提前时间(单位:秒)
	 */
	public static final int YUEKAN_REMIND_SMS_ADVANCED_TIME = 4 * 60 * 60; 
	
	/**
	 * 带看评价短信发送提前时间(单位:秒)
	 */
	public static final int EVALUATE_REMIND_SMS_ADVANCED_TIME = -2 * 60 * 60;
	
	/**
	 * 系统管理员（temployee的fid）
	 */
	public static final String ADMINID = "1";
	
	/**
	 * wangws21 2017-1-16  客源量计算 时间 默认 -30.
	 */
	public static final int KYL_ADVANCE_DAYS = -30;
	
	/**
	 * 角色：自如寓营销 用于渠道配置的权限控制
	 * @author tianxf9
	 */
	public static final String MK_ROLE_ID = "8a8ac426523ef433015253aa5baa01a9";

	/**
	 * M站ZO的积极评价标签数
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2017-03-14
	 */
	public static final String M_ZO_HAS_LABEL_NUM = "M_ZO_HAS_LABEL_NUM";

	/**
	 * M站ZO的标签数
	 *
	 * @Author: wangxm113
	 * @CreateDate: 2017-03-14
	 */
	public static final String M_ZO_LABEL_COUNT = "M_ZO_LABEL_COUNT";

	/**
	 * 卡券状态
	 * 卡券消费情况状态：1：消费成功；2：已回退；3：回退失败
	 */
	public static final Integer CARD_COUPON_USAGE_STATE_YXF = 1;
	public static final Integer CARD_COUPON_USAGE_STATE_YTH = 2;
	public static final Integer CARD_COUPON_USAGE_STATE_HTSB = 3;

	/**
	 * 卡券有效状态 0-有效 10-无效
	 */
	public static final Integer CARD_COUPON_STATUS = 0;//有效
	
	/**
	 * @author tianxf9
	 */
	public static final String STATE = "state";
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	
	private SysConstant() {}
}
