package com.ziroom.zrp.service.trading.dao;

import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonSearchDto;
import com.ziroom.zrp.service.trading.entity.CheckSignCusInfoVo;
import com.ziroom.zrp.service.trading.entity.RentCheckinPersonVo;
import com.ziroom.zrp.service.trading.pojo.PersonAndSharerPojo;
import com.ziroom.zrp.service.trading.utils.MapBeanUtil;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

import com.zra.common.exception.ZrpServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 入住人信息(同步友家客户库的用户信息表)
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017年09月12日
 * @version 1.0
 * @since 1.0
 */
@Repository("trading.rentCheckinPersonDao")
public class RentCheckinPersonDao {

	private String SQLID = "trading.rentCheckinPersonDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * @description: 根据用户uid查询用户验签信息
	 * @author: lusp
	 * @date: 2017/9/12 15:44
	 * @params: customerUid
	 * @return: CheckSignCusInfoVo
	 */
	public CheckSignCusInfoVo findCheckSignCusInfoVoByUid(RentContractEntity rentContractEntity){
		return mybatisDaoContext.findOne(SQLID + "findCheckSignCusInfoVoByUid",CheckSignCusInfoVo.class,rentContractEntity);
	}
	
	
	public int saveCheckinPerson(RentCheckinPersonEntity rentCheckinPersonEntity){
		if(Check.NuNStr(rentCheckinPersonEntity.getUid())){
			throw new ZrpServiceException("入住人UID为空");
		}
		return mybatisDaoContext.save(SQLID+"saveCheckinPerson", rentCheckinPersonEntity);
	}
	
	public RentCheckinPersonEntity findCheckinPersonByContractId(String contractId){
		return mybatisDaoContext.findOne(SQLID+"findCheckinPersonByContractId",RentCheckinPersonEntity.class, contractId);
	}

	/**
	 *
	 * 根据合同id和uid查询入住人，为了兼容老数据的企业合同id是一合同对多个入住人的问题
	 *
	 * @author zhangyl2
	 * @created 2018年01月26日 21:06
	 * @param
	 * @return
	 */
    public RentCheckinPersonEntity findCheckinPerson(String contractId, String uid){
        Map<String, Object> map = new HashMap<>();
        map.put("contractId", contractId);
        map.put("uid", uid);
        return mybatisDaoContext.findOne(SQLID+"findCheckinPerson",RentCheckinPersonEntity.class, map);
    }
	
	public int updateCheckinPerson(RentCheckinPersonEntity rentCheckinPersonEntity){
		return mybatisDaoContext.update(SQLID + "updateCheckinPerson", rentCheckinPersonEntity);
	}
	
	/**
	 * 
	 * 分页查询入住人列表
	 *
	 * @author bushujie
	 * @created 2017年12月5日 下午3:33:28
	 *
	 * @param dto
	 * @return
	 */
	public PagingResult<RentCheckinPersonVo> searchByCriteria(RentCheckinPersonSearchDto dto){
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(dto.getRows());
		pageBounds.setPage(dto.getPage());		
		return mybatisDaoContext.findForPage(SQLID+"searchByCriteria", RentCheckinPersonVo.class,MapBeanUtil.transBean2Map(dto), pageBounds);
	}

	/**
	 *
	 * 查询历史入住人
	 *
	 * @author zhangyl2
	 * @created 2017年12月07日 18:55
	 * @param
	 * @return
	 */
    public PagingResult<PersonAndSharerPojo> selectHistoryPersonAndSharer(RentCheckinPersonSearchDto dto) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(dto.getRows());
        pageBounds.setPage(dto.getPage());
        return mybatisDaoContext.findForPage(SQLID + "selectHistoryPersonAndSharer", PersonAndSharerPojo.class, dto, pageBounds);
    }


    /**
     *
     * 查询历史入住人
     *
     * @author zhangyl2
     * @created 2017年12月07日 18:55
     * @param
     * @return
     */
    public RentCheckinPersonEntity selectHistoryCheckinPerson(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return mybatisDaoContext.findOne(SQLID + "selectHistoryCheckinPerson", RentCheckinPersonEntity.class, map);
    }

	public List<RentContractEntity> findCheckInPersonContractList(String uid) {
		return mybatisDaoContext.findAll(SQLID + "findCheckInPersonContractList", uid);
	}

	/**
	 * 删除合住人并且记录操作人信息
	 * @param personEntity
	 * @return
	 */
	public int deleteByContractIdAndRecord(RentCheckinPersonEntity personEntity) {
		return mybatisDaoContext.update(SQLID + "deleteByContractIdAndRecord", personEntity);
	}
}
