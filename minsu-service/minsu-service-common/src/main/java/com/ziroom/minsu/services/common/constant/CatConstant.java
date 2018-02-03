package com.ziroom.minsu.services.common.constant;

/**
 * <p>cat的常亮设置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/2.
 * @version 1.0
 * @since 1.0
 */
public class CatConstant {

    /** 订单的数量 */
    public static final String ORDER_COUNT = "orderCount";

    /** 支付金额 */
    public static final String PAY_SUM = "payMoneySum";

    /** 超时取消数量 */
    public static final String CANCEL_TIME = "cancelTime";

    /** 支付取消数量 */
    public static final String CANCEL_PAY = "cancelHasPay";

    /** 提前退房数量 */
    public static final String CHECK_OUT_PRE = "checkOutPre";

    /** 提交订单页面的埋点 **/
    public static final String INIT_CREATE = "initCreatHouse";
    
    /** 点击去支付数量 **/
    public static final String TO_PAY = "toPay";
    
    /** 支付回调数量 **/
    public static final String PAY_CALL_BACK = "payCallBack";
    
    
    /**
     * Cat房源常量
     */
	public static class House {
		
		/** 发布房源数量 **/
		public static final String HOUSE_COUNT = "houseCount";
		
		/** 上架房源数量 **/
		public static final String HOUSE_UP_COUNT = "houseUpCount";
		
		/** 下架房源数量 **/
		public static final String HOUSE_DOWN_COUNT = "houseDownCount";
		
		/** 强制下架房源数量 **/
		public static final String FORCE_DOWN_HOUSE_COUNT = "forceDownHouseCount";
		
		/** 房源信息审核通过数量 **/
		public static final String APPROVE_HOUSE_INFO_COUNT = "approveHouseInfoCount";
		
		/** 房源信息审核未通过数量 **/
		public static final String UNAPPROVE_HOUSE_INFO_COUNT = "unapproveHouseInfoCount";
		
		/** 房源照片审核通过数量 **/
		public static final String APPROVE_HOUSE_PIC_COUNT = "approveHousePicCount";
		
		/** 房源照片审核未通过数量 **/
		public static final String UNAPPROVE_HOUSE_PIC_COUNT = "unapproveHousePicCount";
		
		/** 房源修改照片审核通过数量 **/
		public static final String APPROVE_MODIFIED_PIC_SUM = "approveModifiedPicSum";




		private House() {
			
		}
	}
    
	
	private CatConstant(){
		
	}

}
