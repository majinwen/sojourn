package com.ziroom.minsu.services.house.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.house.AbHouseRelateEntity;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>airbnb房源保存关联信息</p>
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
@Repository("house.abHouseRelateDao")
public class AbHouseRelateDao {

    private String SQLID="house.abHouseRelateDao.";

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
    public int save(AbHouseRelateEntity abHouseRelateEntity){
        if (Check.NuNStr(abHouseRelateEntity.getFid())){
            abHouseRelateEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID+"save", abHouseRelateEntity);
    }

    /**
     * 分页查询列表数据
     * @author jixd
     * @created 2017年04月17日 09:05:39
     * @param
     * @return
     */
    public PagingResult<AbHouseRelateVo> listRelateByPage(AbHouseDto abHouseDto){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(abHouseDto.getPage());
        pageBounds.setLimit(abHouseDto.getLimit());
        return mybatisDaoContext.findForPage(SQLID + "listRelateByPage",AbHouseRelateVo.class,abHouseDto.toMap(),pageBounds);
    }

    /**
     * 查找房源关联信息
     * @author jixd
     * @created 2017年04月17日 09:17:06
     * @param
     * @return
     */
    public AbHouseRelateVo findRelateByHouseFid(AbHouseDto abHouseDto){
        return mybatisDaoContext.findOne(SQLID + "findRelateByHouseFid",AbHouseRelateVo.class,abHouseDto);
    }

    /**
     * 根据房源fid查询相关记录
     * @author jixd
     * @created 2017年05月18日 17:43:13
     * @param
     * @return
     */
    public AbHouseRelateEntity findAbHouseByHouseInfo(AbHouseRelateEntity abHouseRelateEntity){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("houseFid",abHouseRelateEntity.getHouseFid());
        paramMap.put("roomFid",abHouseRelateEntity.getRoomFid());
        paramMap.put("rentWay",abHouseRelateEntity.getRentWay());
        return mybatisDaoContext.findOne(SQLID + "findAbHouseByHouseInfo",AbHouseRelateEntity.class,paramMap);
    }
    
    /**
     * 
     * 根据fid修改关系记录
     *
     * @author zyl
     * @created 2017年6月29日 上午10:08:17
     *
     * @param abHouseRelateVo
     * @return
     */
    public int updateHouseRelateByFid(AbHouseRelateVo abHouseRelateVo){
        return mybatisDaoContext.update(SQLID + "updateHouseRelateByFid", abHouseRelateVo);
    }

}
