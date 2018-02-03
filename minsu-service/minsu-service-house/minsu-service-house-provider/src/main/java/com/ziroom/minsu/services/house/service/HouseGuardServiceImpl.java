package com.ziroom.minsu.services.house.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseGuardLogEntity;
import com.ziroom.minsu.entity.house.HouseGuardRelEntity;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseGuardLogDao;
import com.ziroom.minsu.services.house.dao.HouseGuardRelDao;
import com.ziroom.minsu.services.house.dto.HouseGuardDto;
import com.ziroom.minsu.services.house.entity.HouseGuardVo;
import com.ziroom.minsu.valenum.house.HouseChannelEnum;

/**
 * <p>房源管家关系service</p>
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
@Service("house.houseGuardServiceImpl")
public class HouseGuardServiceImpl {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(HouseGuardServiceImpl.class);
	
	@Resource(name = "house.houseGuardRelDao")
	private HouseGuardRelDao houseGuardRelDao;
	
	@Resource(name = "house.houseGuardLogDao")
	private HouseGuardLogDao houseGuardLogDao;
	
	@Resource(name = "house.houseBaseMsgDao")
	private HouseBaseMsgDao houseBaseMsgDao;

	/**
	 * 分页查询房源管家关系列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseGuardDto
	 * @return
	 */
	public PagingResult<HouseGuardVo> findHouseGuardList(HouseGuardDto houseGuardDto) {
		if(houseGuardDto.getRoleType()>0){
			return houseGuardRelDao.findSpecialHouseGuardVoForPage(houseGuardDto);
		}
		return houseGuardRelDao.findHouseGuardVoForPage(houseGuardDto);
	}

	/**
	 * 根据房源逻辑id查询
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseGuardVo findHouseGuardVoByHouseBaseFid(String houseBaseFid) {
		return houseGuardRelDao.findHouseGuardVoByHouseBaseFid(houseBaseFid);
	}
	
	/**
	 * 根据房源逻辑id更新
	 *
	 * @author liyingjie
	 * @created 2016年7月6日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public int updateHouseGuardByHouseBaseFid(HouseGuardRelEntity houseGuardRel) {
		return houseGuardRelDao.updateHouseGuardRelByHouseFid(houseGuardRel);
	}
	
	

	/**
	 * 分页查询房源管家关系日志列表页
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseGuardDto
	 * @return
	 */
	public PagingResult<HouseGuardLogEntity> findHouseGuardLogList(HouseGuardDto houseGuardDto) {
		return houseGuardLogDao.findHouseGuardLogForPage(houseGuardDto);
	}

	/**
	 * 根据房源管家关系逻辑id更新房源管家关系信息
	 * 
	 * 说明：如果更新 当前地推管家 （也会联动去更新房源渠道）
	 *
	 * @author liujun
	 * @created 2016年7月6日
	 *
	 * @param houseGuardRel
	 */
	public void mergeHouseGuardRel(HouseGuardRelEntity houseGuardRel,Integer houseChannel) {
		if(Check.NuNStr(houseGuardRel.getFid())){//新增
			houseGuardRel.setFid(UUIDGenerator.hexUUID());
			houseGuardRel.setCreateDate(new Date());
			houseGuardRelDao.insertHouseGuardRel(houseGuardRel);
		}else{//更新
			houseGuardRelDao.updateHouseGuardRelByFid(houseGuardRel);
			if(!Check.NuNObj(houseChannel)&&houseChannel != HouseChannelEnum.CH_DITUI.getCode()){
				HouseBaseMsgEntity house = new HouseBaseMsgEntity();
				house.setFid(houseGuardRel.getHouseFid());
				house.setHouseChannel(houseChannel);
				this.houseBaseMsgDao.updateHouseBaseMsg(house);
			}
		}
		
		HouseGuardLogEntity houseGuardLog = new HouseGuardLogEntity();
		houseGuardLog.setFid(UUIDGenerator.hexUUID());
		houseGuardLog.setGuardRelFid(houseGuardRel.getFid());
		houseGuardLog.setOldGuardCode(houseGuardRel.getEmpGuardCode());
		houseGuardLog.setOldGuardName(houseGuardRel.getEmpGuardName());
		houseGuardLog.setOldPushCode(houseGuardRel.getEmpPushCode());
		houseGuardLog.setOldPushName(houseGuardRel.getEmpPushName());
		houseGuardLog.setCreaterFid(houseGuardRel.getCreateFid());
		houseGuardLog.setCreateDate(new Date());
		houseGuardLogDao.insertHouseGuardLog(houseGuardLog);
	}

	/**
	 * 判断维护管家信息是否为空
	 *
	 * @author liujun
	 * @param houseGuardRel 
	 * @created 2016年7月6日
	 *
	 * @return
	 */
	@Deprecated
	private boolean isEmpGuardNull(HouseGuardRelEntity houseGuardRel) {
		return Check.NuNStr(houseGuardRel.getEmpGuardCode());
	}
	
	

    /**
     * 
     * 新增房源维护管家关系
     *
     * @author liujun
     * @created 2016年7月5日
     *
     * @param houseDesc
     */
    public void insertHouseGuardRel(HouseGuardRelEntity houseGuardRel) {
		this.houseGuardRelDao.insertHouseGuardRel(houseGuardRel);
	}
    
    
    /**
	 * 
	 * 根据房源逻辑id查询房源维护管家关系
	 *
	 * @author liujun
	 * @created 2016年7月5日
	 *
	 * @param houseBaseFid
	 * @return
	 */
	public HouseGuardRelEntity findHouseGuardRelByHouseBaseFid(String houseBaseFid){
		return houseGuardRelDao.findHouseGuardRelByHouseBaseFid(houseBaseFid);
	}
	/**
	 * 
	 * 条件查询 房源和维护管家  内联
	 *
	 * @author yd
	 * @created 2016年7月12日 下午7:51:39
	 *
	 * @param houseGuardDto
	 * @return
	 */
	public List<HouseGuardVo> findHouseGuardByCondition(HouseGuardDto houseGuardDto){
		return houseGuardRelDao.findHouseGuardByCondition(houseGuardDto);
	}


	/**
	 * 根据管家姓名查询，房源维护管家关联表
	 * @author lisc
	 * @param houseGuardRel
	 * @return
	 */
	public List<HouseGuardRelEntity> findHouseGuardRelByCondition(HouseGuardRelEntity houseGuardRel){
		return houseGuardRelDao.findHouseGuardRelByCondition(houseGuardRel);
	}
}
