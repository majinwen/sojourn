package com.ziroom.zrp.service.trading.proxy.commonlogic;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.thread.pool.SendThreadPool;
import com.ziroom.minsu.services.common.utils.ZkUtil;
import com.ziroom.zrp.houses.entity.EmployeeEntity;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.EmployeeService;
import com.ziroom.zrp.service.houses.api.ProjectService;
import com.ziroom.zrp.service.houses.api.RoomService;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;
import com.ziroom.zrp.service.trading.dep.RoomDep;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.dto.finance.ReceiptBillResponse;
import com.ziroom.zrp.service.trading.entity.SyncContractVo;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.proxy.CheckSignServiceProxy;
import com.ziroom.zrp.service.trading.service.BindPhoneServiceImpl;
import com.ziroom.zrp.service.trading.service.RentContractServiceImpl;
import com.ziroom.zrp.service.trading.service.schedule.ScheduleServiceImpl;
import com.ziroom.zrp.service.trading.utils.DateConvertUtils;
import com.ziroom.zrp.service.trading.utils.builder.DataTransferObjectBuilder;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
import com.ziroom.zrp.service.trading.valenum.*;
import com.ziroom.zrp.service.trading.valenum.delivery.DeliveryStateEnum;
import com.ziroom.zrp.service.trading.valenum.finance.CostCodeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.DocumentTypeEnum;
import com.ziroom.zrp.service.trading.valenum.finance.FinaContractStatusEnum;
import com.ziroom.zrp.service.trading.valenum.finance.VerificateStatusEnum;
import com.ziroom.zrp.trading.entity.BindPhoneEntity;
import com.ziroom.zrp.trading.entity.RentContractAndDetailEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.ziroom.zrp.trading.entity.SchedulePersonEntity;
import com.zra.common.constant.BillMsgConstant;
import com.zra.common.constant.ContractMsgConstant;
import com.zra.common.constant.ContractValueConstant;
import com.zra.common.exception.FinServiceException;
import com.zra.common.exception.ZrpServiceException;
import com.zra.common.utils.DateUtilFormate;
import com.zra.common.vo.contract.RentContractListVo;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>合同公共逻辑</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年12月11日 15:42
 * @since 1.0
 */
