package com.ziroom.zrp.service.trading.api;

/**
 * <p>合同服务接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 19:53
 * @since 1.0
 */
public interface RentContractService {

    /**
	 * 查询合同实体对象
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
     */
    String findContractBaseByContractId(String contractId);
    /**
	 * 根据ID查询签约的合同基本信息
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param	paramJson 包括contractId和deCode标识合同详情还是签约中
	 * @return
     */
    String findContractByContractId(String paramJson);
    /**
	 * 判断合同是否可签约
	 * @author xiangb
	 * @created 2017年9月13日
	 * @param
	 * @return
     */
    String validContract(String contractId);

	/**
	 * 更新付款方式
	 * @author jixd
	 * @created 2017年09月13日 17:54:40
	 * @param
	 * @return
	 */
    String updatePayCodeByContractId(String contractId,String payCode);

	/**
	 * 根据签约邀请保存保同信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
    String saveContractBySignInvite(String signInviteInfo);

    /**
     * 保存合同信息 - 后台操作第一步
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    String saveContractByFirst(String paramJson);


    /**
     * 查询签约主体信息
     * <p></p>
     * @author xiangb
     * @created 2017年9月21日
     * @param contractId
     * @return
     */
    String findCheckinPerson(String contractId);
    /**
     * 查询签约房间信息
     * <p></p>
     * @author xiangb
     * @created 2017年9月21日
     * @param contractId
     * @return
     */
    String findRentRoomInfo(String contractId);
    /**
     * <p>通过合同ID查询签约人信息</p>
     * @author xiangb
     * @created 2017年9月28日
     * @param
     * @return
     */
    String findCheckinPersonEntityByContractId(String contractId);

	/**
	 * <p>根据uid查询最新的一个合同</p>
	 * @author xiangb
	 * @created 2017年10月2日
	 * @param
	 * @return
	 */
	String findLatelyContractByUid(String uid);
	/**
	 * <p>根据uid查询合同列表</p>
	 * @author xiangb
	 * @created 2017年10月2日
	 * @param
	 * @return
	 */
	String findContractListByUid(String uid);

	/**
	 * @description: 提交合同时,修改合同信息
	 * @author: lusp
	 * @date: 2017/10/9 15:23
	 * @params: contractId
	 * @return: String
	 */
	String updateContractInfoForSubmit(String contractId);

	/**
	 * @description: 此方法为app提交合同失败回滚使用,将合同状态由待支付更新为未签约,,同时删除合同号和合同的价格、提交时间等等数据
	 * @author: lusp
	 * @date: 2017/10/11 15:23
	 * @params: contractId
	 * @return: String
	 */
	String rollBackContractInfoForAppSubmit(String contractId);

	/**
	 * 查询合同信息头
	 * @author jixd
	 * @created 2017年10月09日 10:42:36
	 * @param
	 * @return
	 */
	String findContractHeaderInfo(String contractId);

	/**
	 * 根据父合同id查询合同房间信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findRentRoomsInfoByParentId(String parentId);

	/**
	 * 根据父合同id查询合同房间信息
	 * @author cuiyuhui
	 * @created
	 * @param paramJson RentRoomDto.class json字符串
	 * @return
	 */
	String findRentRoomsInfoFromSurrender(String paramJson);

	/**
	 * 根据父合同id查询一个合同信息
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findOneRentContractByParentId(String surParentRentId);

	/**
	 * 关闭合同接口
	 * @param contractId 合同id
	 * @param closeType 合同不关闭类型
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月13日
	 */
	String closeContract(String contractId,Integer closeType);
	/**
	 * <p>根据出房合同号查询合同实体信息</p>
	 * @author xiangb
	 * @created 2017年10月17日
	 * @param
	 * @return
	 */
	String findContractByCode(String contractCode);
	/**
	 * <p>更新合同状态</p>
	 * @author xiangb
	 * @created 2017年10月17日
	 * @param contractId合同ID sourceStatus原状态 targetStatus 目标状态
	 * @return
	 */
	String updateContractStatus(String contractId,String sourceStatus,String targetStatus);

	/**
	 * 作废合同接口
	 * @param contractId 合同id
	 * @param closeType 关闭类型
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月13日
	 */
	String invalidContract(String contractId,Integer closeType);

	/**
	 * <p>获取所有待支付账单及生活账单个数和房租账单个数</p>
	 * @author xiangb
	 * @created 2017年10月19日
	 * @param contractCode
	 * @return
	 */
	String findWaitforPaymentList(String contractCode);

	/**
	 * 获取合同支持的出租方式(年租、月租、日租);
	 * 最终也是为了房间支持的出租方式
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findSupportConTypeByParentId(String parentId);

	/**
	 * 签业签过程中中，合同信息查询
	 * @author cuiyuhui
	 * @created
	 * @param paramJson
	 * @return
	 */
    String findWqyEpsContractPageInfo(String paramJson);

