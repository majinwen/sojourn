package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.paging.PagingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ziroom.zrp.houses.entity.IntellectSmartLockEntity;
import com.ziroom.zrp.service.houses.dao.IntellectSmartLockDao;
import com.ziroom.zrp.service.houses.dto.RoomSmartLockDto;
import com.ziroom.zrp.service.houses.entity.IntellectSmartLockVo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
 * @Date Created in 2017年12月06日 20:39
 * @since 1.0
 */
@Service("houses.intellectSmartLockServiceImpl")
public class IntellectSmartLockServiceImpl {

    @Resource(name = "houses.intellectSmartLockDao")
    private IntellectSmartLockDao intellectSmartLockDao;

    public int saveRoomSmartLock(IntellectSmartLockEntity roomSmartLock){
        return intellectSmartLockDao.saveRoomSmartLock(roomSmartLock);
    }

    /**
     * 分页查询
     * @param roomStmartDto
     * @return
     */
    public PagingResult<IntellectSmartLockVo> paging(RoomSmartLockDto roomStmartDto) {

        return intellectSmartLockDao.findByPaging(roomStmartDto);
    }

    /**
     * ]
     * 条件删除智能锁密码
     *
     * @author bushujie
     * @created 2017年12月7日 上午11:53:51
     *
     * @param roomSmartLock
     * @return
     */
    public int delRoomSmartLock(IntellectSmartLockEntity roomSmartLock){
    	return intellectSmartLockDao.delRoomSmartLock(roomSmartLock);
    }

    /**
     *
     * 查询要删除的智能锁密码
     *
     * @author bushujie
     * @created 2017年12月7日 上午11:54:43
     *
     * @param intellectSmartLockEntity
     * @return
     */
    public List<IntellectSmartLockEntity> getDelRoomSmartLockByCondition(IntellectSmartLockEntity intellectSmartLockEntity){
    	return intellectSmartLockDao.getDelRoomSmartLockByCondition(intellectSmartLockEntity);
    }
    
    /**
     * 
     * 更新智能锁下发状态
     *
     * @author bushujie
     * @created 2017年12月12日 上午11:40:40
     *
     * @param paramMap
     * @return
     */
    public int upSmartLockStatusByServiceId(Map<String, Object> paramMap){
    	
    	//更新所有同一个service_id的记录为失效
    	Map<String, Object> pMap=new HashMap<String, Object>();
    	pMap.put("serviceId", paramMap.get("serviceId"));
    	pMap.put("status", 3);//失效状态
    	intellectSmartLockDao.upSmartLockStatusByServiceId(pMap);
    	return intellectSmartLockDao.upSmartLockStatusByServiceIdLimit(paramMap);
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
        return intellectSmartLockDao.getFailSmartLockRecord(sourceTypes);
    }

    /**
     *
     * 根据fid更新智能锁
     *
     * @author zhangyl2
     * @created 2017年12月20日 14:44
     * @param
     * @return
     */
    public int updateIntellectSmartLockEntity(IntellectSmartLockEntity entity){
        return intellectSmartLockDao.updateByPrimaryKeySelective(entity);
    }
}
