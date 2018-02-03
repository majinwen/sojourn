package com.ziroom.minsu.services.house.api.inner;

import java.util.List;



/**
 * 
 * <p>房东端-房源发布操作接口</p>
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
public interface HouseIssueService {

	/**
	 * 为灵活定价，长期租住优惠插入记录
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 17:51
	 */
	public String saveHouseConfList(String paramJson);

	/**
	 * 为折扣率更新和插入执行的方法
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/6 15:23
	 */
	public String saveOrUpHouseConf(String paramJson);

	/**
	 * 更新灵活定价以及长期租住优惠
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 17:52
	 */
	public String updateHouseConfList(String paramJson);

	/**
	 * 
	 * 根据房源逻辑id查询房源基础信息
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午1:01:39
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String searchHouseBaseMsgByFid(String houseBaseFid);

	/**
	 * 
	 * 新增或更新房源基础信息
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:04:46
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBaseMsgEntity entity)
	 * @return
	 */
	public String saveHouseBaseMsg(String paramJson);

	/**
	 * 
	 * 更新房源基础信息
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:04:46
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBaseMsgEntity entity)
	 * @return
	 */
	public String updateHouseBaseMsg(String paramJson);

	/**
	 * 更新房间信息
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/6 16:14
	 */
	public String updateHouseRoomMsg(String paramJson);
	/**
	 * 
	 * 根据房源逻辑id查询房源物理信息
	 *
	 * @author liujun
	 * @created 2016年4月29日 下午6:59:12
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String searchHousePhyMsgByHouseBaseFid(String houseBaseFid);

	/**
	 * 
	 * 新增或更新房源物理信息表
	 *
	 * @author liujun
	 * @created 2016年4月29日 下午8:49:35
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HousePhyMsgEntity entity)
	 * @return
	 */
	public String mergeHousePhyMsg(String paramJson);

	/**
	 * 
	 * 通过房源基础信息逻辑id查询房源基础信息扩展&通过房源基础信息逻辑id和房源配置code集合查询房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午5:17:37
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBaseExtVo vo)
	 * @return
	 */
	public String searchHouseBaseExtAndHouseConfList(String paramJson);

	/**
	 * 
	 * 更新房源基础信息与(新增|更新)房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午3:12:20
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBaseExtVo vo)
	 * @return
	 */
	public String mergeHouseBaseExtAndHouseConfList(String paramJson);

	/**
	 * 
	 * 根据房源基本信息逻辑id查询房间集合
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午10:30:20
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String searchRoomListByHouseBaseFid(String houseBaseFid);

	/**
	 * 
	 * 更新房源基础信息及房源房间信息集合
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午11:04:29
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseRoomListDto dto)
	 * @return
	 */
	public String updateHouseBaseAndRoomList(String paramJson);

	/**
	 * 
	 * 根据房源房间逻辑id查询详情信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午8:27:49
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public String searchHouseRoomMsgByFid(String houseRoomFid);

	/**
	 * 
	 * 根据房源房间逻辑id逻辑删除房间信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午6:04:53
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public String deleteHouseRoomMsgByFid(String houseRoomFid);


	/**
	 * 删除房间并做逻辑的处理
	 * @author afi
	 * @param houseRoomFid
	 * @return
	 */
	String deleteCheckHouseRoomMsgByFid(String houseRoomFid);

	/**
	 * 
	 * (新增|更新)房源房间信息
	 *
	 * @author liujun
	 * @created 2016年4月1日 下午4:43:05
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseRoomMsgDto dto)
	 * @return
	 */
	public String mergeHouseRoomMsg(String paramJson);

	/**
	 * 校验当前room是否存在
	 * 如果不存在直接插入
	 * @author afi
	 * @param paramJson
	 * @return
	 */
	String mergeCheckHouseRoomMsg(String paramJson,String uid);


	/**
	 * 
	 * 根据房源房间逻辑id查询床位集合
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午10:03:49
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public String searchBedListByRoomFid(String houseRoomFid);


	/**
	 * 通过roomFid 获取当前的床数量
	 * @param houseRoomFid
	 * @return
	 */
	String countBedByRoomFid(String houseRoomFid);

	/**
	 * 
	 * 根据房源床位逻辑id查询详情信息
	 *
	 * @author liujun
	 * @created 2016年4月2日 下午9:16:11
	 *
	 * @param houseBedFid
	 * @return
	 */
	public String searchHouseBedMsgByFid(String houseBedFid);

	/**
	 * 
	 * 根据房源床位逻辑id逻辑删除床位信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午6:04:53
	 *
	 * @param houseRoomFid
	 * @return
	 */
	public String deleteHouseBedMsgByFid(String houseBedFid);

	/**
	 * 
	 * (新增|更新)房源床位信息
	 *
	 * @author liujun
	 * @created 2016年4月1日 下午4:43:05
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBedMsgEntity entity)
	 * @return
	 */
	public String mergeHouseBedMsg(String paramJson);

	/**
	 * 
	 * 查询房源基础信息与房源描述信息
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午10:25:43
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String searchHouseBaseMsgAndHouseDesc(String houseBaseFid);

	/**
	 * 
	 * 更新房源基础信息与房源描述信息
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午9:39:03
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBaseMsgDto dto)
	 * @return
	 */
	public String updateHouseBaseMsgAndHouseDesc(String paramJson);

	/**
	 * 
	 * 更新房源基础信息及合并房源描述信息
	 *
	 * @author liujun
	 * @created 2016年5月4日
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseBaseMsgAndMergeHouseDesc(String paramJson);

	/**
	 * 
	 * 批量保存图片集合
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午5:07:43
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(List<HousePicMsgEntity> list)
	 * @return
	 */
	public String saveHousePicMsgList(String paramJson);

	/**
	 * 
	 * 查询房源图片集合
	 *
	 * @author liujun
	 * @created 2016年4月30日 下午3:16:53
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HousePicDto dto)
	 * @return
	 */
	public String searchHousePicMsgList(String paramJson);

	/**
	 * 
	 * 根据房源图片逻辑id查询图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午8:30:40
	 *
	 * @param housePicFid
	 * @return
	 */
	public String searchHousePicMsgByFid(String housePicFid);

	/**
	 * 
	 * 根据房源图片逻辑id逻辑删除图片信息
	 *
	 * @author liujun
	 * @created 2016年4月5日 下午6:04:53
	 *
	 * @param housePicFid
	 * @return
	 */
	public String deleteHousePicMsgByFid(String paramJson);
	
	/**
	 * 
	 * 根据图片id的集合逻辑删除所有图片信息
	 *
	 * @author lunan
	 * @created 2016年10月28日 下午7:41:10
	 *
	 * @param paramJson
	 * @return
	 */
	public String delAllHousePicMsgByFid(String paramJson);

	/**
	 * 
	 * 根据房源基础信息逻辑id查询基础信息与基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午4:32:35
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String searchHouseBaseAndExtByFid(String houseBaseFid);

	/**
	 * 
	 * 根据房源基础信息逻辑id更新基础信息与基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月9日 下午8:32:23
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HouseBaseExtDto dto)
	 * @return
	 */
	public String updateHouseBaseAndExt(String paramJson);

	/**
	 * 
	 * 发布房源(保存默认配置信息)
	 *
	 * @author liujun
	 * @created 2016年5月4日
	 *
	 * @param paramJson
	 * @return
	 */
	public String issueHouse(String paramJson);

	/**
	 * 
	 * 根据房源基础信息逻辑id查询房源描述及房源基础信息扩展
	 *
	 * @author liujun
	 * @created 2016年4月3日 上午11:10:54
	 *
	 * @param houseBaseFid 房源基础信息逻辑id
	 * @return
	 */
	public String searchHouseDescAndBaseExt(String houseBaseFid);

	/**
	 * 
	 * 更新房源物理信息,房源基础信息扩展与房源描述
	 *
	 * @author liujun
	 * @created 2016年4月1日 下午4:43:05
	 *
	 * @param paramJson JsonEntityTransform.Object2Json(HousePhyMsgDto dto)
	 * @return
	 */
	public String updateHouseDescAndBaseExt(String paramJson);

	/**
	 * 
	 * 更新默认房源信息
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午9:52:01
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseDefaultPic(String paramJson);

	/**
	 * 
	 * 是否默认图片
	 *
	 * @author bushujie
	 * @created 2016年5月20日 上午3:57:38
	 *
	 * @param picFid
	 * @return
	 */
	public String isDefaultPic(String picFid);

	/**
	 * 
	 * 查询录入房源详情信息
	 *
	 * @author bushujie
	 * @created 2016年5月26日 上午12:05:40
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String findHouseInputDetail(String houseBaseFid);

	/**
	 * 
	 * 查询房源相关配套设施和服务列表
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午3:40:52
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String findHouseFacilityAndService(String houseBaseFid);

	/**
	 * 
	 * 录入房源更新房源信息
	 *
	 * @author bushujie
	 * @created 2016年5月26日 下午8:10:55
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseInputDetail(String paramJson);
	
	/**
	 * 
	 * 录入房源，保存用户输入信息
	 *
	 * @author lunan
	 * @created 2016年10月18日 下午2:26:22
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseInfo(String paramJson);

	/**
	 * 新增或更新房源基础信息、房源物理信息、房源扩展信息
	 * @author lishaochuan
	 * @create 2016年5月26日下午2:50:22
	 * @param paramJson
	 * @return
	 */
	public String mergeHouseBaseAndPhyAndExt(String paramJson);

	/**
	 * 
	 * 更新房源配套设施和服务
	 *
	 * @author bushujie
	 * @created 2016年5月28日 下午4:38:25
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseConf(String paramJson);

	/**
	 * 
	 * 查询房源或房间默认图片
	 *
	 * @author bushujie
	 * @created 2016年6月2日 上午3:35:58
	 *
	 * @param fid
	 * @param rentWay
	 * @return
	 */
	public String findDefaultPic(String fid,Integer rentWay);

	/**
	 * 
	 * 删除房源或者房间，根据rentWay
	 *
	 * @author jixd
	 * @created 2016年6月13日 下午3:54:03
	 *
	 * @param paramJson
	 * @return
	 */
	public String deleteHouseOrRoomByFid(String paramJson);

	/**
	 * 
	 * 取消房源或者房间发布，根据rentWay
	 *
	 * @author jixd
	 * @created 2016年6月14日 下午2:25:16
	 *
	 * @param paramJson
	 * @return
	 */
	public String cancleHouseOrRoomByFid(String paramJson);

	/**
	 * 
	 * 列表页进入详情页发布房源
	 *
	 * @author jixd
	 * @created 2016年6月15日 上午11:32:41
	 *
	 * @param paramJson
	 * @return
	 */
	public String issueHouseInDetail(String paramJson);

	/**
	 * 
	 * 查询房源操作日志
	 *
	 * @author jixd
	 * @created 2016年6月15日 下午9:54:49
	 *
	 * @param paramJson
	 * @return
	 */
	public String findOperateLogList(String paramJson);

	/**
	 * 根据房源fid或者房间fid查询配置项集合
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/3 11:18
	 */
	public String findGapAndFlexPrice(String paramJson);

	/**
	 * 
	 * 是否默认图片(isDefaultPic的新接口)
	 *
	 * @author bushujie
	 * @created 2016年5月20日 上午3:57:38
	 *
	 * @param picFid
	 * @return
	 */
	public String newIsDefaultPic(String picFid,String fid,Integer rentWay);

	/**
	 * 
	 * 批量保存图片集合
	 *
	 * @author busj
	 * @created 2016年4月5日 下午5:07:43
	 *
	 * @param paramJson
	 * @return
	 */
	public String newSaveHousePicMsgList(String paramJson);



	/**
	 * 新增或更新房源房源扩展信息、房源描述信息、房源配置信息
	 * @author zl
	 * @created 2016年8月15日 18:43:07
	 * @param paramJson
	 * @return
	 */
	public String mergeHouseExtAndDesc(String paramJson);

	/**
	 * 点击户型选择功能
	 * 
	 * 更新房源户型信息，以及删除多余的房间（删除房间同时删除房间信息）
	 * 整租房间可全部删除
	 * 分租必须包含一个房间
	 *   说明：
	 * 1.初始化，房源不存在，直接提示不让添加房间
	 * 2.房源存在，带出户型，户型情况：A. 0室   B. 大于 0 室
	 * 3. A.0室处理   房间默认没有  当选择户型，保存入库，  并带出相应的房间展示位置（页面dom操作）
	 * 4. B.大于0室，选择户型，保存入库
	 *       效果： 选择X室， 下面展示相应X个房间（当前已存在且未删除房间数量Y<=X， 若Y<X,dom展示Z = X-Y个房间出来）
	 *     设 数据库当前已保存且未删除房间数量为 M(X>=M>=0)
	 *     4.1 选择X室，数据库查询M
	 *         若M<=X,展示M个房间，展示X-M个dom（房间数量不够，用dom元素展示，不保存入库）的房间数量
	 *         若M>X, 按照房间创建时间排序，删除M-X个房间，查询返回聊表FID,取前M-X个fid逻辑删除
	 * 
	 *
	 *
	 * @author yd
	 * @created 2016年8月19日 下午3:50:52
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseAndDelRoom(String paramJson);

	/**
	 * 新增或更新房间及床铺信息
	 *
	 * @author liujun
	 * @created 2016年8月22日
	 *
	 * @param paramJson
	 * @return
	 */
	public String mergeRoomAndBedList(String paramJson);

	/**
	 * 房源状态情况 {@link HouseIssueService#houseInfoSituation(String)}
	 *    1. 房源信息状态计算    （0=未完成  1=完成）
	 *      计算公式：
	 *         整租：要校验发布房源整租第4步是否都已填写，全填写，就认为完成，包括字段：房源名称，房源面积，出租期限，入住人数限制，价格，
	 *         配套设施，房源描述，周边情况。   由于正常情况下，这几个字段都必填后，才能到下一步，故校验任意字段不为null即可，
	 *         这边选择校验，房源名称，房源面积，出租期限,房源描述，周边情况
	 *        
	 *         分租：要校验发布房源分租第4步是否都已填写，全填写，就认为完成，包括字段： 与房东同住，出租期限，配套设施，房源描述，周边情况
	 *         这块需要校验：出租期限,房源描述，周边情况
	 *    2. 房间信息状态计算    （0=未完成  1=完成） 校验当前房源下 所有房间是否都有床位，有就完成，无就未完成
	 *      计算公式：
	 *         整租，分租都校验是否当前房间有床位，即可，有就是完成，无就是未完成
	 *         
	 *    3. 可选信息状态计算   （0=未完成  1=完成）  分：交易信息和入住信息，都完成就算完成，否则未完成
	 *      计算公式：
	 *        交易信息： 校验交易信息页面的所有字段是否都完成，这里都会有默认值，所以不好校验TODO
	 *
	 * @author yd
	 * @created 2016年8月22日 上午11:31:44
	 *
	 * @param houseFid
	 * @return
	 */
	@Deprecated
	public String houseStatusSituation(String houseFid);
	
	/**
	 * 
	 * 整租保存床位信息
	 *
	 * @author yd
	 * @created 2016年8月23日 下午9:46:08
	 *
	 * @param roomBedZDto
	 * @return
	 */
	public String saveRoomBedZ(String roomBedZDto);
	
	/**
	 * 
	 * 根据房源fid或房间fid查询床位数量
	 *
	 * @author yd
	 * @created 2016年8月24日 下午2:03:55
	 *
	 * @param paramMap
	 * @return
	 */
	public String releaseHouse(String fid);
	
	/**
	 * 
	 * 说明：这里是 M站发布整租房源 第5步，选择户型，添加完房间后，点击下一步的功能
	 * 根据房源fid或房间fid查询床位数量
	 * 
	 *
	 * @author yd
	 * @created 2016年8月24日 下午2:03:55
	 *
	 * @param paramMap
	 * @return
	 */
	public String  countBedNumByHouseFid(String fid,String rentWay);
	
	/**
	 * 房源状态情况
	 *    1. 房源信息状态计算    （0=未完成  1=完成）
	 *      计算公式：
	 *         整租：要校验发布房源整租第4步是否都已填写，全填写，就认为完成，包括字段：房源名称，房源面积，出租期限，入住人数限制，价格，
	 *         配套设施，房源描述，周边情况。   由于正常情况下，这几个字段都必填后，才能到下一步，故校验任意字段不为null即可，
	 *         这边选择校验，房源名称，房源面积，出租期限,房源描述，周边情况
	 *        
	 *         分租：要校验发布房源分租第4步是否都已填写，全填写，就认为完成，包括字段： 与房东同住，出租期限，配套设施，房源描述，周边情况
	 *         这块需要校验：出租期限,房源描述，周边情况
	 *    2. 房间信息状态计算    （0=未完成  1=完成） 
	 *      计算公式：
	 *         整租，校验当前房源下 所有房间是否都有床位，有就完成，无就未完成
	 *         分租，校验当前房间是否有床位，即可，有就是完成，无就是未完成
	 *         
	 *    3. 可选信息状态计算   （0=未完成  1=完成）  分：交易信息和入住信息，都完成就算完成，否则未完成
	 *      计算公式：
	 *        交易信息： 校验交易信息页面的所有字段是否都完成，这里都会有默认值，所以不好校验TODO
	 *
	 * @author liujun
	 * @created 2016年9月21日
	 *
	 * @param paramJson
	 * @return
	 */
	public String houseInfoSituation(String paramJson);
	
	/**
	 * 
	 * 获取当前房源的押金
	 * 
	 * 1. 固定收取，查询房源配置项，返回，如果没有，返回0元 （分租 整租处理相同）
	 * 2. 按天收取  
	 *    整租 ：查询当前配置项目值，计算押金（按照当前房源基本价格计算）
	 *    分租 ：查询当前配置项目值，计算押金（按照当前房间基本价格计算）
	 *
	 * @author yd
	 * @created 2016年11月16日 下午3:49:12
	 *
	 * @param paramJson
	 * @return
	 */
	@Deprecated
	public String findHouseDeposit(String houseFid);
	
	/**
	 * 
	 * 查询房源或者房间固定收取的押金，如果没有，返回0
	 *
	 * @author zl
	 * @created 2017年6月26日 下午2:44:08
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseOrRoomDeposit(String paramJson);
	
	
	/**
	 * 
	 * 根据房源id查询房源图片集合
	 *
	 * @author liujun
	 * @created 2017年3月1日
	 *
	 * @param paramJson
	 * @return
	 */
	public String searchHousePicMsgListByHouseFid(String paramJson);
	
	/**
	 * 
	 * 查询房源扩展信息
	 *
	 * @author bushujie
	 * @created 2017年4月5日 下午2:44:08
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public String getHouseBaseExtByHouseBaseFid(String houseBaseFid);
	
	/**
	 * 
	 * 更新房源扩展信息
	 *
	 * @author bushujie
	 * @created 2017年4月5日 下午4:46:20
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateHouseBaseExtByHouseBaseFid(String paramJson);
    /**
	 * 更新房屋守则中的可选守则
	 *
	 * @author baiwei
	 * @created 2017年4月7日 下午1:49:26
	 *
	 * @param paramJson
	 * @return
	 */
	public String updateSelectHouseRules(String paramJson);
	
	/**
	 * 
	 * 查询审核未通过次数
	 *
	 * @author baiwei
	 * @created 2017年4月13日 下午8:00:41
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseAuditNoLogTime(String paramJson);
	
	/**
	 * 更新灵活定价以及长期租住优惠(旧的)
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/5 17:52
	 */
	public String updateHouseConfList(String paramJson,List<String> gapList);

	/**
	 * 根据roomFID获取房间扩展信息
	 *
	 * @author loushuai
	 * @created 2017年6月20日 上午10:20:59
	 *
	 * @param roomFid
	 * @return
	 */
	public String getRoomExtByRoomFid(String roomFid);

	/**
	 * @Description: 发布房源时查询封面图片相关信息
	 * @Author: lusp
	 * @Date: 2017/6/22 20:16
	 * @Params: paramJson
	 */
	public String findDefaultPicListInfo(String paramJson);
	
	/**
	 * 
	 * 保存交易信息
	 *
	 * @author zl
	 * @created 2017年6月27日 上午9:58:58
	 *
	 * @param paramJson
	 * @return
	 */
	public String saveDealPolicy(String paramJson);
	
	/**
	 * 
	 * 根据houseFid ，roomFid ，dicCode 查询房源有效配置
	 *
	 * @author zl
	 * @created 2017年6月27日 上午11:55:33
	 *
	 * @param paramJson
	 * @return
	 */
	public String findHouseConfValidList(String paramJson);

	/**
	 * 是否已经设置封面照片（发布房源时检验）
	 * @author lusp
	 * @created 2017年7月14日 上午11:55:33
	 * @param paramJson
	 * @return
	 */
	public String isSetDefaultPic(String paramJson);
	
	/**
	 * 
	 * get room by  fid
	 *
	 * @author yd
	 * @created 2017年9月22日 下午4:41:58
	 *
	 * @param roomFid
	 * @return
	 */
	public String searchRoomByRoomFid(String roomFid);

	/**
	 * 查询房间类型roomType
	 * 校验是否为共享客厅
	 * 默认返回为0,若为共享客厅则返回1
	 * 可改名为isHall
	 * @author yanb
	 * @created 2017年11月21日 16:25:59
	 * @param  * @param null
	 * @return
	 */
	public String isHallByHouseBaseFid(String houseBaseFid);

	/**
	 * 根据houseBaseFid获取共享客厅的roomfid
	 * @param houseBaseFid
	 * @return
	 */
	public String getHallRoomFidByHouseBaseFid(String houseBaseFid);

}
