package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.ItemListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>物品清单表dao</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 11:07
 * @since 1.0
 */
@Repository("houses.itemListDao")
public class ItemListDao {
    private String SQLID = "houses.itemListDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 查找所有物品
     * @author jixd
     * @created 2017年10月30日 11:13:30
     * @param
     * @return
     */
    public List<ItemListEntity> listItems(String itemType,String itemName){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("itemType",itemType);
        paramMap.put("itemName",itemName);
        return mybatisDaoContext.findAll(SQLID + "listItems",ItemListEntity.class,paramMap);
    }

    /**
     * 根据fid查询
     * @author jixd
     * @created 2017年11月02日 18:25:00
     * @param
     * @return
     */
    public ItemListEntity findByFid(String fid){
        return mybatisDaoContext.findOne(SQLID +"findByFid",ItemListEntity.class,fid);
    }

}
