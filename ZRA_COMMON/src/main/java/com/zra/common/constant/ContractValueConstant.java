package com.zra.common.constant;
/**
 * <p>一些常量的定义</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年11月10日
 * @since 1.0
 */
public class ContractValueConstant {
	//解约参数start
	public static final String UNRENT_PREFIX = "JY";//生成退租协议ID前缀
	
	//解约参数end
	
	//合同详情页参数start
	public static final int LINK_TYPE_HTML = 1; //跳转链接到HTML
	public static final int LINK_TYPE_PDF = 2;// 跳转链接到PDF
	public static final int LINK_TYPE_PROMPT = 3;// 给予提示"您已签署纸质合同，所有信息均以纸质合同为准"

	public static final String DECODE_XQ = "XQ";//查看合同详情页decode参数
	public static final String DECODE_QY = "QY";//查看合同详情页decode参数
	
	public static final int CONTACT_ZO_YES = 1;//显示联系管家
	public static final int CONTACT_ZO_NO = 0;//不显示
	
	public static final int CLOSE_CONTRACT_YES = 1;//可以关闭合同
	public static final int CLOSE_CONTRACT_NO = 0;//不可以关闭合同
	
	public static final String COLOR_OF_ORANGER = "#FF8C00";
	
	public static final int RENT_XZ_TIME_THIRTY = 30;//合同大于-1小于30天显示续约。
	//合同到期后一天可以显示签约按钮
	public static final int RENT_XZ_TIME_ZERO = -1;//合同大于-1小于30天显示续约。
	
	public static final int CONTACT_NEXT_DAY_ZO_TIME = 19;//大于19点联系第二天值班的管家
	
	//合同详情页参数end
	public static final String QIBAO_PROJECT_ID = "2c908d194f5f09b8014f62b8a9ab0024";
	

}
