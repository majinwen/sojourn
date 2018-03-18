package com.zra.vacancyreport.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.vacancyreport.dao.VacancyReportDao;
import com.zra.vacancyreport.entity.VacancyReportEntity;
import com.zra.vacancyreport.entity.dto.RoomInfoDto;
import com.zra.vacancyreport.entity.dto.VacancyReportDto;

/**
 * 空置报表基本服务类
 *
 * @author dongl50@ziroom.com
 * @date 2016/11/30 15:08
 */
@Service
public class VacancyReportService {

    private static final Logger LOGGER = Logger.getLogger(VacancyReportService.class);

    @Autowired
    private VacancyReportDao vacancyReportDao;

    /**
     * 根据项目获取所有的空置房源信息
     * @param projectId
     * @param date
     * @return
     */
    public List<VacancyReportDto> getVacancyReportByProjectId(String projectId, Date date) {

        return null;
    }

    /**
     * 根据日期获取所有的空置房源信息
     *
     * @param date
     * @return
     */
    public List<VacancyReportDto> listAllVacancyReportByDate(Date date) {

        return null;
    }
    
    /**
     * 
     * @author tianxf9
     * @param date
     * @return
     */
    public RoomInfoDto getRoomInfoService(String date,String projectId,String roomId) {
    	return this.vacancyReportDao.getRoomInfo(date,projectId,roomId);
    }

    /**
     *
     * @param date
     * @param projectId
     * @return
     */
    public List<VacancyReportEntity> getReportEntityByProjectId(String date, String projectId) {
        return this.vacancyReportDao.getReportEntityByProjectId(date, projectId);
    }
    
    /**
     * @author tianxf9
     * @param roomId
     * @param recordDate
     * @return
     */
    public VacancyReportEntity getVacRptEntity(String roomId,String recordDate) {
    	return this.vacancyReportDao.getReporEntityByDate(roomId, recordDate);
    }
    
    /**
     * 保存
     * @author tianxf9
     * @param entitys
     * @return
     */
    public int saveReportEntitys(List<VacancyReportEntity> entitys) {
    	return this.vacancyReportDao.saveReportEntitys(entitys);
    }
    
    /**
     * 判断是否是初始化
     * @author tianxf9
     * @return
     */
    public boolean isInit(String projectId,String roomId) {
    	int rows = this.vacancyReportDao.getTotalCount(projectId,roomId);
    	return rows<=0;
    }
    
    /**
     * 删除记录根据日期
     * @author tianxf9
     * @param DateStr
     * @return
     */
    public int delReportEntitysByDate(String dateStr,String projectId) {
    	return this.vacancyReportDao.delReportEntitysByRecordDate(dateStr,projectId);
    }

}