	/**
	 * @description: 此方法为zrams提交合同失败回滚使用,将合同状态由待支付更新为未签约,修改不可修改标志,同时删除合同号和合同的价格、提交时间等等数据
	 * @author: lusp
	 * @date: 2017/10/25 下午 18:06
	 * @params: paramJson
	 * @return: String
	 */
	String rollBackContractInfoForZramsSubmit(String paramJson);

	/**
	 * 更新合同基本信息
	 * @author jixd
	 * @created 2017年10月27日 10:26:35
	 * @param
	 * @return
	 */
	String updateBaseContractById(String entityStr);

	/**
	 * 判断是否可以续约(个人和企业通用)
	 *
	 * @param conRentCode 合同号
	 * @param billType 账单类型
	 * @return
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	String isExistNotFinishedBill(String conRentCode,String billType);

	/**
	 * 查询存在的有效的签约邀请生成的合同
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findWqyContractInviteByRoomIds(String paramJson);
	/**
	 * <p>批量保存解约协议及更新合同中的解约申请日期</p>
	 * @author xiangb
	 * @created 2017年11月2日
	 */
	String saveSurrenderAndUpdateRentContract(String param);
	/**
	 * <p>查询失效合同列表</p>
	 * @author xiangb
	 * @created 2017年11月5日
	 */
	String findInvalidContractList(String uid);
	/**
	 *
	 * 根据父合同id查询 RentContractEntity 列表
	 * @author cuiyuhui
	 * @created
	 * @param
	 * @return
	 */
	String findRentContractListByParentId(String surParentRentId);

	/**
	 * 批量查询父合同号
	 * @param paramJson 子合同号列表
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	String getBatchParentContractCode(String paramJson);

	/**
	  * @description: 根据父合同号批量查询合同号
	  * @author: lusp
	  * @date: 2017/11/25 下午 17:40
	  * @params: parentCode
	  * @return: String
	  */
	String getBatchContractCodeByParentCode(String parentCode);

	/**
	 * @description: 根据父合同号批量查询合同号（除了已退租和已关闭的）
	 * @author: lusp
	 * @date: 2017/12/26 下午 17:40
	 * @params: parentCode
	 * @return: String
	 */
	String getCodesByParentCodeOnCondition(String parentCode);

	/**
	 * 获取
	 * @author jixd
	 * @created 2017年11月23日 14:54:46
	 * @param
	 * @return
	 */
	String getRenewPreContract(String currentContractId);

	/**
	 * 获取合同参与的活动
	 * @param contractId 合同标识
	 * @return
	 * @author cuigh6
	 * @Date 2017年11月
	 */
	String getContractActivityList(String contractId);

	/**
	 * 查询列表
	 * @author jixd
	 * @created 2017年11月27日 19:21:18
	 * @param
	 * @return
	 */
	String listContractByPage(String paramJson);

	/**
	  * @description: 客户签字，修改合同状态为已签约，如果为续约合同，修改签合同状态为已续约，修改房间状态为已出租
	  * @author: lusp
	  * @date: 2017/12/5 下午 16:03
	  * @params: contractId
	  * @return: String
	  */
	String customerSignatureForMs(String contractId);

	/**
	  * @description: 根据合同id查询合同信息以及物业交割状态
	  * @author: lusp
	  * @date: 2017/12/20 下午 17:24
	  * @params: contractId
	  * @return: String
	  */
	String findContractAndDetailsByContractId(String contractId);

    /**
     * 获取同步到财务数据主线的合同id（历史数据为合同子表的id）
     *
     * @param contractId
     * @return
     */
    String finOldDataContractId(String contractId);
	/**
	 * 查询原合同是否存在续约合同
	 * @param contractCode 合同号
	 * @return
	 * @author cuigh6
	 * @Date 2017年12月22日
	 */
	String findRenewContractByPreRentCode(String contractCode);

	/**
	  * @description: 根据父合同号查询合同列表
	  * @author: lusp
	  * @date: 2018/1/11 下午 18:09
	  * @params: surParentRentCode
	  * @return: String
	  */
	String findContractListByParentCode(String surParentRentCode);
	/**
	 * <p>根据合同号查询有效合同</p>
	 * @author xiangb
	 * @created 2018年1月15日
	 * @param
	 * @return
	 */
	String findValidContractByRentCode(String contractCode);

	/**
	 *  查询当前房间的最近的合同
	 * @author yd
	 * @created
	 * @param
	 * @return
	 */
	String findCurrentContract(String paramsJson);

	/**
	 * 续约延长房间智能锁密码
	 * @param paramJson
	 * @return
	 * @author cuigh6
	 * @Date 2018年1月26日
	 */
	String renewAddSmartLock(String paramJson);

	/**
	 * 查询房间当前有效的合同列表
	 * @param roomId 房间标识
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月8日
	 */
	String getRoomValidContractList(String roomId);
}
