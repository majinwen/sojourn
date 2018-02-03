package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.file.FileLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/15
 */
@Repository("basedata.fileLogDao")
public class FileLogDao {

    private String SQLID = "basedata.fileLogDao.";

    @Autowired
    @Qualifier("basedata.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 插入操作景点商圈的日志记录
     * @Author lunan【lun14@ziroom.com】
     * @Date 2016/11/15 16:19
     */
    public void saveOperatelog(FileLogEntity fileLogEntity){
        mybatisDaoContext.save(SQLID+"saveOperatelog",fileLogEntity);
    }

}
