/**
 * @FileName: MsgHouseServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年4月18日 下午1:21:16
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.message.MsgBaseEntity;
import com.ziroom.minsu.entity.message.MsgHouseEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.vo.HouseStatsVo;
import com.ziroom.minsu.services.message.dao.MsgBaseDao;
import com.ziroom.minsu.services.message.dao.MsgHouseDao;
import com.ziroom.minsu.services.message.dto.MsgBaseRequest;
import com.ziroom.minsu.services.message.dto.MsgHouseRequest;
import com.ziroom.minsu.services.message.entity.MsgHouseListVo;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.msg.IsDelEnum;

/**
 * <p>测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("message.msgHouseServiceImpl")
public class MsgHouseServiceImpl {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgHouseServiceImpl.class);

	@Resource(name = "message.msgHouseDao")
	private MsgHouseDao msgHouseDao;
	@Resource(name = "message.messageSource")
	private MessageSource messageSource;
	@Resource(name = "message.msgBaseDao")
	private MsgBaseDao msgBaseDao;

	/**
	 * 
	 * 条件分页查询留言房源关系实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:41:14
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgHouseEntity> queryByPage(MsgHouseRequest msgHouseRequest){
		LogUtil.info(logger, "当前查询条件msgHouseRequest={}", msgHouseRequest);
		return msgHouseDao.queryByPage(msgHouseRequest);
	}

	/**
	 * 
	 * 查询房客列表
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午8:07:42
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgHouseListVo> queryTenantMsgList(MsgHouseRequest msgHouseRequest){
		LogUtil.info(logger, "当前查询条件msgHouseRequest={}", msgHouseRequest);
		return msgHouseDao.queryTenantMsgList(msgHouseRequest);
	}
	/**
	 * 
	 * 查询房东列表
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午8:08:01
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public PagingResult<MsgHouseListVo> queryLandlordMsgList(MsgHouseRequest msgHouseRequest){
		LogUtil.info(logger, "当前查询条件msgHouseRequest={}", msgHouseRequest);
		return msgHouseDao.queryLandlordMsgList(msgHouseRequest);
	}
	
	/**
	 * 
	 * 根据fid删除 即把状态is_del修改为1
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:54:25
	 *
	 * @param fid
	 */
	public DataTransferObject deleteByFid(MsgHouseEntity msgHouseEntity){

		//返回对象
		DataTransferObject dto = new DataTransferObject();
		if(Check.NuNObj(msgHouseEntity)||Check.NuNStr(msgHouseEntity.getFid())){
			LogUtil.info(logger, "msgHouseEntity or fid is null");
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("修改对象错误");
			return dto;
		}

		msgHouseEntity  = msgHouseDao.queryByFid(msgHouseEntity.getFid());

		if(Check.NuNObj(msgHouseEntity)){
			LogUtil.info(logger, "query msgHouseEntity by fid={},the return is null",msgHouseEntity.getFid());
			dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
			dto.setMsg("根据fid={"+msgHouseEntity.getFid()+"},查询为null");
			return dto;
		}
		msgHouseDao.updateByFid(msgHouseEntity);
		return dto;
	}
	
	/**
	 * 
	 * update MsgHouseEntity by fid
	 * only to update is_del or is_read
	 * @author yd
	 * @created 2016年4月18日 下午2:19:48
	 *
	 * @param fid
	 * @return
	 */
	public int updateByFid(MsgHouseEntity msgHouseEntity){
		
		if(Check.NuNObj(msgHouseEntity)||Check.NuNStr(msgHouseEntity.getFid())){
			LogUtil.info(logger,"msgHouseEntity or fid is null,return 0");
			return 0;
		}
		return msgHouseDao.updateByFid(msgHouseEntity);
	}
	
	/**
	 * 
	 * 保存实体
	 *
	 * @author yd
	 * @created 2016年4月16日 下午3:58:10
	 *
	 * @param msgHouseEntity
	 * @return
	 */
	public int save(MsgHouseEntity msgHouseEntity){
		return this.msgHouseDao.save(msgHouseEntity);
	}
	
	/**
	 *
	 * query MsgHouseEntity by fid
	 *
	 * @author yd
	 * @created 2016年4月18日 下午1:33:43
	 *
	 * @param fid
	 * @return
	 */
	public MsgHouseEntity queryByFid(String fid){
		return this.msgHouseDao.queryByFid(fid);
	}
	
	/**
	 * 
	 * 查询好友（根据房东好友uid或查询房客好友uid）
	 * 
	 * 只能单向查询(不能同时包含两个uid或同时为null)
	 *
	 * @author yd
	 * @created 2016年9月14日 下午4:13:29
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public List<String> queryFriendsUid(MsgHouseRequest msgHouseRequest){
		return msgHouseDao.queryFriendsUid(msgHouseRequest);
	}
	
	/**
	 * 
	 * 按房客uid或房东uid 查询最近一条聊天主记录 （作用： 现在聊天以人与人为单位  之前是以房源  因此查询 最近一条 俩人聊天记录   房客与房东   或者房东与房客  都是符合条件的）
	 *
	 * @author yd
	 * @created 2016年9月21日 下午5:19:46
	 *
	 * @param msgHouseRequest
	 * @return
	 */
	public MsgHouseEntity queryOneMsgHouse(MsgHouseRequest msgHouseRequest){
		return msgHouseDao.queryOneMsgHouse(msgHouseRequest);
	}
	
	/**
	 * 
	 * 保存一条消息记录 当前主记录没有 新增
	 *
	 * @author yd
	 * @created 2016年9月21日 下午6:16:52
	 *
	 * @param msgHouseEntity
	 * @param msgBaseEntity
	 * @return
	 */
	public int saveMsgHouseAndMsgBase(MsgHouseEntity msgHouseEntity,MsgBaseEntity msgBaseEntity,DataTransferObject dto ){
		int count = 0;
		if(!Check.NuNObj(msgHouseEntity)&&!Check.NuNObj(msgBaseEntity)){
			if(Check.NuNStr(msgHouseEntity.getFid())){
				msgHouseEntity.setFid(UUIDGenerator.hexUUID());
				if(Check.NuNObj(dto)) dto = new DataTransferObject();
				count = this.msgHouseDao.save(msgHouseEntity);
			} else{
				count = this.msgHouseDao.updateByFid(msgHouseEntity);
			}
			msgBaseEntity.setMsgHouseFid(msgHouseEntity.getFid());
			dto.putValue("msgHouse", msgHouseEntity);
			count += this.msgBaseDao.save(msgBaseEntity);
			
		}
		return count;
	}
	
	/**
	 * 
	 *  说明: 
	 *   保存消息成功  如果是房东的消息，需要设置之前房东消息距离房东回复时长
	 *   1. 根据条件查询上一条 房东的聊天记录,记为A
	 *   2. 当前保存记录记为B,查找出A记录到B记录 房客的聊天记录 记为listA
	 *   3. listA集合中,用B记录创建时间戳减去listA集合中任一条记录的创建时间，并赋值此记录的回复时长（按房源或房东计算）
	 *   4. 根据fid 修改listA
	 *
	 * @author yd
	 * @created 2016年11月15日 下午5:30:34
	 *
	 */
	public void updateImReplayTime(MsgBaseEntity msgBaseEntity){
		//更新房东回复时长  按房东
		updateImReplayTime(msgBaseEntity,0);
		
		//更新房东回复时长  按房源
		updateImReplayTime(msgBaseEntity,1);
	}
	
	/**
	 * 
	 *  说明: 
	 *   保存消息成功  如果是房东的消息，需要设置之前房东消息距离房东回复时长
	 *   1. 根据条件查询上一条 房东的聊天记录,记为A
	 *   2. 当前保存记录记为B,查找出A记录到B记录 房客的聊天记录 记为listA
	 *   3. listA集合中,用B记录创建时间戳减去listA集合中任一条记录的创建时间，并赋值此记录的回复时长（按房源或房东计算）
	 *   4. 根据fid 修改listA
	 *
	 * @author yd
	 * @param replayFlag 设置回复时长标志  replayFlag = 0 按房东  默认  replayFlag = 1 按房源
	 * @created 2016年11月15日 下午5:30:34
	 *
	 */
	private void updateImReplayTime(MsgBaseEntity msgBaseEntity,int replayFlag){
		
		if(!Check.NuNObj(msgBaseEntity)
				&&!Check.NuNObj(msgBaseEntity.getMsgSenderType())
				&&(msgBaseEntity.getMsgSenderType() == UserTypeEnum.LANDLORD_HUAXIN.getUserType()
				||msgBaseEntity.getMsgSenderType() == UserTypeEnum.LANDLORD.getUserType())){
			
			String dateFormat = "yyyy-MM-dd HH:mm:ss";
			//当前记录
			MsgBaseEntity curMsgBase = this.msgBaseDao.findMsgBaseByFid(msgBaseEntity.getFid());
			if(!Check.NuNObj(curMsgBase)){
				MsgBaseRequest msgBaseRequest = new MsgBaseRequest();
				
				msgBaseRequest.setIsDel(IsDelEnum.NOT_DEL.getCode());
				msgBaseRequest.setMsgHouseFid(msgBaseEntity.getMsgHouseFid());
				msgBaseRequest.setEndTime(DateUtil.dateFormat(curMsgBase.getCreateTime(),dateFormat));
				if(replayFlag == 1) msgBaseRequest.setHouseFid(msgBaseEntity.getHouseFid());
				if(replayFlag == 0)curMsgBase.setHouseFid("");
				//当前记录之前的记录
				MsgBaseEntity preMsgBase = this.msgBaseDao.findPreLanRecord(curMsgBase);
				if(!Check.NuNObj(preMsgBase)){
					msgBaseRequest.setStartTime(DateUtil.dateFormat(preMsgBase.getCreateTime(),dateFormat));
				}
				
				List<MsgBaseEntity> list = this.msgBaseDao.queryByCondition(msgBaseRequest);
				
				if(!Check.NuNCollection(list)){
					List<MsgBaseEntity> updateList = new ArrayList<MsgBaseEntity>();
					for (MsgBaseEntity msg: list) {
						if(msg.getMsgSenderType() !=msgBaseEntity.getMsgSenderType()  ){
							MsgBaseEntity updaeMsgBase = new MsgBaseEntity();
							updaeMsgBase.setFid(msg.getFid());
							if(replayFlag == 1) {
								updaeMsgBase.setReplayTimeHouse(curMsgBase.getCreateTime().getTime()-msg.getCreateTime().getTime());
							}else{
								updaeMsgBase.setReplayTimeLanlord(curMsgBase.getCreateTime().getTime()-msg.getCreateTime().getTime());
							}
							
							updateList.add(updaeMsgBase);
						}
					}
					if(!Check.NuNCollection(updateList)){
						for (MsgBaseEntity updateBase : updateList) {
							this.msgBaseDao.updateByFid(updateBase);
						}
					}
				}
			}
		}
	}

	/**
	 * 查询单位时间内房源(房间)咨询量
	 * 单位时间内相同房客,相同房东,相同房源(房间)的所有IM消息算一次咨询
	 *
	 * @author liujun
	 * @created 2016年12月2日
	 *
	 * @param paramMap
	 * @return
	 */
	public List<HouseStatsVo> queryConsultNumByHouseFid(Map<String, Object> paramMap) {
		return msgHouseDao.queryConsultNumByHouseFid(paramMap);
	}
}
