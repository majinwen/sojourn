package com.ziroom.minsu.services.message.dao;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.message.MsgCustomizationEntity;
import com.ziroom.minsu.services.message.entity.MsgCustomizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * <p>环信IM回复自定义消息持久层</p>
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
@Repository("message.msgCustomizationDao")
public class MsgCustomizationDao {

	private String SQLID = "message.msgCustomizationDao.";

	@Autowired
	@Qualifier("message.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 *
	 * 插入自定义消息
	 *
	 * @author lunan
	 * @created 2017年3月28日
	 *
	 * @param msgCustomizationEntity
	 */
	public int insertMsgCustomization(MsgCustomizationEntity msgCustomizationEntity) {
		if (Check.NuNObj(msgCustomizationEntity)) {
			return 0;
		}
		return mybatisDaoContext.save(SQLID + "insertMsgCustomization", msgCustomizationEntity);
	}

	/**
	 *
	 * 更新或者删除自定义消息
	 *
	 * @author lunan
	 * @created 2017年3月28日
	 *
	 * @param msgCustomizationEntity
	 */
	public int updateMsgCustomization(MsgCustomizationEntity msgCustomizationEntity) {
		if (Check.NuNObj(msgCustomizationEntity)) {
			return 0;
		}
		return mybatisDaoContext.update(SQLID + "updateMsgCustomization", msgCustomizationEntity);
	}

	/**
	 *
	 * 查询自定义消息
	 *
	 * @author lunan
	 * @created 2017年3月28日
	 *
	 * @param msgCustomizationFid
	 */
	public MsgCustomizationEntity findStaticResourceByFid(String msgCustomizationFid) {
		if (Check.NuNObj(msgCustomizationFid)) {
			return null;
		}
		return mybatisDaoContext.findOneSlave(SQLID + "findMsgCustomizationByFid", MsgCustomizationEntity.class, msgCustomizationFid);
	}

	/**
	 *
	 * 查询房东自定义消息集合
	 *
	 * @author lunan
	 * @created 2017年3月28日
	 *
	 * @param uid
	 */
	public List<MsgCustomizationVo> findAllMsgCustomizationByUid(String uid) {
		if (Check.NuNStr(uid)) {
			return null;
		}
		return mybatisDaoContext.findAll(SQLID+"findAllMsgCustomizationByUid",MsgCustomizationVo.class,uid);
	}

}
