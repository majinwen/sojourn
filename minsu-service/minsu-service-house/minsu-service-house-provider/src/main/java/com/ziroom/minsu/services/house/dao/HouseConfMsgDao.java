package com.ziroom.minsu.services.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseConfParamsDto;
import com.ziroom.minsu.services.house.entity.HouseBaseExtVo;
import com.ziroom.minsu.services.house.entity.HouseConfVo;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>房源配置信息dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
@Repository("house.houseConfMsgDao")
public class HouseConfMsgDao {


    private String SQLID="house.houseConfMsgDao.";

    @Autowired
    @Qualifier("house.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;
    
    /**
     * 
     * 新增房源配置信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param houseConfMsg
     * @return
     */
    public int insertHouseConfMsg(HouseConfMsgEntity houseConfMsg) {
		return mybatisDaoContext.save(SQLID+"insertHouseConfMsg", houseConfMsg);
	}
    
    /**
     * 
     * 更新房源配置信息
     *
     * @author liujun
     * @created 2016年4月1日 下午3:56:44
     *
     * @param houseConfMsg
     * @return
     */
    public int updateHouseConfMsg(HouseConfMsgEntity houseConfMsg) {
    	return mybatisDaoContext.update(SQLID+"updateHouseConfMsgByFid", houseConfMsg);
    }

	public int updateHouseConfMsgByother(HouseConfMsgEntity houseConfMsg){
		return mybatisDaoContext.update(SQLID+"updateHouseConfMsgByother",houseConfMsg);
	}

	/**
	 * @description: 根据房源fid更新配置信息
	 * @author: lusp
	 * @date: 2017/8/2 15:49
	 * @params: houseConfMsg
	 * @return:
	 */
	public int updateHouseConfMsgByHouseBaseFid(HouseConfMsgEntity houseConfMsg){
		return mybatisDaoContext.update(SQLID+"updateHouseConfMsgByHouseBaseFid",houseConfMsg);
	}

    /**
     * 
     * 根据房源fid逻辑删除房源配置
     *
     * @author jixd
     * @created 2016年6月13日 下午5:59:41
     *
     * @param houseFid
     * @return
     */
    public int deleteHouseConfMsgByHouseFid(String houseFid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fid", houseFid);
		return mybatisDaoContext.update(SQLID + "deleteHouseConfMsgByHouseFid", map);
	}

	/**
	 * 通过房源基础信息逻辑id和房源配置code集合查询房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月3日 下午6:07:42
	 *
	 * @param houseBaseExt
	 * @return
	 */
	public List<HouseConfMsgEntity> findConfListByHouseFidAndCodeList(HouseBaseExtVo houseBaseExt) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("houseBasefid", houseBaseExt.getHouseBaseFid());
		map.put("houseConfList", houseBaseExt.getHouseConfList());
		return mybatisDaoContext.findAll(SQLID + "findConfListByHouseFidAndCodeList",
				HouseConfMsgEntity.class, map);
	}
	
	/**
	 * 
	 * 获取房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年4月6日 下午10:29:27
	 *
	 * @param fid
	 * @return
	 */
	public List<HouseConfMsgEntity> findHouseConfList(String fid){
		return mybatisDaoContext.findAll(SQLID+"findHouseConfList", HouseConfMsgEntity.class, fid);
	}

	/**
	 * 为价格不同设定提供的配置项集合查询
	 * @Author lunan【lun14@ziroom.com】
	 * @Date 2016/12/3 12:52
	 */
	public List<HouseConfMsgEntity> findGapFlexPriceList(HouseConfMsgEntity confMsgEntity){
		return mybatisDaoContext.findAll(SQLID+"findGapFlexPriceList",HouseConfMsgEntity.class,confMsgEntity);
	}

