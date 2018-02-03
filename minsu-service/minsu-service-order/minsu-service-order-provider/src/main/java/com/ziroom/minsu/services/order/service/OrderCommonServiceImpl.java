package com.ziroom.minsu.services.order.service;


import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.order.*;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.order.dao.*;
import com.ziroom.minsu.services.order.dto.*;
import com.ziroom.minsu.services.order.entity.*;
import com.ziroom.minsu.valenum.order.CheckedStatusEnum;
import com.ziroom.minsu.valenum.order.OrderParamEnum;
import com.ziroom.minsu.valenum.order.OrderStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.ParseException;
import java.util.*;

/**
 * <p>订单的公共的东西</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Service("order.orderCommonServiceImpl")
public class OrderCommonServiceImpl {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommonServiceImpl.class);

	@Resource(name = "order.usualContactsDao")
	private UsualContactDao usualContactsDao;

	@Resource(name = "order.orderDao")
	private OrderDao orderDao;

	@Resource(name = "order.orderEvalDao")
	private OrderEvalDao orderEvalDao;


	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;

	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;

	@Resource(name = "order.paramDao")
	private OrderParamDao orderParamDao;

	@Resource(name = "order.orderLogDao")
	private OrderLogDao orderLogDao;

	@Resource(name = "order.orderRelationDao")
	private OrderRelationDao orderRelationDao;
	
	@Resource(name = "order.houseSnapshotDao")
	private OrderHouseSnapshotDao orderHouseSnapshotDao;
	
	@Resource(name = "order.financePenaltyDao")
	private FinancePenaltyDao  financePenaltyDao;
	
	@Resource(name = "order.financePenaltyPayRelDao")
	private FinancePenaltyPayRelDao  financePenaltyPayRelDao;

	@Resource(name = "order.activityDao")
	private OrderActivityDao orderActivityDao;

	@Resource(name = "order.houseLockDao")
    private HouseLockDao houseLockDao;
	
	@Resource(name = "order.orderConfigDao")
	private OrderConfigDao orderConfigDao;

	// ============================ 房客联系人操作相关开始 =====================================//
	/**
	 * 保存用户常用联系人
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param uce
	 * @return
	 */
	public int saveUsualContact(UsualContactEntity uce){
		return usualContactsDao.insertUsualContact(uce);
	}


	/**
	 * 获取用户常用联系人列表
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param userUid
	 * @return
	 */
	public List<UsualContactEntity> findUsualContactsByUid(String userUid){
		return usualContactsDao.getUsualContactsByUid(userUid);
	}

	/**
	 * 按照fid集合查询 常用联系人列表
	 *
	 * @author yd
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param usualConRequest
	 * @return
	 */
	public PagingResult<UsualContactEntity> findUsualContactsByFid(UsualConRequest usualConRequest) {
		return usualContactsDao.findUsualContactsByFid(usualConRequest);
	}

	/**
	 * 获取当前订单的入住人
	 * @author afi
	 * @created 2016年4月1日 上午11:20:09
	 * @param orderSn
	 * @return
	 */
	public List<UsualContactEntity> findOrderContactsByOrderSn(String orderSn){
		return usualContactsDao.findOrderContactsByOrderSn(orderSn);
	}

	/**
	 * 获取当前的订单的操作记录
	 * @param orderSn
	 * @return
     */
	public List<OrderLogEntity>  getOrderLogListByOrderSn(String orderSn){
		return orderLogDao.getOrderLogListByOrderSn(orderSn);
	}


	// ============================ 房客联系人操作相关结束 =====================================//

	// ============================ 获取订单列表相关 开始 =====================================//
	/**
	 * 分页  获取订单列表 接口 房东、客户、后台系统
	 *
	 * @author liyingjie
	 * @created 2016年4月2日 
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderInfoVo> getOrderInfoListByCondiction(OrderRequest orderRequest){

		PagingResult<OrderInfoVo> pagingResult = orderDao.getOrderInfoListByCondiction(orderRequest);

		List<OrderInfoVo> listOrderHouseVos =  null;
		if(!Check.NuNObj(pagingResult)&&!Check.NuNCollection(pagingResult.getRows())){

			listOrderHouseVos = new ArrayList<OrderInfoVo>();
			//状态转换
			for (OrderInfoVo orderInfoVo : pagingResult.getRows()) {
				getOrderStatuChineseName(orderInfoVo);
				String startTimeStr = DateUtil.dateFormat(orderInfoVo.getStartTime(),"yyyy-MM-dd");
                Date endTime = null;
				if(!Check.NuNObj(orderInfoVo.getRealEndTime()) && orderInfoVo.getRealEndTime().after(orderInfoVo.getStartTime())){
					endTime = orderInfoVo.getRealEndTime();
				}else {
					endTime = orderInfoVo.getEndTime();
				}
				String endTimeStr = DateUtil.dateFormat(endTime,"yyyy-MM-dd");
				orderInfoVo.setStartTimeStr(startTimeStr);
				orderInfoVo.setEndTimeStr(endTimeStr);
				//格式化完之后计算,防止少算
				try {
					Date startdate = DateUtil.parseDate(startTimeStr, "yyyy-MM-dd");
					Date enddate = DateUtil.parseDate(endTimeStr, "yyyy-MM-dd");
					int housingDay  = DateSplitUtil.countDateSplit(startdate, enddate);
					orderInfoVo.setHousingDay(housingDay);
				} catch (ParseException e) {
                    LogUtil.error(LOGGER, "e:{}", e);
				}
				
				List<UsualContactEntity> contacts = findOrderContactsByOrderSn(orderInfoVo.getOrderSn());
				orderInfoVo.setContactsNum(1);
				if(!Check.NuNCollection(contacts)) {
                    orderInfoVo.setContactsNum(contacts.size());
				}
				listOrderHouseVos.add(orderInfoVo);
			}
			if(listOrderHouseVos.size()>0){
				pagingResult.setRows(listOrderHouseVos);
			}

		}
		return pagingResult;
	}

	/**
	 * 
	 * 得到状态的
	 *
	 * @author yd
	 * @created 2016年4月5日 上午10:20:05
	 *
	 * @param orderHouseVo
	 */
	private void getOrderStatuChineseName(OrderHouseVo orderHouseVo){

		if(!Check.NuNObj(orderHouseVo)&&!Check.NuNObj(orderHouseVo.getOrderStatus())){
			OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusByCode(orderHouseVo.getOrderStatus());
			if(orderStatusEnum!=null){
				orderHouseVo.setOrderStatuChineseName(orderStatusEnum.getStatusName());
				orderHouseVo.setOrderStatusEnum(orderStatusEnum);
			}
		}
	}


	// ============================ 获取订单列表相关 结束 =====================================//   


	/**
	 * 获取订单的参数信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public Map<String,Object> getOrderPar(String orderSn){
		Map<String,Object> orderParMap = new HashMap<>();
		List<OrderParamEntity> orderParamList = orderParamDao.findParamByOrderSn(orderSn);
		if(!Check.NuNObj(orderParamList)){
			for(OrderParamEntity par: orderParamList){
				orderParMap.put(par.getParCode(),par.getParValue());
			}
		}
		return orderParMap;
	}

	/**
	 * 通过订单号获取当前订单的联系人信息
	 * @param orderDetailVo
	 * @return
	 */
	private List<String> getOrderContactsByOrderSn(OrderDetailVo orderDetailVo){

		if(orderDetailVo == null || Check.NuNStr(orderDetailVo.getOrderSn())) return null;
		List<String> contactList = new ArrayList<>();
		List<UsualContactEntity> contacts = findOrderContactsByOrderSn(orderDetailVo.getOrderSn());
		if(!Check.NuNObj(contacts)){
			for(UsualContactEntity usualContactEntity: contacts){
				contactList.add(usualContactEntity.getConName());
			}
			orderDetailVo.setContacts(contactList);
			orderDetailVo.setListUsualContactEntities(contacts);
		}
		return contactList;
	}

	/**
	 * 更改订单状态等信息
	 * @author afi
	 * @param oldOrderStatus 订单的原始状态
	 * @param orderEntity 订单的主信息
	 * @param orderMoney 订单的金额信息
	 * @param remark 备注
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum 备注类型code
	 * @param remarkType
	 * @param opUuid
	 */
	public int updateOrderInfoAndStatus(String opUuid,int oldOrderStatus,OrderEntity orderEntity,OrderMoneyEntity orderMoney,String remark,String remarkType){
		int rst = 0;
		if(Check.NuNObj(orderEntity)){
			return rst;
		}
		if(Check.NuNStr(orderEntity.getOrderSn())){
			throw  new BusinessException("orderSn is null on updateOrderInfo");
		}
		if(OrderStatusEnum.getOrderStatusByCode(oldOrderStatus) == null){
            LogUtil.error(LOGGER,"request par is {} ,{} ,{},{}",oldOrderStatus,JsonEntityTransform.Object2Json(orderEntity),remark,remarkType);
			throw  new BusinessException("oldOrderStatus is error on updateOrderInfo");
		}else {
			orderEntity.setOldStatus(oldOrderStatus);
		}
		//直接保存订单信息
		rst = orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
		if (rst == 0){
			LogUtil.error(LOGGER,"更新订单状态失败 历史状态：{} ,{} ,{},{}",oldOrderStatus,JsonEntityTransform.Object2Json(orderEntity),remark,remarkType);
			throw  new BusinessException("update nothing 请确定当前的订单状态");
		}
		//保存订单的金额
		if(!Check.NuNObj(orderMoney)){
			//校验是否有金额的变动
			orderMoneyDao.updateOrderMoney(orderMoney);
		}
		//记录订单的修改记录，并保存参数信息
		this.saveOrderLog(opUuid,oldOrderStatus,orderEntity,remark,remarkType,opUuid);
		return rst;
	}


	/**
	 * 更改订单基础信息和状态等信息 此处 日志备注和 参数配置的value值相同
	 * @author afi
	 * @param oldOrderStatus 订单的原始状态
	 * @param orderEntity 订单的主信息
	 * @param remark 备注
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum 备注类型code
	 * @param remarkType 参数配置的code
	 *
	 */
	public int updateOrderBaseAndStatus(String opUuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType){

		return updateOrderBaseAndStatus(opUuid,oldOrderStatus, orderEntity, remark, remarkType,null);
	}

	/**
	 * 
	 * 客服强制取消恢复
	 *
	 * @author yd
	 * @created 2016年4月26日 下午3:46:32
	 *
	 * @param oldOrderStatus
	 * @param orderEntity
	 * @param remark
	 * @param remarkType
	 * @return
	 */
	public int updateLanCancellOrder(String opUuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType){
		int index =  updateOrderBaseAndStatus(opUuid,oldOrderStatus, orderEntity, remark, remarkType,null);

		if(index>0){
			OrderRelationRequest orderRelationRequest  =new OrderRelationRequest();
			orderRelationRequest.setOldOrderSn(orderEntity.getOrderSn());
			Map<String, Object> reMap = this.orderRelationDao.checkOrderIsRelation(orderRelationRequest);

			boolean flag = (boolean) reMap.get("flag");
			//更新新旧订单关联表
			if(flag == true){
				OrderRelationEntity orderRelationEntity = (OrderRelationEntity) reMap.get("orderRelationEntity");
				if(!Check.NuNObj(orderRelationEntity)){
					orderRelationEntity.setLastModifyDate(new Date());
					orderRelationEntity.setRecoveryTime(new Date());
					index = this.orderRelationDao.updateByCondition(orderRelationEntity);
				}
			}
		}

		return index;
	}
	/**
	 * 更改订单基础信息和状态等信息（ 此处 日志备注和 参数配置的value值不相同 自己设定）
	 * @author afi
	 * @param oldOrderStatus 订单的原始状态
	 * @param orderEntity 订单的主信息
	 * @param remark 备注
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum 备注类型code
	 * @param remarkType  参数配置的code
	 * @param parValue 参数配置的value
	 *
	 */
	public int updateOrderBaseAndStatus(String opUuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType,String parValue){
		int rst = 0;
		if(Check.NuNObj(orderEntity)){
			return rst;
		}
		if(Check.NuNStr(orderEntity.getOrderSn())){
			throw  new BusinessException("orderSn is null on updateOrderInfo");
		}
		if(OrderStatusEnum.getOrderStatusByCode(oldOrderStatus) == null){
            LogUtil.error(LOGGER,"request par is {} ,{} ,{},{}",oldOrderStatus,JsonEntityTransform.Object2Json(orderEntity),remark,remarkType);
			throw  new BusinessException("oldOrderStatus is error on updateOrderInfo");
		}else {
			orderEntity.setOldStatus(oldOrderStatus);
		}
		//直接保存订单信息
		rst = orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
		//记录订单的修改记录，并保存参数信息

		this.saveOrderLog(opUuid,oldOrderStatus,orderEntity,remark,remarkType,parValue);
		return rst;
	}


	/**
	 * 
	 * 房东强制取消订单
	 *
	 * @author yd
	 * @created 2016年4月26日 下午5:34:48
	 *
	 * @param opUuid
	 * @param oldOrderStatus
	 * @param orderEntity
	 * @param remark
	 * @param remarkType
	 * @param parValue
	 * @return
	 */
	public int updateCancellOrderByLandlord(String opUuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType,String parValue){

		int index = updateOrderBaseAndStatus(opUuid, oldOrderStatus, orderEntity, remark, remarkType,parValue);
		//插入差额信息  房东取消申请——》客服恢复——房东申请....
		OrderRelationRequest orderRelationRequest =new  OrderRelationRequest();
		orderRelationRequest.setOldOrderSn(orderEntity.getOrderSn());
		Map<String, Object> reMap = orderRelationDao.checkOrderIsRelation(orderRelationRequest);
		boolean flag = (boolean) reMap.get("flag");
		OrderRelationEntity orderRelationEntity = null;
		if(flag){
			if(reMap.get("orderRelationEntity") == null){
				orderRelationEntity = new OrderRelationEntity();
				orderRelationEntity.setOldOrderSn(orderEntity.getOrderSn());
				orderRelationEntity.setCheckedStatus(CheckedStatusEnum.INIT_CHECKED.getCode());
				orderRelationEntity.setCreatedFid(orderEntity.getLandlordUid());
				orderRelationEntity.setCreatedTime(new Date());
				orderRelationEntity.setLastModifyDate(new Date());
				orderRelationEntity.setApplyTime(new Date());
				index = this.orderRelationDao.insert(orderRelationEntity);
			}else{
				orderRelationEntity = (OrderRelationEntity) reMap.get("orderRelationEntity");
				orderRelationEntity.setLastModifyDate(new Date());
				index = orderRelationDao.updateByCondition(orderRelationEntity);
			}
		}
		return index;

	}

	/**
	 * 保存订单的修改记录
	 * @author afi
	 * @param oldOrderStatus
	 * @param orderEntity
	 * @param remark
	 * @param remarkType
	 * @see com.ziroom.minsu.valenum.order.OrderParamEnum
	 */
	private void saveOrderLog(String opuuid,int oldOrderStatus,OrderEntity orderEntity,String remark,String remarkType,String parValue){

		if(Check.NuNObj(orderEntity)){
			return;
		}
		if(Check.NuNStr(orderEntity.getOrderSn())){
			throw  new BusinessException("orderSn is null on saveOrderLog");
		}
		if(OrderStatusEnum.getOrderStatusByCode(oldOrderStatus) == null){
            LogUtil.error(LOGGER,"request par is {}",orderEntity.getOrderSn());
			throw  new BusinessException("oldOrderStatus is error on saveOrderLog");
		}
		if(Check.NuNStr(parValue)){
			parValue = remark;
		}
		OrderLogEntity log = new OrderLogEntity();
		log.setOrderSn(orderEntity.getOrderSn());
		log.setToStatus(orderEntity.getOrderStatus() == null ? oldOrderStatus : orderEntity.getOrderStatus());
		log.setFromStatus(oldOrderStatus);
		log.setRemark(remark);
		log.setCreateId(opuuid);
		orderLogDao.insertOrderLog(log);
		//获取参数类型
		OrderParamEnum orderParamEnum = OrderParamEnum.geOrderParamByCode(remarkType);
		if(orderParamEnum != null && orderParamEnum !=OrderParamEnum.LOG ){
			saveOrderpar(orderEntity.getOrderSn(),orderParamEnum.getCode(),parValue,orderEntity.getUserUid());
		}
	}
	/**
	 * 保存订单的参数信息
	 * @author afi
	 * @param orderSn
	 * @param parCode
	 * @param parValue
	 * @param userFid
	 */
	private void saveOrderpar(String orderSn,String parCode,String parValue,String userFid){
		OrderParamEntity paramEntity = new OrderParamEntity();
		paramEntity.setOrderSn(orderSn);
		paramEntity.setParCode(parCode);
		paramEntity.setParValue(parValue);
		paramEntity.setCreateFid(userFid);
		orderParamDao.insertParamRes(paramEntity);
	}

	/**
	 * 获取当前订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderEntity getOrderBaseByOrderSn(String orderSn){
		return orderBaseDao.getOrderBaseByOrderSn(orderSn);
	}


	/**
	 * 获取当前订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderInfoVo getOrderInfoByOrderSn(String orderSn){
		return orderDao.getOrderInfoByOrderSn(orderSn);
	}


	/**
	 * 获取当前订单详情信息
	 * @author yd
	 * @param orderSn
	 * @return
	 */
	public OrderDetailVo orderHouseToOrderDetail(String orderSn){
		if(Check.NuNStr(orderSn)){
			throw new BusinessException("orderSn is null");
		}
		OrderInfoVo orderInfo = this.getOrderInfoByOrderSn(orderSn);
		if( orderInfo == null){
			LOGGER.debug("订单编号orderSn={}的订单不存在",orderSn);
			return null;
		}
		OrderDetailVo orderDetailVo = new OrderDetailVo();
		BeanUtils.copyProperties(orderInfo, orderDetailVo);
		LOGGER.info("当前订单详情信息orderInfo:{}"+orderInfo.toString());
		//查询订单入住人信息
		orderDetailVo.setContacts(getOrderContactsByOrderSn(orderDetailVo));
		//该订单的参数明细
		orderDetailVo.setParamMap(this.orderParamDao.getOrderPar(orderSn));
		//定单配置
		orderDetailVo.setListOrderConfigEntities(orderConfigDao.getOrderConfigListByOrderSn(orderSn));
        //其他消费明细
        orderDetailVo.setOtherMoneyDes((String) orderDetailVo.getParamMap().get(OrderParamEnum.OTHER_COST_DES.getCode()));

		//加入入住人列表
		List<UsualContactEntity> contacts = usualContactsDao.findOrderContactsByOrderSn(orderSn);
		orderDetailVo.setListUsualContactEntities(contacts);
		orderDetailVo.setContactsNum(contacts == null ?0:contacts.size());
		//初始化housingDay
		int housingDay  = DateSplitUtil.countDateSplit(orderDetailVo.getStartTime(), orderDetailVo.getEndTime());
		orderDetailVo.setHousingDay(housingDay);
		orderDetailVo.setStartTimeStr(DateUtil.dateFormat(orderDetailVo.getStartTime(),"yyyy-MM-dd"));
		orderDetailVo.setEndTimeStr(DateUtil.dateFormat(orderDetailVo.getEndTime(), "yyyy-MM-dd"));
		orderDetailVo.setRealEndTime(orderInfo.getRealEndTime());
		//如果是房东取消 添加房东的罚款金额  
		if(orderDetailVo.getOrderStatus() == OrderStatusEnum.CANCEL_LAN_APPLY.getOrderStatus()
				||orderDetailVo.getOrderStatus() == OrderStatusEnum.FINISH_LAN_APPLY.getOrderStatus()){
			try {
				FinancePenaltyEntity financePenalty =  financePenaltyDao.findLanFinancePenaltyByOrderSn(orderDetailVo.getOrderSn());
				if(!Check.NuNObj(financePenalty)){
					if(Check.NuNStrStrict(financePenalty.getPenaltyUid())
							||!financePenalty.getPenaltyUid().equals(orderDetailVo.getLandlordUid())){
						throw new BusinessException("【查询订单详情异常】罚款单的罚款人错误orderSn="+orderSn+",penaltyUid="+financePenalty.getPenaltyUid()+",landlordUid="+orderDetailVo.getLandlordUid());
					}
					orderDetailVo.setFinancePenalty(financePenalty);
				}
			} catch (Exception e) {
				LogUtil.error(LOGGER, "【M站查询订单详情,罚款异常】orderSn={},存在多条罚款信息,e={}", orderSn,e);
			}
			
		}
		
		//被罚详情记录
		List<FinancePenaltyPayRelVo> listFinancePenaltyPayRelVo = financePenaltyPayRelDao.findFinancePenaltyPayRelVoByPvOrderSn(orderDetailVo.getOrderSn());
		if(!Check.NuNCollection(listFinancePenaltyPayRelVo)){
			orderDetailVo.setListFinancePenaltyPayRelVo(listFinancePenaltyPayRelVo);
		}
		
		return orderDetailVo;
	}

	/**
	 * 
	 * 条件查询订单
	 *
	 * @author yd
	 * @created 2016年4月12日 下午3:07:26
	 *
	 * @param orderRequest
	 * @return
	 */
	public List<OrderEntity> queryOrderByCondition(OrderRequest orderRequest){
		if(Check.NuNObj(orderRequest)) return null;
		return this.orderDao.queryOrderByCondition(orderRequest);
	}


	/**
	 * 
	 * 根据订单编号修改订单的评价状态
	 *
	 * @author yd
	 * @created 2016年4月12日 下午5:43:04
	 *
	 * @param orderRequest
	 * @return
	 */
	public int updateEvaStatuByOrderSn(OrderRequest orderRequest){
		if(Check.NuNObj(orderRequest)||Check.NuNCollection(orderRequest.getListOrderSn())||Check.NuNObj(orderRequest.getEvaStatus())) return -1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("evaStatus", orderRequest.getEvaStatus());
		map.put("listOrderSn", orderRequest.getListOrderSn());
		return this.orderDao.updateEvaStatuByOrderSn(orderRequest);
	}




	/**
	 * 同步当前评价状态
	 * @author afi
	 * @param evalSynRequest
	 * @return
	 */
	public int updatePjStatuByOrderSn(EvalSynRequest evalSynRequest){
		return this.orderEvalDao.updatePjStatuByOrderSn(evalSynRequest);
	}



	/**
	 * 校验是否是提前退房
	 *
	 * @author afi
	 * @created 2016年4月12日 下午5:43:04
	 *
	 * @param orderInfo
	 * @return
	 */
	public boolean checkOutAdvance(OrderInfoVo orderInfo){
		boolean res = false;
        Date endTime = orderInfo.getEndTime();
        if(new Date().before(DateSplitUtil.getYesterday(endTime))){
            res = true;
        }
		return res;
	}
	/**
	 * 
	 * 查询参数配置信息
	 *
	 * @author yd
	 * @created 2016年4月23日 下午7:21:46
	 *
	 * @param paramMap
	 * @return
	 */
	public List<OrderParamEntity> findParamByCondiction(Map<String,Object> paramMap){
		return this.orderParamDao.findParamByCondiction(paramMap);
	}
	 /**
     * 获取订单的房源信息
     * @author afi
     * @param orderSn
     * @return
     */
    public OrderHouseSnapshotEntity findHouseSnapshotByOrderSn(String orderSn){
        return orderHouseSnapshotDao.findHouseSnapshotByOrderSn(orderSn);
    }
    /**
     * 更新订单的信息
     * @author afi
     * @param orderEntity
     * @return
     */
    public int updateOrderBaseByOrderSn(OrderEntity orderEntity){
        return this.orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
    }
    
    
    
    /**
     * 根据订单号list获取订单list
     * @author lishaochuan
     * @create 2016年9月11日下午5:44:59
     * @param orderSns
     * @return
     */
    public List<OrderEntity> getOrdersBySns(Set<String> orderSns){
        if(Check.NuNCollection(orderSns)){
        	LogUtil.error(LOGGER,"参数为空，orderSns：{}", orderSns);
            throw new BusinessException("参数为空");
        }
        return orderBaseDao.getOrdersBySns(orderSns);
    }


	/**
	 * 统计三小时内新增的当前的恶意下单数量
	 * @author afi
	 * @return
	 */
	public List<MinsuEleEntity> findMaliceOrder(Integer num){
		if (ValueUtil.getintValue(num) == 0){
			return null;
		}
		return orderHouseSnapshotDao.findMaliceOrder(num);
	}
	
	   /**
     * 
     * 根据　用户uid 判断用户是否符合首单立减用户
     * 1. 用户存在 有效订单且发生过入住  
     * 2. 用户存在有效订单且未发生过入住且违约金大于0
     * 3. 取 满足1且满足2  如果>0 不是新用户  ==0 是新用户
     *
     * @author yd
     * @created 2017年6月5日 下午4:13:31
     *
     * @param uid
     * @return
     */
    public boolean isNewUserByOrder(String uid){
    	return this.orderDao.isNewUserByOrder(uid);
    }

    

    /**
     * 
     * 根据　用户uid 判断用户是否是新人  供首页使用
     * 
     * 说明： 只判断当前用户是否有订单
     *
     * @author yd
     * @created 2017年6月16日 下午12:05:05
     *
     * @param uid
     * @return
     */
    public boolean isNewUserByOrderForFirstPage(String uid){
    	return this.orderDao.isNewUserByOrderForFirstPage(uid);
    }
    
	/**
	 * 
	 * 条件分页查询订单
	 *
	 * @author yd
	 * @created 2016年4月12日 下午3:07:26
	 *
	 * @param orderRequest
	 * @return
	 */
	public PagingResult<OrderEntity> queryOrderByPage(OrderRequest orderRequest){
		return orderDao.queryOrderByPage(orderRequest);
	}


	/**
	 * 未支付订单取消
	 *
	 * @author loushuai
	 * @created 2017年10月10日 下午8:01:40
	 *
	 * @param orderSn
	 * @param cancelReason
	 */
	public int cancelUnPayOrder(CancelOrderServiceRequest cancelOrde) {
		String orderSn = cancelOrde.getOrderSn();
		if(Check.NuNStr(orderSn)){
    		return 0;
    	}
        OrderMoneyEntity moneyEntity = orderMoneyDao.getOrderMoneyByOrderSn(orderSn);
        if (Check.NuNObj(moneyEntity)){
            throw new BusinessException("获取订单金额信息失败。orderSn："+orderSn);
        }

        OrderLogEntity log = new OrderLogEntity();
        log.setOrderSn(orderSn);
        log.setToStatus(OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus());
        log.setFromStatus(OrderStatusEnum.WAITING_CHECK_IN.getOrderStatus());
        log.setRemark(cancelOrde.getCancelReason());
        if(!Check.NuNStrStrict(cancelOrde.getOperUid())){
        	log.setCreateId(cancelOrde.getOperUid());
        }else{
        	log.setCreateId("001");
        }
        orderLogDao.insertOrderLog(log);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderSn);
        orderEntity.setOrderStatus(OrderStatusEnum.CANCEL_NEGOTIATE.getOrderStatus());
        int orderRes = orderBaseDao.updateOrderStatusByOrderSn(orderEntity);
        if (orderRes ==1){
            //当前的订单使用了优惠券 需要取消优惠券信息
            if (moneyEntity.getCouponMoney() > 0){
                orderActivityDao.updateGetStatusByCoupon(orderSn);
            }
        }

		if( orderRes ==1){
            //更新订单成功之后 释放房源信息
            houseLockDao.delLockHouseByOrderSn(orderSn);
		}else{
            LogUtil.error(LOGGER,"【取消订单Job】更新订单失败 orderSn:{}",orderSn);
			throw new BusinessException("【取消订单Job】更新订单失败");
		}
        return orderRes;
	}

    /**
     *
     * 查询昨天遗漏的订单行为(房东行为成长体系定时任务)
     *
     * @author zhangyl2
     * @created 2017年10月13日 12:59
     * @param
     * @return
     */
    public List<OrderEntity> queryOrderForCustomerBehaviorJob(OrderRequest orderRequest){
        return this.orderDao.queryOrderForCustomerBehaviorJob(orderRequest);
    }


	/**
	 * 查询房东近60天内的，通过“申请类型”的订单数量
	 *
	 * @author loushuai
	 * @created 2017年10月26日 上午9:24:54
	 *
	 * @param uid
	 * @return
	 */
	public long countAcceptApplyOrder(String uid) {
		return orderDao.countAcceptApplyOrder(uid);
	}


	/**
	 * 查询房东近60天内的，所有“申请类型”的订单数量
	 *
	 * @author loushuai
	 * @created 2017年10月26日 上午9:24:59
	 *
	 * @param uid
	 * @return
	 */
	public long countAllApplyOrder(String uid) {
		return orderDao.countAllApplyOrder(uid);
	}


	/**
	 * 批量获取被邀请用户，订单及状态，填充其被邀请状态
	 *
	 * @author loushuai
	 * @created 2017年12月4日 下午2:32:56
	 *
	 * @param request
	 * @return
	 */
	public List<BeInviterStatusInfoVo> getBeInviterStatusInfo(BeInviterStatusInfoRequest request) {
		return orderBaseDao.getBeInviterStatusInfo(request);
	}

	/**
	 * 查询当前时间4个小时内的已结算的订单的uid和orderSn
	 * @author yanb
	 * @return List<OrderInviteVo>
	 */
	public List<OrderInviteVo> queryOrder4Hour() {
		return orderDao.queryOrder4Hour();
	}


	/**
	 * 查询该用户的创建时间最早的订单的
	 *
	 * @author loushuai
	 * @created 2017年12月19日 下午8:21:22
	 *
	 * @param uid
	 * @return
	 */
	public BeInviterStatusInfoVo getEarliestOrderTime(String uid) {
		return orderBaseDao.getEarliestOrderTime(uid);
	}
}
