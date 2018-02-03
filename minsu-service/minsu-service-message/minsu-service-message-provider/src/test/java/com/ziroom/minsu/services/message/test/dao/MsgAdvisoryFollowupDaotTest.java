package com.ziroom.minsu.services.message.test.dao;

/**
 * <p>首次咨询跟踪持久化层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author loushuai
 * @since 1.0
 * @version 1.0
 */

import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.services.message.dao.MsgAdvisoryFollowupDao;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class MsgAdvisoryFollowupDaotTest extends BaseTest{
	
	@Resource(name = "message.msgAdvisoryFollowupDao")
	private MsgAdvisoryFollowupDao msgAdvisoryFollowupDao;
	
	
	@Test
	public void saveMsgAdvisoryFollowupTest() {
		MsgAdvisoryFollowupEntity test= new MsgAdvisoryFollowupEntity();
		test.setAfterStatus(20);
		test.setBeforeStatus(10);
		test.setCreateTime(new Date());
		test.setEmpCode("123456789");
		test.setEmpFid("1123456789");
		test.setEmpName("娄帅测试");
		test.setFid("123456789");
		test.setMsgFirstAdvisoryFid("8a9e988a5c3d4feb015c3d4febc80000");
		test.setRemark("这是新建这张表的第一次测试");
		int save = msgAdvisoryFollowupDao.save(test);
		System.out.println(save);
	}
	
	@Test
	public void getMsgAdvisoryFollowupTest() {
		MsgAdvisoryFollowupEntity byMsgAdvisoryfid = msgAdvisoryFollowupDao.getByFid("8a9e988a5c3d4feb015c3d4febc80000");
		System.out.println(byMsgAdvisoryfid);
	}
}