@Component("trading.rentContractLogic")
public class RentContractLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentContractLogic.class);

    @Resource(name="houses.employeeService")
    private EmployeeService employeeService;

    @Resource(name="trading.scheduleServiceImpl")
    private ScheduleServiceImpl scheduleServiceImpl;

    @Resource(name="trading.bindPhoneServiceImpl")
    private BindPhoneServiceImpl bindPhoneServiceImpl;

    @Resource(name="houses.projectService")
    private ProjectService projectService;

    @Resource(name = "trading.checkSignServiceProxy")
    private CheckSignServiceProxy checkSignServiceProxy;

    @Resource(name = "trading.rentContractServiceImpl")
    private RentContractServiceImpl rentContractServiceImpl;

	@Resource(name = "trading.financeBaseCall")
	private FinanceBaseCall financeBaseCall;

    @Value("#{'${CUSTOMER_SERVICE_TELEPHONE}'.trim()}")
    private String CUSTOMER_SERVICE_TELEPHONE;
    
    @Resource(name = "houses.roomService")
    private RoomService roomService;

	@Resource(name = "trading.roomDep")
	private RoomDep roomDep;

    @Value("#{'${pic_url}'.trim()}")
    private String pic_url;

    //获取管家电话  排班电话 项目电话
    public String getEmployeePhone(String projectId,String handlZoCode){
        LogUtil.info(LOGGER,"【getEmployeePhone】projectId={},handlZoCode={}",projectId,handlZoCode);
        //获取管家电话
        String phoneNum = null;
        String currentEmployId = null;
        if(!Check.NuNStr(handlZoCode)){
            DataTransferObject employeeObject = JsonEntityTransform.json2DataTransferObject(employeeService.findEmployeeByCode(handlZoCode));
            if (employeeObject.getCode() == DataTransferObject.SUCCESS) {
                EmployeeEntity employeeEntity = employeeObject.parseData("employeeEntity",new TypeReference<EmployeeEntity>() {});
                BindPhoneEntity bindPhoneEntity = bindPhoneServiceImpl.selectByEmployeeId(employeeEntity.getFid());
                currentEmployId = employeeEntity.getFid();
                if(!Check.NuNObj(bindPhoneEntity) && !Check.NuNObj(bindPhoneEntity.getFourooTel())){
                    //管家400电话
                    return CUSTOMER_SERVICE_TELEPHONE+String.valueOf(bindPhoneEntity.getFourooTel());
                }
            } else {
                LogUtil.error(LOGGER, "【getEmployeePhone】查询ZO信息失败，handlZoCode:{},result={}",handlZoCode,employeeObject.toJsonString());
                return phoneNum;
            }
        }

        //项目排班电话，多个值班的随机取一个
        //当天9:00-19点，联系值班管家400电话，超过19点到都联系第二天值班管家400电话
        int week = DateUtilFormate.getWeekDay();
        Date now = DateUtilFormate.getCurrentDate();
        int hour = DateUtilFormate.getHourOfDay(now);
        if(hour > ContractValueConstant.CONTACT_NEXT_DAY_ZO_TIME){
            week = Integer.valueOf(DateUtilFormate.getAddWeekDay(1));//加一天
        }
        List<SchedulePersonEntity> schedulePersonEntitys = scheduleServiceImpl.findSchedulePersonByProjectId(projectId, String.valueOf(week));
        if(!Check.NuNCollection(schedulePersonEntitys)){
            List<String> employFids = schedulePersonEntitys.stream().map(SchedulePersonEntity::getEmployid).collect(Collectors.toList());
            if (!Check.NuNObj(currentEmployId)){
                employFids.remove(currentEmployId);
            }
            if (!Check.NuNCollection(employFids)){
                currentEmployId = employFids.get(new Random().nextInt(employFids.size()));
                BindPhoneEntity bindPhoneEntity = bindPhoneServiceImpl.selectByEmployeeId(currentEmployId);
                if(!Check.NuNObj(bindPhoneEntity) && !Check.NuNObj(bindPhoneEntity.getFourooTel())){
                    //管家400电话
                    return CUSTOMER_SERVICE_TELEPHONE+ String.valueOf(bindPhoneEntity.getFourooTel());
                }
            }
        }

        //取项目销售电话
        String projectStr = projectService.findProjectById(projectId);
        LogUtil.info(LOGGER, "【getEmployeePhone】查询项目信息返回={}",projectStr);
        DataTransferObject projectObj = JsonEntityTransform.json2DataTransferObject(projectStr);
        if(projectObj.getCode() == DataTransferObject.SUCCESS){
            ProjectEntity project = projectObj.parseData("projectEntity", new TypeReference<ProjectEntity>() {});
            if (!Check.NuNObj(project)){
                phoneNum = project.getFmarkettel();
            }
        }else {
            LogUtil.error(LOGGER, "【getEmployeePhone】查询项目信息失败，projectId:{},result={}",projectId,projectObj.toJsonString());
        }
        return phoneNum;//都没有取到只能返回空了
    }

    /**
     * app 确认物业交割
     * 更新物业交割状态 生成合同  同步财务合同状态
     * @author jixd
     * @created 2017年12月20日 13:57:41
     * @param
     * @return
     */
    public DataTransferObject confirmDelivery(final String contractId){
        List<RentContractAndDetailEntity> rentContractAndDetailEntities = rentContractServiceImpl.findContractAndDetailsByContractId(contractId);
        if (Check.NuNCollection(rentContractAndDetailEntities)){
            return DataTransferObjectBuilder.buildError("合同不存在");
        }
		RentContractAndDetailEntity rentContractAndDetailEntity = rentContractAndDetailEntities.get(0);
        SyncContractVo syncContractVo = new SyncContractVo();
        syncContractVo.setCrmContractId(contractId);
        syncContractVo.setStatusCode(FinaContractStatusEnum.getByStatusCodeAndAuditState(rentContractAndDetailEntity.getConStatusCode(), rentContractAndDetailEntity.getDeliveryState(),rentContractAndDetailEntity.getConAuditState()));
        syncContractVo.setRentContractCode(rentContractAndDetailEntity.getConRentCode());
		try {
			financeBaseCall.updateContract(syncContractVo);
		} catch (FinServiceException e) {
			e.printStackTrace();
			return DataTransferObjectBuilder.buildError(e.getMessage());
		}

		ContractRoomDto build = ContractRoomDto.builder().contractId(contractId).roomId(rentContractAndDetailEntity.getRoomId()).build();
        int count = rentContractServiceImpl.updateHasDelivery(build);
        if(count == 1){
			Thread task = new Thread(() -> checkSignServiceProxy.generatePDFContractAndSignature(contractId));
			SendThreadPool.execute(task);
        }
        return DataTransferObjectBuilder.buildOk("");
    }
   //拼装合同列表需要的项目信息和房间信息 xiangb 2017年12月24日
  	public void buildRentContractListVo(RentContractListVo rentContractListVo,RentContractEntity rentContractEntity) throws Exception{
  		  //查询合同对应的项目信息
          String rentRoomStr = roomService.getRentRoomInfoByRoomId(rentContractEntity.getRoomId());
          LogUtil.info(LOGGER, "【buildRentContractListVo】查询项目及房间信息返回:{}", rentRoomStr);
          RentRoomInfoDto rentRoomInfoDto = null;
          DataTransferObject rentRoomObj = JsonEntityTransform.json2DataTransferObject(rentRoomStr);
          if(rentRoomObj.getCode() == DataTransferObject.SUCCESS){
          	rentRoomInfoDto = rentRoomObj.parseData("rentRoom", new TypeReference<RentRoomInfoDto>() {
              });
          }else{
          	LogUtil.error(LOGGER, "【buildRentContractListVo】查询项目及房间信息返回异常={}", rentRoomStr);
          }
          if(!Check.NuNObj(rentRoomInfoDto)){
              rentContractListVo.setProHeadFigureUrl(pic_url+rentRoomInfoDto.getProHeadFigureUrl());
              String rentTitle = "";
              if(!Check.NuNStr(rentRoomInfoDto.getProName()) 
  					&& !Check.NuNStr(rentRoomInfoDto.getRoomNumber()) 
  					&& !Check.NuNObj(rentRoomInfoDto.getFloorNumber())
  					&& !Check.NuNStr(rentRoomInfoDto.getRoomDirection())){
              	rentTitle = rentRoomInfoDto.getProName()
  		    			+rentRoomInfoDto.getRoomNumber()+" "
  		    			+rentRoomInfoDto.getFloorNumber()+ContractMsgConstant.FLOOR_MSG
  		    			+DirectionEnum.getDirection(Integer.valueOf(rentRoomInfoDto.getRoomDirection()))+ContractMsgConstant.DIRECTION_MSG;
  			}
  			if(Check.NuNStr(rentTitle)){//一般不会发生的情况，房间信息缺失的时候只显示项目名称
  				rentTitle = rentRoomInfoDto.getProName();
  			}
              rentContractListVo.setRentTitle(rentTitle);
          }
          String rentTime = "";
          if(!Check.NuNObj(rentContractEntity.getConStartDate()) && !Check.NuNObj(rentContractEntity.getConEndDate())){
              rentTime = DateUtilFormate.formatDateToString(rentContractEntity.getConStartDate(),DateUtilFormate.DATEFORMAT_4)+
              		ContractMsgConstant.FROM_TO_MSG+DateUtilFormate.formatDateToString(rentContractEntity.getConEndDate(),DateUtilFormate.DATEFORMAT_4);
          }
          rentContractListVo.setRentTime(rentTime);
          if(!Check.NuNStr(rentContractEntity.getConType()) && !Check.NuNObj(rentContractEntity.getRoomSalesPrice())){
              // 房间信息
              String roomStr = roomService.getRoomByFid(rentContractEntity.getRoomId());
              DataTransferObject roomObject = JsonEntityTransform.json2DataTransferObject(roomStr);
              if (roomObject.getCode() == DataTransferObject.ERROR) {
                  LogUtil.error(LOGGER, "【buildRentContractListVo】请求结果异常={}", roomStr);
              }
              RoomInfoEntity roomInfoEntity = roomObject.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
              });
              if(!Check.NuNObj(roomInfoEntity)){
              	LeaseCyclePojo leaseCyclePojo = new LeaseCyclePojo(rentContractEntity.getConType(),rentContractEntity.getProjectId(),roomInfoEntity.getRentType());
                  String roomSalesPrice = null;
          		if(Check.NuNObj(rentContractEntity.getFactualprice())){//实际出房价为空，取计划出房价
          			roomSalesPrice = LeaseCycleFactory.createLeaseCycle(leaseCyclePojo).calculateActualRoomPrice(rentContractEntity.getConRentYear(), rentContractEntity.getRoomSalesPrice());
          		}else{
          			BigDecimal actualPrice = new BigDecimal(rentContractEntity.getFactualprice()).setScale(0, BigDecimal.ROUND_HALF_UP);
          			if(LeaseCycleEnum.DAY.getCode().equals(rentContractEntity.getConType())){
          				roomSalesPrice = String.format("%s%s/日",
              					BillMsgConstant.RMB, actualPrice);
          			}else{
          				roomSalesPrice = String.format("%s%s/月",
              					BillMsgConstant.RMB, actualPrice);
          			}
          		}
                  rentContractListVo.setRoomSalesPrice(roomSalesPrice);
              }
          }
  	}
  	
  	//获取最近一期未支付账单，及账单总数目 xiangb 2017年12月24日
  	public void findWaitforPaymentListUtil(List<ReceiptBillResponse> listBill,Map<String,Integer> countMap,ReceiptBillResponse fzbillResponse,List<ReceiptBillResponse> shfyResponse){
  		int allCount = countMap.get("allCount");
  		int liftCount = countMap.get("liftCount");
  		for (ReceiptBillResponse receiptBillResponse : listBill) {
              if(VerificateStatusEnum.DONE.getCode() !=receiptBillResponse.getVerificateStatus()){
                  if(DocumentTypeEnum.RENT_FEE.getCode().equals(receiptBillResponse.getDocumentType())
                		  && CostCodeEnum.KHFZ.getCode().equals(receiptBillResponse.getCostCode())){
                  		allCount = 1;
                      	if(!Check.NuNObj(fzbillResponse) && Check.NuNStr(fzbillResponse.getBillNum())){
//                      		fzbillResponse = receiptBillResponse;
                      		BeanUtils.copyProperties(receiptBillResponse, fzbillResponse);
                      	}
                          if(!Check.NuNStr(receiptBillResponse.getPreCollectionDate()) && !Check.NuNObj(fzbillResponse) && !Check.NuNStr(fzbillResponse.getPreCollectionDate())){
                              if(DateUtilFormate.formatStringToDate(receiptBillResponse.getPreCollectionDate(),DateUtilFormate.DATEFORMAT_4).getTime()
                              		< DateUtilFormate.formatStringToDate(fzbillResponse.getPreCollectionDate(),DateUtilFormate.DATEFORMAT_4).getTime()){
//                                  fzbillResponse = receiptBillResponse;
                            	  BeanUtils.copyProperties(receiptBillResponse, fzbillResponse);
                              }
                          }
                  }
                  if(DocumentTypeEnum.LIFE_FEE.getCode().equals(receiptBillResponse.getDocumentType())
                          || DocumentTypeEnum.OVERDUE_FEE.getCode().equals(receiptBillResponse.getDocumentType())){
                      liftCount = liftCount +1;
                      if(!Check.NuNObj(shfyResponse)){
                      	shfyResponse.add(receiptBillResponse);
                      }
                  }

              }
          }
  		if(liftCount >0){
  			allCount = allCount + liftCount;
  		}
  		countMap.put("liftCount", liftCount);
  		countMap.put("allCount", allCount);
  	}
  	//判断是否显示去支付按钮 xiangb 2017年12月24日
  	public boolean isShowBill(String conCycleCode,String payDateStr){
  		boolean isShow = false;
  		if(Check.NuNStr(conCycleCode) || Check.NuNStr(payDateStr)){
  			return isShow;
  		}
  		try{
  			Date payDate = DateUtilFormate.formatStringToDate(payDateStr,DateUtilFormate.DATEFORMAT_4);
  			Date now = new Date();
  			Calendar calendar = Calendar.getInstance();
  			calendar.setTime(now);
  			//付款周期：1 月付 3 季付 6 半年付 12 年付 9 一次性付清（短租）
  			if(conCycleCode.equals(PaymentCycleEnum.YF.getCode())){//月付，距离支付日期前10天展示
  				calendar.add(Calendar.DATE, 10);
  				now = calendar.getTime();
  				if(!payDate.after(now)){//小于等于10天
  					isShow  = true;
  				}
  			}else if(conCycleCode.equals(PaymentCycleEnum.JF.getCode())){//季付，距离支付日期前30天展示
  				calendar.add(Calendar.DATE, 30);
  				now = calendar.getTime();
  				if(!payDate.after(now)){
  					isShow  = true;
  				}
  			}else if(conCycleCode.equals(PaymentCycleEnum.BNF.getCode())){//半年付，距离支付日期前60天展示
  				calendar.add(Calendar.DATE, 60);
  				now = calendar.getTime();
  				if(!payDate.after(now)){
  					isShow  = true;
  				}
  			}else{
  				isShow  = false;
  			}
  			return isShow;
  		}catch(Exception e){
  			LogUtil.error(LOGGER, "【isShowBill】出错：{}", e);
  			return false;
  		}
  	}
    //返回已到期，已关闭，已退租的合同，已到期若没有续约则不显示
	public List<RentContractAndDetailEntity> sortInvalidContractList(List<RentContractAndDetailEntity> contracts){
		if(Check.NuNCollection(contracts)){
			return null;
		}
		List<RentContractAndDetailEntity> returnContracts = new ArrayList<>();
		returnContracts.addAll(contracts);
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.WQY.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList()));//待签约
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.DZF.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList()));//待支付
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.WJG.getCode() == v.getDeliveryState())
				.collect(Collectors.toList()));//待物业交割
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.JJG.getCode() == v.getDeliveryState()
				&& !(ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())))
				.collect(Collectors.toList()));//审核中
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.JJG.getCode() == v.getDeliveryState()
				&& ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())
				&& v.getConStartDate().getTime() > new Date().getTime())
				.collect(Collectors.toList()));//待入住
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
			v.getConStatusCode()) && DeliveryStateEnum.JJG.getCode() == v.getDeliveryState()
			&& ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())
			&& v.getConStartDate().getTime() < new Date().getTime()
			&& DateUtilFormate.addDate(v.getConEndDate(),1).getTime() > new Date().getTime())
			.collect(Collectors.toList()));//履约中
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.XYZ.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList()));//续约中
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.JYZ.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList()));//解约中
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.YXY.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList()));//已续约,到期后合同状态为已到期
		returnContracts.removeAll(contracts.stream().filter(v -> ContractStatusEnum.YZF.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList()));//已作废
		return returnContracts;//已到期的需要判断是否续约，续约的显示，
	}
	//把返回合同按照待签约--待支付--待物业交割--审核中--履约中展示 xiangb 2017年12月24日
	public List<RentContractAndDetailEntity> sortContractList(List<RentContractAndDetailEntity> contracts){
		if(Check.NuNCollection(contracts)){
			return null;
		}
		List<RentContractAndDetailEntity> returnContracts = new ArrayList<>();
		List<RentContractAndDetailEntity> interimContracts = new ArrayList<>();
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.WQY.getStatus().equals(
				v.getConStatusCode()) && String.valueOf(ContractSourceEnum.APP.getCode()).equals(v.getFsource())
				).collect(Collectors.toList());//待签约
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.DQY.getCode()); v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES); v.setOperationEnum(OperationEnum.QY.getCode());});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.DZF.getStatus().equals(
				v.getConStatusCode()) && String.valueOf(ContractSourceEnum.APP.getCode()).equals(v.getFsource())).
				collect(Collectors.toList());//待支付
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.DZF.getCode()); v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES); v.setOperationEnum(OperationEnum.ZF.getCode());});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.WJG.getCode() == v.getDeliveryState()
				&& String.valueOf(ContractSourceEnum.APP.getCode()).equals(v.getFsource()))
				.collect(Collectors.toList());//待物业交割
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.DWYJG.getCode()); v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES); v.setOperationEnum(OperationEnum.WYJG.getCode());});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.JJG.getCode() == v.getDeliveryState()
				&& !(ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())))
				.collect(Collectors.toList());//审核中
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.SHZ.getCode());v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES);});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.JJG.getCode() == v.getDeliveryState()
				&& ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())
				&& v.getConStartDate().getTime() > new Date().getTime())
				.collect(Collectors.toList());//待入住
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.DRZ.getCode()); v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES);});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.YQY.getStatus().equals(
				v.getConStatusCode()) && DeliveryStateEnum.JJG.getCode() == v.getDeliveryState()
				&& ContractAuditStatusEnum.YTG.getStatus().equals(v.getConAuditState())
				&& v.getConStartDate().getTime() < new Date().getTime()
				&& DateUtilFormate.addDate(v.getConEndDate(),1).getTime() > new Date().getTime())
				.collect(Collectors.toList());//履约中--可以有支付操作
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.LXZ.getCode());v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES);});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.XYZ.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList());//续约中
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.LXZ.getCode());v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES); v.setOperationEnum(OperationEnum.XY.getCode());});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.JYZ.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList());//解约中
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.JYZ.getCode());v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES);});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.YXY.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList());//已续约--最终状态已到期
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.LXZ.getCode());v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES);});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		interimContracts = contracts.stream().filter(v -> ContractStatusEnum.YDQ.getStatus().equals(
				v.getConStatusCode())).collect(Collectors.toList());//已到期，未解约,需要排除已续约到期的
		interimContracts.stream().forEach(v -> {v.setShowEnum(ConstatusShowEnum.YDQ.getCode());v.setShowContactZO(ContractValueConstant.CONTACT_ZO_YES);});
		if(!Check.NuNCollection(interimContracts)){
			returnContracts.addAll(interimContracts);
			interimContracts = null;//清空
		}
		return returnContracts;
	}
	/**
     * <p>获取合同状态的枚举类</p>
     * @author xiangb
     * @created 2017年10月11日
     * @param
     * @return
     */
    public ConstatusShowEnum getConstatusShowEnum(String conStatus,String conAuditState,Integer deliverStatus){
        if(Check.NuNStr(conStatus)) return null;
        if(ContractStatusEnum.WQY.getStatus().equals(conStatus)){
            return ConstatusShowEnum.DQY;//待签约
        }else if(ContractStatusEnum.DZF.getStatus().equals(conStatus)){
            return ConstatusShowEnum.DZF;//待支付
        }else if(ContractStatusEnum.YQY.getStatus().equals(conStatus) && deliverStatus == 0){
            return ConstatusShowEnum.DWYJG;//待物业交割
        }else if(ContractStatusEnum.YQY.getStatus().equals(conStatus) && deliverStatus == 1 
        		&& !ContractAuditStatusEnum.YTG.getStatus().equals(conAuditState)){
            return ConstatusShowEnum.SHZ;//审核中
        }else if(ContractStatusEnum.YQY.getStatus().equals(conStatus) && deliverStatus == 1 
        		&& ContractAuditStatusEnum.YTG.getStatus().equals(conAuditState)){
            return ConstatusShowEnum.LXZ;//履行中
        }else if(ContractStatusEnum.YTZ.getStatus().equals(conStatus)){
            return ConstatusShowEnum.YTZ;//已退租（失效列表）
        }else if(ContractStatusEnum.JYZ.getStatus().equals(conStatus)){
            return ConstatusShowEnum.JYZ;//解约中
        }else if(ContractStatusEnum.YGB.getStatus().equals(conStatus)){
            return ConstatusShowEnum.YGB;//已关闭（失效列表） 需要判断是否是管家关闭
        }else if(ContractStatusEnum.YZF.getStatus().equals(conStatus)){
            return null;//已作废的不显示
        }else if(ContractStatusEnum.XYZ.getStatus().equals(conStatus)){
            return ConstatusShowEnum.LXZ;//续约中显示履行中
        }else if(ContractStatusEnum.YXY.getStatus().equals(conStatus)){
            return ConstatusShowEnum.LXZ;//已续约--履行中，考虑未到合同结束日期
        }else if(ContractStatusEnum.YDQ.getStatus().equals(conStatus)){
            return ConstatusShowEnum.YDQ;//已到期--（失效列表）需判断是否解约
        }else{
            return null;
        }
    }

	/**
	 * 查询分摊因子
	 *
	 * @param contractId 合同标识
	 * @return
	 * @throws ZrpServiceException
	 * @author cuigh6
	 * @Date 2018年2月7日
	 */
	public int getDistributionFactor(String contractId) throws ZrpServiceException {
		LogUtil.info(LOGGER, "【getDistributionFactor】查询分摊因子参数：{}", contractId);

		try {

			RentContractEntity contractEntity = this.rentContractServiceImpl.findContractBaseByContractId(contractId);
			if (Check.NuNObj(contractEntity)) {
				LogUtil.error(LOGGER, "【findContractBaseByContractId】查询分摊因子方法，查询合同信息为空；contractId={}", contractId);
			}
			List<RentContractEntity> roomValidContractList = this.getRoomValidContractList(contractEntity.getRoomId());
			return roomValidContractList.size();
		} catch (Exception e) {
			LogUtil.error(LOGGER, "【getRoomValidContractList】查询分摊因子错误；contractId={}", contractId, e);
			throw new ZrpServiceException("查询分摊因子错误");
		}
	}

	/**
	 * 查询房间下的有效合同列表
	 *
	 * @param roomId 房间标识
	 * @return
	 * @throws Exception
	 * @author cuigh6
	 * @Date 2018年2月8日
	 */
	public List<RentContractEntity> getRoomValidContractList(String roomId) throws Exception {
		List<RentContractEntity> list = new ArrayList<>();
		// 根据这个房间查询是否存在父房间
		String roomJson = this.roomService.getRoomByFid(roomId);
		DataTransferObject roomObj = JsonEntityTransform.json2DataTransferObject(roomJson);
		RoomInfoEntity roomInfo = roomObj.parseData("roomInfo", new TypeReference<RoomInfoEntity>() {
		});
		if (Check.NuNObj(roomInfo)) {
			return list;
		}
		Date currentDate = DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDate(new Date())
				.format(DateTimeFormatter.ofPattern(DateUtilFormate.DATEFORMAT_4)) + "T00:00:00");// LocalDate 时间格式 月份和时间 中间要有T
		// 如果不存在父房间
		if (Check.NuNObj(roomInfo.getParentId())) {
			// 判断该房间是否存在子房间
			List<RoomInfoEntity> roomInfoEntities = this.roomDep.getRoomInfoByParentId(roomId);
			if (Check.NuNCollection(roomInfoEntities)) {
				List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.findValidContractByRoomId(roomId);
				List<RentContractEntity> collect = rentContractEntities.stream().filter(v -> v.getConStartDate().compareTo(currentDate) < 1
						&& v.getConEndDate().compareTo(currentDate) >= 0).collect(Collectors.toList());
				list.addAll(collect);
			}else{
				for (RoomInfoEntity roomInfoEntity : roomInfoEntities) {
					List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.findValidContractByRoomId(roomInfoEntity.getFid());
					List<RentContractEntity> collect = rentContractEntities.stream().filter(v -> v.getConStartDate().compareTo(currentDate) < 1
							&& v.getConEndDate().compareTo(currentDate) >= 0).collect(Collectors.toList());
					list.addAll(collect);
				}
			}
		} else {
			List<RoomInfoEntity> roomInfoEntities = this.roomDep.getRoomInfoByParentId(roomInfo.getParentId());
			// 查询房间下是否存在有效的合同
			for (RoomInfoEntity roomInfoEntity : roomInfoEntities) {
				List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.findValidContractByRoomId(roomInfoEntity.getFid());
				List<RentContractEntity> collect = rentContractEntities.stream().filter(v -> v.getConStartDate().compareTo(currentDate) < 1
						&& v.getConEndDate().compareTo(currentDate) >= 0).collect(Collectors.toList());
				list.addAll(collect);
			}
		}
		return list;
	}

	/**
	 * 查询所有 有有效合同并且存在智能水表设备的房间列表
	 * @return
	 * @author cuigh6
	 * @Date 2018年2月28日
	 */
   public List<RoomInfoEntity> getAllRoomOfValidContractAndExistIntellectWaterMeter() {
	   List<RoomInfoEntity> roomInfoEntityList = new ArrayList<>();
	   String allRoomOfBindingWaterMeter = this.roomService.getAllRoomOfBindingWaterMeter();
	   DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(allRoomOfBindingWaterMeter);
	   if (dataTransferObject.getCode() == DataTransferObject.ERROR) {
		   LogUtil.error(LOGGER, "【getAllRoomOfBindingWaterMeter】查询所有有效合同并且存在智能水表房间报错，message：{}", dataTransferObject.getMsg());
	   }
	   List<RoomInfoEntity> roomList = dataTransferObject.parseData("roomList", new TypeReference<List<RoomInfoEntity>>() {
	   });
	   Date currentDate = DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDate(new Date())
			   .format(DateTimeFormatter.ofPattern(DateUtilFormate.DATEFORMAT_4)) + "T00:00:00");// LocalDate 时间格式 月份和时间 中间要有T
	   // 查询房间下是否存在有效的合同
	   for (RoomInfoEntity roomInfoEntity : roomList) {
		   List<RentContractEntity> rentContractEntities = this.rentContractServiceImpl.findValidContractByRoomId(roomInfoEntity.getFid());
		   List<RentContractEntity> collect = rentContractEntities.stream().filter(v -> v.getConStartDate().compareTo(currentDate) < 1
				   && v.getConEndDate().compareTo(currentDate) >= 0).collect(Collectors.toList());
		   if (!Check.NuNCollection(collect)) {
			   roomInfoEntityList.add(roomInfoEntity);
		   }
	   }
	   return roomInfoEntityList;
   }
}
