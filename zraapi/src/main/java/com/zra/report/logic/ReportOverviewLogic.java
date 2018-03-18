package com.zra.report.logic;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.zra.common.utils.DateUtil;
import com.zra.common.utils.PropUtils;
import com.zra.common.utils.SmsUtils;
import com.zra.common.utils.SysConstant;
import com.zra.common.utils.ZraApiConst;
import com.zra.house.entity.dto.ProjectListReturnDto;
import com.zra.house.logic.ProjectLogic;
import com.zra.report.entity.ReportOverviewEntity;
import com.zra.report.entity.dto.ReportOverviewDto;
import com.zra.report.service.ReportOverviewService;
import com.zra.zmconfig.ConfigClient;

/**
 * 自如寓业务总览实体服务.
 * @author wangws21 
 * @date 2017年5月22日
 */
@Component
public class ReportOverviewLogic {

	private static final Logger LOGGER = LoggerFactoryProxy.getLogger(ReportOverviewLogic.class);
	
	@Autowired
    private ProjectLogic projectLogic;
	
	@Autowired
    private ConfigClient configClient;
	
	@Autowired
	private ReportOverviewService reportOverviewService;
	
	/**
	 * 获取当日的统计数据并保存到数据库.
	 * @param recordDate 待统计时间
	 */
	public void createAndSaveReportOverview(Date recordDate){
	    
	    String recordStr = DateUtil.DateToStr(recordDate, DateUtil.DATE_FORMAT);
	    String startDate = DateUtil.getDayBeginTime(recordStr);
	    String endDate = DateUtil.getDayEndTime(recordStr);
	    //获取所有项目
        List<ProjectListReturnDto> projectList = projectLogic.getProjectList();
        Map<String, ReportOverviewEntity> roeMap = new HashMap<>();
        for(ProjectListReturnDto project : projectList) {
            ReportOverviewEntity roe = new ReportOverviewEntity();
            roe.setProjectId(project.getProjId());
            try {
                roe.setRecordDate(DateUtil.castString2Date(recordStr, DateUtil.DATE_FORMAT));
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
            }
            roe.setShortSignCount(0);
            roe.setLongSignCount(0);
            roe.setSignCount(0);
            roe.setSurrenderCount(0);
            roe.setVoucherTotalAmount(BigDecimal.ZERO);
            roe.setPayTotalAmount(BigDecimal.ZERO);
            roe.setCreateId(SysConstant.ADMINID);
            roe.setUpdateId(SysConstant.ADMINID);
            roe.setCreateTime(new Date());
            roe.setUpdateTime(new Date());
            roe.setIsDel(0);
            roe.setIsValid(1);
            roeMap.put(project.getProjId(), roe);
        }
        /*设置签约数量*/
        this.setupSignCount(roeMap, startDate, endDate);
        /*设置解约数量*/
        this.setupSurrenderCount(roeMap, startDate, endDate);
        /*设置收款金额*/
        this.setupVoucherTotalAmount(roeMap, startDate, endDate);
        /*设置支出金额*/
        this.setupPayTotalAmount(roeMap, startDate, endDate);
        
        for(Entry<String, ReportOverviewEntity> entry : roeMap.entrySet()){
            ReportOverviewEntity entity = entry.getValue();
            this.reportOverviewService.insert(entity);
            LOGGER.info("时间："+recordStr+" 保存业务总览报表数据："+JSON.toJSONString(entity));
        }
	}

    /**
	 * 设置签约数量.
	 * @param roeMap 统计报表map
	 * @param startDate    开始时间
	 * @param endDate      结束时间
	 */
	private void setupSignCount(Map<String, ReportOverviewEntity> roeMap, String startDate, String endDate) {
        List<ReportOverviewDto> signCountList = this.reportOverviewService.getSignCount(startDate, endDate);
        for(ReportOverviewDto signCount : signCountList){
            ReportOverviewEntity roe = roeMap.get(signCount.getProjectId());
            if(roe == null){
                continue;
            }
            /*类型 1 长租;2 短租  */
            if ("1".equals(signCount.getConType())){
                roe.setLongSignCount(signCount.getSignCount());
            } else {
                roe.setShortSignCount(signCount.getSignCount());
            }
            roe.setSignCount(roe.getShortSignCount()+roe.getLongSignCount());
        }
    }

