package com.ziroom.minsu.services.house.api.inner;

/**
 * <p>特洛伊房源管理操作接口</p>
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
public interface TroyHouseMgtService {


	/**
	 * 根据houseSn查询房源信息
	 * @author lisc
	 * @param houseSn
	 * @return
     */
	public String findHouseBaseByHouseSn(String houseSn);

	/**
	 * 根据房间编号查找房间信息
	 * @author jixd
	 * @created 2016年11月21日 10:40:13
	 * @param
	 * @return
	 */
	public String findHouseRoomMsgByRoomSn(String roomSn);

    /**
     *
     * 后台查询房源信息列表
     *
     * @author liujun
     * @created 2016年4月6日 下午2:17:16
     *
     * @param paramJson JsonEntityTransform.Object2Json(HouseRequestDto dto)
     * @return
     */
    public String searchHouseMsgList(String paramJson);
    
    
    /**
    *
    * 后台查询房源信息列表(区分houseFid和roomFid)
    *
    * @author zl
    * @created 2016年11月21日
    *
    * @param paramJson 
    * @return
    */
   public String searchHouseMsgListNew(String paramJson);

    /**
     *
     * 根据房源逻辑id查询房源详情
     *
     * @author liujun
     * @created 2016年4月7日 下午2:25:19
     *
     * @param houseBaseFid
     * @return
     */
    public String searchHouseDetailByFid(String houseBaseFid);

    /**
     *
     * 根据房间逻辑id查询房间详情
     *
     * @author liujun
     * @created 2016年4月7日 下午11:15:45
     *
     * @param houseRoomFid
     * @return
     */
    public String searchRoomDetailByFid(String houseRoomFid);

    /**
     *
     * 强制下架房源
     *
     * @author liujun
     * @created 2016年4月7日 下午3:13:14
     *
     * @param houseBaseFid
     * @param operaterFid
     * @param remark
     * @return
     */
    public String forceDownHouse(String houseBaseFid, String operaterFid, String remark);

    /**
     *
     * 强制下架房间
     *
     * @author liujun
     * @created 2016年4月7日 下午3:41:15
     *
     * @param houseRoomFid
     * @param operaterFid
     * @param remark
     * @return
     */
    public String forceDownRoom(String houseRoomFid, String operaterFid, String remark);

    /**
     *
     * 房源信息审核通过
     *
     * @author liujun
     * @created 2016年4月7日 下午4:13:21
     *
     * @param houseBaseFid 房源逻辑id
     * @param operaterFid 登录账户逻辑id
     * @param remark 备注
     * @param addtionalInfo 补充信息 
     * @return
     */
    public String approveHouseInfo(String houseBaseFid, String operaterFid, String remark, String addtionalInfo);

    /**
     *
     * 房间信息审核通过
     *
     * @author liujun
     * @created 2016年4月7日 下午4:34:31
     *
     * @param houseRoomFid 房间逻辑id
     * @param operaterFid 登录账户逻辑id
     * @param remark 备注
     * @param addtionalInfo 补充信息 
     * @return
     */
    public String approveRoomInfo(String houseRoomFid, String operaterFid, String remark, String addtionalInfo);

    /**
     *
     * 房源信息审核未通过
     *
     * @author liujun
     * @created 2016年4月7日 下午4:28:36
     *
     * @param houseBaseFid 房源逻辑id
     * @param operaterFid 登录账户逻辑id
     * @param remark 备注 
     * @param addtionalInfo 补充信息 
     * @return
     */
    public String unApproveHouseInfo(String houseBaseFid, String operaterFid, String remark, String addtionalInfo);

    /**
     *
     * 房间信息审核未通过
     *
     * @author liujun
     * @created 2016年4月7日 下午5:06:22
     *
     * @param houseRoomFid 房间逻辑id
     * @param operaterFid 登录账户逻辑id
     * @param remark 备注 
     * @param addtionalInfo 补充信息 
     * @return
     */
    public String unApproveRoomInfo(String houseRoomFid, String operaterFid, String remark, String addtionalInfo);

    /**
     *
     * 房源照片审核通过
     *
     * @author liujun
     * @created 2016年4月7日 下午5:11:09
     * @updated 2016年11月3日
     *
     * @param paramJson
     * @return
     */
    public String approveHousePic(String paramJson);

    /**
     *
     * 房间照片审核通过
     *
     * @author liujun
     * @created 2016年4月7日 下午5:12:07
     * @updated 2016年11月3日
     *
     * @param paramJson
     * @return
     */
    public String approveRoomPic(String paramJson);

    /**
     *
     * 房源照片审核未通过
     *
     * @author liujun
     * @created 2016年4月7日 下午5:12:53
     * @updated 2016年11月3日
     *
     * @param paramJson
     * @return
     */
    public String unApproveHousePic(String paramJson);

    /**
     *
     * 房间照片审核未通过
     *
     * @author liujun
     * @created 2016年4月7日 下午5:13:25
     * @updated 2016年11月3日
     *
     * @param paramJson
     * @return
     */
    public String unApproveRoomPic(String paramJson);

    /**
     *
     * 查询房源操作历史记录
     *
     * @author liujun
     * @created 2016年4月7日 下午11:55:05
     *
     * @param houseBaseFid
     * @return
     */
    public String searchHouseOperateLogList(String paramJson);
    
    /**
     * 
     * 条件查询楼盘信息列表
     *
     * @author bushujie
     * @created 2016年4月12日 下午3:55:31
     *
     * @param paramJson
     * @return
     */
    public String searchHousePhyMsgList(String paramJson);
    
    /**
     * 
     * 更换房源对应楼盘信息
     *
     * @author bushujie
     * @created 2016年4月12日 下午3:57:42
     *
     * @param newPhyFid
     * @param oldPhyFid
     * @return
     */
    public String updateHouseBasePhyFid(String newPhyFid,String oldPhyFid);
    
    /**
     * 
     * 更新扩展信息表
     *
     * @author jixd
     * @created 2016年9月20日 下午10:09:34
     *
     * @param houseFid
     * @return
     */
    public String updateHouseBaseAndExt(String paramJson);
    
    /**
     * 
     * 更新楼盘信息表
     *
     * @author bushujie
     * @created 2016年4月12日 下午4:04:31
     *
     * @param paramJson
     * @return
     */
    public String updateHousePhyMsg(String paramJson);

	/**
	 * 根据房源基础信息逻辑id与枚举code查询房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月13日 上午11:05:46
	 *
	 * @param houseBaseFid
	 * @param enumCode
	 * @return
	 */
    @Deprecated
	public String searchHouseConfListByFidAndCode(String houseBaseFid, String enumCode);
	
	/**
	 * 
	 * 房源图片审核列表
	 *
	 * @author bushujie
	 * @created 2016年4月14日 上午11:43:28
	 *
	 * @param paramJson
	 * @return
	 */
	public String housePicAuditList(String paramJson);
	
	
	/**
	 * 
	 * 房源图片审核列表(troy专用！！！！！)
	 *
	 * @author zl
	 * @created 2017年7月10日 下午2:26:48
	 *
	 * @param paramJson
	 * @return
	 */
	public String housePicAuditListForTroy(String paramJson);	
	
	
	/**
	 * 查询照片修改后审核未通过上架房源列表
	 *
	 * @author liujun
	 * @created 2016年4月14日 上午12:36:42
	 *
	 * @return paramJson
	 */
	public String searchPicUnapproveedHouseList(String paramJson);
	
	/**
	 * 
	 * 房源照片修改审核通过
	 *
	 * @author liujun
	 * @created 2016年4月15日 上午9:56:56
	 *
	 * @param houseFid
	 * @param rentWay
	 * @param operaterFid
	 * @param remark
	 * @return
	 */
	public String approveModifiedPic(String houseFid, String rentWay, String operaterFid, String remark);
	
	/**
	 * 
	 * 查询房源下某个房间或类型数量
	 *
	 * @author bushujie
	 * @created 2016年4月16日 上午11:54:55
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHousePicCountByType(String paramJson);
	
	/**
	 * 
	 * 批量修改房源房间权重分值
	 *
	 * @author liujun
	 * @created 2016年4月25日 下午7:02:55
	 *
	 * @param paramJson
	 * @return
	 */
	public String batchEditHouseWeight(String paramJson);
	
	/**
	 * 
	 *录入房源信息
	 *
	 * @author bushujie
	 * @created 2016年5月4日 下午11:12:43
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseInput(String paramJson);
	
	/**
	 * 
	 * 更新房源基本信息
	 *
	 * @author bushujie
	 * @created 2016年5月11日 下午2:00:28
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseBaseMsg(String paramJson);
	
	/**
	 * 
	 * 重新发布房源
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param houseBaseFid
	 * @param operaterFid
	 * @param remark
	 * @return
	 */
	public String reIssueHouse(String houseBaseFid, String operaterFid,
			String remark);

	/**
	 * 重新发布房间
	 *
	 * @author liujun
	 * @created 2016年5月25日
	 *
	 * @param houseRoomFid
	 * @param operaterFid
	 * @param remark
	 * @return
	 */
	public String reIssueRoom(String houseRoomFid, String operaterFid,
			String remark);

	/**
	 * 
	 * 更新房源基本信息
	 *
	 * @author bushujie
	 * @created 2016年6月22日 下午3:25:02
	 *
	 * @param paramJson
	 * @return
	 */
	String upHouseMsg(String paramJson);
	
	/**
	 * 
	 * 查询整租或者合租未审核图片列表
	 *
	 * @author bushujie
	 * @created 2016年6月23日 下午5:54:17
	 *
	 * @param paramJson
	 * @return
	 */
	String findNoAuditHousePicList(String paramJson);
	
	/**
	 * 
	 * 删除未审核图片
	 *
	 * @author bushujie
	 * @created 2016年6月23日 下午8:57:28
	 *
	 * @param fid
	 * @param serverUuid
	 * @return
	 */
	String delNoAuditHousePic(String fid);
	
	/**
	 * 
	 * 审核房源图片
	 *
	 * @author bushujie
	 * @created 2016年6月24日 下午3:08:26
	 *
	 * @param paramJson
	 * @return
	 */
	String auditHousePic(String paramJson);
	
	/**
	 * 
	 * 查询房源
	 *
	 * @author liyingjie
	 * @created 2016年6月29日
	 *
	 * @param uid
	 * @return
	 */
	String findHouseListByPhy(String guardAreaLogRe);
	
	/**
	 * 根据房源fidList批量查询房源信息
	 * @author lishaochuan
	 * @create 2016年8月4日下午5:40:52
	 * @param paramJson
	 * @return
	 */
	public String getHouseCityVoByFids(String paramJson);

	/**
	 * 房源品质审核通过
	 * 
	 *
	 * @author liujun
	 * @created 2016年9月9日
	 *
	 * @param paramJson
	 * @return
	 */
	@Deprecated
	public String approveHouseQuality(String paramJson);

	/**
	 * 房间品质审核通过
	 *
	 * @author liujun
	 * @created 2016年9月9日
	 *
	 * @param paramJson
	 * @return
	 */
	@Deprecated
	public String approveRoomQuality(String paramJson);

	/**
	 * 房源品质审核未通过
	 *
	 * @author liujun
	 * @created 2016年9月9日
	 *
	 * @param paramJson
	 * @return
	 */
	@Deprecated
	public String unApproveHouseQuality(String paramJson);

	/**
	 * 房间品质审核未通过
	 *
	 * @author liujun
	 * @created 2016年9月9日
	 *
	 * @param paramJson
	 * @return
	 */
	@Deprecated
	public String unApproveRoomQuality(String paramJson);
	
	/**
	 * 
	 *  根据权限类型 查询房源fid集合
	 *  说明：如果返回集合 为null 说明当前用户 无任何房源的权限
	 *  
	 *  1. 有区域权限2，但无区域，则无权限
	 *
	 * @author yd
	 * @created 2016年10月31日 上午11:52:58
	 *
	 * @param authMenu
	 * @return
	 */
	public String findHouseFidByAuth(String authMenuJson);
	
	/**
	 * 
	 *根据 房源fid  以及新房东uid  切换房东
	 *
	 * @author yd
	 * @created 2016年12月6日 上午10:00:37
	 *
	 * @param paramJson
	 * @return
	 */
	public String exchanageLanlordHouse(String paramJson);
	
	/**
	 * 
	 * 发送mq消息
	 *
	 * @author loushuai
	 * @created 2017年5月17日 下午1:36:04
	 *
	 * @return
	 */
	public String sendRabbitMq(String houseFid, Integer rentWay);
	
	/**
	 * 
	 * 根据房源名称 ， 国家， 城市三个维度获取整租房源houseFidList
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午4:35:35
	 *
	 * @param paramJson
	 * @return
	 */
	public String getHoseFidListForIMFollow(String paramJson); 	
	
	/**
	 * 
	 * 根据房间名称 ， 国家， 城市三个维度获取分租roomFidList
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午4:35:35
	 *
	 * @param paramJson
	 * @return
	 */
	public String getRoomFidListForIMFollow(String paramJson);

	/**
	 * 根据housefid获取房源名称 城市
	 *
	 * @author loushuai
	 * @created 2017年5月26日 下午4:23:53
	 *
	 * @param houseFid
	 * @return
	 */
	public String getHouseInfoForImFollow(String paramJson);

	/**
	 * 根据roomfid获取房源名称 城市
	 *
	 * @author loushuai
	 * @created 2017年5月26日 下午4:45:47
	 *
	 * @param object2Json
	 * @return
	 */
	public String getRoomInfoForImFollow(String object2Json);

	/**
	 * 获取房源或房间名称集合
	 *
	 * @author lusp
	 * @created 2017年6月30日 下午4:52:09
	 * @param paramJson
	 * @return
	 */
	public String getHouseOrRoomNameList(String paramJson);

	/**
	 * @description: 根据条件查询（houseFid 、roomFid、rentWay等）
	 * @author: lusp
	 * @date: 2017/7/31 14:13
	 * @params: paramJson
	 * @return:
	 */
	public String getHouseUpdateFieldAuditNewlogByCondition(String paramJson);

	/**
	 * @description: 根据id 更新审核记录表数据
	 * @author: lusp
	 * @date: 2017/8/3 14:37
	 * @params: paramJson
	 * @return:
	 */
	public String updateHouseUpdateFieldAuditNewlogById(String paramJson);

	/**
	 * @description: 审核驳回单张照片
	 * @author: lusp
	 * @date: 2017/8/4 14:26
	 * @params: paramJson
	 * @return:
	 */
	public String updateHousePicMsg(String paramJson);

	/**
	 * @description: 审核通过上架房源的待审核信息
	 * @author: lusp
	 * @date: 2017/8/4 17:46
	 * @params: paramJson
	 * @return:
	 */
	public String approveGroundingHouseInfo(String paramJson);
	
	
	/**
	 * 
	 * 房源信息修改
	 *
	 * @author yd
	 * @created 2017年11月2日 下午5:17:16
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchUpateHouseMsgList(String paramJson);


}
