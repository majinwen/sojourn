package com.zra.report.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.house.entity.dto.PageDto;
import com.zra.kanban.entity.dto.KanbanRateDto;
import com.zra.report.entity.ReportStockEntity;
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
public interface ReportStockDao {
	
	/**
	 * 获取报表库存数据总数
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return the total numbers of the stock report.
	 */
	public int selectReportStockCount(@Param("projectId") String projectId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**
	 * 获取报表库存记录时间列表--支持分页
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @return the time list of the report stock.
	 */
	public List<String> selectReportStockTimeSpanByPage(@Param("projectId") String projectId, @Param("beginTime") String beginTime, 
			@Param("endTime") String endTime, @Param("page") PageDto page);
	
	/**
	 * 获取报表库存数据
	 * @param projectId
	 * @param recordDate
	 * @return the list of the bo report.
	 */
	public List<ReportStockEntity> selectReportStockList(@Param("projectId") String projectId, @Param("recordDate") String recordDate);
	
	/**
	 * 根据时间范围查询报表库存数据
	 * @param projectId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<ReportStockEntity> selectReportStockListByTimeSpan(@Param("projectId") String projectId, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	/**
	 * 新增报表库存数据
	 * @param reportStockEntity
	 * @return the number of rows affected.
	 */
	public int insertReportStock(ReportStockEntity reportStockEntity);
	
	/**
	 * 修改报表库存数据
	 * @param reportStockEntity
	 * @return the number of rows affected.
	 */
	public int updateReportStock(ReportStockEntity reportStockEntity);
	
	/**
	 * 根据业务id删除报表库存数据
	 * @param bid
	 * @return the number of rows affected.
	 */
	public int deleteReportStockByBid(ReportStockEntity reportStockEntity);
	
	/**
	 * 获取库存总数
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getStockCount(String projectId);
	
	/**
	 * 获取可出租数
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getRentableCount(String projectId);
	
	/**
	 * 获取已出租数
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getLeasedCount(@Param("recordDate")String recordDate,@Param("projectId")String projectId);
	
	/**
	 * 获取短租出租数
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getShortLeasedCount(@Param("recordDate")String recordDate,@Param("projectId")String projectId);
	
	/**
	 * 获取剩余库存(配置中,待租中)
	 * @author tianxf9
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getLeaveStockCount(@Param("recordDate")String recordDate,@Param("projectId")String projectId); 
	
	/**
	 * 获得退租量
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param projectId
	 * @return
	 */
	public List<BaseReportDto> getQuitCount(@Param("recordDate")String recordDate,@Param("projectId") String projectId);
	
	/**
	 * 删除记录依据记录时间
	 * @author tianxf9
	 * @param recordDate
	 * @return
	 */
	public int delEntitysByRecordDate(@Param("recordDate")String recordDate,@Param("delId")String delId,@Param("delTime")Date delTime);
	
	/**
	 * 获取所有项目的出租率（目标看板）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<KanbanRateDto> getLeasedRate(@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	/**
	 * 获取所有项目的日均入住率（目标看板）
	 * @author tianxf9
	 * @param startDate
	 * @param endDate
	 * @param count
	 * @return
	 */
	public List<KanbanRateDto> getOccupancyRateAvge(@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("count")int count);
}