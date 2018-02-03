/**
 * @FileName: MsgBaseServiceImpl.java
 * @Package com.ziroom.minsu.services.message.service
 * 
 * @author yd
 * @created 2016年4月18日 下午2:28:18
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.message.service;


import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgCustomizationEntity;
import com.ziroom.minsu.services.message.dao.MsgCustomizationDao;
import com.ziroom.minsu.services.message.entity.MsgCustomizationVo;
import com.ziroom.minsu.valenum.msg.MsgTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>自定义消息回复服务层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author lunan
 * @since 1.0
 * @version 1.0
 */
@Service("message.msgCustomizationimpl")
public class MsgCustomizationimpl {

	/**
	 *  日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(MsgCustomizationimpl.class);

	@Resource(name = "message.msgCustomizationDao")
	private MsgCustomizationDao msgCustomizationDao;

	/**
	 *
	 * 查询用户自定义消息集合
	 *
	 * @author lunan
	 * @created 2017年3月29日
	 * @param uid (参数已在上层校验)
	 * @return
	 */
	public List<MsgCustomizationVo> queryMsgCustomizationByUid(String uid){
		
		List<MsgCustomizationVo>  listMsgCustomizationVo = msgCustomizationDao.findAllMsgCustomizationByUid(uid);
		List<MsgCustomizationVo> listSysMsg = new LinkedList<MsgCustomizationVo>();
		if(!Check.NuNCollection(listMsgCustomizationVo)){
			for (MsgCustomizationVo msgCustomizationVo : listMsgCustomizationVo) {
				if(msgCustomizationVo.getMsgType() == MsgTypeEnum.SYSTEM_USER.getCode()){
					listSysMsg.add(msgCustomizationVo);
				}
			}
		}
		if(!Check.NuNCollection(listSysMsg)){
			listMsgCustomizationVo.removeAll(listSysMsg);
			listMsgCustomizationVo.addAll(listSysMsg);
		}
		return listMsgCustomizationVo;
	}

	/**
	 *
	 * 添加用户自定义消息
	 *
	 * @author lunan
	 * @created 2017年3月29日
	 * @param msgCustom
	 * @return
	 */
	public int addMsgCustomization(MsgCustomizationEntity msgCustom){
		if(Check.NuNObj(msgCustom)){
			return 0;
		}
		if(Check.NuNStr(msgCustom.getFid())){
			msgCustom.setFid(UUIDGenerator.hexUUID());
		}
		return msgCustomizationDao.insertMsgCustomization(msgCustom);
	}

	/**
	 *
	 * 更新用户自定义消息
	 *
	 * @author lunan
	 * @created 2017年3月29日
	 * @param msgCustom
	 * @return
	 */
	public int updateMsgCustomization(MsgCustomizationEntity msgCustom){
		if(Check.NuNObj(msgCustom)){
			return 0;
		}
		if(Check.NuNStr(msgCustom.getFid())){
			return 0;
		}
		return msgCustomizationDao.updateMsgCustomization(msgCustom);
	}
}
