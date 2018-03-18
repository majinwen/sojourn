package com.zra.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.house.entity.dto.PageDto;
import com.zra.report.entity.ReportBoEntity;
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
public interface ReportBoDao {
	
	/**
	 * 获取报表商机数据总数
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return the total numbers of the bo report.
	 */
	public int selectReportBoCount(@Param("projectId") String projectId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**
	 * 获取报表商机记录时间列表--支持分页
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return the time list of the report bo.
	 */
	public List<String> selectReportBoTimeSpanByPage(@Param("projectId") String projectId, @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime, @Param("page") PageDto page);
	
	/**
	 * 获取报表商机数据
	 * @param projectId
	 * @param recordDate
	 * @return the list of the bo report.
	 */
	public List<ReportBoEntity> selectReportBoList(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 根据时间范围查询表报商机数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return the list of the bo report.
	 */
	public List<ReportBoEntity> selectReportBoListByTimeSpan(@Param("projectId") String projectId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	/**
	 * 新增报表商机数据
	 * @param reportBoEntity
	 * @return the number of rows affected.
	 */
	public int insertReportBo(ReportBoEntity reportBoEntity);
	
	/**
	 * 修改报表商机数据
	 * @param reportBoEntity
	 * @return the number of rows affected.
	 */
	public int updateReportBo(ReportBoEntity reportBoEntity);
	
	/**
	 * 根据业务id删除报表商机数据
	 * @param bid
	 * @return the number of rows affected.
	 */
	public int deleteReportBoByBid(ReportBoEntity reportBoEntity);
	
	
	/**
	 * 根据条件，获取新增商机量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getNewBusinessCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据条件，获取商机成交量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getDealBusinessCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据条件，获取带看量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getSeeBusinessCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据条件，获取新增客源量
	 * @author wangws21
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getBusinessKYLCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据条件，获取新签量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getSignCount(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据条件，获取实际出房价（按照签约周期，户型进行计算总和）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getActualPrice(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectId")String projectId);
	
	/**
	 * 根据记录时间删除数据
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delByRecordDate(@Param("recordDate")String recordDate,@Param("delTime")Date delTime,@Param("delId")String delId);
	
}