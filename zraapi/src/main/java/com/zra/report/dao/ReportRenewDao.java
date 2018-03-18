package com.zra.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.house.entity.dto.PageDto;
import com.zra.report.entity.ReportRenewEntity;
import com.zra.report.entity.dto.BaseReportDto;

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
public interface ReportRenewDao {

	/**
	 * 获取报表续约数据总数
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return the total numbers of the renew report.
	 */
	public int selectReportRenewCount(@Param("projectId") String projectId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**
	 * 获取报表续约记录时间列表--支持分页
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return the time list of the report renew.
	 */
	public List<String> selectReportRenewTimeSpanByPage(@Param("projectId") String projectId, @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime, @Param("page") PageDto page);
	
	/**
	 * 获取报表续约数据
	 * @param projectId
	 * @param recordDate
	 * @return the list of the renew report.
	 */
	public List<ReportRenewEntity> selectReportRenewList(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 根据时间范围获取报表续约数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<ReportRenewEntity> selectReportRenewListByTimeSpan(@Param("projectId") String projectId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	/**
	 * 新增报表续约数据
	 * @param reportRenewEntity
	 * @return the number of rows affected.
	 */
	public int insertReportRenew(ReportRenewEntity reportRenewEntity);
	
	/**
	 * 修改报表续约数据
	 * @param reportRenewEntity
	 * @return the number of rows affected.
	 */
	public int updateReportRenew(ReportRenewEntity reportRenewEntity);
	
	/**
	 * 根据业务id删除报表续约数据
	 * @param bid
	 * @return the number of rows affected.
	 */
	public int deleteReportRenewByBid(ReportRenewEntity reportRenewEntity);
	
	/**
	 * 获取到期房源数量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getExpireRoomCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 获取长租续约量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRenewLongCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);

	/**
	 * 获取短租续约量（1——3个月）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRenewShort1Count(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 获取短租续约量（4-6个月）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRenewShort2Count(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据记录时间删除记录
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delRenewEntityByRecordDate(@Param("recordDate")String recordDate,@Param("delId")String delId,@Param("delTime")Date delTime);
	
}