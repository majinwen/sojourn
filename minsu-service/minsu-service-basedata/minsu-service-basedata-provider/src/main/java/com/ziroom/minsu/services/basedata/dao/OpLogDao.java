package com.ziroom.minsu.services.basedata.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.services.basedata.dto.OpLogRequest;
import com.ziroom.minsu.services.basedata.entity.OpLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>操作日志</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017/5/8.
 * @version 1.0
 * @since 1.0
 */
@Repository("basedata.opLogDao")
public class OpLogDao {


    private String SQLID="basedata.opLogDao.";

    @Autowired
    @Qualifier("ups.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     *
     * 插入操作日志
     *
     * @author lusp
     * @created 2017/5/8
     *
     * @param sysOpLogEntity
     */
    public void insertSysOpLogEntity(OpLogEntity sysOpLogEntity){
    	if (Check.NuNStr(sysOpLogEntity.getFid())){
    		sysOpLogEntity.setFid(UUIDGenerator.hexUUID());
		}
        mybatisDaoContext.save(SQLID+"insertOpLogEntity", sysOpLogEntity);
    }
    
    /**
    *
    * 查询操作日志
    *
    * @author lusp
    * @created 2017/5/8
    *
    * @param opLogRequest
    */
   public PagingResult<OpLogVo> findSysOpLogList(OpLogRequest opLogRequest){
	   
	   PageBounds pageBounds = new PageBounds();
	   pageBounds.setLimit(opLogRequest.getLimit());
	   pageBounds.setPage(opLogRequest.getPage());
	   return mybatisDaoContext.findForPage(SQLID + "findOpLogByCondition", OpLogVo.class, opLogRequest,
				pageBounds);
	}

}
