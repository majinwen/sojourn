package com.ziroom.minsu.services.basedata.api.inner;

/**
 * <p>程式模板测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/21.
 * @version 1.0
 * @since 1.0
 */
public interface CityTemplateService {


	/**
	 * 获取当前的模板列表
	 * @return
	 */
	String  getTemplateListByPage(String paramJson);



	/**
	 * 获取字典结构
	 * @param line 业务线标志
	 * @return
	 */
	String getDicTree(String line);



	/**
	 * 获取当前节点下的子节点信息
	 * @param pfid
	 * @return
	 */
	String getConfDicByPfid(String pfid);


	/**
	 * 获取当前字典信息的列表
	 * @return
	 */
	String getConfDicByFid(String fid);


	/**
	 * 获取当前城市的模板信息
	 * @param cityCode
	 * @return
	 */
	String getCityTemplateByCityCode(String cityCode);

	/**
	 * 获取当前城市的模板信息
	 * @param paramJson
	 * @return
	 */
	String insertCityTemplate(String paramJson);


	/**
	 *
	 * @param confDicJosn
	 * @return
	 */
	String updateConfDicByFid(String confDicJosn);

	/**
	 * 插入字典信息
	 * @param confDicJosn
	 * @return
	 */
	String insertConfDic(String confDicJosn);


	/**
	 * 获取字典值列表
	 * @param code
	 * @param templateFid
	 * @return
	 */
	String getDicItemListByCodeAndTemplate(String code,String templateFid);



	/**
	 * 插入字典值信息
	 * @param dicItemJosn
	 * @return
	 */
	String insertDicItem(String dicItemJosn);


	/**
	 * 更新字典值信息
	 * @param dicItemJosn
	 * @return
	 */
	String updateDicItem(String dicItemJosn);

	/**
	 * 插入模板信息
	 * @param templateJosn
	 * @return
	 */
	String insertTemplate(String templateJosn);

	/**
	 * 
	 * 查询枚举值集合
	 *
	 * @author bushujie
	 * @created 2016年3月22日 下午7:02:35
	 *
	 * @param templateFid
	 * @param dicCode
	 * @return
	 */
	String getSelectEnum(String cityCode,String dicCode);


	/**
	 * 查询枚举值集合（有效的）
	 * @author lishaochuan
	 * @create 2016年5月31日下午8:51:53
	 * @param cityCode
	 * @param dicCode
	 * @return
	 */
	public String getEffectiveSelectEnum(String cityCode, String dicCode);
	
	
	/**
	 * 
	 * 查询枚举值集合（有效的）
	 *
	 * @author zl
	 * @created 2017年3月13日 下午4:26:23
	 *
	 * @param cityCode
	 * @param dicCode
	 * @return
	 */
	public String getEffectiveSelectEnum(String cityCode, String dicCode,boolean fromCache);

	/**
	 * 
	 * 计算值的个数
	 *
	 * @author liyingjie
	 * @created 2016年3月24日 
	 *
	 * @param templateFid
	 * @param dicCode
	 * @return
	 */
	String countItemNumList(String templateFid, String dicCode);

	/**
	 * 
	 * 查询配置项唯一值
	 *
	 * @author bushujie
	 * @created 2016年3月26日 下午4:21:47
	 *
	 * @param cityCode
	 * @param dicCode
	 * @return
	 */
	String getTextValue(String cityCode,String dicCode);


	/**
	 * 查询配置项唯一值 前缀匹配
	 * @author afi
	 * @created 2016年4月21日
	 * @param cityCode
	 * @param dicCodes  多个用，隔开
	 * @return
	 */
	String getTextListByLikeCodes(String cityCode,String dicCodes);


	/**
	 * 查询配置项唯一值
	 * @author afi
	 * @created 2016年4月21日
	 * @param cityCode
	 * @param dicCodes  多个用，隔开
	 * @return
	 */
	String getTextListByCodes(String cityCode,String dicCodes);


	/**
	 * 获取 强制取消房东无责任时限、罚金交付时限、罚金天数
	 * @author lishaochuan
	 * @create 2016年5月3日
	 * @return
	 */
	public String getConfigForceVo();

	/**
	 * 
	 * 父项code查询子项列表
	 *
	 * @author bushujie
	 * @created 2016年3月26日 下午5:35:27
	 *
	 * @param dicCode
	 * @return
	 */
	String getSelectSubDic(String cityCode,String dicCode);

	/**
	 * 
	 * 获取房源照片 标准
	 *
	 * @author yd
	 * @created 2016年10月19日 下午7:33:16
	 *
	 * @return
	 */
	public  String getPicValidParams();

	/**
	 * 更新配置项列表
	 *
	 * @author liujun
	 * @created 2017年1月10日
	 *
	 * @param paramJson
	 * @return
	 */
	String updateDicItemList(String paramJson);

	/**
	 * 获取自如寓支付方式列表
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年09月12日 17:42:22
	 */
	String listZrpPayStyle(String paramJson);

	/**
	 * 通用获取单个配置项
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年09月13日 14:44:49
	 */
	String getTextValueForCommon(String serviceLine, String dicCode);


	/**
	 * 通用获取配置多个值获取多个值
	 *
	 * @param
	 * @return
	 * @author jixd
	 * @created 2017年09月14日 10:37:33
	 */
	String listTextValueForCommon(String serviceLine, String dicCode);

}
