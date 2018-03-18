package com.zra.vacancyreport.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.vacancyreport.entity.VacancyReportEntity;
import com.zra.vacancyreport.entity.dto.RoomInfoDto;

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
public interface VacancyReportDao {
	
	/**
	 * 获取房间信息
	 * @author tianxf9
	 * @param date
	 * @return
	 */
	public RoomInfoDto getRoomInfo(@Param("date")String date,@Param("projectId")String projectId,@Param("roomId")String roomId);
	
	/**
	 * 根据房间id和日期获取历史记录
	 * @author tianxf9
	 * @param roomId
	 * @param recordDate
	 * @return
	 */
	public VacancyReportEntity getReporEntityByDate(@Param("roomId")String roomId,@Param("recordDate")String recordDate);

	/**
	 * 根据日期和项目id查询历史记录
	 *
	 * @author dongl50
	 * @param date
	 * @param projectId
	 * @return
	 */
	List<VacancyReportEntity> getReportEntityByProjectId(@Param("date") String date, @Param("projectId") String projectId);
	
	/**
	 * 保存报表实体
	 * @author tianxf9
	 * @param entitys
	 * @return
	 */
	public int saveReportEntitys(List<VacancyReportEntity> entitys);
	
	/**
	 * 获取报表总条数
	 * @author tianxf9
	 * @return
	 */
	public int getTotalCount(@Param("projectId")String projectId,@Param("roomId")String roomId);
	
	/**
	 * 根据日期删除记录
	 * @author tianxf9
	 * @param dateStr
	 * @return
	 */
	public int delReportEntitysByRecordDate(@Param("dateStr")String dateStr,@Param("projectId")String projectId);
}
