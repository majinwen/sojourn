package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.CityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("houses.cityDao")
public class CityDao {


    private String SQLID = "houses.cityDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    public CityEntity selectByPrimaryKey(String fid) {
        CityEntity cityEntity = mybatisDaoContext.findOneSlave(SQLID + "selectByPrimaryKey", CityEntity.class, fid);
        return cityEntity;
    }

    /**
      * @description: 查询城市集合
      * @author: lusp
      * @date: 2017/10/19 下午 16:11
      * @params:
      * @return: List<CityEntity>
      */
    public List<CityEntity> findCityList(){
        return mybatisDaoContext.findAll(SQLID+"findCityList",CityEntity.class);
    }





}