package com.ziroom.minsu.services.house.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.AbHouseStatusEntity;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>airbnb房源状态</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @created 2017年04月15日 14:18:28
 * @since 1.0
 * @version 1.0
 */
@Repository("house.abHouseStatusDao")
public class AbHouseStatusDao {

    private String SQLID="house.abHouseStatusDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存实体
     * @author jixd
     * @created 2017年04月15日 14:27:02
     * @param
     * @return
     */
    public int save(AbHouseStatusEntity abHouseStatusEntity){
        if (Check.NuNStr(abHouseStatusEntity.getFid())){
            abHouseStatusEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID+"save", abHouseStatusEntity);
    }

    /**
     * 删除状态
     * @param abHouseDto
     * @return
     */
    public int deleteByLockTime(AbHouseDto abHouseDto){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("abSn",abHouseDto.getAbSn());
        return mybatisDaoContext.delete(SQLID + "deleteByLockTime",paramMap);
    }
}
