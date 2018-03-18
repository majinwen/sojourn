package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.SharerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>合租人</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月18日 13:57
 * @since 1.0
 */
@Repository("trading.sharerDao")
public class SharerDao {

    private String SQLID = "trading.sharerDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存合租人信息
     * @author jixd
     * @created 2017年09月18日 14:02:21
     * @param
     * @return
     */
    public String saveSharer(SharerEntity sharerEntity){
        String fid = UUIDGenerator.hexUUID();
        if (Check.NuNStr(sharerEntity.getFid())){
            sharerEntity.setFid(fid);
        }
        mybatisDaoContext.save(SQLID + "saveSharer",sharerEntity);
        return  fid;
    }

    /**
     * 根据fid查询合租人
     * @author jixd
     * @created 2017年09月18日 14:06:28
     * @param
     * @return
     */
    public SharerEntity findByFid(String fid){
        return mybatisDaoContext.findOneSlave(SQLID + "findByFid",SharerEntity.class,fid);
    }

    /**
     * 查询合同列表
     * @author jixd
     * @created 2017年09月18日 15:29:40
     * @param
     * @return
     */
    public List<SharerEntity> listSharerByContractId(String contractId){
        return mybatisDaoContext.findAll(SQLID + "listSharerByContractId",SharerEntity.class,contractId);
    }

    /**
     * 查询合住人 根据关联id
     * @author jixd
     * @created 2018年01月29日 09:39:09
     * @param
     * @return
     */
    public List<SharerEntity> listSharerByRentId(Integer rentId){
        return mybatisDaoContext.findAllByMaster(SQLID + "listSharerByRentId",SharerEntity.class,rentId);
    }

    /**
     * 更新合住人信息
     * @author jixd
     * @created 2017年09月18日 14:13:35
     * @param
     * @return
     */
    public int updateByFid(SharerEntity sharerEntity){
        sharerEntity.setFupdatetime(new Date());
        return mybatisDaoContext.update(SQLID + "updateByFid",sharerEntity);
    }

    /**
     * 逻辑删除合住人
     * @author jixd
     * @created 2017年09月18日 16:05:00
     * @param
     * @return
     */
    public int deleteByFid(String fid){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("fid",fid);
        return mybatisDaoContext.update(SQLID + "deleteByFid",paramMap);
    }

    /**
     * 逻辑删除合住人
     * @author jixd
     * @created 2017年09月18日 16:05:00
     * @param
     * @return
     */
    public int deleteByContractId(String contractId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractId);
        return mybatisDaoContext.update(SQLID + "deleteByContractId",paramMap);
    }

    /**
     *
     * 逻辑删除合住人、记录操作人
     *
     * @author zhangyl2
     * @created 2018年01月09日 16:35
     * @param
     * @return
     */
    public int deleteByContractIdAndRecord(SharerEntity sharerEntity) {
        if (!Check.NuNObj(sharerEntity.getRentId())
                && !Check.NuNStr(sharerEntity.getFupdaterid())
                && !Check.NuNStr(sharerEntity.getFupdatername())) {
            return mybatisDaoContext.update(SQLID + "deleteByContractIdAndRecord", sharerEntity);
        }
        return 0;
    }

}
