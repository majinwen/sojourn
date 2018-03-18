package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.paging.PagingResult;

import java.util.HashMap;
import java.util.List;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.service.houses.dto.RoomSmartLockDto;
import com.ziroom.zrp.service.houses.entity.IntellectSmartLockVo;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yd
 * @version 1.0
 * @Date Created in 2017年12月06日 20:10
 * @since 1.0
 */
@Repository("houses.intellectSmartLockDao")
public class IntellectSmartLockDao {

    private String SQLID = "houses.intellectSmartLockDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 根据fid 查询
     * @author yd
     * @created  
     * @param 
     * @return 
     */
    public IntellectSmartLockEntity getRoomSmartLockByFid(String fid) {
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomSmartLockByFid", IntellectSmartLockEntity.class, fid);
    }

    /**
     * 保存
     * @author yd
     * @created
     * @param 
     * @return 
     */
    public int saveRoomSmartLock(IntellectSmartLockEntity roomSmartLock){
        if (Check.NuNStr(roomSmartLock.getFid())){
            roomSmartLock.setFid(UUIDGenerator.hexUUID());
        }

        return mybatisDaoContext.save(SQLID + "saveRoomSmartLock",roomSmartLock);
    }

    /**
     *
     * 条件删除智能锁密码
     *
     * @author bushujie
     * @created 2017年12月7日 上午11:38:44
     *
     * @param roomSmartLock
     */
    public int delRoomSmartLock(IntellectSmartLockEntity roomSmartLock){
    	return mybatisDaoContext.update(SQLID+"delRoomSmartLock", roomSmartLock);
    }

    /**
     *
     * 条件查询要删除智能锁列表
     *
     * @author bushujie
     * @created 2017年12月7日 上午11:50:34
     *
     * @param intellectSmartLockEntity
     * @return
     */
    public List<IntellectSmartLockEntity> getDelRoomSmartLockByCondition(IntellectSmartLockEntity intellectSmartLockEntity){
    	return mybatisDaoContext.findAll(SQLID, IntellectSmartLockEntity.class, intellectSmartLockEntity);
    }

    public PagingResult<IntellectSmartLockVo> findByPaging(RoomSmartLockDto roomStmartDto) {

        Map<String,String> searchMap = null;
        try {
            searchMap = BeanUtils.describe(roomStmartDto);
            searchMap.put("isDel", "0");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(roomStmartDto.getLimit());
        pageBounds.setPage(roomStmartDto.getPage());
        return mybatisDaoContext.findForPage(SQLID+"findByPaging",IntellectSmartLockVo.class,searchMap,pageBounds);
    }
    
    /**
     * 
     * 更新智能锁密码下发状态
     *
     * @author bushujie
     * @created 2017年12月12日 上午11:39:37
     *
     * @param paramMap
     * @return
     */
    public int upSmartLockStatusByServiceId(Map<String, Object> paramMap){
    	return mybatisDaoContext.update(SQLID+"upSmartLockStatusByServiceId", paramMap);
    }

    /**
     *
     * 获取需要补偿的失败记录
     *
     * @author zhangyl2
     * @created 2017年12月20日 14:39
     * @param
     * @return
     */
    public List<IntellectSmartLockEntity> getFailSmartLockRecord(List<Integer> sourceTypes){
        Map<String, Object> map = new HashMap<>();
        map.put("sourceTypes", sourceTypes);
        return mybatisDaoContext.findAll(SQLID+"getFailSmartLockRecord", map);
    }

    /**
     *
     * 根据fid更新智能锁
     *
     * @author zhangyl2
     * @created 2017年12月20日 14:41
     * @param
     * @return
     */
    public int updateByPrimaryKeySelective(IntellectSmartLockEntity entity){
        if(Check.NuNStr(entity.getFid())){
            return 0;
        }else{
            return mybatisDaoContext.update(SQLID+"updateByPrimaryKeySelective", entity);
        }
    }
    
    /**
     * 
     * 更新智能锁密码下发状态
     *
     * @author bushujie
     * @created 2017年12月12日 上午11:39:37
     *
     * @param paramMap
     * @return
     */
    public int upSmartLockStatusByServiceIdLimit(Map<String, Object> paramMap){
    	return mybatisDaoContext.update(SQLID+"upSmartLockStatusByServiceIdLimit", paramMap);
    }

}
