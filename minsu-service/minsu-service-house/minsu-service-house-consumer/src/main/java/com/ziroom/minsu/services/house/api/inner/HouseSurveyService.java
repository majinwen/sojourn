package com.ziroom.minsu.services.house.api.inner;


/**
 * 
 * <p>房源实勘操作接口</p>
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
public interface HouseSurveyService {
	
	/**
	 * 
	 * 查询需要实勘房源列表
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findSurveyHouseListByPage(String paramJson);
	
	/**
	 * 
	 * 根据房源fid查询需要实勘房源信息
	 * recordStatus in (100, 101)
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String findHouseSurveyMsgByHouseFid(String houseBaseFid);
	
	/**
	 * 
	 * 新增房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param paramJson
	 * @return
	 */
	public String insertHouseSurveyMsg(String paramJson);
	
	/**
	 * 
	 * 更新房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseSurveyMsg(String paramJson);
	
	/**
	 * 
	 * 根据房源实勘图片fid查询房源实勘信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param surveyPicFid
	 * @return
	 */
	public String findHouseSurveyPicMsgByFid(String surveyPicFid);
	
	/**
	 * 
	 * 根据房源实勘fid与图片类型查询房源实勘图片列表
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param paramJson (surveyFid not null)
	 * @return
	 */
	public String findSurveyPicListByType(String paramJson);
	
	/**
	 * 
	 * 新增房源实勘图片信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param paramJson
	 * @return
	 */
	public String saveSurveyPicMsgList(String paramJson);
	
	/**
	 * 
	 * 更新房源实勘图片信息
	 *
	 * @author liujun
	 * @created 2016年11月16日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseSurveyPicMsg(String paramJson);

	/**
	 * 根据图片类型查询图片数量
	 *
	 * @author liujun
	 * @created 2016年11月17日
	 *
	 * @param paramJson
	 * @return
	 */
	public String findPicCountByType(String paramJson);
}
