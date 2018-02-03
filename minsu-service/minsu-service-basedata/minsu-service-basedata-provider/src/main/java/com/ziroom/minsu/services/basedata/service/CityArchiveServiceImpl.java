package com.ziroom.minsu.services.basedata.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.HotRegionEntity;
import com.ziroom.minsu.entity.file.FileCityEntity;
import com.ziroom.minsu.entity.file.FileLogEntity;
import com.ziroom.minsu.entity.file.FileRegionEntity;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.dao.*;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.entity.CityArchivesVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/8
 */
@Service("basedata.cityArchiveServiceImpl")
public class CityArchiveServiceImpl {

    @Resource(name = "basedata.fileCityDao")
    private FileCityDao fileCityDao;

    @Resource(name = "basedata.fileRegionDao")
    private FileRegionDao fileRegionDao;

    @Resource(name = "basedata.fileRegionItemsDao")
    private FileRegionItemsDao fileRegionItemsDao;

    @Resource(name = "basedata.fileLogDao")
    private FileLogDao fileLogDao;

    @Resource(name = "basedata.hotRegionDao")
    private HotRegionDao hotRegionDao;

    /**
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/8 14:13
     * @param
     * @return
     */
    public PagingResult<CityArchivesVo> searchArchivesList(CityArchivesRequest cityArchivesRequest){
        return fileCityDao.searchArchivesList(cityArchivesRequest);
    }

    /**
     * 查询城市景点商圈
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 10:33
     * @param
     * @return
     */
    public List<CityArchivesVo> getRegionList(CityArchivesRequest cityArchivesRequest){
        return fileRegionDao.getRegionList(cityArchivesRequest);
    }

    /**
     * 查询景点商圈下辖推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/11 11:52
     */
    public List<CityArchivesVo> getRegionItems(CityArchivesRequest cityArchivesRequest){
        return fileRegionDao.getRegionItems(cityArchivesRequest);
    }

    /**
     * 根据景点商圈fid查询其对应的推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/14 9:47
     */
    public List<FileRegionItemsEntity> getItemsByHotRegionFid(CityArchivesRequest cityArchivesRequest){
        return fileRegionItemsDao.getItemsByHotRegionFid(cityArchivesRequest);
    }

    /**
     * 根据regionFid查询景点商圈记录
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 15:13
     */
    public FileRegionEntity getRegion(FileRegionEntity fileRegionEntity){
        return fileRegionDao.getRegion(fileRegionEntity);
    }

    /**
     * 插入一条景点商圈内容记录
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 15:59
     */
    public void saveRegion(FileRegionEntity fileRegionEntity,String cityCode){
        fileRegionDao.saveRegion(fileRegionEntity);
        //更新其他表的关联字段
        fileCityDao.upCityIsFile(cityCode);
        fileCityDao.updateHotConfRegion(fileRegionEntity.getHotRegionFid());
        //在日志表中插入操作日志 0：城市档案，1：商圈景点档案，2：推荐项目(fid对应着操作项去选择)
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFid(UUIDGenerator.hexUUID());
        fileLogEntity.setCityCode(cityCode);
        fileLogEntity.setLogType(1);
        fileLogEntity.setTypeFid(fileRegionEntity.getFid());
        fileLogEntity.setOperatorFid(fileRegionEntity.getCreateFid());
        fileLogDao.saveOperatelog(fileLogEntity);
    }

    /**
     * 更新景点商圈内容
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 19:53
     */
    public void updateRegion(FileRegionEntity fileRegionEntity){
        fileRegionDao.updateRegion(fileRegionEntity);
        //在日志表中插入操作日志 0：城市档案，1：商圈景点档案，2：推荐项目(fid对应着操作项去选择)
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFid(UUIDGenerator.hexUUID());
        //设置log表城市code
        HotRegionEntity region = hotRegionDao.findHotRegionByFid(fileRegionEntity.getHotRegionFid());
        fileLogEntity.setCityCode(region.getCityCode());
        fileLogEntity.setLogType(1);
        fileLogEntity.setTypeFid(fileRegionEntity.getFid());
        fileLogEntity.setOperatorFid(fileRegionEntity.getCreateFid());
        fileLogDao.saveOperatelog(fileLogEntity);
    }

