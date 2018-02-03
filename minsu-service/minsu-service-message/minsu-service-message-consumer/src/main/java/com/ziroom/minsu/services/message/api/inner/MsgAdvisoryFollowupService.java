package com.ziroom.minsu.services.message.api.inner;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/27
 * @version 1.0
 * @since 1.0
 */
public interface MsgAdvisoryFollowupService {

    /**
     * 保存实体
     *
     * @param msgBaseEntity
     * @return
     * @author loushuai
     * @created 2017年5月25日 下午4:49:05
     */
    public String save(String msgAdvisoryFollowupEntity);

    /**
     * 根据t_msg_first_advisory的fid获取MsgAdvisoryFollowupEntity
     *
     * @param msgAdvisoryfid
     * @return
     * @author loushuai
     * @created 2017年5月25日 上午10:58:19
     */
    public String getByFid(String fid);

    /**
     * 根据首次咨询表fid查询所有跟进记录
     *
     * @param
     * @return
     * @author wangwentao
     * @created 2017/5/27 10:08
     */
    public String getAllByFisrtAdvisoryFid(String msgAdvisoryfid);
}
