package com.zra.business.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zra.business.entity.BusinessEntity;
import com.zra.business.entity.BusinessListBusInfoEntity;
import com.zra.business.entity.BusinessResultEntity;
import com.zra.business.entity.CustomerEntity;
import com.zra.business.entity.dto.BusinessApplyReturnDto;
import com.zra.business.entity.dto.BusinessEvaluateSMSDto;
import com.zra.business.entity.dto.BusinessListDto;
import com.zra.business.entity.dto.BusinessOrderDto;
import com.zra.business.entity.vo.BusinessResultVo;
import com.zra.business.service.BoCloseSmsService;
import com.zra.business.service.BusinessService;
import com.zra.common.dto.business.BOQueryParamDto;
import com.zra.common.dto.business.BoCloseParamDto;
import com.zra.common.dto.business.BoCloseSMSContent;
import com.zra.common.dto.business.BoDistParamDto;
import com.zra.common.dto.business.BusinessDto;
import com.zra.common.dto.business.BusinessFullDto;
import com.zra.common.dto.business.BusinessResultDto;
import com.zra.common.dto.business.BusinessShowDto;
import com.zra.common.dto.business.CustomerDto;
import com.zra.common.dto.house.BusinessListReturnDto;
import com.zra.common.dto.house.ProjectDto;
import com.zra.common.dto.log.LogRecordDto;
import com.zra.common.dto.push.PushDto;
import com.zra.common.enums.BoBusinessStep;
import com.zra.common.enums.BoCloseReason;
import com.zra.common.enums.BoHandResult;
import com.zra.common.enums.BoHandState;
import com.zra.common.enums.BoSourceType;
import com.zra.common.enums.BussSystemEnums;
import com.zra.common.enums.ErrorEnum;
import com.zra.common.error.ResultException;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.KeyGenUtils;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.SmsUtils;
import com.zra.common.utils.StrUtils;
import com.zra.common.utils.StringUtil;
import com.zra.common.utils.SysConstant;
import com.zra.common.utils.ZraApiConst;
import com.zra.evaluate.entity.dto.EvaluateDto;
import com.zra.evaluate.logic.EvaluateLogic;
import com.zra.house.entity.HouseTypeEntity;
import com.zra.house.logic.ProjectLogic;
import com.zra.log.logic.LogLogic;
import com.zra.marketing.logic.MkChannelLogic;
import com.zra.projectZO.logic.ProjectZOLogic;
import com.zra.push.logic.PushLogic;
import com.zra.system.entity.EmployeeEntity;
import com.zra.system.logic.EmployeeLogic;
import com.zra.system.logic.UserAccountLogic;
import com.zra.zmconfig.ConfigClient;
import com.zra.zmconfig.entity.CfShortUrl;
import com.zra.zmconfig.logic.ShortUrlLogic;

/**
 * @author wangws21 2016年8月6日
 *
 */
@Component
public class BusinessLogic {
    private static final Logger LOGGER = LoggerFactoryProxy.getLogger(BusinessLogic.class);
    
    private Calendar CAL = Calendar.getInstance();

    @Autowired
    private BusinessService businessService;
    @Autowired
    private ProjectLogic projectLogic;
    
    @Autowired
    private EmployeeLogic employeeLogic;
    
    @Autowired
    private UserAccountLogic userAccountLogic;
    
    @Autowired
    private LogLogic logLogic;

    @Autowired
    private ConfigClient configClient;

    @Autowired
    private ProjectZOLogic projectZOLogic;

    @Autowired
    private ShortUrlLogic shortUrlLogic;

    @Autowired
    private PushLogic pushLogic;

    @Autowired
    private EvaluateLogic evaluateLogic;

    @Autowired
    private CustomerLogic customerLogic;
    
    @Autowired
    private MkChannelLogic mkChannelLogic;
    
    @Autowired
    private BoCloseSmsService boCloseSmsService;