	/**
	 * 根据房源基础信息逻辑id与枚举code查询房源配置集合
	 *
	 * @author liujun
	 * @created 2016年4月13日 上午11:19:08
	 *
	 * @param houseBaseFid
	 * @param enumCode
	 * @return
	 */
	@Deprecated
	public List<String> findHouseConfListByFidAndCode(String houseBaseFid, String enumCode) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("houseBaseFid", houseBaseFid);
		map.put("dicCode", enumCode);
		return mybatisDaoContext.findAll(SQLID+"findHouseConfListByFidAndCode", String.class, map);
	}
	
	/**
	 * 
	 * 模糊查询房源配置信息
	 *
	 * @author bushujie
	 * @created 2016年5月5日 下午11:05:36
	 *
	 * @param paramMap
	 * @return
	 */
	public  List<HouseConfVo> findHouseConfVoList(Map<String, Object> paramMap){
		return mybatisDaoContext.findAll(SQLID+"findHouseConfListByCode", HouseConfVo.class, paramMap);
	}
	
	/**
	 * 
	 * 删除房源配置信息根据code
	 *
	 * @author bushujie
	 * @created 2016年5月28日 下午4:47:40
	 *
	 * @param houseBaseFid
	 * @param code
	 */
	public  void delHouseConfByCode(String houseBaseFid,String dicCode){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("dicCode", dicCode);
		mybatisDaoContext.delete(SQLID+"delHouseConfByCode", paramMap);
	}
	
	/**
	 * 
	 * 删除房源或者房间配置
	 *
	 * @author zl
	 * @created 2017年7月13日 上午11:49:43
	 *
	 * @param houseBaseFid
	 * @param roomFid
	 * @param dicCode
	 */
	public  void delHouseConfByLikeCode(String houseBaseFid,String roomFid,String dicCode){
		Map<String, Object> paramMap=new HashMap<String,Object>();
		paramMap.put("houseBaseFid", houseBaseFid);
		paramMap.put("roomFid", roomFid);
		paramMap.put("dicCode", dicCode);
		mybatisDaoContext.delete(SQLID+"delHouseConfByLikeCode", paramMap);
	}
	
	
	/**
	 * 
	 *  根据房源fid 获取当前房源的押金
	 *  正常 就一条数据
	 *
	 * @author yd
	 * @created 2016年11月16日 下午4:23:20
	 *
	 * @param houseFid
	 * @return
	 */
	@Deprecated
	public  HouseConfVo findHouseDepositConfByHouseFid(String houseFid){
		return mybatisDaoContext.findOne(SQLID+"findHouseDepositConfByHouseFid", HouseConfVo.class, houseFid);
	}
	
	/**
	 * 
	 * @author zl
	 * @created 2017年06月26日 下午1:43:27
	 * @param houseFid
	 * @param roomFid
	 * @param rentWay
	 * @return
	 */
	public  HouseConfVo findHouseDepositConfByHouseFid(String houseFid,String roomFid,int rentWay){		
		if(rentWay==RentWayEnum.HOUSE.getCode()){
			return mybatisDaoContext.findOne(SQLID+"findHouseDepositConfByHouseFid", HouseConfVo.class, houseFid);
		}else{
			return mybatisDaoContext.findOne(SQLID+"findRoomDepositConfByRoomFid", HouseConfVo.class, roomFid);
		}
		
	}
	
	
	
	
	

	/**
	 * 根据房源housefid,roomFid,dicCode 删除押金配置 
	 *
	 * @author loushuai
	 * @created 2017年6月22日 下午7:29:07
	 *
	 * @param map
	 */
	public void delHouseConfByParmas(Map<String, Object> map) {
		mybatisDaoContext.delete(SQLID+"delHouseConfByParmas", map);
	}
	
	/**
	 * 
	 * 删除配置项跟进fid
	 *
	 * @author bushujie
	 * @created 2017年6月27日 下午3:17:05
	 *
	 * @param fid
	 */
	public void delHouseConfByFid(String fid){
		Map<String, Object> paraMap=new HashMap<>();
		paraMap.put("fid", fid);
		mybatisDaoContext.delete(SQLID+"delHouseConfByFid",paraMap);
	}
	
	/**
	 * 查询有效配置项
	 *
	 * @author zl
	 * @created 2017年6月27日 下午2:10:15
	 *
	 * @param paramsDto
	 * @return
	 */
	public List<HouseConfMsgEntity> findHouseConfValidList(HouseConfParamsDto paramsDto) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("houseBaseFid", paramsDto.getHouseBaseFid());
		map.put("roomFid", paramsDto.getRoomFid());
		map.put("dicCode", paramsDto.getDicCode());
		return mybatisDaoContext.findAll(SQLID+"findHouseConfValidList", HouseConfMsgEntity.class, map);
	}

    /**
     * 删除房间配置
     *
     * @param houseBaseFid
     * @param roomFid
     */
    public void delHouseRoomConf(String houseBaseFid, String roomFid) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("houseBaseFid", houseBaseFid);
        paraMap.put("roomFid", roomFid);
        mybatisDaoContext.update(SQLID + "delHouseRoomConf", paraMap);
    }
    
    /**
     * 
     *合租转整租删除合租配置
     *
     * @author bushujie
     * @created 2017年7月21日 上午11:11:54
     *
     * @param houseBaseFid
     */
    public void delRoomConfByHouseBaseFid(String houseBaseFid){
    	Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("houseBaseFid", houseBaseFid);
    	mybatisDaoContext.delete(SQLID+"delRoomConfByHouseBaseFid", paraMap);
    }
}
