package com.ziroom.zrp.service.houses.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.dao.mybatis.paginator.domain.PageList;
import com.ziroom.zrp.houses.entity.HouseSignInviteRecordEntity;
import com.ziroom.zrp.service.houses.dto.QueryInvitereCordParamDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>邀请记绿DAO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年9月20日
 * @since 1.0
 */
@Repository("houses.houseSignInviteRecordDao")
public class HouseSignInviteRecordDao extends PageList{

	private static final long serialVersionUID = 5970057319027444950L;

	private String SQLID = "houses.houseSignInviteRecordDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    public int insert(HouseSignInviteRecordEntity record) {
        return mybatisDaoContext.save(SQLID+"insert", record);

    }
    //更新签约邀请签约成功 add by xiangb
    public int updateIsDealByContractId(String contractId){
    	if(Check.NuNStr(contractId)){
    		return 0;
    	}
    	Map<String,Object> map = new HashMap<>();
    	map.put("contractId", contractId);
    	return mybatisDaoContext.update(SQLID+"updateIsDealByContractId", map);
    }
    
    public HouseSignInviteRecordEntity selectByPrimaryKey(Long id) {
        return null;
    }

    public List<Map> countByRoomIds(List<String> roomIdList)  {
        Map<String, Object> map = new HashMap<>();
        map.put("roomIds", roomIdList);
        List<Map> list = mybatisDaoContext.findAll(SQLID + "countByRoomIds", Map.class, map);
        return list;
    }



    public PagingResult<HouseSignInviteRecordEntity> findListByRoomId(QueryInvitereCordParamDto paramDto) {
        PageBounds pageBounds=new PageBounds();
    	pageBounds.setPage(paramDto.getPage());
    	pageBounds.setLimit(paramDto.getLimit());
    	return mybatisDaoContext.findForPage(SQLID+"findListByRoomId", HouseSignInviteRecordEntity.class, paramDto, pageBounds);
    }
    

}