    /**
     * 保存商机 wangws21 2016-8-1
     * @param businessFullDto 商机实体
     * @return true/false
     */
    public boolean save(BusinessFullDto businessFullDto){
        BusinessResultDto businessResultDto = businessFullDto.getBusinessResult(); //传进来的商机处理结果dto
        BusinessDto businessDto = businessFullDto.getBusiness();
        CustomerDto customerDto = businessFullDto.getCustomer();

        /*管家id和姓名 城市id*/
        String euid = "",ename = "",ePhone="",userId=businessFullDto.getUserId(),cityId = businessFullDto.getCityId();
        EmployeeEntity emp = employeeLogic.getEmployeeByUserId(userId);
        boolean isZO = this.userAccountLogic.isZO(userId);
        if(emp!=null && isZO){
            euid=emp.getId();
            ename = emp.getName();
            ePhone = emp.getMobile();
        }

        String businessBid = KeyGenUtils.genKey();
        String cusBid = KeyGenUtils.genKey();
        byte step = this.getBusinessStepFromResult(businessResultDto.getHandResult(), businessBid);
        Date endTime = null;
        try {
            endTime = this.getEndTimeByStep(step,businessResultDto.getHandResult() , businessResultDto.getHandResultTime());
        } catch (ParseException e) {
            LOGGER.error("[保存商机]计算约看截止日期出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
        }

        Byte handState = null;
        try {
            if(endTime==null){
                handState = 6; //完成
            }else{
                handState = DateUtil.getDealStatusByEndTime(endTime);
            }
        } catch (ParseException e) {
            LOGGER.error("[保存商机]判断处理状态出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
        }

        //商机
        BusinessEntity business = new BusinessEntity(businessBid, euid, new Date(), euid, new Date(), cityId, cusBid,
                step, endTime, handState, businessDto.getMessage(), businessDto.getProjectId(), businessDto.getHouseTypeId(), businessDto.getSource(),
                businessDto.getSourceZra(), businessDto.getExpectTime(), businessDto.getExpectInStartTime(),businessDto.getExpectInEndTime(),
                businessDto.getExpectLowPrice(), businessDto.getExpectHighPrice(), businessDto.getType(), businessDto.getRemark(), euid, ename);

        //客户
        CustomerEntity customer = new CustomerEntity(cusBid, euid, new Date(), euid, new Date(), cityId, customerDto.getName(), customerDto.getPhone(),
                customerDto.getGender(), customerDto.getAge(), customerDto.getCompany(), customerDto.getAddress());

        //新建商机的商机阶段来源于不同的商机处理结果
        Byte resultStep = this.getPreBusinessResultStepByResult(businessResultDto.getHandResult());
        //是否发送约看提醒
        Byte isSendSMS = this.checkIsSendYKSms(businessResultDto.getHandResult(), businessResultDto.getHandResultTime(),null);
        //是否评价短信
        Byte isEvaluateSms = businessResultDto.getHandResult()==BoHandResult.XJ_QR_YK.getIndex()?(byte)1:(byte)0;
        // 商机处理结果 处理结果不保存跟进记录，数据表该字段为空
        String resultBid = KeyGenUtils.genKey();
        BusinessResultEntity businessResult = new BusinessResultEntity(resultBid, business.getBusinessBid(), euid, cityId, resultStep,
                business.getHandState(), business.getEndTime(), businessResultDto.getHandResult(), businessResultDto.getHandResultTime(),
                businessResultDto.getHandResultContent(), null, euid, isSendSMS,isEvaluateSms);

        boolean result = businessService.insertFullBusinessEntitys(business, customer, businessResult);
        //记录操作日志
        this.addBusinessLog(businessResult,businessResultDto.getRecord(),emp!=null?emp.getName():"");
        //发送短信
        this.sendSMS(businessResultDto.getHandResult(), businessResultDto.getHandResultTime(), customer.getPhone(),
        		business.getProjectId(), ename, ePhone,business.getBusinessBid(),businessResultDto.getCloseMsgDescContent());
        return result;
    }

    /**
     * wangws21 2016-8-11
     * 新建商机的处理结果商机阶段来源于不同的商机处理结果
     * @param handResult 商机处理结果
     * @return 原商机阶段
     */
    private Byte getPreBusinessResultStepByResult(Byte handResult) {
        /*新建商机 键值在10和19之间*/
        /*XJ_DQR_SJ((byte)14,"待和用户确认约看时间"),
        XJ_QR_YK((byte)13,"确认约看"),
        XJ_DHF((byte)12,"已带看，待进一步跟进回访"),
        XJ_YQY((byte)11,"已签约，完成商机"),
        XJ_GB_SJ((byte)10,"关闭商机"),  带原因*/
        if(handResult==BoHandResult.XJ_DHF.getIndex()){
            return BoBusinessStep.D_DK.getIndex();
        }else{   /*已签约和关闭商机默认都是待约看  待约看数据统计时需要*/
            return BoBusinessStep.D_YK.getIndex();
        }
    }

	/**
	 * wangws21 2016-8-6
	 * 根据处理结果确定商机阶段
	 * @author wangws21
	 * @param handResult 处理结果
	 * @return 商机阶段
	 */
	public byte getBusinessStepFromResult(Byte handResult, String businessBid) {
		
		if(handResult==BoHandResult.XJ_DQR_SJ.getIndex()){
			
			return BoBusinessStep.D_YK.getIndex();/* 1：待约看 */
			
		}else if(handResult==BoHandResult.YK_DGJ.getIndex() || handResult==BoHandResult.YK_DGJ_YK.getIndex()){
			BusinessResultEntity lastBusinessResult = this.businessService.getLastBusinessResult(businessBid);
			//约看结果中出现2次联系不上用户，则系统自动关闭商机，商机阶段变更为未成交
			if(lastBusinessResult!=null && lastBusinessResult.getHandResult()==BoHandResult.YK_DGJ.getIndex()){
				return BoBusinessStep.W_CJ.getIndex();/*5：未成交*/
			}else{
				return BoBusinessStep.D_YK.getIndex();/* 1：待约看 */
			}
		}else if(handResult==BoHandResult.XJ_QR_YK.getIndex()||
				handResult==BoHandResult.YK_QR_YK.getIndex() ||
				handResult==BoHandResult.DK_TC_SJ.getIndex()){
			return BoBusinessStep.D_DK.getIndex();/* 2：待带看 */
			
		}else if(handResult==BoHandResult.XJ_DHF.getIndex()||
				handResult==BoHandResult.DK_DHF.getIndex()||
				handResult==BoHandResult.HF_DHF.getIndex()){
			return BoBusinessStep.D_HF.getIndex();/* 3：待回访 */
			
		}else if(handResult==BoHandResult.XJ_YQY.getIndex()||
				handResult==BoHandResult.DK_YQY.getIndex()||
				handResult==BoHandResult.HF_YQY.getIndex()){
			return BoBusinessStep.Y_CJ.getIndex();/* 4：成交 */
			
		}else{
			return BoBusinessStep.W_CJ.getIndex();/*5：未成交*/
		}
	}
    /**
     * 查询商机
     * @author tianxf9
     * @param paramsDto
     * @return
     */
    public Map<String,Object> getBusinessList(BOQueryParamDto paramsDto) {
        PageHelper.startPage(paramsDto.getPageNum(), paramsDto.getRows());
        //设置查询条件日期
        if(!StringUtil.isBlank(paramsDto.getFilter_and_createTimeBeg())) {
            paramsDto.setFilter_and_createTimeBeg(DateUtil.getDayBeginTime(paramsDto.getFilter_and_createTimeBeg()));
        }
        if(!StringUtil.isBlank(paramsDto.getFilter_and_createTimeEnd())) {
            paramsDto.setFilter_and_createTimeEnd(DateUtil.getDayEndTime(paramsDto.getFilter_and_createTimeEnd()));
        }
        if(!StringUtil.isBlank(paramsDto.getFilter_and_endTimeBeg())) {
            paramsDto.setFilter_and_endTimeBeg(DateUtil.getDayBeginTime(paramsDto.getFilter_and_endTimeBeg()));
        }
        if(!StringUtil.isBlank(paramsDto.getFilter_and_endTimeEnd())) {
            paramsDto.setFilter_and_endTimeEnd(DateUtil.getDayEndTime(paramsDto.getFilter_and_endTimeEnd()));
        }
        List<BusinessShowDto> relsultList = this.businessService.query(paramsDto);
        for(BusinessShowDto showDto:relsultList) {
            ProjectDto project = this.projectLogic.getProjectDtoById(showDto.getProjectId());
            showDto.setProjectName(project.getName());
        }
        PageInfo<BusinessShowDto> pageInfo = new PageInfo<>(relsultList);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        LOGGER.info("total:"+relsultList.size()+","+relsultList);
        dataMap.put("total", pageInfo.getTotal());
        dataMap.put("rows", relsultList);
        return dataMap;
    }

    /**
     * 预约看房
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    public BusinessApplyReturnDto insertBusinessApply(BusinessOrderDto dto) {
        String customerBid = KeyGenUtils.genKey();//约看客户的bid
        //客户
        CustomerEntity ce = new CustomerEntity();
        ce.setCusBid(customerBid);
        ce.setCusUuid(dto.getUuid());
        ce.setName(dto.getName());
        ce.setPhone(dto.getPhone());
        ce.setCompany(dto.getCompany());
        ce.setNationality(dto.getNationality());

        //商机
        String businessBid = KeyGenUtils.genKey();
        BusinessEntity be = new BusinessEntity();
        be.setBusinessBid(businessBid);
        be.setCityId(projectLogic.getCityIdByProjectId(dto.getProjectId()));
        be.setCustomerId(customerBid);
        be.setMessage(dto.getMessage());
        be.setProjectId(dto.getProjectId());
        be.setHouseTypeId(dto.getHtId());
        be.setSource(dto.getSourceOfBu());
        try {
            be.setExpectTime(DateUtil.castString2Date(dto.getExpectTime(), "yyyy-MM-dd HH:mm"));
        } catch (ParseException e) {
            LOGGER.error("[预约看房]约看时间出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_EXPECTTIME_FAIL);
        }
        Date DYKEndTime = null;
        try {
            DYKEndTime = getDYKEndTime();
            be.setEndTime(DYKEndTime);
        } catch (ParseException e) {
            LOGGER.error("[预约看房]计算约看截止日期出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
        }
        if (dto.getExpectInStartTime() != null) {
            try {
                be.setExpectInStartTime(DateUtil.castString2Date(dto.getExpectInStartTime(), "yyyy-MM-dd"));
            } catch (ParseException e) {
                LOGGER.error("[预约看房]期望入住时间出错", e);
                throw new ResultException(ErrorEnum.MSG_BUSINESS_EXPECTINTIME_FAIL);
            }
        }
        Byte handState = null;
        try {
            handState = DateUtil.getDealStatusByEndTime(DYKEndTime);
            be.setHandState(handState);
        } catch (ParseException e) {
            LOGGER.error("[预约看房]判断处理状态出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
        }
        //判断分配给那个ZO
        EmployeeEntity ee = distributeZO(dto.getProjectId(), handState);
        be.setZoId(ee.getId());
        String zoName = ee.getName();
        be.setZoName(zoName);

        /*春节期间商机特殊处理 wangws21 2017-1-13*/
        this.businessInChunjie(be, ce.getPhone());

        //保存商机和客户
        businessService.insertBusinessApply(ce, be);
        BusinessApplyReturnDto bd = new BusinessApplyReturnDto();
        bd.setMessage(PropUtils.getString(ZraApiConst.APP_BUSINESS_APPLY_RETURN));
        /*保存操作记录*/
        LogRecordDto logRecord = new LogRecordDto(BussSystemEnums.ZRA.getKey(), "business", businessBid, "系统", "新建商机");
        LOGGER.info("logRecord.toString():" + logRecord.getSystemId() + "," + logRecord.getOperModId() + "," + logRecord.getOperObjId() + "," + logRecord.getOperator() + "," + logRecord.getLoginfo());
        boolean flag = logLogic.saveLog(logRecord);
        LOGGER.info("save info flag:" + flag);
        try {
            sendYytzMsg(businessBid);
        } catch (Exception e) {
            LOGGER.error("[预约看房]出错", e);
        }
        return bd;
    }
    
    /**
     * wangws21 2017-1-13.
     * 春节期间商机处理规则说明：
     * 对象：创建时间段为1月24日00:00～2月2日23:59的所有商机
     * 操作：给商机中的客户电话推送一条短信，并将商机的截止处理时间改为2月3日23:59
     * @param be
     */
    private void businessInChunjie(BusinessEntity business, String toPhone) {
        Date currentDate = new Date();
        try {
            Date startDate = DateUtil.castString2Date("2017-01-24 00:00:00", "yyyy-MM-dd HH:mm:ss");
            Date endDate = DateUtil.castString2Date("2017-02-2 23:59:59", "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtil.castString2Date("2017-02-3 23:59", "yyyy-MM-dd HH:mm");

            if(currentDate.before(endDate) && currentDate.after(startDate)){
                LOGGER.info("[春节商机]商机创建时间在春节范围内，发送提示短信并延期商机截止处理时间,商机id：" + business.getBusinessBid());
                //修改截止处理时间
                business.setEndTime(endTime);
                //发送短信
                if(!StringUtil.isPhoneNum(toPhone)){
                    LOGGER.info("[春节商机]发送春节商机提示短信失败，客户电话不是手机号："+toPhone);
                    return;
                }
                //亲爱的自如客，新年好！我们已收到您的预约信息，ZO管家承诺在2月3日上班后与您取得联系。期待年后与您在自如寓相遇，并为您提供更好的服务。春节快乐，愿你自如。
                String chunjieMsg = PropUtils.getString(ZraApiConst.SMS_CHUNJIE_MSG_CONTENT);
                SmsUtils.INSTANCE.sendSMS(chunjieMsg, toPhone);
            }

        } catch (ParseException e) {
            LOGGER.error("春节商机处理 时间转换错误");
        }
    }

    /**
     * 查询未完结商机数量.
     * 根据用户手机号、预约项目ID、房型ID查询未完结商机数据
     * @param projectId 项目ID
     * @param houseTypeId 房型ID
     * @param phone  客户手机号
     * @return true:已经存在商机 ; false:不存在商机
     */
    public boolean hasBuss(String projectId, String houseTypeId, String phone) {
        int size = businessService.queryByYkInfo(projectId, houseTypeId, phone);
        return size > 0;
    }

    /**
     * 
     * M站预约看房
     * 之所以增加保存预约看房方法是因为M站预约参数比客户端少传expectInStartTime参数,后期可能增加重复预约校验逻辑
     *
     * @author liujun
     * @created 2016年8月16日
     *
     * @param dto
     * @return
     */
    public BusinessApplyReturnDto insertBusinessApplyFromMsite(BusinessOrderDto dto) {
        String customerBid = KeyGenUtils.genKey();//约看客户的bid
        //客户
        CustomerEntity ce = new CustomerEntity();
        ce.setCusBid(customerBid);
        ce.setCusUuid(dto.getUuid());
        ce.setName(dto.getName());
        ce.setPhone(dto.getPhone());
        ce.setCompany(dto.getCompany());
        ce.setNationality(dto.getNationality());

        //商机
        BusinessEntity be = new BusinessEntity();
        be.setBusinessBid(KeyGenUtils.genKey());
        be.setCityId(projectLogic.getCityIdByProjectId(dto.getProjectId()));
        be.setCustomerId(customerBid);
        be.setMessage(dto.getMessage());
        be.setProjectId(dto.getProjectId());
        be.setHouseTypeId(dto.getHtId());
        be.setSource(BoSourceType.ONLINE.getIndex());//M站 算是在线预约
        be.setComeSource(dto.getComeSource());  //  add by xiaona 2016年11月1日  记录M站预约的渠道来源
        String sourceZra = mkChannelLogic.getChannelBidByContent(dto.getComeSource(), projectLogic.getCityIdByProjectId(dto.getProjectId()));
        be.setSourceZra(sourceZra);
        try {
            be.setExpectTime(DateUtil.castString2Date(dto.getExpectTime(), "yyyy-MM-dd HH:mm"));
        } catch (ParseException e) {
            LOGGER.error("[预约看房]约看时间出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_EXPECTTIME_FAIL);
        }
        Date DYKEndTime = null;
        try {
            DYKEndTime = getDYKEndTime();
            be.setEndTime(DYKEndTime);
        } catch (ParseException e) {
            LOGGER.error("[预约看房]计算约看截止日期出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
        }
        Byte handState = null;
        try {
            handState = DateUtil.getDealStatusByEndTime(DYKEndTime);
            be.setHandState(handState);
        } catch (ParseException e) {
            LOGGER.error("[预约看房]判断处理状态出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
        }
        //判断分配给那个ZO
        EmployeeEntity ee = distributeZO(dto.getProjectId(), handState);
        be.setZoId(ee.getId());
        be.setZoName(ee.getName());

        /*春节期间商机特殊处理 wangws21 2017-1-13*/
        this.businessInChunjie(be, ce.getPhone());
        //保存商机和客户
        businessService.insertBusinessApply(ce, be);
        BusinessApplyReturnDto bd = new BusinessApplyReturnDto();
        bd.setMessage(PropUtils.getString(ZraApiConst.APP_BUSINESS_APPLY_RETURN));

        //发送预约提醒短信
        this.sendYytzMsg(be.getBusinessBid());

        /*保存操作记录*/
        LogRecordDto logRecord = new LogRecordDto(BussSystemEnums.ZRA.getKey(), "business", be.getBusinessBid(), "系统", "新建商机");
        LOGGER.info("logRecord.toString():" + logRecord.getSystemId() + "," + logRecord.getOperModId() + "," + logRecord.getOperObjId() + "," + logRecord.getOperator() + "," + logRecord.getLoginfo());
        boolean flag = logLogic.saveLog(logRecord);
        LOGGER.info("save info flag:" + flag);

        return bd;
    }

    /**
     * 获取待约看的截止处理日期
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    public Date getDYKEndTime() throws ParseException {
        String dykMinutes = configClient.get(ZraApiConst.DYK_MINUTES, ZraApiConst.CONS_SYSTEMID_ZRA);
        int waitMinute = Integer.parseInt(dykMinutes);
        long waitMilliSeconds = 60L * waitMinute * 1000;
        SimpleDateFormat sdfNo = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowDate = new Date();
        String nowStr = sdfNo.format(nowDate);
        String workStartStr = nowStr + " 09:00:00";
        String workEndStr = nowStr + " 18:00:00";
        Date workStartDate = sdf.parse(workStartStr);
        Date workEndDate = sdf.parse(workEndStr);
        if (nowDate.before(workStartDate)) {//创建时间在00:00～8:59，默认时间为9:00＋60分钟
            return new Date(workStartDate.getTime() + waitMilliSeconds);
        } else if (!nowDate.after(workEndDate)) {//商机创建时间若在9:00～18:00，默认增加60分钟（参数可配置）
            return new Date(nowDate.getTime() + waitMilliSeconds);
        } else {//创建时间在18:01～23:59，默认在第二天9:00＋60分钟
            return new Date(workStartDate.getTime() + waitMilliSeconds + 24 * 60 * 60 * 1000);
        }
    }

    /**
     * 分配ZO
     *
     * @Author: wangxm113
     * @CreateDate: 2016-08-03
     */
    private EmployeeEntity distributeZO(String projectId, Byte handState) {
        EmployeeEntity result = new EmployeeEntity();
        int week = 0;
        if (handState == 2) {//新建商机时处理状态只能2：今日待办；3：次日待办
            week = DateUtil.getWeekByDate(new Date());
        } else if (handState == 3) {
            week = DateUtil.getWeekByDate(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
        } else {
            LOGGER.info("[预约看房]新建商机时处理状态出错,处理状态为:" + handState);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_HANDSTATE_FAIL);
        }

        List<EmployeeEntity> list = businessService.getLeastZOOfBusiness(projectId, week, handState);
        if (list == null) {
            LOGGER.info("[预约看房]项目:" + projectId + "、周" + week + "，查询不到排班的ZO");
            throw new ResultException(ErrorEnum.MSG_BUSINESS_NOSHEDUALZO_FAIL);
        }
        if (handState == 3) {//次日待办
            result = list.get(0);
        } else {//今日待办，还需要加上今天处理过的
            List<EmployeeEntity> dealList = businessService.getTodayDealCount(projectId, week);
            for (EmployeeEntity deal : dealList) {
                for (EmployeeEntity e : list) {
                    if (deal.getId().equals(e.getId())) {
                        e.setCsrType(e.getCsrType() + deal.getCsrType());
                        break;
                    }
                }
            }

            int count = Integer.MAX_VALUE;
            for (EmployeeEntity e : list) {
                if(count > e.getCsrType()){
                    count = e.getCsrType();
                    result = e;
                }
            }
        }

//        //如果用一个sql来解决，当查不出数据即当日没有分配任何商机时，还需要重新拿一次ZO列表，所以，分两步
//        //获取指定项目指定周几的ZO列表
//        List<EmployeeEntity> list = businessService.getZOByProIdAndWeek(projectId, week);
//        if (list.size() <= 0) {//理论上每天都会有管家的排班
//            LOGGER.info("[预约看房]项目:" + projectId + "、周" + handState + "，查询不到排班的ZO");
//            throw new ResultException(ErrorEnum.MSG_BUSINESS_NOSHEDUALZO_FAIL);
//        }
//        Collections.shuffle(list);
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            sb.append("'").append(list.get(i).getId()).append("',");
//        }
//        sb = sb.deleteCharAt(sb.length() - 1);
//        //根据管家列表，查出其中当日（也可能是次日）分配商机数最少的管家
//        List<EmployeeEntity> employeeList = businessService.getLeastBusinessZO(sb.toString(), handState);
//        if (employeeList.isEmpty()) {//即没有做过任何分配，此时随机一个管家，否则则分配给这个最少的管家
//            result = list.get(0);
//        } else if (list.size() == employeeList.size()) {
//            result = employeeList.get(0);
//        } else {
//            Map<String, Integer> empIdMap = new HashMap<>();
//            for (EmployeeEntity e : employeeList) {
//                empIdMap.put(e.getId(), 1);
//            }
//
//            for (EmployeeEntity e : list) {
//                Integer i = empIdMap.get(e.getId());
//                if (i == null) {
//                    result = e;
//                    break;
//                }
//            }
//        }
        return result;
    }

    /**
     * 获取商机  用于商机处理
     * @author wangws21 2016-8-4
     * @param businessBid 商机bid
     * @return BusinessSaveDto
     */
    public BusinessFullDto getBusinessDetail(String businessBid) {
        return this.businessService.getBusinessDetail(businessBid);
    }
    
    
    /**
     * 获得最近一次商机
     * @author tianxf9
     * @param projectId
     * @param customerNumber
     * @return
     */
    public List<BusinessEntity> getNearestBusinessEntity(String projectId,String customerNumber) {

        return this.businessService.getNearestBusiness(projectId,customerNumber);
    }
    
    /**
     * 更新商机
     * @author tianxf9
     * @param entity
     * @return
     */
    public int updateBusinessEntity(BusinessEntity entity) {
        return this.businessService.updateBusinessEntity(entity);
    }
    
    
    /**
     * 保存商机
     * @author tianxf9
     * @param entity
     * @return
     */
    public boolean saveBusiness(BusinessEntity entity) {
        return this.businessService.save(entity);
    }

    /**
     * 处理商机 更新商机、客户信息  保存商机处理结果
     * wangws21 2016-8-6
     * @param businessFullDto 商机实体
     * @return true/false
     */
    public boolean updateBusinessFull(BusinessFullDto businessFullDto) {
        //传进来的商机完整dto
        BusinessDto businessDto =  businessFullDto.getBusiness();
        CustomerDto customerDto = businessFullDto.getCustomer();
        BusinessResultDto businessResultDto = businessFullDto.getBusinessResult();

        //获取三个基本信息   管家id 管家姓名  城市id
        String euid = "", ename = "",ePhone="", cityId = businessFullDto.getCityId();
        EmployeeEntity emp = employeeLogic.getEmployeeByUserId(businessFullDto.getUserId());
        if(emp!=null){
            euid=emp.getId();
            ename = emp.getName();
            ePhone = emp.getMobile();
        }

        BusinessEntity preBusiness = this.businessService.getBusinessByBid(businessDto.getBusinessBid());
        if(preBusiness==null){
            throw new ResultException(ErrorEnum.MSG_BUSINESS_GET_FAIL);
        }
        /*商机阶段已经变更  说明有人已经处理了该商机 */
        if(preBusiness.getStep()!=businessDto.getStep()){
            throw new ResultException(ErrorEnum.MSG_BUSINESS_ALREADY_HANDED);
        }

        byte step = this.getBusinessStepFromResult(businessResultDto.getHandResult(),businessDto.getBusinessBid()); //商机处理阶段
        Date endTime = null;  // 截止时间
        try {
            endTime = this.getEndTimeByStep(step,businessResultDto.getHandResult(), businessResultDto.getHandResultTime());
        } catch (ParseException e) {
            LOGGER.error("[处理商机]计算约看截止日期出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
        }

        //
        Byte handState = null;
        try {
            if(endTime==null){
                handState = 6; //完成
            }else{
                handState = DateUtil.getDealStatusByEndTime(endTime);
            }
        } catch (ParseException e) {
            LOGGER.error("[处理商机]判断处理状态出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
        }

        /*保存的实体*/
        BusinessEntity business = new BusinessEntity(businessDto.getBusinessBid(), euid, new Date(), cityId, customerDto.getCusBid(),
                step, endTime, handState, businessDto.getHouseTypeId(), businessDto.getExpectInStartTime(),businessDto.getExpectInEndTime(),
                businessDto.getExpectLowPrice(), businessDto.getExpectHighPrice(), businessDto.getType(), businessDto.getRemark(), null, null);

        CustomerEntity customer = new CustomerEntity(customerDto.getCusBid(), null, new Date(), customerDto.getName(), customerDto.getPhone(),
                customerDto.getGender(), customerDto.getAge(), customerDto.getCompany(), customerDto.getAddress());

        /*根据未更新前的商机最后处理时间判断处理状态*/
        try {
            handState = preBusiness.getEndTime()==null ? 6: DateUtil.getDealStatusByEndTime(preBusiness.getEndTime());
        } catch (ParseException e) {
            LOGGER.error("[处理商机]判断处理状态出错", e);
            throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
        }
        //是否发送约看提醒
        Byte isSendSMS = this.checkIsSendYKSms(businessResultDto.getHandResult(), businessResultDto.getHandResultTime(),businessDto.getBusinessBid());
        //是否评价短信
        //Byte isEvaluateSms = businessResultDto.getHandResult()==BoHandResult.YK_QR_YK.getIndex() || businessResultDto.getHandResult()==BoHandResult.DK_TC_SJ.getIndex()?(byte)1:(byte)0;
        // 商机处理结果   商机处理结果的商机阶段为老商机阶段
        String resultBid = KeyGenUtils.genKey();
        BusinessResultEntity businessResult = new BusinessResultEntity(resultBid, business.getBusinessBid(), euid, cityId, businessDto.getStep(),
                handState, preBusiness.getEndTime(), businessResultDto.getHandResult(), businessResultDto.getHandResultTime(),
                businessResultDto.getHandResultContent(), null, euid, isSendSMS,(byte)0);

        boolean result = businessService.updateFullBusinessEntitys(business, customer, businessResult);
        //记录操作日志
        this.addBusinessLog(businessResult,businessResultDto.getRecord(),ename);
        //发送短信    给商机分配的管家发送
        String zoName="",zoPhone="";
        if(StrUtils.isNotNullOrBlank(preBusiness.getZoId())){
            EmployeeEntity zo = employeeLogic.getEmployeeById(preBusiness.getZoId());
            if(zo!=null){
                zoName = zo.getName();
                zoPhone = zo.getMobile();
            }
        }
        boolean isSMS = this.sendSMS(businessResultDto.getHandResult(), businessResultDto.getHandResultTime(), 
        		customer.getPhone(), preBusiness.getProjectId(), zoName, zoPhone,businessResultDto.getBusinessId(),businessResultDto.getCloseMsgDescContent());
        this.sendLXBSSMS(step,businessResultDto.getHandResult(), zoName, zoPhone, customer.getPhone()); //联系不上短信
        this.sendEvaluateSms(businessResultDto.getHandResult(), businessResultDto.getHandResultContent(), preBusiness.getId(),
                preBusiness.getProjectId(), preBusiness.getZoId(), customerDto.getCusUuid(), customerDto.getPhone(),customerDto.getName()); //发送评价短信、推送
        return result;
    }

    /**
     * 发送联系不上短信
     * <br>第一次联系不上发送短信;第二次联系不上直接关闭商机 不发送短信
     * @param step 商机阶段
     * @param handResult 商机处理结果
     * @param zoPhone 关机手机号
     */
    private void sendLXBSSMS(Byte step, Byte handResult, String zoName, String zoPhone,String toPhone) {
        try {

            //第一次联系不上  商机阶段为待约看  并且 商机处理结果为 暂时联系不上用户，待继续跟进
            if(step == BoBusinessStep.D_YK.getIndex() && handResult==BoHandResult.YK_DGJ.getIndex()){

                if(!StringUtil.isPhoneNum(toPhone)){
                    LOGGER.info("[处理商机]发送联系不上提示短信，客户电话不是手机号："+toPhone);
                    return;
                }

                String zoPhoneMsg = "";
                String zoSMSBodyMsg = PropUtils.getString(ZraApiConst.SMS_LXBS_MSG_CONTENT); //亲爱的用户，您好，由于管家无法电话联系到您，暂时无法确认您的约看行程，管家会在24小时内再次给您致电，请保证手机畅通。{1}祝您生活自如。
                if(StrUtils.isNotNullOrBlank(zoName) && StrUtils.isNotNullOrBlank(zoPhone)){
                    zoPhoneMsg = PropUtils.getString(ZraApiConst.SMS_LXBS_MSG_PHONE); //您也可以直接联系您的管家{1}，电话：{2}，
                    zoPhoneMsg = zoPhoneMsg.replace("{1}", zoName);
                    zoPhoneMsg = zoPhoneMsg.replace("{2}", zoPhone);
                }
                zoSMSBodyMsg = zoSMSBodyMsg.replace("{1}", zoPhoneMsg);

                boolean isSend = SmsUtils.INSTANCE.sendSMS(zoSMSBodyMsg, toPhone);
                if(!isSend){
                    LOGGER.error("[处理商机]发送联系不上提示短信失败:"+zoSMSBodyMsg+"手机号："+toPhone);
                }
            }

        } catch (Exception e) {
            LOGGER.error("[处理商机]发送联系不上提示短信失败，失败原因", e);
        }
    }


    /**
     * 在待带看阶段，提交处理结果为“已带看，待进一步跟进回访”，“已签约，完成商机”，“关闭商机”且关闭理由非“用户个人原因取消行程”.
     * wangws21 2016-9-30
     * 
     * @param handResult 处理结果
     * @param handResultContent 处理结果内容，主要是关闭原因
     * @param businessId 商机id
     * @param projectId 项目id
     * @param zoId 管家id
     * @param cusUuid 客户uuid
     * @param cusPhone 客户手机号
     */
    public void sendEvaluateSms(Byte handResult,String handResultContent, Integer businessId,String projectId, 
    		String zoId,String cusUuid, String cusPhone, String cusName) {
        try{
            /*if(BoHandResult.DK_DHF.getIndex() == handResult ||
                    BoHandResult.DK_YQY.getIndex() == handResult ||
                    (BoHandResult.DK_GB_SJ.getIndex() == handResult && !handResultContent.equals(BoCloseReason.DK_GB_5.getIndex().toString())) ){
                
                LOGGER.info("[评价推送]本次处理的商机Id："+businessId+" 时间："+new Date());
                String smsContentTep = PropUtils.getString(ZraApiConst.SMS_DKPJ_MSG);
                String title = PropUtils.getString(ZraApiConst.YYTZ_PUSH_TITLE);
                String pushContentTep = PropUtils.getString(ZraApiConst.YYTZ_PUSH_MSG);
                String shortUrl = PropUtils.getString(ZraApiConst.ZOEVA_SHORT_URL);
                
                boolean isSendZOEVASms = Boolean.valueOf(PropUtils.getString(ZraApiConst.ZOEVA_SMS_OPEN));
                boolean isSendZOEVAPush = Boolean.valueOf(PropUtils.getString(ZraApiConst.ZOEVA_PUSH_OPEN));
                
                if(StrUtils.isNotNullOrBlank(zoId)){
                    //http://10.16.24.74:8081/zra_mst/zoeva/zoeva.action?beEvaluatorId=9000073834120133123&businessBid=3a7fb204-e9c7-4730-b77d-16ea03b8c7af
                    //1. 推送来源     &from=push    2. 短信来源     &from=sms
                    StringBuilder longUrl = new StringBuilder();
                    longUrl.append(PropUtils.getString(ZraApiConst.ZOEVA_LONG_URL))
                    .append("?beEvaluatorId=").append(zoId)
                    .append("&businessBid=").append(businessId);
                    //TODO 这应该传bid  客户端不支持String  只能取id
                    //.append("&businessBid=").append(bes.getBusinessBid());
                    *//*手机号有效的话发送短信*//*
                    if(isSendZOEVASms){
                        if(StrUtils.isNotNullOrBlank(cusPhone) && StringUtil.isPhoneNum(cusPhone)){
                            cusName = URLEncoder.encode(cusName, "UTF-8"); //中文转码  m站接受时在转回来
                            String smsLongUrl = longUrl+"&from=sms&requesterPhone="+cusPhone+"&requesterName="+cusName+"&requesterType=message";
                            CfShortUrl cfShortUrl = this.shortUrlLogic.genShortUrlSuff(smsLongUrl.toString());
                            String smsContent = smsContentTep;
                            smsContent = smsContent.replace("{1}", shortUrl+cfShortUrl.getSuid());
                            
                            LOGGER.info("[评价推送]手机号："+cusPhone+"\t短信内容："+smsContent+"businessid="+businessId);
                            SmsUtils.INSTANCE.sendSMS(smsContent, cusPhone);
                        }else{
                            LOGGER.info("[评价推送]本次处理的商机："+businessId+" 客户电话不是手机号：" + cusPhone);
                        }
                    }else{
                        LOGGER.info("[评价推送]带看评价短信未开启");
                    }
                    
                    *//*uuid存在的进行推送*//*
                    if(isSendZOEVAPush && StrUtils.isNotNullOrBlank(cusUuid)){
                        String pushContent = pushContentTep;
                        String projectName = this.projectLogic.getProjectById(projectId).getName();
                        pushContent = pushContent.replace("{1}", projectName);
                        String pushLongUrl = longUrl.toString()+"&from=push";
                        CfShortUrl cfShortUrl = this.shortUrlLogic.genShortUrlSuff(pushLongUrl);
                        PushDto pushDto = new PushDto(BussSystemEnums.ZRA.getKey(), "YYTZ", title, pushContent, shortUrl+cfShortUrl.getSuid(), cusUuid);
                        LOGGER.info("[评价推送]uuid:"+cusUuid+"\t推送内容"+JSON.toJSONString(pushDto));
                        pushLogic.push(pushDto);
                    }else{
                        LOGGER.info("[评价推送]评价未开启："+isSendZOEVAPush+"或者用户uuid为空："+cusUuid);
                    }
                }else{
                    LOGGER.info("[评价推送]本次处理的商机Id："+businessId+"，推送失败,管家为空");
                }
            } else*/
            LOGGER.info("[评价推送]本次处理的商机Id：" + businessId + " 时间：" + new Date());
            boolean isSendZOEVAPush = Boolean.valueOf(PropUtils.getString(ZraApiConst.ZOEVA_PUSH_OPEN));
            String title = PropUtils.getString(ZraApiConst.YYTZ_PUSH_TITLE);
            if (BoHandResult.YK_QR_YK.getIndex() == handResult) {//约看确认
                String pushContent = PropUtils.getString(ZraApiConst.YKQR_PUSH_MSG);
                String shortUrl = PropUtils.getString(ZraApiConst.ZOEVA_SHORT_URL);
                if (StrUtils.isNotNullOrBlank(zoId)) {
                    if (isSendZOEVAPush && StrUtils.isNotNullOrBlank(cusUuid)) {
                        StringBuilder longUrl = new StringBuilder();
                        longUrl.append(PropUtils.getString(ZraApiConst.YKQR_LONG_URL))
                                .append("?uid=").append(cusUuid)
                                .append("&state=").append(0);
                        String pushLongUrl = longUrl.toString() + "&from=push";
                        CfShortUrl cfShortUrl = this.shortUrlLogic.genShortUrlSuff(pushLongUrl);
                        PushDto pushDto = new PushDto(BussSystemEnums.ZRA.getKey(), "1", title, pushContent, shortUrl + cfShortUrl.getSuid(), cusUuid);
                        LOGGER.info("[已确认约看推送]uuid:" + cusUuid + "\t推送内容" + JSON.toJSONString(pushDto));
                        pushLogic.push(pushDto);
                    } else {
                        LOGGER.info("[已确认约看推送]商机Id:" + businessId + ";未开启：" + isSendZOEVAPush + "或者用户uuid为空：" + cusUuid);
                    }
                } else {
                    LOGGER.info("[已确认约看推送]本次处理的商机Id：" + businessId + "，推送失败,管家为空");
                }
            } else if (BoHandResult.DK_DHF.getIndex() == handResult ||
                    BoHandResult.DK_YQY.getIndex() == handResult ||
                    (BoHandResult.DK_GB_SJ.getIndex() == handResult && !handResultContent.equals(BoCloseReason.DK_GB_5.getIndex().toString()))) {
                String pushContent = PropUtils.getString(ZraApiConst.DKWC_PUSH_MSG);
                String shortUrl = PropUtils.getString(ZraApiConst.ZOEVA_SHORT_URL);
                if(StrUtils.isNotNullOrBlank(zoId)){
                    if(isSendZOEVAPush && StrUtils.isNotNullOrBlank(cusUuid)){
                        StringBuilder longUrl = new StringBuilder();
                        longUrl.append(PropUtils.getString(ZraApiConst.YKQR_LONG_URL))
                                .append("?uid=").append(cusUuid)
                                .append("&state=").append(1);
                        String pushLongUrl = longUrl.toString()+"&from=push";
                        CfShortUrl cfShortUrl = this.shortUrlLogic.genShortUrlSuff(pushLongUrl);
                        PushDto pushDto = new PushDto(BussSystemEnums.ZRA.getKey(), "2", title, pushContent, shortUrl+cfShortUrl.getSuid(), cusUuid);
                        LOGGER.info("[已带看完成推送]uuid:"+cusUuid+"\t推送内容"+JSON.toJSONString(pushDto));
                        pushLogic.push(pushDto);
                    }else{
                        LOGGER.info("[已带看完成推送]评价未开启："+isSendZOEVAPush+"或者用户uuid为空："+cusUuid);
                    }
                }else{
                    LOGGER.info("[评价推送]本次处理的商机Id："+businessId+"，推送失败,管家为空");
                }
            } 
        } catch (Exception e){
            LOGGER.info("[评价推送]异常错误："+e+",商机ID："+businessId);
        }
        
    }

    /**
     * wangws21 2016-8-12
     * 判断是否需要进行约看提醒         <br>
     * 商机创建时间距离确认约看时间超过24h 需要进行约看时间提醒
     * @param handResult 处理结果
     * @param handResultTime 约看时间
     * @return 0否   1是
     */
    private Byte checkIsSendYKSms(Byte handResult, Date handResultTime,String businessBid) {
        if(handResult == BoHandResult.YK_QR_YK.getIndex()||    //各处理结果  确认约看发送短信     推迟约看发送短信
                handResult == BoHandResult.DK_TC_SJ.getIndex()||
                handResult == BoHandResult.XJ_QR_YK.getIndex()){
            //用户更改带看时间  修改上一个处理结果 短信发送状态为0
            if(handResult == BoHandResult.DK_TC_SJ.getIndex() && StrUtils.isNotNullOrBlank(businessBid)){
                BusinessResultEntity lastBusinessResult = this.businessService.getLastBusinessResult(businessBid);
                BusinessResultEntity businessResult = new BusinessResultEntity(lastBusinessResult.getResultBid(), (byte)0,  (byte)0);
                this.businessService.updateBusinessResultEntity(businessResult);
            }

            Date now = new Date();
            long oneDayMilliSeconds = 24L * 60 * 60 * 1000;
            if(handResultTime.getTime()-now.getTime()>oneDayMilliSeconds){
                return (byte)1;
            }
        }
        return (byte)0;
    }

    /**
     * wangws21 2016-8-10
     * 发送约看确认短信（包括约看时间变更短信）,关闭短信
     * @param handResult 处理结果
     * @param resultTime 约看时间
     * @param toPhone    客户手机号
     * @param projectId  项目id
     * @param zoName       管家姓名
     * @param zoPhone     管家手机号
     * @return true/false
     */
	private boolean sendSMS(Byte handResult, Date resultTime, String toPhone, String projectId, String zoName,
			String zoPhone,String businessId,Integer smsContent) {

		try {

			if (!StringUtil.isPhoneNum(toPhone)) {
				LOGGER.info("[处理商机]发送约看短信，客户电话不是手机号：" + toPhone);
				return true;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String smsContet = "";
			if (handResult == BoHandResult.YK_QR_YK.getIndex() || // 各处理结果
																	// 确认约看发送短信
																	// 推迟约看发送短信
					handResult == BoHandResult.XJ_QR_YK.getIndex() ||

					handResult == BoHandResult.DK_TC_SJ.getIndex()) { // 推送约看确认短信

				ProjectDto project = projectLogic.getProjectById(projectId);
				if (project == null) {
					LOGGER.info("[处理商机]发送约看短信，项目错误projectId：" + projectId);
					return false;
				}

				// 拼接 ，您的服务管家：［zo姓名］，联系电话：［zo电话］
				String zoNameMsg = PropUtils.getString(ZraApiConst.SMS_ZO_NAME_MSG); // ，您的服务管家：
				String zoPhoneMsg = PropUtils.getString(ZraApiConst.SMS_ZO_PHONE_MSG); // ，联系电话：
				StringBuilder zoMsg = new StringBuilder();
				if (StrUtils.isNotNullOrBlank(zoName)) {
					zoMsg.append(zoNameMsg).append(zoName);
				}
				if (StrUtils.isNotNullOrBlank(zoPhone) && zoPhone.length() >= 11) {
					if (zoPhone.startsWith("0"))
						zoPhone = zoPhone.substring(1);
					if (StringUtil.isPhoneNum(zoPhone)) {
						zoPhoneMsg = zoPhoneMsg.replace("{1}", zoPhone);
						zoMsg.append(zoPhoneMsg);
					} else {
						LOGGER.info("[处理商机]管家手机号不正确，管家：" + zoName + ",手机号:" + zoPhone);
					}
				} else {
					LOGGER.info("[处理商机]管家手机号不正确，管家：" + zoName + ",手机号:" + zoPhone);
				}

				// 推送约看时间变更短信
				if (handResult == BoHandResult.DK_TC_SJ.getIndex()) {
					smsContet = PropUtils.getString(ZraApiConst.SMS_YKSJBG_MSG);// 亲爱的用户，您自如寓约看行程时间变更为{1}，我们期待您的到访。约看公寓：{2}，公寓地址：{3}{4}，有任何问题请随时与我们联系，祝您生活愉快。
					smsContet = smsContet.replace("{1}", sdf.format(resultTime));
					smsContet = smsContet.replace("{2}", project.getName());
					smsContet = smsContet.replace("{3}", project.getAddress());
					smsContet = smsContet.replace("{4}", zoMsg.toString());
				} else { // 确认约看发送短信
					smsContet = PropUtils.getString(ZraApiConst.SMS_YKQR_MSG);// 亲爱的用户，您的自如寓约看行程时间已确认。约看公寓：{1}，公寓地址：{2}，约看时间：{3}，{4}，有任何问题请随时和我们联系，祝您生活愉快。
					smsContet = smsContet.replace("{1}", project.getName());
					smsContet = smsContet.replace("{2}", project.getAddress());
					smsContet = smsContet.replace("{3}", sdf.format(resultTime));
					smsContet = smsContet.replace("{4}", zoMsg.toString());
				}

				// 约看时间要在当前时间之后 才进行短信发送
				if (resultTime.after(new Date())) {
					return SmsUtils.INSTANCE.sendSMS(smsContet, toPhone);
				}
			//add by tianxf9 关闭发送短信	
			}else if(BoHandResult.XJ_GB_SJ.getIndex()==handResult||
            		BoHandResult.YK_QX_GB.getIndex()==handResult||
            		BoHandResult.YK_GB_SJ.getIndex()==handResult||
            		BoHandResult.YK_GB_RS.getIndex()==handResult||
            		BoHandResult.DK_GB_SJ.getIndex()==handResult||
            		BoHandResult.HF_GB_SJ.getIndex()==handResult) {
            	if(smsContent!=null) {
            		BoCloseSMSContent smsContentDto = this.boCloseSmsService.getSmsContentById(smsContent);
            		boolean isSendCloseSms = smsContentDto.getIsSend().intValue()==0?false:true;
                    if(isSendCloseSms && StrUtils.isNotNullOrBlank(toPhone)) {
                        SmsUtils.INSTANCE.sendSMS(smsContentDto.getContent(), toPhone);   
                    } else {
                        LOGGER.info("[关闭商机推送短信]商机Id:" + businessId + ";未开启：" + isSendCloseSms + "或者客户手机号为空：" + toPhone);
                    }
            	}else {
            		LOGGER.info("[关闭商机推送短信]商机Id:" + businessId + "推送短信模板为空！");
            	}
            }

		} catch (Exception e) {
			LOGGER.error("[处理商机]发送短信失败，失败原因", e);
		}
		return false;
	}

    /**
     * wangws21 2016-8-6
     * 截止处理时间：该字段取值和商机阶段关联，不同商机阶段，取值策略不同
     * @param step 商机处理阶段
     * @param baseDate 基准时间， 处理结果输入的时间
     * @return 截止时间
     * @throws ParseException
     */
    private Date getEndTimeByStep(byte step,byte handResult, Date baseDate) throws ParseException {
        if(BoBusinessStep.D_YK.getIndex()==step){

			//暂时联系不上用户，待继续跟进   截止处理时间取值为当前系统时间＋1天
			if(handResult==BoHandResult.YK_DGJ.getIndex()){
				CAL.setTime(new Date());
				CAL.add(Calendar.DATE, 1);
				return CAL.getTime();
			}else if(handResult==BoHandResult.YK_DGJ_YK.getIndex()){   //add by xiaona  2016年10月18日  新增处理结果的状态值
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endDateStr = sdf1.format(baseDate)+" 23:59:59";
				return sdf2.parse(endDateStr);
			}else{
				return this.getDYKEndTime();
			}
		}
		if(BoBusinessStep.D_DK.getIndex()==step || BoBusinessStep.D_HF.getIndex()==step){
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String endDateStr = sdf1.format(baseDate)+" 23:59:59";
			return sdf2.parse(endDateStr);
		}
		return null;
	}

	/**
	 * wangws21 2016-8-8
	 * 获取商机处理历史结果
	 * @param businessBid 商机bid
	 * @return List<BusinessResultDto>
	 */
	public List<BusinessResultDto> getBusinessResultList(String businessBid) {
		return this.businessService.getBusinessResultList(businessBid);
	}
	

	/**
	 * wangws21 2016-8-10
	 * 记录操作日志和跟进记录
	 * @param businessResult 商机处理结果
	 * @param record 跟进记录
	 */
	private void addBusinessLog(BusinessResultEntity businessResult, String record, String zoName) {
		
		try {
			Byte handResult = businessResult.getHandResult();
			StringBuilder sb = new StringBuilder();
			sb.append("处理结果：");
			sb.append(BoHandResult.getEnum2map().get(handResult));
			
			String resultTime = ""; 
			if(businessResult!=null && businessResult.getHandResultTime()!=null){
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
				try {
					resultTime = sdf1.format(businessResult.getHandResultTime())+"&nbsp;"+ sdf2.format(businessResult.getHandResultTime());
				} catch (Exception e) {
					LOGGER.error("[处理商机]添加商机操作日志时间处理失败", e);
				}
			}
			switch (handResult) {
				case 13:  //确认约看
				case 25:  //确认约看
					sb.append("，约看时间："+resultTime);
					break;
					
				case 33:  //用户更改带看时间
					sb.append("，新约带看时间："+resultTime);
					break;
					
				case 12:  //已带看，待进一步跟进回访
				case 32:  //已带看，待进一步跟进回访
				case 26:  //已约看，待进一步跟进回访   新增的待沟通选项  add by xiaona  2016年10月19日
					sb.append("，回访时间："+resultTime);
					break;
					
				case 42:  //已带看，待进一步跟进回访
					sb.append("，再次跟进时间："+resultTime);
					break;
					
				case 11:  //已签约，完成商机
				case 31:  //已签约，完成商机
				case 41:  //已签约，完成商机
					sb.append("，签约合同号："+ businessResult.getHandResultContent());
					break;
					
				case 10:  //关闭商机
				case 30:  //关闭商机
				case 40:  //关闭商机
					sb.append("，未签约原因：");
					if(BoCloseReason.getEnum2stringmap().containsKey(businessResult.getHandResultContent())){
						sb.append(BoCloseReason.getEnum2stringmap().get(businessResult.getHandResultContent()));
					}else{
						sb.append(businessResult.getHandResultContent());
					}
					break;
			}
			
			/*保存操作记录*/
			LogRecordDto logRecord = new LogRecordDto(BussSystemEnums.ZRA.getKey(), "business", businessResult.getBusinessId(), zoName, sb.toString());
			LOGGER.info("logRecord.toString():"+logRecord.getSystemId()+","+logRecord.getOperModId()+","+logRecord.getOperObjId()+","+logRecord.getOperator()+","+logRecord.getLoginfo());
			boolean flag = logLogic.saveLog(logRecord);
			LOGGER.info("save info flag:"+flag);
			/*保存跟进记录*/
			if(StrUtils.isNotNullOrBlank(record)){
				LogRecordDto logRecord2 = new LogRecordDto(BussSystemEnums.ZRA.getKey(), "business", businessResult.getBusinessId(), zoName, "跟进记录："+record);
				LOGGER.info("跟进记录："+logRecord2.toString());
				logLogic.saveLog(logRecord2);
			}
		} catch (Exception e) {
			LOGGER.error("[处理商机]添加商机操作日志失败："+e);
		}
	}

//  /**
//   * 获取用户未完成约看列表
//   * @param uid
//   * @param state
//   * @return
//   */
//  public List<BusinessNoFinishListDto> businessUnfinishList(String uid) {
//      String picPrefix = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
//      List<BusinessNoFinishListDto> result = new ArrayList<>();
//      //查询除项目信息外的所有信息
//      List<BusinessListBusInfoEntity> businessEntityList = businessService.findUserBusinessList(uid, 1);
//      StringBuilder sb = new StringBuilder();
//      for (BusinessListBusInfoEntity entity : businessEntityList) {
//          BusinessNoFinishListDto dto = new BusinessNoFinishListDto();
//          dto.setOrderId(Long.valueOf(entity.getBusiId()));
//          dto.setKeeperName(entity.getZoName());
//          dto.setKeeperId(entity.getEmployeeId());
//          dto.setKeeperPhone(picPrefix + entity.getZoMobile());
//          dto.setKeeperHeadCorn(entity.getZoSmallImg());
//          dto.setAppointTime(entity.getExpectTime());
//          dto.setAppointAddr(entity.getProAddress());
//          dto.setHouseId(entity.getProjectId());
//          dto.setCreateTime(String.valueOf(entity.getCreateTime().getTime() / 1000));
//          dto.setSource("zra");
//          dto.setAppointStatusZra(entity.getBusiStep());
//          result.add(dto);
//          sb.append("'").append(entity.getProjectId()).append("',");
//      }
//
//      if (sb.length() > 0) {
//          sb = sb.deleteCharAt(sb.length() - 1);
//          List<BusinessListReturnDto> list = projectLogic.getProInfoForBusinessList(sb.toString());
//          for (BusinessListReturnDto e : list) {
//              for (BusinessNoFinishListDto dto : result) {
//                  if (e.getProId().equals(dto.getHouseId())) {
//                      dto.setVillageName(e.getProName());
//                      dto.setArea(CommonUtil.doubleToString(e.getMinArea()));
//                      dto.setRent(CommonUtil.doubleToString(e.getMinPrice()));
//                      dto.setHousePhoto(picPrefix + e.getProHeadImg());
//                  }
//              }
//          }
//      }
//      return result;
//  }

    /**
     * 获取用户约看列表
     * @param uid
     * @param state
     * @return
     */
    public List<BusinessListDto> businessList(String uid, Integer state) {
        String picPrefix = PropUtils.getString(ZraApiConst.PIC_PREFIX_URL);
        List<BusinessListDto> result = new ArrayList<>();
        //查询除项目信息外的所有信息
        List<BusinessListBusInfoEntity> businessEntityList = businessService.findUserBusinessList(uid, state);
        Map<Integer, BusinessListDto> busiIdMap = new HashMap<>();
        Set<String> proId = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder busiSb = new StringBuilder();
        for (BusinessListBusInfoEntity entity : businessEntityList) {
            BusinessListDto dto = new BusinessListDto();
            dto.setOrderId(Long.valueOf(entity.getBusiId()));
            dto.setKeeperName(entity.getZoName());
            dto.setKeeperId(entity.getEmployeeId());
            dto.setKeeperPhone(ZraApiConst.ZRA_TEL_PREFIX + entity.getZoMobile());
            dto.setAppointTime(entity.getExpectTime());
            dto.setAppointAddr(entity.getProAddress());
            dto.setHouseId(entity.getProjectId());
            dto.setCreateTime(String.valueOf(entity.getCreateTime().getTime() / 1000));
            dto.setIsEvaluate("0");//假设都能评价，即没有评价过
            dto.setSource("zra");
            dto.setAppointStatusZra(entity.getBusiStep());
            
            //modify by tianxf9  获取管家头像 sql有问题，改成根据projectId，employeeId获取；
            String smallImgUrl = this.projectZOLogic.getProjectZoSmallImg(entity.getProjectId(), entity.getEmployeeId());
            if(StringUtils.isNotBlank(smallImgUrl)) {
            	dto.setKeeperHeadCorn(picPrefix + smallImgUrl);
            }else {
            	 dto.setKeeperHeadCorn(null);
            }
            //end 
            //如果是查询已完成的约看，需要特别处理由用户取消约看的商机
            if (state == 1) {
                BusinessResultEntity lastBusinessResult = this.businessService.getLastBusinessResult(entity.getBusiBid());
                if (lastBusinessResult.getHandResult() == BoHandResult.WC_DHF.getIndex()) {//用户自主关闭商机
                    dto.setIsEvaluate("1");//不可评价
                    dto.setAppointStatusZra("已取消");
                }
            }

            result.add(dto);
            busiIdMap.put(entity.getBusiId(), dto);
            proId.add(entity.getProjectId());
            busiSb.append(entity.getBusiId().toString()).append(",");
        }

        for (String s : proId) {
            sb.append("'").append(s).append("',");
        }

        //项目信息
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
            List<BusinessListReturnDto> list = projectLogic.getProInfoForBusinessList(sb.toString());
            for (BusinessListReturnDto e : list) {
                for (BusinessListDto dto : result) {
                    if (e.getProId().equals(dto.getHouseId())) {
                        dto.setVillageName(e.getProName());
                        dto.setArea(e.getMinArea().toString());
                        dto.setRent(e.getMinPrice().toString());
                        dto.setRentUnit("元/月起");
                        dto.setHousePhoto(picPrefix + e.getProHeadImg());
                    }
                }
            }
        }

        //评价信息
        if (busiSb.length() > 0) {
            busiSb = busiSb.deleteCharAt(busiSb.length() - 1);
            List<EvaluateDto> list = evaluateLogic.ifEvaluate(busiSb.toString());
            for (EvaluateDto e : list) {
                BusinessListDto dto = busiIdMap.get(e.getBusinessBid());
                dto.setTokenId(e.getTokenId());
                dto.setIsEvaluate("1");
            }

        }
        return result;
    }

	/**
	 * 取消约看
	 * @param businessId
	 * @return
	 */
	public Boolean cancelBusiness(String businessId){
		//修改商机状态
		BusinessEntity business = businessService.getBusinessById(businessId);
		Byte step = business.getStep();
		if (step != 1) {
			throw new ResultException(ErrorEnum.MSG_BUSINESS_CANCEL_STEP_FAIL);
		}
		business.setUpdateTime(new Date());
		business.setStep(BoBusinessStep.W_CJ.getIndex());
		business.setHandState(BoHandState.DOWN.getIndex());

		/*add by xiaona 2016年10月19日  自主关闭商查机jiezhi处理时间为空*/
		business.setEndTime(null);

		//处理结果
		String resultBid = KeyGenUtils.genKey();
		BusinessResultEntity businessResult = new BusinessResultEntity(resultBid, business.getBusinessBid(), null,
				business.getCityId(), step, business.getHandState(), business.getEndTime(),
				BoHandResult.WC_DHF.getIndex(), new Date(),BoHandResult.WC_DHF.getValue(), null, null, (byte)0,(byte)0);

		boolean result = businessService.cancelBusiness(business, businessResult);
		//记录日志
		if (result){
			addBusinessLog(businessResult, null,"");
		}
		return result;
	}
	
	/**
	 * 根据商机bid跟新商机信息
	 *
	 * @author liujun
	 * @created 2016年8月11日
	 *
	 * @param dto
	 * @return
	 */
	public int updateBusinessEntityByBid(BoDistParamDto dto) {
		String euid = "",eName="";
		String userId = dto.getUpdaterId();
		EmployeeEntity emp = employeeLogic.getEmployeeByUserId(userId);
		if (emp != null) {
			euid = emp.getId();
			eName = emp.getName();
		}
		
		BusinessEntity business = this.businessService.getBusinessByBid(dto.getBusinessBid());
		Date endTime = null;
		try {
			endTime = this.getEndTime4Distribute(business);
		} catch (ParseException e) {
			LOGGER.error("[商机分配]计算约看截止日期出错", e);
			throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
		}
		
		Byte handState = null;
		try {
			if(endTime==null){
				handState = 6; //完成
			}else{
				handState = DateUtil.getDealStatusByEndTime(endTime);
			}
		} catch (ParseException e) {
			LOGGER.error("[商机分配]判断处理状态出错", e);
			throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
		}
		
		BusinessEntity entity = new BusinessEntity();
		entity.setBusinessBid(dto.getBusinessBid());
		entity.setZoId(dto.getZoId());
		entity.setZoName(dto.getZoName());
		entity.setUpdaterId(euid);
		entity.setUpdateTime(new Date());
		//设置分配后商机的截止处理时间和处理状态
		entity.setEndTime(endTime);
		entity.setHandState(handState);
		int result = businessService.updateBusinessEntityByBid(entity);
		if(result>0){
		    //更新管家成功后添加分配商机日志
            addDistributeLog(entity.getBusinessBid(), entity.getZoName(), eName);
		}
		return result;
	}

	/**
	 * 根据商机bid批量更新商机信息
	 *
	 * @author liujun
	 * @created 2016年8月11日
	 *
	 * @param listBusinessBid
	 * @param dto
	 * @return
	 */
	public int batchUpdateBusinessEntity(BoDistParamDto dto) {
		String euid = "", eName = "";
		String userId = dto.getUpdaterId();
    	EmployeeEntity emp = employeeLogic.getEmployeeByUserId(userId);
		if (emp != null) {
			euid = emp.getId();
			eName = emp.getName();
		}
		
		int upNum = 0;
		int effectNum = 0;
		List<String> listBusinessBid = new ArrayList<>();
		
		List<BusinessEntity> businessList = this.getBusinessListByBidList(dto.getListBusinessBid());
		for(BusinessEntity business : businessList){
			Date endTime = null;
			try {
				endTime = this.getEndTime4Distribute(business);
			} catch (ParseException e) {
				LOGGER.error("[商机分配]计算约看截止日期出错", e);
				throw new ResultException(ErrorEnum.MSG_BUSINESS_CALENDTIME_FAIL);
			}
			
			Byte handState = null;
			try {
				if(endTime==null){
					handState = 6; //完成
				}else{
					handState = DateUtil.getDealStatusByEndTime(endTime);
				}
			} catch (ParseException e) {
				LOGGER.error("[商机分配]判断处理状态出错", e);
				throw new ResultException(ErrorEnum.MSG_BUSINESS_CALHANDSTATE_FAIL);
			}
			
			BusinessEntity entity = new BusinessEntity();
			entity.setBusinessBid(business.getBusinessBid());
			entity.setZoId(dto.getZoId());
			entity.setZoName(dto.getZoName());
			entity.setUpdaterId(euid);
			entity.setUpdateTime(new Date());
			//设置分配后商机的截止处理时间和处理状态
			entity.setEndTime(endTime);
			entity.setHandState(handState);
			
			effectNum = businessService.updateBusinessEntityByBid(entity);
			if(effectNum == 0){
				listBusinessBid.add(dto.getBusinessBid());
			} else {
			    //更新管家成功后添加分配商机日志
			    addDistributeLog(entity.getBusinessBid(), entity.getZoName(), eName);
			}
			upNum += effectNum;
		}
		if(!listBusinessBid.isEmpty()){
			LOGGER.error("[商机分配]分配商机操作失败,商机bid列表："+JSON.toJSONString(listBusinessBid));
		}
		return upNum;
	}
	
	
	/**
	 * wangws21 2016-8-26 14:39:57
	 * 分配商机重新设置截止处理时间
	 * @param business 商机
	 * @return 截止处理时间
	 * @throws ParseException 时间解析异常
	 */
	private Date getEndTime4Distribute(BusinessEntity business) throws ParseException {
		byte step = business.getStep();
		if (BoBusinessStep.D_YK.getIndex() == step) {
			return this.getDYKEndTime();
		}
		if (BoBusinessStep.D_DK.getIndex() == step || BoBusinessStep.D_HF.getIndex() == step) {
			CAL.setTime(new Date());
			CAL.add(Calendar.HOUR, 6);
			Date preEndDate = business.getEndTime();
			Date nextDate = CAL.getTime();
			//原截止时间大于当前时间+24小时 ，截止处理时间不变
			//原截止时间小于当前时间  截止时间变更为当前时间+1天
			if(preEndDate.before(nextDate)){
				CAL.setTime(new Date());
				CAL.add(Calendar.DATE, 1);
				return CAL.getTime();
			}else{
				return preEndDate;
			}
		}
		return null;
	}
	
	/**
	 * 更新处理进度
	 * @author tianxf9
	 * @return
	 */
	public int updateBusinessHandState() {
	    //查询出所有待处理商机
		List<BusinessEntity> entitys = this.businessService.getAllTODOBusiness();
		//更新这些商机的处理进度
		for(BusinessEntity entity:entitys) {
			if(entity.getEndTime()!=null) {
		   Byte handState = null;
			try {
				handState = DateUtil.getDealStatusByEndTime(entity.getEndTime());
			} catch (ParseException e) {
				LOGGER.error("========businessBid为:"+entity.getBusinessBid()+"计算处理状态出错=============", e);
				continue;
			}
			
			if(entity.getHandState()!=handState) {
				entity.setHandState(handState);
				this.updateBusinessEntity(entity);
			}
		}
		}
		return entitys.size();
	}

	/**
	 * 关闭商机
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param dto
	 * @return
	 */
	public int closeBusinessEntity(BoCloseParamDto dto) {
		/*管家id和姓名 城市id*/
		String euid = "";
		String ename = "";
		String userId = dto.getUpdaterId();
    	EmployeeEntity emp = employeeLogic.getEmployeeByUserId(userId);
		if (emp != null) {
			euid = emp.getId();
			ename = emp.getName();
		}
    	
    	BusinessEntity preBusiness = this.businessService.getBusinessByBid(dto.getBusinessBid());
    	CustomerEntity customer = this.customerLogic.getCuatomerByBid(preBusiness.getCustomerId());
    	
		/* 商机阶段已经变更 说明有人已经处理了该商机 */
		if (preBusiness.getStep() != dto.getStep()) {
			throw new ResultException(ErrorEnum.MSG_BUSINESS_ALREADY_HANDED);
		}
    	
		
    	/*保存的实体*/
		BusinessEntity business = this.assembleBusinessEntity(dto, euid);
		
		// 商机处理结果   商机处理结果的商机阶段为老商机阶段
		BusinessResultEntity businessResult = this.assembleBusinessResultEntity(dto, euid, preBusiness);
		
		int upNum = businessService.closeBusinessEntity(business, businessResult);
		
		this.sendEvaluateSms(businessResult.getHandResult(), dto.getHandResultContent(), preBusiness.getId(), 
                preBusiness.getProjectId(), preBusiness.getZoId(), customer.getCusUuid(), customer.getPhone(),customer.getName()); //发送评价短信、推送
		//add by tianxf9 发送短信
		this.sendSMS(businessResult.getHandResult(), businessResult.getHandResultTime(), customer.getPhone(), preBusiness.getProjectId(), 
				customer.getName(), customer.getPhone(), business.getBusinessBid(), dto.getCloseMsgDescContent());
        //记录操作日志
        this.addBusinessLog(businessResult, null, ename);
        return upNum;
    }

	/**
	 * 组装商机实体
	 *
	 * @author liujun
	 * @created 2016年8月14日
	 *
	 * @param dto
	 * @param euid
	 * @return
	 */
	private BusinessEntity assembleBusinessEntity(BoCloseParamDto dto,
			String euid) {
		BusinessEntity business = new BusinessEntity();
		business.setBusinessBid(dto.getBusinessBid());
		business.setStep(BoBusinessStep.W_CJ.getIndex());
		business.setHandState(BoHandState.DOWN.getIndex());
		business.setUpdaterId(euid);
		business.setUpdateTime(new Date());
		return business;
	}
	
	/**
	 * 组装商机处理结果实体
	 *
	 * @author liujun
	 * @created 2016年8月14日
	 *
	 * @param dto
	 * @param euid
	 * @param preBusiness 
	 * @return
	 */
	private BusinessResultEntity assembleBusinessResultEntity(
			BoCloseParamDto dto, String euid, BusinessEntity preBusiness) {
		BusinessResultEntity businessResult = new BusinessResultEntity();
		businessResult.setResultBid(KeyGenUtils.genKey());
		businessResult.setBusinessId(dto.getBusinessBid());
		businessResult.setCreaterId(euid);
		businessResult.setCityId(preBusiness.getCityId());
		businessResult.setStep(dto.getStep());
		businessResult.setHandState(preBusiness.getHandState());
		businessResult.setHandResult(this.selectHandResult(dto.getStep()));
		businessResult.setHandResultContent(dto.getHandResultContent());
		businessResult.setHandleZoBid(euid);
		businessResult.setIsSms((byte)0);
		businessResult.setCreateTime(new Date());
		return businessResult;
	}

	/**
	 * 根据商机阶段选择处理结果
	 *
	 * @author liujun
	 * @param step 
	 * @created 2016年8月12日
	 *
	 * @return
	 */
	private Byte selectHandResult(Byte step) {
		Byte handResult = null;
		if(step.intValue() == BoBusinessStep.D_YK.getIndex()){
			handResult = BoHandResult.YK_GB_RS.getIndex();//带评价原因的待约看关闭商机
		}
		if(step.intValue() == BoBusinessStep.D_DK.getIndex()){
			handResult = BoHandResult.DK_GB_SJ.getIndex();
		}
		if(step.intValue() == BoBusinessStep.D_HF.getIndex()){
			handResult = BoHandResult.HF_GB_SJ.getIndex();
		}
		return handResult;
	}

	/**
	 * 根据商机业务id查询商机信息
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param businessBid
	 * @return
	 */
	public BusinessEntity getBusinessEntity(String businessBid) {
		return businessService.getBusinessByBid(businessBid);
	}

	/**
	 * 根据商机业务id集合查询商机信息列表
	 *
	 * @author liujun
	 * @created 2016年8月12日
	 *
	 * @param listBusinessBid
	 * @return
	 */
	public List<BusinessEntity> getBusinessListByBidList(
			List<String> listBusinessBid) {
		return businessService.getBusinessListByBidList(listBusinessBid);
	}

	/**
	 * TODO
	 *
	 * @author liujun
	 * @created 2016年8月14日
	 *
	 * @param dto
	 * @return
	 */
	public int batchCloseBusinessEntity(BoCloseParamDto dto) {
		/*管家id和姓名 城市id*/
		String euid = "";
		String ename = "";
		String userId = dto.getUpdaterId();
    	EmployeeEntity emp = employeeLogic.getEmployeeByUserId(userId);
		if (emp != null) {
			euid = emp.getId();
			ename = emp.getName();
		}
    	
		List<BusinessEntity> holderList = new ArrayList<>();
		List<String> listBusinessBid = dto.getListBusinessBid();
		for (String businessBid : listBusinessBid) {
			BusinessEntity preBusiness = this.businessService.getBusinessByBid(businessBid);
			if(preBusiness==null){
				throw new ResultException(ErrorEnum.MSG_BUSINESS_GET_FAIL);
			}
			
			/* 商机阶段已经变更 说明有人已经处理了该商机 */
			if (preBusiness.getStep() != dto.getStep()) {
				throw new ResultException(ErrorEnum.MSG_BUSINESS_ALREADY_HANDED);
			}
			
			holderList.add(preBusiness);
		}
		
		if(holderList.size() != listBusinessBid.size()){
			/* 说明有人删除了部分商机 */
			throw new ResultException(ErrorEnum.MSG_BUSINESS_CLOSE_FAIL);
		}
    	
		int upNum = 0;
		for (BusinessEntity preBusiness : holderList) {
			dto.setBusinessBid(preBusiness.getBusinessBid());
			
			/*保存的实体*/
			BusinessEntity business = this.assembleBusinessEntity(dto, euid);
			
			// 商机处理结果   商机处理结果的商机阶段为老商机阶段
			BusinessResultEntity businessResult = this.assembleBusinessResultEntity(dto, euid, preBusiness);
			
			upNum += businessService.closeBusinessEntity(business, businessResult);
			
			CustomerEntity customer = this.customerLogic.getCuatomerByBid(preBusiness.getCustomerId());
			this.sendEvaluateSms(businessResult.getHandResult(), dto.getHandResultContent(), preBusiness.getId(), 
	                preBusiness.getProjectId(), preBusiness.getZoId(), customer.getCusUuid(), customer.getPhone(),customer.getName()); //发送评价短信、推送
			//add by tianxf9 发送短信
			this.sendSMS(businessResult.getHandResult(), businessResult.getHandResultTime(), customer.getPhone(), preBusiness.getProjectId(), 
					customer.getName(), customer.getPhone(), preBusiness.getBusinessBid(), dto.getCloseMsgDescContent());
			//记录操作日志
			this.addBusinessLog(businessResult, null, ename);
		}
		return upNum;
	}
	
	/**
	 * 发送约看提醒短信
	 *
	 * @author liujun
	 * @created 2016年8月15日
	 *
	 */
	public void sendYkRemindSms() {
		CAL.setTime(new Date());
		CAL.add(Calendar.SECOND, SysConstant.YUEKAN_REMIND_SMS_ADVANCED_TIME);
		Date deadline = CAL.getTime();
		List<BusinessResultVo> resultVoList = businessService.getYkRemindSmsList(deadline);
		LOGGER.info("[约看提醒]约看提醒客户列表:"+JSON.toJSONString(resultVoList)+"时间："+deadline);
		
		String smsContent = PropUtils.getString(ZraApiConst.SMS_YKTX_MSG);//亲爱的用户，您在{1}有一个自如寓约看行程，我们期待您的到访。约看公寓：{2}，公寓地址：{3}，您的服务管家：{4}，联系电话：{5}，有任何问题请随时与我们联系，祝您生活愉快。
		
		Map<String,String> unSendMap = new HashMap<>();
		for (BusinessResultVo vo : resultVoList) {
			
			//只发送一次短信  成功失败均不在发送
			BusinessResultEntity businessResultEntity = new BusinessResultEntity();
			businessResultEntity.setResultBid(vo.getResultBid());
			businessResultEntity.setIsSms((byte)0);
			businessService.updateBusinessResultEntity(businessResultEntity);
			
			boolean voFlag = this.validBusinessResultVo(vo, unSendMap);
			if(!voFlag){
				continue;
			}
			
			EmployeeEntity zo = employeeLogic.getEmployeeById(vo.getBusinessZoId());
			boolean zoFlag = this.validEmployeeEntity(zo, vo, unSendMap);
			if(!zoFlag){
				continue;
			}
			
			ProjectDto pro = projectLogic.getProjectById(vo.getProjectId());
			boolean proFlag = this.validProjectDto(pro, vo, unSendMap);
			if(!proFlag){
				continue;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String tempSmsContent = new String();
			tempSmsContent = smsContent.replace("{1}", sdf.format(vo.getHandResultTime()));
			tempSmsContent = tempSmsContent.replace("{2}", pro.getName());
			tempSmsContent = tempSmsContent.replace("{3}", pro.getAddress());
			tempSmsContent = tempSmsContent.replace("{4}", zo.getName());
			tempSmsContent = tempSmsContent.replace("{5}", zo.getMobile());
			
			boolean flag = SmsUtils.INSTANCE.sendSMS(tempSmsContent, vo.getCustomerMobile());
			if(!flag){
				unSendMap.put(vo.getResultBid(), "短信平台发送失败");
			}
		}
		
		if(!unSendMap.isEmpty()){
			LOGGER.error("[约看提醒]未发送约看提醒短信列表:"+JSON.toJSONString(unSendMap)+",size="+unSendMap.size());
		}
	}

    /**
     * 校验项目参数
     *
     * @author liujun
     * @created 2016年8月16日
     *
     * @param pro
     * @param vo
     * @param unSendMap
     * @return
     */
    private boolean validProjectDto(ProjectDto pro, BusinessResultVo vo,
            Map<String, String> unSendMap) {
        if(StrUtils.isNullOrBlank(pro)){
            unSendMap.put(vo.getResultBid(), "项目信息为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(pro.getName())){
            unSendMap.put(vo.getResultBid(), "项目名称为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(pro.getAddress())){
            unSendMap.put(vo.getResultBid(), "项目地址为空");
            return false;
        }
        return true;
    }

    /**
     * 校验管家参数
     *
     * @author liujun
     * @created 2016年8月16日
     *
     * @param zo
     * @param vo
     * @param unSendMap
     * @return
     */
    private boolean validEmployeeEntity(EmployeeEntity zo,
            BusinessResultVo vo, Map<String, String> unSendMap) {
        if(StrUtils.isNullOrBlank(zo)){
            unSendMap.put(vo.getResultBid(), "商机管家信息为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(zo.getName())){
            unSendMap.put(vo.getResultBid(), "商机管家姓名为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(zo.getMobile())){
            unSendMap.put(vo.getResultBid(), "商机管家电话为空");
            return false;
        }
        return true;
    }

    /**
     * 校验商机参数
     *
     * @author liujun
     * @created 2016年8月16日
     *
     * @param vo
     * @param unSendMap
     * @return
     */
    private boolean validBusinessResultVo(BusinessResultVo vo,
            Map<String, String> unSendMap) {
        if(StrUtils.isNullOrBlank(vo.getCustomerMobile()) ){
            unSendMap.put(vo.getResultBid(), "客户电话为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(vo.getHandResultTime()) ){
            unSendMap.put(vo.getResultBid(), "约看时间为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(vo.getProjectId()) ){
            unSendMap.put(vo.getResultBid(), "项目id为空");
            return false;
        }

        if(StrUtils.isNullOrBlank(vo.getBusinessZoId()) ){
            unSendMap.put(vo.getResultBid(), "商机管家id为空");
            return false;
        }
        return true;
    }


    /**
     * @author wangws21 2016年8月23日
     * 带看完成后客户对本次带看进行评价提醒<br>
     *
     *     2016-9-30 Deprecated  评价短信定时任务取消，改为处理带看后触发
     */
    @Deprecated
    public void sendEvaluateRemindSms() {
        CAL.setTime(new Date());
        CAL.add(Calendar.SECOND, SysConstant.EVALUATE_REMIND_SMS_ADVANCED_TIME);
        Date endDate = CAL.getTime();
        List<BusinessEvaluateSMSDto> businessEvaluateSMSDtoList = this.businessService.getBusinessEvaluateSmsList(endDate);

        LOGGER.info("[评价推送]本次需要处理的商机数量："+businessEvaluateSMSDtoList.size()+"时间："+endDate);

        Map<String,String> unSendMap = new HashMap<>();
        String smsContentTep = PropUtils.getString(ZraApiConst.SMS_DKPJ_MSG);
        String title = PropUtils.getString(ZraApiConst.YYTZ_PUSH_TITLE);
        String pushContentTep = PropUtils.getString(ZraApiConst.YYTZ_PUSH_MSG);
        String shortUrl = PropUtils.getString(ZraApiConst.ZOEVA_SHORT_URL);

        boolean isSendZOEVASms = Boolean.valueOf(PropUtils.getString(ZraApiConst.ZOEVA_SMS_OPEN));
        boolean isSendZOEVAPush = Boolean.valueOf(PropUtils.getString(ZraApiConst.ZOEVA_PUSH_OPEN));
        for(BusinessEvaluateSMSDto bes:businessEvaluateSMSDtoList){

            if(StrUtils.isNotNullOrBlank(bes.getZoId())){
                //http://10.16.24.74:8081/zra_mst/zoeva/zoeva.action?beEvaluatorId=9000073834120133123&businessBid=3a7fb204-e9c7-4730-b77d-16ea03b8c7af
                //1. 推送来源     &from=push    2. 短信来源     &from=sms
                StringBuilder longUrl = new StringBuilder();
                longUrl.append(PropUtils.getString(ZraApiConst.ZOEVA_LONG_URL))
                .append("?beEvaluatorId=").append(bes.getZoId())
                .append("&businessBid=").append(bes.getId());
                //TODO 这应该传bid  客户端不支持String  只能取id
                //.append("&businessBid=").append(bes.getBusinessBid());
                /*手机号有效的话发送短信*/
                if(isSendZOEVASms){
                    String cusPhone = bes.getCusPhone();
                    if(StrUtils.isNotNullOrBlank(cusPhone) && StringUtil.isPhoneNum(cusPhone)){
                        String smsLongUrl = longUrl+"&from=sms";
                        CfShortUrl cfShortUrl = this.shortUrlLogic.genShortUrlSuff(smsLongUrl.toString());
                        String smsContent = smsContentTep;
                        smsContent = smsContent.replace("{1}", shortUrl+cfShortUrl.getSuid());

                        LOGGER.info("[评价推送]手机号："+cusPhone+"\t短信内容："+smsContent+"businessBid="+bes.getBusinessBid());
                    SmsUtils.INSTANCE.sendSMS(smsContent, cusPhone);
                    }else{
                        unSendMap.put(bes.getBusinessBid(), "客户电话不是手机号："+bes.getCusPhone());
                    }
                }else{
                    LOGGER.info("[评价推送]带看评价短信未开启");
                }

                /*uuid存在的进行推送*/
                if(isSendZOEVAPush && StrUtils.isNotNullOrBlank(bes.getCusUuid())){
                    String pushContent = pushContentTep;
                    String projectName = this.projectLogic.getProjectById(bes.getProjectId()).getName();
                    pushContent = pushContent.replace("{1}", projectName);
                    String pushLongUrl = longUrl.toString()+"&from=push";
                    CfShortUrl cfShortUrl = this.shortUrlLogic.genShortUrlSuff(pushLongUrl);
                    PushDto pushDto = new PushDto(BussSystemEnums.ZRA.getKey(), "YYTZ", title, pushContent, shortUrl+cfShortUrl.getSuid(), bes.getCusUuid());
                    LOGGER.info("[评价推送]"+JSON.toJSONString(pushDto));
                    pushLogic.push(pushDto);
                }else{
                    LOGGER.info("[评价推送]评价未开启："+isSendZOEVAPush+"或者用户uuid为空："+bes.getCusUuid());
                }
            }else{
                unSendMap.put(bes.getBusinessBid(), "管家为空");
            }

            //更新bid
            BusinessResultEntity businessResultEntity = new BusinessResultEntity();
            businessResultEntity.setResultBid(bes.getBusinessResultBid());
            businessResultEntity.setIsEvaluateSms((byte)0);
            businessService.updateBusinessResultEntity(businessResultEntity);
        }

        if(!unSendMap.isEmpty()){
            LOGGER.error("[评价推送]未发送评价推送列表:"+JSON.toJSONString(unSendMap)+",size="+unSendMap.size());
        }
    }


    /**
     * wangws21 2016-8-24
     * 发送管家预约通知短信
     * @param businessBid 商机bid
     */
    public boolean sendYytzMsg(String businessBid){
        if(!Boolean.valueOf(PropUtils.getString(ZraApiConst.YYTZ_SMS_OPEN))){
            LOGGER.info("[预约通知]功能关闭");
            return false;
        }

        BusinessFullDto businessDetail = this.businessService.getBusinessDetail(businessBid);
        if(businessDetail==null){
            LOGGER.info("[预约通知]商机不存在：businessBid"+businessBid);
            return false;
        }
        BusinessDto business = businessDetail.getBusiness();
        CustomerDto customer = businessDetail.getCustomer();
        //SMS_YYTZ_MSG=亲爱的{1}，您有一个{2}的约看。约看项目：{3}，约看房型：{4}，客户联系方式：{5}，请在{6}前跟进约看，并在系统完善约看信息。
        String yytzMsg = PropUtils.getString(ZraApiConst.SMS_YYTZ_MSG);
        //短信模板需要的信息
        String zoName="",zoPhone="",projectName="",houseTypeName="",cusPhone="",endDateStr="";
        if(StrUtils.isNullOrBlank(business.getZoId())){
            LOGGER.info("[预约通知]管家id为空：businessBid"+businessBid);
            return false;
        }
        EmployeeEntity emp = this.employeeLogic.getEmployeeById(business.getZoId());
        if(emp==null){
            LOGGER.info("[预约通知]找不到管家"+businessBid);
            return false;
        }
        zoName = emp.getName();
        zoPhone = emp.getMobile();
        //管家手机号可能加0
        if(zoPhone!=null && zoPhone.startsWith("0")){
            zoPhone = zoPhone.substring(1);
        }
        ProjectDto project = this.projectLogic.getProjectById(business.getProjectId());
        if(project==null){
            LOGGER.info("[预约通知]找不到项目"+businessBid);
            return false;
        }
        projectName = project.getName();
        if(StrUtils.isNotNullOrBlank(business.getHouseTypeId())){
            HouseTypeEntity houseType = this.projectLogic.getHouseTypeById(business.getHouseTypeId());
            if(houseType!=null){
                houseTypeName = houseType.getHouseTypeName();
            }
        }
        if(customer!=null){
            cusPhone = customer.getPhone();
        }
        try {
            Date endTime=this.getEndTimeByStep(BoBusinessStep.D_YK.getIndex(), (byte)0, null);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            endDateStr = sdf.format(endTime);
        } catch (Exception e) {
            LOGGER.error("[预约通知]出错！", e);
        }
        yytzMsg = yytzMsg.replace("{1}", zoName);
        yytzMsg = yytzMsg.replace("{2}", BoSourceType.getEnum2map().get(business.getSource()));
        yytzMsg = yytzMsg.replace("{3}", projectName);
        yytzMsg = yytzMsg.replace("{4}", houseTypeName);
        yytzMsg = yytzMsg.replace("{5}", cusPhone);
        yytzMsg = yytzMsg.replace("{6}", endDateStr);
        return SmsUtils.INSTANCE.sendSMS(yytzMsg, zoPhone);
    }

    /**
     * 待评价约看列表
     *
     * @param uid
     * @return
     */
    public List<BusinessListDto> businessUnevaluateList(String uid) {
        List<BusinessListDto> result = new ArrayList<>();
        List<BusinessListDto> dtoList = businessList(uid, 3);//此处传3，将该用户所有状态的商机都查询出来
        for (BusinessListDto dto : dtoList) {
            if ("0".equals(dto.getIsEvaluate())) {//可以评价的，即尚未评价的
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * wangws21  2017-1-9 添加分配商机日志.
     * @param businessBid 商机bid
     * @param zoName 管家姓名
     */
    public void addDistributeLog(String businessBid, String zoName, String operator){
        try {
            String logInfo = "分配商机给ZO:" + zoName;
            LogRecordDto logRecord = new LogRecordDto(BussSystemEnums.ZRA.getKey(), "business", businessBid, operator, logInfo);
            LOGGER.info("logRecord.toString():" + logRecord.getSystemId() + "," + logRecord.getOperModId() + "," + logRecord.getOperObjId() + "," + logRecord.getOperator() + "," + logRecord.getLoginfo());
            boolean flag = logLogic.saveLog(logRecord);
            LOGGER.info("save info flag:" + flag);
        } catch (Exception e) {
            LOGGER.error("分配商机给ZO出错！", e);
        }
    }
    
    /**
     * 获取关闭商机发送短信的所有模板
     * @author tianxf9
     * @return
     */
    public List<BoCloseSMSContent> getSmsList() {
    	return this.boCloseSmsService.getSmsList();
    }
    
    /**
     * 根据id获取短信内容
     * @author tianxf9
     * @param id
     * @return
     */
    public BoCloseSMSContent getSmsContentById(Integer id) {
    	return this.boCloseSmsService.getSmsContentById(id);
    }
}

