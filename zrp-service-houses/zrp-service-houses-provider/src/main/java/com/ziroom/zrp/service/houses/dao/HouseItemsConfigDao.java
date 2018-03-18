package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.HouseItemsConfigEntity;
import com.ziroom.zrp.service.houses.entity.ExtHouseItemsConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>房型物品dao</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 11:06
 * @since 1.0
 */
@Repository("houses.houseItemsConfigDao")
public class HouseItemsConfigDao {

    private String SQLID = "houses.houseItemsConfigDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 根据房型查询物品列表
     * @author jixd
     * @created 2017年10月30日 12:02:26
     * @param
     * @return
     */
    public List<HouseItemsConfigEntity> listHouseItemsByHouseType(String houseTypeId){
        return mybatisDaoContext.findAll(SQLID + "listHouseItemsByHouseType",houseTypeId);
    }


    public List<ExtHouseItemsConfigVo> listHouseItemsExtByHouseTypeId(String houseTypeId){
        return mybatisDaoContext.findAll(SQLID + "listHouseItemsExtByHouseTypeId",houseTypeId);
    }



}
