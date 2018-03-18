package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.zrp.service.trading.dto.surrender.SurRoomListDto;
import com.ziroom.zrp.service.trading.dto.surrender.SurRoomListReturnDto;
import com.ziroom.zrp.service.trading.dto.surrender.SurrenderCostNextDto;
import com.ziroom.zrp.service.trading.pojo.CalSurrenderPojo;
import com.ziroom.zrp.trading.entity.SurrenderEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月11日 20时25分
 * @Version 1.0
 * @Since 1.0
 */
@Repository("trading.surrenderDao")
public class SurrenderDao {
    private String SQLID = "com.ziroom.zrp.service.trading.dao.SurrenderDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 查询父合同下所有信息
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时22分30秒
     */
    public List<CalSurrenderPojo> getSurAndSurCostInfo(String surParentId) {
        return mybatisDaoContext.findAll(SQLID + "getSurAndSurCostInfo", CalSurrenderPojo.class, surParentId);
    }

    /**
     * 查询指定合同下所有信息
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时23分07秒
     */
    public CalSurrenderPojo getSurAndSurCostInfoByConId(String contractId) {
        return mybatisDaoContext.findOne(SQLID + "getSurAndSurCostInfoByConId", CalSurrenderPojo.class, contractId);
    }

    /**
     * 根据父解约协议获取解约实体
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时23分47秒
     */
    public List<SurrenderEntity> getSurListByParentId(String surParentId) {
        return mybatisDaoContext.findAll(SQLID + "getSurListByParentId", SurrenderEntity.class, surParentId);
    }

    /**
     * 更新
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时23分53秒
     */
    public Integer updateById(SurrenderCostNextDto paramDto) {
        return mybatisDaoContext.update(SQLID + "updateById", paramDto);
    }

    /**
     * 根据合同id获取解约协议实体
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时25分01秒
     */
    public SurrenderEntity getSurrenderByConId(String contractId) {
        return mybatisDaoContext.findOne(SQLID + "getSurrenderByConId", SurrenderEntity.class, contractId);
    }

    /**
     * 作废解约协议
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时25分12秒
     */
    public int deleteBySurrenderId(String surrenderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("surrenderId", surrenderId);
        return mybatisDaoContext.update(SQLID + "deleteBySurrenderId", map);
    }

    /**
     * 解约协议更改状态
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时25分28秒
     */
    public int updateForDoSurrender(String surrenderId, String zoId, String zoName, int code) {
        Map<String, Object> map = new HashMap<>();
        map.put("surrenderId", surrenderId);
        map.put("zoId", zoId);
        map.put("zoName", zoName);
        map.put("submitStatus", code);
        return mybatisDaoContext.update(SQLID + "updateForDoSurrender", map);
    }

    /**
     * 获取批量解约的房间
     *
     * @Author: wangxm113
     * @Date: 2017年11月04日 17时26分00秒
     */
    public PagingResult<SurRoomListReturnDto> getSurRoomList(SurRoomListDto paramDto) {
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(paramDto.getPageNum() == null ? 1 : paramDto.getPageNum());
        pageBounds.setLimit(paramDto.getRows() == null ? 100 : paramDto.getRows());
        return mybatisDaoContext.findForPage(SQLID + "getSurRoomList", SurRoomListReturnDto.class,
                paramDto.getSurParentId(), pageBounds);
    }

    public int saveSurrrender(SurrenderEntity surrender){
    	if(Check.NuNObj(surrender)){
    		return 0;
    	}
    	if(Check.NuNStr(surrender.getSurrenderId())){
    		surrender.setSurrenderId(UUID.randomUUID()+"");
    	}
    	return mybatisDaoContext.save(SQLID + "saveSurrender", surrender);
    }

    /**
     * 修改解约协议时点提交审核
     *
     * @Author: wangxm113
     * @Date: 2017年11月07日 16时01分49秒
     */
    public Integer editCommitAudit(String contractId, String surParentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("contractId", contractId);
        map.put("surParentId", surParentId);
        return mybatisDaoContext.update(SQLID + "editCommitAudit", map);
    }
    /**
     * <p>解约申请超过45天则更新解约申请时间</p>
     * @author xiangb
     * @created 2017年11月9日
     * @param
     * @return
     */
    public Integer updateSurrenderApplyTime(Integer time,String contractId){
    	Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("contractId", contractId);
        return mybatisDaoContext.update(SQLID + "updateSurrenderApplyTime", map);
    }
	/**
	 * <p>根据父合同号查询已解约次数</p>
	 * @author xiangb
	 * @created 2017年11月12日
	 * @param
	 * @return
	 */
    public Integer selectSurrenderTimeByCode(String contractCode){
    	if(Check.NuNStr(contractCode)){
    		return 0;
    	}
    	 Map<String, Object> map = new HashMap<>();
         map.put("contractCode", contractCode);
         return mybatisDaoContext.findOne(SQLID+"selectSurrenderTimeByCode",Integer.class,map);
    }
    /**
     * <p>更新解约协议</p>
     * @author xiangb
     * @created 2017年11月14日
     * @param
     * @return
     */
    public Integer updateSurrender(SurrenderEntity surrender){
    	return mybatisDaoContext.update(SQLID+"updateSurrender", surrender);
    }
    /**
     * <p>根据ID查询解约协议</p>
     * @author xiangb
     * @created 2017年11月16日
     * @param
     * @return
     */
    public SurrenderEntity findSurrenderById(String surrenderId){
    	return mybatisDaoContext.findOne(SQLID+"findSurrenderById", SurrenderEntity.class,surrenderId);
    }
    /**
     * <p>删除合同号对应的解约协议</p>
     * @author xiangb
     * @created 2017年12月8日
     * @param
     * @return
     */
    public int deleteSurrenderByContractId(String contractId){
    	if(Check.NuNStr(contractId)){//防止都删除了。
    		return 0;
    	}
    	Map<String, Object> map = new HashMap<>();
        map.put("contractId", contractId);
    	return mybatisDaoContext.update(SQLID+"deleteSurrenderByContractId", map);
    }

    /**
     * 计算指定合同的退租交割赔偿费
     *
     * @Author: wangxm113
     * @Date: 2017年12月19日 20时30分22秒
     */
    public BigDecimal getTZJGPCF(String contractId) {
        return mybatisDaoContext.findOne(SQLID + "getTZJGPCF", BigDecimal.class, contractId);
    }
}
