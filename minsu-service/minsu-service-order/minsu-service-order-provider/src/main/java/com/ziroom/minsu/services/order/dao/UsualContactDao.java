package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.order.dto.UsualConRequest;

/**
 * <p>常用入住人</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.usualContactsDao")
public class UsualContactDao {
	
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(UsualContactDao.class);

    private String SQLID = "order.usualContactsDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 获取常用联系人
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @return
     */
    public List<UsualContactEntity> getUsualContacts() {
        return mybatisDaoContext.findAll(SQLID + "getUsualContacts", UsualContactEntity.class);
    }



    /**
     * 校验当前是否存在
     * @param tenantFid
     * @return
     */
    public Boolean checkTenant(String tenantFid){
        Map<String,Object> par = new HashMap<>();
        par.put("tenantFid",tenantFid);
        return mybatisDaoContext.countBySlave(SQLID + "checkTenant", par)>0?true:false;
    }




    /**
     * 获取当前订单的入住人
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param orderSn
     * @return
     */
    public List<UsualContactEntity> findOrderContactsByOrderSn(String orderSn) {
        return mybatisDaoContext.findAll(SQLID + "findOrderContactsByOrderSn", UsualContactEntity.class,orderSn);
    }



    /**
     * 获取用户的常用联系人
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param userUid 用户的uid
     * @return
     */
    public List<UsualContactEntity> getUsualContactsByUid(String userUid) {
        return mybatisDaoContext.findAll(SQLID + "getUsualContactsByUid", UsualContactEntity.class,userUid);
    }

    /**
	 * 按照fid集合查询 常用联系人列表
	 *
	 * @author yd
	 * @created 2016年4月1日 上午11:20:09
	 *
	 * @param usualConRequest
	 * @return
	 */
    public PagingResult<UsualContactEntity> findUsualContactsByFid(UsualConRequest usualConRequest) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(usualConRequest.getLimit());
        pageBounds.setPage(usualConRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "findUsualContactsByFid", UsualContactEntity.class, usualConRequest, pageBounds);
    }

    /**
     * 获取用户的默认联系人
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param userUid 用户的uid
     * @return
     */
    public UsualContactEntity getDefaultContactsByUid(String userUid) {
        return mybatisDaoContext.findOne(SQLID + "getDefaultContactsByUid", UsualContactEntity.class, userUid);
    }

    /**
     * 保存常用入住人
     * @author afi
     * @created 2016年3月31日 下午20:22:38
     * @param uaual
     * @return
     */
    public int insertUsualContact(UsualContactEntity uaual) {
        if(Check.NuNObj(uaual)){
        	
        	LogUtil.info(logger,"current uaual is null on insertUsualContact");
            throw new BusinessException("current uaual is null on insertUsualContact");
        }
        if(Check.NuNStr(uaual.getFid())){
            //设置编码
            uaual.setFid(UUIDGenerator.hexUUID());
        }
        if(Check.NuNStr(uaual.getUserUid())){
        	LogUtil.info(logger,"userUid is null on insertUsualContact");
            throw new BusinessException("userUid is null on insertUsualContact");
        }
        return mybatisDaoContext.save(SQLID + "insertUsualContact", uaual);
    }
    
    /**
     * 逻辑删除联系人
     * @author lishaochuan
     * @create 2016/12/1 9:46
     * @param 
     * @return 
     */
    public int deleteUsualContractByFid(String fid, String userUid, int isBooker){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fid", fid);
        paramMap.put("userUid", userUid);
        paramMap.put("isBooker", isBooker);
    	return mybatisDaoContext.update(SQLID+"deleteByFid", paramMap);
    }
    /**
     * 
     * update by fid
     *
     * @author yd
     * @created 2016年5月2日 上午11:54:10
     *
     * @param usualContactEntity
     * @return
     */
    public int updateByFid(UsualContactEntity usualContactEntity){
    	if(Check.NuNObj(usualContactEntity) || Check.NuNObj(usualContactEntity.getFid())){
    		LogUtil.info(logger,"current fid is null on updateByFid");
            throw new BusinessException("current fid is null on updateByFid");
        }
    	return mybatisDaoContext.update(SQLID+"updateByFid", usualContactEntity);
    }


    /**
     * 查询当前用户的预订人联系方式
     * @author lishaochuan
     * @create 2016/12/1 18:28
     * @param 
     * @return 
     */
    public UsualContactEntity getBookerContact(String userUid){
        return mybatisDaoContext.findOne(SQLID+"getBookerContact", UsualContactEntity.class, userUid);
    }

    /**
     * 根据fid查询联系人
     * @author lishaochuan
     * @create 2016/12/1 17:22
     * @param 
     * @return 
     */
    public UsualContactEntity getContactByFid(String fid, String userUid){
        Map<String, Object> map = new HashMap<>();
        map.put("fid", fid);
        map.put("userUid", userUid);
        return mybatisDaoContext.findOne(SQLID+"getContactByFid", UsualContactEntity.class, map);
    }


    /**
     * 查询是否有相同证件类型、证件号码的入住人
     * @author lishaochuan
     * @create 2016/12/1 15:28
     * @param 
     * @return 
     */
    public long checkHaveContact(UsualContactEntity usualContactEntity){
        return mybatisDaoContext.countBySlave(SQLID+"checkHaveContact", usualContactEntity.toMap());
    }


    /**
     * 查询是否有相同信息的入住人
     * @author lishaochuan
     * @create 2017/1/5 19:40
     * @param
     * @return
     */
    public UsualContactEntity getContactByInfo(Map<String, Object> map){
        return mybatisDaoContext.findOne(SQLID+"getContactByInfo", UsualContactEntity.class, map);
    }

}