    /**
     * 根据推荐项fid查询一条推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/9 19:54
     */
    public FileRegionItemsEntity getRegionItem(String itemFid){
        return fileRegionItemsDao.getRegionItem(itemFid);
    }

    /**
     * 保存推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 18:52
     */
    public void saveRegionItem(FileRegionItemsEntity fileRegionItemsEntity){
        fileRegionItemsDao.saveRegionItem(fileRegionItemsEntity);
        //在日志表中插入操作日志 0：城市档案，1：商圈景点档案，2：推荐项目(fid对应着操作项去选择)
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFid(UUIDGenerator.hexUUID());
        //设置城市code
        HotRegionEntity region = hotRegionDao.findHotRegionByFid(fileRegionItemsEntity.getHotRegionFid());
        fileLogEntity.setCityCode(region.getCityCode());
        fileLogEntity.setLogType(2);
        fileLogEntity.setTypeFid(fileRegionItemsEntity.getFid());
        fileLogEntity.setOperatorFid(fileRegionItemsEntity.getCreateFid());
        fileLogDao.saveOperatelog(fileLogEntity);
    }

    /**
     * 修改推荐项
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 18:53
     */
    public void updateRegionItem(FileRegionItemsEntity fileRegionItemsEntity){
        fileRegionItemsDao.updateRegionItem(fileRegionItemsEntity);
        //在日志表中插入操作日志 0：城市档案，1：商圈景点档案，2：推荐项目(fid对应着操作项去选择)
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFid(UUIDGenerator.hexUUID());
        //设置城市code
        HotRegionEntity region = hotRegionDao.findHotRegionByFid(fileRegionItemsEntity.getHotRegionFid());
        fileLogEntity.setCityCode(region.getCityCode());
        fileLogEntity.setLogType(2);
        fileLogEntity.setTypeFid(fileRegionItemsEntity.getFid());
        fileLogEntity.setOperatorFid(fileRegionItemsEntity.getCreateFid());
        fileLogDao.saveOperatelog(fileLogEntity);
    }

    /**
     * 保存城市档案
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 18:53
     */
    public void saveArchive(FileCityEntity fileCityEntity,String cityCode){
        fileCityDao.saveArchive(fileCityEntity);
        //更新其他表的关联字段
        fileCityDao.upCityIsFile(cityCode);
        //在日志表中插入操作日志 0：城市档案，1：商圈景点档案，2：推荐项目(fid对应着操作项去选择)
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFid(UUIDGenerator.hexUUID());
        //设置城市code
        fileLogEntity.setCityCode(cityCode);
        fileLogEntity.setLogType(0);
        fileLogEntity.setTypeFid(fileCityEntity.getFid());
        fileLogEntity.setOperatorFid(fileCityEntity.getCreateFid());
        fileLogDao.saveOperatelog(fileLogEntity);
    }

    /**
     * 根据fid查询一条城市档案内容
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/10 19:05
     */
    public FileCityEntity getArchive(String fid){
        return fileCityDao.getFile(fid);
    }

    public void updateArchive(FileCityEntity fileCityEntity){
        fileCityDao.updateFile(fileCityEntity);
        //在日志表中插入操作日志 0：城市档案，1：商圈景点档案，2：推荐项目(fid对应着操作项去选择)
        FileLogEntity fileLogEntity = new FileLogEntity();
        fileLogEntity.setFid(UUIDGenerator.hexUUID());
        //设置城市code
        fileLogEntity.setCityCode(fileCityEntity.getCityCode());
        fileLogEntity.setLogType(0);
        fileLogEntity.setTypeFid(fileCityEntity.getFid());
        fileLogEntity.setOperatorFid(fileCityEntity.getCreateFid());
        fileLogDao.saveOperatelog(fileLogEntity);
    }
}