	/**
     * 设置解约数量.
     * @param roeMap 统计报表map
     * @param startDate    开始时间
     * @param endDate      结束时间
     */
    private void setupSurrenderCount(Map<String, ReportOverviewEntity> roeMap, String startDate, String endDate) {
        List<ReportOverviewDto> surrenderCountList = this.reportOverviewService.getSurrenderCount(startDate, endDate);
        for(ReportOverviewDto surrenderCount : surrenderCountList){
            ReportOverviewEntity roe = roeMap.get(surrenderCount.getProjectId());
            if(roe == null){
                continue;
            }
            roe.setSurrenderCount(surrenderCount.getSurrenderCount());
        }
    }
    
    /**
     * 设置收款金额.
     * @param roeMap 统计报表map
     * @param startDate    开始时间
     * @param endDate      结束时间
     */
    private void setupVoucherTotalAmount(Map<String, ReportOverviewEntity> roeMap, String startDate, String endDate) {
        List<ReportOverviewDto> paymentAmountList = this.reportOverviewService.getTotalPayment(startDate, endDate);
        for(ReportOverviewDto paymentAmount : paymentAmountList){
            ReportOverviewEntity roe = roeMap.get(paymentAmount.getProjectId());
            if(roe == null){
                continue;
            }
            roe.setVoucherTotalAmount(paymentAmount.getAmount());
        }
    }
    
    /**
     * 设置支出金额.
     * @param roeMap 统计报表map
     * @param startDate    开始时间
     * @param endDate      结束时间
     */
    private void setupPayTotalAmount(Map<String, ReportOverviewEntity> roeMap, String startDate, String endDate) {
        List<ReportOverviewDto> payAmountList = this.reportOverviewService.getTotalPay(startDate, endDate);
        for(ReportOverviewDto payAmount : payAmountList){
            ReportOverviewEntity roe = roeMap.get(payAmount.getProjectId());
            if(roe == null){
                continue;
            }
            roe.setPayTotalAmount(payAmount.getAmount());
        }
    }
    
    /**
     * 查询统计结果.
     * @param recordDate 时间
     * @return List<ReportOverviewEntity>
     */
    public List<ReportOverviewEntity> getReportOverviewList(Date recordDate){
        String recordDateStr = DateUtil.DateToStr(recordDate, DateUtil.DATE_FORMAT);
        return this.reportOverviewService.getReportOverviewListByDateStr(recordDateStr);
    }

    public void dailyReportJob() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-1);
        Date recordDate = calendar.getTime();
        
        this.createAndSaveReportOverview(recordDate);
        
        List<ReportOverviewEntity> reportOverviewList = this.getReportOverviewList(recordDate);
        int signCount = 0;
        int surrenderCount = 0;
        BigDecimal voucherTotalAmount = BigDecimal.ZERO;
        BigDecimal payTotalAmount = BigDecimal.ZERO;
        for(ReportOverviewEntity entity : reportOverviewList){
            signCount += entity.getSignCount();
            surrenderCount += entity.getSurrenderCount();
            voucherTotalAmount = voucherTotalAmount.add(entity.getVoucherTotalAmount());
            payTotalAmount = payTotalAmount.add(entity.getPayTotalAmount());
        }
        //自如寓业务总览：日期{0}，签约{1}个，解约{2}个，总计收款{3}元，支出{4}元；Good morning, everybody!
        String smsTemp = PropUtils.getString(ZraApiConst.SMS_REPORTOVERVIEW_MSG);
        String smsContent = smsTemp.replace("{0}", DateUtil.DateToStr(recordDate, DateUtil.DATE_FORMAT));
        smsContent = smsContent.replace("{1}", String.valueOf(signCount));
        smsContent = smsContent.replace("{2}", String.valueOf(surrenderCount));
        smsContent = smsContent.replace("{3}", voucherTotalAmount.setScale(2).toPlainString());
        smsContent = smsContent.replace("{4}", payTotalAmount.setScale(2).toPlainString());
        
        String phones = configClient.get(ZraApiConst.SMS_REPORTOVERVIEW_PHONES, ZraApiConst.CONS_SYSTEMID_ZRA);
        if(StringUtils.isNotBlank(phones)){
            SmsUtils.INSTANCE.sendSMS(smsContent, phones);
        } else {
            LOGGER.info("请在研发管理配置接收业务总览的手机号，键值："+ZraApiConst.SMS_REPORTOVERVIEW_PHONES);
        }
	}
	
}
