package com.zra.report.dao;

import com.zra.report.entity.ReportPaymenTaskEntity;
import com.zra.report.entity.ReportPaymentEntity;
import com.zra.report.entity.dto.ReportPaymentForKBDto;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
@Repository
public interface ReportPaymentDao {

	/**
	 * 获取报表回款数据
	 * @param projectId
	 * @param recordDate
	 * @return the payment report.
	 */
	public List<ReportPaymentEntity> selectReportPaymentByRecordDate(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 新增报表回款数据
	 * @param reportPaymentEntity
	 * @return the number of rows affected.
	 */
	public int insertReportPayment(ReportPaymentEntity reportPaymentEntity);
	
	/**
	 * 修改报表回款数据
	 * @param reportPaymentEntity
	 * @return the number of rows affected.
	 */
	public int updateReportPayment(ReportPaymentEntity reportPaymentEntity);
	
	/**
	 * 根据业务id删除报表回款数据
	 * @param bid
	 * @return the number of rows affected.
	 */
	public int deleteReportPaymentByBid(ReportPaymentEntity reportPaymentEntity);
	
	/**
	 * 获取本月应回款单量
	 * @param projectId
	 * @param recordDate 统计的日期
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectPaymentCount(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 获取时间范围内的应回款单量
	 * @author tianxf9
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectPaymentCountRange(@Param("projectId") String projectId, @Param("startDate") String startDate,@Param("endDate")String endDate);
	
	/**
	 * 获取实际回款单量
	 * @param projectId
	 * @param recordDate 统计的日期
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectVoucherCount(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 获取及时回款单量
	 * @param projectId
	 * @param recordDate 统计的日期
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectIntimeVoucherCount(@Param("projectId") String projectId, @Param("startDate") String startDate,@Param("endDate")String endDate);
	
	/**
	 * 获取应收回款金额(本月)
	 * @param projectId
	 * @param recordDate 统计的日期
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectPaymentAmount(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 获取时间范围内的应收回款金额
	 * @author tianxf9
	 * @param projectId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectPaymentAmountRange(@Param("projectId") String projectId, @Param("startDate") String startDate,@Param("endDate")String endDate); 
	
	/**
	 * 获取实际回款金额
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectVoucherAmount(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 获取及时回款金额
	 * @param projectId
	 * @param recordDate
	 * @return
	 */
	public List<ReportPaymenTaskEntity> selectIntimeVoucherAmount(@Param("projectId") String projectId, @Param("startDate") String startDate,@Param("endDate")String endDate);
	
	/**
	 * 删除记录依据记录时间
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delEntitysByRecordDate(@Param("recordDate")String recordDate,@Param("delId")String delId,@Param("delTime")Date delTime);
	
	
	/**
	 * 查询时间段内应收的收款单量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymentForKBDto> getPaymentCountForKB(@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	/**
	 * 查询时间段内实际收款单量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ReportPaymentForKBDto> getVoucherCountForKB(@Param("startDate")String startDate,@Param("endDate")String endDate);
}